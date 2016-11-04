package domainapp.fixture.scenarios;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.joda.time.LocalDate;

import domainapp.dom.club.Club;
import domainapp.dom.equipo.Equipo;
import domainapp.dom.estado.Estado;
import domainapp.dom.jugador.Jugador;
import domainapp.dom.jugador.JugadorServicio;
import domainapp.dom.sector.Sector;
import domainapp.dom.tipodocumento.TipoDocumento;

public class JugadorFixture extends FixtureScript {
	public JugadorFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

    	crearJugador(Sector.DAMAS, "220", "Luciana", "Aimar", TipoDocumento.DNI, "31334488",
        		LocalDate.now(), Estado.ACTIVO, "luciana.aimar@gmail.com", "Elordi", "307", "", "",
        		"4489292", "2995183662", null, null, executionContext);
        crearJugador(Sector.DAMAS, "221", "Carla", "Rebequi", TipoDocumento.DNI, "21998362",
        		LocalDate.now(), Estado.ACTIVO, "carla.rebequi@yahoo.com", "Rio Diamante", "667", "", "",
        		"", "2994773722", null, null, executionContext);
        crearJugador(Sector.DAMAS, "222", "Magui", "Aicega", TipoDocumento.DNI, "35772957",
        		LocalDate.now(), Estado.ACTIVO, "magui.aicega@hotmal.com", "Tucuman", "867", "", "",
        		"", "299699330", null, null, executionContext);

        
        
        
        crearJugador(Sector.CABALLEROS, "300", "Diego", "Roger", TipoDocumento.DNI, "32338746",
        		LocalDate.now(), Estado.ACTIVO, "diego.roger@gmail.com", "Formosa", "1671", "", "",
        		"4431786", "2995141516", null, null, executionContext);
        crearJugador(Sector.CABALLEROS, "301", "Joaquin", "Martinez", TipoDocumento.DNI, "33276930",
        		LocalDate.now(), Estado.ACTIVO, "j.m@gmail.com", "Formosa", "1671", "", "",
        		"4477382", "299673992", null, null, executionContext);
        crearJugador(Sector.CABALLEROS, "302", "Alejandro", "McCormack", TipoDocumento.DNI, "26773933",
        		LocalDate.now(), Estado.ACTIVO, "m.m@gmail.com", "Leloir", "995", "", "",
        		"4483002", "2995907683", null, null, executionContext);
        
    }
	
    @SuppressWarnings("deprecation")
    	private Jugador crearJugador(
    			final Sector sector,
                final String ficha,
                final String nombre,
                final String apellido,
                final TipoDocumento tipo,
                final String documento,
                final LocalDate fechaNacimiento,
                final Estado estado,
                final String email,
                final String calle,
                final String numero,
                final String piso,
                final String departamento,
                final String telefono,
                final String celular,
                final Club club,
                final Equipo equipo
        		,
                ExecutionContext executionContext) {
    	return executionContext.add(this, jugadorServicio.crearJugador(
        		 sector, ficha, nombre, apellido, tipo, documento, fechaNacimiento, estado, email,
        		 calle, numero, piso, departamento, telefono, celular, club, equipo));
    }
       
    @javax.inject.Inject
    private JugadorServicio jugadorServicio;
}