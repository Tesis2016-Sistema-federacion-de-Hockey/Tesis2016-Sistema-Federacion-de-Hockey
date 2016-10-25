package domainapp.dom.temporada;

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

import com.google.common.base.Predicate;

import domainapp.dom.estado.Estado;
import domainapp.dom.jugador.Jugador;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = Temporada.class
)
@DomainServiceLayout(
        named="Planificacion", menuBar=DomainServiceLayout.MenuBar.PRIMARY, menuOrder="4"
)
public class TemporadaServicio {
	public TranslatableString title() {
        return TranslatableString.tr("Temporadas");
    }
	
	@MemberOrder(name="Planificacion", sequence = "4.1")
	public List<Temporada> listarTemporadasActivas() {
		return repositoryService.allMatches(Temporada.class, new Predicate<Temporada>() {

			@Override
			public boolean apply(Temporada input) {
				// TODO Auto-generated method stub
				return input.getEstado() == Estado.ACTIVO ? true : false;
			}
		});
	}
	
	@Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Listar todas las Temporadas"
    )
    @MemberOrder(name="Planificacion", sequence = "4.2")
    public List<Temporada> listarTodasLasTemporadas() {
        return repositoryService.allInstances(Temporada.class);
    }
	
    public static class CreateDomainEvent extends ActionDomainEvent<TemporadaServicio> {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("deprecation")
		public CreateDomainEvent(final TemporadaServicio source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }
	
    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @ActionLayout(
    		cssClassFa="fa fa-plus-square"
    )
    @MemberOrder(name="Planificacion", sequence = "4.3")
    public Temporada crearTemporada(
    		final @ParameterLayout(named="Nombre") String nombre,
    		final @ParameterLayout(named="Estado") Estado estado,
    		final @ParameterLayout(named="Observaciones") @Parameter(optionality=Optionality.OPTIONAL) String observaciones
    		){
        final Temporada obj = repositoryService.instantiate(Temporada.class);
        obj.setNombre(nombre);
        obj.setEstado(estado);
        obj.setObservaciones(observaciones);
        repositoryService.persist(obj);
        return obj;
    }
    
    //POR DEFECTO, AL CREAR LA TEMPORADA ES ACTIVA
    public Estado default1CrearTemporada(){    	
    	return Estado.ACTIVO;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
}