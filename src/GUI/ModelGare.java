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

public class ModelGare extends AbstractTableModel{
	
	private Campionato campionato = null;
	private String[] colonne = {"Numero gara", "Nome Circuito", "Nazione"};

	public ModelGare(Campionato campionato){
		this.campionato = campionato;
	}
	
	@Override
	public String getColumnName(int col) 
	{
        return colonne[col];
    }
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return campionato.getLista_gare().size();
	//	return 0;
	}

	@Override
	public Object getValueAt(int n_riga, int n_colonna) {
		
		if(campionato.getLista_gare()!=null){
			
			if(n_colonna==0){
							
				return n_riga+1;
			}
			else if(n_colonna==1)
			{
				return campionato.getLista_gare().get(n_riga).get_Circuito().getNome();				
			}
			else if(n_colonna==2){
				return campionato.getLista_gare().get(n_riga).get_Circuito().getNazione();	
			}
		}
		return null;
	}

}
