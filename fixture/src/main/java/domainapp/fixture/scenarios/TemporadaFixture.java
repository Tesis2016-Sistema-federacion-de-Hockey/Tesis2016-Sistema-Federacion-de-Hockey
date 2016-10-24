package domainapp.fixture.scenarios;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import domainapp.dom.club.Club;
import domainapp.dom.club.ClubServicio;
import domainapp.dom.division.Division;
import domainapp.dom.division.DivisionServicio;
import domainapp.dom.equipo.EquipoServicio;
import domainapp.dom.estado.Estado;
import domainapp.dom.modalidad.Modalidad;
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
   	
    	
    	Division divi01=divisionServicio.crearDivision("1ra DAMAS", Estado.ACTIVO, tor01, Modalidad.IDA_Y_VUELTA, 3, 1, 0);
    	Division divi02=divisionServicio.crearDivision("1ra CABALLEROS", Estado.ACTIVO, tor01, Modalidad.IDA_Y_VUELTA, 3, 1, 0);
    	divisionServicio.crearDivision("7ma DAMAS", Estado.ACTIVO, tor01, Modalidad.IDA_Y_VUELTA, 3, 1, 0);
    	divisionServicio.crearDivision("6ta DAMAS", Estado.ACTIVO, tor01, Modalidad.IDA_Y_VUELTA, 3, 1, 0);
    	divisionServicio.crearDivision("5ta DAMAS", Estado.ACTIVO, tor01, Modalidad.IDA_Y_VUELTA, 3, 1, 0);

    	Club club01=clubServicio.crearClub("CAI","Club Atletico Independiente", "1997", "ID01","Personeria CAI", "caihockeyneuquen@gmail.com",
        		"4488776", "Chocon", "3500", "", "");
    	Club club02=clubServicio.crearClub("CAB","Club Alta Barda", "1970", "ID02","Personeria CAB", "cab@gmail.com",
        		"4433222", "Ruta 7", "km 3", "","");
    	Club club03=clubServicio.crearClub("NRC","Neuquen Rugby Club", "1983", "ID03","Personeria NRC", "nrc@yahoo.com.ar",
        		"4487290", "San Martin", "8762", "", "");
    	Club club04=clubServicio.crearClub("PLAZA HUINCUL","Club Atletico Plaza Huincul", "2008", "ID04","Personeria Huincul", "huincul@hotmail.com",
        		"0299 473 699", "Ramon Castro", "31", "", "");
    	Club club05=clubServicio.crearClub("CENTENARIO","Club Centenario", "2011", "ID05","Personeria Centenario", "centenario@gmail.com",
        		"0299 493 544", "Ruta 7", "Km 18", "", "");
    	Club club06=clubServicio.crearClub("CHOS MALAL","Club ChosMalal", "2003", "ID06","Personeria ChosMa", "chosmalal@gmail.com",
        		"0299 448 318", "Lainez", "7611", "", "");
    	Club club07=clubServicio.crearClub("LIMAY","Club Limay de Plottier", "1994", "ID07","Personeria Plottier", "plottier@gmail.com",
        		"0299 477 1993", "Matheu", "123", "", "");
    	
    	
    	equipoServicio.crearEquipo("CAI", Estado.ACTIVO, club01, divi01);
    	equipoServicio.crearEquipo("CAB", Estado.ACTIVO, club02, divi01);
    	equipoServicio.crearEquipo("NRC", Estado.ACTIVO, club03, divi01);
    	equipoServicio.crearEquipo("Huincul", Estado.ACTIVO, club04, divi01);
    	equipoServicio.crearEquipo("San Jorge", Estado.ACTIVO, club05, divi01);
    	
    	equipoServicio.crearEquipo("CAI A", Estado.ACTIVO, club01, divi02);
    	equipoServicio.crearEquipo("CAI B", Estado.ACTIVO, club01, divi02);
    	equipoServicio.crearEquipo("Alta Barda", Estado.ACTIVO, club02, divi02);
    	equipoServicio.crearEquipo("San Jorge", Estado.ACTIVO, club05, divi02);
    	equipoServicio.crearEquipo("Huincul", Estado.ACTIVO, club04, divi02);
    	equipoServicio.crearEquipo("Chos Malal", Estado.ACTIVO, club06, divi02);
    	equipoServicio.crearEquipo("Limay", Estado.ACTIVO, club07, divi02);
   	
    	creaTemporada("2015", Estado.INACTIVO, "Sin observaciones", executionContext);
    	creaTemporada("2014", Estado.INACTIVO, "Sin observaciones", executionContext);

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