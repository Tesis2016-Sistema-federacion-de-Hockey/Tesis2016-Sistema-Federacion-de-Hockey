package domainapp.dom.localidad;

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
import org.joda.time.LocalDate;

import domainapp.dom.estado.Estado;
import domainapp.dom.jugador.Jugador;
import domainapp.dom.jugador.Jugadores;
import domainapp.dom.jugador.Jugadores.CreateDomainEvent;
import domainapp.dom.sector.Sector;
import domainapp.dom.tipodocumento.TipoDocumento;



@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = Localidad.class
)
@DomainServiceLayout(
        menuOrder = "10"
)
public class Localidades {
	
	//region > title
    public TranslatableString title() {
        return TranslatableString.tr("Localidades");
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
    public List<Localidad> listarTodasLasLocalidades() {
        return repositoryService.allInstances(Localidad.class);
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
    public List<Localidad> buscarPorNombre(
            @ParameterLayout(named="Nombre")
            final String nombre
    ) {
    	return repositoryService.allMatches(
                new QueryDefault<Localidad>(
                        Localidad.class,
                        "buscarPorNombre",
                        "nombre", nombre));
    }
    
  //endregion

    //region > create (action)
    public static class CreateDomainEvent extends ActionDomainEvent<Localidades> {
        public CreateDomainEvent(final Localidades source, final Identifier identifier, final Object... arguments) {
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
    public Localidad crear(
            final @ParameterLayout(named="Nombre") String nombre,
            final @ParameterLayout(named="Codigo Postal") String codigoPostal,
            final @ParameterLayout(named="Provincia") String provincia
    		){
        final Localidad obj = repositoryService.instantiate(Localidad.class);
        obj.setNombre(nombre);
        obj.setCodigoPostal(codigoPostal);
        obj.setProvincia(provincia);
        repositoryService.persist(obj);
        return obj;
    }
    
    
    
    
	
  //endregion

    //region > injected services

    @javax.inject.Inject
    RepositoryService repositoryService;

    //endregion
}
