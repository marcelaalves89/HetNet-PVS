package br.edu.unifesspa.malves.backhaul;

/**
 * 
 * @author Marcela Alves
 * @since 2016-07-15
 *
 */
public class Fiber {

	/**
	 * Power Consumption of Upload Fiber [w]
	 */
	public static final double potenciaFibraUpload = 2;
	
	/**
	 * Power Consumption of Download Fiber [w]
	 */
	public static final double potenciaFibraDownload = 1;
	
	/**
	 * Power Consumption of Transceiver [w]
	 */
	public static final double potenciaSFP = 2;
	
	/**
	 * Power Consumption of Switch Fiber [w]
	 */
	public static final double potenciaSwitchFibra = 92;
	
	/**
	 * Number of ports per Fiber Switch
	 */
	public static final double numeroPortasSwitchFibra = 24;
	
	/**
	 * Capacity Maximal Fiber [Mbps]
	 */
	public static final double capacidadeMaximaFibra = 10000;
}
