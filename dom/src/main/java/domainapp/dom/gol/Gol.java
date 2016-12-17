package domainapp.dom.gol;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import domainapp.dom.equipo.Equipo;
import domainapp.dom.jugador.Jugador;
import domainapp.dom.partido.Partido;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Gol"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="gol_id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Unique(name="Gol_UNQ", members = {"jugador", "equipo", "partido", "minuto"})
public class Gol implements Comparable<Gol>{
	
	public TranslatableString title() {
		
		return TranslatableString.tr("{minuto}", "minuto", "Minuto: " + this.getMinuto());
	}
	
	public String iconName(){return "gol";}

	//JUGADOR
    @MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	private Jugador jugador;
	public Jugador getJugador() {return jugador;}
	public void setJugador(Jugador jugador) {this.jugador = jugador;}
    
	//EQUIPO
    @MemberOrder(sequence = "2")
	@Column(allowsNull = "false")
	private Equipo equipo;
	public Equipo getEquipo() {return equipo;}
	public void setEquipo(Equipo equipo) {this.equipo = equipo;}
	
	//PARTIDO
    @MemberOrder(sequence = "3")
	@Column(allowsNull = "false")
	private Partido partido;
	public Partido getPartido() {return partido;}
	public void setPartido(Partido partido) {this.partido = partido;}
	
	//EQUIPO CONTRARIO
    @MemberOrder(sequence = "4")
	@Column(allowsNull = "false")
	private Equipo equipoContrario;
	public Equipo getEquipoContrario() {return equipoContrario;}
	public void setEquipoContrario(Equipo equipoContrario) {this.equipoContrario = equipoContrario;}
	
	//MINUTO
	@MemberOrder(sequence = "5")
	@Column(allowsNull = "false")
	private int minuto;
	public int getMinuto() {return minuto;}
	public void setMinuto(int minuto) {this.minuto = minuto;}

	@javax.inject.Inject
    RepositoryService repositoryService;
	
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(Gol o) {
		return ObjectContracts.compare(this, o, "minuto", "partido", "jugador");
	}
}