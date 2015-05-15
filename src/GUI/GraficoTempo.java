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

import java.io.*;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.axis.*;
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.ChartUtilities; 

public class GraficoTempo {  
    
	public JFreeChart crea_grafico(Giocatore partecipante, double x_max, int giro_attuale){
         
                //Use createXYLineChart to create the chart
                JFreeChart XYLineChart=ChartFactory.createXYLineChart("","Distanza [m]","Velocit� [m/s]", partecipante.getGiri_grafico().get(giro_attuale),PlotOrientation.VERTICAL,false,false,false);
                          
                XYPlot xyPlot = XYLineChart.getXYPlot();
                NumberAxis domainAxis = (NumberAxis) xyPlot.getDomainAxis();            
                domainAxis.setRange(0, x_max);
                domainAxis.setTickUnit(new NumberTickUnit(500));
                
                return XYLineChart;
                

         
     }
 }
