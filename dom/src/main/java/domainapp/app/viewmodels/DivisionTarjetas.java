package domainapp.app.viewmodels;

import java.util.Collections;
import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import domainapp.dom.division.Division;
import domainapp.dom.jugador.Jugador;

@Mixin
public class DivisionTarjetas {
	
	private final Division division;

	public DivisionTarjetas(Division division) {
		
		this.division = division;
	}
	
	@Action(
			semantics = SemanticsOf.SAFE,
			restrictTo = RestrictTo.PROTOTYPING
			)
	@ActionLayout(
			cssClassFa = "fa-external-link",
			named = "Tabla de Tarjetas"
			)
	public List<TarjetasViewModel> tarjetas() {
		
		final List<Jugador> jugadores = division.tablaDeTarjetas();
		
		List<TarjetasViewModel> listaTarjetas=
				Lists.newArrayList(Iterables.transform(jugadores, byPosiciones()));
		
		Collections.sort(listaTarjetas);
		
		return listaTarjetas;        
	}
	private Function<Jugador, TarjetasViewModel> byPosiciones() {
		
		return new Function<Jugador, TarjetasViewModel>(){
			
			@Override
	        public TarjetasViewModel apply(final Jugador jugador) {
				
				return new TarjetasViewModel(jugador,division);
	        }
	     };
	 } 
}