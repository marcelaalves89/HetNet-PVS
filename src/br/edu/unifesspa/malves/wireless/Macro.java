package br.edu.unifesspa.malves.wireless;

/**
 * 
 * @author	Marcela Alves
 * @since	2016-06-12
 *
 */
public abstract class Macro {

	/**
	 * Bandwidth (MHz)
	 */
	public static double bandWidth = (10*Math.pow(10,6));
	
	/**
	 * Angular Coefficient
	 */
	public static final double aM = 4.7; 
	
	/**
	 * Linear Coefficient
	 */
	public static final double bM = 130;
	
	/**
	 * Number of Sectors
	 */
	public static final double numOfSectors = 3;
	
	/**
	 * Power Transmission
	 */
	public static final double powerTransmission = 40;
	
	/**
	 * Cell Capacity
	 */
	public static final double cellCapacity = Macro.bandWidth*3*((Math.log10(1+1.1)/Math.log10(2))/Math.pow(10, 6));
	
	/**
	 * Peak Demand per Macro BS
	 */
	public static final double peakDemandPerMacro = Macro.cellCapacity;
	
	/**
	 * Average Demand per Macro
	 */
	public static final double averageDemandPerMacro = Macro.cellCapacity*9.64/16.0;	
	
	/**
	 * Macro Power
	 */
	public static final double power = 650;
}