package domainapp.app.viewmodels;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import java.math.BigDecimal;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Title;

import domainapp.dom.cuotajugador.CuotaJugador;
import domainapp.dom.jugador.Jugador;

@DomainObjectLayout(
        named="Deuda de Cuota de Jugador",
        bookmarking = BookmarkPolicy.AS_ROOT        
)
@DomainObject(
        nature = Nature.VIEW_MODEL
)
public class CuotaJugadorViewModel implements Comparable<CuotaJugadorViewModel>{
	
	public String iconName(){return "CuotaClubViewModel";}
	
	public CuotaJugadorViewModel() {}
		
	public CuotaJugadorViewModel(Jugador jugador, CuotaJugador cuotaJugador) {
		this.cuotaJugador=cuotaJugador;
		this.jugador=jugador;
	}
	
	@PropertyLayout(named="Cuota")
	private CuotaJugador cuotaJugador;
	public CuotaJugador getCuotaJugador() {return cuotaJugador;}
	public void setCuotaJugador(CuotaJugador cuotaJugador) {this.cuotaJugador = cuotaJugador;}
	
	@PropertyLayout(named="Jugador")
	private Jugador jugador;
	@Title
	public Jugador getJugador() {return jugador;}
	public void setJugador(Jugador jugador) {this.jugador = jugador;}

	@PropertyLayout(named="Deuda")
	public BigDecimal getDeudaJugador(){
		return jugador.deuda(cuotaJugador);
	}

	@Override
	public int compareTo(CuotaJugadorViewModel o) {
		// TODO Auto-generated method stub		
		return 0;
	}
}