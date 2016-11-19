package domainapp.dom.cuotajugador;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.IsisApplibModule.ActionDomainEvent;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import domainapp.dom.cuota.Cuota;
import domainapp.dom.jugador.Jugador;
import domainapp.dom.jugador.JugadorServicio;
import domainapp.dom.pagojugador.PagoJugador;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "CuotaJugador")
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="cuotaJugador_id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "traerTodos", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.cuotajugador.CuotaJugador"),
    @javax.jdo.annotations.Query(
            name = "traerCuotaJugador", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.cuotajugador.CuotaJugador "
                    + "WHERE this.listaJugadores.contains(:jugador) ")
})
@javax.jdo.annotations.Unique(name="CuotaJugador_UNQ", members = {"vencimiento"})
@DomainObject(bounded=true)
@DomainObjectLayout
public class CuotaJugador extends Cuota implements Comparable<CuotaJugador> {

	public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				"Cuota: " + this.getVencimiento());
	}
	
	public String iconName(){return "CuotaJugador";}
	
	//LISTA DE PAGOS DE JUGADOR
	@MemberOrder(sequence = "5.1")
	@Persistent(mappedBy="cuotaJugador", dependentElement="true")
	@CollectionLayout(named="Lista de Pagos")
	private SortedSet<PagoJugador> listaPagosJugador = new TreeSet<PagoJugador>();
	public SortedSet<PagoJugador> getListaPagosJugador() {return listaPagosJugador;}
	public void setListaPagosJugador(SortedSet<PagoJugador> listaPagosJugador) {this.listaPagosJugador = listaPagosJugador;}

	//LISTA DE JUGADORES
  	@MemberOrder(sequence = "6.1")
  	@Persistent(mappedBy = "cuotasJugador")
  	@CollectionLayout(named="Lista de Jugadores")
  	private SortedSet<Jugador> listaJugadores=new TreeSet<Jugador>();
  	public SortedSet<Jugador> getListaJugadores() {return listaJugadores;}
	public void setListaJugadores(SortedSet<Jugador> listaJugadores) {this.listaJugadores = listaJugadores;}

	//METODO PARA AGREGAR CUOTA	A UN JUGADOR	
	@MemberOrder(sequence = "6")
	@ActionLayout(
			describedAs="Asigna un JUGADOR a la lista de jugadores que le corresponde pagar esta cuota",
			named="Agregar Jugador", cssClassFa="fa fa-plus")
	public CuotaJugador agregarJugadorACuota(final Jugador e) {
		if(e == null || listaJugadores.contains(e)) return this;
		listaJugadores.add(e);
		e.getCuotasJugador().add(this);
		return this;
	}
	
	//METODO PARA QUITAR CUOTA A UN JUGADOR	
	@MemberOrder(sequence = "7")
	@ActionLayout(
			describedAs="Desasigna un JUGADOR de la lista de jugadores que no le corresponde pagar esta cuota",
			named="Quitar Jugador", cssClassFa="fa fa-minus")
	public CuotaJugador quitarJugadorACuota(final Jugador e) {
		if(e == null || !listaJugadores.contains(e)) return this;
		listaJugadores.remove(e);
		e.getCuotasJugador().remove(this);
		return this;
	}
	
	public List<Jugador> choices0AgregarJugadorACuota(){		
		return repositoryService.allMatches(Jugador.class, new Predicate<Jugador>() {
			@Override
			public boolean apply(Jugador jug) {				
				return (jugadorServicio.listarJugadoresActivos().contains(jug)&&!listaJugadores.contains(jug))?true:false;
			}
		});
	}
	
	public List<Jugador> choices0QuitarJugadorACuota(){
		
			return Lists.newArrayList(getListaJugadores());
	}
	
	//METODO PARA AGREGAR TODAS LAS CUOTAS AL JUGADOR
	@MemberOrder(sequence = "8")
	@ActionLayout(
			describedAs="Asigna todos los jugadores a la lista de jugadores que deben pagar esta cuota",
			named="Agregar TODOS los jugadores", cssClassFa="fa fa-thumbs-o-up")
	public CuotaJugador agregarTodas(){
		for (Iterator<?> it=jugadorServicio.listarTodosLosJugadores().iterator();it.hasNext();){
			Jugador jug=((Jugador)it.next());
			jug.getCuotasJugador().add(this);
		}
		return this;
	}

	public static class DeleteDomainEvent extends ActionDomainEvent<CuotaJugador> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
	
	@Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
	@ActionLayout(named="Eliminar Cuota")
	public void delete() {
        repositoryService.remove(this);
    }
	
	public String disableDelete(){
		
		if(!listaJugadores.isEmpty()) return "La lista de jugadores debe estar vacia.";
		
		else if (!listaPagosJugador.isEmpty()) return "La lista de pagos debe estar vacia.";
		
		return "";
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(final CuotaJugador other) {
		// TODO Auto-generated method stub
		return ObjectContracts.compare(this, other, "temporada", "vencimiento");
	}
	
	@javax.inject.Inject
    RepositoryService repositoryService;
    
	@javax.inject.Inject
    JugadorServicio jugadorServicio;	
}