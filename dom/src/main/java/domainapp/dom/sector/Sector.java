package domainapp.dom.sector;

public enum Sector {
	DAMAS("Damas"), CABALLEROS("Caballeros");
	
	private final String nombre;

	public String getNombre() {return nombre;}
	
	private Sector(String nom) {nombre = nom;}

	@Override
	public String toString() {
		return this.nombre;
	}
}
