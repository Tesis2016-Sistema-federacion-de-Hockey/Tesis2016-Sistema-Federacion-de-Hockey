package domainapp.dom.cuotaclub;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import domainapp.dom.club.Club;
import domainapp.dom.club.ClubServicio;
import domainapp.dom.cuota.Cuota;
import domainapp.dom.pagoclub.PagoClub;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "CuotaClub")
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="cuotaClub_id")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "traerTodos", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.cuotaclub.CuotaClub"),
    @javax.jdo.annotations.Query(
            name = "traerCuotaClub", language = "JDOQL",
            value = "SELECT "
                    + "FROM domainapp.dom.cuotaclub.CuotaClub "
                    + "WHERE this.listaClubes.contains(:club) ")
})
@javax.jdo.annotations.Unique(name="CuotaClub_UNQ", members = {"vencimiento"})
@DomainObject(bounded=true)
@DomainObjectLayout
public class CuotaClub extends Cuota implements Comparable<CuotaClub> {
	
	public TranslatableString title() {
		return TranslatableString.tr("{nombre}", "nombre",
				"Cuota de Club: " + this.getVencimiento());
	}
	
	public String iconName(){return "CuotaClub";}
	
	//LISTA DE PAGOS DE CLUB
	@MemberOrder(sequence = "5.1")
	@Persistent(mappedBy="cuotaClub", dependentElement="true")
	@CollectionLayout(named="Lista de Pagos de Club")
	private SortedSet<PagoClub> listaPagosClub = new TreeSet<PagoClub>();
	public SortedSet<PagoClub> getListaPagosClub() {return listaPagosClub;}
	public void setListaPagosClub(SortedSet<PagoClub> listaPagosClub) {this.listaPagosClub = listaPagosClub;}

	//LISTA DE CLUBES
  	@MemberOrder(sequence = "5")
  	@Persistent(mappedBy = "cuotasClub")
  	@CollectionLayout(named="Lista de Clubes")
  	private SortedSet<Club> listaClubes=new TreeSet<Club>();
  	public SortedSet<Club> getListaClubes() {return listaClubes;}
  	public void setListaClubes(final SortedSet<Club> listaClubes) {this.listaClubes = listaClubes;}

	//METODO PARA AGREGAR CUOTA	A UN CLUB	
	@MemberOrder(sequence = "6")
	@ActionLayout(
			describedAs="Asigna un CLUB a la lista de clubes que le corresponde pagar esta cuota",
			named="Agregar Club", cssClassFa="fa fa-plus")
	public CuotaClub agregarClubACuota(final Club e) {
		if(e == null || listaClubes.contains(e)) return this;
		listaClubes.add(e);
		e.getCuotasClub().add(this);
		return this;
	}
	
	//METODO PARA QUITAR CUOTA A UN CLUB	
	@MemberOrder(sequence = "7")
	@ActionLayout(
			describedAs="Desasigna un CLUB de la lista de clubes que no le corresponde pagar esta cuota",
			named="Quitar Club", cssClassFa="fa fa-minus")
	public CuotaClub quitarClubACuota(final Club e) {
		if(e == null || !listaClubes.contains(e)) return this;
		listaClubes.remove(e);
		e.getCuotasClub().remove(this);
		return this;
	}
	
	public List<Club> choices0AgregarClubACuota(){		
		return repositoryService.allMatches(Club.class, new Predicate<Club>() {
			@Override
			public boolean apply(Club club) {				
				return (clubServicio.listarTodosLosClubes().contains(club)&&!listaClubes.contains(club))?true:false;
			}
		});
	}
	
	public List<Club> choices0QuitarClubACuota(){
		
			return Lists.newArrayList(getListaClubes());
	}
	
	//METODO PARA AGREGAR TODAS LAS CUOTAS AL CLUB
	@MemberOrder(sequence = "8")
	@ActionLayout(
			describedAs="Asigna todos los clubes a la lista de clubes que deben pagar esta cuota",
			named="Agregar TODOS los clubes", cssClassFa="fa fa-thumbs-o-up")
	public CuotaClub agregarTodas(){
		for (Iterator<?> it=clubServicio.listarTodosLosClubes().iterator();it.hasNext();){
			Club clu=((Club)it.next());
			clu.getCuotasClub().add(this);
		}
		return this;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(final CuotaClub other) {
		// TODO Auto-generated method stub
		return ObjectContracts.compare(this, other, "temporada", "vencimiento");
	}
	
	@javax.inject.Inject
    RepositoryService repositoryService;
    
	@javax.inject.Inject
    ClubServicio clubServicio;	
}