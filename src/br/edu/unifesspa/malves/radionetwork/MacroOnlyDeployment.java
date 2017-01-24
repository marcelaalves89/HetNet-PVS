package br.edu.unifesspa.malves.radionetwork;

import br.edu.unifesspa.malves.trafficforecast.Environment;
import br.edu.unifesspa.malves.trafficforecast.TrafficForecast;
import br.edu.unifesspa.malves.util.Util;
import br.edu.unifesspa.malves.wireless.Macro;

/**
 * 
 * @author	Marcela Alves
 * @since	2016-06-12
 *
 */
public class MacroOnlyDeployment {

	/**
	 * Traffic forecast calculated previously
	 */
	public TrafficForecast prevision;
	
	/**
	 * Macro Density
	 */
	public double[] macroDensity;
	
	/**
	 * Macrocell Range
	 */
	public double[] alcanceCelulaMacro;
		
	/**
	 * Number of Macros Array
	 */
	public double[] numOfMacros;
	
	/**
	 * Number of active users per MacroBS
	 */
	public double[] numOfActiveUsersPerMacro;
	
	/**
	 * Power consumption of Macro Only Architecture
	 */
	public double[] power;
	
	/**
	 * User Density
	 */
	public double userDensity;
	
	/**
	 * Architecture's Name
	 */
	public String name;
	
	/**
	 * 	Super constructor call and initializing values
	 */
	public MacroOnlyDeployment(double userDensity){
		this.prevision = new TrafficForecast(userDensity);
		this.name = "Macro Only Deployment";
		this.userDensity = userDensity;
		this.run();
	}
	
	/**
	 * Performs the calculation of Macro Density, Macro Cell Range, Number of Macros, Number of Active Users Per Macro and Power consumption of Macro Only Architecture 
	 */
	public void run(){
		this.macroDensity = Util.getProdutoPorEscalar(prevision.getPrevisaoDeTrafego(), (1.0/Macro.cellCapacity));
		
		//Macro cell range
		double[] temp = Util.getProdutoPorEscalar(this.macroDensity, (1.5*Math.sqrt(3)));
		this.alcanceCelulaMacro = Util.getPotencia((Util.getPotencia(temp, -1.0)),1.0/2.0);
		
		//Number of Macros
		this.numOfMacros = Util.getProdutoPorEscalar(this.macroDensity, Environment.area);
		
		//Number of Active users per Macro
		this.numOfActiveUsersPerMacro = Util.getProdutoPorEscalar(Util.getPotencia(this.prevision.getTaxaMediaPorUsuarioExtendida(), -1.0), Macro.cellCapacity);
	}
}