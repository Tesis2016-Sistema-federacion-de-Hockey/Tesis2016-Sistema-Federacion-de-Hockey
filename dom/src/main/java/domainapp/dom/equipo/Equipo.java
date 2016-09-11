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
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import com.google.common.base.Predicate;

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
	//  strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")
	@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "traerTodos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.equipo.Equipo")
	})
@javax.jdo.annotations.Unique(name="Equipo_nombre_UNQ", members = {"nombre","club","division"})
@DomainObject(bounded=true)
@DomainObjectLayout
public class Equipo implements Comparable<Equipo>{
	
    public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				"Equipo: " + this.getNombre()+" ("+this.getDivision().getNombre()+", "+getDivision().getTemporada().getNombre()+")");

    }
	
	public String iconName(){return "equipo";}
	
	public static class NameDomainEvent extends PropertyDomainEvent<Equipo,String>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	
	
    //NOMBRE DEL EQUIPO
    @MemberOrder(sequence = "1")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private String nombre;
	public String getNombre() {return nombre;}
	public void setNombre(final String nombre) {this.nombre = nombre;}
	
	//ESTADO DEL EQUIPO
	@MemberOrder(sequence = "2")
    @Column(allowsNull="false")
	@Property(editing = Editing.ENABLED)
	private Estado estado;
	public Estado getEstado() {return estado;}
	public void setEstado(final Estado estado) {this.estado = estado;}
	
	//CLUB
	@MemberOrder(sequence = "3")
    @Column(allowsNull="false")
	@Property(editing = Editing.ENABLED)
	private Club club;
	public Club getClub() {return club;}
	public void setClub(final Club club) {this.club = club;}

	//DIVISION
	@MemberOrder(sequence = "4")
    @Column(allowsNull="false")
	@Property(editing = Editing.ENABLED)
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
	
	//LISTA DE JUGADORES QUE PERTENECEN AL EQUIPO
	@MemberOrder(sequence = "5")
	@Persistent(mappedBy="equipo", dependentElement="true")
	private SortedSet<Jugador> listaJugadoresEquipo=new TreeSet<Jugador>();
	public SortedSet<Jugador> getListaJugadoresEquipo() {return listaJugadoresEquipo;}
	public void setListaJugadoresEquipo(SortedSet<Jugador> listaJugadoresEquipo) {this.listaJugadoresEquipo = listaJugadoresEquipo;}

	//METODO PARA AGREGAR UN JUGADOR AL EQUIPO
	@MemberOrder(sequence = "6")
	public void agregarJugadorAEquipo(Jugador e) {
		if(e == null || listaJugadoresEquipo.contains(e)) return;
	    e.setEquipo(this);
	    listaJugadoresEquipo.add(e);
	}
	
	public List<Jugador> choices0AgregarJugadorAEquipo(){
		
		return repositoryService.allMatches(Jugador.class, new Predicate<Jugador>() {
			@Override
			public boolean apply(Jugador jug) {
				
				return (jugadorServicio.listarJugadoresActivosSegunClub(club).contains(jug)&& !listaJugadoresEquipo.contains(jug))    ?true:false;
			}
		});

		
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
	public void delete() {
        repositoryService.remove(this);
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