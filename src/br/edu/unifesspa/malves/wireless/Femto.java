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
	public static final double[] penetrationRate = {0,0.0428,0.0856,0.1284,0.1712,0.214,0.2568,0.2996,0.3424,0.3852,0.428,0.4708,0.5136,0.5563,0.6};
	
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