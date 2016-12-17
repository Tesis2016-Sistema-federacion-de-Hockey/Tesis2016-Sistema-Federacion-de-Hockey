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

import domainapp.app.viewmodels.GoleadoresViewModel;
import domainapp.dom.division.Division;
import domainapp.dom.jugador.Jugador;

@Mixin
public class DivisionGoleadores {
	private final Division division;

	public DivisionGoleadores(Division division) { 
	this.division = division;
	}

	@Action(
			semantics = SemanticsOf.SAFE,
			restrictTo = RestrictTo.PROTOTYPING
			)
	@ActionLayout(
			cssClassFa = "fa-external-link",
			named = "Tabla de Goleadores"
			)
	public List<GoleadoresViewModel> goleadores() {
		
		final List<Jugador> jugadores = division.tablaDeGoleadores();
		
		List<GoleadoresViewModel> listaGoleadores=
				Lists.newArrayList(Iterables.transform(jugadores, byPosiciones()));
		
		Collections.sort(listaGoleadores);
		
		return listaGoleadores;        
	}
	private Function<Jugador, GoleadoresViewModel> byPosiciones() {
		
		return new Function<Jugador, GoleadoresViewModel>(){
			
			@Override
	        public GoleadoresViewModel apply(final Jugador jugador) {
				
				return new GoleadoresViewModel(jugador,division);
	        }
	     };
	 } 
}