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

import com.google.common.collect.Lists;

import domainapp.dom.gol.GolServicio.CreateDomainEvent;
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
    		cssClassFa="fa fa-plus-square"
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
    
    

    @javax.inject.Inject
    RepositoryService repositoryService;
}