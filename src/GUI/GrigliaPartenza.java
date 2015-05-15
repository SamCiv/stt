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

public class GrigliaPartenza {

	private JFrame frmNomeSoftware;
	private JTable tabella_partecipanti;// = new JTable(model_partecipanti);
	private ModelQualifica model_partecipanti;// = new ModelQualifica(campionato); //TableModel per la tabella dei partecipanti
	
	
	
	private Campionato campionato = null; //contiene le gare da effetuare (compresi di circuiti) e la lista dei partecipanti
	//private TabellaTempi tab = null;
	
	private boolean gara_singola = false;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GrigliaPartenza window = new GrigliaPartenza(null);
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
	public GrigliaPartenza(Campionato campionato) 
	{
		
		this.campionato = campionato;//import campionato
		tabella_partecipanti = new JTable(model_partecipanti);
		model_partecipanti = new ModelQualifica(campionato);
		
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
		
		
		
		JLabel lblNomeCircuito = new JLabel("Risultati qualifica");
		lblNomeCircuito.setBounds(278, 60, 139, 22);
		lblNomeCircuito.setForeground(new Color(255, 69, 0));
		lblNomeCircuito.setFont(new Font("Trebuchet MS", Font.PLAIN, 18));
		frmNomeSoftware.getContentPane().add(lblNomeCircuito);
		
		JButton button_1 = new JButton("VAI ALLA GARA -->");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				Gara_GUI gara = new Gara_GUI(campionato);
				frmNomeSoftware.dispose();
				//tab = new TabellaTempi(campionato.getLista_gare().get(campionato.getGara_attuale()));
				
			}
		});
		
		
		button_1.setBounds(518, 388, 192, 35);
		button_1.setForeground(new Color(255, 69, 0));
		button_1.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		frmNomeSoftware.getContentPane().add(button_1);
		
		JButton button_2 = new JButton("HOME");
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				frmAttenzione();
				
			}
		});
		button_2.setBounds(10, 388, 176, 35);
		button_2.setForeground(new Color(255, 69, 0));
		button_2.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		frmNomeSoftware.getContentPane().add(button_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(46, 93, 632, 235);
		frmNomeSoftware.getContentPane().add(scrollPane);
		
		RenderTabella renderer = new RenderTabella();
		
		//Tabella partecipanti
		tabella_partecipanti = new JTable(model_partecipanti);
		tabella_partecipanti.setShowGrid(false);
		tabella_partecipanti.setBackground(new Color(102, 205, 170));				
   		
		tabella_partecipanti.getColumnModel().getColumn(0).setResizable(false);
		tabella_partecipanti.setForeground(new Color(255, 69, 0));
		tabella_partecipanti.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		scrollPane.setViewportView(tabella_partecipanti);
		
		this.tabella_partecipanti.setDefaultRenderer(Object.class, renderer);
		
		JLabel lblGriglia = new JLabel("Griglia di partenza " + campionato.getLista_gare().get(campionato.getGara_attuale()).get_Circuito().getNome());		
		lblGriglia.setForeground(new Color(255, 69, 0));
		lblGriglia.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		lblGriglia.setBounds(234, 11, 374, 22);
		frmNomeSoftware.getContentPane().add(lblGriglia);
		
		frmNomeSoftware.setVisible(true);
		
		
	}

	

	
	private void frmAttenzione(){
		
		if (JOptionPane.showConfirmDialog(frmNomeSoftware, "Tornando alla Home verranno persi tutti i dati. Sei sicuro di procedere?", "ATTENZIONE",
		        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) 
		{
			Home home = new Home();
			frmNomeSoftware.dispose();
		} else 
		{
		    // no option
		}
				
	}

	public boolean isGara_singola() {
		return gara_singola;
	}

	public void setGara_singola(boolean gara_singola) {
		this.gara_singola = gara_singola;
	}

	

	public Campionato getCampionato() {
		return campionato;
	}

	public void setCampionato(Campionato campionato) {
		this.campionato = campionato;
	}
}
