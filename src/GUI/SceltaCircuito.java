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

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.DropMode;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

























import campionato.Campionato;
import pilota.Pilota;
import auto.Auto;
import Circuito.Circuito;
import Database.Database;

public class SceltaCircuito {

	private JFrame frmNomeSoftware;
	private JTable tabella_circuito;
	
	private int id_circuito = -1;
	private int numero_gare_campionato = 0;
	
	private Campionato campionato = new Campionato();
	
	private boolean gara_singola; //se true indica una gara singola, false indica un campionato
	
	int giocatori_totali = 0;
	int giocatori_reali = 0;
	
		
	private JButton btnConfermaSelezione = null;
	private JButton button_1 = new JButton("AVANTI -->");
	
	private final JComboBox numero_gare = new JComboBox();
	
	private ArrayList<Giocatore> partecipanti = null;  
	
/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SceltaCircuito window = new SceltaCircuito(true, null, 3 ,1);
					window.frmNomeSoftware.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SceltaCircuito(boolean gara, ArrayList<Giocatore> partecipanti, int gioc_tot, int gioc_reali) 
	{
		this.gara_singola = gara;
		this.partecipanti = partecipanti; //lista dei partecipanti alla gara o al campionato
		
		this.giocatori_reali = gioc_reali;
		this.giocatori_totali = gioc_tot;
		
//		this.partecipanti = new ArrayList<>(tot); //creo la lista dei partecipanti
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNomeSoftware = new JFrame();
		frmNomeSoftware.getContentPane().setForeground(new Color(0, 0, 0));
		frmNomeSoftware.setResizable(false);
		frmNomeSoftware.getContentPane().setBackground(new Color(0, 0, 0));
		frmNomeSoftware.setTitle("Simulator track times");
		frmNomeSoftware.setBounds(100, 100, 726, 462);
		frmNomeSoftware.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNomeSoftware.getContentPane().setLayout(null);
		
		JButton button = new JButton("<-- INDIETRO");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			//if(gara_singola)
			//	{
				SceltaAutoPilota indietro = new SceltaAutoPilota(gara_singola, giocatori_totali, giocatori_reali);
				frmNomeSoftware.dispose();
			//	}
			
				
				
			}
		});
		button.setActionCommand("indietro");
		button.setBounds(10, 388, 176, 35);
		button.setForeground(new Color(255, 69, 0));
		button.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		frmNomeSoftware.getContentPane().add(button);
		
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				campionato.setPartecipanti(partecipanti);//imposto la lista dei partecipanti
				campionato.setGioc_reali(giocatori_reali); //imposto il numero di giocatori reali
				
				RiepilogoCampionato riepilogo = new RiepilogoCampionato(campionato, gara_singola, giocatori_reali);
				frmNomeSoftware.dispose();
			}
		});
		
		button_1.setVisible(false);
		button_1.setEnabled(false);
		button_1.setActionCommand("avanti");
		button_1.setBounds(534, 388, 176, 35);
		button_1.setForeground(new Color(255, 69, 0));
		button_1.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		frmNomeSoftware.getContentPane().add(button_1);
		
		JButton button_2 = new JButton("HOME");
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				Home home = new Home();
				frmNomeSoftware.dispose();
				
			}
		});
		button_2.setActionCommand("home");
		button_2.setBounds(196, 388, 176, 35);
		button_2.setForeground(new Color(255, 69, 0));
		button_2.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		frmNomeSoftware.getContentPane().add(button_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(373, 44, 320, 282);
		frmNomeSoftware.getContentPane().add(scrollPane);
		
		Database db = new Database();
		db.caricadriver();
		db.collegati();
		
		ListTableModel model_circuito = null;
		
		try {
			model_circuito =  ListTableModel.createModelFromResultSet(db.getInfoCircuiti());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.disconettiti();
		
		RenderTabella renderer = new RenderTabella();
		
		ListenerTabellaCircuito listener_circuito = new ListenerTabellaCircuito(this);
		
		tabella_circuito = new JTable(model_circuito);
		tabella_circuito.setShowGrid(false);
		tabella_circuito.setBackground(new Color(102, 205, 170));	
		if(gara_singola) 
			tabella_circuito.addMouseListener(listener_circuito);		
		tabella_circuito.getColumnModel().getColumn(0).setResizable(false);
		tabella_circuito.setForeground(new Color(255, 69, 0));
		tabella_circuito.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		
		this.tabella_circuito.setDefaultRenderer(Object.class, renderer);
		
		scrollPane.setViewportView(tabella_circuito);
		
		btnConfermaSelezione = new JButton("Conferma scelta");
		btnConfermaSelezione.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				if(gara_singola) //caso in cui si tratta di una singola gara
				{
					
					//aggiunge il circuito
					sceltaCircuito();					
					
					//resetta i valori a -1(non select)
					id_circuito = -1;								
					
				    //controllo l'abilitazione del pulsante conferma
					controlloConferma();
					
					//messaggio di Info
					frmInformazione("Circuito aggiunto con successo!");
					
					numero_gare.setEnabled(false);
					
					//Abilito il tasto AVANTI
					
					button_1.setVisible(true);
					button_1.setEnabled(true);
					
								
				}
				else //caso in cui si tratta di un campionato
				{
					numero_gare_campionato = Integer.parseInt(numero_gare.getSelectedItem().toString());
					
					if(numero_gare_campionato>1)
					{					
					//aggiungo i circuiti al campionato
					sceltaCircuito();
					
					//resetta i valori a -1(non select)
					id_circuito = -1;								
					
				    //controllo l'abilitazione del pulsante conferma
					controlloConferma();
					
					//messaggio di Info
					frmInformazione("Circuiti aggiunti con successo! Premere AVANTI per continuare");
					
					//Disabilito la ComboBox
					numero_gare.setEnabled(false);
					
					//Abilito il tasto AVANTI
					
					button_1.setVisible(true);
					button_1.setEnabled(true);
					}
					else
					{
						frmErrorMessage("Il campionato deve essere formato da almeno due gare");
					}
				}
				
				
			}

			
		});
		
		btnConfermaSelezione.setBounds(105, 212, 150, 23);
		btnConfermaSelezione.setEnabled(false);
		btnConfermaSelezione.setVisible(false);
		frmNomeSoftware.getContentPane().add(btnConfermaSelezione);
		
		//final JComboBox numero_gare = new JComboBox();
		numero_gare.setBounds(125, 77, 102, 20);
		if(this.gara_singola){
		numero_gare.addItem(1);
		}
		else
		{
		for (int i = 1; i <= tabella_circuito.getRowCount(); i++) 
		{
			if(i>10)
				break;
			numero_gare.addItem(i);
		}
		btnConfermaSelezione.setEnabled(true);
		btnConfermaSelezione.setVisible(true);
		}
	
		frmNomeSoftware.getContentPane().add(numero_gare);
		
		
		
		
		JLabel lblTitoloTabella = new JLabel("Circuiti Disponibili");
		if(this.gara_singola){
			lblTitoloTabella.setText("Scegli il circuito dove vuoi gareggiare:");
		}
		lblTitoloTabella.setForeground(new Color(255, 69, 0));
		lblTitoloTabella.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblTitoloTabella.setBounds(373, 11, 320, 22);
		frmNomeSoftware.getContentPane().add(lblTitoloTabella);
		
		
		
		
		JLabel lblNCircuiti = new JLabel("Scegli il numero di gare da effettuare:");
		lblNCircuiti.setForeground(new Color(255, 69, 0));
		lblNCircuiti.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblNCircuiti.setBounds(26, 44, 311, 22);
		frmNomeSoftware.getContentPane().add(lblNCircuiti);
		
		frmNomeSoftware.setVisible(true);
		
		
	}


	
	

	protected void sceltaCircuito() 
	{

		if(gara_singola) //gara singola
		{
		campionato.setGara_singola(true);			
		aggiungiCircuito();		
		}
		else //caso in cui si tratta di un campionato
		{
			campionato.setGara_singola(false);
			for (int i = 1; i <= numero_gare_campionato; i++) 
			{
			scegliCircuitoCPU(); //sceglie gli id dei circuiti delle gare in modo random
			aggiungiCircuito();//prende i dati dal database per il circuito, crea la gara e lo aggiunge al campionato
			}
			
		}
		
	}
	
	//Prende le informaiozni del circuito dal database
	private void aggiungiCircuito() 
	{
		
		Circuito circuito = new Circuito();//crea un nuovo circuito
		
		Database db = new Database();
		db.caricadriver();
		db.collegati();		
		db.getDati_Circuito(circuito, id_circuito); //prende le informazioni relative al circuito dal database
		
		db.disconettiti();
		
		Gara gara = new Gara(randomTemperatura(), circuito); //crea una nuova gara
		
		this.campionato.getLista_gare().add(gara); //aggiunge la gara al campionato
	}

	//seleziona una temperatura casuale
	private int randomTemperatura()
	{
	
		Random random = new Random();
		
		int temperatura = random.nextInt(41 - 10) + 10; //numero random compreso tra 10 e 40 
		
		return temperatura;
	
	}

	//sceglie i restanti giocatori
	private void scegliCircuitoCPU() 
	{
				
		int max_circuiti = tabella_circuito.getRowCount();		
		
		Random random = new Random();
		int riga_circuito = random.nextInt(max_circuiti);
		
		int id_circuito = (int) ((ListTableModel)tabella_circuito.getModel()).getRow(riga_circuito).get(0);
		
		this.id_circuito = id_circuito;
		
		//rimuove il circuito dalla lista dei circuiti disponibili
		((ListTableModel)tabella_circuito.getModel()).removeRow(riga_circuito);
		
		
	}
	
	public boolean isGara_singola() {
		return gara_singola;
	}

	public void setGara_singola(boolean gara_singola) {
		this.gara_singola = gara_singola;
	}

	protected void frmErrorMessage(String messaggio) {
		
		JOptionPane.showMessageDialog(frmNomeSoftware,
			    messaggio,
			    "Attenzione",
			    JOptionPane.WARNING_MESSAGE
			    );		
		
	}
	
	void frmInformazione(String messaggio){
		JOptionPane.showMessageDialog(frmNomeSoftware,
			    messaggio,
			    "Info",
			    JOptionPane.INFORMATION_MESSAGE
			    );		
				
	}

	public JButton getBtnConfermaSelezione() {
		return btnConfermaSelezione;
	}

	public void setId_circuito(int id_circuito) {
		
		this.id_circuito = id_circuito;
		
	}

	public void controlloConferma() {

     if(this.id_circuito!=-1) //controllo se � stato selezionato un circuito
     {
    	 if(this.gara_singola&&campionato.getLista_gare().size()==0)
    	 {
    	 btnConfermaSelezione.setVisible(true);
    	 btnConfermaSelezione.setEnabled(true);
    	 }
     }
     else
     {
    	 btnConfermaSelezione.setVisible(false);
    	 btnConfermaSelezione.setEnabled(false);
     }
		
	}
}
