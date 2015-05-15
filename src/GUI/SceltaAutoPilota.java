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





















import pilota.Pilota;
import auto.Auto;
import Database.Database;

public class SceltaAutoPilota {

	private JFrame frmNomeSoftware;
	private JTable tabella_auto;
	private JTable tabella_pilota;
	
	private int id_pilota = -1;
	private int id_auto = -1;
	//private String nome;
	//private String cognome;
	
	private boolean gara_singola;
	
	private int tot_giocatori = 0;
	private int tot_gioc_reali = 0;
	private int parte_sele = 1;
	
	private JButton btnConfermaSelezione = null;
	private JButton button_1 = new JButton("AVANTI -->");
	
	private JLabel lblAutomobile = new JLabel("Scegli un'automobile per il giocatore 1:");
	private JLabel lblScegliUnPilota = new JLabel("Scegli un pilota per il giocatore 1:");
	
	private ArrayList<Giocatore> partecipanti = null;  
	
	private boolean controllo = false;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SceltaAutoPilota window = new SceltaAutoPilota(true, 3 ,1);
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
	public SceltaAutoPilota(boolean gara, int tot, int reali) 
	{
		this.tot_giocatori = tot;
		this.tot_gioc_reali = reali;
		this.gara_singola = gara;
		this.partecipanti = new ArrayList<>(tot); //creo la lista dei partecipanti
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
		frmNomeSoftware.setTitle("Simulator Times Track");
		frmNomeSoftware.setBounds(100, 100, 726, 462);
		frmNomeSoftware.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNomeSoftware.getContentPane().setLayout(null);
		
		
		lblAutomobile.setBounds(37, 11, 311, 22);
		lblAutomobile.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblAutomobile.setForeground(new Color(255, 69, 0));
		frmNomeSoftware.getContentPane().add(lblAutomobile);
		
		
		lblScegliUnPilota.setBounds(37, 174, 311, 22);
		lblScegliUnPilota.setForeground(new Color(255, 69, 0));
		lblScegliUnPilota.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		frmNomeSoftware.getContentPane().add(lblScegliUnPilota);
		
		JButton button = new JButton("<-- INDIETRO");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			if(gara_singola)
				{
				//apre la finestra precedente (scelta n_piloti)
				NumeroGiocatori ng = new NumeroGiocatori(gara_singola);	
				frmNomeSoftware.dispose();
				}
				
				
			}
		});
		button.setBounds(10, 388, 176, 35);
		button.setForeground(new Color(255, 69, 0));
		button.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		frmNomeSoftware.getContentPane().add(button);
		
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				SceltaCircuito circuiti = new SceltaCircuito(gara_singola, partecipanti, tot_giocatori, tot_gioc_reali);
				frmNomeSoftware.dispose();
				
			}
		});
		
		button_1.setVisible(false);
		button_1.setEnabled(false);
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
		button_2.setBounds(196, 388, 176, 35);
		button_2.setForeground(new Color(255, 69, 0));
		button_2.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		frmNomeSoftware.getContentPane().add(button_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 44, 646, 119);
		frmNomeSoftware.getContentPane().add(scrollPane);
		
		Database db = new Database();
		db.caricadriver();
		db.collegati();
		ListTableModel model_pilota = null;
		ListTableModel model_auto = null;
		
		try {
			model_pilota = ListTableModel.createModelFromResultSet(db.getInfo_Pilota());
			model_auto =  ListTableModel.createModelFromResultSet(db.getInfoAuto());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.disconettiti();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(37, 207, 646, 119);
		frmNomeSoftware.getContentPane().add(scrollPane_1);
		
		RenderTabella renderer = new RenderTabella();
        
		
		ListenerTabellePilota listener_pilota = new ListenerTabellePilota(this);
		tabella_pilota = new JTable(model_pilota);
		tabella_pilota.setShowGrid(false);
		tabella_pilota.setBackground(new Color(102, 205, 170));				
		tabella_pilota.addMouseListener(listener_pilota);
		
		tabella_pilota.getColumnModel().getColumn(0).setResizable(false);
		tabella_pilota.setForeground(new Color(255, 69, 0));
		tabella_pilota.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		
		this.tabella_pilota.setDefaultRenderer(Object.class, renderer);
		
		scrollPane_1.setViewportView(tabella_pilota);
		
		ListenerTabelleAuto listener_auto = new ListenerTabelleAuto(this);
		tabella_auto = new JTable(model_auto);
		tabella_auto.setShowGrid(false);
		tabella_auto.setBackground(new Color(102, 205, 170));				
        tabella_auto.addMouseListener(listener_auto);
		
		tabella_auto.getColumnModel().getColumn(0).setResizable(false);
		tabella_auto.setForeground(new Color(255, 69, 0));
		tabella_auto.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		
		this.tabella_auto.setDefaultRenderer(Object.class, renderer);
		
		scrollPane.setViewportView(tabella_auto);
		
		btnConfermaSelezione = new JButton("Conferma selezione");
		btnConfermaSelezione.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				
				if(id_auto>-1&&id_pilota>-1)
				{
					
					//aggiunge il partecipante
					aggiungiPartecipante(id_pilota, id_auto);					
					
					//rimuove il pilota dalla lista dei piloti disponibili
					((ListTableModel)tabella_pilota.getModel()).removeRow(id_pilota);
					
					//resetta i valori a -1(non select)
					id_auto = -1;
					id_pilota = -1;				
					
				    //controllo l'abilitazione del pulsante conferma
					controlloConferma();
					
					//messaggio di Info
					frmInformazione("Partecipante aggiunto con successo");
					
					//Incremento il numero di giocatori selezionati
					parte_sele++;
					
					//Effettuo il controllo per il numero di partecipanti scelti
					controlloPartecipanti();
					
				
				
				
				}
				
				
			}

			
		});
		
		btnConfermaSelezione.setBounds(296, 337, 150, 23);
		btnConfermaSelezione.setEnabled(false);
		frmNomeSoftware.getContentPane().add(btnConfermaSelezione);
		
		frmNomeSoftware.setVisible(true);
		
		
	}

	private void controlloPartecipanti() 
	{
	  if(this.parte_sele>this.tot_gioc_reali)
	  {
		  btnConfermaSelezione.setVisible(false);
		  button_1.setEnabled(true);
		  button_1.setVisible(true);
		  
		  for (int i = 0; i < this.tot_giocatori - (this.parte_sele-1); i++) 
		  {
			scegliGiocCPU();
		  }
		  
		  frmInformazione("Tutti i partecipanti sono stati aggiunti!Premere AVANTI per continuare.");
	  }
	  
	  else {
		  lblAutomobile.setText("Scegli un'automobile per il giocatore "+parte_sele+":");
		  lblScegliUnPilota.setText("Scegli un pilota per il giocatore "+parte_sele+":");
	  }
	  
	
	  
					
	}
	
	//sceglie i restanti giocatori
	private void scegliGiocCPU() 
	{
				
		int max_piloti = tabella_pilota.getRowCount();
		int max_auto = tabella_auto.getRowCount();
		
		
		Random random = new Random();
		int riga_pilota = random.nextInt(max_piloti);		
		int riga_auto = random.nextInt(max_auto);
		
		aggiungiPartecipante(riga_pilota, riga_auto);
		
		//rimuove il pilota dalla lista dei piloti disponibili
		((ListTableModel)tabella_pilota.getModel()).removeRow(riga_pilota);
		
		
		
		
	}
	
	public void aggiungiPartecipante(int riga_pilota, int riga_auto)
	{
		
		  
		String nome_pilota = (String) ((ListTableModel)tabella_pilota.getModel()).getRow(riga_pilota).get(0);
		String cognome_pilota = (String) ((ListTableModel)tabella_pilota.getModel()).getRow(riga_pilota).get(1);
		
		String nome_auto = (String) ((ListTableModel)tabella_auto.getModel()).getRow(riga_auto).get(0);
		String marca_auto = (String) ((ListTableModel)tabella_auto.getModel()).getRow(riga_auto).get(1);
		
		Auto auto = new Auto();
		Pilota pilota = new Pilota();
		
		//mi connetto al database per prendere le informazioni
		Database db = new Database();
		db.caricadriver();
		db.collegati();
		db.getDati_Auto(auto, nome_auto, marca_auto);
		db.getDati_Pilota(nome_pilota, cognome_pilota, pilota);
		
		db.disconettiti();
		
		auto.setRpm();
		auto.setFd();
		
		//creo il nuovo partecipante passandogli i dati che sono stati selezionati
		Giocatore partecipante = new Giocatore(auto, pilota);
		
		if(this.parte_sele<=this.tot_gioc_reali)
			partecipante.setGioc_reale(true); //� un gioc_reale
			
		//aggiungo il giocatore alla lista dei partecipanti
		partecipanti.add(partecipante);	
	}

	public void controlloConferma()
	{
		if(id_auto>-1&&id_pilota>-1)
		{
			btnConfermaSelezione.setEnabled(true);
		}
		else
		{
			btnConfermaSelezione.setEnabled(false);
		}
	}

	public boolean isGara_singola() {
		return gara_singola;
	}

	public void setGara_singola(boolean gara_singola) {
		this.gara_singola = gara_singola;
	}

	public int getId_pilota() {
		return id_pilota;
	}

	public void setId_pilota(int id_pilota) {
		this.id_pilota = id_pilota;
	}

	public int getId_auto() {
		return id_auto;
	}

	public void setId_auto(int id_auto) {
		this.id_auto = id_auto;
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
}
