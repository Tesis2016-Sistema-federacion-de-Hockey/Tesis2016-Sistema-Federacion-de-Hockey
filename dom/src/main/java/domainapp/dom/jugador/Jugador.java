package domainapp.dom.jugador;

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
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.club.Club;
import domainapp.dom.cuotajugador.CuotaJugador;
import domainapp.dom.cuotajugador.CuotaJugadorServicio;
import domainapp.dom.domicilio.Domicilio;
import domainapp.dom.equipo.Equipo;
import domainapp.dom.estado.Estado;
import domainapp.dom.gol.Gol;
import domainapp.dom.pagojugador.PagoJugador;
import domainapp.dom.partido.Partido;
import domainapp.dom.persona.Persona;
import domainapp.dom.sector.Sector;
import domainapp.dom.tarjeta.Tarjeta;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Jugador"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="jugador_id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "traerTodos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.jugador.Jugador "),               
        @javax.jdo.annotations.Query(
                name = "buscarPorDocumento", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.jugador.Jugador "
                        + "WHERE documento.indexOf(:documento) >= 0 "),
        @javax.jdo.annotations.Query(
        		name = "listarJugadoresActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.jugador.Jugador "
                        + "WHERE estado=='Activo'"),
        @javax.jdo.annotations.Query(
        		name = "listarJugadoresInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.jugador.Jugador "
                        + "WHERE estado=='Inactivo'"),
        @javax.jdo.annotations.Query(
        		name = "listarJugadoresSinClub", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.jugador.Jugador "
                        + "WHERE club_club_id_OID==1"),
        @javax.jdo.annotations.Query(
        		name = "listarJugadoresActivosSegunClub", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.jugador.Jugador "
                        + "WHERE (club == :club) && (estado == 'ACTIVO')"),
        @javax.jdo.annotations.Query(
        		name = "buscarDocumentoDuplicado", language = "JDOQL",
        		value = "SELECT "
				+ "FROM dom.jugador.Jugador "
				+ "WHERE documento ==:documento"),
        @javax.jdo.annotations.Query(
                name = "traerJugador", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.jugador.Jugador "
                        + "WHERE nombre == :nombre "
                        + "|| nombre.indexOf(:nombre) >= 0")
})
@javax.jdo.annotations.Unique(name="Jugador_ficha_UNQ", members = {"ficha"})
@DomainObject(bounded=true)
@DomainObjectLayout
public class Jugador extends Persona implements Comparable<Jugador> {

    public static final int NAME_LENGTH = 40;
    
    public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				this.getApellido() + ", " + this.getNombre()+" (DNI: "+this.getDocumento()+")");
	}
    
    public String iconName(){
    	return (getSector()==Sector.DAMAS)? "dama":"caballero";
    }
    
    public String cssClass(){
    	return (getEstado()==Estado.ACTIVO)? "activo":"inactivo";
    }
    
    public static class NameDomainEvent extends PropertyDomainEvent<Jugador,String> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
    
	//SECTOR
    @MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	private Sector sector;
	public Sector getSector() {return sector;}
	public void setSector(final Sector sector) {this.sector = sector;}

	//FICHA
    @MemberOrder(sequence = "2")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private String ficha;
	public String getFicha() {return ficha;}
	public void setFicha(final String ficha) {this.ficha = ficha;}
	
	//DOMICILIO
	@MemberOrder(sequence = "10")
	@Property(editing = Editing.ENABLED, hidden=Where.ALL_TABLES)	
	@Column(name="domicilio_id")	
	private Domicilio domicilio;	
	public Domicilio getDomicilio() {return domicilio;}
	public void setDomicilio(final Domicilio domicilio) {this.domicilio = domicilio;}
	
	//CLUB	
	@MemberOrder(sequence = "16")
	@Column(allowsNull = "true")
	private Club club;
	public Club getClub() {return club;}
	public void setClub(final Club club) {this.club = club;}
	
	//LISTA DE EQUIPOS
	@Persistent(mappedBy = "listaBuenaFe")
	private SortedSet<Equipo>equipos=new TreeSet<Equipo>();
	public SortedSet<Equipo> getEquipos() {return equipos;}
	public void setEquipos(SortedSet<Equipo> equipos) {this.equipos = equipos;}
	
	//LISTA DE PARTIDOS
	@Persistent(mappedBy = "listaPartido")
	private SortedSet<Partido>partidos=new TreeSet<Partido>();
	public SortedSet<Partido> getPartidos() {return partidos;}
	public void setPartidos(SortedSet<Partido> partidos) {this.partidos = partidos;}
	
	//LISTA DE TARJETAS
	@MemberOrder(sequence = "15")
	@Persistent(mappedBy = "jugador", dependentElement = "true")
	@CollectionLayout(named="Tarjetas")
	private SortedSet<Tarjeta> tarjetas = new TreeSet<Tarjeta>();	
	public SortedSet<Tarjeta> getTarjetas() {return tarjetas;}
	public void setTarjetas(SortedSet<Tarjeta> tarjetas) {this.tarjetas = tarjetas;}
		
	//LISTA DE CUOTAS
	@MemberOrder(sequence = "16")
	@Persistent(table="jugador_cuotajugador")
	@Join(column="jugador_id")
	@Element(column="cuotaJugador_id")
	@CollectionLayout(named="Lista de Cuotas a Pagar")
	private SortedSet<CuotaJugador> cuotasJugador = new TreeSet<CuotaJugador>();	
	public SortedSet<CuotaJugador> getCuotasJugador() {return cuotasJugador;}
	public void setCuotasJugador(final SortedSet<CuotaJugador> cuotasJugador) {this.cuotasJugador = cuotasJugador;}

//	//METODO PARA AGREGAR CUOTA		
//	@MemberOrder(sequence = "21")
//	@ActionLayout(named="Agregar Cuota", cssClassFa="fa fa-plus")
//	public Jugador agregarCuota(CuotaJugador e) {
//		if(e == null || cuotasJugador.contains(e)) return this;
//		cuotasJugador.add(e);
//		e.getListaJugadores().add(this);
//		return this;
//	}
//
//	//METODO PARA QUITAR CUOTA		
//	@MemberOrder(sequence = "22")
//	@ActionLayout(named="Quitar Cuota", cssClassFa="fa fa-trash-o")
//	public Jugador quitarCuota(CuotaJugador e) {
//		if(e == null || cuotasJugador.contains(e)) return this;
//		cuotasJugador.remove(e);
//		e.getListaJugadores().remove(this);
//		return this;
//	}	
//	
//	public List<CuotaJugador> choices0AgregarCuota(){
//		
//		return repositoryService.allMatches(CuotaJugador.class, new Predicate<CuotaJugador>() {
//			@Override
//			public boolean apply(CuotaJugador c) {
//				
//				return (cuotaJugadorServicio.listarCuotasJugador().contains(c))?true:false;
//			}
//		});
//	}	
	
	
	
	
	//PAGOS
	@MemberOrder(sequence = "17")
	@Persistent(mappedBy="jugador", dependentElement="true")
	@CollectionLayout(named="Pagos realizados")
	private SortedSet<PagoJugador> pagosJugador=new TreeSet<PagoJugador>();
	public SortedSet<PagoJugador> getPagosJugador() {return pagosJugador;}
	public void setPagosJugador(SortedSet<PagoJugador> pagosJugador) {this.pagosJugador = pagosJugador;}
	
	//LISTA DE GOL
	@MemberOrder(sequence = "18")
	@Persistent(mappedBy = "jugador", dependentElement = "true")
	@CollectionLayout(named="Goles")
	private SortedSet<Gol> goles = new TreeSet<Gol>();	
	public SortedSet<Gol> getGoles() {
		return goles;
	}
	public void setGoles(SortedSet<Gol> goles) {
		this.goles = goles;
	}
	
	
	public static class DeleteDomainEvent extends ActionDomainEvent<Jugador> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
    
	@Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
	@ActionLayout(named="Eliminar Jugador")
    public void delete() {
        repositoryService.remove(this);
    }
	
	@Action(
			invokeOn=InvokeOn.COLLECTION_ONLY
			)
	@MemberOrder(name="estado", sequence="1")
	public Jugador cambiarEstado(){		
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
    
    @javax.inject.Inject
    CuotaJugadorServicio cuotaJugadorServicio;
    
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(final Jugador o) {
		return org.apache.isis.applib.util.ObjectContracts.compare(this, o, "apellido", "nombre");
	}
}