package br.edu.unifesspa.malves.tests;

import java.util.HashMap;

import org.jfree.ui.RefineryUtilities;

import br.edu.unifesspa.malves.photovoltaics.Panel;
import br.edu.unifesspa.malves.trafficforecast.Environment;
import br.edu.unifesspa.malves.transportnetwork.DRABF;
import br.edu.unifesspa.malves.transportnetwork.DRACF;
import br.edu.unifesspa.malves.transportnetwork.FemtoBB;
import br.edu.unifesspa.malves.transportnetwork.FemtoCB;
import br.edu.unifesspa.malves.util.GraficoBarra;

public class Scenario06 {

	public Scenario06(){
		HashMap<String, Double> emissao = new HashMap<String, Double>();

		DRACF dracf = new DRACF(Panel.hspPadrao, Environment.densidadeDeUsuariosPadrao);
		double emissaoDRACF = ((dracf.estatisticas[1]*Environment.fatorCO2Padrao)/(Environment.densidadeDeUsuariosPadrao*Environment.area))/Environment.anos.length;

		DRABF drabf = new DRABF(Panel.hspPadrao, Environment.densidadeDeUsuariosPadrao);
		double emissaoDRABF = ((drabf.estatisticas[1]*Environment.fatorCO2Padrao)/(Environment.densidadeDeUsuariosPadrao*Environment.area))/Environment.anos.length;

		FemtoCB femtocb = new FemtoCB(Panel.hspPadrao, Environment.densidadeDeUsuariosPadrao);
		double emissaoFemtoCB = ((femtocb.estatisticas[1]*Environment.fatorCO2Padrao)/(Environment.densidadeDeUsuariosPadrao*Environment.area))/Environment.anos.length;

		FemtoBB femtobb = new FemtoBB(Panel.hspPadrao, Environment.densidadeDeUsuariosPadrao);
		double emissaoFemtoBB = ((femtobb.estatisticas[1]*Environment.fatorCO2Padrao)/(Environment.densidadeDeUsuariosPadrao*Environment.area))/Environment.anos.length;

		emissao.put("DRA-CF", emissaoDRACF);
		emissao.put("DRA-BF", emissaoDRABF);
		emissao.put("Femto-CB", emissaoFemtoCB);
		emissao.put("Femto-BB", emissaoFemtoBB);

		//Graphics
		GraficoBarra demo = new GraficoBarra(
				this.getClass().getSimpleName(), 
				"", 
				"", 
				"CO2 Emission Avoided [kg/user/year]", 
				emissao);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);		
	}
}