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
package auto;

import java.util.Hashtable;

public class Trasmissione {

//dati presenti nella tabella Trasmissione
private int id_cambio;
private String nome;
private int n_rapporti;
private double rapporto_finale;
private double tempo_cambio;

//dati presenti nella tabella RapportiTrasmissione
//HashTable di tipo Generics
private Hashtable<Integer, Double> rapporti = new Hashtable<Integer, Double>(); //Come chiave utilizzo il numero del rapporto (es.1 marcia) e come valore il fattore di riduzione per quel rapporto
//private Hashtable<Integer, Double> vel_rapporti = new Hashtable<Integer, Double>();

public Trasmissione(){
	
}

public Hashtable getRapporto() {
	return rapporti;
}

public void setRapporti(int n_rapporto, double riduzione) {
	
	this.rapporti.put(n_rapporto, riduzione);
}

public int getId_cambio() {
	return id_cambio;
}

public void setId_cambio(int id_cambio) {
	this.id_cambio = id_cambio;
}

public int getN_rapporti() {
	return n_rapporti;
}

public void setN_rapporti(int n_rapporti) {
	this.n_rapporti = n_rapporti;
}

public double getRapporto_finale() {
	return rapporto_finale;
}

public void setRapporto_finale(double rapporto_finale) {
	this.rapporto_finale = rapporto_finale;
}

public double getRiduzione(int n_rapporto){
	
	return this.rapporti.get(n_rapporto);
}

public double getTempo_cambio() {
	return tempo_cambio;
}

public void setTempo_cambio(double tempo_cambio) {
	this.tempo_cambio = tempo_cambio;
}

public String getNome() {
	return nome;
}

public void setNome(String nome) {
	this.nome = nome;
}

	
}
