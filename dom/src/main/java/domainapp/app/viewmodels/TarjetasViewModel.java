package domainapp.app.viewmodels;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Title;

import domainapp.dom.division.Division;
import domainapp.dom.jugador.Jugador;
import domainapp.dom.tarjeta.Tarjeta;

@DomainObjectLayout(
        named="Tabla de Tarjetas",
        bookmarking = BookmarkPolicy.AS_ROOT
)
@DomainObject(
        nature = Nature.VIEW_MODEL
)
public class TarjetasViewModel implements Comparable<TarjetasViewModel>{
	
	public String iconName(){return "TarjetasViewModel";}
	
	public TarjetasViewModel(){}
	
	public TarjetasViewModel(Jugador jugador, Division division) { 
		this.jugador = jugador;
		this.division = division;
		}
	
	@PropertyLayout(named="Division")
	private Division division;
	@Title
	public Division getDivision() {return division;}
	public void setDivision(final Division division) {this.division = division;}
	
	@PropertyLayout(named="Jugador")
	private Jugador jugador;
	public Jugador getJugador() {return jugador;}
	public void setJugador(Jugador jugador) {this.jugador = jugador;}
	
	@PropertyLayout(named="Tarjetas")
	private Tarjeta tarjeta;
	public Tarjeta getTarjeta() {return tarjeta;}
	public void setTarjeta(Tarjeta tarjeta) {this.tarjeta = tarjeta;}

	@Override
	public int compareTo(TarjetasViewModel o) {
		// TODO Auto-generated method stub
		return 0;
	}
}