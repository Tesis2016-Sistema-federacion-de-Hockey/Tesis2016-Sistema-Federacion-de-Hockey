package domainapp.dom.persona;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.Where;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import domainapp.dom.estado.Estado;
import domainapp.dom.jugador.Jugador.NameDomainEvent;
import domainapp.dom.sector.Sector;
import domainapp.dom.tipodocumento.TipoDocumento;

//Estrategia para la Herencia: Una tabla por cada clase, solo las subclases
@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public abstract class Persona {
	
	public static final int NAME_LENGTH = 40;
	
	//NOMBRE
	@MemberOrder(sequence = "2")
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
    
	//APELLIDO
	@MemberOrder(sequence = "3")
    @Column(allowsNull="false", length = NAME_LENGTH)
    @Property(domainEvent = NameDomainEvent.class)
    private String apellido;
    public String getApellido() {return apellido;}
	public void setApellido(String apellido) {this.apellido = apellido;}
	//VALIDA APELLIDO
	public String validateApellido(String ape) {
		if (ape.matches("[a-z,A-Z,0-9,ñ,Ñ, ]+") == false) {
			return "Datos erroneos, vuelva a intentarlo";
		} else {
			return null;
		}
	}	
	
	//TIPO DE DOCUMENTO
	@MemberOrder(sequence = "4")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	private TipoDocumento tipoDocumento;
	public TipoDocumento getTipoDocumento() {return tipoDocumento;}
	public void setTipoDocumento(TipoDocumento tipoDocumento) {this.tipoDocumento = tipoDocumento;}

	//DOCUMENTO
	@MemberOrder(sequence = "5")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	private String documento;
	public String getDocumento() {return documento;}
	public void setDocumento(String documento) {this.documento = documento;}
	//VALIDA DOCUMENTO
	public String validateDocumento(String doc) {
		if (doc.matches("[0-9]+") == false) {
			return "Datos erroneos, ingrese el número sin puntos ni espacios.";
		} else {
			return null;
		}
	}
	
	//FECHA DE NACIMIENTO
	@MemberOrder(sequence = "6")
	@Column(allowsNull = "false")
	private LocalDate fechaNacimiento;
	public LocalDate getFechaNacimiento() {return fechaNacimiento;}
	public void setFechaNacimiento(final LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;}

	final LocalDate fecha_actual = LocalDate.now();
	//VALIDA FECHA DE NACIMIENTO
	public String validateFechaNacimiento(final LocalDate fechaNacimiento) {

		if (fechaNacimiento.isAfter(fecha_actual))
			return "La fecha de Nacimiento debe ser menor o igual a la fecha actual";
		if (validaMayorEdad(fechaNacimiento) == false)
			return "La persona es menor de edad";
		if (validaMayorCien(fechaNacimiento) == false)
			return "La persona no puede ser mayor a 100 años";
		return "";
	}
	@ActionLayout(hidden = Where.EVERYWHERE)
	public boolean validaMayorEdad(LocalDate fechadeNacimiento) {

		if (getDiasNacimiento_Hoy(fechadeNacimiento) >= 6575) {
			return true;
		}
		return false;
	}

	@ActionLayout(hidden = Where.EVERYWHERE)
	public boolean validaMayorCien(LocalDate fechadeNacimiento) {

		if (getDiasNacimiento_Hoy(fechadeNacimiento) <= 36500) {
			return true;
		}
		return false;
	}

	@ActionLayout(hidden = Where.EVERYWHERE)
	public int getDiasNacimiento_Hoy(LocalDate fechadeNacimiento) {

		Days meses = Days.daysBetween(fechadeNacimiento, fecha_actual);
		return meses.getDays();
	}
	
	//ESTADO
	@MemberOrder(sequence = "7")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	private Estado estado;
	public Estado getEstado() {return estado;}
	public void setEstado(Estado estado) {this.estado = estado;}

	//EMAIL
	@MemberOrder(sequence = "8")
    @Column(allowsNull="false", length = NAME_LENGTH)
    @Property(domainEvent = NameDomainEvent.class)
    private String email;
    public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	
	//TELEFONO FIJO
	@MemberOrder(sequence = "9")
    @Column(allowsNull="false", length = NAME_LENGTH)
    @Property(domainEvent = NameDomainEvent.class, editing=Editing.ENABLED)
    private String telefono;
    public String getTelefono() {return telefono;}
	public void setTelefono(String telefono) {this.telefono = telefono;}

	//CELULAR
	@MemberOrder(sequence = "10")
    @Column(allowsNull="false", length = NAME_LENGTH)
    @Property(domainEvent = NameDomainEvent.class, editing=Editing.ENABLED)
    private String celular;
    public String getCelular() {return celular;}
	public void setCelular(String celular) {this.celular = celular;}
	
	//VISIBLE
	@MemberOrder(sequence = "11")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class, editing=Editing.DISABLED)
    private Boolean visible;
	public Boolean getVisible() {return visible;}
	public void setVisible(Boolean visible) {this.visible = visible;}
	
	
	

}
