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

import java.util.ArrayList;
import java.util.Iterator;

import auto.Curva_motore;

public class Circuito {
	
	private String nome;
	private String nazione;
	
	private int id_start = 0;
	private int ultimo_punto = 0;
	private double lunghezza_giro = 0;
	
	private ArrayList<Segmento> segmento = null;//new ArrayList<Segmento>();
	
	private ArrayList<Punto> lista_punti = null;//new ArrayList<Punto>();
	//private int contatore = 0; //conta i segmenti inseriti nel circuito
	
	public Circuito(){}
	
	private void aggiungi_segmento(Segmento segmento)
	{
		//aggiungo il segmento al circuito
		if(this.segmento == null) this.segmento = new ArrayList<Segmento>();	
		this.segmento.add(segmento);
		
	}

	public void aggiungi_curva(Segmento curva) 
	{ 
		//aggiunge una curva al segmento successivo	
			curva.disegna_curva(); //Calcola i punti di una curva		
			aggiungi_segmento((curva)); //aggiunge la curva ai segmenti del circuito
				
	}
	
	public void aggiungi_rettilineo(Segmento rettilineo, boolean iniziale) 
	{ 
		//aggiunge una curva al segmento successivo	
		rettilineo.calcolaPunti_Rettilineo_Generico(iniziale); //Calcola i punti del rettilineo	
		aggiungi_segmento((rettilineo)); //aggiunge il rettilineo ai segmenti del circuito
				
	}
		
	//restituisce la lista dei segmenti di un circuito
	public ArrayList<Segmento> get_segmenti()
	{
			return this.segmento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public ArrayList<Punto> getLista_punti() {
		return lista_punti;
	}

	public void aggiungi_punto(Punto punto) {
		if(this.lista_punti == null) {
			this.lista_punti = new ArrayList<Punto>();
		}
		
		this.lista_punti.add(punto);
	}
	
	
//calcola la distanza e l'eventulae raggio tra tre punti del circuito
	public double calcola_raggio(int pos_iniziale){ //po iniziale rappresenta la posizione attuale dell'auto
		
//		Iterator<Punto> it = this.lista_punti.listIterator();
		pos_iniziale--;
		Punto[] punto = new Punto[3];
		
		if(pos_iniziale==-1) //caso in cui mi trovo nel punto iniziale 0, e mi serve l'ultimo punto
		{
			pos_iniziale = this.lista_punti.size()-1;
			
		}
//		else if(pos_iniziale>752)
//		{
//			 resto = pos_iniziale/(this.lista_punti.size()-1) - 1;
//		}
//		
		
		
		
//		int n = 1;
//		
//		while(n<pos_iniziale-1)
//		{
//			it.next();
//			n++;
//		}
		
		for (int i = 0; i < 3; i++) 
		{						
		
			if(pos_iniziale+i>=this.lista_punti.size()) pos_iniziale = -1; //caso in cui sto effettuando un nuovo giro
			punto[i] = (Punto) this.lista_punti.get(pos_iniziale+i);
		}
		
		double a = Math.sqrt(Math.pow((punto[2].getX()-punto[0].getX()),2)+Math.pow((punto[2].getY()-punto[0].getY()), 2));
		//a = approssima_numero(a,4);
		double b = Math.sqrt(Math.pow((punto[2].getX()-punto[1].getX()),2)+Math.pow((punto[2].getY()-punto[1].getY()), 2));
		//b = approssima_numero(b,4);
		double c = Math.sqrt(Math.pow((punto[1].getX()-punto[0].getX()),2)+Math.pow((punto[1].getY()-punto[0].getY()), 2));
		//c = approssima_numero(c,4);
		
		double cosA =((Math.pow(b, 2) + Math.pow(c, 2) - Math.pow(a, 2))/(2*b*c));
		double angolo = Math.acos(cosA);
		//angolo = approssima_numero(angolo, 7);
		double raggio = 0;
		
		double differenza = approssima_numero((Math.PI-angolo),5);
		if(differenza!=0) raggio = Math.floor((a/(2*Math.sin(differenza)))); //!Double.isNaN(differenza)||!Double.isInfinite(differenza)
		else raggio = 0;
		
		return raggio;
	}
	
   public double calcola_distanza(int pos_iniziale){ //pos_iniziale rappresenta la posizione attuale dell'auto
		
		Punto[] punto = new Punto[2];
		
		for (int n = 0; n < 2; n++) 
		{			
		  if(pos_iniziale+n<this.lista_punti.size()) 
		  punto[n] = (Punto) this.lista_punti.get(pos_iniziale+n);
		  else punto[n] = (Punto) this.lista_punti.get(0);
		}
		
		double distanza = Math.sqrt(Math.pow((punto[1].getX()-punto[0].getX()),2)+Math.pow((punto[1].getY()-punto[0].getY()), 2)); //Misura la distanza fra due punti A e B
		
		distanza = approssima_numero(distanza, 2);	
		
		return distanza;
	}
	

	private double approssima_numero(double numero, double n){
		double temp = Math.pow(10, n);
		double valore = Math.round(numero*temp)/temp;
		return valore;
	}

	public void aggiungiFinishLine(Punto finishLine) 
	{				
		Segmento segmento_iniziale = this.segmento.get(0); //rettilineo iniziale
		
			for (int i = 0; i < segmento_iniziale.get_lista_punti().size(); i++) //serve per scorrere la lista dei punti del circuito, siccome abbiamo inserito solamente il segmento iniziale ci sono solamente i suoi punti
			{
				double x_temp = segmento_iniziale.get_lista_punti().get(i).getX();
				
				if(finishLine.getX()< x_temp)
				{
				segmento_iniziale.get_lista_punti().add(i, finishLine);
				//this.lista_punti.add(i-1, finishLine);
				this.id_start = i;
				break;
				}
				
			}		
	}

	public int getId_start() {
		return id_start;
	}

	public void setId_start(int id_start) {
		this.id_start = id_start;
	}

	public double getLunghezza_giro() {
		if(this.lunghezza_giro!=0)
			
			return lunghezza_giro;
		else
		{
			setLunghezza_giro();
			return lunghezza_giro;
		}
	}

	private void setLunghezza_giro() 
	{
		double lunghezza_giro = 0;
		
		if(!this.lista_punti.isEmpty())
		for (int i = 0; i < this.lista_punti.size(); i++) 
		{
			lunghezza_giro += calcola_distanza(i);			
		}
		
		this.lunghezza_giro = approssima_numero(lunghezza_giro, 2);
	}

	public int getUltimo_punto() {
		return ultimo_punto;
	}

	public void setUltimo_punto(int ultimo_punto) {
		this.ultimo_punto = ultimo_punto;
	}
}
