package br.edu.unifesspa.malves.transportnetwork;

import br.edu.unifesspa.malves.backhaul.Microwave;
import br.edu.unifesspa.malves.photovoltaics.Inverter;
import br.edu.unifesspa.malves.photovoltaics.Meter;
import br.edu.unifesspa.malves.photovoltaics.Panel;
import br.edu.unifesspa.malves.radionetwork.FemtoBasedDeployment;
import br.edu.unifesspa.malves.tco.CAPEX;
import br.edu.unifesspa.malves.tco.OPEX;
import br.edu.unifesspa.malves.trafficforecast.Environment;
import br.edu.unifesspa.malves.util.Util;
import br.edu.unifesspa.malves.wireless.Macro;

/**
 * 
 * @author	Marcela Alves
 * @since	2016-07-12
 *
 */
public abstract class FemtoPVBased extends FemtoBasedDeployment{

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
	 * Total number of String Inverters
	 */
	public double[] numeroInversores;
	
	/**
	 * Total number of PV Panels
	 */
	public double[] numeroPaineis;

	/**
	 * Generated power
	 */
	public double[] energiaGerada;
	
	/**
	 * TCO
	 */
	public double tco;
	
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
		this.name = "Femto-BB Architecture";
		int dimensao = super.numeroDeFemtos.length;	
		this.potenciaFemtoOnly = new double [dimensao][dimensao];
		this.potenciaMacroOnly = new double [dimensao][dimensao];
		this.potenciaTotal = new double [dimensao][dimensao];
		this.numeroInversores = new double[dimensao];
		this.numeroPaineis = new double[dimensao];
		this.consumoTotal = new double[dimensao];
		this.consumoASerGerado = new double[dimensao];
		this.energiaGerada = new double[dimensao];
		this.tco = 0;
		this.estatisticas = new double[6];
		this.radiacao = radiacao;
	}
	
	/**
	 * Calculating the Total Power Consumption of Macro+DRA-CF Architecture (KWH)
	 */
	public void getConsumoMacro(){				
		this.potenciaMacroOnly = Util.getProdutoPorEscalar(super.numeroDeMacros,Macro.power+(2.0*Microwave.lowPower));		
		Util.converterEmKWH(potenciaMacroOnly);
	}

	/**
	 * 
	 */
	public void getPotenciaDeGeracao(){
		this.consumoTotal = Util.getDiagonalPrincipal(this.potenciaTotal);
		double[][] matrizDePotencia = Util.getZeros(this.consumoTotal.length, this.consumoTotal.length);
		this.matrizConsumoMinimo = Util.getZeros(this.consumoTotal.length, this.consumoTotal.length);

		double geracaoDiariadoPainel = Panel.area*Panel.efficiency*this.radiacao;
		this.numeroPaineisPorInversor = Math.floor((Inverter.ratedDCInput*Environment.defaultHSP)/geracaoDiariadoPainel);		
		double potenciaSaidaInversor = Inverter.efficiecy*this.numeroPaineisPorInversor*geracaoDiariadoPainel;
	
		for (int i=0; i<this.energiaGerada.length; i++){
									
			this.consumoASerGerado[i] = this.consumoTotal[i] - Util.getSomaPorColuna(matrizDePotencia, i);
			double numeroDeMedidores = Math.ceil(this.consumoASerGerado[i]/potenciaSaidaInversor);
			this.numeroPaineis[i] = numeroDeMedidores * this.numeroPaineisPorInversor;
			this.matrizConsumoMinimo[i][i] = numeroDeMedidores * Meter.dailyMinimumConsumptionPerMeter;
			
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
		//CAPEX e OPEX
		double capex = 0;
		double opex = 0;
		double[][] matrizOPEX = Util.getZeros(this.consumoTotal.length, this.consumoTotal.length);
		for (int i=0; i<this.consumoTotal.length; i++){
			double temp = Math.pow(CAPEX.taxaDepreciacaoFinanceira, i);
			if (temp < 0.6)
				temp = 0.6;
				
			capex += this.numeroPaineis[i]*Panel.price*temp;
			capex += this.numeroPaineis[i]*Panel.custoKitInstalacao*temp;
			capex += this.numeroInversores[i]*Inverter.price*temp;
			
			matrizOPEX[i][i] = this.numeroPaineis[i]*Panel.price*OPEX.taxaManutencao;
			Util.getDepreciacao(matrizOPEX, 1);			
			matrizOPEX[i][i] += this.numeroInversores[i]*Meter.meterInstallationCost;
			
			for (int j=i; j<matrizOPEX[0].length; j++){
				if (j+Inverter.warrantyTime < matrizOPEX[0].length)
					matrizOPEX[i][j+(int)Inverter.warrantyTime] += this.numeroInversores[i] * Inverter.price * OPEX.taxaManutencao;
				if (j+Panel.installationKitWarrantyTime < matrizOPEX[0].length)
					matrizOPEX[i][j+(int)Panel.installationKitWarrantyTime] += this.numeroPaineis[i] * Panel.custoKitInstalacao * OPEX.taxaManutencao;				
				matrizOPEX[i][j] += this.numeroInversores[i]*Meter.dailyMinimumConsumptionPerMeter*Meter.purchaseCostOfKWH*365;
				matrizOPEX[i][j] += this.numeroPaineis[i]*Panel.area*OPEX.custoAluguelDiario*365;											
			}
			opex = Util.getSomaColunasVetor(Util.getSomaPorColuna(matrizOPEX));			
		}
		capex = capex * (1+CAPEX.taxaDeInstalacao);
		this.tco = capex + opex;

	}

	/**
	 * 
	 */
	public void getEstatisticas(){		
		double somaConsumo = 0, somaGeracao = 0, somaASerGerada = 0;
		//double diferenca = 0;
		
		double[] disponibilidadeMinimoDaRede = Util.getSomaPorColuna(this.matrizConsumoMinimo);
		double[] energiaMinimaASerGerada = Util.getDiferenca(this.consumoTotal, disponibilidadeMinimoDaRede);

		for (int i=0; i<consumoTotal.length; i++){
			somaConsumo += (this.consumoTotal[i]*365);			
			somaGeracao += (this.energiaGerada[i]*365);
			somaASerGerada += (energiaMinimaASerGerada[i]*365);
		}		
				
		//diferenca = somaGeracao - somaASerGerada;		
		this.estatisticas[0] = somaConsumo;
		this.estatisticas[1] = somaGeracao;		
		this.estatisticas[2] = this.tco;	
		
		double temp = ((somaConsumo*Meter.purchaseCostOfKWH))-this.tco;
		//double temp = ((somaConsumo*Meter.custoKwhCompra)+(diferenca*Meter.custoKwhVenda))-this.tco;
		this.estatisticas[3] = (temp/(this.userDensity*Environment.area))/15;		
		//this.estatisticas[3] = (temp/(this.userDensity*Environment.area))/Environment.anos.length;
		this.estatisticas[5] = somaASerGerada;				
	}
		
	public abstract void getConsumoFemto();
	
	public abstract void debug();
}