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

import javax.swing.*;


public class ProvaJOptionPane
{
public static void main(String[] args)
{
String messaggio = JOptionPane.showInputDialog(null, "Inserisci un messaggio", "Inserisci una stringa", JOptionPane.PLAIN_MESSAGE);
int conferma = JOptionPane.showConfirmDialog(null, "Seleziona s�, no o cancella", "Scegli un'opzione", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
String output = "Hai scritto: \"" + messaggio + "\" ed hai cliccato sul pulsante: " + conferma;
JOptionPane.showMessageDialog(null, output);
System.exit(0);
}
}