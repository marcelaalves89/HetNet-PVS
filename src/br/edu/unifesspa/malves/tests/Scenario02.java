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

public class Scenario02 {

	public Scenario02(){
		HashMap<String, double[]> tco = new HashMap<String, double[]>();

		//DRA-CF
		DRACF dracf = null;
		double[] tcoDRACF = new double[Panel.radiacao.length];

		//DRA-BF
		DRABF drabf = null;
		double[] tcoDRABF = new double[Panel.radiacao.length];

		//Femto-CB
		FemtoCB femtocb = null;
		double[] tcoFemtoCB = new double[Panel.radiacao.length];

		//Femto-BB
		FemtoBB femtobb = null;
		double[] tcoFemtoBB = new double[Panel.radiacao.length];

		for (int i=0; i<Panel.radiacao.length; i++){		
			dracf = new DRACF(Panel.radiacao[i], 3000);
			tcoDRACF[i] = (dracf.estatisticas[2]/(3000*Environment.area))/21.0;

			drabf = new DRABF(Panel.radiacao[i], 3000);
			tcoDRABF[i] = (drabf.estatisticas[2]/(3000*Environment.area))/21.0;

			femtocb = new FemtoCB(Panel.radiacao[i], 3000);
			tcoFemtoCB[i] = (femtocb.estatisticas[2]/(3000*Environment.area))/21.0;

			femtobb = new FemtoBB(Panel.radiacao[i], 3000);
			tcoFemtoBB[i] = (femtobb.estatisticas[2]/(3000*Environment.area))/21.0;
		}
		tco.put("DRA-CF", tcoDRACF);
		tco.put("DRA-BF", tcoDRABF);
		tco.put("Femto-CB", tcoFemtoCB);
		tco.put("Femto-BB", tcoFemtoBB);

		//Graphics
		GraficoLinha demo = new GraficoLinha(this.getClass().getSimpleName(), 
				"", 
				"Solar Irradiance [kWh/m²/day]", 
				"Cost per User per Year [Brazilian Real - BRL]", 
				tco, 
				Panel.radiacao);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);		
	}
}