package domainapp.dom.partido;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.equipo.Equipo;
import domainapp.dom.jugador.Jugador;

@SuppressWarnings("deprecation")
@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = Partido.class
)
@DomainServiceLayout(
        menuOrder = "1",
        named="Partidos"
)

public class PartidoServicio {

	public TranslatableString title() {return TranslatableString.tr("Partidos");}

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="PartidosPorEquipo"
    )
    @MemberOrder(sequence = "1")
    public List<Partido> listarPartidosPorEquipo(Equipo equipo) {
    	return repositoryService.allMatches(new QueryDefault<Partido>(Partido.class, "listarPartidosPorEquipo", "equipo", equipo));
    	
    }
    
    @javax.inject.Inject
    RepositoryService repositoryService;
}
