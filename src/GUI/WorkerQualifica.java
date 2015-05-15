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

import campionato.Campionato;


public class WorkerQualifica extends SwingWorker<Void, Void> {

	private RiepilogoCampionato frame;
	
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
	public WorkerQualifica(RiepilogoCampionato frame)
	{
		this.frame = frame;		
	}
	
	//Codice da eseguire
	protected  Void doInBackground() throws Exception{
		
		if (!isCancelled())
		{
			try 
			{	
				//creo un nuovo oggetto che contiene la lista dei partecipanti, in modo da non modificare la lista originale in campionato
								
				ArrayList<Giocatore> partecipanti = Campionato.clonaPartecipanti(frame.getCampionato().getPartecipanti());
								
				//ArrayList<Giocatore> partecipanti = frame.getCampionato().getPartecipanti();
				
				int num_gara = frame.getCampionato().getGara_attuale();
				
				Gara qualifica = frame.getCampionato().getLista_gare().get(num_gara);
				
				qualifica.setGiri_Qualifica();
				
				
				
				for (int i = 0; i < partecipanti.size(); i++) 
				{
					Giocatore partecipante = partecipanti.get(i);
					
					//Imposto i valori iniziali dell'auto
					partecipante.getInfo_Punto().setRapporto_attuale(1);
					partecipante.getInfo_Punto().setVelocita_attuale(2.77);
					partecipante.getInfo_Punto().setRpm_attuali(partecipante.getAuto().calcolo_rpm_attuali(2.77, 1));
					partecipante.getInfo_Punto().setPotenza_attuale(partecipante.getAuto().getMotore().get_Potenza(partecipante.getAuto().calcolo_rpm_attuali(2.77, 1)));
					partecipante.getAuto().getMotore().setConsumo(qualifica.getTemperatura());
					
					partecipante.getAuto().getTrasmissione().setTempo_cambio(0.3*partecipante.getPilota().coeffTrasmissioni());
					//partecipante.getAuto().getTrasmissione().setTempo_cambio(0.3);	
										
					
					partecipante.getAuto().setFd();
					
					//Dati Parziali da rivedere
		//			partecipante.getAuto().setRpm_min(2500);
		//			partecipante.getAuto().setRpm_max(8000);
					
					partecipante.getInfo_Punto().setMassa_carburante(10);				   
					
					//fine dati parziali
					
					partecipante.setPosizione_griglia(1); //tutti i partecipanti partono dalla prima posizione
					
					qualifica.setPartecipanti(partecipante); //aggiungo il partecipante alla qualifica
					
					qualifica.resetGomma(partecipante);
				}
				
				//setto la posizione di tutti i partecipanti
				qualifica.setPosizionePartecipanti();
				
			    qualifica.startGara();   //avvio la sessione di qualifica 
			    
			    //Ordino i tempi di ciascun pilota in senso crescente
			    for (int i = 0; i < partecipanti.size(); i++) 
			    {
		//	    Giocatore partecipante = partecipanti.get(i);
			    Giocatore partecipante = frame.getCampionato().getLista_gare().get(num_gara).getPartecipanti().get(i);
			    
			    Collections.sort(partecipante.getListaTempi(), new Giocatore.OrdinaTempi());
			    
			    qualifica.resetGomma(partecipante); //monta un nuovo set di gomme al pilota per la gara
			    
			    partecipante.resetGiriGrafico();
			    
			    
			   } 
			    
			    //Ordino i piloti in base al tempo minore, questa sar� l'ordine di partenza		
			    
			    Collections.sort(frame.getCampionato().getLista_gare().get(num_gara).getPartecipanti(), new Gara.TempiGiocatore());
			    
			   			
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
				
		this.cancel(true);
		if(isCancelled()){
			
		}
		else
		{
			
			try 
			{					
				frame.getJFrame().dispose();		
				GrigliaPartenza grigliaPartenza= new GrigliaPartenza(frame.getCampionato());				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void process(List<Void> chunks){ // eseguito nell'EDT, aggiorna GUI con risultati intermedi
				
	
	}
	
  



	
}
