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


public class Scenario04 {

	public Scenario04(){

		HashMap<String, double[]> tco = new HashMap<String, double[]>();

		//DRA-CF
		DRACF dracf = null;
		double[] tcoDRACF = new double[Environment.densidadeDeUsuarios.length];

		//DRA-BF
		DRABF drabf = null;
		double[] tcoDRABF = new double[Environment.densidadeDeUsuarios.length];

		//Femto-CB
		FemtoCB femtocb = null;
		double[] tcoFemtoCB = new double[Environment.densidadeDeUsuarios.length];

		//Femto-BB
		FemtoBB femtobb = null;
		double[] tcoFemtoBB = new double[Environment.densidadeDeUsuarios.length];

		for (int i=0; i<Environment.densidadeDeUsuarios.length; i++){		
			dracf = new DRACF(Panel.irradiancia[8], Environment.densidadeDeUsuarios[i]);
			tcoDRACF[i] = (dracf.estatisticas[2]/(Environment.densidadeDeUsuarios[i]*Environment.area))/21.0;

			drabf = new DRABF(Panel.irradiancia[8], Environment.densidadeDeUsuarios[i]);
			tcoDRABF[i] = (drabf.estatisticas[2]/(Environment.densidadeDeUsuarios[i]*Environment.area))/21.0;;

			femtocb = new FemtoCB(Panel.irradiancia[8], Environment.densidadeDeUsuarios[i]);
			tcoFemtoCB[i] = (femtocb.estatisticas[2]/(Environment.densidadeDeUsuarios[i]*Environment.area))/21.0;;

			femtobb = new FemtoBB(Panel.irradiancia[8], Environment.densidadeDeUsuarios[i]);
			tcoFemtoBB[i] = (femtobb.estatisticas[2]/(Environment.densidadeDeUsuarios[i]*Environment.area))/21.0;;
		}
		
		tco.put("DRA-CF", tcoDRACF);
		tco.put("DRA-BF", tcoDRABF);
		tco.put("Femto-CB", tcoFemtoCB);
		tco.put("Femto-BB", tcoFemtoBB);

		GraficoLinha demo2 = new GraficoLinha(this.getClass().getSimpleName(), 
				"", 
				"User Density (ρ) [Users/km²]", 
				"Cost per User per Year [Brazilian Real - BRL]", 
				tco, 
				Environment.densidadeDeUsuarios);
		demo2.pack();
		RefineryUtilities.centerFrameOnScreen(demo2);
		demo2.setVisible(true);
	}
}