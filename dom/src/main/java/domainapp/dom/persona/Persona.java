/*
#	This file is part of SIFHON.
#
#	Copyright ( C ) 2016 , SIFHON
#
#   SIFHON is free software: you can redistribute it and/or modify
#   it under the terms of the GNU General Public License as published by
#   the Free Software Foundation, either version 3 of the License, or
#   (at your option) any later version.
#
#   SIFHON is distributed in the hope that it will be useful,
#   but WITHOUT ANY WARRANTY; without even the implied warranty of
#   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#   GNU General Public License for more details.
#
#   You should have received a copy of the GNU General Public License
#   along with SIFHON.  If not, see <http://www.gnu.org/licenses/>.
*/

package domainapp.dom.persona;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.joda.time.LocalDate;
import domainapp.dom.estado.Estado;
import domainapp.dom.jugador.Jugador.NameDomainEvent;
import domainapp.dom.tipodocumento.TipoDocumento;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public abstract class Persona {
	
	public static final int NAME_LENGTH = 50;
	
	//NOMBRE
	@MemberOrder(sequence = "3")
    @Column(allowsNull="false", length = NAME_LENGTH)
    @Property(domainEvent = NameDomainEvent.class)
    private String nombre;
    public String getNombre() {return nombre;}
    public void setNombre(final String nombre) {this.nombre = nombre;}
    
	//APELLIDO
	@MemberOrder(sequence = "4")
    @Column(allowsNull="false", length = NAME_LENGTH)
    @Property(domainEvent = NameDomainEvent.class)
    private String apellido;
    public String getApellido() {return apellido;}
	public void setApellido(String apellido) {this.apellido = apellido;}
	
	//TIPO DE DOCUMENTO
	@MemberOrder(sequence = "5")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	private TipoDocumento tipo;
	public TipoDocumento getTipo() {return tipo;}
	public void setTipo(TipoDocumento tipo) {this.tipo = tipo;}

	//DOCUMENTO
	@MemberOrder(sequence = "6")
    @Column(allowsNull="false", length=8)
    @Property(domainEvent = NameDomainEvent.class)
	private String documento;
	public String getDocumento() {return documento;}
	public void setDocumento(String documento) {this.documento = documento;}
	
	//FECHA DE NACIMIENTO
	@MemberOrder(sequence = "7")
	@Column(allowsNull = "false")
	private LocalDate fechaNacimiento;
	public LocalDate getFechaNacimiento() {return fechaNacimiento;}
	public void setFechaNacimiento(final LocalDate fechaNacimiento) {this.fechaNacimiento = fechaNacimiento;}

	//ESTADO
	@MemberOrder(sequence = "8")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	private Estado estado;
	public Estado getEstado() {return estado;}
	public void setEstado(Estado estado) {this.estado = estado;}

	//EMAIL
	@MemberOrder(sequence = "9")
    @Column(allowsNull="true", length = NAME_LENGTH)
    @Property(domainEvent = NameDomainEvent.class)
    private String email;
    public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	
	//TELEFONO FIJO
	@MemberOrder(sequence = "14")
    @Column(allowsNull="true")
    @Property(domainEvent = NameDomainEvent.class)
    private String telefono;
    public String getTelefono() {return telefono;}
	public void setTelefono(String telefono) {this.telefono = telefono;}

	//CELULAR
	@MemberOrder(sequence = "15")
    @Column(allowsNull="true")
    @Property(domainEvent = NameDomainEvent.class)
    private String celular;
    public String getCelular() {return celular;}
	public void setCelular(String celular) {this.celular = celular;}
}