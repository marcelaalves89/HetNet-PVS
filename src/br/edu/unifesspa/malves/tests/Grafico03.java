package br.edu.unifesspa.malves.tests;

import java.util.HashMap;

import org.jfree.ui.RefineryUtilities;

import br.edu.unifesspa.malves.trafficforecast.Environment;
import br.edu.unifesspa.malves.transportnetwork.DRABF;
import br.edu.unifesspa.malves.transportnetwork.DRACF;
import br.edu.unifesspa.malves.transportnetwork.FemtoBB;
import br.edu.unifesspa.malves.transportnetwork.FemtoCB;
import br.edu.unifesspa.malves.util.GraficoLinha;

public class Grafico03 {

	public Grafico03(){
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
			tcoDRACF[i] = dracf.estatisticas[3];

			drabf = new DRABF(Environment.radiacao[i], Environment.densidadeDeUsuariosPadrao);
			tcoDRABF[i] = drabf.estatisticas[3];

			femtocb = new FemtoCB(Environment.radiacao[i], Environment.densidadeDeUsuariosPadrao);
			tcoFemtoCB[i] = femtocb.estatisticas[3];

			femtobb = new FemtoBB(Environment.radiacao[i], Environment.densidadeDeUsuariosPadrao);
			tcoFemtoBB[i] = femtobb.estatisticas[3];
		}
		tco.put("DRA-CF", tcoDRACF);
		tco.put("DRA-BF", tcoDRABF);
		tco.put("Femto-CB", tcoFemtoCB);
		tco.put("Femto-BB", tcoFemtoBB);

		GraficoLinha demo = new GraficoLinha(this.getClass().getSimpleName(), 
				"", 
				"Irradiação Solar (rs) [kWh/m²/dia]", 
				"Economia por usuário [Reais/usuário/ano]", 
				tco, 
				Environment.radiacao,
				null,
				null);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);		
	}
}