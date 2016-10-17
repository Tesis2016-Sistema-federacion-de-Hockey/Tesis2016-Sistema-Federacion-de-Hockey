package domainapp.dom.temporada;

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
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.estado.Estado;
import domainapp.dom.torneo.Torneo;


@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Temporada"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="temporada_id")
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "traerTodos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.temporada.Temporada")
})
@javax.jdo.annotations.Unique(name="Temporada_nombre_UNQ", members = {"nombre"})
@DomainObject(bounded=true)
@DomainObjectLayout
public class Temporada implements Comparable<Temporada>{
	
    public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				"Temporada: " + this.getNombre());
	}
	
	public String iconName(){return "temporada";}
	
	public static class NameDomainEvent extends PropertyDomainEvent<Temporada,String>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	
	
    //NOMBRE DE LA TEMPORADA
    @MemberOrder(sequence = "1")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private String nombre;
	public String getNombre() {return nombre;}
	public void setNombre(final String nombre) {this.nombre = nombre;}

	//ESTADO DE LA TEMPORADA
	@MemberOrder(sequence = "2")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	private Estado estado;
	public Estado getEstado() {return estado;}
	public void setEstado(Estado estado) {this.estado = estado;}

    //OBSERVACIONES
    @MemberOrder(sequence = "3")
	@Column(allowsNull = "true")
	private String observaciones;
	public String getObservaciones() {return observaciones;}
	public void setObservaciones(String observaciones) {this.observaciones = observaciones;}

	//LISTA DE TORNEOS DE UNA TEMPORADA
	@MemberOrder(sequence = "4")
	@Persistent(mappedBy="temporada", dependentElement="true")
	private SortedSet<Torneo> listaTorneos=new TreeSet<Torneo>();
	public SortedSet<Torneo> getListaTorneos() {return listaTorneos;}
	public void setListaTorneos(SortedSet<Torneo> listaTorneos) {this.listaTorneos = listaTorneos;}
		
	//VISIBLE
	@MemberOrder(sequence = "10")
    @Column(allowsNull="true")
    @Property(domainEvent = NameDomainEvent.class, editing=Editing.DISABLED)
    private Boolean visible;
	public Boolean getVisible() {return visible;}
	public void setVisible(Boolean visible) {this.visible = visible;}

	public static class DeleteDomainEvent extends ActionDomainEvent<Temporada> {

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
		return !listaTorneos.isEmpty()?"La lista de torneos debe estar vacia.":null;
	}
	
	@javax.inject.Inject
    RepositoryService repositoryService;
	
	@javax.inject.Inject
	TemporadaServicio temporadaServicio;
	
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(final Temporada o) {
		return ObjectContracts.compare(this, o, "nombre");
	}
}