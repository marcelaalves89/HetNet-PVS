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
	 * 	Super constructor call and initializing values
	 */
	public DRABasedDeployment(double densidadeDeUsuarios){
		super(densidadeDeUsuarios);
		this.name = "DRA-Based Deployment";

		this.numeroDeMacros = new double[DRA.penetrationRate.length][this.prevision.getPrevisaoDeTrafego().length];
		this.numeroDeAntenasDRA = new double[DRA.penetrationRate.length][1];
		this.numeroMaximoDePrediosComDRA = new double[DRA.penetrationRate.length];

		this.getNumeroDeMacros();
		this.getNumeroDRAS();
	}

	/**
	 * Total Number of MacroBS's
	 */
	public void getNumeroDeMacros() {
		for (int i=0; i<DRA.penetrationRate.length; i++){
			double temp = this.userDensity*Environment.area*Environment.alphaMaximo*(1-DRA.penetrationRate[i]*Environment.porcentagemUsuariosIndoor);
			this.numeroDeMacros[i] = Util.getDivisao(this.numOfActiveUsersPerMacro, temp); 
		}
	}

	/**
	 * Total Number of DRA antennas, DRA Antennas per Building
	 */
	public void getNumeroDRAS(){
		for (int i=0; i<DRA.penetrationRate.length; i++){
			this.numeroMaximoDePrediosComDRA[i] = DRA.penetrationRate[i]*Environment.numeroMaximoPredios;
			this.numeroDeAntenasDRA[i][0] = this.numeroMaximoDePrediosComDRA[i]*Environment.numeroDeAndaresPorPredio;			
		}
	}
}