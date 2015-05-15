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

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import Database.Database;
import pilota.Pilota;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

//import jdk.internal.org.objectweb.asm.tree.FrameNode;

public class AggiungiPilota {

	private JFrame frmNomeSoftware;
	private ArrayList<JTextField> campi = new ArrayList<JTextField>(11);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AggiungiPilota window = new AggiungiPilota();
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
	public AggiungiPilota() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmNomeSoftware = new JFrame(); //creazione frame
		frmNomeSoftware.setVisible(true);
		frmNomeSoftware.setTitle("Simulator Times Track");
		frmNomeSoftware.setResizable(false);
		frmNomeSoftware.getContentPane().setBackground(new Color(0, 0, 0));
		frmNomeSoftware.setBounds(100, 100, 726, 462);
		frmNomeSoftware.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmNomeSoftware.getContentPane().setLayout(null);
		
		JButton button = new JButton("<-- INDIETRO");         //creazione bottone INDIETRO con relative azioni
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {       //al click di INDIETRO si chiude il frame in uso e si apre il frame di EDITOR
				frmNomeSoftware.dispose();
				Editor e = new Editor();
			}
		});
		button.setForeground(new Color(255, 69, 0));
		button.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		button.setBounds(10, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(button);
		
		JButton button_1 = new JButton("HOME");                //creazione bottone HOME con relative azioni
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {        //al click di HOME si chiude il frame in uso e si apre il frame di HOME
				frmNomeSoftware.dispose();
				Home h = new Home();
			}
		});
		button_1.setForeground(new Color(255, 69, 0));
		button_1.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		button_1.setBounds(196, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(button_1);
		
		JButton btnConferma = new JButton("CONFERMA");
		btnConferma.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				
				if(controllo_generale())
				{                                                                    //se i valori dei vari text field non sono errati si procede...
					
				 Pilota pilota = new Pilota();                                                              //creazione nuovo pilota
				 
				 //accesso al database
				 
				 Database database;
				 database = new Database();
				 database.caricadriver();
				 database.collegati();
				 
				 database.getDati_Pilota(campi.get(0).getText(), campi.get(1).getText(), pilota);     //viene invocata la fuzione che prende i valori del pilota alla quale vengono passati il nome ed il cognome del pilota inseriti nelle textfield.
				 																						   //Questa funzione controlla se esiste gi� un pilota con lo stesso nome e lo fa tramite una query nel database
				 
				 if(pilota.getNome()==null){                                          					   //Se il precedente controllo non ha restituito alcuna vista si precede con il s
					 
					 pilota.setNome(campi.get(0).getText());
					 pilota.setCognome(campi.get(1).getText());
					 pilota.setAccelerazione(Integer.parseInt(campi.get(8).getText()));
					 pilota.setAggressivita(Integer.parseInt(campi.get(9).getText()));
					 pilota.setConcentrazione(Integer.parseInt(campi.get(3).getText()));
					 pilota.setEsperienza(Integer.parseInt(campi.get(4).getText()));
					 pilota.setEta(Integer.parseInt(campi.get(2).getText()));
					 pilota.setFrenata(Integer.parseInt(campi.get(10).getText()));
					 pilota.setMotivazione(Integer.parseInt(campi.get(6).getText()));
					 pilota.setTecnica(Integer.parseInt(campi.get(5).getText()));
					 pilota.setPeso(Integer.parseInt(campi.get(7).getText()));
					 pilota.setTotale();
					 
					 database.setDati_Pilota(pilota);
					 
					 frmConferma();																			//JOptionPane di conferma
					 
				 }else{																						//JOptionPane di errore
					 
					 frmErroreGen("Il pilota � gi� presente nel database!");
					 
				 }}
			}
		});
		btnConferma.setForeground(new Color(255, 69, 0));
		btnConferma.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnConferma.setBounds(534, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(btnConferma);
		
		//creazione delle varie label
		
		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setForeground(new Color(255, 69, 0));
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblNewLabel_1.setBounds(10, 78, 101, 29);
		frmNomeSoftware.getContentPane().add(lblNewLabel_1);
		
		JLabel lblCognome = new JLabel("Cognome");
		lblCognome.setForeground(new Color(255, 69, 0));
		lblCognome.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblCognome.setBounds(344, 78, 101, 29);
		frmNomeSoftware.getContentPane().add(lblCognome);
		
		JLabel lblAggressivit = new JLabel("Aggressivit\u00E0");
		lblAggressivit.setForeground(new Color(255, 69, 0));
		lblAggressivit.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblAggressivit.setBounds(10, 238, 101, 29);
		frmNomeSoftware.getContentPane().add(lblAggressivit);
		
		JLabel lblConcentrazione = new JLabel("Concentrazione");
		lblConcentrazione.setForeground(new Color(255, 69, 0));
		lblConcentrazione.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblConcentrazione.setBounds(344, 118, 101, 29);
		frmNomeSoftware.getContentPane().add(lblConcentrazione);
		
		JLabel lblEsperienza = new JLabel("Esperienza");
		lblEsperienza.setForeground(new Color(255, 69, 0));
		lblEsperienza.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblEsperienza.setBounds(10, 158, 101, 29);
		frmNomeSoftware.getContentPane().add(lblEsperienza);
		
		JLabel lblTecnica = new JLabel("Tecnica");
		lblTecnica.setForeground(new Color(255, 69, 0));
		lblTecnica.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblTecnica.setBounds(344, 158, 101, 29);
		frmNomeSoftware.getContentPane().add(lblTecnica);
		
		JLabel lblMotivazione = new JLabel("Motivazione");
		lblMotivazione.setForeground(new Color(255, 69, 0));
		lblMotivazione.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblMotivazione.setBounds(10, 198, 101, 29);
		frmNomeSoftware.getContentPane().add(lblMotivazione);
		
		JLabel lblEt = new JLabel("Et\u00E0");
		lblEt.setForeground(new Color(255, 69, 0));
		lblEt.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblEt.setBounds(10, 118, 101, 29);
		frmNomeSoftware.getContentPane().add(lblEt);
		
		JLabel lblPeso = new JLabel("Peso [Kg]");
		lblPeso.setForeground(new Color(255, 69, 0));
		lblPeso.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblPeso.setBounds(344, 193, 101, 29);
		frmNomeSoftware.getContentPane().add(lblPeso);
		
		JLabel lblAccelerazione = new JLabel("Accelerazione");
		lblAccelerazione.setForeground(new Color(255, 69, 0));
		lblAccelerazione.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblAccelerazione.setBounds(344, 238, 101, 29);
		frmNomeSoftware.getContentPane().add(lblAccelerazione);
		
		JLabel lblFrenata = new JLabel("Frenata");
		lblFrenata.setForeground(new Color(255, 69, 0));
		lblFrenata.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblFrenata.setBounds(10, 273, 101, 29);
		frmNomeSoftware.getContentPane().add(lblFrenata);
		
		JLabel lblInserisciIDati = new JLabel("INSERISCI I DATI DEL PILOTA:");
		lblInserisciIDati.setForeground(new Color(255, 69, 0));
		lblInserisciIDati.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblInserisciIDati.setBounds(10, 10, 619, 29);
		frmNomeSoftware.getContentPane().add(lblInserisciIDati);
		
		//creazione delle varie textfield
		
		JTextField textFieldNome = new JTextField();
		textFieldNome.setName("nome");
		textFieldNome.setBounds(118, 78, 200, 29);
		textFieldNome.addKeyListener(new KeyAdapterLettereOnly());
		frmNomeSoftware.getContentPane().add(textFieldNome);
		textFieldNome.setColumns(10);		
		campi.add(textFieldNome);
		
		JTextField textFieldCognome = new JTextField();
		textFieldCognome.setName("cognome");
		textFieldCognome.setColumns(10);
		textFieldCognome.setBounds(474, 78, 200, 29);
		textFieldCognome.addKeyListener(new KeyAdapterLettereOnly());
		campi.add(textFieldCognome);
		frmNomeSoftware.getContentPane().add(textFieldCognome);
		
		JTextField textFieldEta = new JTextField();
		textFieldEta.setName("eta");
		textFieldEta.setColumns(10);
		textFieldEta.setBounds(118, 118, 200, 29);
		textFieldEta.addKeyListener(new KeyAdapterNumbersOnly());
		campi.add(textFieldEta);
		frmNomeSoftware.getContentPane().add(textFieldEta);
		
		
		JTextField textFieldConcentrazione = new JTextField();
		textFieldConcentrazione.setName("concentrazione");
		textFieldConcentrazione.setColumns(10);
		textFieldConcentrazione.setBounds(474, 118, 200, 29);
		textFieldConcentrazione.addKeyListener(new KeyAdapterNumbersOnly());
		campi.add(textFieldConcentrazione);
		frmNomeSoftware.getContentPane().add(textFieldConcentrazione);
		
		
		JTextField textFieldEsperienza = new JTextField();
		textFieldEsperienza.setName("esperienza");
		textFieldEsperienza.setColumns(10);
		textFieldEsperienza.setBounds(118, 158, 200, 29);
		textFieldEsperienza.addKeyListener(new KeyAdapterNumbersOnly());    //aggiunta KeyAdapter per il text field (numeri)
		campi.add(textFieldEsperienza);
		frmNomeSoftware.getContentPane().add(textFieldEsperienza);
		
		JTextField textFieldTecnica = new JTextField();
		textFieldTecnica.setName("tecnica");
		textFieldTecnica.setColumns(10);
		textFieldTecnica.setBounds(474, 158, 200, 29);
		textFieldTecnica.addKeyListener(new KeyAdapterNumbersOnly());       //aggiunta KeyAdapter per il text field (numeri)
		campi.add(textFieldTecnica);
		frmNomeSoftware.getContentPane().add(textFieldTecnica);
		
		JTextField textFieldMotivazione = new JTextField();
		textFieldMotivazione.setName("motivazione");
		textFieldMotivazione.setColumns(10);
		textFieldMotivazione.setBounds(118, 198, 200, 29);
		textFieldMotivazione.addKeyListener(new KeyAdapterNumbersOnly());   //aggiunta KeyAdapter per il text field (numeri)
		campi.add(textFieldMotivazione);
		frmNomeSoftware.getContentPane().add(textFieldMotivazione);
		
		JTextField textFieldPeso = new JTextField();
		textFieldPeso.setName("peso");
		textFieldPeso.setColumns(10);
		textFieldPeso.setBounds(474, 198, 200, 29); 
		textFieldPeso.addKeyListener(new KeyAdapterNumbersOnly());          //aggiunta KeyAdapter per il text field (numeri)
		campi.add(textFieldPeso);
		frmNomeSoftware.getContentPane().add(textFieldPeso);
		
		JTextField textFieldAccelerazione = new JTextField();
		textFieldAccelerazione.setName("accelerazione");
		textFieldAccelerazione.setColumns(10);
		textFieldAccelerazione.setBounds(474, 238, 200, 29);
		textFieldAccelerazione.addKeyListener(new KeyAdapterNumbersOnly()); //aggiunta KeyAdapter per il text field (numeri)
		campi.add(textFieldAccelerazione);
		frmNomeSoftware.getContentPane().add(textFieldAccelerazione);
		
		JTextField textFieldAggressivita = new JTextField();
		textFieldAggressivita.setName("aggressivit�");
		textFieldAggressivita.setColumns(10);
		textFieldAggressivita.setBounds(118, 238, 200, 29);
		textFieldAggressivita.addKeyListener(new KeyAdapterNumbersOnly());  //aggiunta KeyAdapter per il text field (numeri)
		campi.add(textFieldAggressivita);
		frmNomeSoftware.getContentPane().add(textFieldAggressivita);
		
		JTextField textFieldFrenata = new JTextField();
		textFieldFrenata.setName("frenata");
		textFieldFrenata.setColumns(10);
		textFieldFrenata.setBounds(118, 278, 200, 29);
		textFieldFrenata.addKeyListener(new KeyAdapterNumbersOnly());       //aggiunta KeyAdapter per il text field (numeri)
		campi.add(textFieldFrenata);
		frmNomeSoftware.getContentPane().add(textFieldFrenata);
		
		
	}
	public class KeyAdapterNumbersOnly extends KeyAdapter {

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
	
	public class KeyAdapterLettereOnly extends KeyAdapter {

	    /**
	     * Regular expression which defines the allowed characters.
	     */
	    private String allowedRegex = "[^a-z]";

	    /**
	     * Key released on field.
	     */
	    public void keyReleased(KeyEvent e) {
	        String curText = ((JTextComponent) e.getSource()).getText();
	        curText = curText.replaceAll(allowedRegex, "");

	        ((JTextComponent) e.getSource()).setText(curText);
	    }
	}
	
  private boolean controllo_generale()
	{
			
		for (int i = 0; i < campi.size(); i++) 
		{
			JTextField parziale = campi.get(i);
			
			if(parziale.getText().isEmpty())
			{
				frmErroreGen("Il campo '"+ parziale.getName() + "' � vuoto.");
				return false;
				
			}
			else if(parziale.getName()=="eta")
			{
				if(!(Integer.parseInt(parziale.getText())>=18&&Integer.parseInt(parziale.getText())<=50)){
					frmErroreGen("L'et� del pilota deve essere un valore compreso tra 18 e 50 (inclusi).");
					return false;				
				}
					
			}
			else if(parziale.getName()=="peso")
			{
				if(!(Integer.parseInt(parziale.getText())>=45&&Integer.parseInt(parziale.getText())<=120)){
					
					frmErroreGen("Il peso del pilota deve essere un valore compreso tra 45 e 120 (inclusi).");
					return false;
					
				}
					
			}
			else if(i>1){
				if(!(Integer.parseInt(parziale.getText())>=1&&Integer.parseInt(parziale.getText())<=100))
				{
				frmErroreGen("Il valore del campo '"+ parziale.getName() + "' deve essere compreso tra 1 e 100 (inclusi).");
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
				    "Errore",
				    JOptionPane.ERROR_MESSAGE);
	}

	
	void frmConferma(){
		JOptionPane.showMessageDialog(frmNomeSoftware,
			    "Pilota memorizzato nel database!",
			    "Info",
			    JOptionPane.INFORMATION_MESSAGE
			    );		
				frmNomeSoftware.dispose();
				Editor es=new Editor();
	}
	

	
}
