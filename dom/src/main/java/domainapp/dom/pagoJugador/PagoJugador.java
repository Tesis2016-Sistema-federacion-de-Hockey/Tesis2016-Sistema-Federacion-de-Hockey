package domainapp.dom.pagoJugador;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.club.Club;
import domainapp.dom.jugador.Jugador;
import domainapp.dom.pago.Pago;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "PagoJugador"
)

@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="pagoJugador_id")
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")
public class PagoJugador extends Pago implements Comparable<PagoJugador>{

	public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				"Pago: " + this.getNroRecibo());
	}
	
	//JUGADOR
	@MemberOrder(sequence = "14")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "true")
	private Jugador jugador;	
	public Jugador getJugador() {return jugador;}
	public void setJugador(Jugador jugador) {this.jugador = jugador;}


	@Override
	public int compareTo(PagoJugador arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@javax.inject.Inject
    RepositoryService repositoryService;

}
