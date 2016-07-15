package br.edu.unifesspa.malves.radionetwork;

import br.edu.unifesspa.malves.backhaul.Microwave;
import br.edu.unifesspa.malves.trafficforecast.Environment;
import br.edu.unifesspa.malves.trafficforecast.PrevisaoDeTrafego;
import br.edu.unifesspa.malves.util.Util;
import br.edu.unifesspa.malves.wireless.Macro;

/**
 * 
 * @author	Marcela Alves
 * @since	2016-06-12
 *
 */
public class MacroOnlyDeployment extends RadioNetworkDimensioning {

	/**
	 * Traffic forecast calculated previously
	 */
	public PrevisaoDeTrafego previsao;
	
	/**
	 * Macro Density
	 */
	public double[] densidadeDeMacros;
	
	/**
	 * Macro cell range
	 */
	public double[] alcanceCelulaMacro;
		
	/**
	 * Number of Macros
	 */
	public double[] numeroDeMacros;
	
	/**
	 * Number of active users per MacroBS
	 */
	public double[] numeroDeUsuarioAtivosPorMacro;
	
	/**
	 * Power consumption of Macro Only Architecture
	 */
	public double[] potencia;
	
	/**
	 * User Density
	 */
	public double densidadeDeUsuarios;
	
	/**
	 * 	Super constructor call and initializing values
	 */
	public MacroOnlyDeployment(double densidadeDeUsuarios){
		this.previsao = new PrevisaoDeTrafego(densidadeDeUsuarios);
		this.nome = "Macro Only Deployment";
		this.densidadeDeUsuarios = densidadeDeUsuarios;
		this.run();
	}
	
	/**
	 * Performs the calculation of Macro Density, Macro Cell Range, Number of Macros, Number of Active Users Per Macro and Power consumption of Macro Only Architecture 
	 */
	public void run(){
		//Macro Density
		this.densidadeDeMacros = Util.getProdutoPorEscalar(previsao.getPrevisaoDeTrafego(), (1.0/Macro.capacidadeDaCelula));
		System.out.println("Macro Density");
		Util.imprime(this.densidadeDeMacros);
		
		//Macro cell range
		double[] temp = Util.getProdutoPorEscalar(this.densidadeDeMacros, (1.5*Math.sqrt(3)));
		this.alcanceCelulaMacro = Util.getPotencia((Util.getPotencia(temp, -1.0)),1.0/2.0);
		System.out.println("Macro Cell Range");
		Util.imprime(this.alcanceCelulaMacro);
		System.out.println();
		
		//Number of Macros
		this.numeroDeMacros = Util.getProdutoPorEscalar(this.densidadeDeMacros, Environment.area);
		System.out.println("Number of Macros");
		Util.imprime(this.numeroDeMacros);
		System.out.println();
		
		//Number of Active users per Macro
		this.numeroDeUsuarioAtivosPorMacro =  Util.getProdutoPorEscalar(Util.getPotencia(this.previsao.getTaxaMediaPorUsuarioExtendida(), -1.0), Macro.capacidadeDaCelula);
		System.out.println("Number of Active users per Macro");
		Util.imprime(this.numeroDeUsuarioAtivosPorMacro);
		System.out.println();
		
		//Power consumption of Macro Only Architecture
		this.potencia = Util.getSoma(Util.getProdutoPorEscalar(this.numeroDeMacros,Macro.potencia),Util.getProdutoPorEscalar(this.numeroDeMacros, 2.0*Microwave.potenciaBaixa));		
		System.out.println("Power consumption of Macro Only Architecture:");
		Util.imprime(this.potencia);
		System.out.println();
	}
}