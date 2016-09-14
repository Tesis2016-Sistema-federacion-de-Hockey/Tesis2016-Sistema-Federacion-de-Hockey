package domainapp.fixture.scenarios;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.dom.club.Club;
import domainapp.dom.division.Division;
import domainapp.dom.equipo.Equipo;
import domainapp.dom.equipo.EquipoServicio;
import domainapp.dom.estado.Estado;

public class EquipoFixture extends FixtureScript {
	public EquipoFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

//    	BorrarDBEquipo(executionContext);
        
    	crearEquipo("CAI", Estado.ACTIVO, null, null, executionContext);

    }
	
    @SuppressWarnings("deprecation")
    	private Equipo crearEquipo(
                final String nombre,
                final Estado estado,
                final Club club,
                final Division division,
                ExecutionContext executionContext) {
    	return executionContext.add(this, equipoServicio.crearEquipo(nombre, Estado.ACTIVO, null, null));
    }
   
    public void BorrarDBEquipo(ExecutionContext executionContext)
    {
//        execute(new EquipoTearDown(), executionContext);
    	
       return;
    }
       
    @javax.inject.Inject
    private EquipoServicio equipoServicio;
}