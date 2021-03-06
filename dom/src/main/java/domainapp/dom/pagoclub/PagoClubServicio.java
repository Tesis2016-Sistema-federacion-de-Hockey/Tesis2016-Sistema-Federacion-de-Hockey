/*
#	This file is part of SIFHON.
#
#	Copyright ( C ) 2016 , SIFHON
#
#   SIFHON is free software: you can redistribute it and/or modify
#   it under the terms of the GNU General Public License as published by
#   the Free Software Foundation, either version 3 of the License, or
#   (at your option) any later version.
#
#   SIFHON is distributed in the hope that it will be useful,
#   but WITHOUT ANY WARRANTY; without even the implied warranty of
#   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#   GNU General Public License for more details.
#
#   You should have received a copy of the GNU General Public License
#   along with SIFHON.  If not, see <http://www.gnu.org/licenses/>.
*/

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
        nature = NatureOfService.VIEW_MENU_ONLY,
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
    		cssClassFa="fa fa-list-ol",
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
    @MemberOrder(name="Pagos", sequence = "3.8")
    public PagoClub crearPago(
    		final @ParameterLayout(named="Recibo") String nroRecibo,
    		final @ParameterLayout(named="Fecha de Pago") LocalDate fechaDePago,
    		final @ParameterLayout(named="Monto entregado") BigDecimal valor,
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
			
			BigDecimal sumaPagosParciales=new BigDecimal(0); // suma de pagos parciales
			
			BigDecimal restoAPagar=new BigDecimal(0); // resto a pagar para alcanzar el valor de la cuotaClub
			
			for(PagoClub pagoCl:listaPagoClub){
				
				sumaPagosParciales=sumaPagosParciales.add(pagoCl.getValor());
			}
			restoAPagar=restoAPagar.add(cuotaClubb.getValor().subtract(sumaPagosParciales));
			
			if(cuotaClubb.getValor().compareTo(sumaPagosParciales)==0){
				
				return "La cuota ya esta pagada";
				
			}
			
			else if(valor.compareTo(restoAPagar)==1){
				
				return "El valor del pago ingresado ($ " + valor.toString() +
						") no debe ser mayor que el valor que falta pagar de la cuota ($ " +
						restoAPagar.toString() + ")";
			}
		}
		else if (listaPagoClub.isEmpty()){
			
			if (valor.compareTo(cuotaClubb.getValor())==1){
				
				return "El valor del pago ingresado ($ " + valor.toString() +
						") no debe ser mayor que el valor de la cuota ($ " +
						cuotaClubb.getValor().toString() + ")";
			}
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
	
	public List<CuotaClub> choices1BuscarCuotaClub(final Club club,
	CuotaClub cuotaClub) {
		return repositoryService.allMatches(QueryDefault.create(CuotaClub.class,
		"traerCuotaClub", "club", club, "cuotaClub", cuotaClub));
}
	
//	//aca parece que hay un error en traercuotaclub
//	public List<Club> choices1BuscarCuotaClub(final Club club,
//			CuotaClub cuotaClub) {
//		return repositoryService.allMatches(QueryDefault.create(Club.class,
//				"traerCuotaClub", "club", club, "cuotaClub", cuotaClub));
//	}
	
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
	
//	@Action(
//            semantics = SemanticsOf.SAFE
//    )
//    @ActionLayout(
//    		cssClassFa="fa fa-usd",
//            bookmarking = BookmarkPolicy.AS_ROOT,
//            named="Clubes que adeudan Cuotas"
//    )
//	@MemberOrder(name="Pagos", sequence = "4.9")
//    public SortedSet<Club> listarClubesConDeuda(
//    		
//    		final @ParameterLayout(named="Ingrese Cuota de Club:") CuotaClub cuotaClub){
//	
//		SortedSet<Club>listaPagaron=new TreeSet<Club>();
//		
//		SortedSet<Club>listaDeudores=new TreeSet<Club>();
//		
//		BigDecimal sumaPagosParciales=new BigDecimal(0); // suma de pagos parciales
//		
//		for (Club cl:cuotaClub.getListaClubes()){
//			
//			sumaPagosParciales=sumaPagosParciales.subtract(sumaPagosParciales);
//			
//			for(PagoClub pagoCl:cuotaClub.getListaPagosClub()){
//				
//				if (pagoCl.getClub()==cl){
//					
//					sumaPagosParciales=sumaPagosParciales.add(pagoCl.getValor());
//				}
//			}
//			
//			if (sumaPagosParciales.compareTo(cuotaClub.getValor())==0) listaPagaron.add(cl);
//			
//			cl.setDeuda(cuotaClub.getValor().subtract(sumaPagosParciales));
//		}
//		
//		for (Club cl:cuotaClub.getListaClubes()){
//				
//			if (!listaPagaron.contains(cl)){
//				
//				listaDeudores.add(cl);
//			}
//		}
//		return listaDeudores;
//	}
	
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