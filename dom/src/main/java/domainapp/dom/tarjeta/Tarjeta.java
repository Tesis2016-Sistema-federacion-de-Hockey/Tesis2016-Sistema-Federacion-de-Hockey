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

package domainapp.dom.tarjeta;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.IsisApplibModule.ActionDomainEvent;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;
import org.joda.time.DateTime;

import domainapp.dom.jugador.Jugador;
import domainapp.dom.partido.Partido;


@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Tarjeta"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="tarjeta_id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "traerTodas", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.tarjeta.Tarjeta")
})
@javax.jdo.annotations.Unique(name="Tarjeta_nombre_UNQ", members = {"minutoTarjeta","tipoTarjeta","partido","jugador"})
@DomainObject(bounded=true)
@DomainObjectLayout
public class Tarjeta implements Comparable<Tarjeta>{
	
    public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre", this.getTipoTarjeta().getNombre());
	}
	
    public String iconName(){
    	return (getTipoTarjeta()==TipoTarjeta.VERDE)? "verde":"amarilla";
    }

	
	public static class NameDomainEvent extends PropertyDomainEvent<Tarjeta,String>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	
	//TIPO DE TARJETA
	@MemberOrder(sequence = "1")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	@PropertyLayout(named="Tipo")
	private TipoTarjeta tipoTarjeta;
	public TipoTarjeta getTipoTarjeta() {return tipoTarjeta;}
	public void setTipoTarjeta(TipoTarjeta tipoTarjeta) {this.tipoTarjeta = tipoTarjeta;}
	
	//MINUTO
	@MemberOrder(sequence = "2")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	@PropertyLayout(named="Minuto")
	private int minutoTarjeta;
	public int getMinutoTarjeta() {return minutoTarjeta;}
	public void setMinutoTarjeta(int minutoTarjeta) {this.minutoTarjeta = minutoTarjeta;}

	//PARTIDO
	@MemberOrder(sequence = "3")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	private Partido partido;
	public Partido getPartido() {return partido;}
	public void setPartido(Partido partido) {this.partido = partido;}

	//JUGADOR
	@MemberOrder(sequence = "4")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	private Jugador jugador;
	public Jugador getJugador() {return jugador;}
	public void setJugador(Jugador jugador) {this.jugador = jugador;}
	
	//HORARIO DE CUANDO SE SACO LA TARJETA
	private DateTime fechaHora;
	public DateTime getFechaHora() {
		
		fechaHora=partido.getFechaHora();return fechaHora;
		
	}
 
	public static class DeleteDomainEvent extends ActionDomainEvent<Tarjeta> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	
	@Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
	public void delete() {
		partido.getTarjetas().remove(this);
		jugador.getTarjetas().remove(this);
        repositoryService.remove(this);
    }
	
	@javax.inject.Inject
    RepositoryService repositoryService;
	
	@javax.inject.Inject
	TarjetaServicio tarjetaServicio;
	
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(final Tarjeta o) {
		return ObjectContracts.compare(this, o, "fechaHora", "partido", "minutoTarjeta","tipoTarjeta", "jugador");
	}
}