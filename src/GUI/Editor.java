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

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Color;

import javax.swing.JButton;

import Database.Database;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Editor {

	private JFrame frmNomeSoftware;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Editor window = new Editor();
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
	public Editor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNomeSoftware = new JFrame();
		frmNomeSoftware.setVisible(true);
		frmNomeSoftware.getContentPane().setBackground(Color.BLACK);
		frmNomeSoftware.setBackground(Color.BLACK);
		frmNomeSoftware.setResizable(false);
		frmNomeSoftware.setTitle("Simulator Times Track");
		frmNomeSoftware.setBounds(100, 100, 726, 462);
		frmNomeSoftware.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmNomeSoftware.getContentPane().setLayout(null);
		
		JButton button_4 = new JButton("<-- INDIETRO");                         //creazione bottone INDIETRO con relative azioni
		button_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) { 						//al click di INDIETRO si chiude il frame in uso e si apre il frame di EDITOR
				frmNomeSoftware.dispose();
				Home h = new Home();
			}
		});
		button_4.setForeground(new Color(255, 69, 0));
		button_4.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		button_4.setBounds(10, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(button_4);
		
		JButton btnNewButton = new JButton("AGGIUNGI AUTOMOBILE");				//creazione bottone AGGIUNGI AUTOMOBILE con relative azioni
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {							//al click di AGGIUNGI AUTOMOBILE si chiude il frame in uso e si apre il frame di AggiungiAutomobile
				frmNomeSoftware.dispose();
				AggiungiAutomobile aa = new AggiungiAutomobile();
			}
		});
		btnNewButton.setForeground(new Color(255, 69, 0));
		btnNewButton.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnNewButton.setBounds(46, 40, 261, 35);
		frmNomeSoftware.getContentPane().add(btnNewButton);
		
		JButton btnAggiungiMotore = new JButton("AGGIUNGI MOTORE"); 			//creazione bottone AGGIUNGI MOTORE con relative azioni
		btnAggiungiMotore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {							//al click di AGGIUNGI MOTORE si chiude il frame in uso e si apre il frame di AggiungiMotore
				frmNomeSoftware.dispose();
				AggiungiMotore am = new AggiungiMotore();
			}
		});
		btnAggiungiMotore.setForeground(new Color(255, 69, 0));
		btnAggiungiMotore.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnAggiungiMotore.setBounds(46, 134, 261, 35);
		frmNomeSoftware.getContentPane().add(btnAggiungiMotore);
		
		JButton btnAggiungiPneumatico = new JButton("AGGIUNGI PNEUMATICO");		//creazione bottone AGGIUNGI PNEUMATICO con relative azioni
		btnAggiungiPneumatico.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {							//al click di AGGIUNGI PNEUMATICO si chiude il frame in uso e si apre il frame di AggiungiPneumatico
				frmNomeSoftware.dispose();
				AggiungiPneumatico apn = new AggiungiPneumatico();
			}
		});
		btnAggiungiPneumatico.setForeground(new Color(255, 69, 0));
		btnAggiungiPneumatico.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnAggiungiPneumatico.setBounds(46, 231, 261, 35);
		frmNomeSoftware.getContentPane().add(btnAggiungiPneumatico);
		
		JButton btnAggiungiPilota = new JButton("AGGIUNGI PILOTA");				//creazione bottone AGGIUNGI PILOTA con relative azioni
		btnAggiungiPilota.setForeground(new Color(255, 69, 0));
		btnAggiungiPilota.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnAggiungiPilota.setBounds(374, 40, 261, 35);
		frmNomeSoftware.getContentPane().add(btnAggiungiPilota);
		btnAggiungiPilota.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {							//al click di AGGIUNGI PILOTA si chiude il frame in uso e si apre il frame di AggiungiPilota
				frmNomeSoftware.dispose();
				AggiungiPilota pilota = new AggiungiPilota();
			}
		});
		
		JButton btnAggiungiCircuito = new JButton("AGGIUNGI CIRCUITO");			//creazione bottone AGGIUNGI CIRCUITO con relative azioni
		btnAggiungiCircuito.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {							//al click di AGGIUNGI CIRCUITO si chiude il frame in uso e si apre il frame di AggiungiCircuito
				frmNomeSoftware.dispose();
				AggiungiCircuito ac = new AggiungiCircuito();
			}
		});
		btnAggiungiCircuito.setForeground(new Color(255, 69, 0));
		btnAggiungiCircuito.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnAggiungiCircuito.setBounds(374, 134, 261, 35);
		frmNomeSoftware.getContentPane().add(btnAggiungiCircuito);
		
		JButton btnNewButton_1 = new JButton("AGGIUNGI TRASMISSIONE");			//creazione bottone AGGIUNGI TRASMISSIONE con relative azioni
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmNomeSoftware.dispose();
				AggiungiTrasmissione at = new AggiungiTrasmissione();
			}
		});
		btnNewButton_1.setForeground(new Color(255, 69, 0));
		btnNewButton_1.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnNewButton_1.setBounds(374, 231, 261, 35);
		frmNomeSoftware.getContentPane().add(btnNewButton_1);
		
		JButton btnResetDatabase = new JButton("RESET DATABASE");				//creazione bottone RESET DATABASE con relative azioni
		btnResetDatabase.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frmConferma();                                                  //JOptionPane di tipo YES_OR_NO_OPTION
			}
		});
		btnResetDatabase.setForeground(new Color(255, 69, 0));
		btnResetDatabase.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnResetDatabase.setBounds(211, 310, 261, 35);
		frmNomeSoftware.getContentPane().add(btnResetDatabase);
	}
	
	
	
			                      
	void frmConferma(){                                                          //JOptionPane di tipo YES_OR_NO_OPTION  
		 
		
		
		int dialogButton =  JOptionPane.showConfirmDialog (
				 null,
				 "Sei sicuro di voler cancellare tutti i dati in memoria?",
				 "Warning",
				 JOptionPane.YES_NO_OPTION);
		 //al click di SI viene resettato il database
				 if(dialogButton==JOptionPane.YES_OPTION)
				 {                     
					 
						Database database = new Database();
						database.caricadriver();
						database.collegati();
						database.Reset_Database();
						database.disconettiti();
						
						JOptionPane.showMessageDialog(frmNomeSoftware,
							    "Database resettato",
							    "OK",
							    JOptionPane.INFORMATION_MESSAGE
							    );	
				 }
				 	
	}
}
