package br.edu.unifesspa.malves.photovoltaics;

/**
 * 
 * @author Marcela Alves
 * @since 2016-07-13
 *
 */
public class Panel {

	/**
	 * Rated Power (W)
	 */
	public static final double potenciaNominal = 265;
	
	/**
	 * Panel Area [m²]
	 */
	public static final double area = 1.6085;
	
	/**
	 * Panel Acquisition Cost (Brazilian Real - BRL)
	 */
	public static final double custoPorPainel = 835.00;
	
	/**
	 * Panel Efficiency
	 */
	public static final double eficiencia = 0.1616;
	
	/**
	 * Panel Performance Rate
	 */
	public static final double taxaDesempenho = 0.995;
	
	/**
	 * Standard Hours of Full Sun [h]
	 */
	public static final double hspPadrao = 5.9;
	
	/**
	 * Hours of Full Sun [h]
	 */
	public static final double[] hsp = {2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10};
	
	/**
	 * Standard Irradiance
	 */
	public static final double irradianciaPadrao = 5.5;
	
	/**
	 * Irradiance [kWh/m²/day]
	 */
	public static final double[] irradiancia = {2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5};
}