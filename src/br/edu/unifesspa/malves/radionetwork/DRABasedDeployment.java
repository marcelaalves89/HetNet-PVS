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
public class DRABasedDeployment extends MacroOnlyDeployment {

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
	}

	/**
	 * Total Number of MacroBS's
	 */
	public void getNumeroDeMacros() {	
		double densidadeDeUsuariosIndoor = this.densidadeDeUsuarios*Environment.porcentagemUsuariosIndoor;
		double densidadeDeUsuariosOutdoor = this.densidadeDeUsuarios*Environment.porcentagemUsuariosOutdoor;
		for (int i=0; i<DRA.taxaDePenetracao.length; i++){
			double mu = (1-DRA.taxaDePenetracao[i]);
			double temp = (densidadeDeUsuariosOutdoor + (densidadeDeUsuariosIndoor*mu))*Environment.alphaMaximo*Environment.area;
			this.numeroDeMacros[i] = Util.getDivisao(this.numeroDeUsuarioAtivosPorMacro, temp);
		}
}

	/**
	 * Total Number of DRA antennas, DRA Antennas per Building
	 */
	public void getNumeroDRAS(){
		this.numeroMaximoDePrediosComDRA = Util.getProdutoPorEscalar(DRA.taxaDePenetracao,Environment.numeroMaximoPredios);

		for (int i=0; i<DRA.taxaDePenetracao.length; i++){
			this.numeroDeAntenasDRA[i][0] = this.numeroMaximoDePrediosComDRA[i]*Environment.numeroDeAndaresPorPredio;
			if (i>0)
				this.numeroDeAntenasDRAPorPredio[i] = (this.numeroDeAntenasDRA[i][0]/this.numeroMaximoDePrediosComDRA[i]);
			else this.numeroDeAntenasDRAPorPredio[i] = 0;
		}
		
		System.out.println("Hugo1");
		Util.imprime(numeroDeAntenasDRA);
	}
}