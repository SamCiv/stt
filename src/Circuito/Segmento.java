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

import org.sqlite.Function.Aggregate;

public class Segmento {

	
		
	private double lunghezza;
	private double raggio;//il segno - significa che la curva � a destra
	private double m =0;
	private double ang_finale;
	private double ang_iniziale;
	
	private Punto coordinate_iniziali = new Punto();
	private Punto coordinate_finali = new Punto();
	private Punto centro_circonferenza = new Punto();
	
	ArrayList<Punto> lista_punti = new ArrayList<Punto>();
	

	//Ricava i punti di una curva
	public void disegna_curva(){
				
		double stepsize; //angolo_parziale
		double x2, y2; //coordinate calcolate
        
		double radius = getRaggio();
		double start_angle = getAng_iniziale();
		
		//Nael caso ho una curva a destra la converto in una a sinistra
		
		if (getRaggio() < 0.0)
		{
			radius = -radius;

			start_angle = start_angle - getLunghezza() - Math.PI;
			
			while (start_angle < 0.0)				
			{
			  start_angle += (2 * Math.PI);
			}
		}

		// Determina l'angolo parziale
		
		//LINESEG_LENGHT = 20
		//Pu� essere moltiplicato per un intero in modo da aumentare l'angolo parziale
		
		double LINESEG_LENGTH = 20;
	
		
		
		stepsize = LINESEG_LENGTH / (2.0 * Math.PI * radius);
		int dim_array = 0;
		
		Punto[] dati = null;
	    //Calcola il punto della curva 
		if(getRaggio()<0){
		dim_array = (int) (getLunghezza()/stepsize);
		dati = new Punto[dim_array];	
		}
		
		
		for (double a = stepsize; a < getLunghezza(); a += stepsize)
		{
			//Calcola le coordinate del punto interessato
			
			
		//	x2 = approssima_numero(getCentro_circonferenza().getX() + radius * Math.sin(start_angle + a));
		//	y2 = approssima_numero(getCentro_circonferenza().getY() - radius * Math.cos(start_angle + a));
			
			x2 = getCentro_circonferenza().getX() + radius * Math.sin(start_angle + a);
			y2 = getCentro_circonferenza().getY() - radius * Math.cos(start_angle + a);

			Punto punto =new Punto();			
			punto.setX(x2);
			punto.setY(y2);
			if(getRaggio()<0)
			{				
			dim_array--;
			dati[dim_array] = punto;	
			}			
			else
			//Aggiunge il punto calcolato alla lista dei punti del segmento in questione
			this.lista_punti.add(punto);
			
		}

		if(getRaggio()<0)
		{
//			x2 = approssima_numero(getCentro_circonferenza().getX() + radius * Math.sin(start_angle + getLunghezza()));
//			y2 = approssima_numero(getCentro_circonferenza().getY() - radius * Math.cos(start_angle + getLunghezza()));
//			
//			Punto punto =new Punto();			
//			punto.setX(x2);
//			punto.setY(y2);
//			
//			lista_punti.add(punto);
			
			for (int i = 0; i < dati.length; i++) 
			{
				lista_punti.add(dati[i]);
			}
			
		}
		else
		{
		//Calcola il punto finale dell'arco 
//		x2 = approssima_numero(getCentro_circonferenza().getX() + radius * Math.sin(start_angle + getLunghezza()));
//		y2 = approssima_numero(getCentro_circonferenza().getY() - radius * Math.cos(start_angle + getLunghezza()));
//		
//		
//		Punto punto =new Punto();			
//		punto.setX(x2);
//		punto.setY(y2);
//		
//		//Aggiunge il punto alla lista dei punti
//		this.lista_punti.add(punto);
		}
		
	}	


	//Serve per calcolare i punti intermedi di un rettilineo
	public void calcolaPunti_Rettilineo_Generico(boolean iniziale){
		
		   //Lunghezza totale del rettilineo (NB: non la sua proiezione in x o y)
		   double lunghezza = getLunghezza();
		   
		   //Intervallo in m, ogni quanto disegnare un punto
		   int lunghezza_intervallo = 10;//intervallo in metri tra un punto ed un altro		   
		  
		   //Valore di x per il valore considerato in lunghezza intervallo
		   double delta_x = 0;		   
		  
		   
		   double x = getCoordinate_iniziali().getX();
		   double y = getCoordinate_iniziali().getY();		   
		 
		   if(iniziale) //aggiunge le coordinate iniziali del rettilineo principale
		   {
			   Punto e = new Punto();
			   e.setX(x);
			   e.setY(y);
			   lista_punti.add(e);
		   }
		   //Calcolo il valore di delta_x
		   delta_x = (lunghezza_intervallo/(Math.sqrt(Math.pow(getM(), 2)+1)));
		 //  delta_x = approssima_numero((lunghezza_intervallo/(Math.sqrt(Math.pow(getM(), 2)+1))));
		   //calcola i punti intermedi di un rettilineo
	       for (int i = 1; i < (lunghezza/lunghezza_intervallo); i++) {
				
	    	   //crea un nuovo punto
	    	   Punto e = new Punto();
								
					if(delta_x!=0)	//Il rettilineo ha una certa pendenza					
					{
					  if(getCoordinate_iniziali().getX()-getCoordinate_finali().getX()>0) //rettilineo da destra verso sinistra, le x decrescono
					    
						  x = x-delta_x;
					  			  
					  else //x crescenti
						  
						  x = x+delta_x; 
						
					   y = (getCoordinate_iniziali().getY()) + (getM()*(x-(getCoordinate_iniziali().getX()))); //calcolo il corrispondente valore di y considerando il coeff.angolare del rettilineo e il valore di x calcolato in precedenza
					   //y = approssima_numero((getCoordinate_iniziali().getY()) + (getM()*(x-(getCoordinate_iniziali().getX()))));
					}
					else//caso in cui il rettilineo non ha una varizione in x (x=k)
					{
						//ottengo il verso del rettilineo
						double diff =  getCoordinate_iniziali().getY() - getCoordinate_finali().getY();
						
						if(diff<0) //dal basso verso l'alto
						{
							y = getCoordinate_iniziali().getY() + (i*lunghezza_intervallo);
						//	y = approssima_numero(getCoordinate_iniziali().getY() + (i*lunghezza_intervallo));
						}
						else //dall'alto verso il basso, y decrescenti
						    y = getCoordinate_iniziali().getY() - (i*lunghezza_intervallo);
						   // y = approssima_numero(getCoordinate_iniziali().getY() - (i*lunghezza_intervallo));
					}
										
									
					e.setX(x);
					e.setY(y);
					
					lista_punti.add(e);				
				}			
			
	       //aggiunge il punto alla lista dei punti dell'segmento in questione
	     //  lista_punti.add(getCoordinate_finali());

		}	
	
	
	//Stampa i punti di un segmento
	public void print(){
		Iterator<Punto> it = lista_punti.iterator();
		
		while (it.hasNext()) {
			Punto punto = (Punto) it.next();
			System.out.println(punto.getX() +";"+ punto.getY());
		}
	}
	

	
//	public Punto get_puntoiniziale()
//	{
//        Iterator<Punto> it = lista_punti.iterator();
//        Punto punto = null;
//			while(it.hasNext())
//			{			
//			 punto = (Punto) it.next();				
//			}
//			
//			return punto;		
//	}
//	
	
	
	public double getLunghezza() {
		return lunghezza;
	}

	public boolean setLunghezza(double lunghezza, boolean radianti) 
	{
		
		if(getRaggio()==0) //� un rettilineo, quindi converto i feet in metri
			this.lunghezza = lunghezza*0.3048;
		
		//� una curva, devo controllare se l'angolo � espresso in radianti oppure in gradi
		else if(radianti)
		{			
		    if(lunghezza<5.0)	
		    {
		    	radianti = true; //indico che il file che ho aperto misura gli angoli in radianti	
		    	this.lunghezza=lunghezza;
		    }
										
			else 
				radianti = false; //angolo espreso in radianti
		}
	//	else //angolo espresso in gradi
		if(!radianti && getRaggio()!=0)	
			this.lunghezza=(Math.toRadians(lunghezza));	
		
		return radianti;
		
	}

	public double getRaggio() {
		return raggio;
	}

	public void setRaggio(double raggio) {
		if(raggio!=0)
			
		//this.raggio = approssima_numero(raggio*0.3048);//trasformo feet in m
		
		this.raggio = raggio*0.3048;
		
		else this.raggio=0; //� un rettilineo impongo il raggio uguale a zero
		
	}
	
//	private double approssima_numero(double numero){
//		double valore = Math.round(numero*100000)/100000;
//		return valore;
//		return numero;
//	}
	
	public double getM() {
		return m;
	}
	
	public void setM(double m) {
		this.m = m;
	}

	public Punto getCoordinate_iniziali() {
		return coordinate_iniziali;
	}

	public void setCoordinate_iniziali(Punto coordinate_iniziali) {
		
		this.coordinate_iniziali = coordinate_iniziali;
	}

	public Punto getCoordinate_finali() {
		return coordinate_finali;
	}

	public void setCoordinate_finali(Punto coordinate_finali) {
		this.coordinate_finali = coordinate_finali;
	}

	public Punto getCentro_circonferenza() {
		return centro_circonferenza;
	}

	public void setCentro_circonferenza(Punto centro_circonferenza) {
		this.centro_circonferenza = centro_circonferenza;
	}

	public double getAng_finale() {
		return ang_finale;
	}

	public void setAng_finale(double ang_finale) {
		this.ang_finale = ang_finale;
	}


	public double getAng_iniziale() {
		return ang_iniziale;
	}


	public void setAng_iniziale(double ang_iniziale) {
		this.ang_iniziale = ang_iniziale;
	}
	
	public ArrayList<Punto> get_lista_punti(){
		
		return this.lista_punti;
	}



}
