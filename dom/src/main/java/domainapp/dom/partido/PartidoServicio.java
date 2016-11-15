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
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.equipo.Equipo;

@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = Partido.class
)
@DomainServiceLayout(
		named="Planificacion", menuBar=DomainServiceLayout.MenuBar.PRIMARY, menuOrder="15"
)

public class PartidoServicio {

	public TranslatableString title() {return TranslatableString.tr("Partidos");}

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list fa-lg",
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Partidos Disputados por Equipo",
            hidden=Where.PARENTED_TABLES
    )
    @MemberOrder(sequence = "1")
    public List<Partido> listarPartidosPorEquipo(Equipo equipo) {
    	return repositoryService.allMatches(new QueryDefault<Partido>(Partido.class, "listarPartidosPorEquipo", "equipo", equipo));
    	
    }
    
    @javax.inject.Inject
    RepositoryService repositoryService;
}
