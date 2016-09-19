package domainapp.dom.domicilio;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;

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
	
	public String iconName() {return "Domicilio";}

	public String title() {return getCalle()+" "+getNumero();}
	
	@MemberOrder(sequence = "1")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "true")
	private String calle;
	public String getCalle() {return calle;}
	public void setCalle(String calle) {this.calle = calle;}
	
	@MemberOrder(sequence = "2")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "true")
	private String numero;
	public String getNumero() {return numero;}
	public void setNumero(String numero) {this.numero = numero;}
	
	@MemberOrder(sequence = "3")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "true")
	private String piso;
	public String getPiso() {return piso;}
	public void setPiso(String piso) {this.piso = piso;}
	
	@MemberOrder(sequence = "4")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "true")
	private String departamento;
	public String getDepartamento() {return departamento;}
	public void setDepartamento(String departamento) {this.departamento = departamento;}
}