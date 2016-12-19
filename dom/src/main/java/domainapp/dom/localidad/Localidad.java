/*
#	This file is part of SIFHON.
#
#	Copyright ( C ) 2016 , SIFHON
#
#   SIFHON is free software: you can redistribute it and/or modify
#   it under the terms of the GNU General Public License as published by
#   the Free Software Foundation, either version 3 of the License, or
#   (at your option) any later version.
#
#   SIFHON is distributed in the hope that it will be useful,
#   but WITHOUT ANY WARRANTY; without even the implied warranty of
#   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#   GNU General Public License for more details.
#
#   You should have received a copy of the GNU General Public License
#   along with SIFHON.  If not, see <http://www.gnu.org/licenses/>.
*/

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