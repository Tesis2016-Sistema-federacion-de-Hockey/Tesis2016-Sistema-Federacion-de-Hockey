package domainapp.dom.localidad;

public enum Localidad {
	NEUQUEN("Neuquén"),
	CENTENARIO("Centenario"),
	PLOTTIER("Plottier"),
	CHOS_MALAL("Chos Malal"),
	RINCON("Rincon de los Sauces"),
	ZAPALA("Zapala"),
	PLAZA_HUINCUL("Plaza Huincul")
	;
	
	private final String nombre;

	public String getNombre() {return nombre;}
	
	private Localidad(String nom) {nombre = nom;}

	@Override
	public String toString() {
		return this.nombre;
	}
}