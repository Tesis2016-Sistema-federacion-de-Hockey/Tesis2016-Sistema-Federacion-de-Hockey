package domainapp.app.viewmodels;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import java.math.BigDecimal;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Title;

import domainapp.dom.club.Club;
import domainapp.dom.cuotaclub.CuotaClub;

@DomainObjectLayout(
        named="Deuda de Cuota de Club",
        bookmarking = BookmarkPolicy.AS_ROOT        
)
@DomainObject(
        nature = Nature.VIEW_MODEL
)
public class CuotaClubViewModel implements Comparable<CuotaClubViewModel>{
	
	public String iconName(){return "CuotaClubViewModel";}
	
	public CuotaClubViewModel() {}
		
	public CuotaClubViewModel(Club club, CuotaClub cuotaClub) {
		this.cuotaClub=cuotaClub;
		this.club=club;
	}
	
	@PropertyLayout(named="Cuota")
	private CuotaClub cuotaClub;
	public CuotaClub getCuotaClub() {return cuotaClub;}
	public void setCuotaClub(CuotaClub cuotaClub) {this.cuotaClub = cuotaClub;}
	
	@PropertyLayout(named="Club")
	private Club club;
	@Title
	public Club getClub() {return club;}
	public void setClub(Club club) {this.club = club;}

	@PropertyLayout(named="Deuda")
	public BigDecimal getDeudaClub(){
		return club.deuda(cuotaClub);
	}

	@Override
	public int compareTo(CuotaClubViewModel o) {
		// TODO Auto-generated method stub
		return 0;
	}
}