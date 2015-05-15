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

import java.awt.event.*;

public class ListenersTabella implements ActionListener {

	private Gara_GUI frame;
	private WorkerGara worker;

	public ListenersTabella(Gara_GUI frame) //costrutture principale
	{
	this.frame = frame;
	}
	
@Override
public void actionPerformed(ActionEvent e) {

	String comando = e.getActionCommand();
	if(comando == "gara") 
	{ 
	  simula_gara();	  
	}
	
}



private void simula_gara() { //Azioni che vengono eseguite nell EDT una volta che � stato premuto il tasto "Start Gara";
	
		
	this.frame.getBtn_Start().setEnabled(false);
	this.frame.getBtnPausa().setEnabled(true);
	
	worker = new WorkerGara(frame);//inizializzo il worker
	worker.execute();
	
}
			
}
	

