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
