package domainapp.dom.equipo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.apache.isis.applib.value.Blob;

import domainapp.dom.club.Club;
import domainapp.dom.club.ClubServicio;
import domainapp.dom.division.Division;
import domainapp.dom.estado.Estado;
import domainapp.dom.estado.EstadoPartido;
import domainapp.dom.fecha.Fecha;
import domainapp.dom.jugador.Jugador;
import domainapp.dom.modules.reportes.ListaBuenaFeDataSource;
import domainapp.dom.modules.reportes.ReporteListaBuenaFe;
import domainapp.dom.partido.Partido;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

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
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Listar Equipos"
    )
    @MemberOrder(sequence = "1")
    public List<Equipo> listarTodosLosEquipos() {
        return repositoryService.allInstances(Equipo.class);
    }
	
	@Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Listar Equipos por Club"
    )
    @MemberOrder(sequence = "2")
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
    @MemberOrder(sequence = "3")
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
    
    public String validateCrearEquipo(
    		final String nombre,
    		final Estado estado,
    		final Club club,
    		final Division division    		  		
    		){
      if (!division.getListaFechas().isEmpty()){
    	return "No se puede crear un equipo en esta division. Ya existe un fixture iniciado.";
      }
      return "";  
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
    
//    //METODO PARA CALCULAR LOS PARTIDOS JUGADOS QUE ESTEN FINALIZADOS
//    @ActionLayout(hidden=Where.EVERYWHERE)
//    public void calcularTablaPosiciones(Equipo equipo){
//    	
//    	int pj=0;
//    	int pg=0;
//    	int pp=0;
//    	int pe=0;
//    	int gf=0;
//    	int gc=0;
//    	int pts=0;
//    	
//    	if (equipo.getDivision().getListaFechas().size()==0) return;
//    	
//    	for (Iterator<?> it1=equipo.getDivision().getListaFechas().iterator();it1.hasNext();){
//    		
//			Fecha f=((Fecha)it1.next());
//			
//			for (Iterator<?> it2=f.getListaPartidos().iterator();it2.hasNext();){
//				
//				Partido p =((Partido)it2.next());
//				
//				if (p.getEstadoPartido()==EstadoPartido.FINALIZADO){
//					
//					if(p.getEquipoLocal()==equipo || p.getEquipoVisitante()==equipo) pj++;
//					
//					if(p.getEquipoLocal()==equipo){
//						
//						if (p.getGolesLocal()>p.getGolesVisitante()){
//							
//							pg++;
//							
//							pts=pts+equipo.getDivision().getPuntosGanar();
//						}
//						
//						else if (p.getGolesLocal()<p.getGolesVisitante()){
//							
//							pp++;
//							
//							pts=pts+equipo.getDivision().getPuntosPerder();
//						}
//						
//						else if (p.getGolesLocal()==p.getGolesVisitante()){
//							
//							pe++;
//							
//							pts=pts+equipo.getDivision().getPuntosEmpatar();
//						}
//						
//						gf=gf+p.getGolesLocal();
//						
//						gc=gc+p.getGolesVisitante();
//					}
//					
//					else if (p.getEquipoVisitante()==equipo){
//						
//						if (p.getGolesVisitante()>p.getGolesLocal()){
//							
//							pg++;
//							
//							pts=pts+equipo.getDivision().getPuntosGanar();
//						}
//						
//						else if (p.getGolesVisitante()<p.getGolesLocal()){
//							
//							pp++;
//							
//							pts=pts+equipo.getDivision().getPuntosPerder();
//						}
//						
//						else if (p.getGolesVisitante()==p.getGolesLocal()){
//							
//							pe++;
//							
//							pts=pts+equipo.getDivision().getPuntosEmpatar();
//						}
//						
//						gf=gf+p.getGolesVisitante();
//						
//						gc=gc+p.getGolesLocal();
//					}
//				}
//			}
//    	}
//    	equipo.setPartidosJugados(pj);
//    	equipo.setPartidosGanados(pg);
//    	equipo.setPartidosPerdidos(pp);
//    	equipo.setPartidosEmpatados(pe);
//    	equipo.setGolesAFavor(gf);
//    	equipo.setGolesAContra(gc);
//    	equipo.setPuntos(pts);
//    	equipo.setOrdenDescendentePuntos(100-pts);
//    	
//    	return;
//    }
    
    @ActionLayout(named = "Exportar Lista de Buena Fe")
	public Blob downloadAll(final Equipo equipo) throws JRException, IOException {
		
		ListaBuenaFeDataSource datasource = new ListaBuenaFeDataSource();
		
		for (Jugador jug : equipo.getListaBuenaFe()){
		
			ReporteListaBuenaFe reporteListaBuenaFe=new ReporteListaBuenaFe();
			
			reporteListaBuenaFe.setNombre(jug.getNombre());
			reporteListaBuenaFe.setApellido(jug.getApellido());
			reporteListaBuenaFe.setDni(jug.getDocumento());
			
			datasource.addParticipante(reporteListaBuenaFe);
		}
		
		File file = new File("listaBuenaFe.jrxml");
		
		FileInputStream input = null;
		
		try {
			input = new FileInputStream(file);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		JasperDesign jd = JRXmlLoader.load(input);
		
		JasperReport reporte = JasperCompileManager.compileReport(jd);
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("equipo", equipo.getNombre());
		parametros.put("club", equipo.getClub().getNombre());
		parametros.put("division", equipo.getDivision().getNombre());
//		parametros.put("jugadores", lst);
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, datasource);
//		JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, null,datasource);
		
		JasperExportManager.exportReportToPdfFile(jasperPrint, "/tmp/salida.pdf");
		
		File archivo = new File("/tmp/salida.pdf");
		
		byte[] fileContent = new byte[(int) archivo.length()];
		
		if (!(archivo.exists())) {
			try {
				archivo.createNewFile();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		try {
			FileInputStream fileInputStream = new FileInputStream(archivo);

			fileInputStream.read(fileContent);
			
			fileInputStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			
			return new Blob(equipo.getNombre()+" - "+"lista de buena fe.pdf", "application/pdf", fileContent);
			
		} catch (Exception e) {
			byte[] result = new String("error en crear archivo").getBytes();
			return new Blob("error.txt", "text/plain", result);
		}
	}
    
	@javax.inject.Inject
    EquipoServicio equipoServicio;

    
    @javax.inject.Inject
    Equipo equipo;
	
    @javax.inject.Inject
    RepositoryService repositoryService;
    
    @javax.inject.Inject
    ClubServicio clubServicio;
}