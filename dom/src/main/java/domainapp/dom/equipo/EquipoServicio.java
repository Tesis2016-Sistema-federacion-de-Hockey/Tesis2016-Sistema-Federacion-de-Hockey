package domainapp.dom.equipo;

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
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;
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
	
	@Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "5.1")
    public List<Equipo> listarTodosLosEquipos() {
        return repositoryService.allInstances(Equipo.class);
    }
	
	@Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "5.2")
    public List<Equipo> listarTodosLosEquiposDelClub(Club club){
		
    	return repositoryService.allMatches(new QueryDefault<Equipo>(Equipo.class, "listarTodosLosEquiposDelClub", "club", club));
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
    @MemberOrder(sequence = "5.3")
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
        obj.setVisible(true);
        repositoryService.persist(obj);
        return obj;
    }
	
    //POR DEFECTO, SE SETEA EL VALOR DEL ESTADO A ACTIVO
    public Estado default1CrearEquipo(){    	
    	return Estado.ACTIVO;
    }
    
    @ActionLayout(hidden=Where.EVERYWHERE)
    public String buscarEquipo(final Club club, Equipo equipo){
    	return "";
    }    
    public List<Club> choices0BuscarEquipo(final Club club){
    	return repositoryService.allMatches(QueryDefault.create(Club.class, "traerClub", "club",club));
    }
    public Club default0BuscarEquipo(final Club cl){
    	return repositoryService.allInstances(Club.class, 0).get(0);
    }
    public List<Club>choices1BuscarEquipo(final Club club, Equipo equipo){
    	return repositoryService.allMatches(QueryDefault.create(Club.class, "traerEquipo", "club",club, "equipo",equipo));
    }
    @ActionLayout(hidden=Where.EVERYWHERE)
    public List<Club> buscarClub(String cl){
    	return repositoryService.allMatches(QueryDefault.create(Club.class, "traerClub", "nombre",cl));
    }
    
	
    @javax.inject.Inject
    RepositoryService repositoryService;
    
    @javax.inject.Inject
    ClubServicio clubServicio;
}