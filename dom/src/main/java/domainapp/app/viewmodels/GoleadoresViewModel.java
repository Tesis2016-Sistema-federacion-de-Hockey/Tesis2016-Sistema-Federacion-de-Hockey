/*
#	This file is part of SIFHON.
#
#	Copyright ( C ) 2016 , SIFHON
#
#   SIFHON is free software: you can redistribute it and/or modify
#   it under the terms of the GNU General Public License as published by
#   the Free Software Foundation, either version 3 of the License, or
#   (at your option) any later version.
#
#   SIFHON is distributed in the hope that it will be useful,
#   but WITHOUT ANY WARRANTY; without even the implied warranty of
#   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#   GNU General Public License for more details.
#
#   You should have received a copy of the GNU General Public License
#   along with SIFHON.  If not, see <http://www.gnu.org/licenses/>.
*/

package domainapp.app.viewmodels;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Title;

import domainapp.dom.division.Division;
import domainapp.dom.jugador.Jugador;

@DomainObjectLayout(
        named="Tabla de goleadores",
        bookmarking = BookmarkPolicy.AS_ROOT
)
@DomainObject(
        nature = Nature.VIEW_MODEL
)
public class GoleadoresViewModel  implements Comparable<GoleadoresViewModel>{
	
	public String iconName(){return "GoleadoresViewModel";}

	public GoleadoresViewModel() {}

	public GoleadoresViewModel(Jugador jugador, Division division) { 
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
	
	@PropertyLayout(named="Goles")
	public long getGoles() {return jugador.golesEquipo(division);}

	@Override
	public int compareTo(GoleadoresViewModel o) {
	// TODO Auto-generated method stub 
	if (this.getGoles() < o.getGoles()) return 1;
	   if (this.getGoles() > o.getGoles()) return -1;
	return 0;
	}
}