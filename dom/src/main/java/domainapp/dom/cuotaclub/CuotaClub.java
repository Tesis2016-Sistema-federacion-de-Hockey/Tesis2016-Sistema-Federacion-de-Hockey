package domainapp.dom.cuotaclub;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.services.i18n.TranslatableString;

import domainapp.dom.club.Club;
import domainapp.dom.cuota.Cuota;
import domainapp.dom.jugador.Jugador;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Cuotaclub"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="cuotaClub_id")
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")

public class CuotaClub extends Cuota implements Comparable<CuotaClub>{
	
	public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				"Cuota: " + this.getDetalle());
	}
	
	//LISTA DE CLUBES
  	@MemberOrder(sequence = "5")
  	@Persistent(table="CUOTACLUBES_CLUBES")
  	@Join(column="CUOTACLUB_ID")
  	@Element(column="CLUB_ID")
  	private SortedSet<Club> clubes=new TreeSet<Club>();
  	public SortedSet<Club> getClubes() {return clubes;}
	public void setClubes(SortedSet<Club> clubes) {this.clubes = clubes;}


	@Override
	public int compareTo(CuotaClub o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
