package domainapp.dom.cuotajugador;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;

import domainapp.dom.cuota.Cuota;
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
public class CuotaJugador extends Cuota{

	public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				"Cuota: " + this.getDetalle());
	}
	
	//LISTADO DE JUGADORES
  	@MemberOrder(sequence = "5")
  	@Persistent(table="cuotajugadores_jugadores")
  	@Join(column="cuotaJugador_id")
  	@Element(column="jugador_id")
  	private SortedSet<Jugador> jugadores=new TreeSet<Jugador>();
  	public SortedSet<Jugador> getJugadores() {return jugadores;}
  	public void setJugadores(final SortedSet<Jugador> jugadores) {this.jugadores = jugadores;}
	
	
	@javax.inject.Inject
    RepositoryService repositoryService;
}