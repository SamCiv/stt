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

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import campionato.Campionato;

public class RiepilogoCampionato {

	private JFrame frmNomeSoftware;
	private JTable tabella_partecipanti = null;
	private JTable tabella_gare = null;
	
	private JButton btnConfermaSelezione = null;	
	private JButton button_1 = null;
	private JButton button_2 = null;

	private int gioc_reali = 0;
	
	private Campionato campionato = null; //contiene le gare da effetuare (compresi di circuiti) e la lista dei partecipanti
	
	private boolean gara_singola = false;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RiepilogoCampionato window = new RiepilogoCampionato(null, true, 1);
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
	public RiepilogoCampionato(Campionato campionato, boolean gara_singola, int gioc_reali) 
	{
		
		this.campionato = campionato;//import campionato
		this.gara_singola = gara_singola;//controllo gara_singola
		this.setGioc_reali(gioc_reali);
		
		initialize();
	}
	
	public RiepilogoCampionato(Campionato campionato)
	{
		this.campionato = campionato;
		this.gara_singola = false;
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		boolean terminato = this.campionato.getGara_attuale()>=this.campionato.getLista_gare().size(); // Ho finito il campionato o la gara;
		
		ListenersRiepilogo listener_generale = new ListenersRiepilogo(this);
		
		RenderTabella renderer = new RenderTabella();
		
		
		frmNomeSoftware = new JFrame();
		frmNomeSoftware.getContentPane().setForeground(new Color(0, 0, 0));
		frmNomeSoftware.setResizable(false);
		frmNomeSoftware.getContentPane().setBackground(new Color(0, 0, 0));
		frmNomeSoftware.setTitle("Simulator Times Track");
		frmNomeSoftware.setBounds(100, 100, 726, 462);
		frmNomeSoftware.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNomeSoftware.getContentPane().setLayout(null);
		
		ModelPartecipanti model_partecipanti = new ModelPartecipanti(campionato); //TableModel per la tabella dei partecipanti
		ModelGare model_gare = new ModelGare(campionato); //TableModel per la tabella delle gare
		
		JLabel lblGare = new JLabel("Lista gare:");
		if(gara_singola)
			lblGare.setText("Gara: ");
		lblGare.setBounds(387, 58, 101, 22);
		lblGare.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblGare.setForeground(new Color(255, 69, 0));
		frmNomeSoftware.getContentPane().add(lblGare);
		
		
		JLabel lblPartecipanti = new JLabel("Lista partecipanti:");
		lblPartecipanti.setBounds(37, 58, 149, 22);
		lblPartecipanti.setForeground(new Color(255, 69, 0));
		lblPartecipanti.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		frmNomeSoftware.getContentPane().add(lblPartecipanti);
		
		
		if(!terminato){
		button_1 = new JButton("RISULTATI QUALIFICA-->");
		button_1.setActionCommand("avanti");
		button_1.setBounds(480, 388, 230, 35);
		button_1.setForeground(new Color(255, 69, 0));
		button_1.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		button_1.addActionListener(listener_generale);
		frmNomeSoftware.getContentPane().add(button_1);
		}
		
		button_2 = new JButton("HOME");
		button_2.setActionCommand("home");
		button_2.setBounds(282, 388, 176, 35);
		button_2.setForeground(new Color(255, 69, 0));
		button_2.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		button_2.addActionListener(listener_generale);
		frmNomeSoftware.getContentPane().add(button_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 91, 311, 205);
		frmNomeSoftware.getContentPane().add(scrollPane);
		
			
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(387, 91, 311, 205);
		frmNomeSoftware.getContentPane().add(scrollPane_1);
		
		//Tabella partecipanti
		tabella_partecipanti = new JTable(model_partecipanti);
		tabella_partecipanti.setShowGrid(false);
		tabella_partecipanti.setBackground(new Color(102, 205, 170));
		tabella_partecipanti.getColumnModel().getColumn(0).setResizable(false);
		tabella_partecipanti.setForeground(new Color(255, 69, 0));
		tabella_partecipanti.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		
		tabella_partecipanti.setDefaultRenderer(Object.class, renderer);
		
		//Tabella Gare
		tabella_gare = new JTable(model_gare);
		tabella_gare.setShowGrid(false);
		tabella_gare.setBackground(new Color(102, 205, 170));				
   		
		tabella_gare.getColumnModel().getColumn(0).setResizable(false);
		tabella_gare.setForeground(new Color(255, 69, 0));
		tabella_gare.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		
		tabella_gare.setDefaultRenderer(Object.class, renderer);
			
		scrollPane_1.setViewportView(tabella_gare);
		scrollPane.setViewportView(tabella_partecipanti);
		
		
		JLabel lblRiepilogoCampionato = new JLabel("Riepilogo Campionato");
		if(gara_singola) lblRiepilogoCampionato.setText("Riepilogo gara");
		lblRiepilogoCampionato.setForeground(new Color(255, 69, 0));
		lblRiepilogoCampionato.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		lblRiepilogoCampionato.setBounds(262, 11, 206, 22);
		frmNomeSoftware.getContentPane().add(lblRiepilogoCampionato);
		
		if(!terminato)
		{
		JLabel lblProssimoEvento = new JLabel("Prossimo evento: ");
		lblProssimoEvento.setForeground(new Color(255, 69, 0));
		lblProssimoEvento.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblProssimoEvento.setBounds(205, 321, 143, 22);
		frmNomeSoftware.getContentPane().add(lblProssimoEvento);
		}
		
		JLabel lblEvento = new JLabel("");
		if(terminato) //La gara/campionato
		{
			lblEvento.setBounds(151, 322, 559, 22);
			lblEvento.setForeground(Color.GREEN);
			
			if(this.campionato.getLista_gare().size()==1)
				lblEvento.setText("Fine gara: il vincitore � "+ this.campionato.getPartecipanti().get(0).getPilota().getNome()+ " " + this.campionato.getPartecipanti().get(0).getPilota().getCognome());
			else
				lblEvento.setText("Fine campionato: il vincitore � "+ this.campionato.getPartecipanti().get(0).getPilota().getNome()+ " " + this.campionato.getPartecipanti().get(0).getPilota().getCognome());
		}
		else
		{
			lblEvento.setText("Qualifica "+this.campionato.getLista_gare().get(this.campionato.getGara_attuale()).get_Circuito().getNome());
			lblEvento.setBounds(345, 321, 559, 22);
			lblEvento.setForeground(Color.RED);
		}
			
		
		lblEvento.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		
		
		frmNomeSoftware.getContentPane().add(lblEvento);
		
		frmNomeSoftware.setVisible(true);
		
		
	}

	

	
	void frmInformazione(String messaggio){
		JOptionPane.showMessageDialog(frmNomeSoftware,
			    messaggio,
			    "Info",
			    JOptionPane.INFORMATION_MESSAGE
			    );		
				
	}

	public boolean isGara_singola() {
		return gara_singola;
	}

	public void setGara_singola(boolean gara_singola) {
		this.gara_singola = gara_singola;
	}

	public JButton getBtnConfermaSelezione() {
		return btnConfermaSelezione;
	}
	
	public JButton getBtnQualifica() {
		return button_1;
	}
	
	public JButton getBtnHome() {
		return button_2;
	}

	public Campionato getCampionato() {
		return campionato;
	}

	public void setCampionato(Campionato campionato) {
		this.campionato = campionato;
	}
	
	public JFrame getJFrame(){
		return frmNomeSoftware;
	}

	public int getGioc_reali() {
		return gioc_reali;
	}

	public void setGioc_reali(int gioc_reali) {
		this.gioc_reali = gioc_reali;
	}
	
	
}
