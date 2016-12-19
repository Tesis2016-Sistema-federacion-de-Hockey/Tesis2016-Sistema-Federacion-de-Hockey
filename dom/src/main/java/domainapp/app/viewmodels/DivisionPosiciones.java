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

import java.util.Collections;
import java.util.List;
import java.util.SortedSet;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import domainapp.dom.division.Division;
import domainapp.dom.equipo.Equipo;

@Mixin
public class DivisionPosiciones {
	private final Division division;

	public DivisionPosiciones(Division division) { 
	this.division = division;
	}
	
	@Action(
			semantics = SemanticsOf.SAFE,
			restrictTo = RestrictTo.PROTOTYPING
			)
	@ActionLayout(
			cssClassFa = "fa-external-link",
			named = "Tabla de Posiciones"
			)
	public List<PosicionesViewModel> posiciones() {
		final SortedSet<Equipo> equipos = division.getListaEquipos();
		
		List<PosicionesViewModel> listaEquipos=
				Lists.newArrayList(Iterables.transform(equipos, byPosiciones()));
		
		Collections.sort(listaEquipos);
		
		return listaEquipos;
	}
	
	private Function<Equipo, PosicionesViewModel> byPosiciones() {
		
		return new Function<Equipo, PosicionesViewModel>(){
			
			@Override
	        public PosicionesViewModel apply(final Equipo equipo) {
				
				return new PosicionesViewModel(equipo);
	        }
	     };
	 } 
}
