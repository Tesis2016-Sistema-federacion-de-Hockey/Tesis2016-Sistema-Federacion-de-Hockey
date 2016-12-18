package domainapp.dom.division;

import java.util.ArrayList;
import java.util.Iterator;
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
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.equipo.Equipo;
import domainapp.dom.estado.Estado;
import domainapp.dom.fecha.Fecha;
import domainapp.dom.jugador.Jugador;
import domainapp.dom.modalidad.Modalidad;
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
@javax.jdo.annotations.Unique(name="Division_nombre_UNQ", members = {"nombre", "torneo"})
@DomainObject(bounded=true)
@DomainObjectLayout
public class Division implements Comparable<Division>{
	
    public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				this.getNombre()+" ("+this.getTorneo().getNombre()
				+", "+this.getTorneo().getTemporada().getNombre()+")");
	}
	
	public String iconName(){return "division";}
	
	public static class NameDomainEvent extends PropertyDomainEvent<Division,String>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	
    //NOMBRE DE LA DIVISION
    @MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	private String nombre;
	public String getNombre() {return nombre;}
	public void setNombre(final String nombre) {this.nombre = nombre;}
	
	//CATEGORIA (MAXIMO DE AÑOS QUE UN JUGADOR PUEDE TENER PARA PARTICIPAR EN ESTA DIVISION)
	@MemberOrder(sequence = "1.1")
    @Column(allowsNull="false")
	private int categoria;
	public int getCategoria() {return categoria;}
	public void setCategoria(int categoria) {this.categoria = categoria;}

	//ESTADO DE LA DIVISION
	@MemberOrder(sequence = "2")
    @Column(allowsNull="false")
	private Estado estado;
	public Estado getEstado() {return estado;}
	public void setEstado(final Estado estado) {this.estado = estado;}
	
	//TORNEO
	@MemberOrder(sequence = "4")
	@Property(editing = Editing.DISABLED)
    @Column(allowsNull="false")
	private Torneo torneo;
	public Torneo getTorneo() {return torneo;}
	public void setTorneo(final Torneo torneo) {this.torneo = torneo;}
	
	//MODALIDAD
    @MemberOrder(sequence = "5")
	@Column(allowsNull = "true")
    @PropertyLayout(describedAs="IDA Y VUELTA / IDA")
	private Modalidad modalidad;
	public Modalidad getModalidad() {return modalidad;}
	public void setModalidad(Modalidad modalidad) {this.modalidad = modalidad;}

	//PUNTOS POR GANAR
	@MemberOrder(sequence = "6")
    @Column(allowsNull="false")
	private int puntosGanar;
	public int getPuntosGanar() {return puntosGanar;}
	public void setPuntosGanar(final int puntosGanar) {this.puntosGanar = puntosGanar;}
	
	//PUNTOS POR EMPATAR
	@MemberOrder(sequence = "7")
    @Column(allowsNull="false")
	private int puntosEmpatar;
	public int getPuntosEmpatar() {return puntosEmpatar;}
	public void setPuntosEmpatar(final int puntosEmpatar) {this.puntosEmpatar = puntosEmpatar;}
	
	//PUNTOS POR PERDER
	@MemberOrder(sequence = "8")
    @Column(allowsNull="false")
	private int puntosPerder;
	public int getPuntosPerder() {return puntosPerder;}
	public void setPuntosPerder(final int puntosPerder) {this.puntosPerder = puntosPerder;}
	
	//LISTA DE EQUIPOS DE UNA DIVISION
	@MemberOrder(sequence = "9")
	@Persistent(mappedBy="division", dependentElement="true")
	@CollectionLayout(named="Equipos")
	private SortedSet<Equipo> listaEquipos=new TreeSet<Equipo>();
	public SortedSet<Equipo> getListaEquipos() {return listaEquipos;}
	public void setListaEquipos(SortedSet<Equipo> listaEquipos) {this.listaEquipos = listaEquipos;}
	
	//LISTA DE FECHAS
	@MemberOrder(sequence = "9")
	@Persistent(mappedBy="division", dependentElement="true")
	@CollectionLayout(named="Fixture")
	private SortedSet<Fecha> listaFechas=new TreeSet<Fecha>();
	public SortedSet<Fecha> getListaFechas() {return listaFechas;}
	public void setListaFechas(SortedSet<Fecha> listaFechas) {this.listaFechas = listaFechas;}
	
	//VISIBLE
	@MemberOrder(sequence = "10")
    @Column(allowsNull="true")
	@Property(editing = Editing.DISABLED, hidden=Where.EVERYWHERE)
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
	@ActionLayout(named="Eliminar Division")
	public void delete() {
        repositoryService.remove(this);
    }
	
	public String disableDelete(){
		return !listaEquipos.isEmpty()?"La lista de equipos debe estar vacia.":null;
	}
	
	@Action(
			invokeOn=InvokeOn.COLLECTION_ONLY
			)
	@MemberOrder(name="estado", sequence="1")
	public Division cambiarEstado(){		
		if(this.getEstado()==Estado.ACTIVO){
			setEstado(Estado.INACTIVO);
		}
		else if(this.getEstado()==Estado.INACTIVO){
			setEstado(Estado.ACTIVO);
		}
		return actionInvocationContext.getInvokedOn().isObject()?this:null;
	}
	
	@ActionLayout(named="Tabla de Goleadores", hidden=Where.EVERYWHERE)
	public List<Jugador> tablaDeGoleadores(){
		List<Jugador> jugadores = new ArrayList<Jugador>();
		for (Iterator<?> it=this.getListaEquipos().iterator();it.hasNext();){
			Equipo eq=((Equipo)it.next());
			for (Iterator<?> it2=eq.getListaBuenaFe().iterator();it2.hasNext();){
				Jugador jug=(Jugador)it2.next();
				if (jug.golesEquipo(this) > 0){
					jugadores.add(jug);
				}
			}
		}
		return jugadores;
	}
	
	@ActionLayout(named="Tabla de Tarjetas", hidden=Where.EVERYWHERE)
	public List<Jugador> tablaDeTarjetas(){
		List<Jugador> jugadores = new ArrayList<Jugador>();
		for (Iterator<?> it=this.getListaEquipos().iterator();it.hasNext();){
			Equipo eq=((Equipo)it.next());
			for (Iterator<?> it2=eq.getListaBuenaFe().iterator();it2.hasNext();){
				Jugador jug=(Jugador)it2.next();
				
				
				
				
				
//				if (jug.golesEquipo(this) > 0){
//					jugadores.add(jug);
//				}
			}
		}
		return jugadores;
	}
	
	@javax.inject.Inject
	ActionInvocationContext actionInvocationContext;
	
	@javax.inject.Inject
    RepositoryService repositoryService;
	
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(final Division o) {
		return ObjectContracts.compare(this, o, "nombre");
	}
}