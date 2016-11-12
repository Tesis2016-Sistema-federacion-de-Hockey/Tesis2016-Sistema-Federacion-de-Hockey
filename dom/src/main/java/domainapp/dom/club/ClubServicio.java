package domainapp.dom.club;

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

import domainapp.dom.domicilio.Domicilio;
import domainapp.dom.localidad.Localidad;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = Club.class
)
@DomainServiceLayout(
        menuOrder = "2",
        named="Clubes"
)
public class ClubServicio {
    public TranslatableString title() {
        return TranslatableString.tr("Clubes");
    }
    
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<Club> listarTodosLosClubes() {
        return repositoryService.allInstances(Club.class);
    }
    
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-search",
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "2")
    public List<Club> buscarPorNombre(
            @ParameterLayout(named="nombre")
            final String nombre
    ) {
    	return repositoryService.allMatches(
                new QueryDefault<Club>(
                        Club.class,
                        "buscarPorNombre",
                        "nombre", nombre));
    }
    public static class CreateDomainEvent extends ActionDomainEvent<ClubServicio> {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("deprecation")
		public CreateDomainEvent(final ClubServicio source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @ActionLayout(
    		cssClassFa="fa fa-plus-square"
    )
    @MemberOrder(sequence = "3")
    public Club crearClub(
    		final @ParameterLayout(named="Nombre") String nombre,
            final @ParameterLayout(named="Nombre institucional") @Parameter(optionality=Optionality.OPTIONAL) String nombreInstitucional,
            final @ParameterLayout(named="Anio de afiliacion") @Parameter(optionality=Optionality.OPTIONAL) String anioAfiliacion,
            final @ParameterLayout(named="Id Interno") String idInterno,
            final @ParameterLayout(named="Personeria Juridica") @Parameter(optionality=Optionality.OPTIONAL) String personeriaJuridica,
            final @ParameterLayout(named="Email") @Parameter(optionality=Optionality.OPTIONAL) String email,
            final @ParameterLayout(named="Telefono") @Parameter(optionality=Optionality.OPTIONAL) String telefono,
            final @ParameterLayout(named="Calle") @Parameter(optionality=Optionality.OPTIONAL) String calle,
            final @ParameterLayout(named="Numero") @Parameter(optionality=Optionality.OPTIONAL) String numero,
            final @ParameterLayout(named="Piso") @Parameter(optionality=Optionality.OPTIONAL) String piso,
            final @ParameterLayout(named="Departamento") @Parameter(optionality=Optionality.OPTIONAL) String departamento,
            final @ParameterLayout(named="Localidad") @Parameter(optionality=Optionality.OPTIONAL) Localidad localidad
    		){
        final Club obj = repositoryService.instantiate(Club.class);
        final Domicilio domicilio=new Domicilio();
        domicilio.setCalle(calle);
        domicilio.setNumero(numero);
        domicilio.setPiso(piso);
        domicilio.setDepartamento(departamento);
        domicilio.setLocalidad(localidad);
        obj.setNombre(nombre);
        obj.setNombreInstitucional(nombreInstitucional);
        obj.setAnioAfiliacion(anioAfiliacion);  
        obj.setIdInterno(idInterno);
        obj.setPersoneriaJuridica(personeriaJuridica);
        obj.setEmail(email);
        obj.setTelefono(telefono);
        obj.setDomicilio(domicilio);
        repositoryService.persist(obj);
        return obj;
    }
    
    @ActionLayout(hidden = Where.EVERYWHERE)
	public List<Club> buscarClub(String club) {
		return repositoryService.allMatches(QueryDefault
				.create(Club.class, "traerTodos"));
	}

    @javax.inject.Inject
    RepositoryService repositoryService;
}