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
package Database;
import java.sql.*;
import java.util.Iterator;

import javax.swing.JComboBox;

import Circuito.Circuito;
import Circuito.Punto;
import Circuito.Segmento;
import GUI.ComboItem;
import auto.Auto;
import auto.Curva_motore;
import auto.Motore;
import auto.Pneumatico;
import auto.Trasmissione;
import pilota.Pilota;

public class Database {

	public static Connection conn;
	
	public void caricadriver() {
		   try {  Class.forName("org.sqlite.JDBC");
	//	    System.out.println("Driver Caricato");
		   }
		   catch(ClassNotFoundException e) {
		 //     System.out.println("Driver SQL non trovato");
		      System.exit(1); }
		 }
	
	public void collegati(){
		   try { conn = DriverManager.getConnection("jdbc:sqlite:database.db");
			 
		  // System.out.println("Connesso al Database");
		   }
		   catch(SQLException e) {
	//	      System.out.println("Collegamento non riuscito");
		      System.exit(1); }
		   
	}	
	
	public void disconettiti(){
		try {
			conn.close();
	//		System.out.println("disconesso da Database");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getDati_Pilota(String Nome, String Cognome, Pilota pilota){
		
		try {
		
			Statement stmt = null;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from Pilota where Nome='" + Nome + "' AND Cognome='" + Cognome + "'");
					
			while(rs.next()){
				
				 pilota.setNome(rs.getString("Nome"));
				 pilota.setCognome(rs.getString("Cognome"));
				 pilota.setEta(rs.getInt("Eta"));
				 pilota.setPeso(rs.getInt("Peso"));
				 pilota.setAggressivita(rs.getInt("Aggressivita"));
				 pilota.setConcentrazione(rs.getInt("Concentrazione"));
				 pilota.setEsperienza(rs.getInt("Esperienza"));
				 pilota.setTecnica(rs.getInt("Tecnica"));
				 pilota.setMotivazione(rs.getInt("Motivazione"));
				 pilota.setAccelerazione(rs.getInt("Accelerazione"));
				 pilota.setFrenata(rs.getInt("Frenata"));
				 pilota.setTotale();
								 
			}		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setDati_Pilota(Pilota pilota){
		
		try {
		
			PreparedStatement stmt = null;			
			
			String query_pilota = "insert into Pilota(Nome, Cognome, Eta, Peso, Aggressivita, Concentrazione, Esperienza, Tecnica, Motivazione, Accelerazione, Frenata, Totale) values (?,?,?,?,?,?,?,?,?,?,?,?)";
						
			stmt= conn.prepareStatement(query_pilota);
			
			stmt.setString(1, pilota.getNome());
			stmt.setString(2, pilota.getCognome());
			stmt.setInt(3, pilota.getEta());
			stmt.setInt(4, pilota.getPeso());
			stmt.setInt(5, pilota.getAggressivita());
			stmt.setInt(6, pilota.getConcentrazione());
			stmt.setInt(7, pilota.getEsperienza());
			stmt.setInt(8, pilota.getTecnica());
			stmt.setInt(9, pilota.getMotivazione());
			stmt.setInt(10, pilota.getAccelerazione());
			stmt.setInt(11, pilota.getFrenata());
			stmt.setInt(12, pilota.getTotale());			
			
			stmt.executeUpdate();			
			
			stmt.close();
								 
			}		
			
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		
	public ResultSet getInfo_Pilota(){
		
			ResultSet rs = null;
		try {
		
			Statement stmt = null;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("Select Nome, Cognome from Pilota");
					
			return rs;	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public int Count_Pilota(){
		
		ResultSet rs = null;
	try {
	
		Statement stmt = null;
		stmt = conn.createStatement();
		String query_size = "Select count(*) as conteggio from Pilota";
		
		rs = stmt.executeQuery(query_size);
		
		int size = rs.getInt("conteggio");
				
		return size;	
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return 0;
}
	
	public void getDati_Motore(Motore motore, int id_motore){
		try {
			
			Statement stmt = null;
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("Select * from Motore where Id='" + id_motore + "'");
			
			int id = 0;
			
			while(rs.next())
			{
				id = rs.getInt("Id");
				motore.setId_motore(id);
				motore.setNomeMotore(rs.getString("Nome"));
				motore.setCilindrata(rs.getInt("Cilindrata"));
				motore.setCorsa(rs.getFloat("Corsa"));
				motore.setAlesaggio(rs.getFloat("Alesaggio"));
				motore.setCilindri(rs.getInt("Cilindri"));	
				motore.setPotmax(rs.getFloat("PotMax"));
				motore.setCoppiamax(rs.getFloat("Cilindri"));
				
				int turbo = rs.getInt("Turbo");
				
				if(turbo==0) motore.setTurbo(false);
				else motore.setTurbo(true);
				
			}	
			
			stmt.close();
			
			getDati_Curva(motore, id);
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getDati_Trasmissione(Trasmissione trasmissione, int id_trasmissione){
			
		try {
			
			Statement stmt = null;
			stmt = conn.createStatement();
			
			String query_trasmissione = "Select * from Trasmissione where Id='"+ id_trasmissione + "'";
			String query_rapporti_trasmissione = "Select * from RapportiTrasmissione where Id_Cambio='"+ id_trasmissione + "'";
			
			ResultSet rs_trasmissione = stmt.executeQuery(query_trasmissione);			
			
			
			//prendi i dati dalla tabella Trasmissione
			trasmissione.setNome(rs_trasmissione.getString("Nome"));
			trasmissione.setId_cambio(rs_trasmissione.getInt("Id")); //Id della trasmissione
			trasmissione.setN_rapporti(rs_trasmissione.getInt("NumeroRapporti")); //NUmero di rapporti per quella trasmissione
			trasmissione.setRapporto_finale(rs_trasmissione.getDouble("RapportoFinale")); //Rapporto di Trasmissione Finale
			
			int n_rapporti = rs_trasmissione.getInt("NumeroRapporti");//serve per il ciclo for succesivo
						
			ResultSet rs_rapporti_trasmissione = stmt.executeQuery(query_rapporti_trasmissione); //prendo i rapporti di riduzione relativi a quella trasmissione
		   
			for (int i = 1; i <= n_rapporti; i++) //assegna il rapporto al corrispettivo numero di marcia
			{
				rs_rapporti_trasmissione.next();
				double rapporto_riduzione = rs_rapporti_trasmissione.getDouble("Rapporto");
				trasmissione.setRapporti(i, rapporto_riduzione);
			}
			
						

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void getDati_Pneumatico(Pneumatico pneumatico, int id_pneumatico){
		
		try {
			
			Statement stmt = null;
			stmt = conn.createStatement();
			
			String query_pneumatico = "Select * from Pneumatico where Id='"+ id_pneumatico + "'";			
			
			ResultSet rs_pneumatico = stmt.executeQuery(query_pneumatico);		
			
			int id = rs_pneumatico.getInt(1);//id del pneumatico
						
			String nome= rs_pneumatico.getString(2);//nome dello pneumatico
			
			int dim_cerchio = rs_pneumatico.getInt(3);//dimensioni del cerchione in pollici
			int larghezza = rs_pneumatico.getInt(4);//larghezza pneumatico in mm
			int rapporto = rs_pneumatico.getInt(5);//rapporto l/a in percentuale
			double coefficiente = rs_pneumatico.getDouble(6); //coeff.d'attrito dello pneumatico
			int durata = rs_pneumatico.getInt(7);//durata della gomma
			
			//salve le informazioni in pneumatico
			pneumatico.setId_pneumatico(id);
			pneumatico.setNome(nome);		
			pneumatico.setDim_cerchio(dim_cerchio);
			pneumatico.setLarghezza(larghezza);
			pneumatico.setRapporto(rapporto);
			pneumatico.setCoefficiente(coefficiente);
			pneumatico.setCoeff_totale(coefficiente);
			pneumatico.setDurata(durata);
			
			pneumatico.setCirconferenza_rotolamento();
						

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void getDati_Curva(Motore motore, int id_motore){
		
		try {
			
			Statement stmt = null;
			stmt = conn.createStatement();
			
			String query_size = "Select count(*) as conteggio from Curva where IdMotore='"+ id_motore + "'";
			String query_elementi = "Select * from Curva where IdMotore='"+ id_motore + "'";
			
			ResultSet size = stmt.executeQuery(query_size);
			int num = size.getInt("conteggio");			
			
				   
		    ResultSet numero = stmt.executeQuery(query_elementi);
			
			num=0;
			
			while(numero.next())
			{				
				int rpm = numero.getInt("RPM");
				double coppia = numero.getDouble("Coppia");
				
				motore.setCurva(rpm, coppia);
						
				num++;				
			}						

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setDati_Circuito(Circuito circuito)
	{		
		try {
			PreparedStatement stmt = null;			
			
			String query_circuito = "insert into Circuito (Nome, Nazione, Traguardo, UltimoPunto) values (? ,?,?,?)";
				
			stmt= conn.prepareStatement(query_circuito);
			
			stmt.setString(1, circuito.getNome());
			stmt.setString(2, circuito.getNazione());
			stmt.setInt(3, circuito.getId_start());
			stmt.setInt(4, circuito.getUltimo_punto());
			stmt.executeUpdate();
			
			
			stmt.close();
			
			String query_id = "select Id from Circuito where Nome='"+circuito.getNome()+"'AND Nazione='"+circuito.getNazione()+"'";
			
			Statement get_Id = conn.createStatement();
			
			ResultSet rs_id = get_Id.executeQuery(query_id);			
			
			int id= rs_id.getInt("id");//nome dello pneumatico
			
			get_Id.close();
			
			String query_punti = "insert into Punto (Id_Circuito, val_x, val_y) values (? ,? , ?)";
			
			 Iterator<Segmento> segmento = circuito.get_segmenti().iterator();
		      
		      while (segmento.hasNext()) 
		      {    	  
		    	  Segmento segmento_selezionato = (Segmento) segmento.next();
		    	  Iterator<Punto> punti = segmento_selezionato.get_lista_punti().iterator();
		    	  
		    	  stmt= conn.prepareStatement(query_punti);
		    	  while (punti.hasNext()) 
		    	  {
		    		Punto punto = (Punto) punti.next();				    		
		    		
		  			stmt.setInt(1, id);		  			
		  			stmt.setDouble(2, punto.getX());
		  			stmt.setDouble(3, punto.getY());
		  			
		  			stmt.executeUpdate();
		  			
				
		    	  }  
		    	  stmt.close();  	  
		      }
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getDati_Circuito(Circuito circuito, int id_circuito)
	{		
		try 
		{
			Statement stmt = null;
			stmt = conn.createStatement();
			
			String query_punti = "Select * from Punto where Id_Circuito='"+ id_circuito + "'";		
			String query_circuito = "Select * from Circuito where Id='"+id_circuito + "'";			
			
			ResultSet rs_info = stmt.executeQuery(query_circuito);
			
			circuito.setNome(rs_info.getString(2));
			circuito.setNazione(rs_info.getString(3));
			circuito.setId_start(rs_info.getInt(4));
			circuito.setUltimo_punto(rs_info.getInt(5));
			
			stmt.close();
			
			stmt = null;
			stmt = conn.createStatement();
			
			ResultSet rs_punti = stmt.executeQuery(query_punti);
			while (rs_punti.next()) {
				Punto coordinate = new Punto();
				
				coordinate.setX(rs_punti.getDouble(2));
				coordinate.setY(rs_punti.getDouble(3));
				
				circuito.aggiungi_punto(coordinate);
				
			}
			
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setDati_Auto(Auto auto){
		try {
			PreparedStatement stmt = null;			
			
			String query_auto = "insert into Auto (Nome, Marca, Cd, Area, Serbatoio, Massa, Id_Motore, Id_Cambio, Id_Pneumatico) values (?,?,?,?,?,?,?,?,?)";
				
			stmt= conn.prepareStatement(query_auto);
			
			stmt.setString(1, auto.getNome());
			stmt.setString(2, auto.getMarca());
			stmt.setDouble(3, auto.getCd());
			stmt.setDouble(4, auto.getArea());
			stmt.setInt(5, auto.getSerbatoio());
			stmt.setInt(6, auto.getMassa_auto());
			stmt.setInt(7, auto.getMotore().getId_motore());
			stmt.setInt(8, auto.getTrasmissione().getId_cambio());
			stmt.setInt(9, auto.getPneumatico().getId_pneumatico());
			
			stmt.executeUpdate();
			
			
			stmt.close();
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			
	}
	
	public void getDati_Auto(Auto auto, String nome, String marca){
		try {
			Statement stmt = null;
			stmt = conn.createStatement();
			
			String query_auto = "Select * from Auto where Nome='"+nome+"' AND Marca='"+marca+"'";
			
			ResultSet rs_auto = stmt.executeQuery(query_auto);
			
			auto.setNome(rs_auto.getString(2));
			auto.setMarca(rs_auto.getString(3));
			auto.setCd(rs_auto.getDouble(4));
			auto.setArea(rs_auto.getDouble(5));
			auto.setSerbatoio(rs_auto.getInt(6));
			auto.setMassa_auto(rs_auto.getInt(7));
			
			int id_motore = rs_auto.getInt(8);
			int id_trasmissione = rs_auto.getInt(9);
			int id_pneumatico = rs_auto.getInt(10);
			
			Motore motore = new Motore();
			Trasmissione trasmissione = new Trasmissione();
			Pneumatico pneumatico = new Pneumatico();
			
			getDati_Motore(motore, id_motore);
			getDati_Trasmissione(trasmissione, id_trasmissione);
			getDati_Pneumatico(pneumatico, id_pneumatico);
			
			auto.setMotore(motore);
			auto.setTrasmissione(trasmissione);
			auto.setPneumatico(pneumatico);
		//	auto.setId_motore(rs_auto.getInt(7));
			
			
			stmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public ResultSet getNome_Circuito(){
		ResultSet rs_nome = null;
		try {			
			Statement stmt = null;
			stmt = conn.createStatement();
			String query_NomeCircuito = "Select Nome, Nazione from Circuito";
		    rs_nome = stmt.executeQuery(query_NomeCircuito);
		    stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return rs_nome;
	}
	
	public ResultSet getInfoCircuiti(){
		
		ResultSet rs_circuiti = null;
		try {			
			Statement stmt = null;
			stmt = conn.createStatement();
			String query_NomeCircuito = "Select Id, Nome, Nazione from Circuito";
		    rs_circuiti = stmt.executeQuery(query_NomeCircuito);
		    
		   // stmt.close(); questo provoca errore
		    
		    return rs_circuiti;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return rs_circuiti;
	}
	
	public void setDati_Pneumatico(Pneumatico pneumatico){
		try{
		PreparedStatement stmt = null;
		
		String query_pneumatico ="insert into Pneumatico (Id, Nome, DimensioneCerchio, Larghezza, RapportoLA, Coefficiente, Durata) values (?,?,?,?,?,?,?)";
		
		stmt = conn.prepareStatement(query_pneumatico);
		
		stmt.setString(2, pneumatico.getNome());
		stmt.setInt(3, pneumatico.getDim_cerchio());
		stmt.setInt(4, pneumatico.getLarghezza());
		stmt.setInt(5, pneumatico.getRapporto());
		stmt.setDouble(6, pneumatico.getCoefficiente());
		stmt.setInt(7, pneumatico.getDurata());
		
		stmt.executeUpdate();
		
		stmt.close();
		
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void setDati_Trasmissione(Trasmissione trasmissione){
		try {
			
			PreparedStatement stmt = null;
			
			String query_trasmissione = "insert into Trasmissione(Id, Nome, NumeroRapporti, RapportoFinale) values (?,?,?,?)";
			
			stmt = conn.prepareStatement(query_trasmissione);
			
			stmt.setString(2, trasmissione.getNome());
			stmt.setInt(3, trasmissione.getN_rapporti());
			stmt.setDouble(4, trasmissione.getRapporto_finale());
			
			stmt.executeUpdate();
			
			stmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public boolean controllo_NomeCircuito(String nome, String nazione){
		boolean flag=true;
		try {
		
		Statement stmt = null;
		
		String query_circuito = "Select * from Circuito where Nome='" + nome +"' AND Nazione='" + nazione + "'";
		
		stmt=conn.createStatement();
			
		ResultSet rs_info = stmt.executeQuery(query_circuito);
		
				
		if(rs_info.next()){
			flag = false;
		}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}

	public int getId_Trasmissione() 
	{
		int id_cambio = 0;
		try {
			
		Statement stmt = null;
		
		String query_id = "select Id from Trasmissione order by Id desc limit 1"; //selezione l'ultima riga inserita	
		
		stmt=conn.createStatement();
		
		ResultSet rs_trasmissione = stmt.executeQuery(query_id);
				
		id_cambio = rs_trasmissione.getInt(1);	
		
		stmt.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id_cambio;
		
	}
	
	public int getId_Motore(){
		
		int id_motore = 0;
		try {
			
		Statement stmt = null;
		
		String query_id = "select Id from Motore order by Id desc limit 1"; //selezione l'ultima riga inserita	
		
		stmt=conn.createStatement();
		
		ResultSet rs_motore = stmt.executeQuery(query_id);
				
		id_motore = rs_motore.getInt(1);	
		
		stmt.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id_motore;
	}

	public void setDati_RapportiTrasmissione(Trasmissione trasmissione) {
		try {
			
		PreparedStatement stmt = null;
		
		String query_trasmissione = "insert into RapportiTrasmissione(Id_Cambio, Numero, Rapporto) values (?,?,?)";
		
		stmt = conn.prepareStatement(query_trasmissione);
		
		for (int i = 1; i <= trasmissione.getN_rapporti(); i++) 
		{
			
		stmt.setInt(1, trasmissione.getId_cambio());
		stmt.setInt(2, i);
		stmt.setDouble(3, (double) trasmissione.getRapporto().get(i));
		
		stmt.executeUpdate();
		
		}
		stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setDati_CurvaMotore(Motore motore)
	{
		
	try {
		
		PreparedStatement stmt = null;
		
		String query_curva_motore = "insert into Curva(IdMotore, Coppia, RPM) values (?,?,?)";
		
		stmt = conn.prepareStatement(query_curva_motore);
		
		for(int i=0; i<motore.getCurva().size();i++)
		{			
		
		stmt.setInt(1, motore.getId_motore());
		stmt.setDouble(2, motore.getCurva().get(i).getCoppia());
		stmt.setInt(3, motore.getCurva().get(i).getRpm());
		stmt.executeUpdate();
		
		}
		
		stmt.close();
		
	} catch (Exception e) {
		// TODO: handle exception
	}	
	}
	
	public void setDati_Motore(Motore motore){
	
		try {
			
			int turbo = 0;
			
			PreparedStatement stmt = null;
			
			String query_motore="insert into Motore(Id, Nome, Cilindrata, Corsa, Alesaggio, Cilindri, PotMax, CoppiaMax, Turbo) values (?,?,?,?,?,?,?,?,?)";
			
			stmt=conn.prepareStatement(query_motore);
			

			
			stmt.setString(2, motore.getNomeMotore());
			stmt.setInt(3, motore.getCilindrata());
			stmt.setDouble(4, motore.getCorsa());
			stmt.setDouble(5, motore.getAlesaggio());
			stmt.setInt(6, motore.getCilindri());
			stmt.setDouble(7, motore.getPotmax());
			stmt.setDouble(8, motore.getCoppiamax());
			
			if(motore.getTurbo())
			{
				turbo=1;
				stmt.setInt(9, turbo);
			}
			else
			{ 
				stmt.setInt(9, turbo);
			}
			
			stmt.executeUpdate();
			stmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void comboBox_Motore(JComboBox comboBox){
		try {
			 
			Statement stmt = null;
			
			String query_motore = "select Id from Motore";
			stmt=conn.createStatement();
			ResultSet rs = stmt.executeQuery(query_motore);
			
			while(rs.next()){
				String name = rs.getString("Id");
				comboBox.addItem(name);
			}
			
			stmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	 
	
	public void inizializza_ComboBox(JComboBox comboBox, String tipologia){
		try {
						
			Statement stmt = null;
			
			String query = "select Id,Nome from '"+tipologia+"'";
			stmt=conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next())
			{
				String id = rs.getString("Id");
				String nome = rs.getString("Nome");
				
				ComboItem item = new ComboItem(id, nome);
				
				comboBox.addItem(item);
				
			}
			
			
			stmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void Reset_Database(){
		try {
			
			Statement stmt = null;
			
			
			   String query_resetAuto = "delete from Auto";
			   String query_resetMotore = "delete from Motore";
			   String query_resetCircuito = "delete from Circuito";
			   String query_resetPilota= "delete from Pilota";
			   String query_resetTrasmissione="delete from Trasmissione";
			   String query_resetPneumatico="delete from Pneumatico";
			   String query_resetPunto="delete from Punto";
			   String query_resetRapportiTrasmissione="delete from RapportiTrasmissione";
			   String query_resetSettore="delete from Settore";
			   String query_resetCurva="delete from Curva";
			   
			   stmt=conn.createStatement();   
			   
			   stmt.execute(query_resetAuto);			   
			   stmt.execute(query_resetMotore);
			   stmt.execute(query_resetCircuito);
			   stmt.execute(query_resetPilota);
			   stmt.execute(query_resetTrasmissione);
			   stmt.execute(query_resetPneumatico);	
			   stmt.execute(query_resetCurva);
			   stmt.execute(query_resetSettore);
			   stmt.execute(query_resetRapportiTrasmissione);
			   stmt.execute(query_resetPunto);
			
			
			   stmt.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public boolean Popolamento(){
		boolean flag=true;
		try {
			
			Statement stmt = null;
			
		   String query_resetAuto = "select * from Auto";
		   String query_resetMotore = "select * from Motore";
		   String query_resetCircuito = "select * from Circuito";
		   String query_resetPilota= "select count(*) as conteggio from Pilota";
		   String query_resetTrasmissione="select * from Trasmissione";
		   String query_resetPneumatico="select * from Pneumatico";
		   String query_resetPunto="select * from Punto";
		   String query_resetRapportiTrasmissione="select * from RapportiTrasmissione";
		   String query_resetSettore="select * from Settore";
		   String query_resetCurva="select * from Curva";
		   
		   stmt=conn.createStatement();
		   
		   ResultSet rs;
		   
		   rs = stmt.executeQuery(query_resetAuto);
		   
		   if(!rs.next()){
			   flag=false;
			   return flag;
		   }
		   
//		   rs=stmt.executeQuery(query_resetMotore);
//		   
//		   if(!rs.next()){
//			   flag=false;
//			   return flag;
//		   }
		   
		   rs=stmt.executeQuery(query_resetCircuito);
		   if(!rs.next()){
			   flag=false;
			   return flag;
		   }
		   
		   rs = stmt.executeQuery(query_resetPilota);
		   int valore = rs.getInt("conteggio");
		   if(valore<2){
			   flag=false;
			   return flag;
		   }
		   
//		   rs=stmt.executeQuery(query_resetTrasmissione);
//		   if(!rs.next()){
//			   flag=false;
//			   return flag;
//		   }
		   
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		   return flag;
		
	}

	public ResultSet getInfoAuto() {
		
		ResultSet rs = null;
		try {
		
			Statement stmt = null;
			stmt = conn.createStatement();
			rs = stmt.executeQuery("Select Nome, Marca, Cd, Area, Serbatoio, Massa from Auto");
					
			return rs;	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	public boolean Popolamento_Auto(){
		boolean flag=true;
		try {
			
			Statement stmt = null;
			
		  
		   String query_resetMotore = "select * from Motore"; 
		   String query_resetTrasmissione="select * from Trasmissione";
		   String query_resetPneumatico="select * from Pneumatico";
		  
		   
		   stmt=conn.createStatement();
		   
		   ResultSet rs;
		   
		   rs = stmt.executeQuery(query_resetMotore);
		   
		   if(!rs.next()){
			   flag=false;
			   return flag;
		   }
		   
		   
		   rs=stmt.executeQuery(query_resetTrasmissione);
		   if(!rs.next()){
			   flag=false;
			   return flag;
		   }
		   
		   rs = stmt.executeQuery(query_resetPneumatico);
		   if(!rs.next()){
			   flag=false;
			   return flag;
		   }
		   
		
		} catch (Exception e) {
			// TODO: handle exception
		}
		   return flag;
		
	}
}

  
