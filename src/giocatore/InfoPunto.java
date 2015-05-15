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
package giocatore;


//Questa classe salva memorizza le informazioni per ciasucn punto del tracciato

public class InfoPunto implements Cloneable {
	
	private double velocita_attuale = 0; //in m/s
	private int rapporto_attuale = 0;
	private double potenza_attuale = 0;
	private double rpm_attuali = 0;
	private double tempo_parziale = 0;
	private double lunghezza = 0;
	private double consumo_medio = 0;
	private double massa_carburante;
	
	
	
	
	
	public double getVelocita_attuale() {
		return velocita_attuale;
	}
	public void setVelocita_attuale(double velocita_attuale) {
		this.velocita_attuale = velocita_attuale;
	}
	public int getRapporto_attuale() {
		return rapporto_attuale;
	}
	public void setRapporto_attuale(int rapporto_attuale) {
		this.rapporto_attuale = rapporto_attuale;
	}
	public double getPotenza_attuale() {
		return potenza_attuale;
	}
	public void setPotenza_attuale(double potenza_attuale) {
		this.potenza_attuale = potenza_attuale;
	}
	public double getRpm_attuali() {
		return rpm_attuali;
	}
	public void setRpm_attuali(double rpm_attuali) {
		this.rpm_attuali = rpm_attuali;
	}
	public double getTempo_parziale() {
		return tempo_parziale;
	}
	public void setTempo_parziale(double tempo_parziale) {
		this.tempo_parziale = tempo_parziale;
	}
	

	
	public Object clone() 
	{
		try {
			return super.clone();
			} 
		catch (CloneNotSupportedException e) 
		{
		e.printStackTrace(); 
		return null; 
		} 
	}
	
	public double getLunghezza() {
		return lunghezza;
	}
	public void setLunghezza(double lunghezza) {
		this.lunghezza = lunghezza;
	}
	public double getConsumo_medio() {
		return consumo_medio;
	}
	public void setConsumo_medio(double consumo_medio) {
		this.consumo_medio = consumo_medio;
	}
	public double getMassa_carburante() {
		return massa_carburante;
	}
	public void setMassa_carburante(double massa_carburante) {
		this.massa_carburante = massa_carburante;
	}

}
