package domainapp.dom.equipo;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.IsisApplibModule.ActionDomainEvent;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import domainapp.dom.club.Club;
import domainapp.dom.division.Division;
import domainapp.dom.estado.Estado;
import domainapp.dom.jugador.Jugador;
import domainapp.dom.jugador.JugadorServicio;

@javax.jdo.annotations.PersistenceCapable(
    identityType=IdentityType.DATASTORE,
    schema = "simple",
    table = "Equipo")
@javax.jdo.annotations.DatastoreIdentity(
    strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
     column="equipo_id")
@javax.jdo.annotations.Version(
    strategy= VersionStrategy.DATE_TIME,
    column="version")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "traerTodos", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.equipo.Equipo"),
    @javax.jdo.annotations.Query(
            name = "traerEquipo", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.equipo.Equipo "
            		+ "WHERE club == :club"),
    @javax.jdo.annotations.Query(
            name = "listarTodosLosEquiposDelClub", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.equipo.Equipo "
            		+ "WHERE (club == :club) && (estado == 'ACTIVO')")
})
@javax.jdo.annotations.Unique(name="Equipo_nombre_UNQ", members = {"nombre","club","division"})
@DomainObject(bounded=true)
@DomainObjectLayout
public class Equipo implements Comparable<Equipo>{
	
    public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre", this.getNombre());

    }
	
	public String iconName(){return "equipo";}
	
	public static class NameDomainEvent extends PropertyDomainEvent<Equipo,String>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	
    //NOMBRE DEL EQUIPO
    @MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	private String nombre;
	public String getNombre() {return nombre;}
	public void setNombre(final String nombre) {this.nombre = nombre;}
	
	//ESTADO DEL EQUIPO
	@MemberOrder(sequence = "2")
    @Column(allowsNull="false")
	private Estado estado;
	public Estado getEstado() {return estado;}
	public void setEstado(final Estado estado) {this.estado = estado;}
	
	//CLUB
	@MemberOrder(sequence = "3")
    @Column(allowsNull="false")
	private Club club;
	public Club getClub() {return club;}
	public void setClub(final Club club) {this.club = club;}

	//DIVISION
	@MemberOrder(sequence = "4")
    @Column(allowsNull="false")
	private Division division;
	public Division getDivision() {return division;}
	public void setDivision(Division division) {this.division = division;}
	
	//VISIBLE
	@MemberOrder(sequence = "10")
    @Column(allowsNull="true")
	@Property(editing = Editing.DISABLED)
    private Boolean visible=true;
	public Boolean getVisible() {return visible;}
	public void setVisible(final Boolean visible) {this.visible = visible;}
	
	//LISTA DE BUENA FE
	@MemberOrder(sequence = "5")
	@Persistent(mappedBy="equipo", dependentElement="true")
	@CollectionLayout(named="Lista de Buena Fe")
	private SortedSet<Jugador> listaBuenaFe=new TreeSet<Jugador>();
	public SortedSet<Jugador> getListaBuenaFe() {return listaBuenaFe;}
	public void setListaBuenaFe(SortedSet<Jugador> listaBuenaFe) {this.listaBuenaFe = listaBuenaFe;}

	//METODO PARA AGREGAR UN JUGADOR A LA LISTA DE BUENA FE DEL EQUIPO
	@MemberOrder(sequence = "10")
	@ActionLayout(named="Agregar Jugador")
	public Equipo agregarJugadorAListaBuenaFe(Jugador e) {
		if(e == null || listaBuenaFe.contains(e)) return this;
	    e.setEquipo(this);
	    listaBuenaFe.add(e);
	    return this;
	}
		
	public List<Jugador> choices0AgregarJugadorAListaBuenaFe(){
		
		return repositoryService.allMatches(Jugador.class, new Predicate<Jugador>() {
			@Override
			public boolean apply(Jugador jug) {
				
				return (jugadorServicio.listarJugadoresActivosSegunClub(club).contains(jug)&& !listaBuenaFe.contains(jug)&&jug.getEquipo()==null)?true:false;
			}
		});
	}
		
	//METODO PARA QUITAR UN JUGADOR DE LA LISTA DE BUENA FE DEL EQUIPO
	@MemberOrder(sequence = "11")
	@ActionLayout(named="Quitar Jugador")
	public Equipo quitarJugadorDeListaBuenaFe(Jugador e) {
		if(e == null || !listaBuenaFe.contains(e)) return this;
		
		//Duplico el jugador e y luego lo elimino
	    final Jugador obj = repositoryService.instantiate(Jugador.class);
        obj.setSector(e.getSector());
        obj.setFicha(e.getFicha());
        obj.setNombre(e.getNombre());
        obj.setApellido(e.getApellido());
        obj.setTipo(e.getTipo());
        obj.setDocumento(e.getDocumento());
        obj.setFechaNacimiento(e.getFechaNacimiento());
        obj.setEstado(e.getEstado());
        obj.setEmail(e.getEmail());
        obj.setTelefono(e.getTelefono());
        obj.setCelular(e.getCelular());
        obj.setDomicilio(e.getDomicilio());
        obj.setClub(e.getClub());
        repositoryService.persist(obj);
        obj.setEquipo(null);
	    listaBuenaFe.remove(e);
	    return this;
	}
	
	public List<Jugador> choices0QuitarJugadorDeListaBuenaFe(){
		
		return Lists.newArrayList(getListaBuenaFe());
	}
		
	public static class DeleteDomainEvent extends ActionDomainEvent<Equipo> {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;}
		
	@Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
	@ActionLayout(named="Eliminar Equipo")
	public void delete() {
		repositoryService.remove(this);
    }
	
	public String disableDelete(){
		return !listaBuenaFe.isEmpty()?"La lista de Buena Fe debe estar vacia.":null;
	}
	
	@javax.inject.Inject
    JugadorServicio jugadorServicio;
	
	@javax.inject.Inject
    RepositoryService repositoryService;
	
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(final Equipo o) {
		return ObjectContracts.compare(this, o, "nombre");
	}
}