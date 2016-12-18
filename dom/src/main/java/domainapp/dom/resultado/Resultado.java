package domainapp.dom.resultado;

public enum Resultado {
	GANADO("Ganado"), PERDIDO("Perdido"),EMPATADO("Empatado"),SINRESULTADO("Sin resultado");
	
	private final String nombre;

	public String getNombre() {return nombre;}
	
	private Resultado(String nom) {nombre = nom;}

	@Override
	public String toString() {
		return this.nombre;
	}
}
