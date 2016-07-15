package br.edu.unifesspa.malves.util;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class GraficoBarra extends ApplicationFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3222603704302811628L;

	private String tituloGrafico;

	private String tituloEixoX;

	private String tituloEixoY;

	private HashMap<String, Double> dados;

	public GraficoBarra(String tituloJanela, String tituloGrafico, String tituloEixoX, String tituloEixoY, HashMap<String, Double> dados) {
		super(tituloJanela);
		this.tituloGrafico = tituloGrafico;
		this.tituloEixoX = tituloEixoX;
		this.tituloEixoY = tituloEixoY;
		this.dados = dados;

		JPanel chartPanel = createDemoPanel();
		chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
		setContentPane(chartPanel);
	}

	private JFreeChart createChart(final CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createBarChart(
				this.tituloGrafico,     
				this.tituloEixoX,                     
				this.tituloEixoY,                     
				dataset,              
				PlotOrientation.VERTICAL,
				true,                     
				true,                 
				true);
		
		CategoryPlot plot = chart.getCategoryPlot();
		BarRenderer br = (BarRenderer) plot.getRenderer();
		br.setMaximumBarWidth(.1);
		
		
		
		return chart;
	}

	public JPanel createDemoPanel() {
		JFreeChart chart = this.createChart(createDataset());
		ChartPanel panel = new ChartPanel(chart);
		panel.setMouseWheelEnabled(true);
		return panel;
	}

	private CategoryDataset createDataset() {		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();		
		for(Entry<String, Double> elemento : this.dados.entrySet()) {
			String key = elemento.getKey();
			Double value = elemento.getValue();		    		    			    	    
			dataset.addValue(value.doubleValue(), key, "");
		}	    
		return dataset;
	}
}