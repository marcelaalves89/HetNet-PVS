package br.edu.unifesspa.malves.radionetwork;

import br.edu.unifesspa.malves.trafficforecast.Environment;
import br.edu.unifesspa.malves.util.Util;
import br.edu.unifesspa.malves.wireless.DRA;
import br.edu.unifesspa.malves.wireless.Femto;

/**
 * 
 * @author	Marcela Alves
 * @since	2016-06-12
 *
 */
public class FemtoBasedDeployment extends MacroOnlyDeployment {

	/**
	 * Represents the number of MacroBS in HetNet Macro+Femto
	 */
	public double[][] numeroDeMacros;

	/**
	 * Represents the total number of Femto BS's
	 */
	public double[][] numeroDeFemtos;

	/**
	 * Represents the max number of building equipped with Femto BS's
	 */
	public double[] numeroMaximoDePrediosComFemto;

	/**
	 * Represents the number of Femto BS's per Building
	 */
	public double[] numeroDeFemtosPorPredio;
	
	/**
	 * Represents the increased number of outdoor users that macrocell can support after offloading costly indoor traffic to DRA
	 */
	public static final double offGain = 0;

	/**
	 * Superclass constructor call and initializing values
	 */
	public FemtoBasedDeployment(double densidadeDeUsuarios){
		super(densidadeDeUsuarios);
		this.name = "Femto-Based Deployment";
		int x = Femto.penetrationRate.length, y=this.prevision.getPrevisaoDeTrafego().length;

		this.numeroDeMacros = new double[x][y];
		this.numeroDeFemtos = new double[x][1];
		this.numeroMaximoDePrediosComFemto = new double[x];
		this.numeroDeFemtosPorPredio = new double[x];
		
		this.getNumeroDeMacros();
		this.getNumeroFemtos();
	}
	
	/**
	 * Performs the calculation of Total Number of MacroBS's
	 */
	public void getNumeroDeMacros() {		
		for (int i=0; i<DRA.penetrationRate.length; i++){
			double temp = this.userDensity*Environment.area*Environment.alphaMaximo* (1-Femto.penetrationRate[i]);
			this.numeroDeMacros[i] = Util.getDivisao(this.numOfActiveUsersPerMacro, temp);
		}
	}

	/**
	 * Performs the calculation of Total Number of FemtoBS's
	 */
	public void getNumeroFemtos(){
		for (int i=0; i<Femto.penetrationRate.length; i++)
			this.numeroDeFemtos[i][0] = (Environment.numeroMaximoPredios*Environment.numeroApartamentosPorPredio*Femto.penetrationRate[i])/Environment.porcentagemUsuariosIndoor;
	}

	/**
	 * Print values for Debug
	 */
	public void debug(){
	
	}
}