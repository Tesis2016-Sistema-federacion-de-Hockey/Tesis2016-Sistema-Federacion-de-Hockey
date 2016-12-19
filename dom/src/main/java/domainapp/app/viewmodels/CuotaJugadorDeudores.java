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

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import domainapp.dom.club.Club;
import domainapp.dom.cuotajugador.CuotaJugador;
import domainapp.dom.jugador.Jugador;
import domainapp.dom.jugador.JugadorServicio;

@Mixin
public class CuotaJugadorDeudores {
	
	private CuotaJugador cuotaJugador;

	public CuotaJugadorDeudores(CuotaJugador cuotaJugador) {
		this.cuotaJugador = cuotaJugador;
	}
	
	@Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
	@ActionLayout(
            cssClassFa = "fa-external-link",
            named = "Lista de Jugadores que adeudan Cuota"            
    )
	public List<CuotaJugadorViewModel> deudores(final Club club){
		
		final List<Jugador> jugadores=jugadorServicio.listarJugadoresActivosSegunClub(club);
		
		List<CuotaJugadorViewModel>listaDeudores=Lists.newArrayList(Iterables.transform(jugadores, byPosiciones()));
		
		return listaDeudores;
	}
	
	private Function<Jugador, CuotaJugadorViewModel> byPosiciones() {
		
		return new Function<Jugador, CuotaJugadorViewModel>(){
			
			@Override
	        public CuotaJugadorViewModel apply(final Jugador jugador) {
				
				return new CuotaJugadorViewModel(jugador, cuotaJugador);
	        }
	     };
	 }	
	
    @javax.inject.Inject
    JugadorServicio jugadorServicio;
}