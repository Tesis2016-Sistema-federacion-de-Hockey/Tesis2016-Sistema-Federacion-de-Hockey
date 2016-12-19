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
import domainapp.dom.club.ClubServicio;
import domainapp.dom.cuotaclub.CuotaClub;

@Mixin
public class CuotaClubDeudores {
	
	private CuotaClub cuotaClub;

	public CuotaClubDeudores(CuotaClub cuotaClub) {
		this.cuotaClub = cuotaClub;
	}
	
	@Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
	@ActionLayout(
            cssClassFa = "fa-external-link",
            named = "Lista de Clubes que adeudan Cuota"            
    )
	public List<CuotaClubViewModel> deudores(){
		
		final List<Club> clubes=clubServicio.listarTodosLosClubes();
		
		List<CuotaClubViewModel>listaDeudores=Lists.newArrayList(Iterables.transform(clubes, byPosiciones()));
		
		return listaDeudores;
	}
	
	private Function<Club, CuotaClubViewModel> byPosiciones() {
		
		return new Function<Club, CuotaClubViewModel>(){
			
			@Override
	        public CuotaClubViewModel apply(final Club club) {
				
				return new CuotaClubViewModel(club, cuotaClub);
	        }
	     };
	 }	
	
    @javax.inject.Inject
    ClubServicio clubServicio;
}