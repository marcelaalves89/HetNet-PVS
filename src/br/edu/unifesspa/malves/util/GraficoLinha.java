package br.edu.unifesspa.malves.util;

import java.awt.BasicStroke;
import java.awt.Stroke;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class GraficoLinha extends ApplicationFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3222603704302811628L;
	
	private String tituloGrafico;
	
	private String tituloEixoX;
	
	private String tituloEixoY;
	
	private HashMap<String, double[]> dadosX;
	
	private double[] dadosY;

	public GraficoLinha(String tituloJanela, String tituloGrafico, String tituloEixoX, String tituloEixoY, HashMap<String, double[]> dadosX, double[] dadosY) {
		super(tituloJanela);
		this.tituloGrafico = tituloGrafico;
		this.tituloEixoX = tituloEixoX;
		this.tituloEixoY = tituloEixoY;
		this.dadosX = dadosX;
		this.dadosY = dadosY;
		
		JPanel chartPanel = createDemoPanel();
		chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
		setContentPane(chartPanel);
	}

	private JFreeChart createChart(final XYDataset dataset) {

		JFreeChart chart = ChartFactory.createXYLineChart(
				this.tituloGrafico,     
				this.tituloEixoX,                     
				this.tituloEixoY,                     
				dataset,              
				PlotOrientation.VERTICAL,
				true,                     
				true,                 
				true             
				);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setDomainPannable(true);
		plot.setRangePannable(true);
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(){

			private static final long serialVersionUID = 1L;
			Stroke soild = new BasicStroke(3.0f);
			Stroke dashed =  new BasicStroke(3.0f,BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[] {10.0f}, 0.0f);
			@Override
			public Stroke getItemStroke(int row, int column) {
				if (row > 10){
					double x = dataset.getXValue(row, column);
					if ( x > 1){
						return dashed;
					} else {
						return soild;
					} 
				} else
					return super.getItemStroke(row, column);
			}
		};

		renderer.setBaseShapesVisible(true);
		renderer.setBaseShapesFilled(true);
		renderer.setBaseStroke(new BasicStroke(4));
		plot.setRenderer(renderer);
		return chart;
	}

	public JPanel createDemoPanel() {
		JFreeChart chart = this.createChart(createDataset());
		ChartPanel panel = new ChartPanel(chart);
		panel.setMouseWheelEnabled(true);
		return panel;
	}

	private XYDataset createDataset() {		
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series = null;
		for(Entry<String, double[]> elemento : this.dadosX.entrySet()) {
		    String key = elemento.getKey();
		    double[] valores = elemento.getValue();
		    series = new XYSeries(key);
		    for (int i=0; i<valores.length; i++){		    			    	
		    	series.add(this.dadosY[i], valores[i]);	
		    }
		    dataset.addSeries(series);
		}	    
		return dataset;
	}
}