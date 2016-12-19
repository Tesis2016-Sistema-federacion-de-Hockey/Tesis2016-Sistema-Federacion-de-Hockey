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