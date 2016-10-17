package domainapp.dom.cuotaclub;

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

import domainapp.dom.cuotajugador.CuotaJugador;
import domainapp.dom.cuotajugador.CuotaJugadorServicio;
import domainapp.dom.cuotajugador.CuotaJugadorServicio.CreateDomainEvent;

@SuppressWarnings("deprecation")
@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = CuotaClub.class
)
@DomainServiceLayout(
        menuOrder = "3",
        named="Cuotas"
)

public class CuotaClubServicio {
	public TranslatableString title() {return TranslatableString.tr("CuotasClub");}
	
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
            domainEvent = CreateDomainEvent.class
    )
    @ActionLayout(
    		cssClassFa="fa fa-plus-square"
    )
    @MemberOrder(name="Cuotas", sequence = "3.1")
    public CuotaClub crearCuotaClub(
    		final @ParameterLayout(named="Valor") Double valor,
            final @ParameterLayout(named="Vencimiento") LocalDate vencimiento,
            final @ParameterLayout(named="Detalle") @Parameter(optionality=Optionality.OPTIONAL) String detalle            
    		){
        final CuotaClub obj = repositoryService.instantiate(CuotaClub.class);
        obj.setValor(valor);
        obj.setVencimiento(vencimiento);
        obj.setDetalle(detalle);
        
        repositoryService.persist(obj);
        return obj;
    }
	
	@javax.inject.Inject
    RepositoryService repositoryService;
}
