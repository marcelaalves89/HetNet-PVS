package br.edu.unifesspa.malves.tests;

import java.util.HashMap;

import org.jfree.ui.RefineryUtilities;

import br.edu.unifesspa.malves.photovoltaics.Meter;
import br.edu.unifesspa.malves.photovoltaics.Panel;
import br.edu.unifesspa.malves.transportnetwork.DRABF;
import br.edu.unifesspa.malves.transportnetwork.DRACF;
import br.edu.unifesspa.malves.transportnetwork.FemtoBB;
import br.edu.unifesspa.malves.transportnetwork.FemtoCB;
import br.edu.unifesspa.malves.util.GraficoLinha;

public class Scenario01 {

	public Scenario01(){
		HashMap<String, double[]> tco = new HashMap<String, double[]>();

		//DRA-CF
		DRACF dracf = null;
		double[] tcoDRACF = new double[Panel.irradiancia.length];
		double[] consumoDRACF = new double[Panel.irradiancia.length];

		//DRA-BF
		DRABF drabf = null;
		double[] tcoDRABF = new double[Panel.irradiancia.length];
		double[] consumoDRABF = new double[Panel.irradiancia.length];

		//Femto-CB
		FemtoCB femtocb = null;
		double[] tcoFemtoCB = new double[Panel.irradiancia.length];
		double[] consumoFemtoCB = new double[Panel.irradiancia.length];

		//Femto-BB
		FemtoBB femtobb = null;
		double[] tcoFemtoBB = new double[Panel.irradiancia.length];
		double[] consumoFemtoBB = new double[Panel.irradiancia.length];

		for (int i=0; i<Panel.irradiancia.length; i++){		
			dracf = new DRACF(Panel.irradiancia[i], 3000);
			tcoDRACF[i] = dracf.estatisticas[2]/1000000.0;
			consumoDRACF[i] = (dracf.estatisticas[0]*Meter.custoKwhCompra)/1000000.0;

			drabf = new DRABF(Panel.irradiancia[i], 3000);
			tcoDRABF[i] = drabf.estatisticas[2]/1000000.0;
			consumoDRABF[i] = (drabf.estatisticas[0]*Meter.custoKwhCompra)/1000000.0;

			femtocb = new FemtoCB(Panel.irradiancia[i], 3000);
			tcoFemtoCB[i] = femtocb.estatisticas[2]/1000000.0;
			consumoFemtoCB[i] = (femtocb.estatisticas[0]*Meter.custoKwhCompra)/1000000.0;

			femtobb = new FemtoBB(Panel.irradiancia[i], 3000);
			tcoFemtoBB[i] = femtobb.estatisticas[2]/1000000.0;
			consumoFemtoBB[i] = (femtobb.estatisticas[0]*Meter.custoKwhCompra)/1000000.0;

		}
		
		
		tco.put("DRA-CF Energy Consumption", consumoDRACF);
		tco.put("DRA-BF Energy Consumption", consumoDRABF);
		tco.put("Femto-CB Energy Consumption", consumoFemtoCB);
		tco.put("Femto-BB Energy Consumption", consumoFemtoBB);
		
		tco.put("DRA-CF", tcoDRACF);
		tco.put("DRA-BF", tcoDRABF);
		tco.put("Femto-CB", tcoFemtoCB);
		tco.put("Femto-BB", tcoFemtoBB);

		
		//Graphics
		GraficoLinha demo = new GraficoLinha(this.getClass().getSimpleName(), 
				"", 
				"Medium Solar Irradiance [kWh/m²/day]", 
				"TCO - Total Cost of Ownership [Million Brazilian Real - BRL]", 
				tco, 
				Panel.irradiancia);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);		
	}
}