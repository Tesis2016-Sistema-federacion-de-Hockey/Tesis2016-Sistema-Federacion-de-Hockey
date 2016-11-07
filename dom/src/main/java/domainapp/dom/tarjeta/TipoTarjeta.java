package domainapp.dom.tarjeta;

public enum TipoTarjeta {
	VERDE("Verde"),
	AMARILLA("Amarilla"),
	ROJA("Roja");
	
	private final String nombre;

	public String getNombre() {return nombre;}
	
	private TipoTarjeta(String nom) {nombre = nom;}

	@Override
	public String toString() {
		return this.nombre;
	}
}