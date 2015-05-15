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
package Circuito;

import java.io.*;
import java.nio.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Database.Database;

public class Importa_Circuito {
	
	private ArrayList<Segmento> segmenti_circuito = null;
	
	private double start = 0;
	
	private Punto finishLine = new Punto();
    
	private int segmenti = 0;
	
	private String nome_circuito;
	private String nazione;
	
	private boolean radianti = true;

public void Importa_File (String nome_file) 
{	
		Path path = Paths.get(nome_file);
		newFile2(path);
		conversione_segmenti();
}
	
private void newFile2(Path path) {
   
		
	
		Charset set_caratteri = Charset.forName("US-ASCII");
		
      	try(BufferedReader reader = Files.newBufferedReader(path, set_caratteri))
      	{
      		
      		boolean eof=false; //controlla se non ho raggiunto la fine del descrittore dei segmenti
			int contatore=1;  //conta le righe del file man mano che scorre
			//int segmenti=0;  //numero di segmenti della pista
			int i=3; //massima dimensione (in n.caratteri) di segmenti, serve per ciclare il primo while
			
			Segmento segmento_iniziale = new Segmento();
			
			Punto coordinate_iniziali = new Punto();
			
			double angolo_precedente = 0;		
			double finish = 0;
			
						
			String a = new String(); //sotto_stringa della stringa principale line
						
			String line = null; //ogni riga viene memorizzata in questa stringa
			
		    while (((line = reader.readLine().trim()) != null)&&!eof) //cicla fino a quando non si raggiunge la fine del file oppure la fine della descrzione dei segmenti
		    {
		        if(!line.isEmpty()) //non considero le righe vuote
		        {        			    		
			    	while(contatore==1&&this.segmenti==0) //calcolo il numero di segmenti, visto che � il primo valore del file
			    	{
			    		
			    		
			    		if(line.length()>2&&line.contains(" "))
			    			a=line.substring(0,i);
			    		
			    		else if(line.length()<=2&&line.contains(" ")){
			    			a=line.substring(0,i);
			    		}
			    			
			    		else i--;
			    		
			    		
			    		if(!a.contains(" "))
				    		{
				    		this.segmenti = Integer.parseInt(line.substring(0, i));
				    		
				    		this.segmenti_circuito = new ArrayList<Segmento>(segmenti);//inizializzo l'arraylist che conterr� tutti i segmenti del mio circuito
				    		
				    		//System.out.println("segmenti= " + segmenti +"\n");
				    		}
			    		else i--;  			
			    		
			        }
			    	
			    	if(contatore==4) //calcolo il numero di segmenti, visto che � il primo valore del file
			    	{		    					    		
			    		get_Posizione_Iniziale(line, segmento_iniziale); //imposto le coordinate iniziali del segmento iniziale. NB:non ancora conosco la lunghezza del segmento principale		
			    		angolo_precedente = segmento_iniziale.getAng_finale(); //imposto come angolo precedente il valore dell'angolo del segmento iniziale
			    	}	
			    	
			    	if(contatore==9) //calcolo la posizione iniziale della linea di arrivo
			    	{
			    		finish = get_Start(line, finish);
			    	}
			    	
			    	if(contatore>=14&&contatore<(this.segmenti+14)){
			    		
			    		Segmento segmento = new Segmento();			    		
			    		
			    		boolean flag=true;
			    		int c=2;
			    		
			    		while(flag){
			    			
			    		a=line.substring(0,c);
			    		
			    		if(a.contains(" ")||a.contains("\t")){ // \t rappresenta tab
			    			
			    			if(contatore==14)//segmento iniziale
				    			{
			    				
					    			String raggio = line.substring(0, c-1);			    			
					    			segmento_iniziale.setRaggio(Double.parseDouble(raggio));//setto il raggio del segmento iniziale (in teroia 0 visto che � un rettilineo)
					    			
					    			flag=false;
					    			break;
				    			}
			    			else
				    			{			    				    
				    				double raggio = Double.parseDouble(line.substring(0, c-1));	
				    				
					    			segmento.setRaggio(raggio);//setto il raggio del segmento, in caso di rettilineo questo vale zero
					    							    			
					    			flag=false;
					    			break;
				    			}
			    			}
			    		
			    		else c++;
			    		}
			    		
			    		int d=c;
			    		
			    		int lunghezza = 0;
			    		
			    		String linea = new String();
			    		linea=line.substring(d).trim();
			    		lunghezza=linea.length();
			    		boolean overflow=false; //devo gestire il caso in cui d>lunghezza della stringa
			    		
			    		if(d>lunghezza) overflow=true;
			    		
			    		while(!flag)//&&d<=lunghezza)
			    		{			    				
			    		
			    		if(overflow) a=linea.substring(0,d-(d-lunghezza));
			    		else a=linea.substring(0,d);
			    		
			    		if(a.contains(" "))
			    		{
			    			if(contatore==14)//segmento iniziale
			    			{
			    				
					    		String lunghezza_valore = linea.substring(0,d-1);			    			
				    			radianti = segmento_iniziale.setLunghezza(Double.parseDouble(lunghezza_valore), radianti);
				    			
				    			//Conoscendo adesso la lunghezza finale del segmento principale posso calcolarmi le coordinate finali di quest'ultimo.
				    			get_coordinate_finali_rettilineo(segmento_iniziale, angolo_precedente);
				    			
				    			//imposto le coordinate iniziali del prossimo segmento (rettilineo o curva)
				    			coordinate_iniziali = segmento_iniziale.getCoordinate_finali();
				    			
				    			setFinishLine(segmento_iniziale, finish);
				    			
				    			//Aggiungo il segmento iniziale alla lista dei segmenti
				    			this.segmenti_circuito.add(segmento_iniziale);
					    		break;
			    			}
			    			else //sto inserendo un segmento dopo un altro segmento.
			    			{
			    				String lunghezza_valore = linea.substring(0,d-1);			    			
				    			radianti = segmento.setLunghezza(Double.parseDouble(lunghezza_valore), radianti);
				    			
				    			//Devo considerare i tre casi: rettilineo, curva a destra e curva a sinistra
				    			if(segmento.getRaggio()==0)
				    			{
				    			 segmento.setCoordinate_iniziali(coordinate_iniziali);
				    			  //calcolo le coordinate finali del rettilineo conoscendo le coordinate iniziali(relative all'ultimo segmento aggiunto),
				    			  //e l'angolo precedente.
				    			  get_coordinate_finali_rettilineo(segmento, angolo_precedente);
				    			  //imposto le coordinate iniziali del prossimo segmento come coordinate finali del segmento precedente (in questo caso il rettilineo).
				    			  coordinate_iniziali = segmento.getCoordinate_finali();
				    			}
				    			//curva a destra, raggio < 0
				    			else
				    			{				    		
				    			  segmento.setCoordinate_iniziali(coordinate_iniziali);
				    			  get_centro_circonferenza(segmento, coordinate_iniziali, angolo_precedente);	
				    			  coordinate_iniziali = segmento.getCoordinate_finali();
				    			  angolo_precedente = segmento.getAng_finale();
				    			}
				    			
				    			
				    			this.segmenti_circuito.add(segmento);
					    		break;	
			    			}
			    		}
			    		else 
			    			if(d==lunghezza||d>lunghezza)
				    		{
			    			 if(contatore==14)//segmento iniziale
			    			 {
				    			 String lunghezza_valore =linea.substring(0,lunghezza);			    			
					    		 radianti = segmento_iniziale.setLunghezza(Double.parseDouble(lunghezza_valore), radianti);
					    		 
					    		 //Conoscendo adesso la lunghezza finale del segmento principale posso calcolarmi le coordinate finali di quest'ultimo.
					    		 get_coordinate_finali_rettilineo(segmento_iniziale, angolo_precedente);
					    			
					    		 //imposto le coordinate iniziali del prossimo segmento (rettilineo o curva)
					    		 coordinate_iniziali = segmento_iniziale.getCoordinate_finali();
					    		 
					    		 setFinishLine(segmento_iniziale, finish);
					    			
					    		 //Aggiungo il segmento iniziale alla lista dei segmenti
					    		 this.segmenti_circuito.add(segmento_iniziale);
						    	 break;
					    	 }
			    			 else 
			    			 {
			    				 String lunghezza_valore =linea.substring(0,lunghezza);			    			
					    		 radianti = segmento.setLunghezza(Double.parseDouble(lunghezza_valore), radianti);
					    		 if(segmento.getRaggio()==0)
					    			{
					    			  segmento.setCoordinate_iniziali(coordinate_iniziali);
					    			  //calcolo le coordinate finali del rettilineo conoscendo le coordinate iniziali(relative all'ultimo segmento aggiunto),
					    			  //e l'angolo precedente.
					    			  get_coordinate_finali_rettilineo(segmento, angolo_precedente);
					    			  //imposto le coordinate iniziali del prossimo segmento come coordinate finali del segmento precedente (in questo caso il rettilineo).
					    			  coordinate_iniziali = segmento.getCoordinate_finali();
					    			}
					    			//curva
					    			else
					    			{				    			  
					    			  
					    			  get_centro_circonferenza(segmento, coordinate_iniziali, angolo_precedente);	
					    			  coordinate_iniziali = segmento.getCoordinate_finali();
					    			  angolo_precedente = segmento.getAng_finale();
					    			}
					    		 this.segmenti_circuito.add(segmento);
						    	 break;
							 }
				    		}
			    		else d++;
			    		
			    		}
			    	}
			    	
			        contatore++;
			        
			    	if(contatore>=(14+this.segmenti)) break;
		    	
		    	} 		  
		   
		   }
		    
		  }
      	
		  
		   
	  catch (IOException e) {
	    e.printStackTrace();
		  }
      
} 

private void setFinishLine(Segmento segmento_iniziale, double finish) //calcola le coordinate della linea di arrivo/partenza
{
	
	double finish_x = segmento_iniziale.getCoordinate_iniziali().getX() +  finish * segmento_iniziale.getLunghezza() * Math.cos(segmento_iniziale.getAng_iniziale());
	double finish_y = segmento_iniziale.getCoordinate_iniziali().getY() +  finish * segmento_iniziale.getLunghezza() * Math.sin(segmento_iniziale.getAng_iniziale());
	
	this.finishLine.setX(finish_x);
	this.finishLine.setY(finish_y);
}


private void get_centro_circonferenza(Segmento segmento, Punto coordinate_iniziali, double angolo_precedente) {
	
	Punto centro_circonferenza = new Punto();
	Punto coordinate_finali = new Punto();
	
	segmento.setCoordinate_iniziali(coordinate_iniziali);
	segmento.setAng_iniziale(angolo_precedente);
		
	if(segmento.getRaggio()<0)
	{	
		//Calcolo le coordinate (x,y) del centro della circonferenza
		double x = coordinate_iniziali.getX() - segmento.getRaggio() * Math.sin(segmento.getAng_iniziale());
		double y = coordinate_iniziali.getY() + segmento.getRaggio() * Math.cos(segmento.getAng_iniziale());
		
		centro_circonferenza.setX(x);
		centro_circonferenza.setY(y);
		
		//calcolo il nuovo valore dell'angolo finale della curva
		double angolo = angolo_precedente - segmento.getLunghezza();
		
		if(angolo < -2.0*Math.PI) angolo = angolo + 2.0*Math.PI;
		
//		if(angolo < -2.0*Math.PI) angolo = (angolo*Math.PI)/180;
		
		segmento.setCentro_circonferenza(centro_circonferenza);
		
		segmento.setAng_finale(angolo);
		
	}
	else // curva a sinistra
	{
		//Calcolo le coordinate (x,y) del centro della circonferenza
		double x = coordinate_iniziali.getX() - segmento.getRaggio() * Math.sin(segmento.getAng_iniziale());
		double y = coordinate_iniziali.getY() + segmento.getRaggio() * Math.cos(segmento.getAng_iniziale());
		
		centro_circonferenza.setX(x);
		centro_circonferenza.setY(y);
		
		//calcolo il nuovo valore dell'angolo finale della curva
		double angolo = angolo_precedente + segmento.getLunghezza();
		
		if(angolo > 2.0*Math.PI) angolo = angolo - 2.0*Math.PI;
		
//		if(angolo > 2.0*Math.PI) angolo = (angolo*Math.PI)/180;
		
		segmento.setCentro_circonferenza(centro_circonferenza);
		
		segmento.setAng_finale(angolo);
	}
	
	//calcolo le coordinate finali della curva
	double x_fin = segmento.getCentro_circonferenza().getX() + segmento.getRaggio()*Math.sin(segmento.getAng_finale());
	double y_fin = segmento.getCentro_circonferenza().getY() - segmento.getRaggio()*Math.cos(segmento.getAng_finale());
	
	//imposto le coordinate finali
	coordinate_finali.setX(x_fin);
	coordinate_finali.setY(y_fin);
	
	segmento.setCoordinate_finali(coordinate_finali);
    
  }
	





//Calcola le coordinate finali del rettilineo ed assegno l'angolo
private void get_coordinate_finali_rettilineo(Segmento segmento, double angolo) 
{
	
	  Punto coordinate_finali = new Punto();
	   
	  //l'angolo � uguale all'angolo dell'elemento precedente, nel caso del segmento principale sovrascrivo il valore con se stesso.
	  
	  segmento.setAng_iniziale(angolo);
	  segmento.setAng_finale(angolo);
	  
	  
	  
	  //calcolo le coordinate finali del mio rettilineo
	  double x_finale = segmento.getCoordinate_iniziali().getX() + segmento.getLunghezza() * Math.cos(segmento.getAng_finale());
	  double y_finale = segmento.getCoordinate_iniziali().getY() + segmento.getLunghezza() * Math.sin(segmento.getAng_finale());
	  
	  //prova calcolo coeff.angolare retta segmento. 
	  segmento.setM((y_finale-segmento.getCoordinate_iniziali().getY())/((x_finale-segmento.getCoordinate_iniziali().getX())));
	  
	  
	  coordinate_finali.setX(x_finale);
	  coordinate_finali.setY(y_finale);
	  
	  segmento.setCoordinate_finali(coordinate_finali);//imposto le coordinate finali appena calcolate
	  
}

private String getEstensioneFile(String nomeFile){
    return nomeFile.substring(nomeFile.lastIndexOf("."));
}


private double get_Start(String line, double finish)
{
	
	boolean flag=true;
	
	String a = new String();
	
	int c=2;
	
	while(flag) // primo ciclo serve per ricavare la posizione iniziale di x
	{
		
	a=line.substring(0,c);
	
	if(a.contains(" ")||a.contains("\t")){ // \t rappresenta tab
		
		String val_x = line.substring(0, c-1);			    			
		
		finish = (Double.parseDouble(val_x));
		
		flag=false;
		
		break;
	}
	else c++;
	}	
	
	return finish;
}

private void get_Posizione_Iniziale(String line, Segmento segmento_iniziale){
	
	boolean x=true;
	boolean y=false;
	boolean angolo = false;
	
	Punto coordinate_iniziali = new Punto();
	
	String a = new String();
	
	int c=2;
	
	while(x) // primo ciclo serve per ricavare la posizione iniziale di x
	{
		
	a=line.substring(0,c);
	
	if(a.contains(" ")||a.contains("\t")){ // \t rappresenta tab
		
		String val_x = line.substring(0, c-1);			    			
		coordinate_iniziali.setX(Double.parseDouble(val_x));
		
		x=false;
		y=true;
		
		break;
	}
	
	else c++;
	}
	
	line=line.substring(c,line.length());
	
	line=line.trim();	
	
	c=2;
	
	while(y) // secondo ciclo serve per ricavare la posizione iniziale di y
	{			
		a=line.substring(0,c);
		
		if(a.contains(" ")||a.contains("\t")){ // \t rappresenta tab
			
			String val_y = a.substring(0, c-1);			    			
			coordinate_iniziali.setY(Double.parseDouble(val_y));
			
			y=false;
			angolo=true;
			
			break;
			}
		
		else c++;
		
	}
	
	line=line.substring(c,line.length());
	
	line=line.trim();
	
	c=2;
	
	while(c>line.length()) c--;
	
	while(angolo) // terzo ciclo serve per ricavare l'angolo finale del primo elemento
	{
	
		a=line.substring(0,c);
		
		if(a.contains(" ")||a.contains("\t")){ // \t rappresenta tab
			
			String ang = line.substring(0, c-1);			    			
			segmento_iniziale.setAng_finale(Double.parseDouble(ang)); //imposto il valore dell'angolo iniziale
			
			x=false;
			y=true;
			
			break;
			}
		else if(line.length()==c) //caso in cui la striga ha lo stesso valore di c
		{
			String ang = a;			    			
			segmento_iniziale.setAng_finale(Double.parseDouble(ang)); //imposto il valore dell'angolo iniziale
			
			x=false;
			y=true;
			
			break;
		}
		else c++;
	}
	
	segmento_iniziale.setCoordinate_iniziali(coordinate_iniziali); //imposto le coordinate iniziali del segmento
	
	
		
}



private void conversione_segmenti(){
	
	Circuito circuito = null;	
	
	Iterator<Segmento> segmento = this.segmenti_circuito.iterator();	

	//int segmento_sel =0;
	
	

	while (segmento.hasNext()) {				
		
		Segmento segmento_singolo = (Segmento) segmento.next();		
		
		if(circuito==null)//� il primo elemento, quindi assegno memoria al circuito
		{
			circuito = new Circuito();
			
			circuito.setNome(this.nome_circuito);
			circuito.setNazione(this.nazione);
			
			circuito.aggiungi_rettilineo(segmento_singolo, true);	
			
			circuito.aggiungiFinishLine(this.finishLine);
			
			int ultimo_punto = segmento_singolo.get_lista_punti().size() - 1; //ultimo punto del rettilineo principale
			
			circuito.setUltimo_punto(ultimo_punto);
			
		}
		else //circuito non � vuoto
		{
			if(segmento_singolo.getRaggio()==0)//devo aggiungere un rettilineo all'elemento precedente
			{ 							
				circuito.aggiungi_rettilineo(segmento_singolo , false);					
			}
			else//devo aggiungere una curva all'elemento precedente
			{
				circuito.aggiungi_curva(segmento_singolo);				
			} 
		}
		
		
		//segmento_sel++;
		
	}
	
//	writeFile2(circuito); per debug, esporta la pista in formato testo sottoforma di coordinate cartesiane
	memorizza_circuito(circuito);
}

private static void memorizza_circuito(Circuito circuito)
{
	Database database;
	  
	database = new Database();
	  
	database.caricadriver();
	  
	database.collegati();
	
	database.setDati_Circuito(circuito);
	
}

//Salva il circuito in un file in forma x;y
public static void writeFile2(Circuito asd) {
	
    String path = "C:/circuito.txt";
    try 
    {    	
      File file = new File(path);
      FileWriter fw = new FileWriter(file);
      BufferedWriter bw = new BufferedWriter(fw);
      Iterator<Segmento> segmento = asd.get_segmenti().iterator();
      
      while (segmento.hasNext()) 
      {    	  
    	  Segmento segmento2 = (Segmento) segmento.next();
    	  Iterator<Punto> punti = segmento2.get_lista_punti().iterator();
    	  
    	  while (punti.hasNext()) 
    	  {
    		  Punto punto = (Punto) punti.next();		
    		  bw.newLine();
    		  bw.write(punto.getX()+";"+punto.getY());
		
    	  }    	  
      }             
      
      bw.flush();
      bw.close();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }

public double getStart() {
	return start;
}

public void setStart(double start) {
	this.start = start;
}

public String getNome_circuito() {
	return nome_circuito;
}

public void setNome_circuito(String nome_circuito) {
	this.nome_circuito = nome_circuito;
}

public String getNazione() {
	return nazione;
}

public void setNazione(String nazione) {
	this.nazione = nazione;
} 
	
}
