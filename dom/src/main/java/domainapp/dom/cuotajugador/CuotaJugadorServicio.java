package domainapp.dom.cuotajugador;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Persistent;

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
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.joda.time.LocalDate;

import domainapp.dom.club.Club;
import domainapp.dom.club.ClubServicio;
import domainapp.dom.club.ClubServicio.CreateDomainEvent;
import domainapp.dom.domicilio.Domicilio;
import domainapp.dom.jugador.Jugador;

@SuppressWarnings("deprecation")
@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = CuotaJugador.class
)
@DomainServiceLayout(
        //menuOrder = "2",
        //named="CuotasFederativas"
)
public class CuotaJugadorServicio {
	public TranslatableString title() {return TranslatableString.tr("CuotasFederativas");}

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(name="Cuotas", sequence = "3.2")
    public List<CuotaJugador> listarCuotasJugadores() {
        return repositoryService.allInstances(CuotaJugador.class);
    }
    
    public static class CreateDomainEvent extends ActionDomainEvent<CuotaJugadorServicio> {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CreateDomainEvent(final CuotaJugadorServicio source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }
    
    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @ActionLayout(
    		cssClassFa="fa fa-plus-square"
    )
    @MemberOrder(name="Cuotas", sequence = "3.3")
    public CuotaJugador crearCuotaFederativa(
    		final @ParameterLayout(named="Valor") Double valor,
            final @ParameterLayout(named="Vencimiento") LocalDate vencimiento,
            final @ParameterLayout(named="Detalle") @Parameter(optionality=Optionality.OPTIONAL) String detalle            
    		){
        final CuotaJugador obj = repositoryService.instantiate(CuotaJugador.class);
        obj.setValor(valor);
        obj.setVencimiento(vencimiento);
        obj.setDetalle(detalle);
        
        repositoryService.persist(obj);
        return obj;
    }
    
  

    
    @javax.inject.Inject
    RepositoryService repositoryService;
}
