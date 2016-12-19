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

import java.math.BigDecimal;

public class PagoClubReporte {
	
	private String nroRecibo;
	public String getNroRecibo() {return nroRecibo;}
	public void setNroRecibo(String nroRecibo) {this.nroRecibo = nroRecibo;}
	
	private String fechaDePago;
	public String getFechaDePago() {return fechaDePago;}
	public void setFechaDePago(String fechaDePago) {this.fechaDePago = fechaDePago;}

	private BigDecimal valor;
	public BigDecimal getValor() {return valor;}
	public void setValor(BigDecimal valor) {this.valor = valor;}
	
	private String club;
	public String getClub() {return club;}
	public void setClub(String club) {this.club = club;}
	
	private String cuotaClub;
	public String getCuotaClub() {return cuotaClub;}
	public void setCuotaClub(String cuotaClub) {this.cuotaClub = cuotaClub;}
}