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

package domainapp.dom.gol;

import java.util.List;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.google.common.collect.Lists;

import domainapp.dom.jugador.Jugador;

import domainapp.dom.partido.Partido;


@SuppressWarnings("deprecation")
@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY,
        repositoryFor = Gol.class
)
@DomainServiceLayout(
        menuOrder = "1",
        named="Goles"
)

public class GolServicio {

	public TranslatableString title() {return TranslatableString.tr("Goles");}
	
	public static class CreateDomainEvent extends ActionDomainEvent<GolServicio> {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CreateDomainEvent(final GolServicio source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @ActionLayout(
    		cssClassFa="fa fa-futbol-o fa-lg fa-fw",
    		named="Asignar GOL"
    )
    public Partido crearGol(
            final @ParameterLayout(named="Jugador") Jugador jugador,
            final @ParameterLayout(named="Partido") Partido partido,
            final @ParameterLayout(named="minuto") int minuto
            ){
        final Gol obj = repositoryService.instantiate(Gol.class);
        obj.setJugador(jugador);
        obj.setPartido(partido);
        obj.setMinuto(minuto);
        if(partido.getEquipoLocal().getListaBuenaFe().contains(jugador)){
        	obj.setEquipo(partido.getEquipoLocal());
        	obj.setEquipoContrario(partido.getEquipoVisitante());
        }else{
        	obj.setEquipo(partido.getEquipoVisitante());
        	obj.setEquipoContrario(partido.getEquipoLocal());
        }        
        repositoryService.persist(obj);
        
        return partido;
    }
    
	public List<Jugador> choices0CrearGol(
			final @ParameterLayout(named="Jugador") Jugador jugador,
            final @ParameterLayout(named="Partido") Partido partid,
            final @ParameterLayout(named="minuto") int minuto
			){
		
		return Lists.newArrayList(partid.getListaPartido());
	}

    public String validateCrearGol(
    		final @ParameterLayout(named="Jugador") Jugador jugador,
            final @ParameterLayout(named="Partido") Partido partid,
            final @ParameterLayout(named="minuto") int minuto
			){    	
    	if (minuto<0||minuto>70) return "Ingrese un valor desde 0 hasta 70";
    	return "";
    }
    
	@javax.inject.Inject
    RepositoryService repositoryService;
}