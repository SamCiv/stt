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
package campionato;

import gara.Gara;
import giocatore.Giocatore;

import java.util.ArrayList;
import java.util.Collections;

public class Campionato {
	
	private boolean gara_singola;
	
	private ArrayList<Giocatore> partecipanti = new ArrayList<Giocatore>(); //lista dei partecipanti
	private ArrayList<Gara> lista_gare = new ArrayList<Gara>(); //lista delle gare del campionato, nel caso di gara singola abbiamo una sola gara
    
	private int numero_giocatori = 0;
	private int gioc_reali = 0;
	
	private int gara_attuale = 0; //id della gara che dovr� essere svolta
	
	public ArrayList<Gara> getLista_gare() {
		return lista_gare;
	}

	public void setLista_gare(ArrayList<Gara> lista_gare) {
		this.lista_gare = lista_gare;
	}

	public boolean isGara_singola() {
		return gara_singola;
	}

	public void setGara_singola(boolean gara_singola) {
		this.gara_singola = gara_singola;
	}

	public ArrayList<Giocatore> getPartecipanti() {
		return partecipanti;
	}

	public void setPartecipanti(ArrayList<Giocatore> partecipanti) {
		this.partecipanti = partecipanti;
		this.numero_giocatori = partecipanti.size()-1;
	}

	public int getNumero_giocatori() {
		return numero_giocatori;
	}

	public int getGara_attuale() {
		return gara_attuale;
	}

	public void setGara_attuale(int gara_attuale) {
		this.gara_attuale = gara_attuale;
		
		
	}

	public static ArrayList<Giocatore> clonaPartecipanti(ArrayList<Giocatore> lista) 
    {
    	ArrayList<Giocatore> clone = new ArrayList<Giocatore>(lista.size());
        
        for(Giocatore item: lista) //per ogni oggetto Giocatore dentro lista
        	clone.add((Giocatore) item.clone());
        return clone;
        
    }

	public int getGioc_reali() {
		return gioc_reali;
	}

	public void setGioc_reali(int gioc_reali) {
		this.gioc_reali = gioc_reali;
	}

	

}
