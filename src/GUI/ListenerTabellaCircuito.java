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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;

public class ListenerTabellaCircuito implements MouseListener {
	
	private SceltaCircuito frame;

	public ListenerTabellaCircuito(SceltaCircuito sceltaCircuito) {
		this.frame = sceltaCircuito;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		
		 JTable target = (JTable)e.getSource();
		 
	     int riga = target.getSelectedRow();	
	     
	     int id_circuito = (int) ((ListTableModel)target.getModel()).getRow(riga).get(0);
	      
	     frame.setId_circuito(id_circuito);
	     
	     frame.controlloConferma();
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public SceltaCircuito getFrame() {
		return frame;
	}

	public void setFrame(SceltaCircuito frame) {
		this.frame = frame;
	}

}
