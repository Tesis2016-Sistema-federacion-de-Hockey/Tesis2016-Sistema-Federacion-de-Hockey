package domainapp.dom.club;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.factory.FactoryService;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;
import org.joda.time.LocalDate;

import domainapp.dom.domicilio.Domicilio;
import domainapp.dom.estado.Estado;
import domainapp.dom.jugador.Jugador;
import domainapp.dom.sector.Sector;
import domainapp.dom.tipodocumento.TipoDocumento;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Club"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="club_id")
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "traerTodos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.club.Club "),
        @javax.jdo.annotations.Query(
                name = "buscarPorNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.club.Club "
                        + "WHERE nombre.indexOf(:nombre) >= 0 ")
})
@javax.jdo.annotations.Unique(name="Club_nombre_UNQ", members = {"nombre"})
@DomainObject(autoCompleteRepository = Clubes.class, autoCompleteAction = "buscarClub")
@DomainObjectLayout(bookmarking=BookmarkPolicy.AS_ROOT)
public class Club implements Comparable<Club> {
	
	public static final int NAME_LENGTH = 40;
    
    public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				"Club: " + this.getNombre());
	}

    public String iconName(){return "club";}
    
    public static class NameDomainEvent extends PropertyDomainEvent<Club,String> {}
    
    //NOMBRE
    @MemberOrder(sequence = "1")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private String nombre;
	public String getNombre() {return nombre;}
	public void setNombre(final String nombre) {this.nombre = nombre;}
			
	//NOMBRE INSTITUCIONAL
	@MemberOrder(sequence = "2")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "true")
	private String nombreInstitucional;
	public String getNombreInstitucional() {return nombreInstitucional;}
	public void setNombreInstitucional(final String nombreInstitucional) {this.nombreInstitucional = nombreInstitucional;}
	
	//ANIO AFILIACION
	@MemberOrder(sequence = "3")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "true")
	private String anioAfiliacion;
	public String getAnioAfiliacion() {return anioAfiliacion;}
	public void setAnioAfiliacion(final String anioAfiliacion) {this.anioAfiliacion = anioAfiliacion;}
	
	//ID INTERNO
	@MemberOrder(sequence = "4")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private String idInterno;
	public String getIdInterno() {return idInterno;}
	public void setIdInterno(String idInterno) {this.idInterno = idInterno;}
	
	//PERSONERIA JURIDICA
	@MemberOrder(sequence = "5")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "true")
	private String personeriaJuridica;
	public String getPersoneriaJuridica(){return personeriaJuridica;}
	public void setPersoneriaJuridica(String personeriaJuridica) {this.personeriaJuridica = personeriaJuridica;}
	
	//EMAIL
	@MemberOrder(sequence = "5")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "true")
	private String email;
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	
	//TELEFONO
	@MemberOrder(sequence = "6")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "true")
	private String telefono;
	public String getTelefono() {return telefono;}
	public void setTelefono(String telefono) {this.telefono = telefono;}
	
	//DOMICILIO
	@MemberOrder(sequence = "7")
	@Property(editing = Editing.ENABLED)	
	@Column(name="DOMICILIO_ID")	
	private Domicilio domicilio;	
	public Domicilio getDomicilio() {return domicilio;}
	public void setDomicilio(Domicilio domicilio) {this.domicilio = domicilio;}
	
	
	
	
	//LISTADO DE JUGADORES DEL CLUB
//	@Column(allowsNull = "false")
	@Persistent(mappedBy="club", dependentElement="true")
//	@Join(column = "jugador_id")
	private SortedSet<Jugador> listaJugadores=new TreeSet<Jugador>();
    
	@MemberOrder(sequence = "8")
	public SortedSet<Jugador> getListaJugadores() {return listaJugadores;}
	public void setListaJugadores(final SortedSet<Jugador> listaJugadores) {this.listaJugadores = listaJugadores;}

	
	
	
	@MemberOrder(sequence = "9")
	public void agregarJugador(Jugador e) {

		if(e == null || listaJugadores.contains(e)) return;
	    e.setClub(this);
	    listaJugadores.add(e);
	}
	@MemberOrder(sequence = "10")
	public void quitarJugador(Jugador e) {
	    if(e == null || !listaJugadores.contains(e)) return;
	    
	    //Duplico el jugador e y luego lo elimino
	    final Jugador obj = repositoryService.instantiate(Jugador.class);
//        final Domicilio domicilio=new Domicilio();
//        domicilio.setCalle(e.getDomicilio().getCalle());
//        domicilio.setNumero(e.getDomicilio().getNumero());
//        domicilio.setPiso(e.getDomicilio().getPiso());
//        domicilio.setDepartamento(e.getDomicilio().getDepartamento());
        obj.setSector(e.getSector());
        obj.setFicha(e.getFicha());
        obj.setNombre(e.getNombre());
        obj.setApellido(e.getApellido());
        obj.setTipo(e.getTipo());
        obj.setDocumento(e.getDocumento());
        obj.setFechaNacimiento(e.getFechaNacimiento());
        obj.setEstado(e.getEstado());
        obj.setEmail(e.getEmail());
        obj.setTelefono(e.getTelefono());
        obj.setCelular(e.getCelular());
        obj.setDomicilio(e.getDomicilio());
        repositoryService.persist(obj);
        obj.setClub(null);
	    
	    listaJugadores.remove(e);
	}
	
	@SuppressWarnings("deprecation")
	public int compareTo(final Club other) {
        return ObjectContracts.compare(this, other, "nombre");
    }
	
	@javax.inject.Inject
	FactoryService factoryService;
	
	@javax.inject.Inject
    RepositoryService repositoryService;
    
    @javax.inject.Inject
    Club clubes;
}