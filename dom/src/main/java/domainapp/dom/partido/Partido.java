package domainapp.dom.partido;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.IsisApplibModule.ActionDomainEvent;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.ActionLayout.Position;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;
import org.joda.time.LocalDate;

import domainapp.dom.equipo.Equipo;
import domainapp.dom.estado.EstadoPartido;
import domainapp.dom.fecha.Fecha;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Partido"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="partido_id")
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "traerTodos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.partido.Partido"),               
        @javax.jdo.annotations.Query(
        		name = "listarPartidosPorEquipo", language = "JDOQL",
                value = "SELECT "
                		+ "FROM domainapp.dom.partido.Partido "
                        + "WHERE equipoLocal == :equipo || equipoVisitante == :equipo")
})
@javax.jdo.annotations.Unique(name="Partido_nombre_UNQ", members = {"nombre","fecha"})
@DomainObject(bounded=true)
@DomainObjectLayout
public class Partido implements Comparable<Partido>{
	
    public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				"Partido: " + this.getNombre()
				);
	}
	
	public String iconName(){return "partido";}
	
	public static class NameDomainEvent extends PropertyDomainEvent<Partido,String>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	
	
    //NOMBRE DEL PARTIDO
    @MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	private String nombre;
	public String getNombre() {return nombre;}
	public void setNombre(final String nombre) {this.nombre = nombre;}
	
    //EQUIPO LOCAL
    @MemberOrder(sequence = "1.1")
	@Property(editing = Editing.DISABLED)
	@Column(allowsNull = "false")
    @PropertyLayout(named="Local")
	private Equipo equipoLocal;
	public Equipo getEquipoLocal() {return equipoLocal;}
	public void setEquipoLocal(Equipo equipoLocal) {this.equipoLocal = equipoLocal;}

    //EQUIPO VISITANTE
    @MemberOrder(sequence = "1.2")
	@Property(editing = Editing.DISABLED)
	@Column(allowsNull = "false")
    @PropertyLayout(named="Visitante")
	private Equipo equipoVisitante;
	public Equipo getEquipoVisitante() {return equipoVisitante;}
	public void setEquipoVisitante(Equipo equipoVisitante) {this.equipoVisitante = equipoVisitante;}

	//ESTADO DEL PARTIDO
	@MemberOrder(sequence = "2")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	@PropertyLayout(named="Estado")
	private EstadoPartido estadoPartido;
	public EstadoPartido getEstadoPartido() {return estadoPartido;}
	public void setEstadoPartido(EstadoPartido estadoPartido) {this.estadoPartido = estadoPartido;}

	//FECHA
	@MemberOrder(sequence = "3")
    @Column(allowsNull="false")
	private Fecha fecha;
	public Fecha getFecha() {return fecha;}
	public void setFecha(Fecha fecha) {this.fecha = fecha;}
	
	//CUANDO DE JUGO
	@MemberOrder(sequence = "4")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	@PropertyLayout(named="Horario")
	private LocalDate fechaHora;
	public LocalDate getFechaHora() {return fechaHora;}
	public void setFechaHora(LocalDate fechaHora) {this.fechaHora = fechaHora;}
	
	//GOLES DEL EQUIPO LOCAL
	@MemberOrder(sequence = "5")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	@PropertyLayout(named="Goles")
	private int golesLocal;
	public int getGolesLocal() {return golesLocal;}
	public void setGolesLocal(int golesLocal) {this.golesLocal = golesLocal;}
	
	//GOLES DEL EQUIPO VISITANTE
	@MemberOrder(sequence = "6")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	@PropertyLayout(named="Goles")
	private int golesVisitante;
	public int getGolesVisitante() {return golesVisitante;}
	public void setGolesVisitante(int golesVisitante) {this.golesVisitante = golesVisitante;}
	
	

	public static class DeleteDomainEvent extends ActionDomainEvent<Partido> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	
	@Action(
			invokeOn=InvokeOn.OBJECT_AND_COLLECTION
			)
	@ActionLayout(position=Position.RIGHT)
	@MemberOrder(name="estadoPartido", sequence="1")
	public Partido cambiarEstado(){		
		if(this.getEstadoPartido()==EstadoPartido.PENDIENTE){
			this.setEstadoPartido(EstadoPartido.FINALIZADO);
		}
		else if(this.getEstadoPartido()==EstadoPartido.FINALIZADO){
			this.setEstadoPartido(EstadoPartido.PENDIENTE);
		}
		return actionInvocationContext.getInvokedOn().isObject()?this:null;
	}
	
	@javax.inject.Inject
	ActionInvocationContext actionInvocationContext;
	
	@javax.inject.Inject
    RepositoryService repositoryService;
	
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(final Partido o) {
		return ObjectContracts.compare(this, o, "nombre");
	}
}