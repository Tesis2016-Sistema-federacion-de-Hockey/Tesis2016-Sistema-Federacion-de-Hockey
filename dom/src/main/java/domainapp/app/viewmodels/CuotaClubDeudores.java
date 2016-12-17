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