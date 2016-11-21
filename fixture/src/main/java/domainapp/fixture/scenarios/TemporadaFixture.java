package domainapp.fixture.scenarios;

import java.math.BigDecimal;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.joda.time.LocalDate;

import domainapp.dom.club.Club;
import domainapp.dom.club.ClubServicio;
import domainapp.dom.cuotaclub.CuotaClub;
import domainapp.dom.cuotaclub.CuotaClubServicio;
import domainapp.dom.cuotajugador.CuotaJugador;
import domainapp.dom.cuotajugador.CuotaJugadorServicio;
import domainapp.dom.division.Division;
import domainapp.dom.division.DivisionServicio;
import domainapp.dom.equipo.Equipo;
import domainapp.dom.equipo.EquipoServicio;
import domainapp.dom.estado.Estado;
import domainapp.dom.jugador.Jugador;
import domainapp.dom.jugador.JugadorServicio;
import domainapp.dom.modalidad.Modalidad;
import domainapp.dom.sector.Sector;
import domainapp.dom.temporada.Temporada;
import domainapp.dom.temporada.TemporadaServicio;
import domainapp.dom.tipodocumento.TipoDocumento;
import domainapp.dom.torneo.Torneo;
import domainapp.dom.torneo.TorneoServicio;

public class TemporadaFixture extends FixtureScript {
	public TemporadaFixture() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(ExecutionContext executionContext) {

//    	BorrarDBClub(executionContext);
    	
        
    	Temporada tempo01=crearTemporada("2016", Estado.ACTIVO, new LocalDate(2016, 1, 1), new LocalDate(2016, 12, 31), "Sin observaciones", executionContext);
    	
    	Torneo tor01=torneoServicio.crearTorneo("APERTURA", Estado.ACTIVO, tempo01, "Sin observaciones");
    	torneoServicio.crearTorneo("CLAUSURA", Estado.ACTIVO, tempo01, "Sin observaciones");
    	torneoServicio.crearTorneo("PISTA INVIERNO", Estado.ACTIVO, tempo01, "Sin observaciones");
    	torneoServicio.crearTorneo("SEVEN", Estado.ACTIVO, tempo01, "Sin observaciones");
   	
    	
    	Division divi01=divisionServicio.crearDivision("1ra DAMAS", 1970, Estado.ACTIVO, tor01, Modalidad.IDA_Y_VUELTA, 3, 1, 0);
    	Division divi02=divisionServicio.crearDivision("1ra CABALLEROS", 1970, Estado.ACTIVO, tor01, Modalidad.IDA_Y_VUELTA, 3, 1, 0);
    	divisionServicio.crearDivision("7ma DAMAS", 2005, Estado.ACTIVO, tor01, Modalidad.IDA_Y_VUELTA, 3, 1, 0);
    	divisionServicio.crearDivision("6ta DAMAS", 2004, Estado.ACTIVO, tor01, Modalidad.IDA_Y_VUELTA, 3, 1, 0);
    	divisionServicio.crearDivision("5ta DAMAS", 2003, Estado.ACTIVO, tor01, Modalidad.IDA_Y_VUELTA, 3, 1, 0);

    	
    	Club club01=clubServicio.crearClub("CAI","Club Atletico Independiente", "1997", "ID01","Personeria CAI", "caihockeyneuquen@gmail.com",
        		"299 4061216", "Jose Rosa", "246", "", "", null);
    	Club club02=clubServicio.crearClub("NRC","Neuquen Rugby Club", "1983", "ID02","Personeria NRC", "hockey@neuquenrc.com.ar",
        		"444 1188", "San Martin", "8000", "", "", null);
    	Club club03=clubServicio.crearClub("CAB","Club Alta Barda", "1970", "ID03","Personeria CAB", "cab@gmail.com",
        		"443 3222", "Ruta 7", "km 3", "","", null);
    	Club club04=clubServicio.crearClub("Ribera","Asociacion La Ribera", "2008", "ID04","Personeria La Ribera", "laribera@hotmail.com",
        		"473 699", "Ramon Castro", "31", "", "", null);
    	Club club05=clubServicio.crearClub("San Jorge","Club San Jorge", "2011", "ID05","Personeria San Jorge", "sanjorge@gmail.com",
        		"0299 493 544", "Ruta 7", "Km 18", "", "", null);
    	Club club06=clubServicio.crearClub("CAN","Club Atletico Neuquen", "2003", "ID06","Personeria Neuquen", "atleticoneuquenhockey@yahoo.com.ar",
        		"0299 443 8800", "Saavedra", "250", "", "", null);
    	Club club07=clubServicio.crearClub("LIMAY","Club Limay de Plottier", "1994", "ID07","Personeria Plottier", "plottier@gmail.com",
        		"0299 477 1993", "Matheu", "123", "", "", null);
    	Club club08=clubServicio.crearClub("Tiro F","Tiro Federal Zapala", "2009", "ID08","Personeria Zapala", "zapala@gmail.com",
        		"02948 438888", "Bolivia", "4025", "", "", null);

    	
    	
    	Equipo equipo01=equipoServicio.crearEquipo("CAI", Estado.ACTIVO, club01, divi01);
    	Equipo equipo02=equipoServicio.crearEquipo("NRC", Estado.ACTIVO, club02, divi01);
    	equipoServicio.crearEquipo("CAB", Estado.ACTIVO, club03, divi01);
    	equipoServicio.crearEquipo("Ribera", Estado.ACTIVO, club04, divi01);
    	equipoServicio.crearEquipo("San Jorge", Estado.ACTIVO, club05, divi01);
    	equipoServicio.crearEquipo("Tiro F", Estado.ACTIVO, club08, divi01);
    	
    	equipoServicio.crearEquipo("CAI A", Estado.ACTIVO, club01, divi02);
    	equipoServicio.crearEquipo("CAI B", Estado.ACTIVO, club01, divi02);
    	equipoServicio.crearEquipo("Alta Barda", Estado.ACTIVO, club03, divi02);
    	equipoServicio.crearEquipo("San Jorge", Estado.ACTIVO, club05, divi02);
    	equipoServicio.crearEquipo("Ribera", Estado.ACTIVO, club04, divi02);
    	equipoServicio.crearEquipo("CAN", Estado.ACTIVO, club06, divi02);
    	equipoServicio.crearEquipo("Limay", Estado.ACTIVO, club07, divi02);
   	
    	Temporada tempo02=crearTemporada("2015", Estado.INACTIVO, new LocalDate(2015, 1, 1), new LocalDate(2015, 12, 31), "Sin observaciones", executionContext);
    	Temporada tempo03=crearTemporada("2014", Estado.INACTIVO, new LocalDate(2014, 1, 1), new LocalDate(2014, 12, 31),"Sin observaciones", executionContext);
    	tempo02.setEstado(Estado.INACTIVO);
    	tempo03.setEstado(Estado.INACTIVO);
    	
    	
    	Torneo tor02=torneoServicio.crearTorneo("APERTURA", Estado.ACTIVO, tempo02, "Sin observaciones");
    	Torneo tor03=torneoServicio.crearTorneo("CLAUSURA", Estado.ACTIVO, tempo02, "Sin observaciones");
    	Torneo tor04=torneoServicio.crearTorneo("PISTA INVIERNO", Estado.ACTIVO, tempo02, "Sin observaciones");
    	Torneo tor05=torneoServicio.crearTorneo("SEVEN", Estado.ACTIVO, tempo02, "Sin observaciones");
    	
    	tor02.setEstado(Estado.INACTIVO);
    	tor03.setEstado(Estado.INACTIVO);
    	tor04.setEstado(Estado.INACTIVO);
    	tor05.setEstado(Estado.INACTIVO);
    	    	
    	Jugador jug01=jugadorServicio.crearJugador(Sector.DAMAS, "111", "Julia", "Blanco", TipoDocumento.DNI, "27883920",
    			new LocalDate(1996, 12, 1), Estado.ACTIVO, "julia.blanco@gmail.com", "Dr. Ramon", "15", "PB", "B",
    			null, "4483131", "2994523497", club01, equipo01);
    	Jugador jug02=jugadorServicio.crearJugador(Sector.DAMAS, "112", "Adriana", "Petazzi", TipoDocumento.DNI, "28996773",
    			new LocalDate(1997, 6, 4), Estado.ACTIVO, "adriana.petazzi@hotmail.com.ar", "Avenida Argentina", "1050", "PB", "D",
    			null, "4471223", "2996013887", club01, equipo01);
    	Jugador jug03=jugadorServicio.crearJugador(Sector.DAMAS, "113", "Maru", "Lopez", TipoDocumento.DNI, "27885990",
    			new LocalDate(1995, 2, 4), Estado.ACTIVO, "", "Ameghino", "615", "", "",
    			null, "4482447", "2996031234", club01, equipo01);
    	Jugador jug04=jugadorServicio.crearJugador(Sector.DAMAS, "114", "Victoria", "Falleti", TipoDocumento.DNI, "27434960",
    			new LocalDate(1994, 11, 17), Estado.ACTIVO, "", "Belgrano", "2250", "", "",
    			null, "4439898", "2996388572", club01, equipo01);
    	Jugador jug05=jugadorServicio.crearJugador(Sector.DAMAS, "115", "Majo", "Alonso", TipoDocumento.DNI, "21348873",
    			new LocalDate(1996, 9, 21), Estado.ACTIVO, "", "Esmeralda", "23", "", "",
    			null, "4331778", "2995239385", club01, equipo01);
    	Jugador jug06=jugadorServicio.crearJugador(Sector.DAMAS, "116", "Silvina", "Armengol", TipoDocumento.DNI, "31004827",
    			new LocalDate(2001, 8, 7), Estado.ACTIVO, "", "Garcia", "123", "", "",
    			null, "4457338", "299638776", club01, equipo01);
    	Jugador jug07=jugadorServicio.crearJugador(Sector.DAMAS, "117", "Valeria", "Bahurlet", TipoDocumento.DNI, "32676335",
    			new LocalDate(1999, 6, 5), Estado.ACTIVO, "", "Echeverry", "432", "", "",
    			null, "4455882", "2996178229", club01, equipo01);
    	Jugador jug08=jugadorServicio.crearJugador(Sector.DAMAS, "118", "Moreto", "Pamela", TipoDocumento.DNI, "33881720",
    			new LocalDate(1996, 11, 23), Estado.ACTIVO, "", "Lainez", "15", "", "",
    			null, "4452771", "", club01, equipo01);
    	Jugador jug09=jugadorServicio.crearJugador(Sector.DAMAS, "119", "Moreto", "Paola", TipoDocumento.DNI, "33881721",
    			new LocalDate(1997, 10, 19), Estado.ACTIVO, "", "Lainez", "15", "", "",
    			null, "4452771", "", club01, equipo01);
    	Jugador jug10=jugadorServicio.crearJugador(Sector.DAMAS, "120", "Ana", "Servidio", TipoDocumento.DNI, "20887293",
    			new LocalDate(1998, 12, 2), Estado.ACTIVO, "", "Rio Negro", "3221", "", "",
    			null, "", "", club01, equipo01);
    	Jugador jug11=jugadorServicio.crearJugador(Sector.DAMAS, "121", "Susana", "Horia", TipoDocumento.DNI, "21377334",
    			new LocalDate(1996, 1, 3), Estado.ACTIVO, "", "San Martin", "1030", "", "",
    			null, "", "", club01, equipo01);
    	
    	
    	equipo01.getListaBuenaFe().add(jug01);
    	equipo01.getListaBuenaFe().add(jug02);
    	equipo01.getListaBuenaFe().add(jug03);
    	equipo01.getListaBuenaFe().add(jug04);
    	equipo01.getListaBuenaFe().add(jug05);
    	equipo01.getListaBuenaFe().add(jug06);
    	equipo01.getListaBuenaFe().add(jug07);
    	equipo01.getListaBuenaFe().add(jug08);
    	equipo01.getListaBuenaFe().add(jug09);
    	equipo01.getListaBuenaFe().add(jug10);
    	equipo01.getListaBuenaFe().add(jug11);
    	
    	
    	
    	
    	Jugador jug21=jugadorServicio.crearJugador(Sector.DAMAS, "221", "Luciana", "Aimar", TipoDocumento.DNI, "31334488",
    			new LocalDate(2005, 3, 8), Estado.ACTIVO, "luciana.aimar@gmail.com", "Elordi", "307", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug22=jugadorServicio.crearJugador(Sector.DAMAS, "222", "Carla", "Rebequi", TipoDocumento.DNI, "21998362",
    			new LocalDate(2004, 4, 26), Estado.ACTIVO, "carla.rebequi@yahoo.com", "Rio Diamante", "667", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug23=jugadorServicio.crearJugador(Sector.DAMAS, "223", "Magdalena", "Aicega", TipoDocumento.DNI, "35772957",
    			new LocalDate(2005, 3, 13), Estado.ACTIVO, "magui.aicega@hotmal.com", "Tucuman", "867", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug24=jugadorServicio.crearJugador(Sector.DAMAS, "224", "Zulema", "Garcia", TipoDocumento.DNI, "34662738",
    			new LocalDate(2004, 4, 4), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug25=jugadorServicio.crearJugador(Sector.DAMAS, "225", "Teresa", "Pareto", TipoDocumento.DNI, "34982840",
    			new LocalDate(2005, 5, 5), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug26=jugadorServicio.crearJugador(Sector.DAMAS, "226", "Valeria", "Martinez", TipoDocumento.DNI, "34277399",
    			new LocalDate(2005, 6, 6), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug27=jugadorServicio.crearJugador(Sector.DAMAS, "227", "Fernanda", "Gimenez", TipoDocumento.DNI, "34678876",
    			new LocalDate(2003, 8, 1), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug28=jugadorServicio.crearJugador(Sector.DAMAS, "228", "Graciela", "Borges", TipoDocumento.DNI, "34288400",
    			new LocalDate(2005, 6, 29), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug29=jugadorServicio.crearJugador(Sector.DAMAS, "229", "Helena", "Trapo", TipoDocumento.DNI, "34822044",
    			new LocalDate(2005, 2, 20), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug30=jugadorServicio.crearJugador(Sector.DAMAS, "230", "Maria Azucena", "Techaba", TipoDocumento.DNI, "31316677",
    			new LocalDate(2004, 2, 7), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug31=jugadorServicio.crearJugador(Sector.DAMAS, "231", "Maria Luisa", "Srevernick", TipoDocumento.DNI, "32324433",
    			new LocalDate(2005, 4, 14), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);

    	
    	
    	
    	
    	equipo02.getListaBuenaFe().add(jug21);
    	equipo02.getListaBuenaFe().add(jug22);
    	equipo02.getListaBuenaFe().add(jug23);
    	equipo02.getListaBuenaFe().add(jug24);
    	equipo02.getListaBuenaFe().add(jug25);
    	equipo02.getListaBuenaFe().add(jug26);
    	equipo02.getListaBuenaFe().add(jug27);
    	equipo02.getListaBuenaFe().add(jug28);
    	equipo02.getListaBuenaFe().add(jug29);
    	equipo02.getListaBuenaFe().add(jug30);
    	equipo02.getListaBuenaFe().add(jug31);
    	
    	//CREO CUOTAS DE CLUB
    	
    	CuotaClub cuotaClub01= cuotaClubServicio.crearCuotaClub(tempo01, new BigDecimal(1750), new LocalDate(2016,3,31), "Primera");
    	CuotaClub cuotaClub02= cuotaClubServicio.crearCuotaClub(tempo01, new BigDecimal(2270), new LocalDate(2016,6,30), "Segunda");
    	CuotaClub cuotaClub03= cuotaClubServicio.crearCuotaClub(tempo01, new BigDecimal(3100), new LocalDate(2016,9,30), "Tercera");
    	
    	CuotaJugador cuotaJugador01=cuotaJugadorServicio.crearCuotaJugador(tempo01, new BigDecimal(450), new LocalDate(2016,3,31), "Primera");
    	CuotaJugador cuotaJugador02=cuotaJugadorServicio.crearCuotaJugador(tempo01, new BigDecimal(600), new LocalDate(2016,4,30), "Segunda");
    	CuotaJugador cuotaJugador03=cuotaJugadorServicio.crearCuotaJugador(tempo01, new BigDecimal(500), new LocalDate(2016,5,31), "Tercera");
  	
    	cuotaClub01.getListaClubes().add(club01);
    	cuotaClub01.getListaClubes().add(club02);
    	cuotaClub01.getListaClubes().add(club03);
    	cuotaClub01.getListaClubes().add(club04);
    	cuotaClub01.getListaClubes().add(club05);
    	cuotaClub01.getListaClubes().add(club06);
    	cuotaClub01.getListaClubes().add(club07);
    	cuotaClub01.getListaClubes().add(club08);
    	
    	cuotaClub02.getListaClubes().add(club01);
    	cuotaClub02.getListaClubes().add(club03);
    	cuotaClub02.getListaClubes().add(club06);
    	
    	cuotaClub03.getListaClubes().add(club02);
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }
	
    @SuppressWarnings("deprecation")
    	private Temporada crearTemporada(
                final String nombre,
                final Estado estado,
                final LocalDate fechaInicio,
                final LocalDate fechaCierre,
                final String observaciones
        		,
                ExecutionContext executionContext) {
    	return executionContext.add(this, temporadaServicio.crearTemporada(nombre, Estado.ACTIVO, fechaInicio, fechaCierre, observaciones));
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
    
    @javax.inject.Inject
    private CuotaClubServicio cuotaClubServicio;
    
    @javax.inject.Inject
    private CuotaJugadorServicio cuotaJugadorServicio;
    
    @javax.inject.Inject
    private JugadorServicio jugadorServicio;

}