package domainapp.dom.club;

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

import domainapp.dom.domicilio.Domicilio;



@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "club"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="idClub")
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.club.Club "),
        @javax.jdo.annotations.Query(
                name = "buscarPorNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.club.Club "
                        + "WHERE nombre.indexOf(:nombre) >= 0 ")
})
@javax.jdo.annotations.Unique(name="Club_idInterno_UNQ", members = {"idInterno"})
@DomainObject
@DomainObjectLayout
public class Club implements Comparable<Club> {
	
	public static final int NAME_LENGTH = 40;
    
    public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				"Club: " + this.getNombre());
	}

    public String iconName(){return "club";}
    
    public static class NameDomainEvent extends PropertyDomainEvent<Club,String> {}
    
    //NOMBRE
    @MemberOrder(sequence = "0")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private String nombre;
	public String getNombre() {return nombre;}
	public void setNombre(final String nombre) {this.nombre = nombre;}
	//VALIDA NOMBRE
	public String validateNombre(String nom) {
		if (nom.matches("[a-z,A-Z,0-9,ñ,Ñ, ]+") == false) {
			return "Datos erroneos, vuelva a intentarlo";
		} else {
			return null;
		}
	}
		
	//NOMBRE INSTITUCIONAL
	@MemberOrder(sequence = "1")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private String nombreInstitucional;
	public String getNombreInstitucional() {return nombreInstitucional;}
	public void setNombreInstitucional(final String nombreInstitucional) {this.nombreInstitucional = nombreInstitucional;}
	//VALIDA NOMBRE
	public String validateNombreInstitucional(String nom) {
		if (nom.matches("[a-z,A-Z,0-9,ñ,Ñ, ]+") == false) {
			return "Datos erroneos, vuelva a intentarlo";
		} else {
			return null;
		}
	}
	
	//ANIO AFILIACION
	@MemberOrder(sequence = "2")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private int anioAfiliacion;
	public int getAnioAfiliacion() {return anioAfiliacion;}
	public void setAnioAfiliacion(final int anioAfiliacion) {this.anioAfiliacion = anioAfiliacion;}
	
	//ID INTERNO
	@MemberOrder(sequence = "3")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private String idInterno;
	public String getIdInterno() {return idInterno;}
	public void setIdInterno(String idInterno) {this.idInterno = idInterno;}
	
	//PERSONERIA JURIDICA
	@MemberOrder(sequence = "3")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private String personeriaJuridica;
	public String getPersoneriaJuridica(){return personeriaJuridica;}
	public void setPersoneriaJuridica(String personeriaJuridica) {this.personeriaJuridica = personeriaJuridica;}
	
	//EMAIL
	@MemberOrder(sequence = "4")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private String email;
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	
	//TELEFONO
	@MemberOrder(sequence = "5")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private String telefono;
	public String getTelefono() {return telefono;}
	public void setTelefono(String telefono) {this.telefono = telefono;}
	
	//DOMICILIO
	@MemberOrder(sequence = "6")
	@Property(editing = Editing.ENABLED)	
	@Column(name="DOMICILIO_ID")	
	private Domicilio domicilio;	
	public Domicilio getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public int compareTo(final Club other) {
        return ObjectContracts.compare(this, other, "idInterno");
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    
    @javax.inject.Inject
    Club clubes;
	
}
