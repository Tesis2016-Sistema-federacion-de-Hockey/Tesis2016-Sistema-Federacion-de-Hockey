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

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Title;

import domainapp.dom.club.Club;
import domainapp.dom.equipo.Equipo;

@DomainObjectLayout(
        named="Tabla de posiciones",
        bookmarking = BookmarkPolicy.AS_ROOT
)
@DomainObject(
        nature = Nature.VIEW_MODEL
)
public class PosicionesViewModel implements Comparable<PosicionesViewModel>{

	public String iconName(){return "PosicionesViewModel";}
	
	public PosicionesViewModel() {}
	
	public PosicionesViewModel(Equipo equipo) {				
		this.equipo = equipo;
	}

	@MemberOrder(sequence = "1")
	@PropertyLayout(named="Club")
	@Title
	public Club getClub() {
		return equipo.getClub();
	}	
	
	@MemberOrder(sequence = "2")
	@PropertyLayout(named="Equipo")
	private Equipo equipo;	
	public Equipo getEquipo() {
		return equipo;
	}
	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}
	
	@MemberOrder(sequence = "3")
	@PropertyLayout(named="Puntos")
	public long getPuntos(){
		return this.equipo.getPuntos();
	}

	@MemberOrder(sequence = "4")
	@PropertyLayout(named="PJ")
	public long getPartidosJugados(){
		return this.equipo.getPartidosJugados();
	}
	
	@MemberOrder(sequence = "5")
	@PropertyLayout(named="PG")
	public long getPartidosGanados(){
		return this.equipo.getPartidosGanados();
	}
	
	@MemberOrder(sequence = "6")
	@PropertyLayout(named="PE")
	public long getPartidosEmpatados(){
		return this.equipo.getPartidosEmpatados();
	}
	
	@MemberOrder(sequence = "7")
	@PropertyLayout(named="PP")
	public long getPartidosPerdidos(){
		return this.equipo.getPartidosPerdidos();
	}
	
	@MemberOrder(sequence = "8")
	@PropertyLayout(named="GF")
	public long getGolesAFabor(){
		return this.equipo.getGolesAFavor();
	}
	
	@MemberOrder(sequence = "9")
	@PropertyLayout(named="GC")
	public long getGolesEnContra(){
		return this.equipo.getGolesAContra();
	}
	
	@Override
	public int compareTo(PosicionesViewModel o) {
		if (this.getPuntos() < o.getPuntos()) return 1;
		if (this.getPuntos() > o.getPuntos()) return -1;
		return 0;
	}
}