package br.edu.unifesspa.malves.transportnetwork;

import br.edu.unifesspa.malves.backhaul.Microwave;
import br.edu.unifesspa.malves.photovoltaics.Inverter;
import br.edu.unifesspa.malves.photovoltaics.Meter;
import br.edu.unifesspa.malves.photovoltaics.Panel;
import br.edu.unifesspa.malves.radionetwork.DRABasedDeployment;
import br.edu.unifesspa.malves.tco.CAPEX;
import br.edu.unifesspa.malves.tco.OPEX;
import br.edu.unifesspa.malves.trafficforecast.Environment;
import br.edu.unifesspa.malves.util.Util;
import br.edu.unifesspa.malves.wireless.Macro;

public abstract class DRAPVBased extends DRABasedDeployment{

	/**
	 * Total Power Consumption of DRA-CF Deployment, including the power consumption of the outdoor macro BS network
	 */
	public double[][] potenciaTotal;

	/**
	 * Partial Power Consumption of Macro+DRA-CF Architecture
	 */
	public double[] consumoTotal;

	/**
	 * Power Consumption of only DRA-CF Deployment
	 */
	public double[][] potenciaDRAOnly;

	/**
	 * Power Consumption of MacroBS Only
	 */
	public double[][] potenciaMacroOnly;

	/**
	 * Total number of Panels
	 */
	public double[] numeroInversores;

	/**
	 * Generated power
	 */
	public double[] potenciaGerada;

	/**
	 * TCO
	 */
	public double[] tco;

	/**
	 * 
	 */
	public double[] estatisticas;

	/**
	 * 
	 */
	public double radiacao;

	/**
	 * 
	 */
	public double numeroPaineisPorInversor;

	/**
	 * Super constructor call and initializing values
	 */
	public DRAPVBased(double radiacao, double densidadeDeUsuarios){
		super(densidadeDeUsuarios);
		this.nome = "DRA-PV Based Architecture";
		int dimensao = super.numeroDeAntenasDRA.length;
		this.potenciaDRAOnly = new double [dimensao][dimensao];
		this.potenciaMacroOnly = new double [dimensao][dimensao];
		this.potenciaTotal = new double [dimensao][dimensao];
		this.numeroInversores = new double[dimensao];
		this.consumoTotal = new double[dimensao];
		this.potenciaGerada = new double[dimensao];
		this.tco = new double[dimensao];
		this.estatisticas = new double[5];
		this.radiacao = radiacao;
	}

	/**
	 * Calculating the Total Power Consumption of Macro+DRA-CF Architecture (KWH)
	 */
	public void getConsumoMacro(){				
		this.potenciaMacroOnly = Util.getSoma(Util.getProdutoPorEscalar(super.numeroDeMacros,Macro.potencia),Util.getProdutoPorEscalar(this.numeroDeMacros, 2.0*Microwave.potenciaBaixa));		
		Util.converterEmKWH(potenciaMacroOnly);
	}

	/**
	 * 
	 */
	public void getPotenciaDeGeracao(){
		this.consumoTotal = Util.getDiagonalPrincipal(this.potenciaTotal);		
		double[][] matrizDePotencia = Util.getZeros(this.consumoTotal.length, this.consumoTotal.length);

		double potenciaSaidaPainel = Panel.area*Panel.eficiencia*this.radiacao;
		this.numeroPaineisPorInversor = (Inverter.potenciaNominalEntrada*Panel.hspPadrao)/potenciaSaidaPainel;
		double potenciaSaidaInversor = Inverter.eficiencia*this.numeroPaineisPorInversor*potenciaSaidaPainel;
		
		for (int i=0; i<this.potenciaGerada.length; i++){
			matrizDePotencia[i][i] = this.consumoTotal[i] - Util.getSomaPorColuna(matrizDePotencia, i);
			this.numeroInversores[i] = matrizDePotencia[i][i]/potenciaSaidaInversor;
			
			double consumoMinimo = this.numeroInversores[i] * Meter.consumoMinimo;
			this.numeroInversores[i] = (matrizDePotencia[i][i]-consumoMinimo)/potenciaSaidaInversor;
			
			matrizDePotencia[i][i] = this.numeroInversores[i] * potenciaSaidaInversor;
			Util.getDepreciacao(matrizDePotencia,Panel.taxaDesempenho);			
		}
		this.potenciaGerada = Util.getSomaPorColuna(matrizDePotencia);
	}

	/**
	 * Calculate TCO
	 */
	public void getTCO(){
		double[][] matrizCAPEX = Util.getZeros(this.consumoTotal.length, this.consumoTotal.length);
		double[][] matrizOPEX = Util.getZeros(this.consumoTotal.length, this.consumoTotal.length);
		double opex = 0;
		for (int i=0; i<this.consumoTotal.length; i++){			
			double numeroTotalDePaineis = this.numeroInversores[i]*this.numeroPaineisPorInversor;

			//CAPEX
			matrizCAPEX[i][i] = (numeroTotalDePaineis*Panel.custoPorPainel 
					+ numeroInversores[i]*Inverter.custo) 
					+ numeroTotalDePaineis * Panel.custoKitInstalacao;
			
			double capexInicial = matrizCAPEX[i][i]; 			
			Util.getDepreciacao(matrizCAPEX, CAPEX.taxaDepreciacaoFinanceira);			

			//OPEX
			for (int j=0; j<matrizOPEX.length; j++)
				matrizOPEX[i][i] = matrizCAPEX[i][i]*OPEX.taxaManutencao;
			Util.getDepreciacao(matrizOPEX, 1);
			
			matrizOPEX[i][i] += capexInicial * OPEX.taxaInstalacao;
			matrizOPEX[i][i] += this.numeroInversores[i]*Meter.consumoMinimo*Meter.custoKwhCompra;
			matrizOPEX[i][i] += this.numeroInversores[i]*Meter.custoInstalacao;

			opex = Util.getSomaColunasVetor(Util.getSomaPorColuna(matrizOPEX));
		}
		this.tco = Util.getSomaPorColuna(matrizCAPEX);
		this.tco[20] += opex;
	}

	/**
	 * 
	 */
	public void getEstatisticas(){		
		double somaConsumo = 0, somaGeracao = 0, diferenca = 0;
		for (int i=0; i<consumoTotal.length; i++){
			somaConsumo += (this.consumoTotal[i]*365);
			somaGeracao += (this.potenciaGerada[i]*365);
		}
		diferenca = somaGeracao - somaConsumo; 
		this.estatisticas[0] = somaConsumo;
		this.estatisticas[1] = somaGeracao;
		this.estatisticas[2] = this.tco[20];

		double temp = (somaConsumo*Meter.custoKwhCompra)+(diferenca*Meter.custoKwhVenda)-this.tco[20];
		this.estatisticas[3] = (temp/(this.densidadeDeUsuarios*Environment.area))/Environment.anos.length;
	}

	/**
	 * 
	 */
	public abstract void getConsumoDRA();
	
	/**
	 * 
	 */
	public abstract void debug();
}
