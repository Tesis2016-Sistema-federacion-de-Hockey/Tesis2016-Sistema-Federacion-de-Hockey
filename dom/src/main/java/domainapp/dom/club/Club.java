package domainapp.dom.club;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
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

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Club"
)
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
                        + "FROM domainapp.dom.club.Club "),
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
	@ActionLayout(named="Agregar Jugador al Club", cssClassFa="fa fa-thumbs-o-up")
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
	    obj.setCuotas(e.getCuotas());
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
	
	//CUOTAS
	@MemberOrder(sequence = "11")
	@Persistent(mappedBy = "clubes", dependentElement = "true")
	@CollectionLayout(named="Cuotas del Club")
	private SortedSet<CuotaClub> cuotasClub = new TreeSet<CuotaClub>();
	public SortedSet<CuotaClub> getCuotasClub() {return cuotasClub;}
	public void setCuotasClub(SortedSet<CuotaClub> cuotasClub) {this.cuotasClub = cuotasClub;}

	public static class DeleteDomainEvent extends ActionDomainEvent<Club> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	
	@Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
	public void delete() {
			repositoryService.remove(this);
    }
	
	public String disableDelete(){
		return !listaJugadores.isEmpty()?"La lista de jugadores debe estar vacia.":null;
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
}