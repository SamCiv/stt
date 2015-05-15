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
package pilota;

import java.util.Random;

public class Pilota {

	
	private String nome, cognome;
	
    private int aggressivita;         // skill che incide sul coeff. curve
    private int concentrazione;		//skill che incide su coeff. curve, consumo carburante, counsumo gomme
    private int esperienza;			//skill che incide su coeff. curve,  consumo carburante, counsumo gomme, coeff. rettilineo
    private int tecnica;			//skill che incide su coeff. curve, consumo carburante
    private int motivazione;		//skill che incide su coeff. rettilineo
    private int peso;
    private int eta;
    private int accelerazione;		//skill che incide su coeff. rettilineo
    private int frenata;			//skill che incide su coeff. curve
    private int totale;
    private final double pc=0.006;    //peso curva--->coefficiente di riduzione velocit� in curva per punto skill
 
    private final double pr=0.007;    //peso rettilineo--->coefficiente di riduzione velocit� in rettilineo per punto skill
    
    private final double pb=0.005;    //peso carburante--->coefficiente di riduzione carburante
    
    private final double pg=0.0001;
    
    
    public Pilota (){
    	
    }
    
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public int getAggressivita() {
		return aggressivita;
	}

	public void setAggressivita(int aggressivita) {
		if(aggressivita>0||aggressivita<100)
		this.aggressivita = aggressivita;
	}

	public int getConcentrazione() {
		return concentrazione;
	}

	public void setConcentrazione(int concentrazione) {
		if(concentrazione>0||concentrazione<100)
		this.concentrazione = concentrazione;
	}

	public int getEsperienza() {
		return esperienza;
	}

	public void setEsperienza(int esperienza) {
		if(esperienza>=0||esperienza<=100)
		this.esperienza = esperienza;
	}

	public int getTecnica() {
		return tecnica;
	}

	public void setTecnica(int tecnica) {
		if(tecnica>=0||tecnica<=100)
		this.tecnica = tecnica;
	}

	public int getMotivazione() {
		return motivazione;
	}

	public void setMotivazione(int motivazione) {
		if(motivazione>=0||motivazione<=100)
		this.motivazione = motivazione;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		if(peso>30||peso<120)
		this.peso = peso;
	}

	public int getEta() {
		return eta;
	}

	public void setEta(int eta) {
		if(eta>18||eta<50)
		this.eta = eta;
	}

	public int getAccelerazione() {
		return accelerazione;
	}

	public void setAccelerazione(int accelerazione) {
		if(accelerazione>=0||accelerazione<=100)
		this.accelerazione = accelerazione;
	}

	public int getFrenata() {
		return frenata;
	}

	public void setFrenata(int frenata) {
		if(frenata>=0||frenata<=100)
		this.frenata = frenata;
	}

	public int getTotale() {
		return totale;
	}

	public void setTotale() {
		
		int tot2 = calcoloTotale();
		this.totale=tot2;
	}

	private int calcoloTotale(){
		
	int tot = 0;
	 
	tot=((this.getAccelerazione()+this.getAggressivita()+this.getConcentrazione()+this.getEsperienza()+this.getFrenata()+this.getMotivazione()+this.getTecnica())/7);
	
	if(tot==100||tot==99||tot==98) 
			    return	tot;
	
	else
				tot= tot + 3;
	
	return tot;
	
	}
	

	public double coeffCurva(){ 
	
		double risultato = 0;
		
		Random random = new Random();
		double media = (concentrazione+esperienza+aggressivita+tecnica+frenata)/5; //media skill
		media = 15-(media*0.15);
		
		int skill = (int) (media + 101);
		int coeff_curva = (int) (random.nextInt(skill - 100) + 100);
		
		risultato = coeff_curva;		
		risultato /= 100;
		
		return risultato;
}
	
	
	public double coeffRettilineo(){
		
		double risultato = 0;
		
		Random random = new Random();
		double media = (motivazione+esperienza+accelerazione)/3; //media skill
		media = 10-(media*0.10);
		
		int skill = (int) (media + 101);
		int coeff_rettilineo = (int) (random.nextInt(skill - 100) + 100);
		
		risultato = coeff_rettilineo;		
		risultato /= 100;
		
		return risultato;
}
	public double coeffCarbunte(){
		
		double cb=0; //coeff. carburante
		double cbf=0; //coeff. carburante finale
		
		cb=((this.getEsperienza()+this.getConcentrazione()+this.getTecnica())*pb);//calcolo cb
		
		if(cb>0){
		
		cbf=1+(Math.random()*cb);  } //calcolo cbf
		
		else{
			
		cbf=1+(Math.random()*0.01);}
		
		return cbf;
		
	}
	
	public double coeffGomme(){ //influisce sul coefficiente d'attrito
		
		double cg=0;
		double cgf=0;
		
		cg=((this.getEsperienza()+this.getConcentrazione()+this.getTecnica())*this.pg);//calcolo cg
		
		if(cg>0) //calcolo cbf
		{		
		cgf=1-(Math.random()*cg);  
		} 
		
		else
		{			
		cgf=1-(Math.random()*0.01);
		}
		
		return cgf;
		    
	}
	
	public double coeffTrasmissioni(){
		
		double risultato;
		
		Random random = new Random();
		int ct= random.nextInt( (80- ((int) 0.7*this.getTecnica())));
		
		ct += 100;		
		risultato = ct;		
		risultato /= 100;
		
		return risultato;		
		
	}
	
	//influisce sulla durata delle gomme
	public double durataPneumatici()
	{
		double risultato = 0;
		
		Random random = new Random();
		double media = (concentrazione+esperienza+tecnica)/3; //media skill
		media *= 0.3;
		
		int skill = (int) (media + 71);
		int coeff_gomma = (int) (random.nextInt(130 - skill) + skill);
		
		risultato = coeff_gomma;		
		risultato /= 100;
		
		return risultato;
	}
	
}