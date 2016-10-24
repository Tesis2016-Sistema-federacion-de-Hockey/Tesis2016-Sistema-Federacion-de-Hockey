package domainapp.dom.modalidad;

public enum Modalidad {
	IDA("Ida"), IDA_Y_VUELTA("Ida y Vuelta");
	
	private final String nombre;

	public String getNombre() {return nombre;}
	
	private Modalidad(String nom) {nombre = nom;}

	@Override
	public String toString() {
		return this.nombre;
	}
}