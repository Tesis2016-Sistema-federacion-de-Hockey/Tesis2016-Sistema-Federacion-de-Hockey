package domainapp.dom.club;

import org.joda.time.LocalDate;

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
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.domicilio.Domicilio;
import domainapp.dom.jugador.Jugador;



@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = Club.class
)
@DomainServiceLayout(
        menuOrder = "10"
)
public class Clubes {
	//region > title
    public TranslatableString title() {
        return TranslatableString.tr("Clubes");
    }
    //endregion
    
  //region > listAll (action)
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
    //endregion
    
  //region > findByName (action)
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
    //endregion
    
    //region > create (action)
    public static class CreateDomainEvent extends ActionDomainEvent<Clubes> {
        public CreateDomainEvent(final Clubes source, final Identifier identifier, final Object... arguments) {
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
    public Club crear(
            final @ParameterLayout(named="Nombre") String nombre,
            final @ParameterLayout(named="Nombre institucional") String nombreInstitucional,
            final @ParameterLayout(named="Anio de afiliacion") int anioAfiliacion,
            final @ParameterLayout(named="Id Interno") String idInterno,
            final @ParameterLayout(named="Personeria Juridica") String personeriaJuridica,
            final @ParameterLayout(named="Email") String email,
            final @ParameterLayout(named="Telefono") String telefono,
            final @ParameterLayout(named="Calle") String calle,
            final @ParameterLayout(named="Numero") int numero,
            final @ParameterLayout(named="Piso") int piso,
            final @ParameterLayout(named="Departamento") String departamento            
    		){
        final Club obj = repositoryService.instantiate(Club.class);
        final Domicilio domicilio=new Domicilio();
        domicilio.setCalle(calle);
        domicilio.setNumero(numero);
        domicilio.setPiso(piso);
        domicilio.setDepartamento(departamento);
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

    //endregion

    //region > injected services

    @javax.inject.Inject
    RepositoryService repositoryService;

    //endregion
}
