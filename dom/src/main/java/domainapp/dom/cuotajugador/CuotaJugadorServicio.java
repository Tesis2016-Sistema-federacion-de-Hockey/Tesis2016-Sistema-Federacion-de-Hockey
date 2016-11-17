package domainapp.dom.cuotajugador;

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
        nature = NatureOfService.VIEW,
        repositoryFor = CuotaJugador.class
)
@DomainServiceLayout(
        menuOrder = "3",
        named="Cuotas"
)
public class CuotaJugadorServicio {
	public TranslatableString title() {return TranslatableString.tr("Cuotas del Jugador");}
	
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
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Listar Cuotas de Jugador"
    )
    @MemberOrder(name="Cuotas", sequence = "3.1")
    public List<CuotaJugador> listarCuotasJugador() {
        return repositoryService.allInstances(CuotaJugador.class);
    }
    
    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @ActionLayout(
    		cssClassFa="fa fa-plus-square",
    		named="Crear Cuota de Jugador"
    )
    @MemberOrder(name="Cuotas", sequence = "3.2")
    public CuotaJugador crearCuotaJugador(
    		final @ParameterLayout(named="Temporada") Temporada temporada,
    		final @ParameterLayout(named="Nombre") String nombre,
    		final @ParameterLayout(named="Valor") BigDecimal valor,
            final @ParameterLayout(named="Vencimiento") LocalDate vencimiento,
            final @ParameterLayout(named="Detalle") @Parameter(optionality=Optionality.OPTIONAL) String detalle            
    		){
        final CuotaJugador obj = repositoryService.instantiate(CuotaJugador.class);
        obj.setTemporada(temporada);
        obj.setNombre(nombre);
        obj.setValor(valor);
        obj.setVencimiento(vencimiento);
        obj.setDetalle(detalle);
        
        repositoryService.persist(obj);
        return obj;
    }
    
    @javax.inject.Inject
    RepositoryService repositoryService;
}