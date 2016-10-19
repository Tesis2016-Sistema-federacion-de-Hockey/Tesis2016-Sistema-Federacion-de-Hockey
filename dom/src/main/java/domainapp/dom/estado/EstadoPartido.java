package domainapp.dom.estado;

public enum EstadoPartido {
	FINALIZADO("Finalizado"), PENDIENTE("Pendiente");
	
	private final String nombre;

	public String getNombre() {return nombre;}
	
	private EstadoPartido(String nom) {nombre = nom;}

	@Override
	public String toString() {
		return this.nombre;
	}
}