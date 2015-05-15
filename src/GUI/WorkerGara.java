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
package GUI;


import gara.Gara;
import giocatore.Giocatore;







import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import campionato.Campionato;




public class WorkerGara extends SwingWorker<Void, TabellaTempi> {

	private Gara_GUI frame;
	private TabellaTempi tab;
	private ArrayList<ChartPanel> pannelli = null;
	boolean aggiorna_grafico = false;

	String nome; //nome e cognome del vincitore
	String cognome;
	
	/**
	 Al termine di doInBackground il worker thread invoca il metodo done();
	 Il risultato pu� essere ottenuto invocando su questo oggetto il metodo get();	
	 
	Per eseguire attivit� particolari dopo doInBackground bisogna effettuare l'overridding del metodo done(), che viene invocato automaticamente al completamento di doInBackground()
	e viene eseguito nell'event dispatch thread -> � possibile aggiornare la GUI!
	
	E' possibile visualizzare risutati intermedi:
	all'interno del metodo doInBackground() invocare il metodo publish() specificando uno
	o pi� risultati intermedi della computazione (del tipo specificato nella definizione della
	classe):
	eseguire l'overriding del metodo process(List<V> chunks), il quale
	- riceve una lista di risultati intermedi prodotti e notificati da un'invocazione di
	publish()
	- viene eseguito nell'event dispatch thread (EDT) -> possibile aggiornare la GUI!
	
	
	**/
	
	//Costruttore principale
	public WorkerGara(Gara_GUI frame){
		this.frame = frame;
		//this.tab = this.frame.getTab();
	}
	
	//Codice da eseguire
	protected  Void doInBackground() throws Exception{
		if (!isCancelled())
		{
			try 
			{
			//simulo la gara
			simulazione_gara();	
			int numero_giri = this.frame.getCampionato().getLista_gare().get(this.frame.getCampionato().getGara_attuale()).getGiri_gara();
				
			//creo la TabellaTempi
			//TabellaTempi tabella = new TabellaTempi(this.frame.getPartecipanti());
			
			TabellaTempi tabella = new TabellaTempi(this.frame.getCampionato().getLista_gare().get(this.frame.getCampionato().getGara_attuale()).getPartecipanti());
			
			publish(tabella);	
			
			Thread.sleep(1000);
				
			  for (int i = 0; i < numero_giri; i++)  //ciclo per il numero di giri
			  {			
				
				if(i!=0)
				{
					tabella.incrementaGiro();
			    }
				
				for (int j = 0; j < 3; j++) //ciclo che scorre i settori
				{
					
					//calcola per ogni pilota, il gap che ha nei confronti del pilota che lo precede per ogni settore del giro considerato
					tempi_settore(tabella.getPartecipanti(), j, i); 
					
					tabella.setSettore(j);//imposto il settore che voglio far visualizzare per il pilota che verr� selezionato (e quelli precedenti per gli altri piloti)

					
					for(int n=0; n<tabella.getPartecipanti().size(); n++) //cicla i partecipanti della gara
						
						//n corrisponde al partecipante a cui devo aggiornare il tempo
						
						{
								
						tabella.setGiocatore_selezionato(n); //imposto il numero max di giocatore che devo far comparire nella tabella (1-n)
						 
						 Thread.sleep(1000);
						 						 
						 tabella.setFlag(true);
						 
						 publish(tabella);
						
						 Thread.sleep(250);
						 
						}
					
				}
				
				inizializza_grafico(i);
				this.aggiorna_grafico = true;
				publish(tabella);
				Thread.sleep(100);
				
						
			  }
			 
			  
			  
			  //assegno i punteggi in  ordine di arrivo
				for (int i = 0; i < this.frame.getCampionato().getLista_gare().get(this.frame.getCampionato().getGara_attuale()).getPartecipanti().size(); i++) 
				{				
					Giocatore partecipante = this.frame.getCampionato().getLista_gare().get(this.frame.getCampionato().getGara_attuale()).getPartecipanti().get(i);
					
					int punteggio = partecipante.getPunteggio();
					
					
					if(i>9) break;
					
					else if(i==0)	{
						nome = partecipante.getPilota().getNome();
						cognome = partecipante.getPilota().getCognome();
						punteggio += 25;
					}												
					else if(i==1)					
					    punteggio += 18;
					else if(i==2)
						punteggio += 15;
					else if(i==3)					
					    punteggio += 12;
					else if(i==4)
						punteggio += 10;
					else if(i==5)					
					    punteggio += 8;
					else if(i==6)
						punteggio += 6;
					else if(i==7)					
					    punteggio += 4;
					else if(i==8)
						punteggio += 2;
					else if(i==9)					
					    punteggio += 1;		
					
					partecipante.setPunteggio(punteggio);
					partecipante.setNull();				
				}				
				
				int gara_attuale = this.frame.getCampionato().getGara_attuale();
				
				Collections.sort(this.frame.getCampionato().getLista_gare().get(gara_attuale).getPartecipanti(), new Gara.SortPunteggio());//ordina i piloti in base al punteggio
				Collections.reverse(this.frame.getCampionato().getLista_gare().get(gara_attuale).getPartecipanti());
				
				this.frame.getCampionato().setPartecipanti(this.frame.getCampionato().getLista_gare().get(gara_attuale).getPartecipanti());	
				
				
			    
				gara_attuale++;
				this.frame.getCampionato().setGara_attuale(gara_attuale);			
					
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		else return null;
		return null;
	}
	



	@Override
	protected void done(){ //chiamato dall'EDT dopo che � stato completato doInBackground
			try 
			{
				//faccio comparire la schermata finale
				this.frame.frmFine(nome, cognome);
								
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	
	@Override
	protected void process(List<TabellaTempi> chunks){ // eseguito nell'EDT, aggiorna GUI con risultati intermedi
		
		if(this.aggiorna_grafico)
		{
			aggiorna_grafico();
			this.aggiorna_grafico = false;
		}
		
		else
		{
	//	TabellaTempi tabella = new TabellaTempi(this.frame.getPartecipanti());	
		
		this.frame.setJTabellaTempi(chunks.get(chunks.size()-1));
	//	this.frame.get
	    // aggiornaTempi(chunks.get(0), chunks.get(1));
	     chunks.clear();
		}
		//frame.getBtn_Start().setEnabled(true);
	}
	
private void simulazione_gara() 
{
	

	//i valori delle auto sono gi� settati, devo solo impostare le velocit� di partenza e la posizione in griglia
	int n_gara = frame.getCampionato().getGara_attuale();
	
	Gara gara = frame.getCampionato().getLista_gare().get(n_gara); //Gara che devo svolgere
	
	gara.setGiri_gara();
	
	ArrayList<Giocatore> partecipanti = gara.getPartecipanti(); //non ho necessit� di effettuare la copia della lista dei partecipanti
	
	for (int i = 0; i < partecipanti.size(); i++) 
	{
		Giocatore partecipante = partecipanti.get(i);
		
		partecipante.setNull(); //resetta tutto		
		
		//Imposto i valori iniziali dell'auto
		partecipante.getInfo_Punto().setRapporto_attuale(1);
		partecipante.getInfo_Punto().setVelocita_attuale(2.77);
		partecipante.getInfo_Punto().setRpm_attuali(partecipante.getAuto().calcolo_rpm_attuali(2.77, 1));
		partecipante.getInfo_Punto().setPotenza_attuale(partecipante.getAuto().getMotore().get_Potenza(partecipante.getAuto().calcolo_rpm_attuali(2.77, 1)));
		
		
		//Dati Parziali da rivedere		
	//	partecipante.getAuto().setRpm_min(2500);
	//	partecipante.getAuto().setRpm_max(8000);
		partecipante.getAuto().setFd();
		partecipante.getAuto().getTrasmissione().setTempo_cambio(0.3*partecipante.getPilota().coeffTrasmissioni());
	//	partecipante.getAuto().getTrasmissione().setTempo_cambio(0.3);					   
		partecipante.getAuto().getMotore().setConsumo(gara.getTemperatura());
		//fine dati parziali
		
		partecipante.setPosizione_griglia(i+1); //siccome la lista dei partecipanti � ordinata secondo la posizione di partenza, l'indice i aumentato di un unit� mi indica la posizione sulla griglia
		
	}
	
	//imposto il carburante per tutti i partecipanti
	gara.setCarburante();
	//inizializzo la posizione fisica dei partecipanti sulla pista
	gara.setPosizionePartecipanti();
	
	//simulo la gara
    gara.startGara();   
    
   // this.frame.setPartecipanti(partecipanti);//imposto i partecipanti, e quindi la gara    
		
}
private void tempi_settore(ArrayList<Giocatore> partecipanti, int numero_settore, int giro) {
	
	for (int i = 1; i < partecipanti.size(); i++) 
	{		
		calcola_gap(partecipanti, i, giro, numero_settore); //calcola l'intervallo e la posizione del pilota selezionato con quello del pilota che � davanti a me
	}
	
	
	setGapPartecipanti(partecipanti, numero_settore , giro);
	
	
}

//Una volta calcolati tutti gli intervalli tra i vari partecipanti calcolo il gap (per il settore specificato) di ogni partecipante
private void setGapPartecipanti(ArrayList<Giocatore> partecipanti, int numero_settore, int giro) 
{
	double gap_parziale = 0;
		
	
	for (int i = 1; i < partecipanti.size(); i++) //scorre tutti i partecipanti tranne il primo
	{
		if(numero_settore==0)
		{
			gap_parziale += partecipanti.get(i).getListaTempi().get(giro).getGap_1();
		}
		
		else if (numero_settore==1) 
		{
			gap_parziale += partecipanti.get(i).getListaTempi().get(giro).getGap_2();
		}
		else if(numero_settore==2)
		{
			gap_parziale += partecipanti.get(i).getListaTempi().get(giro).getGap_3();
		}
		
		if(giro==0&&numero_settore==0||partecipanti.get(i).getGap()==0)
			partecipanti.get(i).setGap_precedente(gap_parziale);
		else
			partecipanti.get(i).setGap_precedente(partecipanti.get(i).getGap());
		partecipanti.get(i).setGap(gap_parziale);
		
	}
	
		
}

private void calcola_gap(ArrayList<Giocatore> partecipanti, int gioc_attuale, int n_giro, int numero_settore) //calcola il gap tra i partecipanti
{
	
	
	Giocatore partecipante_successivo = partecipanti.get(gioc_attuale-1); //L'avversario che mi sta davanti
	Giocatore partecipante_attuale = partecipanti.get(gioc_attuale);//Giocatore attuale		
	
	double totale_successivo = 0;
	double totale_attuale = 0;
	double differenza = 0;
	double precedente = 0;
	
	boolean flag = true; //controlla l'accesso per il cambio di posizione
	
	if(numero_settore==0&&n_giro==0) //primo settore quindi vado a considerare il gap della partenza
	{
	//calcolo il tempo totale che impiega l'auto davanti a me per arrivare all'inizo del secondo settore(fine primo settore)
//	totale_successivo = partecipante_successivo.getGap() + partecipante_successivo.getListaTempi().get(n_giro).getSettore_1(); prima con getgap in gara
	totale_successivo = partecipante_successivo.getGap_precedente() + partecipante_successivo.getListaTempi().get(n_giro).getSettore_1();
	
	//calcolo il tempo che impiega l'auto  per arrivare all'inizo del secondo settore(fine primo settore)
//	totale_attuale = partecipante_attuale.getGap() + partecipante_attuale.getListaTempi().get(n_giro).getSettore_1();	
	totale_attuale = partecipante_attuale.getGap_precedente() + partecipante_attuale.getListaTempi().get(n_giro).getSettore_1();
	
	}
	else //caso in cui il giro � diverso da quello iniziale
	{
		if(numero_settore==0)
		{
			totale_successivo =  partecipante_successivo.getListaTempi().get(n_giro).getSettore_1(); //partecipante_successivo.getGap() + partecipante_successivo.getListaTempi().get(n_giro).getSettore_1();
			//totale_attuale = partecipante_attuale.getGap() + partecipante_attuale.getListaTempi().get(n_giro).getSettore_1();
			totale_attuale = partecipante_attuale.getListaTempi().get(n_giro-1).getGap_3() + partecipante_attuale.getListaTempi().get(n_giro).getSettore_1();
		}
	    else if(numero_settore==1)
		{
	    	
			totale_successivo = partecipante_successivo.getListaTempi().get(n_giro).getSettore_2();// partecipante_successivo.getGap() + partecipante_successivo.getListaTempi().get(n_giro).getSettore_2();
	//		totale_attuale = partecipante_attuale.getGap() + partecipante_attuale.getListaTempi().get(n_giro).getSettore_2();
			totale_attuale = partecipante_attuale.getListaTempi().get(n_giro).getGap_1() + partecipante_attuale.getListaTempi().get(n_giro).getSettore_2();
		}
		else
		{
			totale_successivo = partecipante_successivo.getListaTempi().get(n_giro).getSettore_3();//partecipante_successivo.getGap() + partecipante_successivo.getListaTempi().get(n_giro).getSettore_3();
			//totale_attuale = partecipante_attuale.getGap() + partecipante_attuale.getListaTempi().get(n_giro).getSettore_3();
			totale_attuale = partecipante_attuale.getListaTempi().get(n_giro).getGap_2() + partecipante_attuale.getListaTempi().get(n_giro).getSettore_3();
		}
		
	}	
	
	differenza =  totale_attuale - totale_successivo;
	
//	double gap_precedenti = calcolaGapPrecedenti(partecipanti, gioc_attuale, n_giro); //calcolo il gap dei piloti che sono davanti a me
	
	if(differenza < 0) //vuol dire che ho superato l'auto davanti a me
	{			
//		if(n_giro==0 ) partecipante_successivo.setGap_precedente(Math.abs(differenza)+gap_precedenti);
//		else partecipante_successivo.setGap_precedente(partecipante_successivo.getGap());
		
	//	if(n_giro!=0 ) partecipante_successivo.setGap_precedente(partecipante_successivo.getGap());	 //da rivedere
	//	else if(n_giro==0&&partecipante_successivo.getPosizione_griglia()==1) partecipante_successivo.setGap(Math.abs(differenza));
	//	else
	//	partecipante_successivo.setGap(Math.abs(differenza)+gap_precedenti);
		
	
		if(numero_settore==0)
		{ 
			
			if(partecipante_successivo.getPosizione_griglia()==1)
			{
			partecipante_attuale.setGap(0);			
			partecipante_attuale.getListaTempi().get(n_giro).setGap_1(0);
			partecipante_successivo.getListaTempi().get(n_giro).setGap_1(Math.abs(differenza));
			}
			else
			{				
				if(flag)
				{
				ricalcola_gap(partecipanti, partecipante_attuale, partecipante_successivo, gioc_attuale, n_giro, numero_settore, differenza);				
				flag = false;
				}
			}
			
		}
		else if(numero_settore==1)
		{
	//		partecipante_successivo.getListaTempi().get(n_giro).setGap_2(Math.abs(differenza)); //giocatore_attuale supera giocatore_successivo, quindi imposto il gap di giocatore_successivo nei confronti di giocatore_attuale
			
			//se il partecipante che ho superato era il primo, allora imposto l'intervallo di giocatore_attuale a zero
			if(partecipante_successivo.getPosizione_griglia()==1)
			{
				partecipante_attuale.getListaTempi().get(n_giro).setGap_2(0);
				partecipante_successivo.getListaTempi().get(n_giro).setGap_2(Math.abs(differenza)); //giocatore_attuale supera giocatore_successivo, quindi imposto il gap di giocatore_successivo nei confronti di giocatore_attuale
			}
			
			else if(flag)//devo calcolare il gap di partecipante_attuale nei confronti del giocatore che � davanti al giocatore che ho superato
			{			
				ricalcola_gap(partecipanti, partecipante_attuale, partecipante_successivo, gioc_attuale, n_giro, numero_settore, differenza);				
				flag = false;					
			}
		}
		else
		{
	//		partecipante_successivo.getListaTempi().get(n_giro).setGap_3(Math.abs(differenza));			
			if(partecipante_successivo.getPosizione_griglia()==1)
			{
			partecipante_attuale.getListaTempi().get(n_giro).setGap_3(0);
			partecipante_successivo.getListaTempi().get(n_giro).setGap_3(Math.abs(differenza));
			}			
			else if(flag)
			{			
				ricalcola_gap(partecipanti, partecipante_attuale, partecipante_successivo, gioc_attuale, n_giro, numero_settore, differenza);				
				flag = false;					
			}
		}
		
		if(flag)
		{
		partecipante_successivo.setPosizione_griglia(partecipante_successivo.getPosizione_griglia()+1);
		partecipante_attuale.setPosizione_griglia(partecipante_attuale.getPosizione_griglia()-1);			
		Collections.swap(partecipanti, gioc_attuale, (gioc_attuale-1));	
		}
		
	}
		
	else if(differenza>0) //mi trovo dietro l'auto successiva
	{
//		if(n_giro==0&&numero_settore==0) partecipante_attuale.setGap_precedente(Math.abs(differenza)+gap_precedenti);
//		else 
//		{
			//double gap_precedente =(;
//			partecipante_attuale.setGap_precedente(partecipante_attuale.getGap());
//		}
		
//		partecipante_attuale.setGap(Math.abs(differenza)+gap_precedenti);
		
		if(numero_settore==0)
		{
			if(partecipante_successivo.getPosizione_griglia()==1) 
				partecipante_successivo.getListaTempi().get(n_giro).setGap_1(0);
		//	else
				
			partecipante_attuale.getListaTempi().get(n_giro).setGap_1(Math.abs(differenza));
		}
		else if(numero_settore==1)
		{
			if(partecipante_successivo.getPosizione_griglia()==1)
				partecipante_successivo.getListaTempi().get(n_giro).setGap_2(0);
		//	else
			partecipante_attuale.getListaTempi().get(n_giro).setGap_2(Math.abs(differenza));
		}
		else
		{
			if(partecipante_successivo.getPosizione_griglia()==1)
				partecipante_successivo.getListaTempi().get(n_giro).setGap_3(0);
		//	else
			partecipante_attuale.getListaTempi().get(n_giro).setGap_3(Math.abs(differenza));
		}
		
	}
	
	else //sono a testa a testa con l'auto che mi precede
	{
		if(numero_settore==0)
		{
			partecipante_successivo.getListaTempi().get(n_giro).setGap_1(0);
			partecipante_attuale.getListaTempi().get(n_giro).setGap_1(0);
		}
		else if(numero_settore==1)
		{
			partecipante_successivo.getListaTempi().get(n_giro).setGap_2(0);
			partecipante_attuale.getListaTempi().get(n_giro).setGap_2(0);
		}
		else
		{
			partecipante_successivo.getListaTempi().get(n_giro).setGap_3(0);
			partecipante_attuale.getListaTempi().get(n_giro).setGap_3(0);
		}			
	}
}

private void ricalcola_gap(ArrayList<Giocatore> partecipanti, Giocatore partecipante_attuale, Giocatore partecipante_successivo, int gioc_attuale, int n_giro, int numero_settore, double differenza) 
{
	if(!(numero_settore==0&&n_giro==0))
	{
		//double gapGiocAttuale =	gapGiocatoreprecedente(partecipanti, gioc_attuale, n_giro, numero_settore, differenza);
		gapGiocatoreprecedente(partecipanti, gioc_attuale, n_giro, numero_settore, differenza);
		//partecipante_successivo.getListaTempi().get(n_giro).setGap_2(Math.abs(differenza));
		//partecipante_attuale.getListaTempi().get(n_giro).setGap_2(Math.abs(differenza));
	}
	
	
	int posizioneAfterSwap = 0;
	int posizione_attuale = partecipante_attuale.getPosizione_griglia()-1; //posizione che avr� giocatore_attuale dopo il sorpasso
	
		
	partecipante_successivo.setPosizione_griglia(partecipante_successivo.getPosizione_griglia()+1);	//imposto la posizione del giocatore che ho superato
	partecipante_attuale.setPosizione_griglia(posizione_attuale);	//imposto la posizione di giocatore attuale
	
	Collections.swap(partecipanti, gioc_attuale, (gioc_attuale-1)); //faccio lo swap di posizione tra i due giocatori
	
	//adesso devo ricalcolare il gap del giocatore_attuale nei confronti del nuovo partecipante che mi stava davanti (quello che era davanti giocatore_successivo prima del sorpasso)
	calcola_gap(partecipanti, (gioc_attuale-1), n_giro, numero_settore); //gioc_attuale-1 corrispone alla posizione all'interno della lista di gioc_attuale una volta che questo ha effettuato il sorpasso
	
	posizioneAfterSwap = partecipante_attuale.getPosizione_griglia(); //ottengo la posizione dopo il calcolo del gap
	
	if(posizioneAfterSwap!=posizione_attuale) //se la posizione � diversa da quella impostata precedentemente, quindi devo ricalcolare l'intervallo nei confronti del pilota che ho superato
	{
	//in questo caso gioc_attuale corrisponde all'id del giocatore che risulta essere "dietro" al giocatore che gioc_attuale ha superato precedentemente in calcola_gap
	calcola_gap(partecipanti, (gioc_attuale), n_giro, numero_settore);
	}
	
}

//calcola il gap che ho con il giocatore che sta davanti al giocatore che ho superato
private double gapGiocatoreprecedente(ArrayList<Giocatore> partecipanti,int gioc_attuale, int n_giro, int numero_settore, double differenza) 
{
	double gap = 0;
	int n = gioc_attuale-2;
	if(n<0) n=0;
	int i = gioc_attuale-1;
//	for (int i = gioc_attuale-1; i > n; i--) 
//	{
		if(numero_settore==0)
		{
			gap += partecipanti.get(i).getListaTempi().get(n_giro).getGap_1();
			partecipanti.get(i).getListaTempi().get(n_giro).setGap_1(Math.abs(differenza));
			gap += differenza;
			//partecipanti.get(gioc_attuale).getListaTempi().get(n_giro).setGap_3(gap);
			partecipanti.get(gioc_attuale).getListaTempi().get(n_giro-1).setGap_3(gap);
		}
		
		else if (numero_settore==1) 
		{
			gap += partecipanti.get(i).getListaTempi().get(n_giro).getGap_2();
			partecipanti.get(i).getListaTempi().get(n_giro).setGap_2(Math.abs(differenza));
			gap += differenza;
			partecipanti.get(gioc_attuale).getListaTempi().get(n_giro).setGap_1(gap); //setto gap_1 cos� quando andr� a rifare calcolagap verr� preso questo valore
		}
		else if(numero_settore==2)
		{
			gap += partecipanti.get(i).getListaTempi().get(n_giro).getGap_3();
			partecipanti.get(i).getListaTempi().get(n_giro).setGap_3(Math.abs(differenza));
			gap += differenza;
			partecipanti.get(gioc_attuale).getListaTempi().get(n_giro).setGap_2(gap);
		}	
//	}
	
//	gap -= differenza;
		
//	partecipante_attuale.getListaTempi().get(n_giro).setGap_2(Math.abs(differenza));
	
	return gap;
}

private void inizializza_grafico(int giro_attuale) 
{
	if(this.pannelli!=null) this.pannelli.clear();
	else this.pannelli = new ArrayList<ChartPanel>();
	
	int gara_attuale = this.frame.getCampionato().getGara_attuale();
	double lunghezza_giro = this.frame.getCampionato().getLista_gare().get(gara_attuale).get_Circuito().getLunghezza_giro();
	
	for (int j = 0; j < this.frame.getCampionato().getLista_gare().get(gara_attuale).getPartecipanti().size(); j++) 
	{
		Giocatore partecipante = this.frame.getCampionato().getLista_gare().get(gara_attuale).getPartecipanti().get(j);
		
		if(partecipante.isGioc_reale()) //visualizzo solo i tempi dei partecipanti reali
		{
			JFreeChart grafico = new GraficoTempo().crea_grafico(partecipante, lunghezza_giro, giro_attuale);			
			ChartPanel panel_grafico = new ChartPanel(grafico);
			panel_grafico.setName(partecipante.getPilota().getNome()+" "+partecipante.getPilota().getCognome());
			pannelli.add(panel_grafico);
			//this.frame.getTelemetria().add(panel_grafico, partecipante.getPilota().getNome()+" "+partecipante.getPilota().getCognome());	
		}
	}
	
}

private void aggiorna_grafico() //aggiorna il grafico una volta a giro
{
	this.frame.getTelemetria().removeAll();
	
	for (int i = 0; i < pannelli.size(); i++) 
	{
		pannelli.get(i).getName();
	this.frame.getTelemetria().add(pannelli.get(i), pannelli.get(i).getName());
	//this.frame.getTelemetria().add(pannelli.get(i));		
	}
	
}


public ArrayList<ChartPanel> getPannelli() {
	return pannelli;
}

public void setPannelli(ArrayList<ChartPanel> pannelli) {
	this.pannelli = pannelli;
}



	
}
