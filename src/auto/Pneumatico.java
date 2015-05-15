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
package auto;

public class Pneumatico implements Cloneable{

	private int Id_pneumatico;
	
	private String nome;
	private int larghezza = 0;
	private int rapporto= 0;
	private int dim_cerchio = 0;
	
	private double coefficiente = 0;
	private double coeff_totale = 0;
	
	private double circonferenza_rotolamento = 0;
	
	private int durata;
	private int numero_giri;
	private double coeff_attrito;
	
	
	public Pneumatico(){}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getLarghezza() {
		return larghezza;
	}

	public void setLarghezza(int larghezza) {
		this.larghezza = larghezza;
	}

	public int getRapporto() {
		return rapporto;
	}

	public void setRapporto(int rapporto) {
		this.rapporto = rapporto;
	}

	public int getDim_cerchio() {
		return dim_cerchio;
	}

	public void setDim_cerchio(int dim_cerchio) {
		this.dim_cerchio = dim_cerchio;
	}

	public double getCoefficiente() {
		return coefficiente;
	}

	public void setCoefficiente(double coefficiente) {
		this.coefficiente = coefficiente;
	}

	public double getCirconferenza_rotolamento() {
		return circonferenza_rotolamento;
	}

	public void setCirconferenza_rotolamento() {
		
		if(this.rapporto!=0&&this.larghezza!=0&&this.dim_cerchio!=0)
		{			
		
		double altezza = (((double)this.rapporto)/100) * ((double) this.larghezza); //altezza dello pneumatico in mm
		
		double raggio =  ((dim_cerchio*25.4)/2) + altezza; //raggio ruota
		
		 //calcolo della circonferenza di rotolamento dello pneumatico
		
		this.circonferenza_rotolamento = (2*(Math.PI)*raggio)/1000; //in metri
		}
		
		else this.circonferenza_rotolamento = 0;
	}

	public int getId_pneumatico() {
		return Id_pneumatico;
	}

	public void setId_pneumatico(int id_pneumatico) {
		Id_pneumatico = id_pneumatico;
	}

	public int getDurata() {
		return durata;
	}

	public void setDurata(int durata) {
		this.durata = durata;
	}

	public double getCoeff_attrito() {
		return coeff_attrito;
	}

	public void setCoeff_attrito(double coeff_attrito) {
		this.coeff_attrito = coeff_attrito;
	}

	public int getNumero_giri() {
		return numero_giri;
	}

	public void setNumero_giri(int numero_giri) {
		this.numero_giri = numero_giri;
	}

	public double getCoeff_totale() {
		return coeff_totale;
	}

	public void setCoeff_totale(double coeff_totale) {
		this.coeff_totale = coeff_totale;
	}
	
	public Object clone() 
	{
		try {
			return super.clone();
			} 
		catch (CloneNotSupportedException e) 
		{
		e.printStackTrace(); 
		return null; 
		} 
	}
}
