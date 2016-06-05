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

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

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
                name = "buscarPorNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.jugador.Jugador "
                        + "WHERE nombre.indexOf(:nombre) >= 0 ")
})
@javax.jdo.annotations.Unique(name="Jugador_nombre_UNQ", members = {"nombre"})
@DomainObject
@DomainObjectLayout
public class Jugador implements Comparable<Jugador> {

    public static final int NAME_LENGTH = 40;
    
    
    public TranslatableString title() {
        return TranslatableString.tr("{nombre}", "nombre", getNombre());
    }

    
    public String iconName(){
    	return "Jugador";
    }

    
//    public String title(){
//        return getNombre();
//    }




    public static class NameDomainEvent extends PropertyDomainEvent<Jugador,String> {}
    
    
    @javax.jdo.annotations.Column(
            allowsNull="false",
            length = NAME_LENGTH
    )
    @Property(
        domainEvent = NameDomainEvent.class
    )
    //@Getter @Setter
    private String nombre;
    public String getNombre() {return nombre;}
    public void setNombre(final String nombre) {this.nombre = nombre;}
    
    
    
    

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
        return ObjectContracts.compare(this, other, "name");
    }


    @javax.inject.Inject
    RepositoryService repositoryService;
    
    @javax.inject.Inject
    Jugadores jugadores;

}
