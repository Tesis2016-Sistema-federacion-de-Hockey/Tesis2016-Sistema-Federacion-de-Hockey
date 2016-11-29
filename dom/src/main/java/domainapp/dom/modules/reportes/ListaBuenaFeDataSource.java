package domainapp.dom.modules.reportes;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ListaBuenaFeDataSource implements JRDataSource{
	
	private List<ReporteListaBuenaFe> listado=new ArrayList<ReporteListaBuenaFe>();
	
	private int indiceActual = -1;
	
	@Override
	public Object getFieldValue(JRField jrf) throws JRException {
		
		Object valor = null;
		
		if("nombre".equals(jrf.getName())){
			
			valor=listado.get(indiceActual).getNombre();
			
		}else if ("apellido".equals(jrf.getName())){
			
			valor=listado.get(indiceActual).getApellido();
			
		}else if ("dni".equals(jrf.getName())){
			
			valor=listado.get(indiceActual).getDni();			
		}
		return valor;
	}

	@Override
	public boolean next() throws JRException {
		
		return ++indiceActual<listado.size();
		
	}
	
	public void addParticipante(ReporteListaBuenaFe listaBuenaFe) {
		this.listado.add(listaBuenaFe);
	}
}