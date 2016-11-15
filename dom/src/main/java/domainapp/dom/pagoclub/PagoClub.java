package domainapp.dom.pagoclub;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.services.i18n.TranslatableString;

import domainapp.dom.pago.Pago;
import domainapp.dom.pagojugador.PagoJugador;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "PagoClub"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="pagoClub_id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
public class PagoClub extends Pago implements Comparable<PagoJugador>{
	
	public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				"Pago: " + this.getNroRecibo());
	}
	
	
	
	
	
	
	
	
	
	

	
	@Override
	public int compareTo(PagoJugador arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}