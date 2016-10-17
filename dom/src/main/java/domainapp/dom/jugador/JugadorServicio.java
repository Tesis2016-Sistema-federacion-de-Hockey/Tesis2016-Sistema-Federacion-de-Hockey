package domainapp.dom.jugador;

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

    public TranslatableString title() {return TranslatableString.tr("Jugadores");}

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
    		cssClassFa="fa fa-list",
            bookmarking = BookmarkPolicy.AS_ROOT
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
				// TODO Auto-generated method stub
				return input.getEstado() == Estado.ACTIVO ? true : false;
			}
		});
	}
    
    @MemberOrder(sequence = "3")
	public List<Jugador> listarJugadoresInactivos() {
		return repositoryService.allMatches(Jugador.class, new Predicate<Jugador>() {

			@Override
			public boolean apply(Jugador input) {
				// TODO Auto-generated method stub
				return input.getEstado() == Estado.INACTIVO ? true : false;
			}
		});
	}
    
    @MemberOrder(sequence = "3.1")
    public List<Jugador> listarJugadoresSinClub() {
		return repositoryService.allMatches(Jugador.class, new Predicate<Jugador>() {

			@Override
			public boolean apply(Jugador input) {
				// TODO Auto-generated method stub
				return input.getClub()==null?true:false;
			}
		});
	}
    
    @MemberOrder(sequence = "3.2")
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
            domainEvent = CreateDomainEvent.class
    )
    @ActionLayout(
    		cssClassFa="fa fa-plus-square"
    )
    @MemberOrder(sequence = "4")
    public Jugador crearJugador(
            final @ParameterLayout(named="Sector") @Parameter(optionality=Optionality.OPTIONAL) Sector sector,
            final @ParameterLayout(named="Ficha") String ficha,
            final @ParameterLayout(named="Nombre") String nombre,
            final @ParameterLayout(named="Apellido") String apellido,
            final @ParameterLayout(named="Tipo de Documento") @Parameter(optionality=Optionality.OPTIONAL) TipoDocumento tipo,
            final @ParameterLayout(named="Documento") @Parameter(optionality=Optionality.OPTIONAL) String documento,
            final @ParameterLayout(named="Fecha de Nacimiento") @Parameter(optionality=Optionality.OPTIONAL) LocalDate fechaNacimiento,
            final @ParameterLayout(named="Estado") @Parameter(optionality=Optionality.OPTIONAL) Estado estado,
            final @ParameterLayout(named="Email") @Parameter(optionality=Optionality.OPTIONAL) String email,
            final @ParameterLayout(named="Calle") @Parameter(optionality=Optionality.OPTIONAL) String calle,
            final @ParameterLayout(named="Numero") @Parameter(optionality=Optionality.OPTIONAL) String numero,
            final @ParameterLayout(named="Piso") @Parameter(optionality=Optionality.OPTIONAL) String piso,
            final @ParameterLayout(named="Departamento") @Parameter(optionality=Optionality.OPTIONAL) String departamento,
            final @ParameterLayout(named="Telefono") @Parameter(optionality=Optionality.OPTIONAL) String telefono,
            final @ParameterLayout(named="Celular") @Parameter(optionality=Optionality.OPTIONAL) String celular,
            final @ParameterLayout(named="Club") @Parameter(optionality=Optionality.OPTIONAL) Club club,
            final @ParameterLayout(named="Equipo") @Parameter(optionality=Optionality.OPTIONAL) Equipo equipo
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
        obj.setEquipo(equipo);
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
            final Club clubs
    		){    	
		return repositoryService.allMatches(QueryDefault.create(Equipo.class, "traerEquipo", "club", clubs));
		
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