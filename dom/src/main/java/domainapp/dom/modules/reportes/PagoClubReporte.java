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