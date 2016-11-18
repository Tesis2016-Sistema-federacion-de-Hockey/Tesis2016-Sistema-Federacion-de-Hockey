package domainapp.dom.pagoclub;

import java.math.BigDecimal;
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
import org.joda.time.LocalDate;

import domainapp.dom.club.Club;
import domainapp.dom.cuotaclub.CuotaClub;
import domainapp.dom.cuotaclub.CuotaClubServicio;

@SuppressWarnings("deprecation")
@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = CuotaClub.class
)
@DomainServiceLayout(
        menuOrder = "4",
        named="Pagos"
)
public class PagoClubServicio {
	public TranslatableString title() {return TranslatableString.tr("Pagos del Club");}
	
	public static class CreateDomainEvent extends ActionDomainEvent<PagoClubServicio> {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CreateDomainEvent(final PagoClubServicio source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }
	
	@Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Listar Pagos de Clubes (todos)"
    )
	@MemberOrder(name="Pagos", sequence = "3.1")
    public List<PagoClub> listarTodosLosPagosDeLosClubes() {
        return repositoryService.allInstances(PagoClub.class);
    }
	
	@Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Listar Pagos por Club"
    )
	@MemberOrder(name="Pagos", sequence = "3.2")
    public List<PagoClub> listarPagosPorClub(final Club club) {
		
		return repositoryService.allMatches(new QueryDefault<PagoClub>(PagoClub.class, "listarPagosPorClub", "club", club));
    	
	}
	
	@Action(
            domainEvent = CreateDomainEvent.class
    )
    @ActionLayout(
    		cssClassFa="fa fa-plus-square",
    		named="Cobrar Cuota de Club"
    )
    @MemberOrder(name="Pagos", sequence = "3.9")
    public PagoClub crearPago(
    		final @ParameterLayout(named="Recibo") String nroRecibo,
    		final @ParameterLayout(named="Fecha de Pago") LocalDate fechaDePago,
    		final @ParameterLayout(named="Valor") BigDecimal valor,
    		final @ParameterLayout(named="Club") Club club,
    		final @ParameterLayout(named="Cuota a pagar") CuotaClub cuotaClub          
    		){
        final PagoClub obj = repositoryService.instantiate(PagoClub.class);
        obj.setNroRecibo(nroRecibo);
        obj.setFechaDePago(fechaDePago);
        obj.setValor(valor);
        obj.setClub(club);
        obj.setCuotaClub(cuotaClub);
        repositoryService.persist(obj);
        return obj;
    }
	
	public LocalDate default1CrearPago(){
		return LocalDate.now();
	}
	
	public String validateCrearPago(
			final String nroRecibo,
			final LocalDate fechaDePago,
    		final BigDecimal valor,
    		final Club clubb,
    		final CuotaClub cuotaClubb
			){
		final List<PagoClub> listaPagoClub = repositoryService.allMatches(QueryDefault
				.create(PagoClub.class, "listarPagosPorClubYCuota",
						"club", clubb, "cuotaClub", cuotaClubb));
		if (!listaPagoClub.isEmpty()){			
			return "La cuota elegida ya fue pagada. Seleccione otra";
		}
		return "";
	}
	
	
	
	@ActionLayout(hidden = Where.EVERYWHERE)
	public String buscarCuotaClub(final Club club, CuotaClub cuotaClub) {
		return "";
	}
	
	public List<Club> choices0BuscarCuotaClub(final Club club) {
		return repositoryService.allMatches(QueryDefault.create(Club.class,
				"traerClub", "club", club));
	}
	
	public Club default0BuscarCuotaClub(final Club clu) {
		return repositoryService.allInstances(Club.class, 0).get(0);
	}
	
	//aca parece que hay un error en traercuotajugador
	public List<Club> choices1BuscarCuotaClub(final Club club,
			CuotaClub cuotaClub) {
		return repositoryService.allMatches(QueryDefault.create(Club.class,
				"traerCuotaClub", "club", club, "cuotaClub", cuotaClub));
	}
	
	@ActionLayout(hidden = Where.EVERYWHERE)
	public List<Club> buscarClub(String clu) {
		return repositoryService.allMatches(QueryDefault.create(Club.class,
				"traerClub", "nombre", clu));
	}
	
	public Club default3CrearPago() {
		return repositoryService.firstMatch(QueryDefault.create(Club.class,
				"traerTodos"));
	}
	
	public List<CuotaClub> choices4CrearPago(
			final String nroRecibo,
			final LocalDate fechaDePago,
    		final BigDecimal valor,
    		final Club clubb
    		) {
		return repositoryService.allMatches(QueryDefault.create(CuotaClub.class,
				"traerCuotaClub", "club", clubb));
	}
	
	@ActionLayout(hidden = Where.EVERYWHERE)
	public List<PagoClub> buscarPagoClub(String nroRecibo) {
		return repositoryService.allMatches(QueryDefault
				.create(PagoClub.class, "buscarPagoClub", "nroRecibo", nroRecibo));
	}
	
	@javax.inject.Inject
	CuotaClubServicio cuotaClubServicio;
	
	@javax.inject.Inject
    RepositoryService repositoryService;
}