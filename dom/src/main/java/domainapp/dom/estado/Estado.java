package domainapp.dom.estado;

public enum Estado {
	ACTIVO("Activo"), INACTIVO("Inactivo");
	
	private final String nombre;

	public String getNombre() {return nombre;}
	
	private Estado(String nom) {nombre = nom;}

	@Override
	public String toString() {
		return this.nombre;
	}


}
