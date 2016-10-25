package domainapp.dom.torneo;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.IsisApplibModule.ActionDomainEvent;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.division.Division;
import domainapp.dom.estado.Estado;
import domainapp.dom.temporada.Temporada;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Torneo"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="torneo_id")
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "traerTodos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.torneo.Torneo")
})
@javax.jdo.annotations.Unique(name="Torneo_nombre_UNQ", members = {"nombre","temporada"})
@DomainObject(bounded=true)
@DomainObjectLayout
public class Torneo implements Comparable<Torneo>{
	
    public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre", this.getNombre()+" ("+this.getTemporada().getNombre()+
				")");
	}
	
	public String iconName(){return "torneo";}
	
	public static class NameDomainEvent extends PropertyDomainEvent<Torneo,String>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	
	
    //NOMBRE DEL TORNEO
    @MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	private String nombre;
	public String getNombre() {return nombre;}
	public void setNombre(final String nombre) {this.nombre = nombre;}
	
	//ESTADO DEL TORNEO
	@MemberOrder(sequence = "2")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	private Estado estado;
	public Estado getEstado() {return estado;}
	public void setEstado(final Estado estado) {this.estado = estado;}
	
	//TEMPORADA
	@MemberOrder(sequence = "3")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	private Temporada temporada;
	public Temporada getTemporada() {return temporada;}
	public void setTemporada(final Temporada temporada) {this.temporada = temporada;}
	
	//LISTA DE DIVISIONES DE UN TORNEO
	@MemberOrder(sequence = "4")
	@Persistent(mappedBy="torneo", dependentElement="true")
	@CollectionLayout(named="Divisiones")
	private SortedSet<Division> listaDivisiones=new TreeSet<Division>();
	public SortedSet<Division> getListaDivisiones() {return listaDivisiones;}
	public void setListaDivisiones(SortedSet<Division> listaDivisiones) {this.listaDivisiones = listaDivisiones;}

	//OBSERVACIONES
    @MemberOrder(sequence = "9")
	@Column(allowsNull = "true")
	private String observaciones;
	public String getObservaciones() {return observaciones;}
	public void setObservaciones(final String observaciones) {this.observaciones = observaciones;}
	
	//VISIBLE
	@MemberOrder(sequence = "10")
    @Column(allowsNull="true")
    @Property(domainEvent = NameDomainEvent.class, editing=Editing.DISABLED)
    private Boolean visible;
	public Boolean getVisible() {return visible;}
	public void setVisible(final Boolean visible) {this.visible = visible;}

	public static class DeleteDomainEvent extends ActionDomainEvent<Torneo> {

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
		return !listaDivisiones.isEmpty()?"La lista de divisiones debe estar vacia.":null;
	}
	
	@Action(
			invokeOn=InvokeOn.COLLECTION_ONLY
			)
	@MemberOrder(name="estado", sequence="1")
	public Torneo cambiarEstado(){		
		if(this.getEstado()==Estado.ACTIVO){
			setEstado(Estado.INACTIVO);
		}
		else if(this.getEstado()==Estado.INACTIVO){
			setEstado(Estado.ACTIVO);
		}
		return actionInvocationContext.getInvokedOn().isObject()?this:null;
	}
	
	@javax.inject.Inject
	ActionInvocationContext actionInvocationContext;
	
	@javax.inject.Inject
    RepositoryService repositoryService;	
	
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(final Torneo o) {
		return ObjectContracts.compare(this, o, "nombre");
	}
}