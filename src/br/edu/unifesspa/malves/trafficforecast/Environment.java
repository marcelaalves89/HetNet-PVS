package br.edu.unifesspa.malves.trafficforecast;

/**
 * 
 * @author	Marcela Alves
 * @since	2016-06-19
 *
 */
public abstract class Environment {
	
	/**
	 * Number of apartments per building
	 */
	public static final double numeroApartamentosPorPredio = 15;
	
	/**
	 * Number of floors per building
	 */
	public static final double numeroDeAndaresPorPredio = 5;
	
	/**
	 * User Density Array
	 */
	public static final double[] densidadeDeUsuarios = {1000, 1500, 2000, 2500, 3000, 3500, 3500, 4000, 4500, 5000};
	
	/**
	 * Default User density
	 */
	public static final double densidadeDeUsuariosPadrao = 3000;
	
	/**
	 * Indoor Users Proportion
	 */
	public static final double porcentagemUsuariosIndoor = 0.6;
		
	/**
	 * Outdoor Users Proportion
	 */
	public static final double porcentagemUsuariosOutdoor = 0.4;
	
	/**
	 * Geographic area (km^2)
	 */
	public static final double area =  100;
	
	/**
	 * Max Number of Apartments
	 */
	public static final double numeroMaximoPredios = 10000;
	
	/**
	 * Active users rate
	 */
	public static final double alphaMaximo = 0.16;
	
	/**
	 * Terminal Penetration rate of terminals type
	 */
	public static final double[][] taxaPenetracaoTerminais = {{0.1,0.2,0.3},{0.03,0.05,0.1},{0.3,0.5,0.6}};
	
	/**
	 * Years
	 */
	public static final double[] anos = {2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030};
	
	/**
	 * CO2 Emission Factor
	 */
	public static final double[] fatorCO2 = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};	
	
	/**
	 * Default CO2 Emission Factor
	 */
	public static final double fatorCO2Padrao = 0.6;
	
	/**
	 * Dollar Currency Quote
	 */
	public static final double cotacaoDolar = 3.243;
	
	/**
	 * Default HSP
	 */
	public static final double defaultHSP = 7;
	
	/**
	 * HSP Array
	 */
	public static final double[] hsp = {1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10, 10.5, 11, 11.5, 12};
	
	/**
	 * Default Solar Irradiance (hours)
	 */
	public static final double defaultSolarIrradiance = 7;
	
	/**
	 * Solar Irradiance Array
	 */
	public static final double[] radiacao = {2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5};
}