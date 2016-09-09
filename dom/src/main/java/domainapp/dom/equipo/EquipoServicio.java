package domainapp.dom.equipo;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.club.Club;
import domainapp.dom.club.ClubServicio;
import domainapp.dom.division.Division;
import domainapp.dom.estado.Estado;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = Equipo.class
)
@DomainServiceLayout(
        menuOrder = "5",
        named="Equipos"
)
public class EquipoServicio {
	public TranslatableString title() {
        return TranslatableString.tr("Equipos");
    }
	
	
	
	public static class CreateDomainEvent extends ActionDomainEvent<EquipoServicio> {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("deprecation")
		public CreateDomainEvent(final EquipoServicio source, final Identifier identifier, final Object... arguments) {
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
    public Equipo crearEquipo(
		final @ParameterLayout(named="Nombre") String nombre,
		final @ParameterLayout(named="Estado") Estado estado,
		final @ParameterLayout(named="Club") Club club,
		final @ParameterLayout(named="Division") Division division		
		){
    final Equipo obj = repositoryService.instantiate(Equipo.class);
        obj.setNombre(nombre);
        obj.setEstado(estado);
        obj.setClub(club);
        obj.setDivision(division);
        repositoryService.persist(obj);
        return obj;
    }
	
    //POR DEFECTO, SE SETEA EL VALOR DE PUNTOS
    public Estado default1CrearEquipo(){    	
    	return Estado.ACTIVO;
    }
    
	
    @javax.inject.Inject
    RepositoryService repositoryService;
    
    @javax.inject.Inject
    ClubServicio clubServicio;
}