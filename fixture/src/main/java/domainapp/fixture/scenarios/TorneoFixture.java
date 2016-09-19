package domainapp.fixture.scenarios;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.dom.estado.Estado;
import domainapp.dom.temporada.Temporada;
import domainapp.dom.temporada.TemporadaServicio;
import domainapp.dom.torneo.Torneo;
import domainapp.dom.torneo.TorneoServicio;

public class TorneoFixture extends FixtureScript {
	public TorneoFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

//    	BorrarDBTorneo(executionContext);
        
    	crearTorneo("APERTURA", Estado.ACTIVO, null, "Sin observaciones", executionContext);
//    	crearTorneo("CLAUSURA", Estado.ACTIVO, new Temporada(), "Sin observaciones", executionContext);
//    	crearTorneo("INVIERNO", Estado.ACTIVO, null, "Sin observaciones", executionContext);
//    	crearTorneo("SEVEN", Estado.ACTIVO, null, "Sin observaciones", executionContext);
    }
	
    @SuppressWarnings("deprecation")
    	private Torneo crearTorneo(
                final String nombre,
                final Estado estado,
                final Temporada temporada,
                final String observaciones,
                ExecutionContext executionContext) {
    	return executionContext.add(this, torneoServicio.crearTorneo(nombre, Estado.ACTIVO, null, observaciones));
    }
   
    public void BorrarDBTorneo(ExecutionContext executionContext)
    {
//        execute(new TorneoTearDown(), executionContext);
    	
       return;
    }
       
    @javax.inject.Inject
    private TorneoServicio torneoServicio;
}