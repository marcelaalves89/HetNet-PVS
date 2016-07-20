package br.edu.unifesspa.malves.radionetwork;

import br.edu.unifesspa.malves.trafficforecast.Environment;
import br.edu.unifesspa.malves.util.Util;
import br.edu.unifesspa.malves.wireless.DRA;

/**
 * 
 * @author	Marcela Alves
 * @since	2016-06-18
 *
 */
public class DRABasedDeployment extends MacroOnlyDeployment implements HetNet {

	/**
	 * Represents the max number of building equipped with DRA systems
	 */
	public double[] numeroMaximoDePrediosComDRA;

	/**
	 * Represents the number of MacroBS in HetNet Macro+DRA
	 */
	public double[][] numeroDeMacros;

	/**
	 * Represents the total number of DRA Antennas
	 */
	public double[][] numeroDeAntenasDRA;

	/**
	 * Represents the number of DRA Antennas per Building
	 */
	public double[] numeroDeAntenasDRAPorPredio; 

	/**
	 * Represents the increased number of outdoor users that macrocell can support after offloading costly indoor traffic to DRA
	 */
	public static final double offGain = 0;

	/**
	 * 	Super constructor call and initializing values
	 */
	public DRABasedDeployment(double densidadeDeUsuarios){
		super(densidadeDeUsuarios);
		this.nome = "DRA-Based Deployment";

		this.numeroDeMacros = new double[DRA.taxaDePenetracao.length][this.previsao.getPrevisaoDeTrafego().length];
		this.numeroDeAntenasDRA = new double[DRA.taxaDePenetracao.length][1];		
		this.numeroDeAntenasDRAPorPredio = new double[DRA.taxaDePenetracao.length];
		this.numeroMaximoDePrediosComDRA = new double[DRA.taxaDePenetracao.length];

		this.getNumeroDeMacros();
		this.getNumeroDRAS();
		//this.debug();
	}

	/**
	 * Performs the calculation of Total Number of MacroBS's
	 */
	public void getNumeroDeMacros() {	
		double densidadeDeUsuariosIndoor = this.densidadeDeUsuarios*0.8;
		double densidadeDeUsuariosOutdoor = this.densidadeDeUsuarios*0.2;
		for (int i=0; i<DRA.taxaDePenetracao.length; i++){
			double temp = (densidadeDeUsuariosOutdoor + (densidadeDeUsuariosIndoor*(1-DRA.taxaDePenetracao[i])))*Environment.alphaMaximo*Environment.area;
			this.numeroDeMacros[i] = Util.getDivisao(this.numeroDeUsuarioAtivosPorMacro, temp);
		}
	}

	/**
	 * Performs the calculation of Total Number of DRA antennas, Number of DRA Antennas per Building and Number of Macros Heterogeneous Network
	 */
	public void getNumeroDRAS(){
		this.numeroMaximoDePrediosComDRA = Util.getProdutoPorEscalar(DRA.taxaDePenetracao,Environment.numeroMaximoPredios);

		for (int i=0; i<DRA.taxaDePenetracao.length; i++){
			this.numeroDeAntenasDRA[i][0] = this.numeroMaximoDePrediosComDRA[i]*Environment.numeroDeAndaresPorPredio;
			if (i>0)
				this.numeroDeAntenasDRAPorPredio[i] = (this.numeroDeAntenasDRA[i][0]/this.numeroMaximoDePrediosComDRA[i]);
			else this.numeroDeAntenasDRAPorPredio[i] = 0;
		}		 
	}

	/**
	 * 
	 */
	public void debug(){
		//Printing values for debug
		System.out.println("Max Number of DRA Buildings:");
		Util.imprime(this.numeroMaximoDePrediosComDRA);
		System.out.println();

		//Printing the total number of DRA antennas
		System.out.println("Total Number of DRA antennas:");
		Util.imprime(this.numeroDeAntenasDRA);
		System.out.println();

		//Printing the number of DRA antennas per building
		System.out.println("Number of DRA Antennas per Building:");
		Util.imprime(this.numeroDeAntenasDRAPorPredio);
		System.out.println();

		//Printing the number of macros heterogeneous network
		System.out.println("Number of Macros (Macro+DRA-CF Architecture):");
		Util.imprime(this.numeroDeMacros);
		System.out.println();
	}
}