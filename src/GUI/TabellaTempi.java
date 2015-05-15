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

import giocatore.Giocatore;





import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class TabellaTempi extends AbstractTableModel {
	
	//ArrayList che contiene i listeners della tabella
	//private ArrayList<TableModelListener> listeners = new ArrayList<TableModelListener>();
	
	private String[] colonne = 	{"Posizione", "Pilota", "GAP", "Intervallo", "S1", "S2", "S3", "Ultimo tempo", "PitStop"};//, "Giri"};
	
	private ArrayList<Giocatore> partecipanti ;
	private boolean[] settori_abilitati=new boolean[3]; 
	
	private int giro = 0;
	private int settore = 0;
	private int giocatore_selezionato;
	private int giocatori_visualizzati;
	
	
	boolean flag = false;
	boolean reset_settori = false;
	
//	public void addTableModelListener(TableModelListener l)
//	 {
//	   if(listeners.contains(l))
//		            return;
//	   listeners.add(l);
//     }
	
	public ArrayList<Giocatore> getPartecipanti(){
		return this.partecipanti;
	}
	
	public TabellaTempi(ArrayList<Giocatore> partecipanti)
	{
		this.partecipanti = partecipanti;
	}
		
	
	public String getColumnName(int column) {
        return colonne[column];
    }
	
	
	
	@Override
	public int getColumnCount() {
		
		return 9; //numero di colonne
	}

	@Override
	public int getRowCount() {
		
		return this.partecipanti.size();
	}

	@Override
	public Object getValueAt(int n_riga, int n_colonna) {
		
		resetSettoriAbilitati(); //inizializzo tutti i valori a false
		
		inizializzazionePermessi(n_riga);
		
		if(n_colonna==0) return partecipanti.get(n_riga).getPosizione_griglia();
		
		else if(n_colonna==1) return (partecipanti.get(n_riga).getPilota().getCognome());
		
		else if(flag&&(n_riga<=this.giocatori_visualizzati)&&n_colonna!=7&&n_colonna!=8) //vengono mostrati solamente i piloti parziali
		{						

			
			if(n_colonna==2) //colonna dei GAP, devo andar a far visualizzare il GAP corrispondente, a seconda del settore in cui mi trovo
			{
				if(n_riga==0) return this.giro+"/"+(this.partecipanti.get(n_riga).getListaTempi().size()-2)+" Lap"; // � il primo pilota quindi scrivo il giro 
				if(this.settore==0)
				{
					if(settori_abilitati[0]){
						if(approssima_numero(partecipanti.get(n_riga).getGap(), 3)>=60)							
							return minuti_decimali(approssima_numero(partecipanti.get(n_riga).getGap(), 3));
						else
							return approssima_numero(partecipanti.get(n_riga).getGap(), 3);
					}					
					else
						return null;
				}
				else if(this.settore==1)
				{
					if(settori_abilitati[1])
					{
					if(approssima_numero(partecipanti.get(n_riga).getGap(), 3)>=60)
						return minuti_decimali(approssima_numero(partecipanti.get(n_riga).getGap(), 3));
					else
						return approssima_numero(partecipanti.get(n_riga).getGap(), 3);
					}
					  
					else
					{
						if(approssima_numero(partecipanti.get(n_riga).getGap_precedente(), 3)>=60)
							return minuti_decimali(approssima_numero(partecipanti.get(n_riga).getGap_precedente(), 3));
						else
							return approssima_numero(partecipanti.get(n_riga).getGap_precedente(), 3);
					}
						
				}					
				else if(this.settore==2)
				{
					if(settori_abilitati[2])
					{
						if(approssima_numero(partecipanti.get(n_riga).getGap(), 3)>=60)
						return minuti_decimali(approssima_numero(partecipanti.get(n_riga).getGap(), 3));
					else
						return approssima_numero(partecipanti.get(n_riga).getGap(), 3);//return partecipanti.get(n_riga).getListaTempi().get(giro).getGap_3();
					}
						
					else
					{
					if(approssima_numero(partecipanti.get(n_riga).getGap_precedente(), 3)>=60)
						return minuti_decimali(approssima_numero(partecipanti.get(n_riga).getGap_precedente(), 3));
					else
						return approssima_numero(partecipanti.get(n_riga).getGap_precedente(), 3);
					}
						
				}
					
			}
			
			else if(n_colonna==3) //colonna dei GAP, devo andar a far visualizzare il GAP corrispondente, a seconda del settore in cui mi trovo
			{
				if(this.settore==0)
				{
					if(settori_abilitati[0])
					return approssima_numero(partecipanti.get(n_riga).getListaTempi().get(giro).getGap_1(), 3);
					else return null;
				}
				else if(this.settore==1)
				{
					if(settori_abilitati[1])
					    return approssima_numero(partecipanti.get(n_riga).getListaTempi().get(giro).getGap_2(), 3);
					else
						return approssima_numero(partecipanti.get(n_riga).getListaTempi().get(giro).getGap_1(), 3);
				}					
				else if(this.settore==2)
				{
					if(settori_abilitati[2])
						return approssima_numero(partecipanti.get(n_riga).getListaTempi().get(giro).getGap_3(), 3);
					else
						return approssima_numero(partecipanti.get(n_riga).getListaTempi().get(giro).getGap_2(), 3);
				}
					
			}
			
			
			
			
			else if(n_colonna==4&&this.settore>=0&&settori_abilitati[0]) return partecipanti.get(n_riga).getListaTempi().get(giro).getSettore_1();
			
			else if(n_colonna==5&&this.settore>=1&&settori_abilitati[1])
			{
				if(this.reset_settori) return 0;
				else
				{
					
				return partecipanti.get(n_riga).getListaTempi().get(giro).getSettore_2();	
				
				}
				
			}
			
			else if(n_colonna==6&&this.settore==2&&settori_abilitati[2]){
				if(this.reset_settori) return 0;
				else
				{
					
					return partecipanti.get(n_riga).getListaTempi().get(giro).getSettore_3();
					
				}
				
			}
			
		
		}
		else if(n_colonna==7)
		{
			if(settori_abilitati[2])
			//return partecipanti.get(n_riga).getListaTempi().get(giro).getTempo_totale();
			return minuti_decimali(partecipanti.get(n_riga).getListaTempi().get(giro).getTempo_totale());
			else if(giro>0)
				//return partecipanti.get(n_riga).getListaTempi().get(giro-1).getTempo_totale();
				return minuti_decimali(partecipanti.get(n_riga).getListaTempi().get(giro-1).getTempo_totale());
			else
				return null;
		}
		
		else if(n_colonna==8) 
			return partecipanti.get(n_riga).getListaTempi().get(giro).getPit_stop();
		
		
			return null;
	}
	
	
	private void inizializzazionePermessi(int n_riga) {
		
		if(n_riga<=giocatore_selezionato) //se il giocatore che voglio visualizzare � uguale al giocatore_selezionato(ovvero quello con l'ultimo settore aggiornato) abilito a true fino all'all'ultimo settore in cui � arrivato
		{
			for (int i = 0; i <= this.settore; i++) 
			{
				this.settori_abilitati[i]=true;
			}
		}
		else
		{
			for (int i = 0; i < this.settore; i++) 
			{
				this.settori_abilitati[i]=true;
			}
		}
		
	}

	private void resetSettoriAbilitati() //inizializzo tutti i settori abiitati a false
	{	
		
		for (int i = 0; i < this.settori_abilitati.length; i++) 
		{
			settori_abilitati[i]=false;
		}
		
	}

	public void incrementaGiro(){
		this.giro++;
	}
	
	public int getGiro(){
		return this.giro;
	}
	
	public void setFlag(boolean flag){
		this.flag = flag;
	}
	
	public boolean getFlag(){
		return this.flag;
	}

	public int getSettore() {
		return settore;
	}
	
	public boolean getResetSettori(){
		return this.reset_settori;
	}

	public void setResetSettori(boolean flag){
		this.reset_settori = flag;
	}

	public void setSettore(int settore) {
		this.settore = settore;
	}

	public int getGiocatore_selezionato() {
		return giocatore_selezionato;
	}

	public void setGiocatore_selezionato(int giocatore_selezionato) {
		
		this.giocatore_selezionato = giocatore_selezionato; //imposto il giocatore a cui devo aggiornare il tempo
		
		//imposto il numero di giocatori precedenti che devo visualizare
		if(giocatore_selezionato==0&&this.settore==0) //devo visualizzare solo il pilota in testa
		this.giocatori_visualizzati = giocatore_selezionato;
		else// if(giocatore_selezionato==0&&this.settore>0) //devo visualizzare il tempo degli altri piloti, solo nei settori precedenti
		this.giocatori_visualizzati = this.partecipanti.size()-1;
		//else
		//	this.giocatori_visualizzati = giocatore_selezionato; //i giocatori visualizzati sono tutti quelli compresi tra il primo e il giocatore_selezionato
			
	}

	public int getGiocatori_visualizzati() {
		return giocatori_visualizzati;
	}

	public void setGiocatori_visualizzati(int giocatori_visualizzati) {
		this.giocatori_visualizzati = giocatori_visualizzati;
	}
	
	private double approssima_numero(double numero, double n){
		double temp = Math.pow(10, n);
		double valore = Math.round(numero*temp)/temp;
		return valore;
	}
	
	private String minuti_decimali(double tempo_totale){
		
		tempo_totale *= 1000;
				
		long totale = (long) tempo_totale;
		
		String tempo = String.format("%d:%d.%d", 
			    TimeUnit.MILLISECONDS.toMinutes(totale),
			    TimeUnit.MILLISECONDS.toSeconds(totale) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totale)),
			    (totale - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(totale)))
			);
		
		return tempo;
	}

}
