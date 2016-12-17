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
	public List<CuotaJugadorViewModel> deudores(){
		
		final List<Jugador> jugadores=jugadorServicio.listarJugadoresActivos();
		
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
