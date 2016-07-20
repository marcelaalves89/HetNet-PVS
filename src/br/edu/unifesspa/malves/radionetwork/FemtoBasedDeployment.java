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
		double densidadeDeUsuariosIndoor = this.densidadeDeUsuarios*0.8;
		double densidadeDeUsuariosOutdoor = this.densidadeDeUsuarios*0.2;
		for (int i=0; i<Femto.taxaDePenetracao.length; i++){
			double temp = (densidadeDeUsuariosOutdoor + (densidadeDeUsuariosIndoor*(1-Femto.taxaDePenetracao[i])))*Environment.alphaMaximo*Environment.area;
			this.numeroDeMacros[i] = Util.getDivisao(this.numeroDeUsuarioAtivosPorMacro, temp);
		}
	}

	/**
	 * 
	 */
	public void getNumeroFemtos(){
		this.numeroMaximoDePrediosComFemto = Util.getProdutoPorEscalar(Femto.taxaDePenetracao,Environment.numeroMaximoPredios);

		for (int i=0; i<Femto.taxaDePenetracao.length; i++){
			this.numeroDeFemtos[i][0] = this.numeroMaximoDePrediosComFemto[i]*Environment.numeroDeAndaresPorPredio;
			if (i>0)
				this.numeroDeFemtosPorPredio[i] = (this.numeroDeFemtos[i][0]/this.numeroMaximoDePrediosComFemto[i]);
			else this.numeroDeFemtosPorPredio[i] = 0;
		}
	}

	/**
	 * 
	 */
	public void debug(){
		//Printing values for debug
		//Max Number of Buildings with Femto
		System.out.println("Numero de Predios com Femtos:");
		Util.imprime(this.numeroMaximoDePrediosComFemto);
		System.out.println();

		//Number of FemtoBS's
		System.out.println("Number of FemtoBS's:");
		Util.imprime(this.numeroDeFemtos);
		System.out.println();

		//Number of FemtoBS's per Building
		System.out.println("Number of FemtoBS's per Building:");
		Util.imprime(this.numeroDeFemtosPorPredio);
		System.out.println();

		//Number of Macro BS (in HetNet)
		System.out.println("Number of Macro BS (in HetNet): ");
		Util.imprime(this.numeroDeMacros);
		System.out.println();
	}
}