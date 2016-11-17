package domainapp.dom.pago;

import java.math.BigDecimal;

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
	
	//NRORECIBO
	@MemberOrder(sequence = "1")
	@Column(allowsNull="false")
	@Property(domainEvent = NameDomainEvent.class)
	private String nroRecibo;
	public String getNroRecibo() {return nroRecibo;}
	public void setNroRecibo(String nroRecibo) {this.nroRecibo = nroRecibo;}

	//FECHA DE PAGO
	@MemberOrder(sequence = "2")
	@Column(allowsNull="false")
	@Property(domainEvent = NameDomainEvent.class)
	private LocalDate fechaDePago;
	public LocalDate getFechaDePago() {return fechaDePago;}
	public void setFechaDePago(LocalDate fechaDePago) {this.fechaDePago = fechaDePago;}

	//VALOR
	@MemberOrder(sequence = "3")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
    private BigDecimal valor;
	public BigDecimal getValor() {return valor;}
	public void setValor(BigDecimal valor) {this.valor = valor;}
}