package domainapp.dom.torneo;

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
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.estado.Estado;
import domainapp.dom.temporada.Temporada;


@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = Torneo.class
)
@DomainServiceLayout(
		named="Planificacion", menuBar=DomainServiceLayout.MenuBar.PRIMARY, menuOrder="4"
)
public class TorneoServicio {
	public TranslatableString title() {
        return TranslatableString.tr("Torneos");
    }
	
	@Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT
    )
	@MemberOrder(name="Planificacion",     sequence = "2")
	public List<Torneo> listarTodosLosTorneos() {
        return repositoryService.allInstances(Torneo.class);
    }
	
    public static class CreateDomainEvent extends ActionDomainEvent<TorneoServicio> {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("deprecation")
		public CreateDomainEvent(final TorneoServicio source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }
	
    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @ActionLayout(
    		cssClassFa="fa fa-plus-square"
    )
    @MemberOrder(name="Planificacion", sequence = "3")
    public Torneo crearTorneo(
    		final @ParameterLayout(named="Nombre") String nombre,
    		final @ParameterLayout(named="Estado") Estado estado,
    		final @ParameterLayout(named="Temporada") Temporada temporada,
    		final @ParameterLayout(named="Observaciones") @Parameter(optionality=Optionality.OPTIONAL) String observaciones
    		){
        final Torneo obj = repositoryService.instantiate(Torneo.class);
        obj.setNombre(nombre);
        obj.setEstado(estado);
        obj.setTemporada(temporada);
        obj.setObservaciones(observaciones);
        obj.setVisible(true);
        repositoryService.persist(obj);
        return obj;
    }

    //POR DEFECTO, AL CREAR EL TORNEO SE SETEA A ACTIVO
    public Estado default1CrearTorneo(){    	
    	return Estado.ACTIVO;
    }    
    
    @ActionLayout(hidden = Where.EVERYWHERE)
	public List<Torneo> buscarTorneo(String torneo) {
		return repositoryService.allMatches(QueryDefault
				.create(Torneo.class, "traerTodos"));
	}

    @javax.inject.Inject
    RepositoryService repositoryService;
}