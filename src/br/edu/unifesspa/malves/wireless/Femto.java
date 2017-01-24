 package br.edu.unifesspa.malves.wireless;

 /**
  * 
  * @author	Marcela Alves
  * @since	2016-06-12
  *
  */
public abstract class Femto {
	
	/**
	 * Penetration Rate Array
	 */
	public static final double[] penetrationRate = {0.0, 0.03, 0.06, 0.09, 0.12, 0.15, 0.18, 0.21, 0.24, 0.27, 0.3, 0.33, 0.36, 0.39, 0.42, 0.45, 0.48, 0.51, 0.54, 0.57, 0.6};
	
	/**
	 * Cover/Reach (meters)
	 */
	public static final double cover = 20;	
	
	/**
	 * Femto Power (W)
	 */
	public static final double power = 10;
	
	/**
	 * Number of Wavelengths per Femto
	 */
	public static final double numOfWaveLengthsPerFemto = 100;
}