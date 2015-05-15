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

import javax.swing.JFileChooser;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;

import Circuito.Circuito;
import Circuito.Importa_Circuito;
import Database.Database;
import javax.swing.JProgressBar;

public class AggiungiCircuito{

	private JFrame frmNomeSoftware;
	private JTextField textFieldTrk;
	private String stringa;
	private JTextField textField_Nome;
	private JTextField textField_Nazione;
	private JButton btnConferma;  //creazione bottone conferma con relative azioni
	private JButton butHome;
	private JButton butIndietro;
   
	private JProgressBar progressBar;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AggiungiCircuito window = new AggiungiCircuito();
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
	public AggiungiCircuito() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmNomeSoftware = new JFrame(); //creazione frame
		frmNomeSoftware.setVisible(true);
		frmNomeSoftware.setTitle("Simulator track times");
		frmNomeSoftware.setResizable(false);
		frmNomeSoftware.getContentPane().setBackground(new Color(0, 0, 0));
		frmNomeSoftware.setBounds(100, 100, 726, 462);
		frmNomeSoftware.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmNomeSoftware.getContentPane().setLayout(null);
		
		ListenerCircuito listeners = new ListenerCircuito(this);
		
		butIndietro = new JButton("<-- INDIETRO"); //creazione bottone conferma con relative azioni
		butIndietro.setActionCommand("indietro");
		butIndietro.addActionListener(listeners);
		butIndietro.setForeground(new Color(255, 69, 0));
		butIndietro.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		butIndietro.setBounds(10, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(butIndietro);
		
		butHome = new JButton("HOME"); //creazione bottone home con relative azioni
		butHome.setActionCommand("home");
		butHome.addActionListener(listeners);
		butHome.setForeground(new Color(255, 69, 0));
		butHome.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		butHome.setBounds(196, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(butHome);
		
		btnConferma = new JButton("CONFERMA");
		btnConferma.setActionCommand("conferma");
		btnConferma.addActionListener(listeners);
		btnConferma.setForeground(new Color(255, 69, 0));
		btnConferma.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnConferma.setBounds(534, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(btnConferma);
		
		//creazione dei vari label
		
		JLabel lblImportaUnNuovo = new JLabel("IMPORTA UN NUOVO CIRCUITO:");
		lblImportaUnNuovo.setForeground(new Color(255, 69, 0));
		lblImportaUnNuovo.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
		lblImportaUnNuovo.setBounds(10, 11, 271, 29);
		frmNomeSoftware.getContentPane().add(lblImportaUnNuovo);
		
		JLabel lblFiletrk = new JLabel("File .trk:");
		lblFiletrk.setForeground(new Color(255, 69, 0));
		lblFiletrk.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblFiletrk.setBounds(20, 51, 77, 29);
		frmNomeSoftware.getContentPane().add(lblFiletrk);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setForeground(new Color(255, 69, 0));
		lblNome.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblNome.setBounds(20, 123, 77, 29);
		frmNomeSoftware.getContentPane().add(lblNome);
		
		JLabel lblNazione = new JLabel("Nazione");
		lblNazione.setForeground(new Color(255, 69, 0));
		lblNazione.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblNazione.setBounds(10, 190, 77, 29);
		frmNomeSoftware.getContentPane().add(lblNazione);
		
		textField_Nome = new JTextField();
		textField_Nome.setBounds(97, 123, 347, 29);
		textField_Nome.addKeyListener(new KeyAdapterLettereNumeriOnly());  //aggiunta KeyAdapter per il text field (numeri e lettere)
		frmNomeSoftware.getContentPane().add(textField_Nome);
		textField_Nome.setColumns(10);
			
		textFieldTrk = new JTextField();
		textFieldTrk.setEditable(false);
		textFieldTrk.setBounds(97, 51, 347, 29);
		frmNomeSoftware.getContentPane().add(textFieldTrk);
		textFieldTrk.setColumns(10);
		
		textField_Nazione = new JTextField();
		textField_Nazione.setColumns(10);
		textField_Nazione.setBounds(97, 190, 347, 29);
		textField_Nazione.addKeyListener(new KeyAdapterLettereOnly());                                  //aggiunta KeyAdapter per il text field (lettere)
		frmNomeSoftware.getContentPane().add(textField_Nazione);
		
		JButton btnSfoglia = new JButton("Sfoglia"); //creazione del bottone sfoglia
		btnSfoglia.setActionCommand("sfoglia");
		btnSfoglia.addActionListener(listeners);
		btnSfoglia.setBounds(454, 54, 89, 23);
		frmNomeSoftware.getContentPane().add(btnSfoglia);
		
		progressBar = new JProgressBar();
		progressBar.setToolTipText("");
		progressBar.setVisible(false);
		progressBar.setBounds(283, 282, 212, 29);
		frmNomeSoftware.getContentPane().add(progressBar);
		
		
		
		
	}
	
	void frmErrore(){                                           		//JOptionPane di errore
		JOptionPane.showMessageDialog(frmNomeSoftware,
				    "Circuito gi� esistente",
				    "ERRORE DI INSERIMENTO",
				    JOptionPane.ERROR_MESSAGE);
	}
	
	void frmErroreVuoto(){												//JOptionePane di errore
		JOptionPane.showMessageDialog(frmNomeSoftware,
				    "Campi vuoti",
				    "ERRORE DI INSERIMENTO",
				    JOptionPane.ERROR_MESSAGE);
	}
	
	void frmConferma(){											        //JOptionPane di conferma
		JOptionPane.showMessageDialog(frmNomeSoftware,
			    "Circuito memorizzato nel database.",
			    "Info",
			    JOptionPane.INFORMATION_MESSAGE
			    );		
				frmNomeSoftware.dispose();
				Editor es=new Editor();
	}
	
	public class KeyAdapterLettereNumeriOnly extends KeyAdapter {       //KeyAdapter che permette di accettare solamente l'inserimento di numeri e lettere all'interno delle textfield

	    /**
	     * Regular expression which defines the allowed characters.
	     */
	    private String allowedRegex = "[^a-z, 0-9]";

	    /**
	     * Key released on field.
	     */
	    public void keyReleased(KeyEvent e) {
	        String curText = ((JTextComponent) e.getSource()).getText();
	        curText = curText.replaceAll(allowedRegex, "");

	        ((JTextComponent) e.getSource()).setText(curText);
	    }
	}
	
	public class KeyAdapterLettereOnly extends KeyAdapter {   			//KeyAdapter che permette di accettare solamente l'inserimento di lettere all'interno delle textfield

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
	
	boolean controllo_textField (){										//fuzione di tipo boolean che restituisce true se la textfield � piena e false se � vuota
		boolean flag = true;
		if(textFieldTrk.getText().isEmpty()||textField_Nazione.getText().isEmpty()||textField_Nome.getText().isEmpty()){
			frmErroreVuoto();
			flag = false;
		}
		return flag;
	}
	
	public String getStringa(){
		return this.stringa;
	}
	
	public void setString(String stringa){
		this.stringa = stringa;
		textFieldTrk.setText(stringa);
	}
	
	public JFrame getFrame(){
		return this.frmNomeSoftware;
	}
	
	public JTextField getNome(){
		return this.textField_Nome;
	}
	
	public JTextField getPath(){
		return this.textFieldTrk;
	}
	
	public JTextField getNazione(){
		return this.textField_Nazione;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}
	
	public JButton getConferma(){
		return this.btnConferma;
	}

	public JButton getButHome() {
		return butHome;
	}

	public void setButHome(JButton butHome) {
		this.butHome = butHome;
	}

	public JButton getButIndietro() {
		return butIndietro;
	}

	public void setButIndietro(JButton butIndietro) {
		this.butIndietro = butIndietro;
	}
}
