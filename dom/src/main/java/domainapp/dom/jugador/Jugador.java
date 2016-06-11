/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.dom.jugador;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.persona.Persona;
import domainapp.dom.tipodocumento.TipoDocumento;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Jugador"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="idJugador")
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.jugador.Jugador "),
        @javax.jdo.annotations.Query(
                name = "buscarPorDocumento", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.jugador.Jugador "
                        + "WHERE documento.indexOf(:documento) >= 0 ")
})
@javax.jdo.annotations.Unique(name="Jugador_documento_UNQ", members = {"documento"})
@DomainObject
@DomainObjectLayout
public class Jugador extends Persona implements Comparable<Jugador> {

    public static final int NAME_LENGTH = 40;
    
    public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				"Jugador: " + this.getApellido() + ", " + this.getNombre());
	}
    
//    public TranslatableString title() {
//        return TranslatableString.tr("Jugador: {nombre} ", "nombre", getNombre());
//    }
    
    public String iconName(){
    	return "avatar";
    }
    
//    public String title(){
//        return getNombre();
//    }

    public static class NameDomainEvent extends PropertyDomainEvent<Jugador,String> {}
    
    
	@MemberOrder(sequence = "1")
    @Column(allowsNull="false", length = NAME_LENGTH)
    @Property(domainEvent = NameDomainEvent.class)
    private String nombre;
    public String getNombre() {return nombre;}
    public void setNombre(final String nombre) {this.nombre = nombre;}
    
	public String validateNombre(String nom) {
		if (nom.matches("[a-z,A-Z,0-9,ñ,Ñ, ]+") == false) {
			return "Datos erroneos, vuelva a intentarlo";
		} else {
			return null;
		}
	}
    
	@MemberOrder(sequence = "2")
    @Column(allowsNull="false", length = NAME_LENGTH)
    @Property(domainEvent = NameDomainEvent.class)
    private String apellido;
    public String getApellido() {return apellido;}
	public void setApellido(String apellido) {this.apellido = apellido;}

	public String validateApellido(String ape) {
		if (ape.matches("[a-z,A-Z,0-9,ñ,Ñ, ]+") == false) {
			return "Datos erroneos, vuelva a intentarlo";
		} else {
			return null;
		}
	}
	
	
	@MemberOrder(sequence = "4")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	private TipoDocumento tipoDocumento;
	public TipoDocumento getTipoDocumento() {return tipoDocumento;}
	public void setTipoDocumento(TipoDocumento tipoDocumento) {this.tipoDocumento = tipoDocumento;}

	@MemberOrder(sequence = "5")
    @Column(allowsNull="false", length = NAME_LENGTH)
    @Property(domainEvent = NameDomainEvent.class)
	private String documento;
	public String getDocumento() {return documento;}
	public void setDocumento(String documento) {this.documento = documento;}
	

//    public TranslatableString validateName(final String name) {
//        return name != null && name.contains("!")? TranslatableString.tr("Exclamation mark is not allowed"): null;
//    }





	public static class DeleteDomainEvent extends ActionDomainEvent<Jugador> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void delete() {
        repositoryService.remove(this);
    }


    @Override
    public int compareTo(final Jugador other) {
        return ObjectContracts.compare(this, other, "nombre");
    }


    @javax.inject.Inject
    RepositoryService repositoryService;
    
    @javax.inject.Inject
    Jugadores jugadores;

}
