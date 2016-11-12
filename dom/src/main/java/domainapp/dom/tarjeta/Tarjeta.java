package domainapp.dom.tarjeta;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.IsisApplibModule.ActionDomainEvent;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;
import org.joda.time.DateTime;

import domainapp.dom.club.Club;
import domainapp.dom.jugador.Jugador;
import domainapp.dom.partido.Partido;
import domainapp.dom.partido.Partido.NameDomainEvent;


@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Tarjeta"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="tarjeta_id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "traerTodas", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.tarjeta.Tarjeta")
})
@javax.jdo.annotations.Unique(name="Tarjeta_nombre_UNQ", members = {"minutoTarjeta","tipoTarjeta","partido","jugador"})
@DomainObject(bounded=true)
@DomainObjectLayout
public class Tarjeta implements Comparable<Tarjeta>{
	
    public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre", this.getTipoTarjeta().getNombre());
	}
	
    public String iconName(){
    	return (getTipoTarjeta()==TipoTarjeta.VERDE)? "verde":"amarilla";
    }

	
	public static class NameDomainEvent extends PropertyDomainEvent<Tarjeta,String>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	
	//TIPO DE TARJETA
	@MemberOrder(sequence = "1")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	@PropertyLayout(named="Tipo")
	private TipoTarjeta tipoTarjeta;
	public TipoTarjeta getTipoTarjeta() {return tipoTarjeta;}
	public void setTipoTarjeta(TipoTarjeta tipoTarjeta) {this.tipoTarjeta = tipoTarjeta;}
	
	//MINUTO
	@MemberOrder(sequence = "2")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	@PropertyLayout(named="Minuto")
	private int minutoTarjeta;
	public int getMinutoTarjeta() {return minutoTarjeta;}
	public void setMinutoTarjeta(int minutoTarjeta) {this.minutoTarjeta = minutoTarjeta;}

	//PARTIDO
	@MemberOrder(sequence = "3")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	private Partido partido;
	public Partido getPartido() {return partido;}
	public void setPartido(Partido partido) {this.partido = partido;}

	//JUGADOR
	@MemberOrder(sequence = "4")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	private Jugador jugador;
	public Jugador getJugador() {return jugador;}
	public void setJugador(Jugador jugador) {this.jugador = jugador;}
	
	//HORARIO DE CUANDO SE SACO LA TARJETA
	private DateTime fechaHora;
	public DateTime getFechaHora() {
		fechaHora=partido.getFechaHora();
		return fechaHora;
		}
 
	public static class DeleteDomainEvent extends ActionDomainEvent<Tarjeta> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	
	@Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
	public void delete() {
		partido.getTarjetas().remove(this);
		jugador.getTarjetas().remove(this);
        repositoryService.remove(this);
    }
	
	@javax.inject.Inject
    RepositoryService repositoryService;
	
	@javax.inject.Inject
	TarjetaServicio tarjetaServicio;
	
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(final Tarjeta o) {
		return ObjectContracts.compare(this, o, "minutoTarjeta");
	}
}