package domainapp.fixture.scenarios;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.dom.club.Club;
import domainapp.dom.club.ClubServicio;
import domainapp.dom.division.Division;
import domainapp.dom.division.DivisionServicio;
import domainapp.dom.equipo.EquipoServicio;
import domainapp.dom.estado.Estado;
import domainapp.dom.temporada.Temporada;
import domainapp.dom.temporada.TemporadaServicio;
import domainapp.dom.torneo.Torneo;
import domainapp.dom.torneo.TorneoServicio;

public class TemporadaFixture extends FixtureScript {
	public TemporadaFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

//    	BorrarDBClub(executionContext);
        
    	Temporada tempo01=creaTemporada("2016", Estado.ACTIVO, "Sin observaciones", executionContext);
    	
    	Torneo tor01=torneoServicio.crearTorneo("APERTURA", Estado.ACTIVO, tempo01, "Sin observaciones");
    	
    	
    	torneoServicio.crearTorneo("CLAUSURA", Estado.ACTIVO, tempo01, "Sin observaciones");
    	torneoServicio.crearTorneo("PISTA INVIERNO", Estado.ACTIVO, tempo01, "Sin observaciones");
    	torneoServicio.crearTorneo("SEVEN", Estado.ACTIVO, tempo01, "Sin observaciones");
   	
    	creaTemporada("2015", Estado.INACTIVO, "Sin observaciones", executionContext);
    	creaTemporada("2014", Estado.INACTIVO, "Sin observaciones", executionContext);
    	
    	Division divi01=divisionServicio.crearDivision("PRIMERA DAMAS", Estado.ACTIVO, tor01, "Todos contra todos", 3, 1, 0);
    	Division divi02=divisionServicio.crearDivision("PRIMERA CABALLEROS", Estado.ACTIVO, tor01, "Todos contra todos", 3, 1, 0);
    	divisionServicio.crearDivision("7MA DAMAS", Estado.ACTIVO, tor01, "Todos contra todos", 3, 1, 0);
    	divisionServicio.crearDivision("6TA DAMAS", Estado.ACTIVO, tor01, "Todos contra todos", 3, 1, 0);
    	divisionServicio.crearDivision("5TA DAMAS", Estado.ACTIVO, tor01, "Todos contra todos", 3, 1, 0);

    	Club club01=clubServicio.crearClub("CAI","Club Atletico Independiente", "1997", "ID01","Personeria CAI", "cai@gmail.com",
        		"4488776", "Chocon", "3500", "", "");
    	Club club02=clubServicio.crearClub("CAB","Club Alta Barda", "1970", "ID02","Personeria CAB", "cab@gmail.com",
        		"4433222", "Ruta 7", "km 3", "","");
    	Club club03=clubServicio.crearClub("NRC","Neuquen Rugby Club", "1983", "ID03","Personeria NRC", "nrc@yahoo.com.ar",
        		"4487290", "San Martin", "8762", "", "");
    	Club club04=clubServicio.crearClub("ZAPALA","Club Zapala", "2008", "ID04","Personeria Zapala", "zapala@hotmail.com",
        		"0299 473 699", "Ramon Castro", "31", "", "");
    	Club club05=clubServicio.crearClub("CENT","Club Centenario", "2011", "ID05","Personeria Centenario", "centenario@gmail.com",
        		"0299 493 544", "Ruta 7", "Km 18", "", "");
    	
    	equipoServicio.crearEquipo("CAI", Estado.ACTIVO, club01, divi01);
    	equipoServicio.crearEquipo("CAB", Estado.ACTIVO, club02, divi01);
    	equipoServicio.crearEquipo("NRC", Estado.ACTIVO, club03, divi01);
    	equipoServicio.crearEquipo("Zapala", Estado.ACTIVO, club04, divi01);
    	equipoServicio.crearEquipo("Cente", Estado.ACTIVO, club05, divi01);
    	
    	equipoServicio.crearEquipo("CAI", Estado.ACTIVO, club01, divi02);
    	equipoServicio.crearEquipo("CAB", Estado.ACTIVO, club02, divi02);
    	equipoServicio.crearEquipo("NRC", Estado.ACTIVO, club03, divi02);
    	equipoServicio.crearEquipo("Zapala", Estado.ACTIVO, club04, divi02);
    	equipoServicio.crearEquipo("Cente", Estado.ACTIVO, club05, divi02);
    	equipoServicio.crearEquipo("CAI 2", Estado.ACTIVO, club01, divi02);
    	equipoServicio.crearEquipo("CAB 2", Estado.ACTIVO, club02, divi02);
   	
        
    }
	
    @SuppressWarnings("deprecation")
    	private Temporada creaTemporada(
                final String nombre,
                final Estado estado,
                final String observaciones
        		,
                ExecutionContext executionContext) {
    	return executionContext.add(this, temporadaServicio.crearTemporada(nombre, Estado.ACTIVO, observaciones));
    }
   
    public void BorrarDBTemporada(ExecutionContext executionContext)
    {
//        execute(new TemporadaTearDown(), executionContext);
    	
       return;
    }
       
    @javax.inject.Inject
    private TemporadaServicio temporadaServicio;
    
    @javax.inject.Inject
    private TorneoServicio torneoServicio;
    
    @javax.inject.Inject
    private DivisionServicio divisionServicio;
    
    @javax.inject.Inject
    private EquipoServicio equipoServicio;
    
    @javax.inject.Inject
    private ClubServicio clubServicio;

}