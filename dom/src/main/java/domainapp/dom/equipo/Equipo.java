package domainapp.dom.equipo;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.club.Club;
import domainapp.dom.division.Division;
import domainapp.dom.estado.Estado;
import domainapp.dom.jugador.JugadorServicio;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Equipo"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="equipo_id")
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
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
				"Equipo: " + this.getNombre());
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