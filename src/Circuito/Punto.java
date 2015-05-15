/*
    Simulator Times Track is a game that allows you to simulate lap times of one or more cars.
    For more information see the README.

    Copyright (C) 2014-2015  Samuel Civitarese, Andrea Langone, Domenico D'Uva.
	
    This file is part of Simulator Times Track.

    Simulator Times Track is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Simulator Times Track is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Simulator Times Track.If not, see <http://www.gnu.org/licenses/>.
 */

package Circuito;

public class Punto {
	
private double x;
private double y;

public Punto(){
	
}

public double getX() {
	return x;
}

public void setX(double x) {
	this.x = approssima_numero(x);
}

public double getY() {
	return y;
}

public void setY(double y) {
	this.y = approssima_numero(y);
}

private double approssima_numero(double numero){
	double valore = Math.floor(numero*100000)/100000;
	return valore;
	
}

}
