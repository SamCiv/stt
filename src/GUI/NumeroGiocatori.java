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

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

//import javafx.scene.control.ComboBox;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

import Database.Database;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NumeroGiocatori {

	private JFrame frmNomeSoftware;
	private boolean gara; //indica se si vuole effettuare una singola gara oppure un campionato
	private int num_giocatori = 0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NumeroGiocatori window = new NumeroGiocatori(true);
					window.frmNomeSoftware.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public NumeroGiocatori(boolean gara) 
	{
		//controlla il numero di piloti nel database
		Database db = new Database();
		db.caricadriver();
		db.collegati();
		this.num_giocatori = db.Count_Pilota();
		db.disconettiti();		
		this.gara = gara;
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNomeSoftware = new JFrame();
		frmNomeSoftware.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmNomeSoftware.setVisible(true);
		frmNomeSoftware.setTitle("Simulator Times Track");
		frmNomeSoftware.getContentPane().setBackground(new Color(0, 0, 0));
		frmNomeSoftware.setResizable(false);
		frmNomeSoftware.setBounds(100, 100, 726, 462);
		frmNomeSoftware.getContentPane().setLayout(null);
		
		final JComboBox comboBox_1 = new JComboBox();		
		comboBox_1.setMaximumRowCount(10);
		comboBox_1.setBounds(369, 80, 117, 20);
		
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setMaximumRowCount(2);
		comboBox.setBounds(369, 138, 117, 20);
		
		int giocatori_max = 0;
		
		if(this.num_giocatori>=20)
			giocatori_max = 20;
		else
			giocatori_max = this.num_giocatori;
		
		for (int i = 1; i <= giocatori_max; i++) 
		{
			if(i>=2)
			comboBox_1.addItem(i);
			if(i<=2)
			comboBox.addItem(i);
		}
		
		frmNomeSoftware.getContentPane().add(comboBox_1);
		frmNomeSoftware.getContentPane().add(comboBox);
		
		
		JLabel lblNumeroGiocatori = new JLabel("INSERISCI IL NUMERO GIOCATORI :");
		lblNumeroGiocatori.setForeground(new Color(255, 69, 0));
		lblNumeroGiocatori.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblNumeroGiocatori.setBounds(72, 123, 287, 47);
		frmNomeSoftware.getContentPane().add(lblNumeroGiocatori);
		
		
		
		JButton btnNewButton = new JButton("<-- INDIETRO");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				SceltaGara tipologia = new SceltaGara();
				frmNomeSoftware.dispose();
				
				
			}
		});
		btnNewButton.setForeground(new Color(255, 69, 0));
		btnNewButton.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnNewButton.setBounds(10, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(btnNewButton);
		
		JButton button = new JButton("AVANTI -->");
		
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				int giocatori_tot = Integer.parseInt(comboBox_1.getSelectedItem().toString());
				int giocatori_reali = Integer.parseInt(comboBox.getSelectedItem().toString());
				
				if(gara)
				{
					SceltaAutoPilota sap = new SceltaAutoPilota(true, giocatori_tot, giocatori_reali);
				}
				else
				{
					SceltaAutoPilota sap = new SceltaAutoPilota(false, giocatori_tot, giocatori_reali);
				}
				
				frmNomeSoftware.dispose();
			}
		});
		
		button.setForeground(new Color(255, 69, 0));
		button.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		button.setBounds(534, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(button);
		
		JLabel lblScegliIlNumero = new JLabel("SCEGLI IL NUMERO DI PARTECIPANTI :");
		lblScegliIlNumero.setForeground(new Color(255, 69, 0));
		lblScegliIlNumero.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblScegliIlNumero.setBounds(72, 65, 287, 47);
		frmNomeSoftware.getContentPane().add(lblScegliIlNumero);
		
		
	}

	public boolean isGara() {
		return gara;
	}

	public void setGara(boolean gara) {
		this.gara = gara;
	}

	public int getNum_giocatori() {
		return num_giocatori;
	}

	public void setNum_giocatori(int num_giocatori) {
		this.num_giocatori = num_giocatori;
	}
}
