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

import org.joda.time.LocalDate;

import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.google.common.base.Predicate;

import domainapp.dom.domicilio.Domicilio;
import domainapp.dom.estado.Estado;
import domainapp.dom.sector.Sector;
import domainapp.dom.tipodocumento.TipoDocumento;

@SuppressWarnings("deprecation")
@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = Jugador.class
)
@DomainServiceLayout(
        menuOrder = "2"
)
public class Jugadores{

    public TranslatableString title() {return TranslatableString.tr("Jugadores");}

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence = "1")
    public List<Jugador> listarTodosLosJugadores() {
        return repositoryService.allInstances(Jugador.class);
    }
    
    @MemberOrder(sequence = "2")
	public List<Jugador> listarJugadoresActivos() {
		return repositoryService.allMatches(Jugador.class, new Predicate<Jugador>() {

			@Override
			public boolean apply(Jugador input) {
				// TODO Auto-generated method stub
				return input.getEstado() == Estado.ACTIVO ? true : false;
			}
		});
	}
    
    @MemberOrder(sequence = "3")
	public List<Jugador> listarJugadoresInactivos() {
		return repositoryService.allMatches(Jugador.class, new Predicate<Jugador>() {

			@Override
			public boolean apply(Jugador input) {
				// TODO Auto-generated method stub
				return input.getEstado() == Estado.INACTIVO ? true : false;
			}
		});
	}
    
    
    public static class CreateDomainEvent extends ActionDomainEvent<Jugadores> {
        public CreateDomainEvent(final Jugadores source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @ActionLayout(
    		cssClassFa="fa fa-plus-square"
    )
    @MemberOrder(sequence = "3")
    public Jugador crear(
            final @ParameterLayout(named="Sector") Sector sector,
            final @ParameterLayout(named="Ficha") String ficha,
            final @ParameterLayout(named="Nombre") String nombre,
            final @ParameterLayout(named="Apellido") String apellido,
            final @ParameterLayout(named="Tipo de Documento") TipoDocumento tipoDocumento,
            final @ParameterLayout(named="Documento") String documento,
            final @ParameterLayout(named="Fecha de Nacimiento") LocalDate fechaNacimiento,
            final @ParameterLayout(named="Estado") Estado estado,
            final @ParameterLayout(named="Email") String email,
            final @ParameterLayout(named="Calle") String calle,
            final @ParameterLayout(named="Numero") String numero,
            final @ParameterLayout(named="Piso") String piso,
            final @ParameterLayout(named="Departamento") String departamento,
            final @ParameterLayout(named="Telefono") String telefono,
            final @ParameterLayout(named="Celular") String celular
            
    		){
        final Jugador obj = repositoryService.instantiate(Jugador.class);
        final Domicilio domicilio=new Domicilio();
        domicilio.setCalle(calle);
        domicilio.setNumero(numero);
        domicilio.setPiso(piso);
        domicilio.setDepartamento(departamento);
        obj.setSector(sector);
        obj.setFicha(ficha);
        obj.setNombre(nombre);
        obj.setApellido(apellido);
        obj.setTipoDocumento(tipoDocumento);
        obj.setDocumento(documento);
        obj.setFechaNacimiento(fechaNacimiento);
        obj.setEstado(estado);
        obj.setEmail(email);
        obj.setTelefono(telefono);
        obj.setCelular(celular);
        obj.setDomicilio(domicilio);
        repositoryService.persist(obj);
        return obj;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;
}
