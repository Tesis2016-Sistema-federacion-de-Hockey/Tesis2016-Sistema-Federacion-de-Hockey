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

        crearJugador(Sector.DAMAS, "113", "Julia", "Blanco", TipoDocumento.DNI, "27883920",
        		LocalDate.now(), Estado.ACTIVO, "julia.blanco@gmail.com", "Dr. Ramon", "15", "PB", "B",
        		"4483131", "2994523497", null, null, executionContext);
        crearJugador(Sector.DAMAS, "147", "Adriana", "Petazzi", TipoDocumento.DNI, "28996773",
        		LocalDate.now(), Estado.ACTIVO, "adriana.petazzi@hotmail.com.ar", "Avenida Argentina", "1050", "PB", "D",
        		"4471223", "2996013887", null, null, executionContext);
        crearJugador(Sector.DAMAS, "221", "Luciana", "Aimar", TipoDocumento.DNI, "31334488",
        		LocalDate.now(), Estado.ACTIVO, "luciana.aimar@gmail.com", "Elordi", "307", "", "",
        		"4489292", "2995183662", null, null, executionContext);
        crearJugador(Sector.DAMAS, "732", "Carla", "Camacho", TipoDocumento.DNI, "21998362",
        		LocalDate.now(), Estado.ACTIVO, "carla.camacho73@yahoo.com", "Rio Diamante", "667", "", "",
        		"", "2994773722", null, null, executionContext);
        crearJugador(Sector.DAMAS, "199", "Eva", "Morales", TipoDocumento.DNI, "35772957",
        		LocalDate.now(), Estado.ACTIVO, "eva.morales@hotmal.com", "Tucuman", "867", "", "",
        		"", "299699330", null, null, executionContext);

        crearJugador(Sector.CABALLEROS, "670", "Enzo", "Perez", TipoDocumento.DNI, "32338746",
        		LocalDate.now(), Estado.ACTIVO, "enzo.perez@gmail.com", "Formosa", "1671", "", "",
        		"4431786", "2995141516", null, null, executionContext);
        crearJugador(Sector.CABALLEROS, "51", "Ricardo", "Rubiolo", TipoDocumento.DNI, "33276930",
        		LocalDate.now(), Estado.ACTIVO, "enzo.perez@gmail.com", "Formosa", "1671", "", "",
        		"4477382", "299673992", null, null, executionContext);
        crearJugador(Sector.CABALLEROS, "811", "Joaquin", "Lopez", TipoDocumento.DNI, "26773933",
        		LocalDate.now(), Estado.ACTIVO, "piojo.lopez83@gmail.com", "Colonia Alemana", "995", "", "",
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