/*
#	This file is part of SIFHON.
#
#	Copyright ( C ) 2016 , SIFHON
#
#   SIFHON is free software: you can redistribute it and/or modify
#   it under the terms of the GNU General Public License as published by
#   the Free Software Foundation, either version 3 of the License, or
#   (at your option) any later version.
#
#   SIFHON is distributed in the hope that it will be useful,
#   but WITHOUT ANY WARRANTY; without even the implied warranty of
#   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#   GNU General Public License for more details.
#
#   You should have received a copy of the GNU General Public License
#   along with SIFHON.  If not, see <http://www.gnu.org/licenses/>.
*/

package domainapp.dom.club;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;
import javax.swing.JOptionPane;

import org.apache.isis.applib.IsisApplibModule.ActionDomainEvent;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import domainapp.dom.cuotaclub.CuotaClub;
import domainapp.dom.domicilio.Domicilio;
import domainapp.dom.jugador.Jugador;
import domainapp.dom.jugador.JugadorServicio;
import domainapp.dom.pagoclub.PagoClub;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Club")
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="club_id")
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "traerTodos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.club.Club"),
        @javax.jdo.annotations.Query(
                name = "buscarPorNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.club.Club "
                        + "WHERE nombre.indexOf(:nombre) >= 0 "),
        @javax.jdo.annotations.Query(
                name = "traerClub", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.club.Club "
                        + "WHERE nombre == :nombre "
                        + "|| nombre.indexOf(:nombre) >= 0")
})
@javax.jdo.annotations.Unique(name="Club_idInterno_UNQ", members = {"idInterno"})
@DomainObject(bounded=true)
//@DomainObject(bounded=true, autoCompleteRepository = PagoClubServicio.class, autoCompleteAction = "buscarClub")
@DomainObjectLayout(bookmarking=BookmarkPolicy.AS_ROOT)
public class Club implements Comparable<Club> {
	
	public static final int NAME_LENGTH = 40;
    
    public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre", this.getNombre());
	}

    public String iconName(){return "Club";}
    
    public static class NameDomainEvent extends PropertyDomainEvent<Club,String> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
    
    //NOMBRE
    @MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	private String nombre;
	public String getNombre() {return nombre;}
	public void setNombre(final String nombre) {this.nombre = nombre;}
			
	//NOMBRE INSTITUCIONAL
	@MemberOrder(sequence = "2")
	@Column(allowsNull = "true")
	private String nombreInstitucional;
	public String getNombreInstitucional() {return nombreInstitucional;}
	public void setNombreInstitucional(final String nombreInstitucional) {this.nombreInstitucional = nombreInstitucional;}
	
	//ANIO AFILIACION
	@MemberOrder(sequence = "3")
	@Column(allowsNull = "true")
	@PropertyLayout(named="Año de Afiliacion")
	private String anioAfiliacion;
	public String getAnioAfiliacion() {return anioAfiliacion;}
	public void setAnioAfiliacion(final String anioAfiliacion) {this.anioAfiliacion = anioAfiliacion;}
	
	//ID INTERNO
	@MemberOrder(sequence = "4")
	@Column(allowsNull = "false")
	private String idInterno;
	public String getIdInterno() {return idInterno;}
	public void setIdInterno(final String idInterno) {this.idInterno = idInterno;}
	
	//PERSONERIA JURIDICA
	@MemberOrder(sequence = "5")
	@Column(allowsNull = "true")
	private String personeriaJuridica;
	public String getPersoneriaJuridica(){return personeriaJuridica;}
	public void setPersoneriaJuridica(final String personeriaJuridica) {this.personeriaJuridica = personeriaJuridica;}
	
	//EMAIL
	@MemberOrder(sequence = "5")
	@Column(allowsNull = "true")
	private String email;
	public String getEmail() {return email;}
	public void setEmail(final String email) {this.email = email;}
	
	//TELEFONO
	@MemberOrder(sequence = "6")
	@Column(allowsNull = "true")
	private String telefono;
	public String getTelefono() {return telefono;}
	public void setTelefono(final String telefono) {this.telefono = telefono;}
	
	//DOMICILIO
	@MemberOrder(sequence = "7")
	@Property(hidden=Where.ALL_TABLES)	
	@Column(name="domicilio_id")	
	private Domicilio domicilio;	
	public Domicilio getDomicilio() {return domicilio;}
	public void setDomicilio(final Domicilio domicilio) {this.domicilio = domicilio;}
	
	//LISTADO DE JUGADORES DEL CLUB
	@MemberOrder(sequence = "8")
	@Persistent(mappedBy="club", dependentElement="true")
	@CollectionLayout(named="Jugadores pertenecientes al Club")
	private SortedSet<Jugador> listaJugadores=new TreeSet<Jugador>();
	public SortedSet<Jugador> getListaJugadores() {return listaJugadores;}
	public void setListaJugadores(final SortedSet<Jugador> listaJugadores) {this.listaJugadores = listaJugadores;}

	//METODO PARA AGREGAR UN JUGADOR A LA LISTA DE JUGADORES DEL CLUB		
	@MemberOrder(sequence = "9")
	@ActionLayout(named="Agregar Jugador al Club", cssClassFa="fa fa-thumbs-o-up fa-lg")
	public Club agregarJugador(Jugador e) {
		if(e == null || listaJugadores.contains(e)) return this;
	    e.setClub(this);
	    listaJugadores.add(e);
	    return this;
	}
	
	public List<Jugador> choices0AgregarJugador(){		
		return repositoryService.allMatches(Jugador.class, new Predicate<Jugador>() {
			@Override
			public boolean apply(Jugador jug) {				
				return jugadorServicio.listarJugadoresSinClub().contains(jug)?true:false;
			}
		});
	}
	
	public Jugador default0AgregarJugador(final Jugador j){
		return j!=null? getListaJugadores().first():null;
	}
	
	//METODO PARA QUITAR UN JUGADOR DE LA LISTA DE JUGADORES DEL CLUB
	@MemberOrder(sequence = "10")
	@ActionLayout(named="Quitar Jugador del Club", cssClassFa="fa fa-thumbs-o-down")
	public Club quitarJugador(Jugador e) {
	    if(e == null || !listaJugadores.contains(e)) return this;
	    
	    if (!e.getPartidos().isEmpty()){
	    	JOptionPane.showMessageDialog(null, "No se puede quitar este Jugador. Tiene partidos jugados.");
	    	return this;
	    }
	    
	    //Duplico el jugador e y luego lo elimino
	    final Jugador obj = repositoryService.instantiate(Jugador.class);
	    obj.setApellido(e.getApellido());
	    obj.setCelular(e.getCelular());
	    obj.setDocumento(e.getDocumento());
	    obj.setDomicilio(e.getDomicilio());
	    obj.setEmail(e.getEmail());
	    obj.setEquipos(e.getEquipos());
	    obj.setEstado(e.getEstado());
	    obj.setFechaNacimiento(e.getFechaNacimiento());
	    obj.setFicha(e.getFicha());
	    obj.setGoles(e.getGoles());
	    obj.setNombre(e.getNombre());
	    obj.setPagosJugador(e.getPagosJugador());
	    obj.setPartidos(e.getPartidos());
	    obj.setPartidos(e.getPartidos());
	    obj.setSector(e.getSector());
	    obj.setTelefono(e.getTelefono());
	    obj.setTipo(e.getTipo());
	    obj.setClub(null);
        
	    repositoryService.persist(obj);
	    
	    listaJugadores.remove(e);
	    
	    return this;
	}
	
	public List<Jugador> choices0QuitarJugador(){
		
		return Lists.newArrayList(getListaJugadores());
	}
	
	//DEUDA (sirve para ver cuanto debe cada club de cuotaClub)
	@PropertyLayout(named="Deuda", describedAs="Deuda de cuotas")
	@Property(editing=Editing.DISABLED, hidden=Where.EVERYWHERE)
	@Column(allowsNull = "true")
	private BigDecimal deuda;
	public BigDecimal getDeuda() {return deuda;}
	public void setDeuda(BigDecimal deuda) {this.deuda = deuda;}

	//LISTA DE CUOTAS
	@MemberOrder(sequence = "11")
	@Persistent(table="club_cuotaclub")
	@Join(column="club_id")
	@Element(column="cuotaClub_id")
	@CollectionLayout(named="Lista de Cuotas")
	private SortedSet<CuotaClub> cuotasClub = new TreeSet<CuotaClub>();
	public SortedSet<CuotaClub> getCuotasClub() {return cuotasClub;}
	public void setCuotasClub(final SortedSet<CuotaClub> cuotasClub) {this.cuotasClub = cuotasClub;}

	//LISTA DE PAGOS
	@MemberOrder(sequence = "11")
	@Persistent(mappedBy="club", dependentElement="true")
	@CollectionLayout(named="Pagos realizados")
	private SortedSet<PagoClub> pagosClub = new TreeSet<PagoClub>();
	public SortedSet<PagoClub> getPagosClub() {return pagosClub;}
	public void setPagosClub(SortedSet<PagoClub> pagosClub) {this.pagosClub = pagosClub;}

	public static class DeleteDomainEvent extends ActionDomainEvent<Club> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	
	@Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
	@ActionLayout(named="Eliminar Club")
	public void delete() {
			repositoryService.remove(this);
    }
	
	public String disableDelete(){
		
		if (!listaJugadores.isEmpty()) return "La lista de jugadores debe estar vacia.";
		
		else if (!cuotasClub.isEmpty()) return "La lista de cuotas debe estar vacia.";
		
		return "";
	}

	@SuppressWarnings("deprecation")
	public int compareTo(final Club other) {
        return ObjectContracts.compare(this, other, "nombre");
    }
	
	@javax.inject.Inject
    RepositoryService repositoryService;
    
    @javax.inject.Inject
    ClubServicio clubServicio;

    @javax.inject.Inject
    JugadorServicio jugadorServicio;

    @ActionLayout(named="Deuda de Cuota")
    public BigDecimal deuda(CuotaClub cuotaClub) {
		
		BigDecimal sumaPagosParciales=new BigDecimal(0); // suma de pagos parciales
		
		for(PagoClub pagoCl:this.getPagosClub()){
			
			if (pagoCl.getCuotaClub()==cuotaClub){
				
				sumaPagosParciales=sumaPagosParciales.add(pagoCl.getValor());
			}
		}
		
		return (cuotaClub.getValor().subtract(sumaPagosParciales));
	}
}