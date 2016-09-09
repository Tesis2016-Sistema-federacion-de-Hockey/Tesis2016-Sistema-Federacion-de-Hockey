package domainapp.dom.division;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import org.apache.isis.applib.IsisApplibModule.ActionDomainEvent;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;
import domainapp.dom.estado.Estado;
import domainapp.dom.temporada.Temporada;
import domainapp.dom.torneo.Torneo;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Division"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="division_id")
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "traerTodos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.division.Division")
})
@javax.jdo.annotations.Unique(name="Division_nombre_UNQ", members = {"nombre","temporada","torneo"})
@DomainObject(bounded=true)
@DomainObjectLayout
public class Division implements Comparable<Division>{
	
    public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				"Division: " + this.getNombre());
	}
	
	public String iconName(){return "division";}
	
	public static class NameDomainEvent extends PropertyDomainEvent<Division,String>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	
	
    //NOMBRE DE LA DIVISION
    @MemberOrder(sequence = "1")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private String nombre;
	public String getNombre() {return nombre;}
	public void setNombre(final String nombre) {this.nombre = nombre;}
	
	//ESTADO DE LA DIVISION
	@MemberOrder(sequence = "2")
    @Column(allowsNull="false")
	@Property(editing = Editing.ENABLED)
	private Estado estado;
	public Estado getEstado() {return estado;}
	public void setEstado(final Estado estado) {this.estado = estado;}
	
	//TEMPORADA
	@MemberOrder(sequence = "3")
    @Column(allowsNull="false")
	@Property(editing = Editing.ENABLED)
	private Temporada temporada;
	public Temporada getTemporada() {return temporada;}
	public void setTemporada(final Temporada temporada) {this.temporada = temporada;}
	
	//TORNEO
	@MemberOrder(sequence = "4")
    @Column(allowsNull="false")
	@Property(editing = Editing.ENABLED)
	private Torneo torneo;
	public Torneo getTorneo() {return torneo;}
	public void setTorneo(final Torneo torneo) {this.torneo = torneo;}
	
	//MODALIDAD
    @MemberOrder(sequence = "5")
	@Column(allowsNull = "true")
    @Property(editing = Editing.ENABLED)
    @PropertyLayout(describedAs="TODOS CONTRA TODOS / DIVIDIR EN ZONAS")
	private String modalidad;
	public String getModalidad() {return modalidad;}
	public void setModalidad(final String modalidad) {this.modalidad = modalidad;}

	//PUNTOS POR GANAR
	@MemberOrder(sequence = "6")
    @Column(allowsNull="false")
	@Property(editing = Editing.ENABLED)
	private int puntosGanar;
	public int getPuntosGanar() {return puntosGanar;}
	public void setPuntosGanar(final int puntosGanar) {this.puntosGanar = puntosGanar;}
	
	//PUNTOS POR EMPATAR
	@MemberOrder(sequence = "7")
    @Column(allowsNull="false")
	@Property(editing = Editing.ENABLED)
	private int puntosEmpatar;
	public int getPuntosEmpatar() {return puntosEmpatar;}
	public void setPuntosEmpatar(final int puntosEmpatar) {this.puntosEmpatar = puntosEmpatar;}
	
	//PUNTOS POR PERDER
	@MemberOrder(sequence = "8")
    @Column(allowsNull="false")
	@Property(editing = Editing.ENABLED)
	private int puntosPerder;
	public int getPuntosPerder() {return puntosPerder;}
	public void setPuntosPerder(final int puntosPerder) {this.puntosPerder = puntosPerder;}	
	
	
	
	//VISIBLE
	@MemberOrder(sequence = "10")
    @Column(allowsNull="true")
	@Property(editing = Editing.DISABLED)
    private Boolean visible;
	public Boolean getVisible() {return visible;}
	public void setVisible(final Boolean visible) {this.visible = visible;}

	public static class DeleteDomainEvent extends ActionDomainEvent<Division> {

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
    RepositoryService repositoryService;
	
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(final Division o) {
		return ObjectContracts.compare(this, o, "nombre");
	}
}