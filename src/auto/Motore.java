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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;


public class Motore {	
	
  private String NomeMotore;
  private int Id_motore;
  
  private int cilindrata;
  private int cilindri;
  private int rpm_max;
  private double corsa;
  private double alesaggio;
  private double potmax;
  private double coppiamax;
  private double consumo_rpm;
  
  private boolean turbo;
  private ArrayList<Curva_motore> curva = new ArrayList<Curva_motore>();  
  
 

	public Motore() {}

	public int getCilindrata() {
		return cilindrata;
	}

	public void setCilindrata(int cilindrata) {
		this.cilindrata = cilindrata;
	}

	public double getCorsa() {
		return corsa;
	}

	public void setCorsa(double corsa) {
		this.corsa = corsa;
	}

	public double getAlesaggio() {
		return alesaggio;
	}

	public void setAlesaggio(double alesaggio) {
		this.alesaggio = alesaggio;
	}

	public double getCoppiamax() {
		return coppiamax;
	}

	public void setCoppiamax(double coppiamax) {
		this.coppiamax = coppiamax;
	}

	public double getPotmax() {
		return potmax;
	}

	public void setPotmax(double potmax) {
		this.potmax = potmax;
	}

	public String getNomeMotore() {
		return NomeMotore;
	}

	public void setNomeMotore(String nomeMotore) {
		NomeMotore = nomeMotore;
	}

	public int getCilindri() {
		return cilindri;
	}

	public void setCilindri(int cilindri) {
		this.cilindri = cilindri;
	}
	
	public boolean getTurbo(){
		return turbo;
	}
	
	public void setTurbo(boolean turbo){
		this.turbo = turbo;
	}
	
	public void setConsumo(int temperatura)
	{
	
		double densita = setDensita(temperatura);
		double volume_unitario = this.cilindrata/this.cilindri; //volume di un singolo cilindro
		
		volume_unitario = volume_unitario * 0.000001;
		
		double volume_aria = 0;	
		
		//scelta del coefficiente di riempimento
		if(this.turbo) volume_aria=volume_unitario * 1.3;		
		else  volume_aria=volume_unitario * 0.8;
		
		double massa_aria = densita * volume_aria;
		
		double massa_combustibile_cilindro = massa_aria/(14.5*1.3);
		
		double portata_combustibile = massa_combustibile_cilindro * this.cilindri * 1/60 * 0.5; //consumo di carburante per giro motore
		
		this.consumo_rpm = portata_combustibile;
		
		
	}
	
	public double getConsumo(){
		return this.consumo_rpm;
	}

	
	public double get_Potenza(double rpm){//calcola la potenza del motore ad un determinato numero di giri (con un determinato rapporto)
		
		Iterator<Curva_motore> iteratore = this.curva.iterator();
		
		double rpm_min = 0;
		double rpm_max = 0;
		double cop_min = 0;
		double cop_max = 0;
		
				
		while (iteratore.hasNext()) {//ciclo che permette di individuare all'interno di quale intervallo � contenuto il mio numero di giri
			
			
			Curva_motore curva_motore = (Curva_motore) iteratore.next();
			
			double rpm_it = curva_motore.getRpm();
			
			
			if(rpm > rpm_it){ //se rpm � maggiore imposto come valore minimo il valore attuale di rpm dell'iteratore
				rpm_min = rpm_it;
				cop_min =  curva_motore.getCoppia();
			}
			else //si verifica quando ho trovato l'intervallo di appartenenza, quindi imposto il valore max
			{
				rpm_max = rpm_it;	
				cop_max = curva_motore.getCoppia();
				break;
			}
			
		}
		
		//ho trovato la retta passante per due punti, quindi calclo il coefficiente angolare		
		double m = ((cop_max-cop_min)/(rpm_max-rpm_min));
		//calcolo il corrispondente valore della coppia rispetto al numero di rpm assegnato
	    double coppia_attuale = cop_min + m*(rpm-rpm_min);
		//ritorno il valore espresso in Potenza kW
	   // return (coppia_attuale*rpm/9549);
	     return ((coppia_attuale*rpm*2*Math.PI)/60);// Potenza in W
	}
	
	
	
	
	
	
	
	
	
//	public void getDati(){
//		
//		System.out.println("Nome: " +this.getNomeMotore());
//		System.out.println("Cilidrata: "+this.getCilindrata());
//		System.out.println("Corsa: " +this.getCorsa());
//		System.out.println("Alesaggio: " +this.getAlesaggio());
//		System.out.println("Cilindri: "+this.getCilindri());	
//		System.out.println("PotMax: "+this.getPotmax());
//		System.out.println("CoppiaMax: "+this.getCoppiamax());
//	}

	
	public ArrayList<Curva_motore> getCurva() {
		return curva;
	}

	public void setCurva(int rpm, double coppia) {
		
		Curva_motore dato = new Curva_motore();
		dato.setRpm(rpm);
		dato.setCoppia(coppia);
		
		this.curva.add(dato);
	}

	private double setDensita(int temperatura) { //Imposta la densit� a seconda della temperatura
		return (100000)/((273.15+temperatura)*287.5);
	}

	public int getId_motore() {
		return Id_motore;
	}

	public void setId_motore(int id_motore) {
		Id_motore = id_motore;
	}

	public int getRpm_max() {
		return rpm_max;
	}

	public void setRpm_max(int rpm_max) {
		this.rpm_max = rpm_max;
	}
	

	
}
