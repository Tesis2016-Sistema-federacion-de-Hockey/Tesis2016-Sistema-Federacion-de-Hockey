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

package domainapp.dom.tarjeta;

import java.util.List;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import domainapp.dom.division.Division;
import domainapp.dom.jugador.Jugador;
import domainapp.dom.partido.Partido;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY,
        repositoryFor = Tarjeta.class
)
@DomainServiceLayout(
        menuOrder = "7",
        named="Tarjetas"
)
public class TarjetaServicio {
	
	public TranslatableString title() {return TranslatableString.tr("Tarjetas");}
	
	@Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Lista de Tarjetas",
            describedAs="Se muestran todas las tarjetas de la Division en forma cronologica"
    )
    @MemberOrder(sequence = "2")
    public List<Tarjeta> listarTodasLasTarjetasPorDivision(
    		@ParameterLayout(named="Ingrese Categoria") final Division division) {
		return repositoryService.allMatches(Tarjeta.class, new Predicate<Tarjeta>() {

			@Override
			public boolean apply(Tarjeta input) {
				return input.getPartido().getFecha().getDivision()==division?true:false;
			}
		});
	}
	
	@Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Listar todas las Tarjetas"
    )
    @MemberOrder(sequence = "1")
    public List<Tarjeta> listarTodasLasTarjetas() {
        return repositoryService.allInstances(Tarjeta.class);
    }
	
    public static class CreateDomainEvent extends ActionDomainEvent<TarjetaServicio> {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("deprecation")
		public CreateDomainEvent(final TarjetaServicio source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }
	
    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @ActionLayout(
    		cssClassFa="fa fa-plus",
    		named="Asignar TARJETA"
    		)
    public Partido crearTarjeta(
    		final @ParameterLayout(named="Jugador") Jugador jugador,
    		final @ParameterLayout(named="Tipo") TipoTarjeta tipoTarjeta,
    		final @ParameterLayout(named="Minuto") int minutoTarjeta,
    		final @ParameterLayout(named="Partido") Partido partido
    		){
        final Tarjeta obj = repositoryService.instantiate(Tarjeta.class);
        obj.setMinutoTarjeta(minutoTarjeta);
        obj.setTipoTarjeta(tipoTarjeta);
        obj.setPartido(partido);
        obj.setJugador(jugador);
        repositoryService.persist(obj);
        partido.getTarjetas().add(obj);
        jugador.getTarjetas().add(obj);
        return partido;
    }
    
    public List<Jugador> choices0CrearTarjeta(
    		final @ParameterLayout(named="Jugador") Jugador jugador,
    		final @ParameterLayout(named="Tipo") TipoTarjeta tipoTarjeta,
    		final @ParameterLayout(named="Minuto") int minutoTarjeta,
    		final @ParameterLayout(named="Partido") Partido partido    		
			){
		return Lists.newArrayList(partido.getListaPartido());
	}
    
    public String validateCrearTarjeta(
    		final @ParameterLayout(named="Jugador") Jugador jugador,
    		final @ParameterLayout(named="Tipo") TipoTarjeta tipoTarjeta,
    		final @ParameterLayout(named="Minuto") int minutoTarjeta,
    		final @ParameterLayout(named="Partido") Partido partido
    		){    	
    	if (minutoTarjeta<0||minutoTarjeta>70) return "Ingrese un valor desde 0 hasta 70";
    	return "";
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
}