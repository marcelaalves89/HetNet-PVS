package br.edu.unifesspa.malves.tests;

import java.util.HashMap;

import org.jfree.ui.RefineryUtilities;

import br.edu.unifesspa.malves.photovoltaics.Meter;
import br.edu.unifesspa.malves.trafficforecast.Environment;
import br.edu.unifesspa.malves.transportnetwork.DRABF;
import br.edu.unifesspa.malves.transportnetwork.DRACF;
import br.edu.unifesspa.malves.transportnetwork.FemtoBB;
import br.edu.unifesspa.malves.transportnetwork.FemtoCB;
import br.edu.unifesspa.malves.util.GraficoLinha;
import br.edu.unifesspa.malves.util.Util;

public class Grafico02 {

	public Grafico02(){
		HashMap<String, double[]> tco = new HashMap<String, double[]>();

		//DRA-CF
		DRACF dracf = null;
		double[] tcoDRACF = new double[Environment.radiacao.length];

		//DRA-BF
		DRABF drabf = null;
		double[] tcoDRABF = new double[Environment.radiacao.length];

		//Femto-CB
		FemtoCB femtocb = null;
		double[] tcoFemtoCB = new double[Environment.radiacao.length];

		//Femto-BB
		FemtoBB femtobb = null;
		double[] tcoFemtoBB = new double[Environment.radiacao.length];

		for (int i=0; i<Environment.radiacao.length; i++){		
			dracf = new DRACF(Environment.radiacao[i], Environment.densidadeDeUsuariosPadrao);
			tcoDRACF[i] = (dracf.estatisticas[2]/(Environment.densidadeDeUsuariosPadrao*Environment.area))/15;

			drabf = new DRABF(Environment.radiacao[i], Environment.densidadeDeUsuariosPadrao);
			tcoDRABF[i] = (drabf.estatisticas[2]/(Environment.densidadeDeUsuariosPadrao*Environment.area))/15;

			femtocb = new FemtoCB(Environment.radiacao[i], Environment.densidadeDeUsuariosPadrao);
			tcoFemtoCB[i] = (femtocb.estatisticas[2]/(Environment.densidadeDeUsuariosPadrao*Environment.area))/15;

			femtobb = new FemtoBB(Environment.radiacao[i], Environment.densidadeDeUsuariosPadrao);
			tcoFemtoBB[i] = (femtobb.estatisticas[2]/(Environment.densidadeDeUsuariosPadrao*Environment.area))/15;
		}
		tco.put("DRA-CF", tcoDRACF);
		tco.put("DRA-BF", tcoDRABF);
		tco.put("Femto-CB", tcoFemtoCB);
		tco.put("Femto-BB", tcoFemtoBB);
		
		double[] escalaX = {1.725, 7.775};
		double[] escalaY = {60.0, 323.0};
		
		System.out.println(this.getClass().getSimpleName()+": Custo da Energia Por Usuario, Por Ano (em Reais) junto a Concessionaria: ");
		System.out.println("DRA-BF: "+ Util.formataValorEmReais(((drabf.estatisticas[0]*Meter.purchaseCostOfKWH)/(Environment.densidadeDeUsuariosPadrao*Environment.area))/Environment.anos.length) );
		System.out.println("DRA-CF: "+ Util.formataValorEmReais(((dracf.estatisticas[0]*Meter.purchaseCostOfKWH)/(Environment.densidadeDeUsuariosPadrao*Environment.area))/Environment.anos.length ));		
		System.out.println("Femto-CB: "+ Util.formataValorEmReais(((femtocb.estatisticas[0]*Meter.purchaseCostOfKWH)/(Environment.densidadeDeUsuariosPadrao*Environment.area))/Environment.anos.length ));
		System.out.println("Femto-BB: "+ Util.formataValorEmReais(((femtobb.estatisticas[0]*Meter.purchaseCostOfKWH)/(Environment.densidadeDeUsuariosPadrao*Environment.area))/Environment.anos.length ));
		System.out.println();

		GraficoLinha demo = new GraficoLinha(this.getClass().getSimpleName(), 
				"", 
				"Irradiacao Solar (rs) [kWh/m2/dia]", 
				"Custo por usuario [Reais/usuario/ano]", 
				tco, 
				Environment.radiacao,
				escalaX,
				escalaY);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);		
	}
}