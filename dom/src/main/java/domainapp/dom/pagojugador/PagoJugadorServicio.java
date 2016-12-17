package domainapp.dom.pagojugador;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
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
import domainapp.dom.cuotajugador.CuotaJugador;
import domainapp.dom.jugador.Jugador;

@SuppressWarnings("deprecation")
@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        repositoryFor = CuotaClub.class
)
@DomainServiceLayout(
        menuOrder = "4",
        named="Pagos"
)
public class PagoJugadorServicio {
	public TranslatableString title() {return TranslatableString.tr("Pagos del Jugador");}
	
	public static class CreateDomainEvent extends ActionDomainEvent<PagoJugadorServicio> {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CreateDomainEvent(final PagoJugadorServicio source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }
	
	@Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Listar Pagos de Jugadores (todos)"
    )
	@MemberOrder(name="Pagos", sequence = "4.1")
    public List<PagoJugador> listarTodosLosPagosDeLosJugadores() {
        return repositoryService.allInstances(PagoJugador.class);
    }
	
	@Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list-ol",
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Listar Pagos por Jugador"
    )
	@MemberOrder(name="Pagos", sequence = "4.2")
    public List<PagoJugador> listarPagosPorJugador(final Jugador jugador) {
		
		return repositoryService.allMatches(new QueryDefault<PagoJugador>(PagoJugador.class, "listarPagosPorJugador", "jugador", jugador));
    	
	}
	
	@Action(
            domainEvent = CreateDomainEvent.class
    )
    @ActionLayout(
    		cssClassFa="fa fa-plus-square",
    		named="Cobrar Cuota de Jugador"
    )
    @MemberOrder(name="Pagos", sequence = "4.8")
    public PagoJugador crearPago(
    		final @ParameterLayout(named="Recibo") String nroRecibo,
    		final @ParameterLayout(named="Fecha de Pago") LocalDate fechaDePago,
    		final @ParameterLayout(named="Monto entregado") BigDecimal valor,
    		final @ParameterLayout(named="Jugador") Jugador jugador,
    		final @ParameterLayout(named="Cuota a pagar") CuotaJugador cuotaJugador          
    		){
        final PagoJugador obj = repositoryService.instantiate(PagoJugador.class);
        obj.setNroRecibo(nroRecibo);
        obj.setFechaDePago(fechaDePago);
        obj.setValor(valor);
        obj.setJugador(jugador);
        obj.setCuotaJugador(cuotaJugador);
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
		final Jugador jugadorr,
		final CuotaJugador cuotaJugadorr
		){
		final List<PagoJugador> listaPagoJugador = repositoryService.allMatches(QueryDefault
			.create(PagoJugador.class, "listarPagosPorJugadorYCuota",
					"jugador", jugadorr, "cuotaJugador", cuotaJugadorr));
		
		if (!listaPagoJugador.isEmpty()){
			
			BigDecimal sumaPagosParciales=new BigDecimal(0); // suma de pagos parciales
			
			BigDecimal restoAPagar=new BigDecimal(0); // resto a pagar para alcanzar el valor de la cuotaJugador
			
			for(PagoJugador pagoJug:listaPagoJugador){
				
				sumaPagosParciales=sumaPagosParciales.add(pagoJug.getValor());
			}
			
			restoAPagar=restoAPagar.add(cuotaJugadorr.getValor().subtract(sumaPagosParciales));
			
			if(cuotaJugadorr.getValor().compareTo(sumaPagosParciales)==0){
				
				return "La cuota ya esta pagada";
				
			}
			
			else if(valor.compareTo(restoAPagar)==1){
				
				return "El valor del pago ingresado ($ " + valor.toString() +
						") no debe ser mayor que el valor que falta pagar de la cuota ($ " +
						restoAPagar.toString() + ")";
			}
		}
		else if(listaPagoJugador.isEmpty()){
			
			if (valor.compareTo(cuotaJugadorr.getValor())==1){
				
				return "El valor del pago ingresado ($ " + valor.toString() +
						") no debe ser mayor que el valor de la cuota ($ " +
						cuotaJugadorr.getValor().toString() + ")";
			}
		}
		return "";
		}
	
	@ActionLayout(hidden = Where.EVERYWHERE)
	public String buscarCuotaJugador(final Jugador jugador, CuotaJugador cuotaJugador) {
		return "";
	}
	
	public List<Jugador> choices0BuscarCuotaJugador(final Jugador jugador) {
		return repositoryService.allMatches(QueryDefault.create(Jugador.class,
				"traerJugador", "jugador", jugador));
	}
	
	public Jugador default0BuscarCuotaJugador(final Jugador jugad) {
		return repositoryService.allInstances(Jugador.class, 0).get(0);
	}
	
	//revisar traercuotajugador
	public List<CuotaJugador> choices1BuscarCuotaJugador(final Jugador jugador,
			CuotaJugador cuotaJugador) {
		return repositoryService.allMatches(QueryDefault.create(CuotaJugador.class,
				"traerCuotaJugador", "jugador", jugador, "cuotaJugador", cuotaJugador));
	}
	
	@ActionLayout(hidden = Where.EVERYWHERE)
	public List<Jugador> buscarJugador(String jug) {
		return repositoryService.allMatches(QueryDefault.create(Jugador.class,
				"traerJugador", "nombre", jug));
	}
	
	public Jugador default3CrearPago() {
		return repositoryService.firstMatch(QueryDefault.create(Jugador.class,
				"traerTodos"));
	}
	
	public List<CuotaJugador> choices4CrearPago(
			final String nroRecibo,
			final LocalDate fechaDePago,
    		final BigDecimal valor,
    		final Jugador jugadorr
    		) {
		return repositoryService.allMatches(QueryDefault.create(CuotaJugador.class,
				"traerCuotaJugador", "jugador", jugadorr));
	}
	
	@ActionLayout(hidden = Where.EVERYWHERE)
	public List<PagoJugador> buscarPagoJugador(String nroRecibo) {
		return repositoryService.allMatches(QueryDefault
				.create(PagoJugador.class, "buscarPagoJugador", "nroRecibo", nroRecibo));
	}
	
	@Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-usd",
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Deuda por Cuota de Jugador y por Club"
    )
	@MemberOrder(name="Pagos", sequence = "4.9")
    public SortedSet<Jugador> listarJugadoresConDeuda(
    		
    		final @ParameterLayout(named="Ingrese Cuota de Jugador:") CuotaJugador cuotaJugador,
    		
    		final @ParameterLayout(named="Ingrese Club:") Club club){   		
		
		SortedSet<Jugador>listaPagaron=new TreeSet<Jugador>();
		
		SortedSet<Jugador>listaDeudores=new TreeSet<Jugador>();
		
		BigDecimal sumaPagosParciales=new BigDecimal(0); // suma de pagos parciales
		
		for (Jugador jug:cuotaJugador.getListaJugadores()){
			
			sumaPagosParciales=sumaPagosParciales.subtract(sumaPagosParciales);
			
			if(jug.getClub()==club){
				
				for(PagoJugador pagoJug:cuotaJugador.getListaPagosJugador()){
					
					if (pagoJug.getJugador()==jug){
						
						sumaPagosParciales=sumaPagosParciales.add(pagoJug.getValor());
					}
				}
				
				if (sumaPagosParciales.compareTo(cuotaJugador.getValor())==0) listaPagaron.add(jug);
				
				jug.setDeuda(cuotaJugador.getValor().subtract(sumaPagosParciales));
			}
		}
		
		for (Jugador jug:cuotaJugador.getListaJugadores()){
			
			if(jug.getClub()==club){
				
				if (!listaPagaron.contains(jug)){
					
					listaDeudores.add(jug);
				}
			}
		}
		return listaDeudores;
	}	
	
	@javax.inject.Inject
	CuotaClubServicio cuotaClubServicio;
	
	@javax.inject.Inject
    RepositoryService repositoryService;
}