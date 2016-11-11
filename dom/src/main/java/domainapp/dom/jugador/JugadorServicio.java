package domainapp.dom.jugador;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.List;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;

import com.google.common.base.Predicate;

import domainapp.dom.club.Club;
import domainapp.dom.domicilio.Domicilio;
import domainapp.dom.equipo.Equipo;
import domainapp.dom.equipo.EquipoServicio;
import domainapp.dom.estado.Estado;
import domainapp.dom.sector.Sector;
import domainapp.dom.tipodocumento.TipoDocumento;

@SuppressWarnings("deprecation")
@DomainService(
        nature = NatureOfService.VIEW,
        repositoryFor = Jugador.class
)
@DomainServiceLayout(
        menuOrder = "1",
        named="Jugadores"
)
public class JugadorServicio{
	
	final LocalDate fecha_actual = LocalDate.now();

    public TranslatableString title() {return TranslatableString.tr("Jugadores");}

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Listar Jugadores de la Base Datos"
    )
    @MemberOrder(sequence = "1")
    public List<Jugador> listarTodosLosJugadores() {
        return repositoryService.allInstances(Jugador.class);
    }
    
    @MemberOrder(sequence = "2")
	public List<Jugador> listarJugadoresActivos() {
		return repositoryService.allMatches(Jugador.class, new Predicate<Jugador>() {

			@Override
			public boolean apply(Jugador input) {
				return input.getEstado() == Estado.ACTIVO ? true : false;
			}
		});
	}
    
    @MemberOrder(sequence = "3")
	public List<Jugador> listarJugadoresInactivos() {
		return repositoryService.allMatches(Jugador.class, new Predicate<Jugador>() {

			@Override
			public boolean apply(Jugador input) {
				return input.getEstado() == Estado.INACTIVO ? true : false;
			}
		});
	}
    
    @ActionLayout(named="Listar Jugadores Libres")
    @MemberOrder(sequence = "4")
    public List<Jugador> listarJugadoresSinClub() {
		return repositoryService.allMatches(Jugador.class, new Predicate<Jugador>() {

			@Override
			public boolean apply(Jugador input) {
				return input.getClub()==null?true:false;
			}
		});
	}
    
    @ActionLayout(named="Listar Jugadores Activos por Club")
    @MemberOrder(sequence = "5")
    public List<Jugador> listarJugadoresActivosSegunClub(Club club) {
    	
    	return repositoryService.allMatches(new QueryDefault<Jugador>(Jugador.class, "listarJugadoresActivosSegunClub", "club", club));
    	
    }
    
    public static class CreateDomainEvent extends ActionDomainEvent<JugadorServicio> {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CreateDomainEvent(final JugadorServicio source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }
    
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-search",
            bookmarking = BookmarkPolicy.AS_ROOT,
            named="Buscar por Documento"
    )
    @MemberOrder(sequence = "6")
    public List<Jugador> buscarPorDocumento(
            @ParameterLayout(named="Ingrese el documento")
            final String documento
    ) {
    	return repositoryService.allMatches(
                new QueryDefault<Jugador>(
                        Jugador.class,
                        "buscarPorDocumento",
                        "documento", documento));
    }

    @Action(
            domainEvent = CreateDomainEvent.class
    )
    @ActionLayout(
    		cssClassFa="fa fa-plus-square"
    )
    @MemberOrder(sequence = "7")
    public Jugador crearJugador(
            final @ParameterLayout(named="Sector") Sector sector,
            final @ParameterLayout(named="Ficha") String ficha,
            final @ParameterLayout(named="Nombre") String nombre,
            final @ParameterLayout(named="Apellido") String apellido,
            final @ParameterLayout(named="Tipo de Documento") TipoDocumento tipo,
            final @ParameterLayout(named="Documento") String documento,
            final @ParameterLayout(named="Fecha de Nacimiento") LocalDate fechaNacimiento,
            final @ParameterLayout(named="Estado") Estado estado,
            final @ParameterLayout(named="Email") @Parameter(optionality=Optionality.OPTIONAL) String email,
            final @ParameterLayout(named="Calle") @Parameter(optionality=Optionality.OPTIONAL) String calle,
            final @ParameterLayout(named="Numero") @Parameter(optionality=Optionality.OPTIONAL) String numero,
            final @ParameterLayout(named="Piso") @Parameter(optionality=Optionality.OPTIONAL) String piso,
            final @ParameterLayout(named="Departamento") @Parameter(optionality=Optionality.OPTIONAL) String departamento,
            final @ParameterLayout(named="Telefono") @Parameter(optionality=Optionality.OPTIONAL) String telefono,
            final @ParameterLayout(named="Celular") @Parameter(optionality=Optionality.OPTIONAL) String celular,
            final @ParameterLayout(named="Club") @Parameter(optionality=Optionality.OPTIONAL) Club club,
            final @ParameterLayout(named="Equipo") @Parameter(optionality=Optionality.OPTIONAL) Equipo equipo //Pido el equipo, para usarlo en los fixtures. Sin embargo no guarda la referencia
    		){
        final Jugador obj = repositoryService.instantiate(Jugador.class);
        final Domicilio domicilio=new Domicilio();
        domicilio.setCalle(calle);
        domicilio.setNumero(numero);
        domicilio.setPiso(piso);
        domicilio.setDepartamento(departamento);
        obj.setSector(sector);
        obj.setFicha(ficha);
        obj.setNombre(nombre);
        obj.setApellido(apellido);
        obj.setTipo(tipo);
        obj.setDocumento(documento);
        obj.setFechaNacimiento(fechaNacimiento);
        obj.setEstado(estado);
        obj.setEmail(email);
        obj.setTelefono(telefono);
        obj.setCelular(celular);
        obj.setDomicilio(domicilio);
        repositoryService.persist(obj);
        obj.setClub(club);
        return obj;
    }
    
    public Estado default7CrearJugador(){    	
    	return Estado.ACTIVO;
    }
    
    public TipoDocumento default4CrearJugador(){
    	return TipoDocumento.DNI;
    }
    
    public List<Equipo> choices16CrearJugador(
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
            final Club clubs,
            final Equipo equipo
    		){    	
		return repositoryService.allMatches(QueryDefault.create(Equipo.class, "traerEquipo", "club", clubs));
    }
    
    public String validateCrearJugador(
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
    		){
    	final Jugador jug = repositoryService.firstMatch(QueryDefault
				.create(Jugador.class, "buscarDocumentoDuplicado",
						"documento", documento));
		if (jug != null) {
			if (jug.getDocumento().equals(documento)) {
				return "Ya existe un Jugador con este numero de documento: "
						+ documento;
			}
		}
    	if (fechaNacimiento.isAfter(fecha_actual))
			return "Fecha de Nacimiento mayor a la fecha actual";
		if (validaMayorCien(fechaNacimiento) == false)
			return "Jugador mayor a 100 años";
    	return "";
    }

	//VALIDO LA EDAD MAXIMA DEL JUGADOR INGRESADO.
	//DEBE TENER MENOS DE 100 AÑOS, ES DECIR, MENOS DE 36525 DIAS
	@ActionLayout(hidden = Where.EVERYWHERE)
	public boolean validaMayorCien(LocalDate fechadeNacimiento) {
		if (getDiasNacimiento_Hoy(fechadeNacimiento) <= 36525) {
			return true;
		}
		return false;
	}
	
	//CALCULO LA CANTIDAD DE DIAS ENTRE HOY Y CUANDO NACIO.
	@ActionLayout(hidden = Where.EVERYWHERE)
	public int getDiasNacimiento_Hoy(LocalDate fechadeNacimiento) {
		Days meses = Days.daysBetween(fechadeNacimiento, fecha_actual);
		return meses.getDays();
	}
    
    @ActionLayout(hidden = Where.EVERYWHERE)
	public List<Jugador> buscarJugador(String jugador) {
		return repositoryService.allMatches(QueryDefault
				.create(Jugador.class, "traerTodos"));
	}

    @javax.inject.Inject
    EquipoServicio equipoServicio;
    
    @javax.inject.Inject
    RepositoryService repositoryService;
}