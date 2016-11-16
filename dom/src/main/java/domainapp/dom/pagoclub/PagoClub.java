package domainapp.dom.pagoclub;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.club.Club;
import domainapp.dom.pago.Pago;

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
                    + "FROM domainapp.dom.pagoclub.PagoClub")
})public class PagoClub extends Pago implements Comparable<PagoClub>{
	
	public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				"Pago: " + this.getNroRecibo());
	}
	
	public String iconName(){return "PagoClub";}
	
	//LISTA DE CLUBES
  	@MemberOrder(sequence = "5")
  	@Persistent(mappedBy = "pagosClub")
  	@CollectionLayout(named="Lista de Clubes que deben Pagar")
  	private SortedSet<Club> listaClubes=new TreeSet<Club>();
  	public SortedSet<Club> getListaClubes() {return listaClubes;}
  	public void setListaClubes(SortedSet<Club> listaClubes) {this.listaClubes = listaClubes;}

	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(final PagoClub other) {
		// TODO Auto-generated method stub
		return ObjectContracts.compare(this, other, "nroRecibo");
	}
}