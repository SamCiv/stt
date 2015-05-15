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

import giocatore.Giocatore;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPanel;




import campionato.Campionato;

import java.util.ArrayList;

import javax.swing.JTabbedPane;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import java.awt.Color;
import java.awt.Font;


public class Gara_GUI {

	private JFrame frmNomeSoftware;
	private JTable tabella_tempi;
	private JButton btn_Start;
	private JButton btnPausa;
	private Campionato campionato = null;
	
	private JTabbedPane telemetria = null;
	//private TabellaTempi tab;
	private ArrayList<Giocatore> partecipanti = null;
	private JScrollPane scrollPane = new JScrollPane();

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gara_GUI window = new Gara_GUI(null);
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
	//public Gara_GUI(TabellaTempi tab)
	public Gara_GUI(Campionato campionato){
		// TODO Auto-generated constructor stub
		this.campionato = campionato;
		
		//this.setPartecipanti(arrayList);
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @param tab 
	 */
	private void initialize() {
		
		frmNomeSoftware = new JFrame();
		frmNomeSoftware.getContentPane().setBackground(Color.BLACK);
		frmNomeSoftware.setResizable(false);
		frmNomeSoftware.setVisible(true);
		frmNomeSoftware.setTitle("Simulator Times Track");
		frmNomeSoftware.setBounds(100, 100, 1115, 482);
		frmNomeSoftware.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNomeSoftware.getContentPane().setLayout(null);
		
		//ScrollPane che contiene la tabella dei tempi
		
		scrollPane.setBounds(10, 11, 570, 377);
		frmNomeSoftware.getContentPane().add(scrollPane);
		
		
		ListenersTabella listener = new ListenersTabella(this); //creo il listener e gli passo il frame
		
		//Tasto che serve per far iniziare la gara
		setBtn_Start(new JButton("Start Gara")); 		
		getBtn_Start().setActionCommand("gara");
		getBtn_Start().addActionListener(listener);
		getBtn_Start().setBounds(526, 409, 134, 23);
		frmNomeSoftware.getContentPane().add(getBtn_Start());
		
				
		telemetria = new JTabbedPane(JTabbedPane.TOP);
		telemetria.setBounds(590, 11, 509, 377);
    	frmNomeSoftware.getContentPane().add(telemetria);
		
		
		
		btnPausa = new JButton("Pausa");
		btnPausa.setActionCommand("pausa");
		//btnPausa.addActionListener(listener);
		btnPausa.setBounds(322, 361, 89, 23);
		btnPausa.setEnabled(false);
	//	frmNomeSoftware.getContentPane().add(btnPausa);
		
	}
	
	
    public void setJTabellaTempi(TabellaTempi modello) 
    {
    	this.tabella_tempi = new JTable(modello);
    	
    	RenderTabella renderer = new RenderTabella();
        this.tabella_tempi.setDefaultRenderer(Object.class, renderer);
    	
    	scrollPane.setViewportView(this.tabella_tempi);
    }
  
    
	public JButton getBtn_Start() {
		return btn_Start;
	}

	public void setBtn_Start(JButton btn_Start) {
		this.btn_Start = btn_Start;
		btn_Start.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
		btn_Start.setForeground(new Color(255, 69, 0));
	}

	
	public void frmFine(String nome, String cognome)
	{
		
			
		int input = JOptionPane.showOptionDialog(null, "La gara � terminata, il vincitore � "+ nome+ " "+ cognome + "! Fare clic su OK per continuare.", "Info", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
		
		if(input == JOptionPane.OK_OPTION)
		{
			RiepilogoCampionato riepilogo = new RiepilogoCampionato(this.campionato);
			this.frmNomeSoftware.dispose();			
		}
		
	}

	public ArrayList<Giocatore> getPartecipanti() {
		return partecipanti;
	}

	public void setPartecipanti(ArrayList<Giocatore> partecipanti) {
		this.partecipanti = partecipanti;
	}

	public JButton getBtnPausa() {
		return btnPausa;
	}

	public void setBtnPausa(JButton btnPausa) {
		this.btnPausa = btnPausa;
	}

		
	public Campionato getCampionato() {
		return campionato;
	}

	public void setCampionato(Campionato campionato) {
		this.campionato = campionato;
	}

	public void chiudiFinestra(){
		this.frmNomeSoftware.dispose();
	}

	public JTabbedPane getTelemetria() {
		return telemetria;
	}

	public void setTelemetria(JTabbedPane telemetria) {
		this.telemetria = telemetria;
	}
	
	
}
