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

package domainapp.dom.partido;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.apache.isis.applib.value.Blob;
import org.joda.time.format.DateTimeFormat;
import domainapp.dom.equipo.Equipo;
import domainapp.dom.jugador.Jugador;
import net.sf.jasperreports.engine.JREmptyDataSource;
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
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Partidos Disputados por Equipo",
            hidden=Where.PARENTED_TABLES
    )
    @MemberOrder(sequence = "1")
    public List<Partido> listarPartidosPorEquipo(Equipo equipo) {
    	return repositoryService.allMatches(new QueryDefault<Partido>(Partido.class, "listarPartidosPorEquipo", "equipo", equipo));
    }
    
    //PARA IMPRIMIR
    @ActionLayout(named = "Imprimir Planilla de Partido", cssClassFa="fa fa-download")
	public Blob imprimirPlanillaPartido(final Partido partido) throws JRException, IOException {
		
    	List<String> jugadoresLocal=new ArrayList<String>();
    	for(Jugador jug : partido.getEquipoLocal().getListaBuenaFe()){
    		jugadoresLocal.add(jug.getNombre()+" "+jug.getApellido());
    	}
    	
    	List<String> jugadoresVisitante=new ArrayList<String>();
    	for(Jugador jug : partido.getEquipoVisitante().getListaBuenaFe()){
    		jugadoresVisitante.add(jug.getNombre()+" "+jug.getApellido());
    	}
    	
    	File file = new File("planillaPartido1.jrxml");
		
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
		
		parametros.put("jugadoresLocal", jugadoresLocal);
		parametros.put("club", partido.getEquipoLocal().getNombre());
		parametros.put("division", partido.getEquipoLocal().getDivision().getNombre());
		parametros.put("equipoLocal", partido.getEquipoLocal().getNombre());
		parametros.put("equipoVisitante", partido.getEquipoVisitante().getNombre());
		parametros.put("torneo", partido.getEquipoLocal().getDivision().getTorneo().getNombre());
		parametros.put("horario", DateTimeFormat.forPattern("dd-MM-yyyy HH:mm").print(partido.getFechaHora()));
		parametros.put("jugadoresVisitante", jugadoresVisitante);
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros,new JREmptyDataSource());
    	
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
			
			return new Blob(partido.getNombre()+" - "+partido.getEquipoLocal().getNombre()+
					" vs "+partido.getEquipoVisitante().getNombre()+".pdf", "application/pdf", fileContent);
			
		} catch (Exception e) {
			byte[] result = new String("error en crear archivo").getBytes();
			return new Blob("error.txt", "text/plain", result);
		}
	}
    
    @javax.inject.Inject
    Partido partido;
    
    @javax.inject.Inject
    RepositoryService repositoryService;
}