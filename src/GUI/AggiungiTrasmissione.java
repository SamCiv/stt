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
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import Database.Database;
import auto.Trasmissione;



import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class AggiungiTrasmissione {

	private JFrame frmNomeSoftware;
	private JTextField textFieldRapportoFinale;
	private int n_rapporti;
	private double rapporto_finale;
	private JTextField textFieldRapporto;
	private int contatore = 1;
	private Trasmissione trasmissione = new Trasmissione();
	private JTextField textField_Nome;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AggiungiTrasmissione window = new AggiungiTrasmissione();
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
	public AggiungiTrasmissione() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmNomeSoftware = new JFrame();  //creazione frame
		frmNomeSoftware.setVisible(true);
		frmNomeSoftware.setTitle("Simulator Times Track");
		frmNomeSoftware.setResizable(false);
		frmNomeSoftware.getContentPane().setBackground(new Color(0, 0, 0));
		frmNomeSoftware.setBounds(100, 100, 726, 462);
		frmNomeSoftware.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmNomeSoftware.getContentPane().setLayout(null);
		
		JButton button = new JButton("<-- INDIETRO");            //creazione bottone INDIETRO con relative azioni
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {          //al click di INDIETRO si chiude il frame in uso e si apre il frame di EDITOR
				frmNomeSoftware.dispose();
				Editor e = new Editor();
			}
		});
		
		button.setForeground(new Color(255, 69, 0));
		button.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		button.setBounds(10, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(button);
		
		JButton button_1 = new JButton("HOME");                 //creazione bottone HOME con relative azioni
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {         //al click di HOME si chiude il frame in uso e si apre il frame di HOME
				frmNomeSoftware.dispose();
				Home h = new Home();
			}
		});
		
		button_1.setForeground(new Color(255, 69, 0));
		button_1.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		button_1.setBounds(196, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(button_1);
		
		final JButton btnConferma_1 = new JButton("CONFERMA");
		btnConferma_1.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) 				//al click di CONFERMA inserisce i rapporti_trasmissione nel database
			{
				
				//accesso al database
				
				Database database = new Database();
				database.caricadriver();
				database.collegati();
				
				database.setDati_RapportiTrasmissione(trasmissione); //creazione dei Rapporti Trasmissione all'interno del database
				
				frmConferma();                                       //JOptionPane di conferma
				
			}
		});
		
		btnConferma_1.setEnabled(false);
		btnConferma_1.setVisible(false);
		btnConferma_1.setForeground(new Color(255, 69, 0));
		btnConferma_1.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnConferma_1.setBounds(534, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(btnConferma_1);
		
		//creazione dei vari label
		
		JLabel lblInserisciIDati = new JLabel("INSERISCI I DATI DELLE TRASMISSIONI:");
		lblInserisciIDati.setForeground(new Color(255, 69, 0));
		lblInserisciIDati.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblInserisciIDati.setBounds(10, 11, 619, 29);
		frmNomeSoftware.getContentPane().add(lblInserisciIDati);
		
		JLabel lblNumeroRapporti = new JLabel("Numero rapporti");
		lblNumeroRapporti.setForeground(new Color(255, 69, 0));
		lblNumeroRapporti.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblNumeroRapporti.setBounds(209, 51, 101, 29);
		frmNomeSoftware.getContentPane().add(lblNumeroRapporti);
		
		JLabel lblRapportoFinale = new JLabel("Rapporto finale");
		lblRapportoFinale.setForeground(new Color(255, 69, 0));
		lblRapportoFinale.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblRapportoFinale.setBounds(382, 51, 101, 29);
		frmNomeSoftware.getContentPane().add(lblRapportoFinale);
		
		final JLabel labelRapporti = new JLabel("");
		labelRapporti.setForeground(new Color(255, 69, 0));
		labelRapporti.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		labelRapporti.setBounds(50, 122, 619, 29);
		frmNomeSoftware.getContentPane().add(labelRapporti);
		
		final JLabel lblRapporto = new JLabel("");
		lblRapporto.setForeground(new Color(255, 69, 0));
		lblRapporto.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblRapporto.setBounds(10, 202, 101, 29);
		frmNomeSoftware.getContentPane().add(lblRapporto);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setForeground(new Color(255, 69, 0));
		lblNome.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblNome.setBounds(10, 51, 37, 29);
		frmNomeSoftware.getContentPane().add(lblNome);
		
		//creazione combobox
				
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"3", "4", "5", "6", "7", "8"}));
		comboBox.setBounds(325, 51, 37, 29);
		frmNomeSoftware.getContentPane().add(comboBox);
		
		//creazione varie textfield
		
		textFieldRapportoFinale = new JTextField();
		textFieldRapportoFinale.setBounds(498, 51, 60, 29);
		textFieldRapportoFinale.addKeyListener(new KeyAdapterNumbersOnly());   //aggiunta KeyAdapter per il text field (numeri)
		frmNomeSoftware.getContentPane().add(textFieldRapportoFinale);
		textFieldRapportoFinale.setColumns(10);
		
		textFieldRapporto = new JTextField();
		textFieldRapporto.setBorder(null);
		textFieldRapporto.setBackground(Color.BLACK);
		textFieldRapporto.setEditable(false);
		textFieldRapporto.setColumns(10);
		textFieldRapporto.setBounds(121, 202, 251, 29);
		textFieldRapporto.addKeyListener(new KeyAdapterNumbersOnly());         //aggiunta KeyAdapter per il text field (numeri)
		frmNomeSoftware.getContentPane().add(textFieldRapporto);
		
		textField_Nome = new JTextField();
		textField_Nome.setColumns(10);
		textField_Nome.setBounds(57, 51, 142, 29);
		frmNomeSoftware.getContentPane().add(textField_Nome);
		
		
		final JButton button_2 = new JButton("Conferma valore\r\n");    									 				//creazione bottone CONFERMA VALORE con relative azioni
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0)                    									 				//settaggio azione click CONFERMA VALORE
			{
				if(controllo_campo_vuoto(textFieldRapporto))			 									 				//controllo se la textfield � vuota
				{
					if(contatore<=trasmissione.getN_rapporti())          									 				//controllo che permette di inserire tanti rapporti trasimissione quanti sono gli n rapporti
					{
						boolean controllo = false;
						if(contatore>1)
						controllo = trasmissione.getRiduzione(contatore-1)>Double.parseDouble(textFieldRapporto.getText());
						else controllo = true;
						
						if(controllo)
						{
							trasmissione.setRapporti(contatore, Double.parseDouble(textFieldRapporto.getText())); 				//passaggio dei valori inseriti in rapporti trasmissione all'oggetto trasmissione  
							contatore++;																						//incremento contatore
							labelRapporti.setText("INSERISCI IL VALORE DEL "+contatore+"�"+" RAPPORTO");                        
							textFieldRapporto.setText("");
							
							
							
							if(contatore>trasmissione.getN_rapporti())															//controllo che permette di non inserire pi� rapporti trasmissione di  quanti sono gli n rapporti
							{
								
								//settaggi grafici
								
								button_2.setEnabled(false);
								textFieldRapporto.setEnabled(false);
								labelRapporti.setText("Tutti i valori sono stati immessi, premere CONFERMA per continuare");					
								btnConferma_1.setEnabled(true);
								btnConferma_1.setVisible(true);
								
							}
							else
								frmInfoPunto("Valore memorizzato correttamente. Inserisci il valore del "+contatore+"�"+" rapporto.");
						}
						else 
						  frmErroreRid();
					}
				}
			}
		});
		button_2.setEnabled(false);
		button_2.setVisible(false);
		button_2.setBounds(394, 208, 142, 23);
		frmNomeSoftware.getContentPane().add(button_2);
		
		final JButton btnConferma = new JButton("Conferma valori\r\n");                                           //creazione bottone CONFERMA VALORI con relative azioni
		btnConferma.addMouseListener(new MouseAdapter() 
		{
			
			public void mouseClicked(MouseEvent arg0) {															  //settaggio azione click CONFERMA VALORI
				
				if(controllo_campo_vuoto(textFieldRapportoFinale)&&controllo_campo_vuoto(textField_Nome))		  //controllo se le textfield sono vuote
				{
				
				//accesso al database
					
				Database database = new Database();
				database.caricadriver();
				database.collegati();
				
				//conversione da String a Double/Integer del valori inseriti nelle textfield e settaggio di tali valori nell'oggetto trasmissione
				
				String nome = textField_Nome.getText();
				trasmissione.setNome(nome);
				
				n_rapporti = Integer.parseInt((String) comboBox.getSelectedItem());
				trasmissione.setN_rapporti(n_rapporti);
				
				rapporto_finale = Double.parseDouble(textFieldRapportoFinale.getText());
				trasmissione.setRapporto_finale(rapporto_finale);
				
				database.setDati_Trasmissione(trasmissione);                                 //creazione della trasmissione all'interno del database
				
				trasmissione.setId_cambio(database.getId_Trasmissione());                    //prende l'id del cambio appena inserito
				
				database.disconettiti();
				
				//vari settaggi grafici al click di CONFERMA VALORI
				
				comboBox.setEnabled(false);
				textFieldRapportoFinale.setEditable(false);
				textField_Nome.setEditable(false);
				btnConferma.setEnabled(false);
				btnConferma.setVisible(false);
				
				textFieldRapporto.setEditable(true);
				textFieldRapporto.setBackground(Color.WHITE);				
				lblRapporto.setText("Valore rapporto");
				labelRapporti.setText("INSERISCI IL VALORE DEL "+contatore+"�"+" RAPPORTO");
				
			    button_2.setEnabled(true);
			    button_2.setVisible(true);
			   
								
				 }
			}
		});
		
		
		btnConferma.setBounds(568, 54, 142, 23);
		frmNomeSoftware.getContentPane().add(btnConferma);
			
	}
	
	boolean controllo_campo_vuoto(JTextField textfield){            //fuzione di tipo boolean che ritorna true sela textfield � piena e false se vuota
		
		boolean flag=true;
		
		if(textfield.getText().isEmpty())
		{
		flag= false;
		frmErrore();
		}
		return flag;
	}
	

	
	public class KeyAdapterNumbersOnly extends KeyAdapter {						//KeyAdapter che permette di accettare solamente l'inserimento di numeri all'interno delle textfield

	    /**
	     * Regular expression which defines the allowed characters.
	     */
	    private String allowedRegex = "[^0-9, .]";

	    /**
	     * Key released on field.
	     */
	    public void keyReleased(KeyEvent e) {
	        String curText = ((JTextComponent) e.getSource()).getText();
	        curText = curText.replaceAll(allowedRegex, "");

	        ((JTextComponent) e.getSource()).setText(curText);
	    }
	}
	
	//Creazione dei JOptionPane (Conferma/Errore)
	
	
	void frmInfoPunto(String messaggio){
		JOptionPane.showMessageDialog(frmNomeSoftware,
				    messaggio,
				    "Info",
				    JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	void frmErroreRid(){
		JOptionPane.showMessageDialog(frmNomeSoftware,
				    "Il valore del coefficente di riduzione non pu� essere maggiore rispetto al valore del rapporto precedente.",
				    "Errore",
				    JOptionPane.ERROR_MESSAGE);
	}
	
	void frmErrore(){
		JOptionPane.showMessageDialog(frmNomeSoftware,
				    "Campo/i vuoto/i",
				    "ERRORE DI INSERIMENTO",
				    JOptionPane.ERROR_MESSAGE);
	}
	
	void frmConferma(){
		JOptionPane.showMessageDialog(frmNomeSoftware,
			    "Trasmissione inserita correttamente nel database.",
			    "Info",
			    JOptionPane.INFORMATION_MESSAGE
			    );		
				frmNomeSoftware.dispose();
				Editor es=new Editor();
	}
}
