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

import java.util.Iterator;

public class Auto implements Cloneable{
	
    //Definizione attributi classe Automobile
	private String Nome;
	private String Marca;
	
	private Motore motore;
	private Pneumatico pneumatico;
	
	private double cd;
	private double area;
	private int serbatoio;
	
	private Trasmissione cambio;
	
	private double fd; //Forza relativa al drag dell'auto
	private int massa_auto;
	
	private int rpm_min;
	private int rpm_max;
	
	public double calcolo_rpm_attuali(double velocita, int n_rapporto)//calcola il numero di giri del motore conoscendo la velocita di marcia e il rapporto utilizzato
	{
		//double rpm = velocita * ((cambio.getRiduzione(n_rapporto)*cambio.getRapporto_finale())/(60*pneumatico.getCirconferenza_rotolamento()))*1000; //velocit� in km/h
		
		double rpm = velocita * ((cambio.getRiduzione(n_rapporto)*cambio.getRapporto_finale())/(pneumatico.getCirconferenza_rotolamento()))*60; //velocit� in m/s
		
		return rpm;
		//System.out.println(rpm);
		
		//System.out.println(motore.get_Potenza(rpm));	
	
	}
	
	
    //calcola la velocit� dato un certo rapporto ed un certo numero di giri del motore, da utilizzare durante il cambio del rapporto

	public double calcola_velocita_rapporto(int RPM, int n_rapporto) 
	{
		double distanza = distanza_percorsa_motore(n_rapporto);
		
		double velocita = (distanza * RPM)/60 ;	//*0,06
		
		//System.out.println(velocita);
		return velocita;
	}
	
	private double distanza_percorsa_motore(int n_rapporto) //calcolo la distanza percorsa con 1g/motore con un determinato rapporto
	{
	 double riduzione = cambio.getRiduzione(n_rapporto);
	 double distanza = pneumatico.getCirconferenza_rotolamento()/(riduzione*cambio.getRapporto_finale());
	 
	 return distanza;
	}

	public double getFd() {
		return fd;
	}
	
	public void setFd(){//, double temperatura) {
		//In realt� dovrebbe esserci la velocit� al quadrato
		this.fd = getCd()*(0.5*getArea()); //Fd=cd*1/2*densit�*A 
	}
	
//     private double getDensita(double temperatura){
//		
//		return (100000)/((273.15+temperatura)*287.5);	//Calcola la densit� dell'aria data una certa temperatura
//     }
	
	
	
	
	public Trasmissione getTrasmissione() {
		return cambio;
	}

	public void setTrasmissione(Trasmissione cambio) {
		this.cambio = cambio;
	}
	
	
	public Pneumatico getPneumatico() {
		return pneumatico;
	}

	public void setPneumatico(Pneumatico pneumatico) {
		this.pneumatico = pneumatico;
	}
	
	public Motore getMotore() {
		return motore;
	}

	public void setMotore(Motore motore) {
		this.motore = motore;
	}




	public double getArea() {
		return area;
	}




	public void setArea(double area) {
		this.area = area;
	}




	public double getCd() {
		return cd;
	}




	public void setCd(double cd) {
		this.cd = cd;
	}




	public int getMassa_auto() {
		return massa_auto;
	}




	public void setMassa_auto(int massa_auto) {
		this.massa_auto = massa_auto;
	}


	public int getRpm_min() {
		return rpm_min;
	}


	public void setRpm_min(int rpm_min) {
		this.rpm_min = rpm_min;
	}


	public int getRpm_max() {
		return rpm_max;
	}


	public void setRpm_max(int rpm_max) {
		this.rpm_max = rpm_max;
	}


	public String getNome() {
		return Nome;
	}


	public void setNome(String nome) {
		Nome = nome;
	}


	public String getMarca() {
		return Marca;
	}


	public void setMarca(String marca) {
		Marca = marca;
	}


	public int getSerbatoio() {
		return serbatoio;
	}


	public void setSerbatoio(int serbatoio) {
		this.serbatoio = serbatoio;
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


	public void setRpm() 
	{
		int max_rpm = motore.getCurva().get(motore.getCurva().size()-1).getRpm();
		int min_rpm = motore.getCurva().get(0).getRpm();
		
		rpm_min = min_rpm + 500;
		rpm_max = max_rpm - 500;
		
	}

	
	
	
}
