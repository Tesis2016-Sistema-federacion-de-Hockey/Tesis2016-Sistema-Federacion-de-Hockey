package domainapp.dom.division;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.estado.Estado;
import domainapp.dom.temporada.Temporada;
import domainapp.dom.torneo.Torneo;
import domainapp.dom.torneo.TorneoServicio;
import domainapp.dom.torneo.TorneoServicio.CreateDomainEvent;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = Division.class
)
@DomainServiceLayout(
        menuOrder = "5",
        named="Divisiones"
)
public class DivisionServicio {
	public TranslatableString title() {
        return TranslatableString.tr("Divisiones");
    }
	
	public static class CreateDomainEvent extends ActionDomainEvent<DivisionServicio> {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("deprecation")
		public CreateDomainEvent(final DivisionServicio source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }
	
    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @ActionLayout(
    		cssClassFa="fa fa-plus-square"
    )
    @MemberOrder(sequence = "5.2")
    public Division crearDivision(
		final @ParameterLayout(named="Nombre") String nombre,
		final @ParameterLayout(named="Estado") Estado estado,
		final @ParameterLayout(named="Temporada") Temporada temporada,
		final @ParameterLayout(named="Torneo") Torneo torneo,
		final @ParameterLayout(named="Modalidad") String modalidad,
		final @ParameterLayout(named="Puntos GANAR") int puntosGanar,
		final @ParameterLayout(named="Puntos EMPATAR") int puntosEmpatar,
		final @ParameterLayout(named="Puntos PERDER") int puntosPerder
		){
    final Division obj = repositoryService.instantiate(Division.class);
        obj.setNombre(nombre);
        obj.setEstado(estado);
        obj.setTemporada(temporada);
        obj.setTorneo(torneo);
        obj.setModalidad(modalidad);
        obj.setPuntosGanar(puntosGanar);
        obj.setPuntosEmpatar(puntosEmpatar);
        obj.setPuntosPerder(puntosPerder);
        repositoryService.persist(obj);
        return obj;
    }
	
	
    @javax.inject.Inject
    RepositoryService repositoryService;
}