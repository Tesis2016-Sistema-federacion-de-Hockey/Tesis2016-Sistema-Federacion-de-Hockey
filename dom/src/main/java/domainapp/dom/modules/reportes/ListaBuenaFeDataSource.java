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