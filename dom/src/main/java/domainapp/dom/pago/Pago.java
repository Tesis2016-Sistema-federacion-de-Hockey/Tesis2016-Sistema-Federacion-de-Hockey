package domainapp.dom.pago;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.joda.time.LocalDate;

import domainapp.dom.jugador.Jugador.NameDomainEvent;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public abstract class Pago {

	//VALOR
	@MemberOrder(sequence = "2")
	@Column(allowsNull="false")
	@Property(domainEvent = NameDomainEvent.class)
	private Double valor;
	public Double getValor() {return valor;}
	public void setValor(Double valor) {this.valor = valor;}
	
	//VALOR
	@MemberOrder(sequence = "3")
	@Column(allowsNull="false")
	@Property(domainEvent = NameDomainEvent.class)
	private LocalDate fecha;
	public LocalDate getFecha() {return fecha;}
	public void setFecha(LocalDate fecha) {this.fecha = fecha;}
	
	//NRORECIBO
	@MemberOrder(sequence = "4")
	@Column(allowsNull="false")
	@Property(domainEvent = NameDomainEvent.class)
	private Double nroRecibo;
	public Double getNroRecibo() {return nroRecibo;}
	public void setNroRecibo(Double nroRecibo) {this.nroRecibo = nroRecibo;}
	
	
	
	
}
