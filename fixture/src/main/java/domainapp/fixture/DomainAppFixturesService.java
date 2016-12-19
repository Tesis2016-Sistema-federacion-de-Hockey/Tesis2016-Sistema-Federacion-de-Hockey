package domainapp.fixture;

import java.util.List;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.fixturescripts.FixtureResult;
import org.apache.isis.applib.fixturescripts.FixtureScripts;

import domainapp.fixture.scenarios.JugadorFixture;
import domainapp.fixture.scenarios.TemporadaFixture;

@DomainService
@DomainServiceLayout(
        named="Ejemplo de Carga",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        menuOrder = "22"
)
public class DomainAppFixturesService extends FixtureScripts {

    @SuppressWarnings("deprecation")
	public DomainAppFixturesService() {
        super("domainapp.fixture");
    }
       
//    @MemberOrder(sequence="20")
//    public Object instalarFixturesClub() {
//        final List<FixtureResult> Club = findFixtureScriptFor(ClubFixture.class).run(null);
//        return Club.get(0).getObject();
//    }
    
//    @MemberOrder(sequence="30")
    @ActionLayout(hidden=Where.EVERYWHERE)
    public Object instalarFixturesJugador() {
        final List<FixtureResult> Jugador = findFixtureScriptFor(JugadorFixture.class).run(null);
        return Jugador.get(0).getObject();
    }

//    @MemberOrder(sequence="40")
    @ActionLayout(hidden=Where.EVERYWHERE)
    public Object instalarFixturesTemporada() {
        final List<FixtureResult> Temporada = findFixtureScriptFor(TemporadaFixture.class).run(null);
        return Temporada.get(0).getObject();
    }
    
//    
//	@MemberOrder(sequence="80")
//    public String BorrarBD()
//    {
//		final List<FixtureResult> Borrar = findFixtureScriptFor(GenericTearDownFixture.class).run(null);
//		
//		return "Se ha completado la operacion. Toda la DB ah sido borrada.";
//    }
//	
	  @MemberOrder(sequence="99")
	  @ActionLayout(named="Cargar Datos de Prueba")
	    public String IntstalarTodosLosFixtures()
	    {
//	    	this.instalarFixturesClub();
	    	
	    	this.instalarFixturesJugador();

	    	this.instalarFixturesTemporada();
	    	
//	    	this.instalarFixturesCuotasClub();
	    	
//	    	this.instalarFixturesTorneo();
//	    	
//	    	this.instalarFixturesDivision();
//	    	
//	    	this.instalarFixturesEquipo();
//	    		    	
	    	return "FIXTURES INSTALADOS";
	    }
 
}