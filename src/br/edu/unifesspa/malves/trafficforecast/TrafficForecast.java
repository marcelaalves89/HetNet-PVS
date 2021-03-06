package br.edu.unifesspa.malves.trafficforecast;

import br.edu.unifesspa.malves.util.Util;

/**
 * 
 * @author	Marcela Alves
 * @since	2016-07-12
 */
public class TrafficForecast {	
	
	public double[] taxaMediaPorUsuario;
	
	public double[] taxaMediaPorUsuarioExtendida;
	
	public double[] picoDaDemandaDeTrafegoNaArea;
	
	public double[] previsaoDeTrafego;
	
	public double taxaTotalMediaPorUsuario;
	
	public double demandaDeTrafegoMaxima;
	
	/**
	 * User Density
	 */
	public double densidadeDeUsuarios;
	
	/**
	 * Heavy and ordinary user fraction, based on EARTH Assumptions
	 */
	public final double[][] fracaoDeUsuarios = {{0.1, 0.2, 0.3},{0.9, 0.8, 0.7}};
	
	public TrafficForecast(double densidadeDeUsuarios){
		this.densidadeDeUsuarios = densidadeDeUsuarios;
				
		//Average traffic demand per terminal during busy hour (MB/hour)
		//Rows: PC/Tablet/Smartphone; Columns: 2010/2015/2020
		double[][] trafegoPesadoPorTerminal = {{56.25,900,2700},{28.1,450,1350},{7.03,112.5,337.5}};
		double[][] trafegoOrdinarioPorTerminal = Util.getProductByInteger(trafegoPesadoPorTerminal, 1.0/8.0);
		
		//Translate them into Mbps		
		double[][] taxaDeTrafegoPesadoPorTerminal = Util.getProductByInteger(trafegoPesadoPorTerminal, 8.0/3600.0);
		double[][] taxaDeTrafegoOrdinarioPorTerminal = Util.getProductByInteger(trafegoOrdinarioPorTerminal, 8.0/3600.0);
		taxaDeTrafegoOrdinarioPorTerminal[0][0] = 0.031;
		
		//Average terminal demand over years
		double[][] temp = new double[2][3];
		double[][] taxaMedia = new double[3][3];
		for (int i=0; i<taxaDeTrafegoPesadoPorTerminal.length; i++){
			temp[0] = Util.getColunaMatriz(taxaDeTrafegoPesadoPorTerminal, i);
			temp[1] = Util.getColunaMatriz(taxaDeTrafegoOrdinarioPorTerminal, i);
			taxaMedia[i] = Util.getProdutoMatricial(Util.getTransposed(Util.getColunaMatriz(fracaoDeUsuarios, i)), temp)[0];
		}
		taxaMedia = Util.getTransposed(taxaMedia);
		
		double[][] taxas = Util.getProdutoMatricialPorElemento(taxaMedia, Environment.taxaPenetracaoTerminais);

		this.taxaMediaPorUsuario = Util.formatValues(Util.getSomaPorColuna(taxas));
		
		this.taxaTotalMediaPorUsuario = Util.getSomaColunasVetor(taxaMediaPorUsuario);
		
		this.picoDaDemandaDeTrafegoNaArea = Util.getProductByInteger(this.taxaMediaPorUsuario, (this.densidadeDeUsuarios*Environment.alphaMaximo));
		
		//Peak Area Traffic Demand Calculated with Matlab
		this.picoDaDemandaDeTrafegoNaArea[0] = 2.6083;
		this.picoDaDemandaDeTrafegoNaArea[1] = 82.8000;
		this.picoDaDemandaDeTrafegoNaArea[2] = 474.3000;
		
		double temp1 = Math.pow((Util.getMinEMaximo(picoDaDemandaDeTrafegoNaArea).get("maximo")/Util.getMinEMaximo(picoDaDemandaDeTrafegoNaArea).get("minimo")), 1.0/12.0);
		
		this.previsaoDeTrafego = new double[Environment.anos.length];

		for (int i=1; i<=this.previsaoDeTrafego.length; i++)
			this.previsaoDeTrafego[i-1] = (Math.pow(temp1, i-1))*Util.getMinEMaximo(picoDaDemandaDeTrafegoNaArea).get("minimo").doubleValue();
		
		this.taxaMediaPorUsuarioExtendida = Util.getProductByInteger(this.previsaoDeTrafego, 1.0/480.0);		
		this.demandaDeTrafegoMaxima = this.previsaoDeTrafego[14];
		
		double[] vetorAuxiliarA = new double[15];
		double[] vetorAuxiliarB = new double[15];
		for (int i=0; i<vetorAuxiliarA.length; i++){
			vetorAuxiliarA[i] = taxaMediaPorUsuarioExtendida[i+6];
			vetorAuxiliarB[i] = previsaoDeTrafego[i+6];
		}	
		
		taxaMediaPorUsuarioExtendida = vetorAuxiliarA;
		previsaoDeTrafego = vetorAuxiliarB;

	}

	public double[] getTaxaMediaPorUsuarioExtendida() {
		return taxaMediaPorUsuarioExtendida;
	}

	public double[] getPrevisaoDeTrafego() {
		return previsaoDeTrafego;
	}
}