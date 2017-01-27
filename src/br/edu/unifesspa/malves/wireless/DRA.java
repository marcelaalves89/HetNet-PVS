package br.edu.unifesspa.malves.wireless;

/**
 * 
 * @author	Marcela Alves
 * @since	2016-06-12
 *
 */
public abstract class DRA {

	/**
	 * Maximum Number of Antennas per RRU
	 */
	public static final double maxNumberOfAntennasPerRRU = 8;
	
	/**
	 * Number of RRU's per Building
	 */
	public static final double numOfRRUperBuilding = 1;
	
	/**
	 * Number of DU Ports per DU Rack
	 */
	public static final double numberOfDUPortsPerRack = 200;
	
	/**
	 * Power Amplifier Antenna (W)
	 */
	public static final double powerAmplifierAntenna = 25;
	
	/**
	 * RRU Power
	 */
	public static final double RRUPower = 100;
	
	/**
	 * DU Port Power
	 */
	public static final double DUPortPower = 25;
	
	/**
	 * Rack DU Power
	 */
	public static final double rackDUPower = 150;
	
	/**
	 * Penetration Rate Array
	 */
	public static final double[] penetrationRate = {0,0.07142,0.14284,0.21426,0.28568,0.3571,0.42851,0.49993,0.57136,0.64278,0.7142,0.78562,0.85704,0.92846,1};
}