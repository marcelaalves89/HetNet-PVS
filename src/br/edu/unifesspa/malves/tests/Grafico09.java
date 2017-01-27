package br.edu.unifesspa.malves.tests;

import java.util.HashMap;

import org.jfree.ui.RefineryUtilities;

import br.edu.unifesspa.malves.trafficforecast.Environment;
import br.edu.unifesspa.malves.transportnetwork.DRABF;
import br.edu.unifesspa.malves.transportnetwork.DRACF;
import br.edu.unifesspa.malves.transportnetwork.FemtoBB;
import br.edu.unifesspa.malves.transportnetwork.FemtoCB;
import br.edu.unifesspa.malves.util.GraficoLinha;

/**
 * 
 * @author	Marcela Alves
 * @since	2016-06-12
 *
 */
public class Grafico09 {

	public Grafico09(){

		HashMap<String, double[]> consumo = new HashMap<String, double[]>();
		
		DRACF dracf = new DRACF(Environment.defaultSolarIrradiance, Environment.densidadeDeUsuariosPadrao);
		consumo.put("DRA-CF", dracf.consumoTotal);
		System.out.println((dracf.estatisticas[0]/1000.0)/21.0+" mWh");

		DRABF drabf = new DRABF(Environment.defaultSolarIrradiance, Environment.densidadeDeUsuariosPadrao);
		consumo.put("DRA-BF", drabf.consumoTotal);
		System.out.println((drabf.estatisticas[0]/1000.0)/21.0+" mWh");

		FemtoCB femtocb = new FemtoCB(Environment.defaultSolarIrradiance, Environment.densidadeDeUsuariosPadrao);
		consumo.put("Femto-CB", femtocb.consumoTotal);
		System.out.println((femtocb.estatisticas[0]/1000.0)/21.0+" mWh");

		FemtoBB femtobb = new FemtoBB(Environment.defaultSolarIrradiance, Environment.densidadeDeUsuariosPadrao);
		consumo.put("Femto-BB", femtobb.consumoTotal);
		System.out.println((femtobb.estatisticas[0]/1000.0)/21.0+" mWh");
		
		GraficoLinha demo2 = new GraficoLinha(this.getClass().getSimpleName(), 
				"", 
				"Anos", 
				"Consumo Total de Energia", 
				consumo, 
				Environment.anos,
				null,
				null);
		demo2.pack();
		RefineryUtilities.centerFrameOnScreen(demo2);
		demo2.setVisible(true);
	}
}