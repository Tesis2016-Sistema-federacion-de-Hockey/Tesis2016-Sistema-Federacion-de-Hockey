package domainapp.fixture.scenarios;

import java.math.BigDecimal;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.joda.time.DateTime;
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
import domainapp.dom.pagoclub.PagoClub;
import domainapp.dom.pagoclub.PagoClubServicio;
import domainapp.dom.pagojugador.PagoJugadorServicio;
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
    	
    	Temporada tempo02=crearTemporada("2015", Estado.INACTIVO, new LocalDate(2015, 1, 1), new LocalDate(2015, 12, 31), "Sin observaciones", executionContext);
    	Temporada tempo03=crearTemporada("2014", Estado.INACTIVO, new LocalDate(2014, 1, 1), new LocalDate(2014, 12, 31),"Sin observaciones", executionContext);
    	tempo02.setEstado(Estado.INACTIVO);
    	tempo03.setEstado(Estado.INACTIVO);
    	
    	
    	Temporada tempo01=crearTemporada("2016", Estado.ACTIVO, new LocalDate(2016, 1, 1), new LocalDate(2016, 12, 31), "Sin observaciones", executionContext);
    	
    	Torneo tor01=torneoServicio.crearTorneo("APERTURA", Estado.ACTIVO, tempo01, "Sin observaciones");
    	torneoServicio.crearTorneo("CLAUSURA", Estado.ACTIVO, tempo01, "Sin observaciones");
    	torneoServicio.crearTorneo("PISTA INVIERNO", Estado.ACTIVO, tempo01, "Sin observaciones");
    	torneoServicio.crearTorneo("SEVEN", Estado.ACTIVO, tempo01, "Sin observaciones");
   	
    	
    	Division divi01=divisionServicio.crearDivision("1ra DAMAS", 0, Estado.ACTIVO, tor01, Modalidad.IDA_Y_VUELTA, 3, 1, 0);
    	Division divi02=divisionServicio.crearDivision("1ra CABALLEROS", 0, Estado.ACTIVO, tor01, Modalidad.IDA_Y_VUELTA, 3, 1, 0);
    	divisionServicio.crearDivision("7ma DAMAS", 2005, Estado.ACTIVO, tor01, Modalidad.IDA_Y_VUELTA, 3, 1, 0);
    	divisionServicio.crearDivision("6ta DAMAS", 2004, Estado.ACTIVO, tor01, Modalidad.IDA_Y_VUELTA, 3, 1, 0);
    	divisionServicio.crearDivision("5ta DAMAS", 2003, Estado.ACTIVO, tor01, Modalidad.IDA_Y_VUELTA, 3, 1, 0);

    	
    	
    	Club club01=clubServicio.crearClub("CAI","Club Atletico Independiente", "1997", "ID01","Personeria CAI", "caihockeyneuquen@gmail.com",
        		"0299 154061216", "Jose Rosa", "246", "", "", null);
    	Club club02=clubServicio.crearClub("NRC","Neuquen Rugby Club", "1983", "ID02","Personeria NRC", "hockey@neuquenrc.com.ar",
        		"0299 4441188", "San Martin", "8000", "", "", null);
    	Club club03=clubServicio.crearClub("CAB","Club Alta Barda", "1970", "ID03","Personeria CAB", "cab@gmail.com",
        		"0299 4433222", "Ruta 7", "km 3", "","", null);
    	Club club04=clubServicio.crearClub("Ribera","Asociacion La Ribera", "2008", "ID04","Personeria La Ribera", "laribera@hotmail.com",
        		"0299 4736997", "Ramon Castro", "31", "", "", null);
    	Club club05=clubServicio.crearClub("San Jorge","Club San Jorge", "2011", "ID05","Personeria San Jorge", "sanjorge@gmail.com",
        		"0299 493544", "Ruta 7", "Km 18", "", "", null);
    	Club club06=clubServicio.crearClub("CAN","Club Atletico Neuquen", "2003", "ID06","Personeria Neuquen", "atleticoneuquenhockey@yahoo.com.ar",
        		"0299 4438800", "Saavedra", "250", "", "", null);
    	Club club07=clubServicio.crearClub("LIMAY","Club Limay de Plottier", "1994", "ID07","Personeria Plottier", "plottier@gmail.com",
        		"0299 4771993", "Matheu", "123", "", "", null);
    	Club club08=clubServicio.crearClub("Tiro F","Tiro Federal Zapala", "2009", "ID08","Personeria Zapala", "zapala@gmail.com",
        		"02948 438888", "Bolivia", "4025", "", "", null);

    	
    	//Equipos para la 1ra DAMAS
    	Equipo equipo01=equipoServicio.crearEquipo("CAI", Estado.ACTIVO, club01, divi01);
    	Equipo equipo02=equipoServicio.crearEquipo("NRC", Estado.ACTIVO, club02, divi01);
    	Equipo equipo03=equipoServicio.crearEquipo("CAB", Estado.ACTIVO, club03, divi01);
    	Equipo equipo04=equipoServicio.crearEquipo("Ribera", Estado.ACTIVO, club04, divi01);
    	Equipo equipo05=equipoServicio.crearEquipo("San Jorge", Estado.ACTIVO, club05, divi01);
    	Equipo equipo06=equipoServicio.crearEquipo("Tiro F", Estado.ACTIVO, club08, divi01);
    	
    	
    	//Equipos para la 1ra CABALLEROS
    	Equipo equipo11=equipoServicio.crearEquipo("CAI A", Estado.ACTIVO, club01, divi02);
    	Equipo equipo12=equipoServicio.crearEquipo("CAI B", Estado.ACTIVO, club01, divi02);
    	Equipo equipo13=equipoServicio.crearEquipo("Alta Barda", Estado.ACTIVO, club03, divi02);
    	Equipo equipo14=equipoServicio.crearEquipo("San Jorge", Estado.ACTIVO, club05, divi02);
    	Equipo equipo15=equipoServicio.crearEquipo("Ribera", Estado.ACTIVO, club04, divi02);
    	Equipo equipo16=equipoServicio.crearEquipo("CAN", Estado.ACTIVO, club06, divi02);
    	Equipo equipo17=equipoServicio.crearEquipo("Limay", Estado.ACTIVO, club07, divi02);
   	
    	
    	Torneo tor02=torneoServicio.crearTorneo("APERTURA", Estado.ACTIVO, tempo02, "Sin observaciones");
    	Torneo tor03=torneoServicio.crearTorneo("CLAUSURA", Estado.ACTIVO, tempo02, "Sin observaciones");
    	Torneo tor04=torneoServicio.crearTorneo("PISTA INVIERNO", Estado.ACTIVO, tempo02, "Sin observaciones");
    	Torneo tor05=torneoServicio.crearTorneo("SEVEN", Estado.ACTIVO, tempo02, "Sin observaciones");
    	
    	tor02.setEstado(Estado.INACTIVO);
    	tor03.setEstado(Estado.INACTIVO);
    	tor04.setEstado(Estado.INACTIVO);
    	tor05.setEstado(Estado.INACTIVO);
    	    	
    	Jugador jug01=jugadorServicio.crearJugador(Sector.DAMAS, "111", "Julia", "Blanco", TipoDocumento.DNI, "11883920",
    			new LocalDate(1996, 12, 1), Estado.ACTIVO, "julia.blanco@gmail.com", "Dr. Ramon", "15", "PB", "B",
    			null, "4483131", "2994523497", club01, equipo01);
    	Jugador jug02=jugadorServicio.crearJugador(Sector.DAMAS, "112", "Adriana", "Petazzi", TipoDocumento.DNI, "11996773",
    			new LocalDate(1997, 6, 4), Estado.ACTIVO, "adriana.petazzi@hotmail.com.ar", "Avenida Argentina", "1050", "PB", "D",
    			null, "4471223", "2996013887", club01, equipo01);
    	Jugador jug03=jugadorServicio.crearJugador(Sector.DAMAS, "113", "Maru", "Lopez", TipoDocumento.DNI, "12885990",
    			new LocalDate(1995, 2, 4), Estado.ACTIVO, "", "Ameghino", "615", "", "",
    			null, "4482447", "2996031234", club01, equipo01);
    	Jugador jug04=jugadorServicio.crearJugador(Sector.DAMAS, "114", "Victoria", "Falleti", TipoDocumento.DNI, "12434960",
    			new LocalDate(1994, 11, 17), Estado.ACTIVO, "", "Belgrano", "2250", "", "",
    			null, "4439898", "2996388572", club01, equipo01);
    	Jugador jug05=jugadorServicio.crearJugador(Sector.DAMAS, "115", "Majo", "Alonso", TipoDocumento.DNI, "13348873",
    			new LocalDate(1996, 9, 21), Estado.ACTIVO, "", "Esmeralda", "23", "", "",
    			null, "4331778", "2995239385", club01, equipo01);
    	Jugador jug06=jugadorServicio.crearJugador(Sector.DAMAS, "116", "Silvina", "Armengol", TipoDocumento.DNI, "13004827",
    			new LocalDate(2001, 8, 7), Estado.ACTIVO, "", "Garcia", "123", "", "",
    			null, "4457338", "299638776", club01, equipo01);
    	Jugador jug07=jugadorServicio.crearJugador(Sector.DAMAS, "117", "Valeria", "Bahurlet", TipoDocumento.DNI, "14676335",
    			new LocalDate(1999, 6, 5), Estado.ACTIVO, "", "Echeverry", "432", "", "",
    			null, "4455882", "2996178229", club01, equipo01);
    	Jugador jug08=jugadorServicio.crearJugador(Sector.DAMAS, "118", "Pamela", "Moreto", TipoDocumento.DNI, "14881720",
    			new LocalDate(1996, 11, 23), Estado.ACTIVO, "", "Lainez", "15", "", "",
    			null, "4452771", "", club01, equipo01);
    	Jugador jug09=jugadorServicio.crearJugador(Sector.DAMAS, "119", "Paola", "Moreto", TipoDocumento.DNI, "15881721",
    			new LocalDate(1997, 10, 19), Estado.ACTIVO, "", "Lainez", "15", "", "",
    			null, "4452771", "", club01, equipo01);
    	Jugador jug10=jugadorServicio.crearJugador(Sector.DAMAS, "120", "Ana", "Servidio", TipoDocumento.DNI, "15887293",
    			new LocalDate(1998, 12, 2), Estado.ACTIVO, "", "Rio Negro", "3221", "", "",
    			null, "", "", club01, equipo01);
    	Jugador jug11=jugadorServicio.crearJugador(Sector.DAMAS, "121", "Susana", "Horia", TipoDocumento.DNI, "16377334",
    			new LocalDate(1996, 1, 3), Estado.ACTIVO, "", "San Martin", "1030", "", "",
    			null, "", "", club01, equipo01);
    	Jugador jug12=jugadorServicio.crearJugador(Sector.DAMAS, "122", "Fossatti", "Maria Ines", TipoDocumento.DNI, "16995234",
    			new LocalDate(1996, 1, 3), Estado.ACTIVO, "", "San Martin", "1030", "", "",
    			null, "", "", club01, equipo01);
    	Jugador jug13=jugadorServicio.crearJugador(Sector.DAMAS, "123", "Abalde", "Maria Rosario", TipoDocumento.DNI, "17728811",
    			new LocalDate(1997, 10, 19), Estado.ACTIVO, "", "Lainez", "15", "", "",
    			null, "4452771", "", club01, equipo01);
    	Jugador jug14=jugadorServicio.crearJugador(Sector.DAMAS, "124", "Mirta Graciela", "Abreu", TipoDocumento.DNI, "17277499",
    			new LocalDate(1998, 12, 2), Estado.ACTIVO, "", "Rio Negro", "3221", "", "",
    			null, "", "", club01, equipo01);
    	Jugador jug15=jugadorServicio.crearJugador(Sector.DAMAS, "125", "Dorita", "Seade", TipoDocumento.DNI, "18223760",
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
    	equipo01.getListaBuenaFe().add(jug12);
    	equipo01.getListaBuenaFe().add(jug13);
    	equipo01.getListaBuenaFe().add(jug14);
    	equipo01.getListaBuenaFe().add(jug15);
    	
    	
    	
    	
    	Jugador jug21=jugadorServicio.crearJugador(Sector.DAMAS, "221", "Gabriela Silvana", "Aguirre", TipoDocumento.DNI, "31334488",
    			new LocalDate(1986, 2, 19), Estado.ACTIVO, "gaby.aguirre@gmail.com", "Elordi", "307", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug22=jugadorServicio.crearJugador(Sector.DAMAS, "222", "Victoria Maria", "Zuloaga", TipoDocumento.DNI, "31998362",
    			new LocalDate(1988, 2, 14), Estado.ACTIVO, "victoria.zuloaga@yahoo.com", "Rio Diamante", "667", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug23=jugadorServicio.crearJugador(Sector.DAMAS, "223", "Lucina", "Von Der Heyde", TipoDocumento.DNI, "32772957",
    			new LocalDate(1997, 1, 24), Estado.ACTIVO, "lucina.von@hotmal.com", "Tucuman", "867", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug24=jugadorServicio.crearJugador(Sector.DAMAS, "224", "Eugenia Maria", "Trinchinetti", TipoDocumento.DNI, "32662738",
    			new LocalDate(1997, 7, 17), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug25=jugadorServicio.crearJugador(Sector.DAMAS, "225", "Sofia", "Toccalino", TipoDocumento.DNI, "33982840",
    			new LocalDate(1997, 3, 20), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug26=jugadorServicio.crearJugador(Sector.DAMAS, "226", "Belen", "Succi", TipoDocumento.DNI, "33277399",
    			new LocalDate(1985, 10, 16), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug27=jugadorServicio.crearJugador(Sector.DAMAS, "227", "Rocio", "Sanchez Moccia", TipoDocumento.DNI, "34678876",
    			new LocalDate(1988, 8, 2), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug28=jugadorServicio.crearJugador(Sector.DAMAS, "228", "Maria del Pilar", "Romang", TipoDocumento.DNI, "34288400",
    			new LocalDate(1992, 7, 9), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug29=jugadorServicio.crearJugador(Sector.DAMAS, "229", "Carla", "Rebecchi", TipoDocumento.DNI, "35822044",
    			new LocalDate(1984, 9, 7), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug30=jugadorServicio.crearJugador(Sector.DAMAS, "230", "Maria Paula", "Ortiz", TipoDocumento.DNI, "35316677",
    			new LocalDate(1997, 4, 16), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug31=jugadorServicio.crearJugador(Sector.DAMAS, "231", "Maria Florencia", "Mutio", TipoDocumento.DNI, "36324433",
    			new LocalDate(1984, 11, 20), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug32=jugadorServicio.crearJugador(Sector.DAMAS, "232", "Delfina", "Merino", TipoDocumento.DNI, "36288400",
    			new LocalDate(1989, 10, 15), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug33=jugadorServicio.crearJugador(Sector.DAMAS, "233", "Giselle", "Juarez", TipoDocumento.DNI, "37822044",
    			new LocalDate(1991, 5, 5), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug34=jugadorServicio.crearJugador(Sector.DAMAS, "234", "Julieta", "Jankunas", TipoDocumento.DNI, "37316677",
    			new LocalDate(1999, 1, 20), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug35=jugadorServicio.crearJugador(Sector.DAMAS, "235", "Florencia", "Habif", TipoDocumento.DNI, "38324433",
    			new LocalDate(1993, 8, 22), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug36=jugadorServicio.crearJugador(Sector.DAMAS, "236", "Agustina Paula", "Habif", TipoDocumento.DNI, "38324434",
    			new LocalDate(1992, 3, 8), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug37=jugadorServicio.crearJugador(Sector.DAMAS, "237", "Maria Jose", "Granatto", TipoDocumento.DNI, "39637659",
    			new LocalDate(1995, 4, 21), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug38=jugadorServicio.crearJugador(Sector.DAMAS, "238", "Julia", "Gomez Fantasia", TipoDocumento.DNI, "39558722",
    			new LocalDate(1992, 4, 30), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club02, equipo02);
    	Jugador jug39=jugadorServicio.crearJugador(Sector.DAMAS, "239", "Carla Daniela", "Dupuy Fantasia", TipoDocumento.DNI, "30779209",
    			new LocalDate(1988, 9, 18), Estado.ACTIVO, "", "", "", "", "",
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
    	equipo02.getListaBuenaFe().add(jug32);
    	equipo02.getListaBuenaFe().add(jug33);
    	equipo02.getListaBuenaFe().add(jug34);
    	equipo02.getListaBuenaFe().add(jug35);
    	equipo02.getListaBuenaFe().add(jug36);
    	equipo02.getListaBuenaFe().add(jug37);
    	equipo02.getListaBuenaFe().add(jug38);
    	equipo02.getListaBuenaFe().add(jug39);


    	Jugador jug51=jugadorServicio.crearJugador(Sector.DAMAS, "251", "Agostina", "Alonso", TipoDocumento.DNI, "21348800",
    			new LocalDate(1995, 10, 1), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club03, equipo03);
    	Jugador jug52=jugadorServicio.crearJugador(Sector.DAMAS, "252", "Maria Delfina", "Thome Gustavino", TipoDocumento.DNI, "21332759",
    			new LocalDate(1996, 9, 10), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club03, equipo03);
    	Jugador jug53=jugadorServicio.crearJugador(Sector.DAMAS, "253", "Lucila", "Sanguinetti", TipoDocumento.DNI, "22367432",
    			new LocalDate(1995, 10, 30), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club03, equipo03);
    	Jugador jug54=jugadorServicio.crearJugador(Sector.DAMAS, "254", "Maria Azul", "Rossetti", TipoDocumento.DNI, "22893956",
    			new LocalDate(1995, 8, 9), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club03, equipo03);
    	Jugador jug55=jugadorServicio.crearJugador(Sector.DAMAS, "255", "Macarena", "Losada", TipoDocumento.DNI, "23678000",
    			new LocalDate(1996, 4, 24), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club03, equipo03);
    	Jugador jug56=jugadorServicio.crearJugador(Sector.DAMAS, "256", "Priscila", "Jardel Mateos", TipoDocumento.DNI, "23666678",
    			new LocalDate(1996, 1, 16), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club03, equipo03);
    	Jugador jug57=jugadorServicio.crearJugador(Sector.DAMAS, "257", "Agustina", "Gorzelany", TipoDocumento.DNI, "24762440",
    			new LocalDate(1996, 3, 11), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club03, equipo03);
    	Jugador jug58=jugadorServicio.crearJugador(Sector.DAMAS, "258", "Magdalena", "Fernandez Ladra", TipoDocumento.DNI, "24189378",
    			new LocalDate(1995, 3, 10), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club03, equipo03);
    	Jugador jug59=jugadorServicio.crearJugador(Sector.DAMAS, "259", "Milagros", "Fernandez Ladra", TipoDocumento.DNI, "25555444",
    			new LocalDate(1997, 2, 27), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club03, equipo03);
    	Jugador jug60=jugadorServicio.crearJugador(Sector.DAMAS, "260", "Guadalupe", "Fernandez Lacort", TipoDocumento.DNI, "25111222",
    			new LocalDate(1996, 8, 1), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club03, equipo03);
    	Jugador jug61=jugadorServicio.crearJugador(Sector.DAMAS, "261", "Maria Jimena", "Fernandez Gutierrez", TipoDocumento.DNI, "26557443",
    			new LocalDate(1995, 6, 29), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club03, equipo03);
    	Jugador jug62=jugadorServicio.crearJugador(Sector.DAMAS, "262", "Bianca", "Donati", TipoDocumento.DNI, "26000999",
    			new LocalDate(1995, 6, 5), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club03, equipo03);
    	Jugador jug63=jugadorServicio.crearJugador(Sector.DAMAS, "263", "Barbara", "Dichiara", TipoDocumento.DNI, "27677677",
    			new LocalDate(1996, 11, 13), Estado.ACTIVO, "", "", "", "", "",
    			null, "", "", club03, equipo03);

    	
    	equipo03.getListaBuenaFe().add(jug51);
    	equipo03.getListaBuenaFe().add(jug52);
    	equipo03.getListaBuenaFe().add(jug53);
    	equipo03.getListaBuenaFe().add(jug54);
    	equipo03.getListaBuenaFe().add(jug55);
    	equipo03.getListaBuenaFe().add(jug56);
    	equipo03.getListaBuenaFe().add(jug57);
    	equipo03.getListaBuenaFe().add(jug58);
    	equipo03.getListaBuenaFe().add(jug59);
    	equipo03.getListaBuenaFe().add(jug60);
    	equipo03.getListaBuenaFe().add(jug61);
    	equipo03.getListaBuenaFe().add(jug62);
    	equipo03.getListaBuenaFe().add(jug63);
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	//CREO CUOTAS DE CLUB
    	
    	CuotaClub cuotaClub01= cuotaClubServicio.crearCuotaClub(tempo01, new BigDecimal(1750), new LocalDate(2016,3,31), "Primera");
    	CuotaClub cuotaClub02= cuotaClubServicio.crearCuotaClub(tempo01, new BigDecimal(2270), new LocalDate(2016,6,30), "Segunda");
    	CuotaClub cuotaClub03= cuotaClubServicio.crearCuotaClub(tempo01, new BigDecimal(3100), new LocalDate(2016,9,30), "Tercera");
    	
    	CuotaJugador cuotaJugador01=cuotaJugadorServicio.crearCuotaJugador(tempo01, new BigDecimal(450), new LocalDate(2016,3,31), "Primera");
    	CuotaJugador cuotaJugador02=cuotaJugadorServicio.crearCuotaJugador(tempo01, new BigDecimal(500), new LocalDate(2016,4,30), "Segunda");
    	cuotaJugadorServicio.crearCuotaJugador(tempo01, new BigDecimal(600), new LocalDate(2016,5,31), "Tercera");
  	
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
    	
    	
    	cuotaJugador01.getListaJugadores().add(jug01);
    	cuotaJugador01.getListaJugadores().add(jug02);
    	cuotaJugador01.getListaJugadores().add(jug03);
    	cuotaJugador01.getListaJugadores().add(jug04);
    	cuotaJugador01.getListaJugadores().add(jug05);
    	cuotaJugador01.getListaJugadores().add(jug06);
    	cuotaJugador01.getListaJugadores().add(jug07);
    	cuotaJugador01.getListaJugadores().add(jug08);
    	cuotaJugador01.getListaJugadores().add(jug09);
    	cuotaJugador01.getListaJugadores().add(jug10);
    	cuotaJugador01.getListaJugadores().add(jug11);
    	cuotaJugador01.getListaJugadores().add(jug12);
    	cuotaJugador01.getListaJugadores().add(jug13);
    	cuotaJugador01.getListaJugadores().add(jug14);
    	cuotaJugador01.getListaJugadores().add(jug15);
    	cuotaJugador01.getListaJugadores().add(jug21);
    	cuotaJugador01.getListaJugadores().add(jug22);
    	cuotaJugador01.getListaJugadores().add(jug23);
    	cuotaJugador01.getListaJugadores().add(jug24);
    	cuotaJugador01.getListaJugadores().add(jug25);
    	cuotaJugador01.getListaJugadores().add(jug26);
    	cuotaJugador01.getListaJugadores().add(jug27);
    	cuotaJugador01.getListaJugadores().add(jug28);
    	cuotaJugador01.getListaJugadores().add(jug29);
    	cuotaJugador01.getListaJugadores().add(jug30);
    	cuotaJugador01.getListaJugadores().add(jug31);
    	cuotaJugador01.getListaJugadores().add(jug32);
    	cuotaJugador01.getListaJugadores().add(jug33);
    	cuotaJugador01.getListaJugadores().add(jug34);
    	cuotaJugador01.getListaJugadores().add(jug35);
    	cuotaJugador01.getListaJugadores().add(jug36);
    	cuotaJugador01.getListaJugadores().add(jug37);
    	cuotaJugador01.getListaJugadores().add(jug38);
    	cuotaJugador01.getListaJugadores().add(jug39);
    	cuotaJugador01.getListaJugadores().add(jug51);
    	cuotaJugador01.getListaJugadores().add(jug52);
    	cuotaJugador01.getListaJugadores().add(jug53);
    	cuotaJugador01.getListaJugadores().add(jug54);
    	cuotaJugador01.getListaJugadores().add(jug55);
    	cuotaJugador01.getListaJugadores().add(jug56);
    	cuotaJugador01.getListaJugadores().add(jug57);
    	cuotaJugador01.getListaJugadores().add(jug58);
    	cuotaJugador01.getListaJugadores().add(jug59);
    	cuotaJugador01.getListaJugadores().add(jug60);
    	cuotaJugador01.getListaJugadores().add(jug61);
    	cuotaJugador01.getListaJugadores().add(jug62);
    	cuotaJugador01.getListaJugadores().add(jug63);
  
    	cuotaJugador02.getListaJugadores().add(jug01);
    	cuotaJugador02.getListaJugadores().add(jug02);
    	cuotaJugador02.getListaJugadores().add(jug03);
    	cuotaJugador02.getListaJugadores().add(jug04);
    	cuotaJugador02.getListaJugadores().add(jug05);
    	cuotaJugador02.getListaJugadores().add(jug06);
    	cuotaJugador02.getListaJugadores().add(jug07);
    	cuotaJugador02.getListaJugadores().add(jug08);
    	cuotaJugador02.getListaJugadores().add(jug09);
    	cuotaJugador02.getListaJugadores().add(jug10);
    	cuotaJugador02.getListaJugadores().add(jug11);
    	cuotaJugador02.getListaJugadores().add(jug12);
    	cuotaJugador02.getListaJugadores().add(jug13);
    	cuotaJugador02.getListaJugadores().add(jug14);
    	cuotaJugador02.getListaJugadores().add(jug15);
    	cuotaJugador02.getListaJugadores().add(jug21);
    	cuotaJugador02.getListaJugadores().add(jug22);
    	cuotaJugador02.getListaJugadores().add(jug23);
    	cuotaJugador02.getListaJugadores().add(jug24);
    	cuotaJugador02.getListaJugadores().add(jug25);
    	cuotaJugador02.getListaJugadores().add(jug26);
    	cuotaJugador02.getListaJugadores().add(jug27);
    	cuotaJugador02.getListaJugadores().add(jug28);
    	cuotaJugador02.getListaJugadores().add(jug29);
    	cuotaJugador02.getListaJugadores().add(jug30);
    	cuotaJugador02.getListaJugadores().add(jug31);
    	cuotaJugador02.getListaJugadores().add(jug32);
    	cuotaJugador02.getListaJugadores().add(jug33);
    	cuotaJugador02.getListaJugadores().add(jug34);
    	cuotaJugador02.getListaJugadores().add(jug35);
    	cuotaJugador02.getListaJugadores().add(jug36);
    	cuotaJugador02.getListaJugadores().add(jug37);
    	cuotaJugador02.getListaJugadores().add(jug38);
    	cuotaJugador02.getListaJugadores().add(jug39);
    	cuotaJugador02.getListaJugadores().add(jug51);
    	cuotaJugador02.getListaJugadores().add(jug52);
    	cuotaJugador02.getListaJugadores().add(jug53);
    	cuotaJugador02.getListaJugadores().add(jug54);
    	cuotaJugador02.getListaJugadores().add(jug55);
    	cuotaJugador02.getListaJugadores().add(jug56);
    	cuotaJugador02.getListaJugadores().add(jug57);
    	cuotaJugador02.getListaJugadores().add(jug58);
    	cuotaJugador02.getListaJugadores().add(jug59);
    	cuotaJugador02.getListaJugadores().add(jug60);
    	cuotaJugador02.getListaJugadores().add(jug61);
    	cuotaJugador02.getListaJugadores().add(jug62);
    	cuotaJugador02.getListaJugadores().add(jug63);
  
    	//Asigno algunos pagos
    	
    	pagoClubServicio.crearPago("rc01", new LocalDate(2016, 4, 22), new BigDecimal(450), club01, cuotaClub01);
    	pagoClubServicio.crearPago("rc02", new LocalDate(2016, 8, 19), new BigDecimal(250), club01, cuotaClub01);
    	pagoClubServicio.crearPago("rc03", new LocalDate(2016, 12, 3), new BigDecimal(600), club02, cuotaClub01);
    	pagoClubServicio.crearPago("rc04", new LocalDate(2016, 12, 3), new BigDecimal(1750), club03, cuotaClub01);
    	pagoClubServicio.crearPago("rc05", new LocalDate(2016, 4, 22), new BigDecimal(230), club04, cuotaClub01);
    	pagoClubServicio.crearPago("rc06", new LocalDate(2016, 8, 19), new BigDecimal(550), club05, cuotaClub01);
    	pagoClubServicio.crearPago("rc07", new LocalDate(2016, 12, 3), new BigDecimal(200), club05, cuotaClub01);
    	pagoClubServicio.crearPago("rc08", new LocalDate(2016, 12, 3), new BigDecimal(1750), club06, cuotaClub01);

    	pagoJugadorServicio.crearPago("rj1", new LocalDate(2016, 3, 5), new BigDecimal(350), jug01, cuotaJugador01);
    	pagoJugadorServicio.crearPago("rj2", new LocalDate(2016, 4, 1), new BigDecimal(100), jug01, cuotaJugador01);
    	pagoJugadorServicio.crearPago("rj3", new LocalDate(2016, 8, 5), new BigDecimal(450), jug02, cuotaJugador01);
    	pagoJugadorServicio.crearPago("rj4", new LocalDate(2016, 8, 6), new BigDecimal(320), jug03, cuotaJugador01);
    	pagoJugadorServicio.crearPago("rj5", new LocalDate(2016, 7, 15), new BigDecimal(210), jug04, cuotaJugador01);
    	pagoJugadorServicio.crearPago("rj6", new LocalDate(2016, 12, 19), new BigDecimal(450), jug05, cuotaJugador01);

    	
    	
    	
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
    private PagoClubServicio pagoClubServicio;
    
    @javax.inject.Inject
    private PagoJugadorServicio pagoJugadorServicio;
   
    @javax.inject.Inject
    private CuotaClubServicio cuotaClubServicio;
    
    @javax.inject.Inject
    private CuotaJugadorServicio cuotaJugadorServicio;
    
    @javax.inject.Inject
    private JugadorServicio jugadorServicio;

}