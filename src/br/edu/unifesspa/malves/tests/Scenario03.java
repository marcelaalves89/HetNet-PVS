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


public class Scenario03 {

	public Scenario03(){

		HashMap<String, double[]> et = new HashMap<String, double[]>();

		//DRA-CF
		DRACF dracf = null;
		double[] etDRACF = new double[Environment.densidadeDeUsuarios.length];

		//DRA-BF
		DRABF drabf = null;
		double[] etDRABF = new double[Environment.densidadeDeUsuarios.length];

		//Femto-CB
		FemtoCB femtocb = null;
		double[] etFemtoCB = new double[Environment.densidadeDeUsuarios.length];

		//Femto-BB
		FemtoBB femtobb = null;
		double[] etFemtoBB = new double[Environment.densidadeDeUsuarios.length];

		for (int i=0; i<Environment.densidadeDeUsuarios.length; i++){		
			dracf = new DRACF(Panel.radiacaoPadrao, Environment.densidadeDeUsuarios[i]);
			etDRACF[i] = dracf.estatisticas[3];

			drabf = new DRABF(Panel.radiacaoPadrao, Environment.densidadeDeUsuarios[i]);
			etDRABF[i] = drabf.estatisticas[3];

			femtocb = new FemtoCB(Panel.radiacaoPadrao, Environment.densidadeDeUsuarios[i]);
			etFemtoCB[i] = femtocb.estatisticas[3];

			femtobb = new FemtoBB(Panel.radiacaoPadrao, Environment.densidadeDeUsuarios[i]);
			etFemtoBB[i] = femtobb.estatisticas[3];
		}
		
		et.put("DRA-CF", etDRACF);
		et.put("DRA-BF", etDRABF);
		et.put("Femto-CB", etFemtoCB);
		et.put("Femto-BB", etFemtoBB);

		GraficoLinha demo2 = new GraficoLinha(this.getClass().getSimpleName(), 
				"", 
				"User Density (ρ) [Users/km²]", 
				"Amount Saved per User per Year [Brazilian Real - BRL]", 
				et, 
				Environment.densidadeDeUsuarios);
		demo2.pack();
		RefineryUtilities.centerFrameOnScreen(demo2);
		demo2.setVisible(true);
	}
}