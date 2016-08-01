package br.edu.unifesspa.malves.transportnetwork;

import br.edu.unifesspa.malves.backhaul.Microwave;
import br.edu.unifesspa.malves.photovoltaics.Inverter;
import br.edu.unifesspa.malves.photovoltaics.Meter;
import br.edu.unifesspa.malves.photovoltaics.Panel;
import br.edu.unifesspa.malves.radionetwork.FemtoBasedDeployment2;
import br.edu.unifesspa.malves.tco.CAPEX;
import br.edu.unifesspa.malves.tco.OPEX;
import br.edu.unifesspa.malves.trafficforecast.Environment;
import br.edu.unifesspa.malves.util.Util;
import br.edu.unifesspa.malves.wireless.Macro;

public abstract class FemtoPVBased extends FemtoBasedDeployment2{

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
	public double[][] potenciaFemtoOnly;

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
	public double[] energiaGerada;
	
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

	private double[][] matrizConsumoMinimo;

	private double[] consumoASerGerado;

	/**
	 * Super constructor call and initializing values
	 */
	public FemtoPVBased(double radiacao, double densidadeDeUsuarios){
		super(densidadeDeUsuarios);
		this.nome = "Femto-BB Architecture";
		int dimensao = super.numeroDeFemtos.length;
		this.potenciaFemtoOnly = new double [dimensao][dimensao];
		this.potenciaMacroOnly = new double [dimensao][dimensao];
		this.potenciaTotal = new double [dimensao][dimensao];
		this.numeroInversores = new double[dimensao];
		this.consumoTotal = new double[dimensao];
		this.consumoASerGerado = new double[dimensao];
		this.energiaGerada = new double[dimensao];
		this.tco = new double[dimensao];
		this.estatisticas = new double[6];
		this.radiacao = radiacao;
	}
	
	/**
	 * Calculating the Total Power Consumption of Macro+DRA-CF Architecture (KWH)
	 */
	public void getConsumoMacro(){				
		this.potenciaMacroOnly = Util.getProdutoPorEscalar(super.numeroDeMacros,Macro.potencia+(2.0*Microwave.potenciaBaixa));		
		Util.converterEmKWH(potenciaMacroOnly);
	}

	/**
	 * 
	 */
	public void getPotenciaDeGeracao(){
		this.consumoTotal = Util.getDiagonalPrincipal(this.potenciaTotal);		
		double[][] matrizDePotencia = Util.getZeros(this.consumoTotal.length, this.consumoTotal.length);
		this.matrizConsumoMinimo = Util.getZeros(this.consumoTotal.length, this.consumoTotal.length);

		double potenciaSaidaPainel = Panel.area*Panel.eficiencia*this.radiacao;
		this.numeroPaineisPorInversor = (Inverter.potenciaNominalEntrada*Panel.hspPadrao)/potenciaSaidaPainel;		
		double potenciaSaidaInversor = Inverter.eficiencia*this.numeroPaineisPorInversor*potenciaSaidaPainel;
	
		for (int i=0; i<this.energiaGerada.length; i++){
									
			this.consumoASerGerado[i] = this.consumoTotal[i] - Util.getSomaPorColuna(matrizDePotencia, i);
			double numeroDeMedidores = Math.ceil(this.consumoASerGerado[i]/potenciaSaidaInversor);
			this.matrizConsumoMinimo[i][i] = numeroDeMedidores * Meter.consumoMinimo;
			
			this.consumoASerGerado[i] -= Util.getSomaPorColuna(this.matrizConsumoMinimo, i);			
			
			this.numeroInversores[i] = Math.ceil((this.consumoASerGerado[i])/potenciaSaidaInversor);
			matrizDePotencia[i][i] = this.numeroInversores[i] * potenciaSaidaInversor;
			
			Util.getDepreciacao(matrizDePotencia,Panel.taxaDesempenho);
			Util.getDepreciacao(this.matrizConsumoMinimo,1);
		}
		this.energiaGerada = Util.getSomaPorColuna(matrizDePotencia);
		this.estatisticas[4] = Util.getSomaColunasVetor(Util.getSomaPorColuna(this.matrizConsumoMinimo))*365/1000;		
	}
	
	/**
	 * Calculate TCO
	 */
	public void getTCO(){
		double[][] matrizCAPEX = Util.getZeros(this.consumoTotal.length, this.consumoTotal.length);
		double[][] matrizOPEX = Util.getZeros(this.consumoTotal.length, this.consumoTotal.length);
		double opex = 0;
		
		double somaPaineis = 0;
		
		
		for (int i=0; i<this.consumoTotal.length; i++){			
			double numeroTotalDePaineis = this.numeroInversores[i]*this.numeroPaineisPorInversor;
			
			somaPaineis += numeroTotalDePaineis; 
			
			double custoPaineis = numeroTotalDePaineis*Panel.custoPorPainel;
			double custoInversores = numeroInversores[i]*Inverter.custo;
			double custoKitDeInstalacao = numeroTotalDePaineis * Panel.custoKitInstalacao;
			
			//CAPEX
			matrizCAPEX[i][i] = (custoPaineis + custoInversores + custoKitDeInstalacao);
			Util.getDepreciacao(matrizCAPEX, CAPEX.taxaDepreciacaoFinanceira);
			matrizCAPEX[i][i] += CAPEX.taxaDeInstalacao*(custoPaineis + custoInversores + custoKitDeInstalacao);

			//OPEX
			matrizOPEX[i][i] = custoPaineis*OPEX.taxaManutencao;
			Util.getDepreciacao(matrizOPEX, 1);
			
			matrizOPEX[i][i] += this.numeroInversores[i]*Meter.custoInstalacao;
			
			for (int j=i; j<matrizOPEX[0].length; j++){
				if (j+Inverter.garantiaInversor < matrizOPEX[0].length)
					matrizOPEX[i][j+(int)Inverter.garantiaInversor] += custoInversores * OPEX.taxaManutencao;
				if (j+Panel.garantiaKitInstalacao < matrizOPEX[0].length)
					matrizOPEX[i][j+(int)Panel.garantiaKitInstalacao] += custoKitDeInstalacao * OPEX.taxaManutencao;				
				matrizOPEX[i][j] += this.numeroInversores[i]*Meter.consumoMinimo*Meter.custoKwhCompra*365;
			}							
			opex = Util.getSomaColunasVetor(Util.getSomaPorColuna(matrizOPEX));
		}
		opex += somaPaineis*Panel.area*Panel.custoAluguelDiario*365;
		this.tco = Util.getSomaPorColuna(matrizCAPEX);
		this.tco[20] += opex;
	}

	/**
	 * 
	 */
	public void getEstatisticas(){		
		double somaConsumo = 0, somaGeracao = 0, somaASerGerada = 0, diferenca = 0;
		
		double[] disponibilidadeMinimoDaRede = Util.getSomaPorColuna(this.matrizConsumoMinimo);
		double[] energiaMinimaASerGerada = Util.getDiferenca(this.consumoTotal, disponibilidadeMinimoDaRede);

		for (int i=0; i<consumoTotal.length; i++){
			somaConsumo += (this.consumoTotal[i]*365);			
			somaGeracao += (this.energiaGerada[i]*365);
			somaASerGerada += (energiaMinimaASerGerada[i]*365);
		}		
				
		diferenca = somaGeracao - somaASerGerada;		
		this.estatisticas[0] = somaConsumo;
		this.estatisticas[1] = somaGeracao;		
		this.estatisticas[2] = this.tco[20];	
		
		double temp = ((somaConsumo*Meter.custoKwhCompra)+(diferenca*Meter.custoKwhVenda))-this.tco[20];		
		this.estatisticas[3] = (temp/(this.densidadeDeUsuarios*Environment.area))/Environment.anos.length;		
		this.estatisticas[5] = somaASerGerada;				
	}
		
	public abstract void getConsumoFemto();
	
	public abstract void debug();
}