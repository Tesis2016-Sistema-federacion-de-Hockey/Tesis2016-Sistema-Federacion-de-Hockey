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

package domainapp.dom.cuotaclub;

import java.math.BigDecimal;
import java.util.List;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.joda.time.LocalDate;

import domainapp.dom.temporada.Temporada;

@SuppressWarnings("deprecation")
@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = CuotaClub.class
)
@DomainServiceLayout(
        menuOrder = "3",
        named="Cuotas"
)
public class CuotaClubServicio {
	public TranslatableString title() {return TranslatableString.tr("Cuotas de Club");}
	
	public static class CreateDomainEvent extends ActionDomainEvent<CuotaClubServicio> {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CreateDomainEvent(final CuotaClubServicio source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }
	
	@Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Listar Cuotas de Club"
    )
	@MemberOrder(name="Cuotas", sequence = "3.1")
    public List<CuotaClub> listarCuotasClub() {
        return repositoryService.allInstances(CuotaClub.class);
    }
	
	@Action(
            domainEvent = CreateDomainEvent.class
    )
    @ActionLayout(
    		cssClassFa="fa fa-plus-square",
    		named="Crear Cuota de Club"
    )
    @MemberOrder(name="Cuotas", sequence = "3.2")
    public CuotaClub crearCuotaClub(
    		final @ParameterLayout(named="Temporada") Temporada temporada,
    		final @ParameterLayout(named="Valor") BigDecimal valor,
            final @ParameterLayout(named="Vencimiento") LocalDate vencimiento,
            final @ParameterLayout(named="Detalle") @Parameter(optionality=Optionality.OPTIONAL) String detalle
    		){
        final CuotaClub obj = repositoryService.instantiate(CuotaClub.class);
        obj.setTemporada(temporada);
        obj.setValor(valor);
        obj.setVencimiento(vencimiento);
        obj.setDetalle(detalle);
        
        repositoryService.persist(obj);
        return obj;
    }
	
	@javax.inject.Inject
    RepositoryService repositoryService;
}