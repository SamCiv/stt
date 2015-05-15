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

//import javafx.scene.control.ComboBox;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;

import Database.Database;
import auto.Curva_motore;
import auto.Motore;

public class AggiungiMotore {

	private JFrame frmNomeSoftware;
	
	
	private JButton button;
	private JButton button_1;
	
	private ArrayList<JTextField> campi = new ArrayList<JTextField>(9);
	
	
	private Motore motore = new Motore();
		
		private int rpm_precedente = 0;
	
	  private int rpm;
	  private double coppia;
	  int cilindrata;
	  int cilindri;
	  double corsa;
	  double alesaggio;
	  double potmax;
	  double coppiamax;
	  boolean turbo;
	  
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AggiungiMotore window = new AggiungiMotore();
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
	public AggiungiMotore() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmNomeSoftware = new JFrame();     //creazione frame
		frmNomeSoftware.setVisible(true);
		frmNomeSoftware.setTitle("Simulator Times Track");
		frmNomeSoftware.setResizable(false);
		frmNomeSoftware.getContentPane().setBackground(new Color(0, 0, 0));
		frmNomeSoftware.setBounds(100, 100, 726, 462);
		frmNomeSoftware.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmNomeSoftware.getContentPane().setLayout(null);
		
		button = new JButton("<-- INDIETRO"); 			//creazione bottone INDIETRO con relative azioni
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {         //al click di INDIETRO si chiude il frame in uso e si apre il frame di EDITOR
				frmNomeSoftware.dispose();
				Editor e = new Editor();
			}
		});
		button.setForeground(new Color(255, 69, 0));
		button.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		button.setBounds(10, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(button);
		
		button_1 = new JButton("HOME");					//creazione bottone HOME con relative azioni
		button_1.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseClicked(MouseEvent arg0) {			//al click di HOME si chiude il frame in uso e si apre il frame di HOME
				frmNomeSoftware.dispose();
				Home h = new Home();
			}
		});
		button_1.setForeground(new Color(255, 69, 0));
		button_1.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		button_1.setBounds(196, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(button_1);
		
		final JComboBox comboBox = new JComboBox();                                   //creazione combobox per sapere sei il motore ha il turbo oppure no
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"No", "Si"}));
		comboBox.setBounds(472, 179, 101, 25);
		frmNomeSoftware.getContentPane().add(comboBox);
		
		final JButton btnConferma = new JButton("CONFERMA"); 			//creazione bottone HOME con relative azioni		
		btnConferma.setVisible(false);
		btnConferma.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) 
			{
			 
				//accesso al database
				
				Database database = new Database();
				
				database.caricadriver();
				database.collegati();	
				database.setDati_CurvaMotore(motore);	 				//setto i dati della curva del motore tramite i dati inseriti in motore		
				database.disconettiti();	
				
				frmInserimento();										//JOptionPane di conferma inserimento
				frmNomeSoftware.dispose();
				Editor e = new Editor();
				
			}
		});
		btnConferma.setForeground(new Color(255, 69, 0));
		btnConferma.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnConferma.setBounds(534, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(btnConferma);
		
		//creazione vari label
		
		final JLabel lblInserisciIDati = new JLabel("INSERISCI I DATI DEL MOTORE:");
		lblInserisciIDati.setForeground(new Color(255, 69, 0));
		lblInserisciIDati.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblInserisciIDati.setBounds(10, 11, 237, 29);
		frmNomeSoftware.getContentPane().add(lblInserisciIDati);
		
	    JLabel label = new JLabel("Nome");
		label.setForeground(new Color(255, 69, 0));
		label.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		label.setBounds(10, 73, 71, 29);
		frmNomeSoftware.getContentPane().add(label);
		
		JLabel lblCilindrata = new JLabel("Cilindrata [cm^3]");
		lblCilindrata.setForeground(new Color(255, 69, 0));
		lblCilindrata.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblCilindrata.setBounds(10, 127, 101, 29);
		frmNomeSoftware.getContentPane().add(lblCilindrata);
		
		final JLabel label_2 = new JLabel("");
		label_2.setForeground(new Color(255, 69, 0));
		label_2.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		label_2.setBounds(10, 312, 86, 29);
		frmNomeSoftware.getContentPane().add(label_2);
		
		final JLabel label_1 = new JLabel("");
		label_1.setForeground(new Color(255, 69, 0));
		label_1.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		label_1.setBounds(247, 312, 86, 29);
		frmNomeSoftware.getContentPane().add(label_1);
		
		JLabel lblCorsa = new JLabel("Corsa [mm]");
		lblCorsa.setForeground(new Color(255, 69, 0));
		lblCorsa.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblCorsa.setBounds(10, 175, 101, 29);
		frmNomeSoftware.getContentPane().add(lblCorsa);
		
		JLabel lblAlesaggio = new JLabel("Alesaggio [mm]");
		lblAlesaggio.setForeground(new Color(255, 69, 0));
		lblAlesaggio.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblAlesaggio.setBounds(10, 226, 101, 29);
		frmNomeSoftware.getContentPane().add(lblAlesaggio);
		
		JLabel lblCilindri = new JLabel("Cilindri");
		lblCilindri.setForeground(new Color(255, 69, 0));
		lblCilindri.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblCilindri.setBounds(361, 226, 101, 29);
		frmNomeSoftware.getContentPane().add(lblCilindri);
		
		JLabel lblPotenzaMax = new JLabel("Potenza max [kW]");
		lblPotenzaMax.setForeground(new Color(255, 69, 0));
		lblPotenzaMax.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblPotenzaMax.setBounds(361, 73, 101, 29);
		frmNomeSoftware.getContentPane().add(lblPotenzaMax);
		
		JLabel lblCoppiaMax = new JLabel("Coppia max [Nm]");
		lblCoppiaMax.setForeground(new Color(255, 69, 0));
		lblCoppiaMax.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblCoppiaMax.setBounds(361, 127, 101, 29);
		frmNomeSoftware.getContentPane().add(lblCoppiaMax);
		
		JLabel lblTurbo = new JLabel("Turbo");
		lblTurbo.setForeground(new Color(255, 69, 0));
		lblTurbo.setFont(new Font("Trebuchet MS", Font.BOLD, 11));
		lblTurbo.setBounds(361, 175, 101, 29);
		frmNomeSoftware.getContentPane().add(lblTurbo);
		
		final JLabel lblInserisciIPunti = new JLabel("INSERISCI I PUNTI RPM [g/min] - COPPIA [Nm]");
		lblInserisciIPunti.setForeground(new Color(255, 69, 0));
		lblInserisciIPunti.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblInserisciIPunti.setBounds(10, 266, 350, 29);
		lblInserisciIPunti.setVisible(false);
		frmNomeSoftware.getContentPane().add(lblInserisciIPunti);
		
		//creazione vari textfield
		
		JTextField textField_Nome = new JTextField();
		textField_Nome.setName("Nome");
		textField_Nome.setBounds(114, 73, 237, 29);		
		textField_Nome.setColumns(10);
		campi.add(textField_Nome);
		frmNomeSoftware.getContentPane().add(textField_Nome);
		
		JTextField textField_Cilindrata = new JTextField();
		textField_Cilindrata.setName("Cilindrata");
		textField_Cilindrata.setColumns(10);
		textField_Cilindrata.setBounds(114, 127, 237, 29);
		textField_Cilindrata.addKeyListener(new KeyAdapterNumbersOnly());           //aggiunta KeyAdapter per il text field (numeri)
		campi.add(textField_Cilindrata);
		frmNomeSoftware.getContentPane().add(textField_Cilindrata);
		
		
		JTextField textField_Corsa = new JTextField();
		textField_Corsa.setName("Corsa");
		textField_Corsa.setColumns(10);
		textField_Corsa.setBounds(114, 177, 237, 29);
		textField_Corsa.addKeyListener(new KeyAdapterNumbersPuntoOnly());           //aggiunta KeyAdapter per il text field (numeri e virgola)
		campi.add(textField_Corsa);
		frmNomeSoftware.getContentPane().add(textField_Corsa);
		
		
		JTextField textField_Alesaggio = new JTextField();
		textField_Alesaggio.setName("Alesaggio");
		textField_Alesaggio.setColumns(10);
		textField_Alesaggio.setBounds(114, 226, 237, 29);
		textField_Alesaggio.addKeyListener(new KeyAdapterNumbersPuntoOnly());       //aggiunta KeyAdapter per il text field (numeri e virgola)
		campi.add(textField_Alesaggio);
		frmNomeSoftware.getContentPane().add(textField_Alesaggio);
		
		
		JTextField textField_Potmax = new JTextField();
		textField_Potmax.setName("Potenza max");
		textField_Potmax.setColumns(10);
		textField_Potmax.setBounds(468, 73, 237, 29);
		textField_Potmax.addKeyListener(new KeyAdapterNumbersPuntoOnly());          //aggiunta KeyAdapter per il text field (numeri e virgola)
		campi.add(textField_Potmax);
		frmNomeSoftware.getContentPane().add(textField_Potmax);
		
		
		JTextField textField_Coppiamax = new JTextField();
		textField_Coppiamax.setName("Coppia Max");
		textField_Coppiamax.setColumns(10);
		textField_Coppiamax.setBounds(468, 127, 237, 29);
		textField_Coppiamax.addKeyListener(new KeyAdapterNumbersPuntoOnly());        //aggiunta KeyAdapter per il text field (numeri e virgola)
		campi.add(textField_Coppiamax);
		frmNomeSoftware.getContentPane().add(textField_Coppiamax);
		
		
		JTextField textField_Cilindri = new JTextField();
		textField_Cilindri.setName("Cilindri");
		textField_Cilindri.setColumns(10);
		textField_Cilindri.setBounds(468, 226, 237, 29);
		textField_Cilindri.addKeyListener(new KeyAdapterNumbersOnly());               //aggiunta KeyAdapter per il text field (numeri)
		frmNomeSoftware.getContentPane().add(textField_Cilindri);
		campi.add(textField_Cilindri);
		
		JTextField textField_Coppia = new JTextField();
		textField_Coppia.setName("Coppia");
		textField_Coppia.setBorder(null);
		textField_Coppia.setBackground(new Color(0, 0, 0));
		textField_Coppia.setEditable(false);
		textField_Coppia.setColumns(10);
		textField_Coppia.setBounds(328, 312, 125, 29);
		textField_Coppia.addKeyListener(new KeyAdapterNumbersPuntoOnly());            //aggiunta KeyAdapter per il text field (numeri e virgola)
		campi.add(textField_Coppia);
		frmNomeSoftware.getContentPane().add(textField_Coppia);
		
		JTextField textField_Rpm = new JTextField();
		textField_Rpm.setName("Rpm");
		textField_Rpm.setBorder(null);
		textField_Rpm.setBackground(new Color(0, 0, 0));
		textField_Rpm.setEditable(false);
		textField_Rpm.setColumns(10);
		textField_Rpm.setBounds(97, 312, 125, 29);
		textField_Rpm.addKeyListener(new KeyAdapterNumbersOnly());                 //aggiunta KeyAdapter per il text field (numeri)
		campi.add(textField_Rpm);
		frmNomeSoftware.getContentPane().add(textField_Rpm);
		
		final JButton btnConfermanValori = new JButton("Aggiungi punto");          //creazione bottone AGGIUNGI PUNTO con relative azioni
		btnConfermanValori.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {                                            //al click di AGGIUNGI PUNTO viene aggiunto un nuovo punto nella curva motore. Questa � una operazione eseguibile n volte in base a quanti punti si vogliono inserire
				
				if((!campi.get(7).getText().isEmpty())&&(!campi.get(8).getText().isEmpty()))  //controllo campi vuoti
				{
				 //coverto i valori, passati nelle textfield, di tipo String in Double/Integer
					
				coppia=Double.parseDouble(campi.get(7).getText());
				rpm=Integer.parseInt(campi.get(8).getText());
				
				
				if(rpm>=2000&&motore.getCurva().size()==0)
				{
					frmMinRpm();
				}
				else if(coppia==0)
				{
					frmCoppiaNull();
				}
					
				else
				{
					if(rpm <= rpm_precedente && motore.getCurva().size()!=0)
					{
						rpmPrec();
					}
					else
					{
					motore.setCurva(rpm, coppia); 													   //inserisco il nuovo punto (coppia-rpm) nell'oggetto motore
					campi.get(8).setText("");														   //svuoto la textfield
					campi.get(7).setText("");															//svuoto la textfield
					
					rpm_precedente = rpm;			
					frmConferma();  											//JOptionPane di conferma
					}
				}
				}
				else
				frmErroreGen("Inserire i valori nei campi 'Coppia' ed 'Rpm'");
			}
		});
		btnConfermanValori.setVisible(false);
		btnConfermanValori.setBounds(472, 309, 131, 35);
		frmNomeSoftware.getContentPane().add(btnConfermanValori);
		
		final JButton btnNewButton = new JButton("Fine");               //creazione del tasto FINE con relative azioni
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {					//al click del tasto FINE vengono confermati gli n punti inseriti tramite il bottone AGGIUNGI PUNTO 
				
				if(motore.getCurva().size()>=5)							//controllo che i punti aggiunti per il nuovo motore siano almeno 2
				{
					
				btnConferma.setVisible(true);
				btnConfermanValori.setVisible(false);
				frmFine();												//JOptionPane di conferma
				
				}
				else
				{
					
				 frmMin();												//JOptionPane di errore
				 
				}
			}
		});
		btnNewButton.setVisible(false);
		btnNewButton.setBounds(613, 309, 86, 35);
		frmNomeSoftware.getContentPane().add(btnNewButton);
		
		
		
		final JButton btnConfermaValori = new JButton("Aggiungi motore");        //creazione del tasto AGGIUNGI MOTORE con relative azion
		btnConfermaValori.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {							 //al click di AGGIUNGI MOTORE viene aggiunto il motore appena creato passando cos� all'aggiunta dei punti
				
						
				//if(!(campi.get(3).getText().isEmpty())&&(!campi.get(1).getText().isEmpty())&&(!campi.get(6).getText().isEmpty())&&(!campi.get(5).getText().isEmpty())&&(!campi.get(2).getText().isEmpty())&&(campi.get(0).getText().isEmpty())&&(!campi.get(4).getText().isEmpty()))    //controllo che i campi non siano vuoti
				//{
					if(controllo_motore()){						                           //controllo che i campi inseriti non siano diversi dall'intervallo limite
						
						  
						
						//coverto i valori, passati nelle textfield, di tipo String in Double/Integer
						
						alesaggio = Double.parseDouble(campi.get(3).getText());
						cilindrata = Integer.parseInt(campi.get(1).getText());
						cilindri = Integer.parseInt(campi.get(6).getText());
						coppiamax = Double.parseDouble(campi.get(5).getText());
						corsa = Double.parseDouble(campi.get(2).getText());
						potmax = Double.parseDouble(campi.get(4).getText());
						
						//passaggio dei vari valori inseriti all'interno delle textfield o scelti nelle combobox all'oggetto motore
						
						motore.setNomeMotore(campi.get(0).getText());
						motore.setAlesaggio(alesaggio);						
						motore.setCilindrata(cilindrata);
						motore.setCilindri(cilindri);						
						motore.setCoppiamax(coppiamax);
						motore.setCorsa(corsa);
						motore.setPotmax(potmax);
						
						if(comboBox.getSelectedItem()=="No")
							turbo=false;
						else 
							turbo=true;
						
						motore.setTurbo(turbo);
						
						//accesso al database
						
						Database database = new Database();
						database.caricadriver();
						database.collegati();	
						
						database.setDati_Motore(motore); 										//il nuovo motore viene caricato nel database
						
						int id = database.getId_Motore(); 										//viene invocata la funzione che prende l'ID del motore appena inserito
						motore.setId_motore(id);												//viene settato tale ID cos� che � possibile aggiungere i punti della curva del motore 
						
						database.disconettiti();
						
						frmConfermaMotore();													//JOptionPane di conferma
						 
						//vari settaggi grafici che si hanno al click di AGGIUNGI MOTORE
						
						campi.get(7).setEditable(true);
						campi.get(7).setBackground(Color.WHITE);
						label_1.setText("Coppia [Nm]");
						
						campi.get(8).setEditable(true);
						campi.get(8).setBackground(Color.WHITE);
						label_2.setText("RPM [giri/m]");
						
						
						button.setEnabled(false);
						button.setVisible(false);
						button_1.setEnabled(false);
						button_1.setVisible(false);

						btnConfermaValori.setVisible(false);
						
						btnConfermanValori.setVisible(true);
						
						campi.get(3).setEditable(false);
						campi.get(1).setEditable(false);
						campi.get(6).setEditable(false);
						campi.get(5).setEditable(false);
						campi.get(2).setEditable(false);
						campi.get(0).setEditable(false);
						campi.get(4).setEditable(false);
						comboBox.setEnabled(false);
						
						lblInserisciIPunti.setVisible(true);
						btnNewButton.setVisible(true);
						
						 
//					}
//					else	
//						frmInserimentoErrato();
				}
				
			//	frmErroreGen("");
			}
		});
		btnConfermaValori.setBounds(311, 266, 142, 23);
		frmNomeSoftware.getContentPane().add(btnConfermaValori);
		
		
		
		
	}
	public class KeyAdapterNumbersOnly extends KeyAdapter {            //KeyAdapter che permette di accettare solamente l'inserimento di numeri all'interno delle textfield

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
	
	public class KeyAdapterNumbersPuntoOnly extends KeyAdapter {       //KeyAdapter che permette di accettare solamente l'inserimento di numeri e punti all'interno delle textfield

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
	


boolean controllo_motore(){                         //controllo limiti di inserimento
	
	for (int i = 0; i <= 6; i++) 
	{
		JTextField parziale = campi.get(i);
		
		if(parziale.getText().isEmpty())
		{
			frmErroreGen("Il campo '"+ parziale.getName() + "' � vuoto.");
			return false;			
		}
				else if(i==1)
		{
			if(!(Integer.parseInt(parziale.getText())>=1000&&Integer.parseInt(parziale.getText())<=9000))
			{		
			frmErroreGen("La cilindrata deve essere compresa tra 1000 e 9000.");
			return false;
			}
			
		}			
		
		else if(i==2)
		{
			if(!(Double.parseDouble(parziale.getText())>20&&Double.parseDouble(parziale.getText())<120.0))
			{		
			frmErroreGen("Il valore del campo corsa non pu� superare 120.");
			return false;	
			}
		}
		else if(i==3)
		{
			if(!(Double.parseDouble(parziale.getText())>20&&Double.parseDouble(parziale.getText())<120.0))
			{		
			frmErroreGen("Il valore del campo alesaggio non pu� superare 120.");
			return false;
			}
			
		}
        else if(i==4)
        {
			if(!(Double.parseDouble(parziale.getText())>=20.0&&Double.parseDouble(parziale.getText())<=1000.0))
			{		
			frmErroreGen("La potenza max deve essere compreso tra 20 e 1000.");
			return false;
		    }
        }
        else if(i==5)
        {
        	if(!(Double.parseDouble(parziale.getText())>=50.0&&Double.parseDouble(parziale.getText())<=1700.0))
			{		
			frmErroreGen("La coppia max deve essere compreso tra 50 e 1700.");
			return false;	
			}
        }
        else if(i==6)
        {
			if(!(Integer.parseInt(parziale.getText())>=4&&Integer.parseInt(parziale.getText())<=16))
			{		
			frmErroreGen("I cilindri del motore devono essere compresi tra 4 e 16.");
			return false;	
			}
		}
	}
	return true;
	

}

//Creazione dei JOptionPane (Conferma/Errore)

private void frmErroreGen(String messaggio){
				JOptionPane.showMessageDialog(frmNomeSoftware,
						    messaggio,
						    "Errore",
						    JOptionPane.ERROR_MESSAGE);
			}


private void frmConferma(){
	JOptionPane.showMessageDialog(frmNomeSoftware,
		    "Punto aggiunto.",
		    "Info",
		    JOptionPane.INFORMATION_MESSAGE
		    );		
}

private void frmFine(){
	JOptionPane.showMessageDialog(frmNomeSoftware,
		    "Punti salvati correttamente, premere il tasto CONFERMA per memorizzare i dati nel database.",
		    "Info",
		    JOptionPane.INFORMATION_MESSAGE
		    );		
}

private void frmMin(){
	JOptionPane.showMessageDialog(frmNomeSoftware,
		    "Inserire minimo cinque punti",
		    "Errore",
		    JOptionPane.ERROR_MESSAGE
		    );				
}

private void rpmPrec(){
	JOptionPane.showMessageDialog(frmNomeSoftware,
		    "Il valore di 'RPM' deve essere maggiore rispetto al valore inserito in precedenza.",
		    "Errore",
		    JOptionPane.ERROR_MESSAGE
		    );				
}

private void frmMinRpm (){
	JOptionPane.showMessageDialog(frmNomeSoftware,
		    "Il valore di RPM per il primo punto non pu� essere superiore a 2000 g/min",
		    "Errore",
		    JOptionPane.ERROR_MESSAGE
		    );		
}

private void frmCoppiaNull(){
	JOptionPane.showMessageDialog(frmNomeSoftware,
		    "Il valore della coppia non pu� essere uguale a zero",
		    "Errore",
		    JOptionPane.ERROR_MESSAGE
		    );		
	
			
}
private void frmConfermaMotore(){
	JOptionPane.showMessageDialog(frmNomeSoftware,
		    "Motore inserito correttamente nel database",
		    "Info",
		    JOptionPane.INFORMATION_MESSAGE
		    );		
			
}

private void frmInserimento(){
	JOptionPane.showMessageDialog(frmNomeSoftware,
		    "Tutti i dati sono stati inseriti correttamente nel database.",
		    "Info",
		    JOptionPane.INFORMATION_MESSAGE
		    );		
			
}






	
}
	
	
	
	

	

