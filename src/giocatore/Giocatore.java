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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import org.jfree.data.xy.XYSeriesCollection;

import pilota.Pilota;
import Circuito.Circuito;
import GUI.NumeroGiocatori;
import auto.Auto;

public class Giocatore implements Comparator<Giocatore>, Cloneable {

	private Auto auto;
	private Pilota pilota;
	
	private int posizione_griglia = 0; //indica la posizione sulla griglia di partenza (1�,2�,3�, ecc..)
	private int posizione_attuale = 0; //numero che indica la posizione (coordinata) in qui si trova l'auto
	private int posizione_partenza; //valore che indica il punto da cui parte l'auto
	private byte giro_attuale = 0;
	
	private boolean gioc_reale = false;
	
//	private double massa_combustibile = 0; //combustibile iniziale del giro
//	private int punti_totali = 0;
//	private double fd; //Forza relativa al drag dell'auto
	private double rendimento = 0.91;
	
	boolean pit_stop = false;
	private int numero_pitstop = 0;
	private double gap = 0;
	private double gap_precedente = 0;
	//private double spazio_percorso = 0;
	
	private int punteggio = 0;
	
	private InfoPunto info = new InfoPunto(); //salve le informazioni temporanee in questo elemento, per poi 
	
	private ArrayList<InfoPunto> lista_punti = null;
	private ArrayList<TempiSettore> lista_tempi = null;
	
	private ArrayList<XYSeriesCollection> giri_grafico = null;
	
	public Giocatore(Auto auto, Pilota pilota)
	{
	this.auto = auto;
	this.pilota=pilota;
	}
	
	public boolean getPitStop(){
		return this.pit_stop;
	}
	
	//calcola la velocit� finale considerando la velocit� d'ingresso del segmento e la lunghezza del segmento
	//NB:questa funzione calcola solo la velocit� finale di quel segmento e non le operazioni da fare nel caso in qui ci si sta avvicinando ad una curva,
	// e ne il cambio del rapporto
	public void calcola_velocita_finale (double densita, double lunghezza_segmento, double vel_limite, int num_punti)
	{ 
		boolean vel_costante = false; //flag che serve per il constrollo a velocit� costante
		double spazio_percorso = 0;
		double lunghezza_parziale = lunghezza_segmento;
		
			while(spazio_percorso<lunghezza_segmento)	
			{
			
			//  Dati di Input
			int rapporto_attuale = this.info.getRapporto_attuale();//this.rapporto_attuale; //rapporto attuale della vettura			
			double velocita_attuale = this.info.getVelocita_attuale();//this.velocita_attuale; //velocit� attuale dell'automobile
			//lunghezza del segmento da A - B
			double massa_auto = (getAuto().getMassa_auto()+getPilota().getPeso()+this.info.getMassa_carburante());// massa dell'auto in Kg
			double rpm_attuali = 0;
			double potenza_attuale;// = 0;
			double rpm_finali = 0;			
			
			if(this.info.getPotenza_attuale()==0)//this.potenza_attuale==0) //caso in cui non dispongo dei valori
			{			
				rpm_attuali = getAuto().calcolo_rpm_attuali(velocita_attuale, rapporto_attuale);//calcolo il n.giri del motore data la velocit� ed il rapporto attualemnte utilizato
			
			    potenza_attuale = getAuto().getMotore().get_Potenza(rpm_attuali)*rendimento;//Potenza attuale del motore, calcolata in base ad un certo n.di giri
			}
			else //i valori sono quelli calcolati in precedenza (punto finale del precedente segmento)
			{
				rpm_attuali = this.info.getRpm_attuali();//this.rpm_attuali;
				potenza_attuale = this.info.getPotenza_attuale()*rendimento;//this.potenza_attuale;
			}
			
			
			//Calcolo della velocit� finale
			double forza_accelerazione_totale = potenza_attuale/velocita_attuale; //Fa
			double accelerazione = (forza_accelerazione_totale - (auto.getFd()*densita*Math.pow(velocita_attuale, 2)))/massa_auto; //accelerazione dell'auto
			double velocita_finale = 0;
			
			if(!vel_costante)
				velocita_finale = Math.sqrt(Math.pow(velocita_attuale, 2)+(2*accelerazione*lunghezza_parziale)); //velocit� nel punto finale del segmento m/s
			else
				velocita_finale = vel_limite;
			//Se il numero di giri � maggiore o minore degli intervalli specificati, allora so che il pilota ha effettuato un cambio di rapporto all'interno di quel settore,
			//ci� provoca un manco di potenza per un certo intervallo di tempo dovuto al cambiamento del rapporto
			
			
			if(velocita_finale>vel_limite&&vel_limite!=0) //La velocit� finale � maggiore della velocit� che posso avere in questo segmento (es.curva) quindi calcolo lo spazio che mi serve per raggiungere la velocita limite, e poi continuo a velocita costante
			{
				vel_costante = true;
				double distanza = (Math.pow(vel_limite, 2)-Math.pow(velocita_attuale, 2))/(2*accelerazione); //questa � la distanza percorsa per raggiungere la vel_limite
				spazio_percorso += distanza; //calcolo lo spazio percorso
				lunghezza_parziale = lunghezza_segmento - spazio_percorso; //calcolo lo spazio che devo ancora percorrere
				velocita_finale = vel_limite; //imposto la vel_finale alla vel_limite (raggiunta dopo aver percorso lo spazio "distanza")
				
			}
			
					
			rpm_finali = calcola_rpm(velocita_finale); //calcolo il numero di giri del motore in corrispondenza della velocit� finale
		//	rpm_finali<=getAuto().getRpm_min()||
			if((rpm_finali>=getAuto().getRpm_max())&&getAuto().getTrasmissione().getN_rapporti()!=rapporto_attuale)//this.rapporto_attuale)
		    {
				
			    if(vel_costante) spazio_percorso = 0; //lo spazio percorso deve essere impostato a zero
			    //calcolo lo spazio percorso prima e durante il cambio di rapporto
				spazio_percorso += calcola_punto_cambio(accelerazione, densita, massa_auto, lunghezza_parziale, num_punti);
				lunghezza_parziale = lunghezza_segmento - spazio_percorso;
		    	
		    	//rpm_finali = calcola_rpm(velocita_finale); //ricalcolo il nuovo numero di giri del motore con il nuovo rapporto (a parit� di velocit�)
		    }
			else
			{
				
			spazio_percorso = lunghezza_segmento;	

	        setPosizione_attuale((this.posizione_attuale+1), num_punti, false);
	
			double tempo_parziale = 0;
			if(!vel_costante)
			tempo_parziale = ((-velocita_attuale)+(Math.sqrt(Math.pow(velocita_attuale, 2) + (4*accelerazione*lunghezza_parziale*0.5))))/accelerazione;
			else
			tempo_parziale = (lunghezza_parziale)/vel_limite;
			

			
			info.setTempo_parziale(tempo_parziale+this.info.getTempo_parziale());
			info.setPotenza_attuale(auto.getMotore().get_Potenza(rpm_finali));
			info.setVelocita_attuale(velocita_finale);
			info.setRpm_attuali(calcola_rpm(velocita_finale));
			
			
			  }
			}			
		
			InfoPunto puntos = (InfoPunto) this.info.clone();

			lista_punti.add(puntos);
	}
	
	private double calcola_punto_cambio(double accelerazione, double densita, double massa_auto, double spazio_restante, int num_punti)
	{
	
		int rapporto_attuale = this.info.getRapporto_attuale();//this.rapporto_attuale; //rapporto attuale della vettura		
		
		double velocita_attuale = this.info.getVelocita_attuale();//this.velocita_attuale;
		
		double velocita_max = getAuto().calcola_velocita_rapporto(getAuto().getRpm_max(), rapporto_attuale); //vel.max raggiungibile con rpm_max
		
		double spazio_percorso = (Math.pow(velocita_max, 2)-Math.pow(velocita_attuale, 2))/(2*accelerazione); //spazio percorso prima di effettuare il cambio
		
		//this.rpm_attuali this.info.setRpm_attuali(velocita_attuale); = this.auto.getRpm_max();
		
		this.info.setRpm_attuali(this.auto.getRpm_max());
		
		cambia_rapporto(); //cambio il rapporto
		
		//considerando che durante il cambio di marcia la velocit� rimanga costante (prima e dopo la camabiata), calcolo lo spazio percorso conoscendo il tempo impiegato per cambiare rapporto
		
		double resistenza_aereodinamica = (this.auto.getFd()*densita*Math.pow(velocita_max, 2));
		//double resistenza_gomme = Math.pow(this.auto.getPneumatico().getCoefficiente(),2) * Math.pow((massa_auto*9.81), 2);
		
		//double ft = Math.sqrt(resistenza_gomme);
		
		double forza_decelerazione_totale = resistenza_aereodinamica;// + ft ;
		
		double decelerazione = -forza_decelerazione_totale/massa_auto;
	//	double decelerazione = (forza_accelerazione_totale - (auto.getFd()*densita*Math.pow(velocita_attuale, 2)))/massa_auto; //accelerazione dell'auto
		
	//	double velocita_finale = Math.sqrt(Math.pow(velocita_attuale, 2)+(2*accelerazione*lunghezza_parziale)); //velocit� nel punto finale del segmento m/s
				
	//	double vel = decelerazione * this.auto.getTrasmissione().getTempo_cambio() + velocita_max; //velocit� dell'auto dopo il cambio di rapporto, ovvero nel periodo di tempo in cui l'auto stava rallentando a causa del drag aereodinamico
	
		double vel = 0;
		double spazio = Math.abs(0.5 * decelerazione * Math.pow(this.auto.getTrasmissione().getTempo_cambio(), 2)); //spazio percorso durante il cambio di rapporto
		
		//double spazio = velocita_max * this.auto.getTrasmissione().getTempo_cambio();
		
		double tempo_acc_cost = ((-velocita_attuale)+(Math.sqrt(Math.pow(velocita_attuale, 2) + (4*accelerazione*spazio_percorso*0.5))))/accelerazione; //tempo impiegato per raggiungere i rpm_max
		
		if(spazio_percorso+spazio>=spazio_restante) //lo spazio_percorso � maggiore della lunghezza del segmento che sto attraversando
		{
	    spazio_percorso = spazio_restante;
		vel = Math.sqrt(Math.pow(velocita_max, 2)+(2*decelerazione*(spazio_restante - spazio_percorso)));
		double tempo_cambio = (vel - velocita_max)/decelerazione;
		this.info.setTempo_parziale(tempo_cambio + (tempo_acc_cost));
	//	setPosizione_attuale((this.posizione_attuale+1), num_punti, false);
		}
		else
		{
		  spazio_percorso += spazio; //calcolo lo spazio percorso totale (prima del cambio di marcia + spazio percorso durante il cambio di marcia)
		  vel = decelerazione * this.auto.getTrasmissione().getTempo_cambio() + velocita_max;
		  //calcolo il tempo totale
		  this.info.setTempo_parziale(this.auto.getTrasmissione().getTempo_cambio() + (tempo_acc_cost));
		}
		
	//	spazio_percorso += spazio; //calcolo lo spazio percorso totale		
		
		//ricalcolo la potenza del motore con il nuovo rapporto (a parit� di velocit�)
		
//		double tempo_acc_cost = ((-velocita_attuale)+(Math.sqrt(Math.pow(velocita_attuale, 2) + (4*accelerazione*spazio_percorso*0.5))))/accelerazione; //tempo impiegato per raggiungere i rpm_max
		
	//	this.tempo_parziale += this.auto.getTrasmissione().getTempo_cambio() + (tempo_acc_cost);
		
	//	this.info.setTempo_parziale(this.auto.getTrasmissione().getTempo_cambio() + (tempo_acc_cost));
		this.info.setVelocita_attuale(vel);
		this.info.setRpm_attuali(calcola_rpm(vel));//velocita_max));
		this.info.setPotenza_attuale(auto.getMotore().get_Potenza(calcola_rpm(vel)));//velocita_max)));
		
//		this.velocita_attuale = vel; //velocita_max;
//		this.rpm_attuali = calcola_rpm(velocita_max);
//		this.potenza_attuale = auto.getMotore().get_Potenza(calcola_rpm(velocita_max));
		
		
		
		return spazio_percorso;
		
	}
	
	private double scala_rapporto(double decelerazione, double densita, double massa_auto, double spazio_restante)
	{
	
		int rapporto_attuale = this.info.getRapporto_attuale(); //rapporto attuale della vettura		
		
		double velocita_attuale = this.info.getVelocita_attuale(); //velocit� attuale della vettura
		
		double velocita_min = getAuto().calcola_velocita_rapporto(getAuto().getRpm_min(), rapporto_attuale); //velocit� minima con il rapporto attuale, cio� la velocit� a cui mi trover� quando scaler� il rapporto
		
		double spazio_percorso = (Math.pow(velocita_min, 2)-Math.pow(velocita_attuale, 2))/(2*decelerazione); //spazio percorso prima di effettuare il cambio
		
		this.info.setRpm_attuali(this.auto.getRpm_min()); //imposto come rpm_attuali il numero di giri pari alla vel_minima, considerando che mi trovo a questa velocit�
		
		cambia_rapporto(); //cambio il rapporto		
		
		double spazio = Math.abs(0.5 * decelerazione * Math.pow(this.auto.getTrasmissione().getTempo_cambio(), 2));//spazio percorso durante il cambio di rapporto, la decelerazione � sempre la stessa
				
		//calcolo il tempo di percorrenza prima di effettuare il cambio
		double tempo_dec = (velocita_min - velocita_attuale)/decelerazione;
		
		//calcolo la velocit� dell'auto dopo il cambio di rapporto, sapendo che la velocit� prima del cambio di rapporto era pari a vel_min e che il tempo � pari al tempo per cambiare il rapporto
		double vel = 0;
		
		if(spazio_percorso+spazio>=spazio_restante) //lo spazio_percorso � maggiore della lunghezza del segmento che sto attraversando
		{
	    spazio_percorso = spazio_restante;
		vel = Math.sqrt(Math.pow(velocita_min, 2)+(2*decelerazione*(spazio_restante - spazio_percorso)));
		double tempo_cambio = (vel - velocita_min)/decelerazione;
		this.info.setTempo_parziale(tempo_cambio + (tempo_dec));
		}
		else
		{
		
		  spazio_percorso += spazio; //calcolo lo spazio percorso totale (prima del cambio di marcia + spazio percorso durante il cambio di marcia)
		  vel = velocita_min + ((this.auto.getTrasmissione().getTempo_cambio()*decelerazione));
		  //calcolo il tempo totale
		  this.info.setTempo_parziale(this.auto.getTrasmissione().getTempo_cambio() + (tempo_dec));
		}
		
		
		
		
		
		
		//this.tempo_parziale += this.auto.getTrasmissione().getTempo_cambio() + (tempo_dec);	
		this.info.setVelocita_attuale(vel);
	//	this.velocita_attuale = vel;//velocita_max;
		this.info.setPotenza_attuale(auto.getMotore().get_Potenza(calcola_rpm(vel)));
	//	this.potenza_attuale = auto.getMotore().get_Potenza(calcola_rpm(vel));
		this.info.setRpm_attuali(calcola_rpm(vel));
	//	this.rpm_attuali = calcola_rpm(vel);		
		
		return spazio_percorso;
		
	}
	

	public void frena(double densita, double lunghezza_segmento, double raggio, double vel_limite, boolean new_punto, int num_punti)
	{		
		
		double spazio_percorso = 0; //inizialemento lo spazio percorso � zero
		double lunghezza_parziale = lunghezza_segmento; //lunghezza parziale
		double vel_finale = 0;
		boolean vel_costante = false; //flag che serve per il constrollo a velocit� costante
		
		if(this.info.getVelocita_attuale()<=vel_limite)
			vel_costante = true;
		
		while(spazio_percorso<lunghezza_segmento)
		{	
		double decelerazione = 0;
		double rpm_finali = 0;
		
		if(raggio==0)
		decelerazione = getDecelerazione_Rettilineo(densita);//calcola la decelerazione dell'auto considerando la velocit� attuale dell'auto
		//Nel caso in cui � stato scalato un rapporto la decelerazione viene considerata con la velocit� dopo aver scalato il rapporto
		else
			decelerazione = getDecelerazione_Curva(densita, raggio, vel_limite);
		
		double massa_auto = (getAuto().getMassa_auto()+getPilota().getPeso()+this.info.getMassa_carburante()); //calcola la massa dell'auto
		
		if(!vel_costante)
		vel_finale = Math.sqrt(Math.pow(this.info.getVelocita_attuale(), 2)+(2*decelerazione*lunghezza_parziale)); //vel_finale alla fine del segmento
		
			
			
		if(vel_finale<vel_limite) //La velocit� finale � minore della velocit� che devo raggiungere quindi calcolo lo spazio che mi serve per raggiungere la velocita limite, e poi continuo a velocita costante
		{
				vel_costante = true;
				double distanza = 0; //questa � la distanza percorsa per raggiungere la vel_limite
				
				if(raggio==0) distanza = getSpazio_Frenata_Rettilineo(vel_limite,densita); //caso in cui sono in rettilineo
				else distanza= getSpazio_Frenata_Curva(vel_limite, densita, raggio); //caso in cui sono in curva
				if(distanza==-0.0) distanza = 0;
				spazio_percorso += distanza; //calcolo lo spazio percorso
				lunghezza_parziale = lunghezza_segmento - spazio_percorso; //calcolo lo spazio che devo ancora percorrere
				vel_finale = vel_limite; //imposto la vel_finale alla vel_limite (raggiunta dopo aver percorso lo spazio "distanza")
				
				rpm_finali = calcola_rpm(vel_finale);
				
				if((rpm_finali>getAuto().getRpm_min())) //nel caso in cui non effettuo il cambio di rapporto vado a calcolare il tempo impiegato per reggiungere la velocit� desiserata
				{
					double tempo_parziale = (vel_finale-this.info.getVelocita_attuale())/decelerazione; //tempo necessario per passare alla velocit� limite
					tempo_parziale += this.info.getTempo_parziale(); //sommo il valore ottenuto al tempo precedente
					this.info.setTempo_parziale(tempo_parziale);
				}
				
		}
		
		rpm_finali = calcola_rpm(vel_finale); //calcolo il numero di giri corrispondenti alla velocit� finale
		
		if((rpm_finali<getAuto().getRpm_min())&&this.info.getRapporto_attuale()!=1)//se il numero giri � minore del numero di giri minimi allora significa che ho scalato rapporto quando stavo frenando
			{
				spazio_percorso += scala_rapporto(decelerazione, densita, massa_auto, lunghezza_parziale);//scala il rapporto � calcola lo spazio percorso ed il tempo impiegato per il cambio di marcia (totale)
				lunghezza_parziale = lunghezza_segmento - spazio_percorso; //
			}
		else
			{
			    spazio_percorso = lunghezza_segmento;		
			
				double velocita_attuale = this.info.getVelocita_attuale();//this.velocita_attuale;
				double tempo_parziale = 0;
				
				if(!vel_costante)
				tempo_parziale = (vel_finale-velocita_attuale)/decelerazione;
				else
				tempo_parziale = lunghezza_parziale/vel_finale; //la velocit� � costante quindi calcolo il tempo restante per percorere lo spazio rimanente
				
			//	tempo_parziale *= this.pilota.coeffCurva(); //pilota
				
				this.info.setTempo_parziale(tempo_parziale+this.info.getTempo_parziale());
								
				this.info.setVelocita_attuale(vel_finale);
				
				this.info.setRpm_attuali(calcola_rpm(vel_finale));
				
				this.info.setPotenza_attuale(auto.getMotore().get_Potenza(calcola_rpm(vel_finale)));
				
		
			}		
		}
		
		if(new_punto)
		{
			InfoPunto puntos = (InfoPunto) this.info.clone();
			
			setPosizione_attuale(this.posizione_attuale+1, num_punti, false);

			lista_punti.add(puntos);

		}
		
	 }
	

	
	
	
	public double max_velocita_curva(double densita, double raggio)
	{
		
		double massa_auto =  this.auto.getMassa_auto()+this.pilota.getPeso()+this.info.getMassa_carburante();
		double fd = Math.pow(this.auto.getFd()*densita,2);
		double ft = Math.pow(this.auto.getPneumatico().getCoefficiente()*massa_auto*9.81, 2);
		
		double velocita_max = Math.pow((ft/(fd+(Math.pow(massa_auto/raggio, 2)))), 0.25);
		
		return velocita_max;
	
	}

	
	
	
	
	public double calcola_rpm(double velocita){
		
		double rpm_finali = getAuto().calcolo_rpm_attuali(velocita, this.info.getRapporto_attuale());//rapporto_attuale);
		
	//	this.rpm_attuali= rpm_finali;
		
		return rpm_finali;
	}
	
	
	public int getPosizione_attuale() {
		return posizione_attuale;
	}
	
	public void setPosizione_attuale(int posizione_attuale, int num_punti, boolean setStart) {
				
		
		if((this.posizione_attuale+1)<num_punti)
		{
			this.posizione_attuale = posizione_attuale;
		}
		else
		{
			this.posizione_attuale = 0;			
		}
		

		
	}
	


	public Auto getAuto() {
		return auto;
	}
	
	public void setAuto(Auto auto) {
		this.auto = auto;
	}
	
	public Pilota getPilota() {
		return pilota;
	}
	public void setPilota(Pilota pilota) {
		this.pilota = pilota;
	}
	


//	public double getFd() {
//		return fd;
//	}
//
//
//	public void setFd() {
//		
//		this.fd = auto.getFd();
//	}



   
	//serve per cambiare rapporto
	public void cambia_rapporto() 
	{
		int rapporto_attuale = this.info.getRapporto_attuale();
		
		int numero_rapporti = auto.getTrasmissione().getN_rapporti();
		
		if(this.info.getRpm_attuali()<=auto.getRpm_min())
		{
			if(this.info.getRapporto_attuale()!=1)
		 	rapporto_attuale--;
		}
		else if(this.info.getRpm_attuali()>=auto.getRpm_max())
		{
			if(this.info.getRapporto_attuale()!=numero_rapporti)
			rapporto_attuale++;			
		}
		
		
		this.info.setRapporto_attuale(rapporto_attuale);
		
		
	}



	//Calcola la mssima velocit� d'ingresso che io posso avere prima di affrontare una determinata curva, anche se la velocit� di percorrenza della curva � minore rispetto alla velocit� d'ingresso
	public double getVelocita_Freno(double vel_max_succ, double distanza, double densita, double raggio) 
	{		
		
		double massa_auto = this.auto.getMassa_auto()+this.pilota.getPeso()+this.info.getMassa_carburante();
		
		double fd = this.auto.getFd()*densita*Math.pow(vel_max_succ, 2);
		double ft = Math.pow(this.auto.getPneumatico().getCoefficiente(), 2) * Math.pow((massa_auto*9.81), 2);
		double fc = 0;
		if(raggio!=0)
		fc = Math.pow(((massa_auto*Math.pow(vel_max_succ, 2))/raggio), 2);
		
		double fb = Math.sqrt(ft-fc);
		
		double fs = fd + fb;
		
		double velocita_ingresso = Math.sqrt(Math.pow(vel_max_succ, 2)+((2*distanza*fs)/massa_auto));
		
		return velocita_ingresso;
	}



	public double getSpazio_Frenata_Rettilineo(double vel_max_succ,double densita) //calcola lo spazio necessario per frenare sapendo la velocit� max finale e la velocit� attuale
	{
				
		double decelerazione = getDecelerazione_Rettilineo(densita);			
		
		//double spazio = (-Math.pow(velocita_attuale, 2)+Math.pow(vel_max_succ, 2))/(2*decelerazione);
		double spazio = (-Math.pow(this.info.getVelocita_attuale(), 2)+Math.pow(vel_max_succ, 2))/(2*decelerazione);
		
		return spazio;
		
	}
	
	private double getDecelerazione_Rettilineo(double densita)//calcola la decelerazione nel caso in cui sto frenando in rettilineo
	{//la decelerazione dipente da Ft e Fd
		
		//double velocita_attuale= this.velocita_attuale;
		double velocita_attuale = this.info.getVelocita_attuale();
		
		double massa_auto = this.auto.getMassa_auto()+this.pilota.getPeso()+this.info.getMassa_carburante();
		
		double fd = this.auto.getFd()*densita*Math.pow(velocita_attuale, 2); //forza relativa al drag
		double ft = Math.pow(this.auto.getPneumatico().getCoefficiente(), 2) * Math.pow((massa_auto*9.81), 2); //forza relativa all'attrito delle gomme
						
		double fb = Math.sqrt(ft);
		
		double fs = fd + fb;
		
		double decelerazione = -fs/massa_auto;		
		
		return decelerazione;
	}
	
	public double getDecelerazione_Curva(double densita, double raggio, double vel_finale)//calcola la decelerazione nel caso in cui sto frenando in curva
	{
		double velocita_attuale= this.info.getVelocita_attuale();//this.velocita_attuale; //velocit� attuale
		
		double massa_auto = this.auto.getMassa_auto()+this.pilota.getPeso()+this.info.getMassa_carburante();
		
		double fd = this.auto.getFd()*densita*Math.pow(velocita_attuale, 2); //forza relativa al drag
		
		double ft = Math.pow(this.auto.getPneumatico().getCoefficiente(), 2) * Math.pow((massa_auto*9.81), 2); //forza relativa all'attrito delle gomme
						
		//double fc = Math.pow(((massa_auto*Math.pow(vel_finale, 2))/raggio), 2); //forza centripeta
		
		double fc = Math.pow(((massa_auto*Math.pow(velocita_attuale, 2))/raggio), 2); //forza centripeta
		
		double fb = Math.sqrt(ft-fc);
		
		double fs = fd + fb;
		
		double decelerazione = -fs/massa_auto;		
		
		return decelerazione;
	}


	public double getSpazio_Frenata_Curva(double vel_max_succ ,double densita , double raggio) {
		
		double decelerazione = getDecelerazione_Curva(densita, raggio, vel_max_succ);			
		
	//	double spazio = (-Math.pow(velocita_attuale, 2)+Math.pow(vel_max_succ, 2))/(2*decelerazione);
		
		double spazio = (-Math.pow(this.info.getVelocita_attuale(), 2)+Math.pow(vel_max_succ, 2))/(2*decelerazione);
		
		return spazio;
		
	}
	
	public InfoPunto getInfo_Punto() {
		return info;
	}
	
	public void setInfo_Punto(InfoPunto punto){
		this.info = punto;
	}
	
	public ArrayList<InfoPunto> getLista_punti() {
		return lista_punti;
	}


	public void setLista_punti(int n) 
	{
		if(this.lista_punti == null){		
		
		this.lista_punti = new ArrayList<InfoPunto>(); //inizializza lista_punti
		
		InfoPunto punto_iniziale = (InfoPunto) info.clone(); 		
		
		this.lista_punti.add(punto_iniziale);
		}
	}


	
	public InfoPunto getPunto_Specifico(int posizione)
	{
		return lista_punti.get(posizione);
	}


	public byte getGiro_attuale() {
		return giro_attuale;
	}


	public void setGiro_attuale(byte giro_attuale) {
		this.giro_attuale = giro_attuale;
	}


	public int getPosizione_partenza() {
		return posizione_partenza;
	}


	public void setPosizione_partenza(int posizione_partenza) {
		this.posizione_partenza = posizione_partenza;
	}


//	public double getMassa_combustibile() {
//		return massa_combustibile;
//	}
//
//
//	public void setMassa_combustibile(double massa_combustibile) {
//		this.massa_combustibile = massa_combustibile;
//	}
	
	public void setDurataPneumatico(int temperatura, double lunghezza_giro) //imposta la durata del pneumatico in base alla temperatura
	{
		int durata_base = this.getAuto().getPneumatico().getDurata(); //durata base in Km
		int durata_finale = 0;
		int differenza = 0;
	
		if(temperatura!=25) //se la temperatura � diversa da 25 �C allora ho un aumento o diminuizione della durate delle gomme
		{
			differenza = 25 - temperatura;
			durata_finale = (int) ((int) durata_base + (durata_base * differenza * 0.01)); //l'aumento/diminuizione varia dell'1% per ogni grado di temperatura
		}
		else
		{
		durata_finale = durata_base;	
		}
		
		int numero_giri = (int) (durata_finale*1000/lunghezza_giro); //numero di giri massimo che posso fare con queste gomme
		
		this.auto.getPneumatico().setNumero_giri(numero_giri); //imposto il numero di giri che posso fare con questo tipo di gomma
		
		double rid_attrito = (this.getAuto().getPneumatico().getCoefficiente() * 0.015)/(numero_giri); //imposto il coefficiente di riduzione dell'attrito delle gomme(max 1,5 % in totale)
		
		this.auto.getPneumatico().setCoeff_attrito(rid_attrito); //imposto il coeff. di diminuzione dell'attrito delle gomme
		
	}


	public void setUsuraPneumatico(double lunghezza_giro)  //calcolo la durate delle gomme ed il nuovo coeff. d'attrito
	{
	
	  int numero_giri = this.auto.getPneumatico().getNumero_giri();
		
	  double coeff_gomma = this.auto.getPneumatico().getCoefficiente() - (this.auto.getPneumatico().getCoeff_attrito() * this.pilota.coeffGomme()); //imposto il nuovo valore del coefficiente d'attrito
	  this.auto.getPneumatico().setCoefficiente(coeff_gomma);
	  numero_giri--;
	  this.getAuto().getPneumatico().setNumero_giri(numero_giri);
	
	  if(numero_giri==2)
	  {		 
		  this.pit_stop = true; //devo effettuare il pitstop la prossima volta che passo per il rettilineo principale
	  }
	  
		
		
		    
	}

	public void setPitStop(boolean pit) {
		this.pit_stop = pit;
		
	}

	public int getPosizione_griglia() {
		return posizione_griglia;
	}

	public void setPosizione_griglia(int posizione_griglia) {
		this.posizione_griglia = posizione_griglia;
	}

	public void setTempiSettore(int giri_gara) 
	{
		if(this.lista_tempi == null)
		{		
			this.lista_tempi = new ArrayList<TempiSettore>(giri_gara); //inizializza lista_tempi			
		}
		
	}
	
	
	
	public ArrayList<TempiSettore> getListaTempi(){
		
		return this.lista_tempi;
	}

	public int getNumero_pitstop() {
		return numero_pitstop;
	}

	public void setNumero_pitstop() {
		this.numero_pitstop++;
	}

	public double getGap() {
		return gap;
	}

	public void setGap(double gap) {
		this.gap = gap;
	}

	public double getGap_precedente() {
		return gap_precedente;
	}

	public void setGap_precedente(double gap_precedente) {
		this.gap_precedente = gap_precedente;
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
	
	public static class OrdinaTempi implements Comparator<TempiSettore>
	{

		@Override
		public int compare(TempiSettore t1, TempiSettore t2) {
			
			int risultato = Double.compare(t1.getTempo_totale(), t2.getTempo_totale());
			
			return risultato;
		}
	}

	@Override
	public int compare(Giocatore arg0, Giocatore arg1) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void resetTempi()
	{	
		
		for(int f = this.getListaTempi().size()-1;f>0; f--)
		{
			this.getListaTempi().remove(f);		
		}
	}
	
	public void setNull(){
		
		this.lista_tempi = null;
		this.lista_punti = null;
		
		this.setGiro_attuale((byte) 0);
		this.setGap_precedente(0);	
		this.numero_pitstop = 0;
		
	}

	public int getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}

	public boolean isGioc_reale() {
		return gioc_reale;
	}

	public void setGioc_reale(boolean gioc_reale) {
		this.gioc_reale = gioc_reale;
	}

	public ArrayList<XYSeriesCollection> getGiri_grafico() {
		return giri_grafico;
	}

	public void setGiri_grafico(ArrayList<XYSeriesCollection> giri_grafico) {
		this.giri_grafico = giri_grafico;
	}
	
	public void resetGiriGrafico(){
		this.giri_grafico = null;
	}


		
}


