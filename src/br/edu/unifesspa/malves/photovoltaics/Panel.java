package br.edu.unifesspa.malves.photovoltaics;

/**
 * 
 * @author Marcela Alves
 * @since 2016-07-13
 *
 */
public class Panel {

	/**
	 * Rated Power in Watts (W)
	 */
	public static final double potenciaNominal = 260;
	
	/**
	 * Panel Area in m²
	 */
	public static final double area = 1.6085;
	
	/**
	 * Panel Acquisition Cost (in Brazilian Real R$)
	 */
	public static final double custoPorPainel = 850.00;
	
	/**
	 * 
	 */
	public static final double eficiencia = 0.1616;
	
	/**
	 * 
	 */
	public static final double taxaDesempenho = 0.995;
	
	/**
	 * 
	 */
	public static final double duracaoSolarMediaPadrao = 5.9;
	
	/**
	 * 
	 */
	public static final double hspPadrao = 5.5;
	
	/**
	 * 
	 */
	public static final double[] hsp = {2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7};
}