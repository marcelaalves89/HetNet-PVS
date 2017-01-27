package br.edu.unifesspa.malves.tests;

import java.util.HashMap;

import org.jfree.ui.RefineryUtilities;

import br.edu.unifesspa.malves.trafficforecast.Environment;
import br.edu.unifesspa.malves.transportnetwork.DRABF;
import br.edu.unifesspa.malves.transportnetwork.DRACF;
import br.edu.unifesspa.malves.transportnetwork.FemtoBB;
import br.edu.unifesspa.malves.transportnetwork.FemtoCB;
import br.edu.unifesspa.malves.util.GraficoBarra;

/**
 * 
 * @author	Marcela Alves
 * @since	2016-06-12
 *
 */
public class Grafico04 {

	public Grafico04(){
		HashMap<String, Double> emissao = new HashMap<String, Double>();

		DRACF dracf = new DRACF(Environment.defaultSolarIrradiance, Environment.densidadeDeUsuariosPadrao);
		double emissaoDRACF = ((dracf.estatisticas[1]*Environment.fatorCO2Padrao)/(Environment.densidadeDeUsuariosPadrao*Environment.area))/Environment.anos.length;

		DRABF drabf = new DRABF(Environment.defaultSolarIrradiance, Environment.densidadeDeUsuariosPadrao);
		double emissaoDRABF = ((drabf.estatisticas[1]*Environment.fatorCO2Padrao)/(Environment.densidadeDeUsuariosPadrao*Environment.area))/Environment.anos.length;

		FemtoCB femtocb = new FemtoCB(Environment.defaultSolarIrradiance, Environment.densidadeDeUsuariosPadrao);
		double emissaoFemtoCB = ((femtocb.estatisticas[1]*Environment.fatorCO2Padrao)/(Environment.densidadeDeUsuariosPadrao*Environment.area))/Environment.anos.length;

		FemtoBB femtobb = new FemtoBB(Environment.defaultSolarIrradiance, Environment.densidadeDeUsuariosPadrao);
		double emissaoFemtoBB = ((femtobb.estatisticas[1]*Environment.fatorCO2Padrao)/(Environment.densidadeDeUsuariosPadrao*Environment.area))/Environment.anos.length;

		emissao.put("DRA-CF", emissaoDRACF);
		emissao.put("DRA-BF", emissaoDRABF);
		emissao.put("Femto-CB", emissaoFemtoCB);
		emissao.put("Femto-BB", emissaoFemtoBB);
		
		double[] escalaY = {47, 70};

		GraficoBarra demo = new GraficoBarra(
				this.getClass().getSimpleName(), 
				"", 
				"", 
				"Emissões de CO2 evitadas [kg/usuário/ano]", 
				emissao,
				escalaY);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);		
	}
}