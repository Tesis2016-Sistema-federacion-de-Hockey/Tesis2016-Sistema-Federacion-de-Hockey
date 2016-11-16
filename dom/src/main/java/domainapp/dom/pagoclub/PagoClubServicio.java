package domainapp.dom.pagoclub;

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
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.joda.time.LocalDate;

import domainapp.dom.cuotaclub.CuotaClub;

@SuppressWarnings("deprecation")
@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = CuotaClub.class
)
@DomainServiceLayout(
        menuOrder = "3",
        named="Cuotas"
)
public class PagoClubServicio {
	public TranslatableString title() {return TranslatableString.tr("Pagos del Club");}
	
	public static class CreateDomainEvent extends ActionDomainEvent<PagoClubServicio> {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CreateDomainEvent(final PagoClubServicio source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }
	
	@Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Listar Pagos del Club"
    )
	@MemberOrder(name="Pagos", sequence = "3.1")
    public List<PagoClub> listarPagosClub() {
        return repositoryService.allInstances(PagoClub.class);
    }
	
	
	
	
	
	
	
	@Action(
            domainEvent = CreateDomainEvent.class
    )
    @ActionLayout(
    		cssClassFa="fa fa-plus-square"
    )
    @MemberOrder(name="Pagos", sequence = "3.2")
    public PagoClub crearPagoClub(
    		final @ParameterLayout(named="Valor") BigDecimal valor,
            final @ParameterLayout(named="Fecha de Pago") LocalDate fechaDePago,
            final @ParameterLayout(named="Recibo") String nroRecibo            
    		){
        final PagoClub obj = repositoryService.instantiate(PagoClub.class);
        obj.setFechaDePago(fechaDePago);
        obj.setNroRecibo(nroRecibo);
        obj.setValor(valor);
        repositoryService.persist(obj);
        return obj;
    }
	
	@javax.inject.Inject
    RepositoryService repositoryService;
}