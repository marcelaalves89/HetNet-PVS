package br.edu.unifesspa.malves.tests;

import java.util.HashMap;

import org.jfree.ui.RefineryUtilities;

import br.edu.unifesspa.malves.photovoltaics.Panel;
import br.edu.unifesspa.malves.trafficforecast.Environment;
import br.edu.unifesspa.malves.transportnetwork.DRABF;
import br.edu.unifesspa.malves.transportnetwork.DRACF;
import br.edu.unifesspa.malves.transportnetwork.FemtoBB;
import br.edu.unifesspa.malves.transportnetwork.FemtoCB;
import br.edu.unifesspa.malves.util.GraficoLinha;


public class Grafico09 {

	public Grafico09(){

		HashMap<String, double[]> consumo = new HashMap<String, double[]>();
		
		DRACF dracf = new DRACF(Panel.radiacaoPadrao, Environment.densidadeDeUsuariosPadrao);
		consumo.put("DRA-CF", dracf.consumoTotal);

		DRABF drabf = new DRABF(Panel.radiacaoPadrao, Environment.densidadeDeUsuariosPadrao);
		consumo.put("DRA-BF", drabf.consumoTotal);

		FemtoCB femtocb = new FemtoCB(Panel.radiacaoPadrao, Environment.densidadeDeUsuariosPadrao);
		consumo.put("Femto-CB", femtocb.consumoTotal);

		FemtoBB femtobb = new FemtoBB(Panel.radiacaoPadrao, Environment.densidadeDeUsuariosPadrao);
		consumo.put("Femto-BB", femtobb.consumoTotal);					
		
		GraficoLinha demo2 = new GraficoLinha(this.getClass().getSimpleName(), 
				"", 
				"Anos", 
				"Total Power Consumption", 
				consumo, 
				Environment.anos);
		demo2.pack();
		RefineryUtilities.centerFrameOnScreen(demo2);
		demo2.setVisible(true);
	}
}