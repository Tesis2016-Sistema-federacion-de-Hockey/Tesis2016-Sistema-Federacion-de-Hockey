package domainapp.fixture.scenarios;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.dom.division.Division;
import domainapp.dom.division.DivisionServicio;
import domainapp.dom.estado.Estado;
import domainapp.dom.temporada.Temporada;
import domainapp.dom.torneo.Torneo;

public class DivisionFixture extends FixtureScript {
	public DivisionFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

//    	BorrarDBDivision(executionContext);
        
    	crearDivision("PRIMERA DAMAS", Estado.ACTIVO, null, null, "Todos contra todos", 3, 1, 0, executionContext);
    	crearDivision("PRIMERA CABALLEROS", Estado.ACTIVO, null, null, "Todos contra todos", 3, 1, 0, executionContext);
    	crearDivision("7MA DAMAS", Estado.ACTIVO, null, null, "Todos contra todos", 3, 1, 0, executionContext);
    	crearDivision("6TA DAMAS", Estado.ACTIVO, null, null, "Todos contra todos", 3, 1, 0, executionContext);
    	crearDivision("5TA DAMAS", Estado.ACTIVO, null, null, "Todos contra todos", 3, 1, 0, executionContext);

    }
	
    @SuppressWarnings("deprecation")
    	private Division crearDivision(
                final String nombre,
                final Estado estado,
                final Temporada temporada,
                final Torneo torneo,
                final String modalidad,
                final int puntosGanar,
                final int puntosEmpatar,
                final int puntosPerder,
                ExecutionContext executionContext) {
    	return executionContext.add(this, divisionServicio.crearDivision(nombre, Estado.ACTIVO, null, null, modalidad,
    			puntosGanar, puntosEmpatar, puntosPerder));
    }
   
    public void BorrarDBDivision(ExecutionContext executionContext)
    {
//        execute(new DivisionTearDown(), executionContext);
    	
       return;
    }
       
    @javax.inject.Inject
    private DivisionServicio divisionServicio;
}