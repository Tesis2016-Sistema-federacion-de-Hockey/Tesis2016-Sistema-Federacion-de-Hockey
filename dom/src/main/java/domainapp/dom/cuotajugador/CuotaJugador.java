package domainapp.dom.cuotajugador;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.cuota.Cuota;
import domainapp.dom.cuotaclub.CuotaClub;
import domainapp.dom.jugador.Jugador;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "CuotaJugador"
)
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
                    + "FROM domainapp.dom.cuotajugador.CuotaJugador")
})
public class CuotaJugador extends Cuota implements Comparable<CuotaJugador> {

	public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				"Cuota de Jugador: " + this.getNombre());
	}
	
	public String iconName(){return "CuotaJugador";}
	
	//LISTA DE JUGADORES
  	@MemberOrder(sequence = "5")
  	@Persistent(mappedBy = "cuotasJugador")
  	@CollectionLayout(named="Lista de Jugadores que deben Pagar")
  	private SortedSet<Jugador> listaJugadores=new TreeSet<Jugador>();
  	public SortedSet<Jugador> getListaJugadores() {return listaJugadores;}
	public void setListaJugadores(SortedSet<Jugador> listaJugadores) {this.listaJugadores = listaJugadores;}

	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(final CuotaJugador other) {
		// TODO Auto-generated method stub
		return ObjectContracts.compare(this, other, "nombre");
	}
	
	@javax.inject.Inject
    RepositoryService repositoryService;
}