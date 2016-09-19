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

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.IsisApplibModule.ActionDomainEvent;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.club.Club;
import domainapp.dom.cuota.Cuota;
import domainapp.dom.cuotajugador.CuotaJugador;
import domainapp.dom.domicilio.Domicilio;
import domainapp.dom.equipo.Equipo;
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
         column="jugador_id")
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "traerTodos", language = "JDOQL",
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
                        + "WHERE estado=='Inactivo'"),
        @javax.jdo.annotations.Query(
        		name = "listarJugadoresSinClub", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.jugador.Jugador "
                        + "WHERE club_club_id_OID==1"),
        @javax.jdo.annotations.Query(
        		name = "listarJugadoresActivosSegunClub", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.jugador.Jugador "
                        + "WHERE (club == :club) && (estado == 'ACTIVO')")
        		
})
@javax.jdo.annotations.Unique(name="Jugador_ficha_UNQ", members = {"ficha"})
@DomainObject(bounded=true)		//autoCompleteRepository = JugadorServicio.class, autoCompleteAction = "buscarJugador")
@DomainObjectLayout
public class Jugador extends Persona implements Comparable<Jugador> {

    public static final int NAME_LENGTH = 40;
    
    public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				"Jugador: " + this.getApellido() + ", " + this.getNombre());
	}
    
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
    @MemberOrder(sequence = "1")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "true")
	private Sector sector;
	public Sector getSector() {return sector;}
	public void setSector(final Sector sector) {this.sector = sector;}

	//FICHA
    @MemberOrder(sequence = "2")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "false")
	private String ficha;
	public String getFicha() {return ficha;}
	public void setFicha(final String ficha) {this.ficha = ficha;}
	
	//NUMERO DE CAMISETA
    @MemberOrder(sequence = "12")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "true")
	private String numeroCamiseta;
	public String getNumeroCamiseta() {return numeroCamiseta;}
	public void setNumeroCamiseta(final String numeroCamiseta) {this.numeroCamiseta = numeroCamiseta;}
	
	//DOMICILIO
	@MemberOrder(sequence = "13")
	@Property(editing = Editing.ENABLED, hidden=Where.ALL_TABLES)	
	@Column(name="domicilio_id")	
	private Domicilio domicilio;	
	public Domicilio getDomicilio() {return domicilio;}
	public void setDomicilio(final Domicilio domicilio) {this.domicilio = domicilio;}
	
	//CLUB	
	@MemberOrder(sequence = "14")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "true")
	private Club club;
	public Club getClub() {return club;}
	public void setClub(final Club club) {this.club = club;}
	
	//EQUIPO	
	@MemberOrder(sequence = "15")
	@Property(editing = Editing.ENABLED)
	@Column(allowsNull = "true")
	private Equipo equipo;
	public Equipo getEquipo() {return equipo;}
	public void setEquipo(final Equipo equipo) {this.equipo = equipo;}
	
	//CUOTAS
	@Persistent(mappedBy = "jugadores", dependentElement = "true")
	private SortedSet<CuotaJugador> cuotas = new TreeSet<CuotaJugador>();	
	public SortedSet<CuotaJugador> getCuotas() {return cuotas;}
	public void setCuotas(SortedSet<CuotaJugador> cuotas) {this.cuotas = cuotas;}

	public static class DeleteDomainEvent extends ActionDomainEvent<Jugador> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
    
	@Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void delete() {
        repositoryService.remove(this);
    }
	
    @javax.inject.Inject
    RepositoryService repositoryService;
    
//    @javax.inject.Inject
//    JugadorServicio jugadorServicio;


	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(final Jugador o) {
		// TODO Auto-generated method stub
		return org.apache.isis.applib.util.ObjectContracts.compare(this, o, "apellido", "nombre");
	}
}