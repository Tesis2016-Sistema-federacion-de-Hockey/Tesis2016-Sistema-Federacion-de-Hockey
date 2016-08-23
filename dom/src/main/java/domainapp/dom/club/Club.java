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

//	@Action()
//	public Club agregarJugador(final Jugador jug){
//		
//		final Jugador obj= factoryService.instantiate(Jugador.class);
//		obj.setClub(this);
//		getListaJugadores().add(obj);
//		repositoryService.persist(obj);
//		return this;
//	}
	
	
	
//	@Action()
//	public Club agregarJugador(
//			final @ParameterLayout(named="Sector") @Parameter(optionality=Optionality.OPTIONAL) Sector sector,
//            final @ParameterLayout(named="Ficha") String ficha,
//            final @ParameterLayout(named="Nombre") String nombre,
//            final @ParameterLayout(named="Apellido") String apellido,
//            final @ParameterLayout(named="Tipo") @Parameter(optionality=Optionality.OPTIONAL) TipoDocumento tipo,
//            final @ParameterLayout(named="Documento") @Parameter(optionality=Optionality.OPTIONAL) String documento,
//            final @ParameterLayout(named="Fecha de Nacimiento") @Parameter(optionality=Optionality.OPTIONAL) LocalDate fechaNacimiento,
//            final @ParameterLayout(named="Estado") @Parameter(optionality=Optionality.OPTIONAL) Estado estado,
//            final @ParameterLayout(named="Email") @Parameter(optionality=Optionality.OPTIONAL) String email,
//            final @ParameterLayout(named="Calle") @Parameter(optionality=Optionality.OPTIONAL) String calle,
//            final @ParameterLayout(named="Numero") @Parameter(optionality=Optionality.OPTIONAL) String numero,
//            final @ParameterLayout(named="Piso") @Parameter(optionality=Optionality.OPTIONAL) String piso,
//            final @ParameterLayout(named="Departamento") @Parameter(optionality=Optionality.OPTIONAL) String departamento,
//            final @ParameterLayout(named="Telefono") @Parameter(optionality=Optionality.OPTIONAL) String telefono,
//            final @ParameterLayout(named="Celular") @Parameter(optionality=Optionality.OPTIONAL) String celular
//			){
//		final Jugador obj= factoryService.instantiate(Jugador.class);
//		final Domicilio domicilio=new Domicilio();
//		obj.setClub(this);
//		domicilio.setCalle(calle);
//        domicilio.setNumero(numero);
//        domicilio.setPiso(piso);
//        domicilio.setDepartamento(departamento);
//        obj.setSector(sector);
//        obj.setFicha(ficha);
//        obj.setNombre(nombre);
//        obj.setApellido(apellido);
//        obj.setTipo(tipo);
//        obj.setDocumento(documento);
//        obj.setFechaNacimiento(fechaNacimiento);
//        obj.setEstado(estado);
//        obj.setEmail(email);
//        obj.setTelefono(telefono);
//        obj.setCelular(celular);
//        obj.setDomicilio(domicilio);
//		getListaJugadores().add(obj);
//		repositoryService.persist(obj);
//		return this;
//	}
	
	
	
	@MemberOrder(sequence = "9")
	public void agregarJugador(Jugador e) {

		if(e == null || listaJugadores.contains(e)) return;
	    e.setClub(this);
	    listaJugadores.add(e);
	}
	@MemberOrder(sequence = "10")
	public void quitarJugador(Jugador e) {
	    if(e == null || !listaJugadores.contains(e)) return;
	    
	    //e.setClub(null);
//	    e.setNombre(e.getNombre());
	    
    //listaJugadores.remove(e);
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