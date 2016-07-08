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

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.club.Club;
import domainapp.dom.domicilio.Domicilio;
import domainapp.dom.estado.Estado;
import domainapp.dom.persona.Persona;
import domainapp.dom.sector.Sector;




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
                        + "WHERE documento.indexOf(:documento) >= 0 "),
        @javax.jdo.annotations.Query(
        		name = "listarJugadoresActivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.jugador.Jugador "
                        + "WHERE estado=='Activo'"),
        @javax.jdo.annotations.Query(
        		name = "listarJugadoresInactivos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.jugador.Jugador "
                        + "WHERE estado=='Inactivo'")
        		
})
@javax.jdo.annotations.Unique(name="Jugador_documento_UNQ", members = {"documento"})
@DomainObject(bounded=true)
@DomainObjectLayout
public class Jugador extends Persona implements Comparable<Jugador> {

    public static final int NAME_LENGTH = 40;
    
    public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				"Jugador: " + this.getApellido() + ", " + this.getNombre());
	}
    
    //public String iconName(){return "jugador";}
    
    public String iconName(){
    	return (getSector()==Sector.DAMAS)? "dama":"caballero";
    }
    
    public String cssClass(){
    	return (getEstado()==Estado.ACTIVO)? "activo":"inactivo";
    }
    
    public static class NameDomainEvent extends PropertyDomainEvent<Jugador,String> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
    
	//SECTOR
    @MemberOrder(sequence = "0")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private Sector sector;
	public Sector getSector() {return sector;}
	public void setSector(final Sector sector) {this.sector = sector;}

	//FICHA
    @MemberOrder(sequence = "1")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private String ficha;
	public String getFicha() {return ficha;}
	public void setFicha(final String ficha) {this.ficha = ficha;}
	
	//NUMERO DE CAMISETA
    @MemberOrder(sequence = "12")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private int numeroCamiseta;
	public int getNumeroCamiseta() {return numeroCamiseta;}
	public void setNumeroCamiseta(int numeroCamiseta) {this.numeroCamiseta = numeroCamiseta;}
	
	//DOMICILIO
	@MemberOrder(sequence = "13")
	@Property(editing = Editing.ENABLED)	
	@Column(name="DOMICILIO_ID")	
	private Domicilio domicilio;	
	public Domicilio getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
	
	//CLUB
	@MemberOrder(sequence = "14")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "true")
	private Club club;	
	public Club getClub() {
		return club;
	}
	public void setClub(Club club) {
		this.club = club;
	}
	
	
	
	
	
	
	
	
	
	

//	public static class DeleteDomainEvent extends ActionDomainEvent<Jugador> {}
    
//	@Action(
//            domainEvent = DeleteDomainEvent.class,
//            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
//    )
//    public void delete() {
//        repositoryService.remove(this);
//    }

    

	
	@SuppressWarnings("deprecation")
	@Override
    public int compareTo(final Jugador other) {
        return ObjectContracts.compare(this, other, getDocumento());
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
    
    @javax.inject.Inject
    Jugadores jugadores;
}