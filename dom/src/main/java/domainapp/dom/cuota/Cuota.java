package domainapp.dom.cuota;

import java.math.BigDecimal;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.joda.time.LocalDate;

import domainapp.dom.jugador.Jugador.NameDomainEvent;
import domainapp.dom.temporada.Temporada;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public abstract class Cuota {
	
	//TEMPORADA
	@MemberOrder(sequence = "1")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	private Temporada temporada;
	public Temporada getTemporada() {return temporada;}
	public void setTemporada(final Temporada temporada) {this.temporada = temporada;}
	
	//VALOR
	@MemberOrder(sequence = "2")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
    private BigDecimal valor;
	public BigDecimal getValor() {return valor;}
	public void setValor(BigDecimal valor) {this.valor = valor;}

	//VENCIMIENTO
	@MemberOrder(sequence = "3")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
    private LocalDate vencimiento;
	public LocalDate getVencimiento() {return vencimiento;}
	public void setVencimiento(LocalDate vencimiento) {this.vencimiento = vencimiento;}
	
	//DETALLE
	@MemberOrder(sequence = "4")
    @Column(allowsNull="true")
    @Property(domainEvent = NameDomainEvent.class)
	private String detalle;
	public String getDetalle() {return detalle;}
	public void setDetalle(String detalle) {this.detalle = detalle;}
}