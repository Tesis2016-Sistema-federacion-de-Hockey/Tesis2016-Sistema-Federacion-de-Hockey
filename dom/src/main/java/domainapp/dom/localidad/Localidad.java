package domainapp.dom.localidad;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.jugador.Jugador;
import domainapp.dom.jugador.Jugadores;
import domainapp.dom.jugador.Jugador.NameDomainEvent;






@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Localidad"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="idLocalidad")
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.localidad.Localidad "),
        @javax.jdo.annotations.Query(
                name = "buscarPorNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.localidad.Localidad "
                        + "WHERE localidad.indexOf(:nombre) >= 0 ")
})
@javax.jdo.annotations.Unique(name="Localidad_nombre_UNQ", members = {"nombre"})
@DomainObject
@DomainObjectLayout
public class Localidad implements Comparable<Localidad> {
	
	public static final int NAME_LENGTH = 40;
	
	public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				"Localidad: " + this.getNombre());
	}
	
	public String iconName(){return "localidad";}
	
	public static class NameDomainEvent extends PropertyDomainEvent<Localidad,String> {}
	
	
	//NOMBRE
		@MemberOrder(sequence = "1")
	    @Column(allowsNull="false", length = NAME_LENGTH)
	    @Property(domainEvent = NameDomainEvent.class)
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
	
		//CODIGO POSTAL
		@MemberOrder(sequence = "3")
	    @Column(allowsNull="false")
	    @Property(domainEvent = NameDomainEvent.class)
		private String codigoPostal;
		public String getCodigoPostal() {return codigoPostal;}
		public void setCodigoPostal(String codigoPostal) {this.codigoPostal = codigoPostal;}
		//VALIDA CODIGO POSTAL
		public String validateCodigoPostal(String cod) {
			if (cod.matches("[0-9]+") == false) {
				return "Datos erroneos, ingrese el número sin puntos ni espacios.";
			} else {
				return null;
			}
		}
		
		//PROVINCIA
		@MemberOrder(sequence = "2")
	    @Column(allowsNull="false")
	    @Property(domainEvent = NameDomainEvent.class)
		private String provincia;
		public String getProvincia() {return provincia;}
		public void setProvincia(String provincia) {this.provincia = provincia;}
		//VALIDA CODIGO POSTAL
		public String validateProvincia(String prov) {
			if (prov.matches("[0-9]+") == false) {
				return "Datos erroneos, ingrese el número sin puntos ni espacios.";
			} else {
				return null;
			}
		}
	
	
	
	
	@Override
    public int compareTo(final Localidad other) {
        return ObjectContracts.compare(this, other, "nombre");
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    
    @javax.inject.Inject
    Localidades localidades;
	

}
