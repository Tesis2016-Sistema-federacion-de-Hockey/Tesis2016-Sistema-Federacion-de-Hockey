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

package domainapp.dom.pagojugador;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.IsisApplibModule.ActionDomainEvent;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.cuotajugador.CuotaJugador;
import domainapp.dom.jugador.Jugador;
import domainapp.dom.pago.Pago;
import domainapp.dom.torneo.Torneo.NameDomainEvent;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "PagoJugador"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="pagoJugador_id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "traerTodos", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.pagojugador.PagoJugador"),
    @javax.jdo.annotations.Query(
            name = "listarPagosPorJugador", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.pagojugador.PagoJugador "
            		+ "WHERE (jugador == :jugador)"),
    @javax.jdo.annotations.Query(
            name = "listarPagosPorJugadorYCuota", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.pagojugador.PagoJugador "
            		+ "WHERE (jugador == :jugador) && (cuotaJugador == :cuotaJugador)"),
    @javax.jdo.annotations.Query(
            name = "buscarPagoJugador", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.pagojugador.PagoJugador "
            		+ "WHERE nroRecibo == :nroRecibo")
})
@javax.jdo.annotations.Unique(name="PagoJugador_UNQ", members = {"nroRecibo"})
@DomainObject(bounded=true)
@DomainObjectLayout
public class PagoJugador extends Pago implements Comparable<PagoJugador>{

	public TranslatableString title() {
		return TranslatableString.tr("{nroRecibo}", "nroRecibo",
				"Pago: " + this.getNroRecibo());
	}
	
	public String iconName(){return "PagoJugador";}
	
	//JUGADOR
	@MemberOrder(sequence = "14")
	@Column(allowsNull = "false")
	@PropertyLayout(named="Jugador")
	private Jugador jugador;	
	public Jugador getJugador() {return jugador;}
	public void setJugador(Jugador jugador) {this.jugador = jugador;}
	
	//CUOTA JUGADOR
	@MemberOrder(sequence = "5")
    @Column(allowsNull="false")
	@Property(domainEvent = NameDomainEvent.class)
	@PropertyLayout(named="Cuota")
	private CuotaJugador cuotaJugador;
	public CuotaJugador getCuotaJugador() {return cuotaJugador;}
	public void setCuotaJugador(final CuotaJugador cuotaJugador) {this.cuotaJugador = cuotaJugador;}

	public static class DeleteDomainEvent extends ActionDomainEvent<PagoJugador> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	
	@Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
	@ActionLayout(named="Eliminar Pago")
	public void delete() {
        repositoryService.remove(this);
    }	
	
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(final PagoJugador other) {
		// TODO Auto-generated method stub
		return ObjectContracts.compare(this, other, "nroRecibo");
	}
	
	@javax.inject.Inject
    RepositoryService repositoryService;
}