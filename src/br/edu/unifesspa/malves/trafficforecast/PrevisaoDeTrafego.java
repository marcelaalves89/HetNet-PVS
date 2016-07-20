package br.edu.unifesspa.malves.trafficforecast;

import br.edu.unifesspa.malves.util.Util;

public class PrevisaoDeTrafego {	
	
	private double[] taxaMediaPorUsuario;
	
	private double[] taxaMediaPorUsuarioExtendida;
	
	private double[] picoDaDemandaDeTrafegoNaArea;
	
	private double[] previsaoDeTrafego;
	
	private double taxaTotalMediaPorUsuario;
	
	private double demandaDeTrafegoMaxima;
	
	private double densidadeDeUsuarios;
	
	public PrevisaoDeTrafego(double densidadeDeUsuarios){
		
		//Logging
		System.out.println("Initiating Traffic Forecast Phase...");
		System.out.println();
		
		this.densidadeDeUsuarios = densidadeDeUsuarios;
		
		//Heavy and ordinary user fraction, based on EARTH ans assumptions
		double[] portentagemTrafegoPesado = {0.1, 0.2, 0.3};
		double[] portentagemTrafegoOrdinario = {0.9, 0.8, 0.7};
		double[][] fracaoDeUsuarios = new double[2][3];
		fracaoDeUsuarios[0] = portentagemTrafegoPesado;
		fracaoDeUsuarios[1] = portentagemTrafegoOrdinario;

		//Average traffic demand per terminal during busy hour (MB/hour)
		//Rows: PC/Tablet/Smartphone; Columns: 2010/2015/2020
		double[][] trafegoPesadoPorTerminal = {{56.25,900,2700},{28.1,450,1350},{7.03,112.5,337.5}};
		double[][] trafegoOrdinarioPorTerminal = Util.getProdutoPorEscalar(trafegoPesadoPorTerminal, 1.0/8.0);
		
		//Translate them into Mbps
		double[][] taxaDeTrafegoPesadoPorTerminal = Util.getProdutoPorEscalar(trafegoPesadoPorTerminal, 8.0/3600.0);
		double[][] taxaDeTrafegoOrdinarioPorTerminal = Util.getProdutoPorEscalar(trafegoOrdinarioPorTerminal, 8.0/3600.0);
		taxaDeTrafegoOrdinarioPorTerminal[0][0] = 0.031;
		
		//Average terminal demand over years
		double[][] temp = new double[2][3];
		double[][] taxaMedia = new double[3][3];
		for (int i=0; i<taxaDeTrafegoPesadoPorTerminal.length; i++){
			temp[0] = Util.getColunaMatriz(taxaDeTrafegoPesadoPorTerminal, i);
			temp[1] = Util.getColunaMatriz(taxaDeTrafegoOrdinarioPorTerminal, i);
			taxaMedia[i] = Util.getProdutoMatricial(Util.getTransposta(Util.getColunaMatriz(fracaoDeUsuarios, i)), temp)[0];
		}
		taxaMedia = Util.getTransposta(taxaMedia);

		double[][] taxas = Util.getProdutoMatricialPorElemento(taxaMedia, Environment.taxaPenetracaoTerminais);


		this.taxaMediaPorUsuario = Util.getSomaPorColuna(taxas);
		this.taxaTotalMediaPorUsuario = Util.getSomaColunasVetor(taxaMediaPorUsuario);
	
		this.picoDaDemandaDeTrafegoNaArea = Util.getProdutoPorEscalar(this.taxaMediaPorUsuario, (this.densidadeDeUsuarios*Environment.alphaMaximo));
		
		double temp1 = Math.pow((Util.getMinEMaximo(picoDaDemandaDeTrafegoNaArea).get("maximo")/Util.getMinEMaximo(picoDaDemandaDeTrafegoNaArea).get("minimo")), 1.0/12.0);
		int numeroDePassos = 21;
		this.previsaoDeTrafego = new double[numeroDePassos];

		for (int i=1; i<=numeroDePassos; i++)
			this.previsaoDeTrafego[i-1] = (Math.pow(temp1, i-1))*Util.getMinEMaximo(picoDaDemandaDeTrafegoNaArea).get("minimo").doubleValue();
		
		this.taxaMediaPorUsuarioExtendida = Util.getProdutoPorEscalar(this.previsaoDeTrafego, 1.0/480.0);
		
		this.demandaDeTrafegoMaxima = this.previsaoDeTrafego[14];
		
		System.out.println("Ending Traffic Forecast Phase...");
		System.out.println();
	}
	
	public void debug(){
		
	}

	public double[] getTaxaMediaPorUsuario() {
		return taxaMediaPorUsuario;
	}

	public void setTaxaMediaPorUsuario(double[] taxaMediaPorUsuario) {
		this.taxaMediaPorUsuario = taxaMediaPorUsuario;
	}

	public double getTaxaTotalMediaPorUsuario() {
		return taxaTotalMediaPorUsuario;
	}

	public void setTaxaTotalMediaPorUsuario(double taxaTotalMediaPorUsuario) {
		this.taxaTotalMediaPorUsuario = taxaTotalMediaPorUsuario;
	}

	public double[] getPicoDaDemandaDeTrafegoNaArea() {
		return picoDaDemandaDeTrafegoNaArea;
	}

	public void setPicoDaDemandaDeTrafegoNaArea(
			double[] picoDaDemandaDeTrafegoNaArea) {
		this.picoDaDemandaDeTrafegoNaArea = picoDaDemandaDeTrafegoNaArea;
	}

	public double[] getTaxaMediaPorUsuarioExtendida() {
		return taxaMediaPorUsuarioExtendida;
	}

	public void setTaxaMediaPorUsuarioExtendida(
			double[] taxaMediaPorUsuarioExtendida) {
		this.taxaMediaPorUsuarioExtendida = taxaMediaPorUsuarioExtendida;
	}

	public double[] getPrevisaoDeTrafego() {
		return previsaoDeTrafego;
	}

	public void setPrevisaoDeTrafego(double[] previsaoDeTrafego) {
		this.previsaoDeTrafego = previsaoDeTrafego;
	}

	public double getDemandaDeTrafegoMaxima() {
		return demandaDeTrafegoMaxima;
	}

	public void setDemandaDeTrafegoMaxima(double demandaDeTrafegoMaxima) {
		this.demandaDeTrafegoMaxima = demandaDeTrafegoMaxima;
	}
}