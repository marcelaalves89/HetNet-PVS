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
	public static final double[] penetrationRate = {0,0.05,0.1,0.15,0.2,0.25,0.3,0.35,0.4,0.45,0.5,0.55,0.6,0.65,0.7,0.75,0.8,0.85,0.9,0.95,1};
}