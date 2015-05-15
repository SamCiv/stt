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

import javax.swing.table.AbstractTableModel;

import campionato.Campionato;

public class ModelPartecipanti extends AbstractTableModel {

	private Campionato campionato = null;
	private String[] colonne = {"Nome pilota", "Modello auto", "Punteggio"};
	
	//costruttore principale
	public ModelPartecipanti(Campionato campionato) {
		
		this.campionato = campionato;		
		
	}

	@Override
	public String getColumnName(int col) {
        return colonne[col];
    }
	
	@Override
	public int getColumnCount() {
		//Numero di colonne
		return 3;
	}

	@Override
	public int getRowCount() {
		//Numero di righe
		return campionato.getPartecipanti().size();
	//	return 0;
		
	}

	@Override
	public Object getValueAt(int n_riga, int n_colonna) {
		// TODO Auto-generated method stub
		if(campionato!=null)
		{
			if(n_colonna==0){
				String nome_pilota = campionato.getPartecipanti().get(n_riga).getPilota().getNome();
				String cognome_pilota = campionato.getPartecipanti().get(n_riga).getPilota().getCognome();
				
				return nome_pilota + " " + cognome_pilota;
			}
			else if(n_colonna==1)
			{
				String marca_auto = campionato.getPartecipanti().get(n_riga).getAuto().getMarca();
				String nome_auto = campionato.getPartecipanti().get(n_riga).getAuto().getNome();
				
				return marca_auto + " " + nome_auto;
			}
			
			else if(n_colonna==2)
			{
				String punti = "0";
				
				punti =  Integer.toString(campionato.getPartecipanti().get(n_riga).getPunteggio());
				
				return punti;
			}
		
			
			
		}
		return null;
	}

}
