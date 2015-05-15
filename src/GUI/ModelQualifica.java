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

import java.util.concurrent.TimeUnit;

import javax.swing.table.AbstractTableModel;

import campionato.Campionato;

public class ModelQualifica extends AbstractTableModel {

	private Campionato campionato = null;
	private String[] colonne = {"Posizione", "Nome pilota", "Modello auto", "S1 [s]", "S2 [s]", "S3 [s]", "Tempo qualifica"};
	
	//costruttore principale
	public ModelQualifica(Campionato campionato) {
		
		this.campionato = campionato;		
		
	}

	@Override
	public String getColumnName(int col) {
        return colonne[col];
    }
	
	@Override
	public int getColumnCount() {
		//Numero di colonne
		return 7;
	}

	@Override
	public int getRowCount() {
		//Numero di righe
		return campionato.getPartecipanti().size();
		//return 0;
		
	}

	@Override
	public Object getValueAt(int n_riga, int n_colonna) {
		// TODO Auto-generated method stub
		if(campionato!=null)
		{
			if(n_colonna==0)
			{
				return n_riga+1;
			}
		else if(n_colonna==1){
				String nome_pilota = campionato.getLista_gare().get(campionato.getGara_attuale()).getPartecipanti().get(n_riga).getPilota().getNome();
				String cognome_pilota = campionato.getLista_gare().get(campionato.getGara_attuale()).getPartecipanti().get(n_riga).getPilota().getCognome();
				
				return nome_pilota + " " + cognome_pilota;
			}
			else if(n_colonna==2)
			{
				String marca_auto = campionato.getLista_gare().get(campionato.getGara_attuale()).getPartecipanti().get(n_riga).getAuto().getMarca();
				String nome_auto = campionato.getLista_gare().get(campionato.getGara_attuale()).getPartecipanti().get(n_riga).getAuto().getNome();
				
				return marca_auto + " " + nome_auto;
			}
			else if(n_colonna==3)
			{
				return campionato.getLista_gare().get(campionato.getGara_attuale()).getPartecipanti().get(n_riga).getListaTempi().get(0).getSettore_1();
			}
			else if(n_colonna==4)
			{
				return campionato.getLista_gare().get(campionato.getGara_attuale()).getPartecipanti().get(n_riga).getListaTempi().get(0).getSettore_2();
			}
			else if(n_colonna==5)
			{
				return campionato.getLista_gare().get(campionato.getGara_attuale()).getPartecipanti().get(n_riga).getListaTempi().get(0).getSettore_3();
			}
			else if(n_colonna==6)
			{
				return minuti_decimali(campionato.getLista_gare().get(campionato.getGara_attuale()).getPartecipanti().get(n_riga).getListaTempi().get(0).getTempo_totale());
				//return campionato.getLista_gare().get(campionato.getGara_attuale()).getPartecipanti().get(n_riga).getListaTempi().get(0).getTempo_totale();
			}
			
			
			
		}
		return null;
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
