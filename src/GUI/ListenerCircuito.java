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

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import Circuito.Importa_Circuito;
import Database.Database;

public class ListenerCircuito implements ActionListener {

	private AggiungiCircuito frame;
	private WorkerCircuito worker;

	public ListenerCircuito(AggiungiCircuito frame) //costrutture principale
	{
	this.frame = frame;
	}
	
@Override
public void actionPerformed(ActionEvent e) {

	String comando = e.getActionCommand();
	if(comando == "conferma") 
	{ 
	  aggiungi_circuito();	  
	}
	else if(comando == "sfoglia")
	{
		sfoglia();
	}
	else if(comando == "indietro")
	{
		indietro();
	}
	else if(comando == "home")
		home();
	
}



private void home() 
{
	Home h = new Home();
	this.frame.getFrame().dispose();
}

private void indietro() 
{
	 //al click di INDIETRO si chiude il frame in uso e si apre il frame di EDITOR
		Editor e = new Editor();
		this.frame.getFrame().dispose();
}

private void sfoglia() {
	
	JFileChooser fc = new JFileChooser ();					   								//creazione oggetto di tipo FILECHOOSER
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Tracks", "trk");          //filtro - visualizzazione dei soli file con estensione .trk
    fc.setFileFilter(filter);																//applicazione filtro
	
	int response = fc.showOpenDialog(null);
	
	if(response==JFileChooser.APPROVE_OPTION)                                               //all'approvazione della scelta del file viene visualizzata all'interno di una textfield la directory del file scelto
	{
	  this.frame.setString(fc.getSelectedFile().getPath()); 	  
	}
	
}



private void aggiungi_circuito() { //Azioni che vengono eseguite nell EDT una volta che � stato premuto il tasto "Start Gara";
	
		
	//Connessione al database
	
	Database database = new Database();
	database.caricadriver();
	database.collegati();
	
	boolean controllo = database.controllo_NomeCircuito(this.frame.getNome().getText(), this.frame.getNazione().getText());  //controllo se non esiste un altro circuito con lo stesso nome
	boolean controllo2 = this.frame.controllo_textField();   																 //controllo se i textfield sono vuoti
	
	database.disconettiti();
	
	if(controllo2){
		
		if(controllo)
		{
			this.frame.getConferma().setEnabled(false);
			worker = new WorkerCircuito(frame);//inizializzo il worker
			worker.execute();	
		}
	else
		
		this.frame.frmErrore();        //JOptionPane di errore
	
	
	
}
			
}
	

}