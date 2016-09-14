package domainapp.fixture.scenarios;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.dom.division.DivisionServicio;
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
    	
    	divisionServicio.crearDivision("PRIMERA DAMAS", Estado.ACTIVO, tempo01, tor01, "Todos contra todos", 3, 1, 0);
    	divisionServicio.crearDivision("PRIMERA CABALLEROS", Estado.ACTIVO, tempo01, tor01, "Todos contra todos", 3, 1, 0);
    	divisionServicio.crearDivision("7MA DAMAS", Estado.ACTIVO, tempo01, tor01, "Todos contra todos", 3, 1, 0);
    	divisionServicio.crearDivision("6TA DAMAS", Estado.ACTIVO, tempo01, tor01, "Todos contra todos", 3, 1, 0);
    	divisionServicio.crearDivision("5TA DAMAS", Estado.ACTIVO, tempo01, tor01, "Todos contra todos", 3, 1, 0);

    	
    	
        
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

}