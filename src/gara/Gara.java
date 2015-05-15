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
package gara;

import giocatore.Giocatore;
import giocatore.InfoPunto;
import giocatore.TempiSettore;

import java.util.ArrayList;
import java.util.Comparator;

import javax.sound.midi.MidiDevice.Info;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

//import jdk.nashorn.internal.runtime.ListAdapter;
import Circuito.Circuito;

public class Gara {
	
	private ArrayList<Giocatore> partecipanti = new ArrayList<Giocatore>(); //Lista dei partecipanti alla gara
	private Circuito circuito ; //circuitoin cui viene effettuate la gara
	private int temperatura; //temperatura ambientale del tracciato
	private double densita; //densit� dell aria
	private int giri_gara;
	
	
	

	public Gara(int temperatura, Circuito circuito)
	{
		this.circuito = circuito; //imposto il circuito			
		
		setGiri_gara(); //calcola i giri da percorrere durante la gara	
		
		setTemperatura(temperatura); //imposta la temperatura
		setDensita(); //imposta la densita
		
		
	}
	
	public void startGara() //calcola i tempi di ciascun partecipante alla gara
	{
		for(int n = 0; n<this.partecipanti.size();n++) //serve per selezionare un partecipante
		{
			Giocatore partecipante = this.partecipanti.get(n); 
			
			for (int i = 0; i <= this.giri_gara; i++) //calcola i tempi sul giro del partecipante
			{		
			  boolean finale = false;
			  
			  if(i==this.giri_gara) finale = true;
			  
	          calcola_giro_partecipante(partecipante, finale);
			}
		}
		
	}
	
	
	
	
	public void calcola_giro_partecipante(Giocatore partecipante, boolean finale)
	{
		int n = circuito.getLista_punti().size();
		
		if(partecipante.getGiro_attuale()==0) //giro iniziale, quindi devo inizializzare la lista dei punti
		{
			partecipante.setLista_punti(n);
			partecipante.setTempiSettore(this.giri_gara);
		}
		else
			partecipante.getLista_punti().set(0, (InfoPunto) partecipante.getInfo_Punto().clone());
		
		int i=0;
		double spazio_percorso = 0;// partecipante.getSpazio_percorso(); //inizializzo lo spazio percorso
		
		while(i<circuito.getLista_punti().size())
		{
			
			boolean pit_stop = false;
			double vel_limite = 0; //vel.limite in un deterinato segmento
			
			boolean controllo_partenza = (partecipante.getPosizione_attuale()<=circuito.getId_start()||(partecipante.getPosizione_attuale()-this.circuito.getLista_punti().size()<=circuito.getId_start()))&&partecipante.getGiro_attuale()==0&&i==0;
			
		    double raggio = this.circuito.calcola_raggio(partecipante.getPosizione_attuale());//Calcola il raggio del punto attuale
		    double raggio_successivo = this.circuito.calcola_raggio(partecipante.getPosizione_attuale()+1); //raggio del prossimo elemento
		    double vel_max = partecipante.max_velocita_curva(this.densita, raggio); //velocit� massima raggiungibile nel segmento attuale
		    double vel_max_succ = partecipante.max_velocita_curva(this.densita, raggio_successivo); //velocit� massima raggiungibile nel segmento successivo
			
		    boolean condizione_1 = (partecipante.getPosizione_attuale()>=0&&partecipante.getPosizione_attuale()<circuito.getId_start())&&(partecipante.getAuto().getPneumatico().getNumero_giri()==1);
			boolean condizione_2 = ((partecipante.getPosizione_attuale()>=circuito.getId_start())&&(partecipante.getPosizione_attuale()<=circuito.getUltimo_punto()))&&(partecipante.getAuto().getPneumatico().getNumero_giri()==0);
		
			if(partecipante.getPitStop()&&(condizione_1||condizione_2)&&!finale)
			{				
				if(partecipante.getInfo_Punto().getVelocita_attuale()>16.66) //la velocit� attuale � maggiore della velocit� massima
				{
					pit_stop = true;
				    vel_max= 16.66;
				    
					double distanza_attuale = this.circuito.calcola_distanza(partecipante.getPosizione_attuale());
		    		partecipante.getInfo_Punto().setLunghezza(distanza_attuale);
		    		
		    		double spazio_frenata = 0;
		    		if(raggio!=0)
					     spazio_frenata = partecipante.getSpazio_Frenata_Curva(vel_max, this.densita, raggio); //calcolo lo spazio che mi occorre per frenare
		    		else
		    			spazio_frenata = partecipante.getSpazio_Frenata_Rettilineo(vel_max, this.densita);
		    		
	    			if(distanza_attuale>=spazio_frenata) //lo spazio che devo percorrere � maggiore dello spazio che utilizzer� per frenare, quindi una parte dello spazio totale la utilizzer� per frenare, e l'altra per mantenere la velocit� costante
	    			{
	    				spazio_percorso += spazio_frenata; //spazio percorso per la frenata
	    				
	    			
	    				partecipante.frena(densita, distanza_attuale, raggio, vel_max, true, n);
	    				
	    				setTempo_Pilota(partecipante, i+1, false);
	    			}
	    			else //lo spazio che mi serve per frenare � maggiore dello spazio che ho a disposizione
	    			{
	    				getPunto_Frenata(vel_max, partecipante);
	    			}
	    			
	    			setTempo_Pilota(partecipante, i+1, false);
				}
				else //devo mantenere la velocit� costante nel rettilineo principale
				{
					pit_stop = true;
				    vel_max= 16.66;
					 double distanza_attuale = this.circuito.calcola_distanza(partecipante.getPosizione_attuale());
				     partecipante.getInfo_Punto().setLunghezza(distanza_attuale);
				     vel_limite = vel_max;		//la velocit� limite � uguale alla velocit� massima del segmento che devo percorrere
				     partecipante.calcola_velocita_finale(this.densita, distanza_attuale, vel_limite, n);
				     setTempo_Pilota(partecipante, i+1, false);
	 				 spazio_percorso += distanza_attuale; 	
				}			
				
			}
					    
		    
		    
		    if(raggio_successivo!=0&&!pit_stop)//il prossimo elemento � una curva, ma l'elemento in cui mi trovo pu� essere un rettilineo o una curva
		    {
		    	
			    if(vel_max<vel_max_succ&&vel_max!=0) //la velocit� del segmento che devo percorrere � minore della velocit� max della prossima curva (l'elemento da percorrere � una curva ed anche quello successivo)
			    {	
			    	
			     double distanza_attuale = this.circuito.calcola_distanza(partecipante.getPosizione_attuale());
			     partecipante.getInfo_Punto().setLunghezza(distanza_attuale);
			     if(controllo_partenza)
			    	 vel_limite = 5;
			     else
			    	 vel_limite = vel_max;		//la velocit� limite � uguale alla velocit� massima del segmento che devo percorrere
			     partecipante.calcola_velocita_finale(this.densita, distanza_attuale, vel_limite, n);
			     setTempo_Pilota(partecipante, i+1, false);
 				 spazio_percorso += distanza_attuale; 				 
			   
			    }
			    
			    else if(vel_max>vel_max_succ||vel_max==0) //l'elemento da percorrere � un rettilineo oppure una curva, il prossimo elemento � una curva
			    {
			    	
			   	//double distanza_succ = this.circuito.calcola_distanza(partecipante.getPosizione_attuale()+1); // calcolo la lunghezza del segmento successico da percorrere
			    //	double vel_max_ingresso = partecipante.getVelocita_Freno(vel_max_succ, distanza_succ, this.densita, raggio_successivo);//calcolo la massima velocit� d'ingresso che posso avere nel segmento successivo
			        	
			    	//if()
			    	//vel_max_ingresso sar� leggermente superiore alla max velocit� ammessa per il segmento successivo, questo perch� considero che inizio a rallentare appena entro nel settore successivo, e poi mantengo la velocit� costante
			    
			    	if(raggio==0) //mi trovo in rettilineo ed il prossimo elemento � una curva
			    	{   
			    		double distanza_attuale = this.circuito.calcola_distanza(partecipante.getPosizione_attuale());
			    		partecipante.getInfo_Punto().setLunghezza(distanza_attuale);
			    		
			    		//devo controllare se la mia velocit� attuale � maggiore o minore della velocit� massima d'ingresso del prossimo elemento
			    		if(partecipante.getInfo_Punto().getVelocita_attuale()>vel_max_succ) // la mia velocit� � maggiore della velocit� d'ingresso massima della prossima curva, quindi devo calcolare se lo spazio dell'elemento che sto percorrere � sufficiente per frenare l'auto, altrimenti devo iniziare a frenare prima
			    		{
			    			double spazio_frenata = partecipante.getSpazio_Frenata_Rettilineo(vel_max_succ, this.densita); //calcolo lo spazio che mi occorre per frenare
			    			
			    			if(distanza_attuale>=spazio_frenata) //lo spazio che devo percorrere � maggiore dello spazio che utilizzer� per frenare, quindi una parte dello spazio totale la utilizzer� per frenare, e l'altra per mantenere la velocit� costante
			    			{
			    				spazio_percorso += spazio_frenata; //spazio percorso per la frenata
			    				if(controllo_partenza)
			   			    	 vel_limite = 5;
			    				else
			    				vel_limite = vel_max_succ;			    		
			    				
			    				partecipante.frena(densita, distanza_attuale, raggio, vel_limite, true, n);
			    				setTempo_Pilota(partecipante, i+1, true);
			    				
			    			}
			    			else //devo tornare indietro per calcolare il settore da cui devo iniziare a frenare
			    			{
			    				getPunto_Frenata(vel_max_succ, partecipante);
			    			}
			    		}
			    		else if(partecipante.getInfo_Punto().getVelocita_attuale()<=vel_max_succ) //la velocita attuale dell'auto � minore della massima velocit� d'ingresso in curva
			    		{
			    			if(controllo_partenza)
						    	 vel_limite = 5;
			    			else
			    				vel_limite = vel_max_succ;
			    			
			    			partecipante.calcola_velocita_finale(this.densita, distanza_attuale, vel_limite, n);
			    			setTempo_Pilota(partecipante, i+1, true);
		    				spazio_percorso += distanza_attuale;
			    		}
			    		
			    	
			    	}
			    	else if(raggio!=0)//mi trovo in una curva, ed il prossimo elemento e una curva
			    	{
			    		double distanza_attuale = this.circuito.calcola_distanza(partecipante.getPosizione_attuale());
			    		//devo controllare se la mia velocit� attuale � maggiore o minore della velocit� massima d'ingresso del prossimo elemento
			    		 partecipante.getInfo_Punto().setLunghezza(distanza_attuale);
			    		
			    		if(partecipante.getInfo_Punto().getVelocita_attuale()>vel_max_succ) // la mia velocit� � maggiore della velocit� d'ingresso massima della prossima curva, quindi devo calcolare se lo spazio dell'elemento che sto percorrere � sufficiente per frenare l'auto, altrimenti devo iniziare a frenare prima
			    		{
			    			double spazio_frenata = partecipante.getSpazio_Frenata_Curva(vel_max_succ, this.densita, raggio); //calcolo lo spazio che mi occorre per frenare
			    			
			    			if(distanza_attuale>=spazio_frenata) //lo spazio che devo percorrere � maggiore dello spazio che utilizzer� per frenare, quindi una parte dello spazio totale la utilizzer� per frenare, e l'altra per mantenere la velocit� costante
			    			{
			    				spazio_percorso += spazio_frenata; //spazio percorso per la frenata
			    				if(controllo_partenza)
			   			    	 vel_limite = 5;
			    				else
			    					vel_limite = vel_max_succ;
			    			
			    				partecipante.frena(densita, distanza_attuale, raggio, vel_limite, true, n);
			    				
			    				setTempo_Pilota(partecipante, i+1, false);
			    			}
			    			else //lo spazio che mi serve per frenare � maggiore dello spazio che ho a disposizione
			    			{
			    				getPunto_Frenata(vel_max_succ, partecipante);
			    			}
			    		}
			    		else if(partecipante.getInfo_Punto().getVelocita_attuale()<=vel_max_succ)//la velocit� dell auto (in curva) � minore della vlocit� max_d'ingresso della prossima curva
			    		{
			    			if(controllo_partenza)
						    	 vel_limite = 5;
			    			else
			    				 vel_limite = vel_max_succ;
			    			partecipante.calcola_velocita_finale(this.densita, distanza_attuale, vel_limite, n);			    			
			    			setTempo_Pilota(partecipante, i+1, false);
		    				spazio_percorso += distanza_attuale;
			    		}
			    		
			    	}
			    	//vel_limite = vel_max_ingresso;  //la velocit� limite � uguale alla velocit� massima del segmento successivo da percorrere
			    }
			    
			    else if(vel_max==vel_max_succ) //curva con lo stesso raggio, quindi con velocit� massima uguale
			    {
			    	double distanza_attuale = this.circuito.calcola_distanza(partecipante.getPosizione_attuale());
			    	partecipante.getInfo_Punto().setLunghezza(distanza_attuale);
			    	if(controllo_partenza)
				    	 vel_limite = 5;
			    	else
			    	     vel_limite = vel_max_succ;
			    	partecipante.calcola_velocita_finale(this.densita, distanza_attuale, vel_limite, n);
			    	setTempo_Pilota(partecipante, i+1, false);
	 				spazio_percorso += distanza_attuale; 
			    }
		    
		    }
		    
		    else if(raggio!=0&&raggio_successivo==0&&!pit_stop) //se sto per affrontare una curva e il prossimo elemento � un rettilineo
		    {
		    	vel_limite = vel_max;
		    		
		    	double distanza_attuale = circuito.calcola_distanza(partecipante.getPosizione_attuale());
		    	partecipante.getInfo_Punto().setLunghezza(distanza_attuale);
				
				partecipante.calcola_velocita_finale(this.densita, distanza_attuale, vel_limite, n);
				
				setTempo_Pilota(partecipante, i+1, false);
				
				spazio_percorso += distanza_attuale;
		    }
		    else if (raggio==0&&raggio_successivo==0&&!pit_stop) //mi trovo su un rettilineo
		    {
		    	double distanza_attuale = circuito.calcola_distanza(partecipante.getPosizione_attuale());
		    	partecipante.getInfo_Punto().setLunghezza(distanza_attuale);
				
				partecipante.calcola_velocita_finale(this.densita, distanza_attuale, vel_limite, n);
				
				setTempo_Pilota(partecipante, i+1, true);
				
				spazio_percorso += distanza_attuale;
				
		    }
		    
		    partecipante.getInfo_Punto().setTempo_parziale(0); //Imposto il tempo parziale del prossimo punto a zero  
		    
		    if((partecipante.getPosizione_attuale()==circuito.getUltimo_punto())&&(partecipante.getAuto().getPneumatico().getNumero_giri()==0))
			{

		    	resetGomma(partecipante);
				partecipante.setPitStop(false);
				pit_stop = false;
				
				partecipante.setNumero_pitstop();
			}
		    
		    if((partecipante.getPosizione_attuale()<=circuito.getId_start()||(partecipante.getPosizione_attuale()-this.circuito.getLista_punti().size()<=circuito.getId_start()))&&partecipante.getGiro_attuale()==0&&partecipante.getPosizione_partenza()!=circuito.getId_start()) //non ancora taglio il traguardo, quindi i punti non vengono memorizzati
		    {
		    	//differenza di tempo rispetto al partecipante davanti
		    	double gap = partecipante.getLista_punti().get(1).getTempo_parziale();
		    	gap += partecipante.getGap_precedente();
		    	partecipante.setGap_precedente((gap));
		    	
		    	calcola_consumo_settore(partecipante, 1);
		    	InfoPunto punto = (InfoPunto) partecipante.getLista_punti().get(1);
		    	partecipante.getLista_punti().clear();
		    	partecipante.getLista_punti().add(punto);
		    	int posizione_parziale = partecipante.getPosizione_partenza()+1;
		    	if(posizione_parziale == this.circuito.getLista_punti().size()) //� uguale a size, quindi imposto zero
		    		partecipante.setPosizione_partenza(0);
		    	else
		    	partecipante.setPosizione_partenza(posizione_parziale);
		    	
		    	
		    }
		    else
		    {
		    	i++;
		    	
		    	calcola_consumo_settore(partecipante, i);		    	
		    }
		    	
	
		    
		}
		
		byte giro_successivo =  (byte) (partecipante.getGiro_attuale() + 1);		
		
		TempiSettore tempo_giro = new TempiSettore(partecipante);
		tempo_giro.setPit_stop(partecipante.getNumero_pitstop()); //imposto il numero di pit effettuati
		tempo_giro.setNumero_giro(partecipante.getGiro_attuale()); //imposto il numero di giro a cui corrisponde questo tempo
	    partecipante.getListaTempi().add(tempo_giro); //aggiongo le informaizoni dei tempi
	 
	    
	    //se il giocatore � reale, salvo le informazioni sul giro
	    if(partecipante.isGioc_reale())
	    aggiungiGiroGrafico(partecipante);
	
	    partecipante.setPosizione_attuale(partecipante.getPosizione_attuale(), this.circuito.getLista_punti().size(), false);
	    
	
		partecipante.setGiro_attuale(giro_successivo);
		reset(partecipante);
		
		
	}
	


private void calcola_cosumo_finale(Giocatore partecipante) {
		
	double consumo = 0;

	
	for(int f = 0;f<partecipante.getLista_punti().size(); f++)
	{
		
		consumo += partecipante.getLista_punti().get(f).getConsumo_medio();
		
	}
	
consumo = consumo * partecipante.getPilota().coeffCarbunte();
	
		
	}




private void calcola_consumo_settore(Giocatore partecipante, int i) 
    {
		double consumo_rpm = partecipante.getAuto().getMotore().getConsumo();
		double media_rpm = 0;
		
		i = partecipante.getLista_punti().size()-1;		
	//	if(i==partecipante.getLista_punti().size()) i=partecipante.getLista_punti().size()-1;
	//	if(i==this.circuito.getLista_punti().size()) i=this.circuito.getLista_punti().size()-1;	
	
		
		media_rpm = ((partecipante.getLista_punti().get(i).getRpm_attuali()) + (partecipante.getLista_punti().get(i-1).getRpm_attuali())) * 0.5;
		
	//	media_rpm = ((partecipante.getLista_punti().get(i).getRpm_attuali()) + (partecipante.getLista_punti().get(i-1).getRpm_attuali())) * 0.5;
		
		
		double consumo_medio = (media_rpm) * consumo_rpm * partecipante.getLista_punti().get(i).getTempo_parziale();
		
		consumo_medio *= partecipante.getPilota().coeffCarbunte();
		
		partecipante.getLista_punti().get(i).setConsumo_medio(consumo_medio);
		
		double massa_combustibile = partecipante.getInfo_Punto().getMassa_carburante();
		
		massa_combustibile -= consumo_medio;
		
		partecipante.getInfo_Punto().setMassa_carburante(massa_combustibile);
		
		partecipante.getLista_punti().get(i).setMassa_carburante(massa_combustibile);
	}


private double calcola_tempo_finale(Giocatore partecipante) //calcola il tempo di un giro, il risultato � espresso in secondi
{
	double tempo = 0;
		
	for(int f = 0;f<partecipante.getLista_punti().size(); f++)
	{
		
		tempo += partecipante.getLista_punti().get(f).getTempo_parziale();
		
	}
	
	
	return tempo;
	
}

public void reset(Giocatore partecipante)
{
	
	
	for(int f = partecipante.getLista_punti().size()-1;f>0; f--)
	{
		partecipante.getLista_punti().remove(f);		
	}
	

}
	
	
public void getPunto_Frenata(double vel_max, Giocatore partecipante)	
{

	
	int n = circuito.getLista_punti().size();
	
	int posizione_attuale=0;
	int posizione_parziale=0;
	
	
	if(partecipante.getPosizione_attuale()!=0)
	{
	posizione_attuale = partecipante.getPosizione_attuale() - partecipante.getPosizione_partenza();
	posizione_parziale = posizione_attuale - 1;  //posizione dell'elemento precedente
	}
	else
	{
		posizione_attuale = circuito.getLista_punti().size() - partecipante.getPosizione_partenza();
		posizione_parziale = posizione_attuale - 1;
	}
	InfoPunto punto_iniziale = null;
	boolean flag = true;
			
	while(flag)
	{
	
	punto_iniziale = (InfoPunto) partecipante.getPunto_Specifico(posizione_parziale).clone(); //prende il punto precedente	
	
	partecipante.setInfo_Punto(punto_iniziale); //imposto le nuove informazioni per il punto attuale
	partecipante.getInfo_Punto().setTempo_parziale(0);
	
	for (int i = posizione_parziale; i <= posizione_attuale; i++) 
	{
		InfoPunto punto = null;
		if(i!=posizione_attuale)
		{			
		punto = partecipante.getPunto_Specifico(i+1);	
		}
		else
		punto = new InfoPunto();
		
		punto.setTempo_parziale(0);
		partecipante.getInfo_Punto().setTempo_parziale(0);
		
		//partecipante.setInfo_Punto((InfoPunto) punto.clone());//imposto le nuove informazioni per il punto attuale
		
		double lunghezza_segmento = circuito.calcola_distanza(i+partecipante.getPosizione_partenza());		
		partecipante.frena(densita, lunghezza_segmento, circuito.calcola_raggio(i+partecipante.getPosizione_partenza()), vel_max, false, n);
		
		//Sovrascrivo le nuove informazioni nel punto selezionato
		punto.setPotenza_attuale(partecipante.getInfo_Punto().getPotenza_attuale());
		punto.setRapporto_attuale(partecipante.getInfo_Punto().getRapporto_attuale());
		punto.setRpm_attuali(partecipante.getInfo_Punto().getRpm_attuali());
		punto.setVelocita_attuale(partecipante.getInfo_Punto().getVelocita_attuale());
		punto.setTempo_parziale(partecipante.getInfo_Punto().getTempo_parziale());
		
		
		if(circuito.calcola_raggio(i)==0)
			setTempo_Pilota(partecipante, i, true);
		else 
			setTempo_Pilota(partecipante, i, false);

		
		if(partecipante.getInfo_Punto().getVelocita_attuale()<=vel_max&&i==posizione_attuale)
		{
			punto.setLunghezza(lunghezza_segmento);
			flag = false; //se la velocit� attuale dell'auto � minore o uguale alla velocit� del prossimo elemento esco dal ciclo
			partecipante.getLista_punti().add(punto);
//			posizione_attuale++;
//			partecipante.setPosizione_attuale(posizione_attuale);
		}
	
		
	}
	

	if(flag) posizione_parziale--;
	else
	{
		posizione_attuale++;
		int posizione = 0;
		
		if(posizione_attuale+partecipante.getPosizione_partenza()>=circuito.getLista_punti().size())
			posizione = (posizione_attuale+partecipante.getPosizione_partenza() - circuito.getLista_punti().size());
		else
			posizione = posizione_attuale+partecipante.getPosizione_partenza();
		
		partecipante.setPosizione_attuale(posizione, n, false);
	}
}
}

private void setTempo_Pilota (Giocatore partecipante, int posizione, boolean rettilineo)
{
	double coefficiente = 0;
	
	if(posizione>=this.circuito.getLista_punti().size()) posizione = 0;
	
	if(rettilineo) 
		coefficiente = partecipante.getPilota().coeffRettilineo();
	else
		coefficiente = partecipante.getPilota().coeffCurva();
	
	double tempo_parziale = partecipante.getLista_punti().get(posizione).getTempo_parziale();
	
	tempo_parziale *= coefficiente;			
	
	partecipante.getLista_punti().get(posizione).setTempo_parziale(tempo_parziale); //modifica random pilota
}
	
	
	public Circuito get_Circuito(){
		return this.circuito;
	}
	
	public void set_Circuito(Circuito circuito){
		this.circuito = circuito;
	}
	

	public double getDensita() {
		return densita;
	}

	private void setDensita() { //Imposta la densit� a seconda della temperatura
		this.densita = (100000)/((273.15+this.temperatura)*287.5);
	}
	
	
	public int getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(int temperatura) {
		this.temperatura = temperatura;
	}


	public ArrayList<Giocatore> getPartecipanti() {
		return partecipanti;
	}


	public void setPartecipanti(Giocatore partecipante) {
		this.partecipanti.add(partecipante);
		
		partecipante.setDurataPneumatico(this.temperatura, this.circuito.getLunghezza_giro()); //imposta la durata dello pneumatico in base alla temperatura
		
		
	}
	
	public void setPosizionePartecipanti() //serve per impostare il punto fisico di partenza in base alla posizione della griglia di partenza del giocatore
	{
		
		for (int i = 0; i < this.partecipanti.size(); i++) 
		{
		 int posizione_griglia = this.partecipanti.get(i).getPosizione_griglia(); //prende la posizione sulla griglia di partenza
		 int posizione_traguardo = this.circuito.getId_start();
		 
		 if(posizione_traguardo-posizione_griglia>=0)
			 {
				int posizione_partenza = posizione_traguardo-posizione_griglia;
				this.partecipanti.get(i).setPosizione_attuale(posizione_partenza, this.circuito.getLista_punti().size(), false);
				this.partecipanti.get(i).setPosizione_partenza(posizione_traguardo-posizione_griglia);
			 }
		 else
			 {
				int posizione_parziale = Math.abs(posizione_traguardo-posizione_griglia); //numero negativo che indica il numero di posizioni prima dello zero
				int posizione_partenza = this.circuito.getLista_punti().size() - posizione_parziale;
				
				this.partecipanti.get(i).setPosizione_attuale(posizione_partenza, this.circuito.getLista_punti().size(), false);
				this.partecipanti.get(i).setPosizione_partenza(posizione_partenza);
			 }
		 
		}
		
		
	}

	public int getGiri_gara() {
		return giri_gara;
	}

	public void setGiri_gara() {
		
		int lunghezza_gara = 120; //Lunghezza della gara in Km
		
		double lunghezza_giro = this.circuito.getLunghezza_giro(); //lunghezza di un giro
		int giri_gara = (int) ((int) (lunghezza_gara*1000)/lunghezza_giro);
		
		this.giri_gara = giri_gara;
	}
	
	public void setGiri_Qualifica()
	{
		this.giri_gara = 4;
	}
	
	public void resetGomma(Giocatore partecipante){
		
		int durata_pneumatico = partecipante.getAuto().getPneumatico().getDurata()*1000;
		
		durata_pneumatico *= partecipante.getPilota().durataPneumatici();
		
		double coefficiente = partecipante.getAuto().getPneumatico().getCoeff_totale();
		durata_pneumatico = (int) ((durata_pneumatico)/circuito.getLunghezza_giro());
		
		partecipante.getAuto().getPneumatico().setNumero_giri(durata_pneumatico);
		partecipante.getAuto().getPneumatico().setCoefficiente(coefficiente);
		
	}
	
	public void setCarburante(){		
		
		//per ciascun partecipante calcola la quantit� di carburante per effettuare il numero di giri impostati in precedenza
		for (int i = 0; i < partecipanti.size(); i++) 
		{
			Giocatore partecipante = partecipanti.get(i); 
			double massa_combustibile = partecipante.getAuto().getSerbatoio() * 0.750; //moltiplicola capienza del serbatoio * la densita della benzina Kg/l
			partecipante.getInfo_Punto().setMassa_carburante(massa_combustibile);//imposto il carburante iniziale
	    }
	

		
		
	}
	
	public static class SortPunteggio implements Comparator<Giocatore> //sort punteggio
	{
		@Override
		public int compare(Giocatore g1, Giocatore g2) 
		{				
			
			return Integer.compare(g1.getPunteggio(), g2.getPunteggio());
			
		}	
	}
	
	
	public static class TempiGiocatore implements Comparator<Giocatore>
	{
		@Override
		public int compare(Giocatore g1, Giocatore g2) 
		{				
			
			return Double.compare(g1.getListaTempi().get(0).getTempo_totale(), g2.getListaTempi().get(0).getTempo_totale());
			
		}	
	}
	
	
	private void aggiungiGiroGrafico(Giocatore partecipante) 
	{
				
		if(partecipante.getGiri_grafico()==null)
		{
		   ArrayList<XYSeriesCollection> giri_grafico = new ArrayList<XYSeriesCollection>();
		   partecipante.setGiri_grafico(giri_grafico);
		}
		
		double lunghezza = 0;
		
		XYSeries xy_data = new XYSeries(partecipante.getPilota().getNome()+" "+partecipante.getPilota().getCognome());
		
		for (int i = 0; i < partecipante.getLista_punti().size(); i++) 
		{
			lunghezza += partecipante.getLista_punti().get(i).getLunghezza();
			
			if(i==0)
			{
				xy_data.add(0, partecipante.getLista_punti().get(0).getVelocita_attuale());
			}
			else
			{
				xy_data.add(lunghezza, partecipante.getLista_punti().get(i).getVelocita_attuale());
			}
			
		}
		
		 XYSeriesCollection my_data_series= new XYSeriesCollection();
         // add series using addSeries method
         my_data_series.addSeries(xy_data);
         
    
         
         partecipante.getGiri_grafico().add(my_data_series);
           
		
	}
	
 

	
}
