package domainapp.dom.pagoclub;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.club.Club;
import domainapp.dom.cuotaclub.CuotaClub;
import domainapp.dom.pago.Pago;
import domainapp.dom.torneo.Torneo.NameDomainEvent;

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
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "traerTodos", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.pagoclub.PagoClub"),
    @javax.jdo.annotations.Query(
            name = "listarPagosPorClub", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.pagoclub.PagoClub "
            		+ "WHERE (club == :club)"),
    @javax.jdo.annotations.Query(
            name = "buscarPagoClub", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.pagoclub.PagoClub "
            		+ "WHERE nroRecibo == :nroRecibo")
})
@javax.jdo.annotations.Unique(name="PagoClub_UNQ", members = {"nroRecibo"})
@DomainObject(bounded=true)
@DomainObjectLayout
public class PagoClub extends Pago implements Comparable<PagoClub>{
	
	public TranslatableString title() {
		return TranslatableString.tr("{nroRecibo}", "nroRecibo",
				"Pago: " + this.getNroRecibo());
	}
	
	public String iconName(){return "PagoClub";}
	
	//CLUB
	@MemberOrder(sequence = "4")
    @Column(allowsNull="false")
	private Club club;
	public Club getClub() {return club;}
	public void setClub(Club club) {this.club = club;}

	//CUOTA CLUB
	@MemberOrder(sequence = "5")
    @Column(allowsNull="false")
	@Property(domainEvent = NameDomainEvent.class)
	private CuotaClub cuotaClub;
	public CuotaClub getCuotaClub() {return cuotaClub;}
	public void setCuotaClub(final CuotaClub cuotaClub) {this.cuotaClub = cuotaClub;}

	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(final PagoClub other) {
		// TODO Auto-generated method stub
		return ObjectContracts.compare(this, other, "nroRecibo");
	}
}