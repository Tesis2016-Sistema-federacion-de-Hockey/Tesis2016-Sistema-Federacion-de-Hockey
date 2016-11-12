package domainapp.dom.partido;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.IsisApplibModule.ActionDomainEvent;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.ActionLayout.Position;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;
import org.joda.time.DateTime;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import domainapp.dom.equipo.Equipo;
import domainapp.dom.estado.EstadoPartido;
import domainapp.dom.fecha.Fecha;
import domainapp.dom.gol.Gol;
import domainapp.dom.jugador.Jugador;
import domainapp.dom.jugador.JugadorServicio;
import domainapp.dom.tarjeta.Tarjeta;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Partido"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="partido_id")
@javax.jdo.annotations.Version(
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
				this.getNombre()
				);
	}
	
	public String iconName(){return "partido";}
	
	public static class NameDomainEvent extends PropertyDomainEvent<Partido,String>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}
	
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

	//FECHA DEL FIXTURE
	@MemberOrder(sequence = "3")
    @Column(allowsNull="false")
	@Property(editing = Editing.DISABLED)
	private Fecha fecha;
	public Fecha getFecha() {return fecha;}
	public void setFecha(Fecha fecha) {this.fecha = fecha;}
	
	//HORARIO DE CUANDO DE JUGO
	@MemberOrder(sequence = "4")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	@PropertyLayout(named="Horario")
	private DateTime fechaHora;
	public DateTime getFechaHora() {return fechaHora;}
	public void setFechaHora(DateTime fechaHora) {this.fechaHora = fechaHora;}
	
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
	
	//LISTA DE JUGADORES DEL PARTIDO
	@MemberOrder(sequence = "7")
	@Persistent(table="partido_jugador")
	@Join(column="partido_id")
	@Element(column="jugador_id")
	@CollectionLayout(named="Lista de jugadores del Partido", hidden=Where.EVERYWHERE)
	private SortedSet<Jugador> listaPartido=new TreeSet<Jugador>();
	public SortedSet<Jugador> getListaPartido() {return listaPartido;}
	public void setListaPartido(SortedSet<Jugador> listaPartido) {this.listaPartido = listaPartido;}

	//LISTA DE JUGADORES DEL EQUIPO LOCAL
	@MemberOrder(sequence = "7.1")
	@CollectionLayout(named="Lista de jugadores LOCAL")
	private SortedSet<Jugador> listaJugadoresLocal=new TreeSet<Jugador>();
	public SortedSet<Jugador> getListaJugadoresLocal() {return listaJugadoresLocal;}
	public void setListaJugadoresLocal(SortedSet<Jugador> listaJugadoresLocal) {this.listaJugadoresLocal = listaJugadoresLocal;}

	//LISTA DE JUGADORES DEL EQUIPO VISITANTE
	@MemberOrder(sequence = "7.2")
	@CollectionLayout(named="Lista de jugadores VISITANTE")
	private SortedSet<Jugador> listaJugadoresVisitante=new TreeSet<Jugador>();
	public SortedSet<Jugador> getListaJugadoresVisitante() {return listaJugadoresVisitante;}
	public void setListaJugadoresVisitante(SortedSet<Jugador> listaJugadoresVisitante) {this.listaJugadoresVisitante = listaJugadoresVisitante;}
	
	//METODO PARA AGREGAR UN JUGADOR AL EQUIPO LOCAL		
	@MemberOrder(sequence = "9.1")
	@ActionLayout(named="Agregar Jugador LOCAL", cssClassFa="fa fa-thumbs-o-up")
	public Partido agregarJugadorLocal(Jugador e) {
		if(e == null || listaJugadoresLocal.contains(e)) return this;
		listaJugadoresLocal.add(e);
	    e.getPartidos().add(this);
	    return this;
	}
	
	//METODO PARA AGREGAR UN JUGADOR AL EQUIPO VISITANTE		
	@MemberOrder(sequence = "9.2")
	@ActionLayout(named="Agregar Jugador VISITANTE", cssClassFa="fa fa-thumbs-o-up")
	public Partido agregarJugadorVisitante(Jugador e) {
		if(e == null || listaJugadoresVisitante.contains(e)) return this;
		listaJugadoresVisitante.add(e);
	    e.getPartidos().add(this);
	    return this;
	}
	
	public List<Jugador> choices0AgregarJugadorLocal(){
		return repositoryService.allMatches(Jugador.class, new Predicate<Jugador>() {
			@Override
			public boolean apply(Jugador jug) {
				return (jugadorServicio.listarJugadoresActivosSegunClub(equipoLocal.getClub()).contains(jug)&& !listaJugadoresLocal.contains(jug)&&equipoLocal.getListaBuenaFe().contains(jug))?true:false;
			}
		});
	}
	
	public List<Jugador> choices0AgregarJugadorVisitante(){
		return repositoryService.allMatches(Jugador.class, new Predicate<Jugador>() {
			@Override
			public boolean apply(Jugador jug) {
				return (jugadorServicio.listarJugadoresActivosSegunClub(equipoVisitante.getClub()).contains(jug)&& !listaJugadoresVisitante.contains(jug)&&equipoVisitante.getListaBuenaFe().contains(jug))?true:false;
			}
		});
	}
	
	//METODO PARA QUITAR UN JUGADOR DEL EQUIPO LOCAL
	@MemberOrder(sequence = "9.3")
	@ActionLayout(named="Quitar Jugador LOCAL", cssClassFa="fa fa-thumbs-o-down")
	public Partido quitarJugadorLocal(Jugador e) {
//			e.getTarjetas().clear();  //FALTA
		listaJugadoresLocal.remove(e);
		e.getPartidos().remove(this);
		return this;
	}
	
	//METODO PARA QUITAR UN JUGADOR DEL EQUIPO VISITANTE
	@MemberOrder(sequence = "9.4")
	@ActionLayout(named="Quitar Jugador VISITANTE", cssClassFa="fa fa-thumbs-o-down")
	public Partido quitarJugadorVisitante(Jugador e) {
		listaJugadoresVisitante.remove(e);
		e.getPartidos().remove(this);
		return this;
	}
	
	public List<Jugador> choices0QuitarJugadorLocal(){
		return Lists.newArrayList(getListaJugadoresLocal());
	}
	
	public List<Jugador> choices0QuitarJugadorVisitante(){
		return Lists.newArrayList(getListaJugadoresVisitante());
	}
	
	//METODO PARA AGREGAR TODOS LOS JUGADORES AL EQUIPO LOCAL
	@MemberOrder(sequence = "9.5")
	@ActionLayout(named="Agregar todos LOCAL", cssClassFa="fa fa-thumbs-o-up")
	public Partido agregarTodosLocal() {
		for (Iterator<?> it=equipoLocal.getListaBuenaFe().iterator();it.hasNext();){
			Jugador jug=((Jugador)it.next());
			listaJugadoresLocal.add(jug);
			jug.getPartidos().add(this);
		}
	    return this;
	}
	
	//METODO PARA AGREGAR TODOS LOS JUGADORES AL EQUIPO VISITANTE
	@MemberOrder(sequence = "9.6")
	@ActionLayout(named="Agregar todos VISITANTE", cssClassFa="fa fa-thumbs-o-up")
	public Partido agregarTodosVisitante() {
		for (Iterator<?> it=equipoVisitante.getListaBuenaFe().iterator();it.hasNext();){
			Jugador jug=((Jugador)it.next());
			listaJugadoresVisitante.add(jug);
			jug.getPartidos().add(this);
		}
		this.setListaJugadoresVisitante(listaJugadoresVisitante);
	    return this;
	}

	//LISTA DE GOL
	@MemberOrder(sequence = "10")
	@Persistent(mappedBy = "partido", dependentElement = "true")
	@CollectionLayout(named="Goles")
	private SortedSet<Gol> goles = new TreeSet<Gol>();	
	public SortedSet<Gol> getGoles() {
		return goles;
	}
	public void setGoles(SortedSet<Gol> goles) {
		this.goles = goles;
	}
	
	
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
	
	
	
	//LISTA DE TARJETAS
	@MemberOrder(sequence = "7.3")
	@CollectionLayout(named="Tarjetas")
	private SortedSet<Tarjeta> tarjetas = new TreeSet<Tarjeta>();	
	public SortedSet<Tarjeta> getTarjetas() {return tarjetas;}
	public void setTarjetas(SortedSet<Tarjeta> tarjetas) {this.tarjetas = tarjetas;}
	
	
	@javax.inject.Inject
	ActionInvocationContext actionInvocationContext;
	
	@javax.inject.Inject
	JugadorServicio jugadorServicio;

	@javax.inject.Inject
    RepositoryService repositoryService;
	
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(final Partido o) {
		return ObjectContracts.compare(this, o, "nombre");
	}
}