package domainapp.dom.domicilio;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.jugador.Jugador.NameDomainEvent;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple"        
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY//,
         
        )
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")

@DomainObject
@DomainObjectLayout

public class Domicilio {
	
	public String iconName() {
		return "direccion";
	}

	public String title() {
		return getCalle();
	}
	
	@MemberOrder(sequence = "0")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private String calle;
	public String getCalle() {return calle;}
	public void setCalle(String calle) {this.calle = calle;}
	
	@MemberOrder(sequence = "1")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private int numero;
	public int getNumero() {return numero;}
	public void setNumero(int numero) {this.numero = numero;}
	
	@MemberOrder(sequence = "2")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private int piso;
	public int getPiso() {return piso;}
	public void setPiso(int piso) {this.piso = piso;}
	
	@MemberOrder(sequence = "3")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private String departamento;
	public String getDepartamento() {return departamento;}
	public void setDepartamento(String departamento) {this.departamento = departamento;}
	
	
	
	
		
	
	

}
