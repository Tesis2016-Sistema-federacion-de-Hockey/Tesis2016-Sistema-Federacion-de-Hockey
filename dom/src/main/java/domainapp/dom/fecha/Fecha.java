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

package domainapp.dom.fecha;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.division.Division;
import domainapp.dom.partido.Partido;


@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Fecha"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="fecha_id")
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "traerTodos", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.fecha.Fecha")
})
@javax.jdo.annotations.Unique(name="Fecha_nroFecha_UNQ", members = {"nroFecha","division"})
@DomainObject(bounded=true)
@DomainObjectLayout
public class Fecha implements Comparable<Fecha>{
	
    public TranslatableString title() {
		return TranslatableString.tr("{nroFecha}", "nroFecha",
				"Fecha Nro: " + this.getNroFecha()
				);
	}
	
	public String iconName(){return "fecha";}
	
	public static class NameDomainEvent extends PropertyDomainEvent<Fecha,String>{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	
	
    //NUMERO DE FECHA
    @MemberOrder(sequence = "1")
	@Property(editing = Editing.DISABLED)
	@Column(allowsNull = "false")
	private int nroFecha;
	public int getNroFecha() {return nroFecha;}
	public void setNroFecha(int nroFecha) {this.nroFecha = nroFecha;}

	//ESTADO DE LA FECHA, SI ESTA COMPLETA O INCOMPLETA
	@MemberOrder(sequence = "2")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class)
	private boolean completa;
	public boolean isCompleta() {return completa;}
	public void setCompleta(boolean completa) {this.completa = completa;}

	//DIVISION
	@MemberOrder(sequence = "3")
    @Column(allowsNull="false")
    @Property(domainEvent = NameDomainEvent.class, editing = Editing.DISABLED)
	private Division division;
	public Division getDivision() {return division;}
	public void setDivision(Division division) {this.division = division;}

	//LISTA DE PARTIDOS DE UNA FECHA
	@MemberOrder(sequence = "4")
	@Persistent(mappedBy="fecha", dependentElement="true")
	@CollectionLayout(named="Partidos")
	private SortedSet<Partido> listaPartidos=new TreeSet<Partido>();
	public SortedSet<Partido> getListaPartidos() {return listaPartidos;}
	public void setListaPartidos(SortedSet<Partido> listaPartidos) {this.listaPartidos = listaPartidos;}
	
	

//	public static class DeleteDomainEvent extends ActionDomainEvent<Fecha> {
//
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = 1L;}
//	
//	@Action(
//            domainEvent = DeleteDomainEvent.class,
//            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
//    )
//	public void delete() {
//        repositoryService.remove(this);
//    }
	
//	public String disableDelete(){
//		return !listaPartidos.isEmpty()?"La lista de partidos debe estar vacia.":null;
//	}
	
	@javax.inject.Inject
    RepositoryService repositoryService;
	
	
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(final Fecha o) {
		return ObjectContracts.compare(this, o, "nroFecha");
	}
}