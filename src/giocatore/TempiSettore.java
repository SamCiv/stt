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

public class TempiSettore 
{
	private int numero_giro = 0;
	
	private int pit_stop = 0; //numero di pit_stop parziali gi� effettuati
	
	private double settore_1 = 0; //tempo del 1� settore in secondi
	private double settore_2 = 0; //tempo del 2� settore in secondi
	private double settore_3 = 0; //tempo del 3� settore in secondi
	
	private double gap_1 = 0;
	private double gap_2 = 0;
	private double gap_3 = 0;
	
	private double tempo_totale = 0;

public TempiSettore(Giocatore partecipante) //costruttore iniziale
{
	calcola_tempi_parziali(partecipante);
}


private void calcola_tempi_parziali(Giocatore partecipante) //calcola il tempo di un giro, il risultato � espresso in secondi
{
	
	
	//divide il circuito in 3 settori uguali;
	double primo_settore = (partecipante.getLista_punti().size()-1)/3; //Indica il punto finale del primo settore/inizio del secondo settore
	double secondo_settore = primo_settore *2; //Indica la fine del secondo settore/inizio del terzo settore
			
	//Il primo settore parte dal traguardo e termina con l'inizio del secondo settore
	//Il secondo settore parte dalla fine del primo settore e termina con l'inizio del terzo settore
	//Il terzo settore parte dalla fine del secondo settore e finisce sul traguardo
	//La somma dei tempi dei tre settori ci fornisce il tempo totale.
		
	for(int i = 0;i<3;i++)
	{
	 if(i==0) setSettore_1(calcola_tempo_settore(partecipante, 0, primo_settore));
	 else if(i==1) setSettore_2(calcola_tempo_settore(partecipante, primo_settore, secondo_settore));
	 else setSettore_3(calcola_tempo_settore(partecipante, secondo_settore, partecipante.getLista_punti().size()));
	}
	
	double totale = getSettore_1()+getSettore_2()+getSettore_3();
	setTempo_totale(totale);
	
	
}

private double calcola_tempo_settore(Giocatore partecipante, double inizio, double fine) //calcola il tempo di un settore, prendendo come argomento il punto iniziale e finale del settore
{
	double tempo = 0;
	
	for(int f = (int) inizio;f < fine; f++)
	{		
		tempo += partecipante.getLista_punti().get(f).getTempo_parziale();		
	}
	
	return approssima_numero(tempo, 3);
}

public void converti_secondi()
{
	
	double totale = getSettore_1()+getSettore_2()+getSettore_3();
	
	int minuti= (int) (totale / 60); //since both are ints, you get an int
	int secondi_decimali = (int) (((totale / 60) - minuti)*100);
	int decimi_secondo = (int) (1000 * ((((totale / 60) - minuti)*100) - secondi_decimali));
	
	System.out.println(totale+ " "+minuti+":"+secondi_decimali+"."+decimi_secondo);

}


public double getSettore_1() {
	return settore_1;
}


public void setSettore_1(double settore_1) {
	this.settore_1 = settore_1;
}


public double getSettore_2() {
	return settore_2;
}


public void setSettore_2(double settore_2) {
	this.settore_2 = settore_2;
}


public double getSettore_3() {
	return settore_3;
}


public void setSettore_3(double settore_3) {
	this.settore_3 = settore_3;
}


public int getNumero_giro() {
	return numero_giro;
}


public void setNumero_giro(int numero_giro) {
	this.numero_giro = numero_giro;
}


public double getTempo_totale() {
	return tempo_totale;
}


public void setTempo_totale(double tempo_totale) {
	this.tempo_totale = tempo_totale;
}


public double getGap_1() {
	return gap_1;
}


public void setGap_1(double gap) {
	this.gap_1 = gap;
}


public double getGap_2() {
	return gap_2;
}


public void setGap_2(double gap_2) {
	this.gap_2 = gap_2;
}


public double getGap_3() {
	return gap_3;
}


public void setGap_3(double gap_3) {
	this.gap_3 = gap_3;
}


public int getPit_stop() {
	return pit_stop;
}


public void setPit_stop(int pit_stop) {
	this.pit_stop = pit_stop;
}

private double approssima_numero(double numero, double n){
	double temp = Math.pow(10, n);
	double valore = Math.round(numero*temp)/temp;
	return valore;
}

}
