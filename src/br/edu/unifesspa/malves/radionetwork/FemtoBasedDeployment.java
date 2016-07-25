package br.edu.unifesspa.malves.radionetwork;

import br.edu.unifesspa.malves.trafficforecast.Environment;
import br.edu.unifesspa.malves.util.Util;
import br.edu.unifesspa.malves.wireless.Femto;

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
		this.nome = "Femto-Based Deployment";
		int x = Femto.taxaDePenetracao.length, y=this.previsao.getPrevisaoDeTrafego().length;

		this.numeroDeMacros = new double[x][y];
		this.numeroDeFemtos = new double[x][1];
		this.numeroMaximoDePrediosComFemto = new double[x];
		this.numeroDeFemtosPorPredio = new double[x];
		
		this.getNumeroDeMacros();
		this.getNumeroFemtos();
		//this.debug();
	}
	
	/**
	 * Performs the calculation of Total Number of MacroBS's
	 */
	public void getNumeroDeMacros() {		
		double densidadeDeUsuariosIndoor = this.densidadeDeUsuarios*Environment.porcentagemUsuariosIndoor;
		double densidadeDeUsuariosOutdoor = this.densidadeDeUsuarios*Environment.porcentagemUsuariosOutdoor;
		for (int i=0; i<Femto.taxaDePenetracao.length; i++){
			double temp = (densidadeDeUsuariosOutdoor + (densidadeDeUsuariosIndoor*(1-Femto.taxaDePenetracao[i])))*Environment.alphaMaximo*Environment.area;
			this.numeroDeMacros[i] = Util.getDivisao(this.numeroDeUsuarioAtivosPorMacro, temp);
		}
	}

	/**
	 * Performs the calculation of Total Number of FemtoBS's
	 */
	public void getNumeroFemtos(){
		double num_House = (this.densidadeDeUsuarios*Environment.area)/3.0;
		
		//NumBuilding_max = Num_House/Num_apartaments_per_building
		double numBuildingMax = num_House/(Environment.numeroApartamentosPorPredio);
		
		//eta_f_numBuildings = NumBuilding_max * eta_f
		this.numeroMaximoDePrediosComFemto = Util.getProdutoPorEscalar(Femto.taxaDePenetracao,numBuildingMax);		
		
		for (int i=0; i<Femto.taxaDePenetracao.length; i++){
			//N_femto(i) = Num_House*eta*(1-offgain)
			this.numeroDeFemtos[i][0] = num_House * Femto.taxaDePenetracao[i] *(1-FemtoBasedDeployment.offGain);
			if (i>0)
				this.numeroDeFemtosPorPredio[i] = Math.rint((this.numeroDeFemtos[i][0]/this.numeroMaximoDePrediosComFemto[i]));
			else this.numeroDeFemtosPorPredio[i] = 0;
		}
	}

	/**
	 * Print values for Debug
	 */
	public void debug(){
		System.out.println("Number of Macro BS's in HetNet: ");
		Util.imprime(this.numeroDeMacros);

	}
}