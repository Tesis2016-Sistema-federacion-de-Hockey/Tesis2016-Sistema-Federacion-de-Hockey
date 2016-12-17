package domainapp.app.viewmodels;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.Title;

import domainapp.dom.division.Division;
import domainapp.dom.jugador.Jugador;

@DomainObjectLayout(
        named="Tabla de posiciones",
        bookmarking = BookmarkPolicy.AS_ROOT
)
@DomainObject(
        nature = Nature.VIEW_MODEL
)
public class GoleadoresViewModel  implements Comparable<GoleadoresViewModel>{

	public GoleadoresViewModel() {}

	public GoleadoresViewModel(Jugador jugador, Division division) { 
	this.jugador = jugador;
	this.division = division;
	}



	private Division division;
	@Title
	public Division getDivision() {
	return division;
	}
	public void setDivision(final Division division) {
	this.division = division;
	}
	private Jugador jugador;
	public Jugador getJugador() {
	return jugador;
	}
	public void setJugador(Jugador jugador) {
	this.jugador = jugador;
	}
	public long getGoles() {
	return jugador.golesEquipo(division);
	}

	@Override
	public int compareTo(GoleadoresViewModel o) {
	// TODO Auto-generated method stub 
	if (this.getGoles() < o.getGoles()) return 1;
	   if (this.getGoles() > o.getGoles()) return -1;
	return 0;
	}

	}

