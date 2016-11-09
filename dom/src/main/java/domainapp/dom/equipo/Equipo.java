package domainapp.dom.equipo;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.annotation.ActionLayout.Position;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import domainapp.dom.club.Club;
import domainapp.dom.division.Division;
import domainapp.dom.estado.Estado;
import domainapp.dom.gol.Gol;
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
	@Property(domainEvent = NameDomainEvent.class, editing=Editing.DISABLED)
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
	@Property(editing = Editing.DISABLED)
	private Division division;
	public Division getDivision() {return division;}
	public void setDivision(Division division) {this.division = division;}
	
	//VISIBLE
	@MemberOrder(sequence = "10")
    @Column(allowsNull="true")
	@Property(editing = Editing.DISABLED, hidden=Where.EVERYWHERE)
    private Boolean visible=true;
	public Boolean getVisible() {return visible;}
	public void setVisible(final Boolean visible) {this.visible = visible;}
	
	//LISTA DE BUENA FE
	@MemberOrder(sequence = "5")
	@Persistent(table="equipo_jugador")
	@Join(column="equipo_id")
	@Element(column="jugador_id")
	@CollectionLayout(named="Lista de Buena Fe")
	private SortedSet<Jugador> listaBuenaFe=new TreeSet<Jugador>();
	public SortedSet<Jugador> getListaBuenaFe() {return listaBuenaFe;}
	public void setListaBuenaFe(SortedSet<Jugador> listaBuenaFe) {this.listaBuenaFe = listaBuenaFe;}

	//METODO PARA AGREGAR UN JUGADOR A LA LISTA DE BUENA FE DEL EQUIPO
	@MemberOrder(sequence = "10")
	@ActionLayout(named="Agregar Jugador", cssClassFa="fa fa-thumbs-o-up")
	public Equipo agregarJugadorAListaBuenaFe(Jugador e) {
		listaBuenaFe.add(e);
		e.getEquipos().add(this);
		return this;
	}
		
	public List<Jugador> choices0AgregarJugadorAListaBuenaFe(){
		
		return repositoryService.allMatches(Jugador.class, new Predicate<Jugador>() {
			@Override
			public boolean apply(Jugador jug) {
				
				return (jugadorServicio.listarJugadoresActivosSegunClub(club).contains(jug)&& !listaBuenaFe.contains(jug))?true:false;
				
//				return (jugadorServicio.listarJugadoresActivosSegunClub(club).contains(jug)&& !listaBuenaFe.contains(jug)&&jug.getEquipo()==null)?true:false;
			}
		});
	}
		
	//METODO PARA QUITAR UN JUGADOR DE LA LISTA DE BUENA FE DEL EQUIPO
	@MemberOrder(sequence = "11")
	@ActionLayout(named="Quitar Jugador", cssClassFa="fa fa-thumbs-o-down")
	public Equipo quitarJugadorDeListaBuenaFe(Jugador e) {
		listaBuenaFe.remove(e);
		e.getEquipos().remove(this);
		return this;
	}

		

		
	public List<Jugador> choices0QuitarJugadorDeListaBuenaFe(){
		
		return Lists.newArrayList(getListaBuenaFe());
	}
		
	//LISTA DE GOL
	@MemberOrder(sequence = "18")
	@Persistent(mappedBy = "equipo", dependentElement = "true")
	@CollectionLayout(named="Goles")
	private SortedSet<Gol> goles = new TreeSet<Gol>();	
	public SortedSet<Gol> getGoles() {
		return goles;
	}
	public void setGoles(SortedSet<Gol> goles) {
		this.goles = goles;
	}
		
	//LISTA DE GOLES EN CONTRA
	@MemberOrder(sequence = "19")
	@Persistent(mappedBy = "equipoContrario", dependentElement = "true")
	@CollectionLayout(named="Goles en contra")
	private SortedSet<Gol> golesEnContra = new TreeSet<Gol>();	
	public SortedSet<Gol> getGolesEnContra() {
		return golesEnContra;
	}
	public void setGolesEnContra(SortedSet<Gol> golesEnContra) {
		this.golesEnContra = golesEnContra;
	}

	public static class DeleteDomainEvent extends Equipo.ActionDomainEvent {

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
		division.getListaEquipos().remove(this);
		repositoryService.remove(this);
    }
		
	//REVISAR
	public String disableDelete(){
		
		if (!division.getListaFechas().isEmpty()) return "El equipo esta participando en un torneo.";
		
		else if (!listaBuenaFe.isEmpty()) return "La lista de Buena Fe debe estar vacia.";
		
		return "";
			
		}
	
	
	public static class ActivoDomainEvent extends Equipo.ActionDomainEvent {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L; }
	
	public static abstract class ActionDomainEvent extends domainapp.dom.DomainAppDomainModule.ActionDomainEvent<Equipo> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L; }

    @Action(
            domainEvent =ActivoDomainEvent.class,
            invokeOn = InvokeOn.OBJECT_AND_COLLECTION
    )
    @ActionLayout(position=Position.BELOW, named="Poner Activo")
	@MemberOrder(name="estado", sequence="1")
    public Equipo activo() {		
			setEstado(Estado.ACTIVO);
			return actionInvocationContext.getInvokedOn().isObject()?this:null;
	}
    
    public static class InactivoDomainEvent extends Equipo.ActionDomainEvent {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L; }

    @Action(
            domainEvent =InactivoDomainEvent.class,
            invokeOn = InvokeOn.OBJECT_AND_COLLECTION
    )
    @ActionLayout(position=Position.BELOW, named="Poner Inactivo")
	@MemberOrder(name="estado", sequence="2")
    public Equipo inactivo() {		
			setEstado(Estado.INACTIVO);
			return actionInvocationContext.getInvokedOn().isObject()?this:null;
	}
    
    @javax.inject.Inject
	ActionInvocationContext actionInvocationContext;
		
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