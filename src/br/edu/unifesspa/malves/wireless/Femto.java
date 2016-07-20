 package br.edu.unifesspa.malves.wireless;

public abstract class Femto {

	public static final double aF = 8;
	
	public static final double bF = 4.8;
	
	public static final double[] taxaDePenetracao = {0,0.05,0.1,0.15,0.2,0.25,0.3,0.35,0.4,0.45,0.5,0.55,0.6,0.65,0.7,0.75,0.8,0.85,0.9,0.95,1};
	
	public static final double alcance = 20;
	
	public static final double porcentagemTrafego = 0.5;
	
	public static final double potencia = Femto.aF*0.1+Femto.bF; //10%
 	
	public static final double offGain = 0;
	
	public static final double numeroDeWavelengthsPorFemto = 100;
}