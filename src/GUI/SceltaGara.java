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
import javax.swing.JButton;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SceltaGara {

	private JFrame frmNomeSoftware;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SceltaGara window = new SceltaGara();
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
	public SceltaGara() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNomeSoftware = new JFrame();
		frmNomeSoftware.setVisible(true);
		frmNomeSoftware.setResizable(false);
		frmNomeSoftware.getContentPane().setBackground(new Color(0, 0, 0));
		frmNomeSoftware.setTitle("Simulator Times Track");
		frmNomeSoftware.setBounds(100, 100, 726, 462);
		frmNomeSoftware.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNomeSoftware.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("GARA SINGOLA");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				sceltagiocatori(true);
			}			
		});
		btnNewButton.setForeground(new Color(255, 69, 0));
		btnNewButton.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnNewButton.setBounds(247, 115, 212, 42);
		frmNomeSoftware.getContentPane().add(btnNewButton);
		
		JButton btnCampionato = new JButton("CAMPIONATO");
		btnCampionato.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				sceltagiocatori(false);
			}
		});
		btnCampionato.setForeground(new Color(255, 69, 0));
		btnCampionato.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnCampionato.setBounds(247, 216, 212, 42);
		frmNomeSoftware.getContentPane().add(btnCampionato);
		
		JButton button = new JButton("<-- INDIETRO");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				Home home = new Home();
				frmNomeSoftware.dispose();
				
			}
		});
		button.setForeground(new Color(255, 69, 0));
		button.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		button.setBounds(10, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(button);
		
		JButton btnHome = new JButton("HOME");
		btnHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frmNomeSoftware.dispose();
				Home h = new Home();
			}
		});
		btnHome.setForeground(new Color(255, 69, 0));
		btnHome.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		btnHome.setBounds(196, 388, 176, 35);
		frmNomeSoftware.getContentPane().add(btnHome);
	}

	protected void sceltagiocatori(boolean gara) {
		NumeroGiocatori scelta_giocatori = new NumeroGiocatori(gara);
		frmNomeSoftware.dispose();
	}

}
