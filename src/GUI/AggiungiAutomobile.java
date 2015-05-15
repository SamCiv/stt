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

import javax.swing.DefaultComboBoxModel;
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

import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.text.JTextComponent;

import auto.Auto;
import auto.Motore;
import auto.Pneumatico;
import auto.Trasmissione;
import Database.Database;

import javax.swing.JScrollPane;

public class AggiungiAutomobile {
	
	
	Auto auto = new Auto();

	private JFrame frmNomeSoftware;
	private JTextField textField_Nome;
	private JTextField textField_Cd;
	private JTextField textField_Serbatoio;
	private JTextField textField_Marca;
	private JTextField textField_Area;
	private JTextField textField_Massa;

	private double cd;
	private double area;
	private int serbatoio;
	private int massa_auto;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AggiungiAutomobile window = new AggiungiAutomobile();
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
	public AggiungiAutomobile() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		Database database = new Database();
		database.caricadriver();
		database.collegati();
		
		frmNomeSoftware = new JFrame();  //creazione frame
		frmNomeSoftware.setVisible(true);
		frmNomeSoftware.setTitle("Simulator Times Track");
		frmNomeSoftware.setResizable(false);
		frmNomeSoftware.getContentPane().setBackground(new Color(0, 0, 0));
		frmNomeSoftware.setBounds(100, 100, 726, 462);
		frmNomeSoftware.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JButton button = new JButton("<-- INDIETRO");  //creazione bottone INDIETRO con relative azioni
		button.setBounds(10, 388, 176, 35);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) { //al click di INDIETRO si chiude il frame in uso e si apre il frame di EDITOR
				frmNomeSoftware.dispose();             
				Editor e = new Editor();
			}
		});
		frmNomeSoftware.getContentPane().setLayout(null); //Absolute Layout
		button.setForeground(new Color(255, 69, 0));
		button.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		frmNomeSoftware.getContentPane().add(button);
		
		JButton button_1 = new JButton("HOME");         //creazione bottone HOME con relative azioni
		button_1.setBounds(196, 388, 176, 35);
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) { //al click di HOME si chiude il frame in uso e si apre il frame di HOME
				frmNomeSoftware.dispose();          
				Home h = new Home();
			}
		});
		button_1.setForeground(new Color(255, 69, 0));
		button_1.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		frmNomeSoftware.getContentPane().add(button_1);
		
		//creazione delle varie label
		
		JLabel lblInserisciIDati = new JLabel("INSERISCI I DATI DELL'AUTOMOBILE:"); 
		lblInserisciIDati.setBounds(10, 11, 343, 29);
		lblInserisciIDati.setForeground(new Color(255, 69, 0));
		lblInserisciIDati.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		frmNomeSoftware.getContentPane().add(lblInserisciIDati);
		
		JLabel label = new JLabel("Nome");
		label.setBounds(10, 73, 71, 29);
		label.setForeground(new Color(255, 69, 0));
		label.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		frmNomeSoftware.getContentPane().add(label);
		
		JLabel lblMarca = new JLabel("Marca");
		lblMarca.setBounds(340, 73, 71, 29);
		lblMarca.setForeground(new Color(255, 69, 0));
		lblMarca.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		frmNomeSoftware.getContentPane().add(lblMarca);
		
		JLabel lblCd = new JLabel("Cd");
		lblCd.setBounds(10, 113, 71, 29);
		lblCd.setForeground(new Color(255, 69, 0));
		lblCd.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		frmNomeSoftware.getContentPane().add(lblCd);
		
		JLabel lblArea = new JLabel("Area [m^2]");
		lblArea.setBounds(340, 113, 71, 29);
		lblArea.setForeground(new Color(255, 69, 0));
		lblArea.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		frmNomeSoftware.getContentPane().add(lblArea);
		
		JLabel lblSerbatoio = new JLabel("Serbatoio [l]");
		lblSerbatoio.setBounds(10, 153, 71, 29);
		lblSerbatoio.setForeground(new Color(255, 69, 0));
		lblSerbatoio.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		frmNomeSoftware.getContentPane().add(lblSerbatoio);
		
		JLabel lblMassa = new JLabel("Massa [Kg]");
		lblMassa.setBounds(340, 153, 71, 29);
		lblMassa.setForeground(new Color(255, 69, 0));
		lblMassa.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		frmNomeSoftware.getContentPane().add(lblMassa);
		
		JLabel lblMotore = new JLabel("Motore");
		lblMotore.setBounds(10, 216, 71, 29);
		lblMotore.setForeground(new Color(255, 69, 0));
		lblMotore.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		frmNomeSoftware.getContentPane().add(lblMotore);
		
		JLabel lblCambio = new JLabel("Cambio");
		lblCambio.setBounds(10, 256, 71, 29);
		lblCambio.setForeground(new Color(255, 69, 0));
		lblCambio.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		frmNomeSoftware.getContentPane().add(lblCambio);
		
		JLabel lblPneumatico = new JLabel("Pneumatico");
		lblPneumatico.setBounds(10, 296, 71, 29);
		lblPneumatico.setForeground(new Color(255, 69, 0));
		lblPneumatico.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		frmNomeSoftware.getContentPane().add(lblPneumatico);
		
		//creazione delle varie text field
		
		textField_Nome = new JTextField();
		textField_Nome.setBounds(91, 72, 231, 30);
		frmNomeSoftware.getContentPane().add(textField_Nome);
		textField_Nome.setColumns(10);
		
		textField_Cd = new JTextField();
		textField_Cd.setBounds(91, 113, 231, 30);
		textField_Cd.setColumns(10);
		textField_Cd.addKeyListener(new KeyAdapterNumbersPuntoOnly()); //aggiunta KeyAdapter per il text field (numeri e virgola)
		frmNomeSoftware.getContentPane().add(textField_Cd);
		
		textField_Serbatoio = new JTextField();
		textField_Serbatoio.setBounds(91, 152, 231, 30);
		textField_Serbatoio.setColumns(10);
		textField_Serbatoio.addKeyListener(new KeyAdapterNumbersOnly()); //aggiunta KeyAdapter per il text field (numeri)
		frmNomeSoftware.getContentPane().add(textField_Serbatoio);
		
		textField_Marca = new JTextField();
		textField_Marca.setBounds(417, 73, 231, 30);
		textField_Marca.setColumns(10);
		frmNomeSoftware.getContentPane().add(textField_Marca);
		
		textField_Area = new JTextField();
		textField_Area.setBounds(417, 112, 231, 30);
		textField_Area.setColumns(10);
		textField_Area.addKeyListener(new KeyAdapterNumbersPuntoOnly()); //aggiunta KeyAdapter per il text field (numeri e virgola)
		frmNomeSoftware.getContentPane().add(textField_Area);
		
		textField_Massa = new JTextField();
		textField_Massa.setBounds(417, 152, 231, 30);
		textField_Massa.setColumns(10);
		textField_Massa.addKeyListener(new KeyAdapterNumbersOnly());  	  //aggiunta KeyAdapter per il text field (numeri)
		frmNomeSoftware.getContentPane().add(textField_Massa);
		
		//creazione delle varie ComboBox
		
		final JComboBox comboBox_Motore = new JComboBox();
		comboBox_Motore.setBounds(122, 216, 250, 29);
		database.inizializza_ComboBox(comboBox_Motore, "Motore"); 		  //Viene modellata la combobox con una fuzione che visualizza i nomi dei motori all'interno delle combobox e ne prende l'ID
		frmNomeSoftware.getContentPane().add(comboBox_Motore);	
		
		final JComboBox comboBox_Cambio = new JComboBox();
		comboBox_Cambio.setBounds(122, 256, 250, 29);
		database.inizializza_ComboBox(comboBox_Cambio, "Trasmissione");   //Viene modellata la combobox con una fuzione che visualizza i nomi delle trasmissioni all'interno delle combobox e ne prende l'ID
		frmNomeSoftware.getContentPane().add(comboBox_Cambio); 
		
		final JComboBox comboBox_Pneumatico = new JComboBox();
		comboBox_Pneumatico.setBounds(122, 296, 250, 29);
		database.inizializza_ComboBox(comboBox_Pneumatico, "Pneumatico"); //Viene modellata la combobox con una fuzione che visualizza i nomi dei pneumatici all'interno delle combobox e ne prende l'ID
		frmNomeSoftware.getContentPane().add(comboBox_Pneumatico);
		
		JButton btnConferma = new JButton("CONFERMA");					  //creazione bottone Conferma con relative azioni
		btnConferma.setBounds(534, 388, 176, 35);
		btnConferma.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) { 				  //settaggio azione click CONFERMA
				
				if(controllo_campo_vuoto(textField_Area)&&controllo_campo_vuoto(textField_Cd)&&controllo_campo_vuoto(textField_Marca)&&controllo_campo_vuoto(textField_Massa)&&controllo_campo_vuoto(textField_Nome)&&controllo_campo_vuoto(textField_Serbatoio)) //controllo se le textfield sono vuote
				{					
					Database database = new Database();
					database.caricadriver();
					database.collegati();
					
					if(database.Popolamento_Auto()){
					//conversione da String a Integer/Double dei valori immessi all'interno delle text field
					
					serbatoio=Integer.parseInt(textField_Serbatoio.getText());
					cd=Double.parseDouble(textField_Cd.getText());
					area=Double.parseDouble(textField_Area.getText());
					massa_auto=Integer.parseInt(textField_Massa.getText());
					
					Motore motore = new Motore(); 					//creazione nuovo oggetto motore
					Trasmissione trasmissione = new Trasmissione(); //creazione nuovo oggetto  trasmissione
					Pneumatico pneumatico = new Pneumatico();  		//creazione nuovo oggetto pneumatico
					
					ComboItem elemento = (ComboItem) comboBox_Motore.getSelectedItem();		//creazione oggetto di tipo ComboItem a ciu assegnamo il valore all'intero della combobox motore
					
					motore.setId_motore(Integer.parseInt(elemento.getValore())); 			//settiamo all'oggetto motore, creato in precedenza, l'ID del corrispettivo nome all'interno della combobox
					
					elemento = (ComboItem) comboBox_Cambio.getSelectedItem(); 				//creazione oggetto di tipo ComboItem a cui assegnamo il valore all'intero della combobox cambio
					
					trasmissione.setId_cambio(Integer.parseInt(elemento.getValore())); 		//settiamo all'oggetto trasmissione creato in precedenza l'ID del corrispettivo nome all'interno della combobox
					
					elemento = (ComboItem) comboBox_Pneumatico.getSelectedItem();  			//creazione oggetto di tipo ComboItem a cui assegnamo il valore all'intero della combobox cambio
					
					pneumatico.setId_pneumatico(Integer.parseInt(elemento.getValore()));	//settiamo all'oggetto pneumatico, creato in precedenza, l'ID del corrispettivo nome all'interno della combobox
					
					//passaggio dei vari valori inseriti all'interno delle textfield o scelti nelle combobox all'oggetto auto
					
					auto.setMotore(motore);
					auto.setTrasmissione(trasmissione);
					auto.setPneumatico(pneumatico);
					
					auto.setArea(area);
					auto.setCd(cd);
					auto.setMarca(textField_Marca.getText());
					auto.setMassa_auto(massa_auto);
					auto.setNome(textField_Nome.getText());
					auto.setSerbatoio(serbatoio);
					
					//creazione dell'autombile all'interno del database
					
					database.setDati_Auto(auto);
						
					database.disconettiti();
					
					frmConferma(); 				//JOptionPane di conferma
					
					frmNomeSoftware.dispose(); 
					Editor es=new Editor();
					
				}else
					frmDatabase();
				
			}}
		});
		btnConferma.setForeground(new Color(255, 69, 0));
		btnConferma.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		frmNomeSoftware.getContentPane().add(btnConferma);
		
	}
	
	
	boolean controllo_campo_vuoto(JTextField textfield) 						//fuzione di tipo boolean che ritorna true sela textfield � piena e false se vuota
	{
			
			boolean flag=true;
			
			if(textfield.getText().isEmpty())
			{
			flag= false;
			frmErrore();
			}
			return flag;
	}
	
	void frmErrore(){   //JOptionPane di errore
		JOptionPane.showMessageDialog(frmNomeSoftware,
				    "Campo vuoto.",
				    "ERRORE DI INSERIMENTO",
				    JOptionPane.ERROR_MESSAGE);
	}
	
	void frmDatabase(){   //JOptionPane di errore
		JOptionPane.showMessageDialog(frmNomeSoftware,
				    "Inserisci nel databse almeno un motore, un pneumatico e una trasmissione",
				    "ERRORE DI INSERIMENTO",
				    JOptionPane.ERROR_MESSAGE);
	}
	void frmConferma(){ //JOptionPane di conferma
		JOptionPane.showMessageDialog(frmNomeSoftware,
			    "L'auto � stata inserita correttamente nel database.",
			    "OK",
			    JOptionPane.INFORMATION_MESSAGE
			    );		
	}
	
	public class KeyAdapterNumbersPuntoOnly extends KeyAdapter {  	//KeyAdapter che permette di accettare solamente l'inserimento di numeri e del punto all'interno delle textfield

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
	public class KeyAdapterNumbersOnly extends KeyAdapter { 		//KeyAdapter che permette di accettare solamente l'inserimento di numeri all'interno delle textfield

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
}
