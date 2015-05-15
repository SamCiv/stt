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

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JTextField;

import auto.Pneumatico;
import Database.Database;

import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

public class AggiungiPneumatico {

	private JFrame frmNomeSoftware;
	private ArrayList<JTextField> campi = new ArrayList<JTextField>(6);

	private int rapporto;
	private int larghezza;
	private int durata;
	private int dim_cerchio;
	private double coefficiente;
	private boolean flag=true;
	 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AggiungiPneumatico window = new AggiungiPneumatico();
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
	public AggiungiPneumatico() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmNomeSoftware = new JFrame();                                            //creazione frame
		frmNomeSoftware.setVisible(true);
		frmNomeSoftware.setTitle("Simulator Times Track");
		frmNomeSoftware.setResizable(false);
		frmNomeSoftware.getContentPane().setBackground(new Color(0, 0, 0));
		frmNomeSoftware.setBounds(100, 100, 726, 462);
		frmNomeSoftware.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmNomeSoftware.getContentPane().setLayout(null);
		
		JButton button = new JButton("<-- INDIETRO");               //creazione bottone INDIETRO con relative azioni
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {             //al click di INDIETRO si chiude il frame in uso e si apre il frame di EDITOR
				frmNomeSoftware.dispose();
				Editor e = new Editor();
			}
		});
		button.setForeground(new Color(255, 69, 0));
		button.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		button.setBounds(10, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(button);
		
		JButton button_1 = new JButton("HOME");                     //creazione bottone HOME con relative azioni
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {				//al click di HOME si chiude il frame in uso e si apre il frame di HOME
				frmNomeSoftware.dispose();
				Home h = new Home();
			}
		});
		button_1.setForeground(new Color(255, 69, 0));
		button_1.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		button_1.setBounds(196, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(button_1);
		
		JButton btnConferma = new JButton("CONFERMA");                //creazione bottone Conferma con relative azioni
		btnConferma.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {               //settaggio azione del click CONFERMA
				
				if(ControlloGenerale()){  							  //se i valori dei vari text field non sono errati si procede...
					
				 Pneumatico pneumatico = new Pneumatico();    	      //creazione nuovo pneumatico
				
				 //accesso al database
				 
				 Database database;
				 database = new Database();
				 database.caricadriver();
				 database.collegati();
				 
				//passaggio dei vari valori inseriti all'interno delle textfield o scelti nelle combobox all'oggetto pneumatico
				 
				pneumatico.setCoefficiente(Double.parseDouble(campi.get(4).getText()));
				pneumatico.setDim_cerchio(Integer.parseInt(campi.get(3).getText()));
				pneumatico.setDurata(Integer.parseInt(campi.get(5).getText()));				
				pneumatico.setLarghezza(Integer.parseInt(campi.get(1).getText()));				
				pneumatico.setNome(campi.get(0).getText());
				pneumatico.setRapporto(Integer.parseInt(campi.get(2).getText()));
				
				database.setDati_Pneumatico(pneumatico);  				//creazione del pneumatico all'interno del database
				frmConferma();
				}
				
			}
		});
		btnConferma.setForeground(new Color(255, 69, 0));
		btnConferma.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnConferma.setBounds(534, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(btnConferma);
		
		//creazione vari label
		
		JLabel lblInserisciIDati = new JLabel("INSERISCI I DATI DEL PNEUMATICO:");
		lblInserisciIDati.setForeground(new Color(255, 69, 0));
		lblInserisciIDati.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblInserisciIDati.setBounds(23, 11, 619, 29);
		frmNomeSoftware.getContentPane().add(lblInserisciIDati);
		
		JLabel label = new JLabel("Nome");
		label.setForeground(new Color(255, 69, 0));
		label.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		label.setBounds(10, 51, 101, 29);
		frmNomeSoftware.getContentPane().add(label);
		
		JLabel lblLarghezza = new JLabel("Larghezza [mm]");
		lblLarghezza.setForeground(new Color(255, 69, 0));
		lblLarghezza.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblLarghezza.setBounds(10, 91, 101, 29);
		frmNomeSoftware.getContentPane().add(lblLarghezza);
		
		JLabel lblRapporto = new JLabel("Rapporto [%]");
		lblRapporto.setForeground(new Color(255, 69, 0));
		lblRapporto.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblRapporto.setBounds(10, 171, 101, 29);
		frmNomeSoftware.getContentPane().add(lblRapporto);
		
		JLabel lblDimensioneCerchio = new JLabel("Dimensione \r\ncerchio [inch]");
		lblDimensioneCerchio.setForeground(new Color(255, 69, 0));
		lblDimensioneCerchio.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblDimensioneCerchio.setBounds(10, 241, 176, 29);
		frmNomeSoftware.getContentPane().add(lblDimensioneCerchio);
		
		JLabel lblCoefficiente = new JLabel("Coefficiente");
		lblCoefficiente.setForeground(new Color(255, 69, 0));
		lblCoefficiente.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblCoefficiente.setBounds(410, 171, 81, 29);
		frmNomeSoftware.getContentPane().add(lblCoefficiente);
		
		JLabel lblDurata = new JLabel("Durata (Km)");
		lblDurata.setForeground(new Color(255, 69, 0));
		lblDurata.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblDurata.setBounds(410, 95, 81, 29);
		frmNomeSoftware.getContentPane().add(lblDurata);
		
		//creazione vari textfield
		
		JTextField textFieldNome = new JTextField();
		textFieldNome.setName("nome");
		textFieldNome.setBounds(196, 51, 404, 29);
		frmNomeSoftware.getContentPane().add(textFieldNome);
		campi.add(textFieldNome);
		textFieldNome.setColumns(10);
		
		JTextField textField_Larghezza = new JTextField();
		textField_Larghezza.setName("larghezza");
		textField_Larghezza.setColumns(10);
		textField_Larghezza.addKeyListener(new KeyAdapterNumbersOnly());
		textField_Larghezza.setBounds(196, 95, 204, 29);
		campi.add(textField_Larghezza);
		frmNomeSoftware.getContentPane().add(textField_Larghezza);
		
		JTextField textField_Rapporto = new JTextField();
		textField_Rapporto.setName("rapporto");
		textField_Rapporto.setColumns(10);
		textField_Rapporto.addKeyListener(new KeyAdapterNumbersOnly());
		textField_Rapporto.setBounds(196, 171, 204, 29);
		campi.add(textField_Rapporto);
		frmNomeSoftware.getContentPane().add(textField_Rapporto);
		
		JTextField textField_DimensioneCerchio = new JTextField();
		textField_DimensioneCerchio.setName("dimensione_cerchio");
		textField_DimensioneCerchio.setColumns(10);
		textField_DimensioneCerchio.setBounds(196, 241, 204, 29);
		textField_DimensioneCerchio.addKeyListener(new KeyAdapterNumbersOnly());
		campi.add(textField_DimensioneCerchio);
		frmNomeSoftware.getContentPane().add(textField_DimensioneCerchio);
		
		JTextField textField_Coefficiente = new JTextField();
		textField_Coefficiente.setName("coefficiente");
		textField_Coefficiente.setColumns(10);
		textField_Coefficiente.setBounds(506, 171, 204, 29);
		textField_Coefficiente.addKeyListener(new KeyAdapterDoubleOnly());
		campi.add(textField_Coefficiente);
		frmNomeSoftware.getContentPane().add(textField_Coefficiente);
		
		JTextField textField_Durata = new JTextField();
		textField_Durata.setName("durata");
		textField_Durata.setColumns(10);
		textField_Durata.setBounds(506, 95, 204, 29);
		textField_Durata.addKeyListener(new KeyAdapterDoubleOnly());
		campi.add(textField_Durata);
		frmNomeSoftware.getContentPane().add(textField_Durata);
	}
	
	public boolean ControlloGenerale(){    //funzione che controlla che i textfield non siano vuoti e converte i dati inseriti da String a Double/Integer
		
		for (int i = 0; i < campi.size(); i++) 
		{
			JTextField parziale = campi.get(i);
			
			if(parziale.getText().isEmpty())
			{
				frmErroreGen("Il campo '"+ parziale.getName() + "' � vuoto.");
				return false;
				
			}
			else if(parziale.getName()=="larghezza"){
				
				if(!(Integer.parseInt(parziale.getText())>=50&&(Integer.parseInt(parziale.getText())<=400))){
					frmErroreGen("La larghezza del pneumatico deve essere compresa tra 50 e 400.");
					return false;
				}
			}
			else if(parziale.getName()=="durata"){
				if(!(Integer.parseInt(parziale.getText())>=50&&(Integer.parseInt(parziale.getText())<=200))){
					frmErroreGen("La durata del pneumatico deve essere compresa tra 50 e 400.");
					return false;
				}
			}
			else if(parziale.getName()=="rapporto"){
				if(!(Integer.parseInt(parziale.getText())>=1&&(Integer.parseInt(parziale.getText())<=100))){
					frmErroreGen("Il rapporto del pneumatico deve essere compreso tra 1 e 100.");
					return false;
				}
			}
			else if(parziale.getName()=="coefficiente"){
				if(!(Double.parseDouble(parziale.getText())>=0.9&&(Double.parseDouble(parziale.getText())<=2.0))){
					frmErroreGen("Il coefficiente deve essere compresa tra 0.9 e 2.0.");
					return false;
				}
			}
			else if(parziale.getName()=="dimensione_cerchio"){
				if(!(Integer.parseInt(parziale.getText())>=13&&(Integer.parseInt(parziale.getText())<=22))){
					frmErroreGen("La dimensione del cerchio del pneumatico deve essere compresa tra 13 e 22.");
					return false;
				}
			}
		}
	
	return true;
	}
	
	
	
	//Creazione dei JOptionPane (Conferma/Errore)
	
	void frmErroreGen(String messaggio){
		JOptionPane.showMessageDialog(frmNomeSoftware,
				    messaggio,
				    "ERRORE DI INSERIMENTO",
				    JOptionPane.ERROR_MESSAGE);
	}

	
	void frmConferma(){
		JOptionPane.showMessageDialog(frmNomeSoftware,
			    "Pneumatico memorizzato nel database!",
			    "OK",
			    JOptionPane.INFORMATION_MESSAGE
			    );		
				frmNomeSoftware.dispose();
				Editor es=new Editor();
	}
	
	public class KeyAdapterNumbersOnly extends KeyAdapter {                       //KeyAdapter che permette di accettare solamente l'inserimento di numeri all'interno delle textfield

	    /**
	     * Regular expression which defines the allowed characters.
	     */
	    private String allowedRegex = "[^0-9]";

	    /**
	     * Key released on field.
	     */
	    public void keyReleased(KeyEvent e) {
	        String curText = ((JTextComponent) e.getSource()).getText();
	        curText = curText.replaceAll(allowedRegex, "");

	        ((JTextComponent) e.getSource()).setText(curText);
	    }
	}
	
	public class KeyAdapterDoubleOnly extends KeyAdapter {                          //KeyAdapter che permette di accettare solamente l'inserimento di numeri e di punti all'interno delle textfield

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

 
}
