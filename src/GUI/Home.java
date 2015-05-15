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

import javax.sound.midi.ControllerEventListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JTable;

import Database.Database;

public class Home {

	private JFrame frmNomeSoftware;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Home window = new Home();
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
	public Home() {
		controlloDatabase();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNomeSoftware = new JFrame();
		frmNomeSoftware.setVisible(true);
		frmNomeSoftware.setTitle("Simulator Times Track");
		frmNomeSoftware.setResizable(false);
		frmNomeSoftware.getContentPane().setBackground(new Color(0, 0, 0));
		frmNomeSoftware.setBounds(100, 100, 726, 462);
		frmNomeSoftware.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNomeSoftware.getContentPane().setLayout(null);
		
		JLabel lblTitolo = new JLabel("Simulator Times Track");
		lblTitolo.setForeground(new Color(255, 69, 0));
		lblTitolo.setFont(new Font("Leelawadee", Font.BOLD, 50));
		lblTitolo.setBounds(82, 11, 565, 87);
		frmNomeSoftware.getContentPane().add(lblTitolo);
		
		JLabel lblV = new JLabel("version 1.0");
		lblV.setFont(new Font("Leelawadee", Font.BOLD, 14));
		lblV.setForeground(Color.RED);
		lblV.setBounds(620, 409, 73, 14);
		frmNomeSoftware.getContentPane().add(lblV);
		
		JButton btnNewButton = new JButton("GIOCA PARTITA");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Database database =  new Database();
				database.caricadriver();
				database.collegati();
				
				if(database.Popolamento()){
					
				SceltaGara tipologia = new SceltaGara();
				
				frmNomeSoftware.dispose();
				}
				else
					frmErrore();
			}
		});
		btnNewButton.setForeground(new Color(255, 69, 0));
		btnNewButton.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnNewButton.setBounds(225, 109, 238, 42);
		frmNomeSoftware.getContentPane().add(btnNewButton);
		
		JButton button = new JButton("EDITOR");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frmNomeSoftware.dispose();
				Editor e = new Editor();
			}
		});
		button.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		button.setForeground(new Color(255, 69, 0));
		button.setBounds(225, 202, 238, 42);
		frmNomeSoftware.getContentPane().add(button);
		
		JButton button_1 = new JButton("ESCI");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frmNomeSoftware.dispose();
				
			}
		});
		button_1.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		button_1.setForeground(new Color(255, 69, 0));
		button_1.setBounds(225, 292, 238, 42);
		frmNomeSoftware.getContentPane().add(button_1);
	}
	
	void frmErrore(){   //JOptionPane di errore
		JOptionPane.showMessageDialog(frmNomeSoftware,
				    "Database non popolato. Impossibile iniziare una nuova gara. Popolare il database tramite l'editor.",
				    "DATABASE VUOTO",
				    JOptionPane.ERROR_MESSAGE);
	}
	
	
	void controlloDatabase()
	{
		InputStream controllo = null;
		try {
			controllo = new FileInputStream(new File("database.db"));
		} catch (FileNotFoundException e2) 
		{
			// TODO Auto-generated catch block
			controllo = null;
		}	
		
		if(controllo==null)
		{		
		InputStream stream =  Home.class.getResourceAsStream("database.db");//ClassLoader.class.getResourceAsStream("database.db");//Home.class.getResourceAsStream("/database.db");//note that each / is a directory down in the "jar tree" been the jar the root of the tree"
		
			if (stream == null) {
		        //send your exception or warning
		    }
		    OutputStream resStreamOut = null;
		    int readBytes;
		    byte[] buffer = new byte[4096];
		    
		    try {
		        resStreamOut = new FileOutputStream(new File("database.db"));
		        while ((readBytes = stream.read(buffer)) > 0) {
		            resStreamOut.write(buffer, 0, readBytes);
		        }
		    } catch (IOException e1) {
		        // TODO Auto-generated catch block
		        e1.printStackTrace();
		    } finally {
		        try {
		        	
					stream.close();
					resStreamOut.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		       
		    }
		}
	}
}
