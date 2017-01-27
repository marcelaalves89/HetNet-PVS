package br.edu.unifesspa.malves.transportnetwork;

import br.edu.unifesspa.malves.backhaul.PON;
import br.edu.unifesspa.malves.util.Util;
import br.edu.unifesspa.malves.wireless.DRA;

/**
 * 
 * @author	Marcela Alves
 * @since	2016-06-18
 *
 */
public class DRABF extends DRAPVBased{

	/**
	 * Super constructor call and initializing values
	 */
	public DRABF(double radiacao, double densidadeDeUsuarios){
		super(radiacao, densidadeDeUsuarios);
		this.name = "Macro+DRA-BF Architecture";
		this.getConsumoMacro();
		this.getConsumoDRA();		
		this.getPotenciaDeGeracao();
		this.getTCO();
		this.getEstatisticas();
		this.debug();
	}

	/**
	 * Calculating the Power Consumption of DRA-BF Only (KWH)
	 */
	public void getConsumoDRA(){				
		
		double temp = DRA.numOfRRUperBuilding*(DRA.RRUPower + DRA.DUPortPower 
												 + (DRA.rackDUPower/DRA.numberOfDUPortsPerRack))
		+ PON.numOfONUPerBuilding*(PON.onuPower+(PON.oltPower/PON.numOfWaveLenghtsPerDWDM));
		
		this.potenciaDRAOnly = Util.getSoma(
				Util.getProductByInteger(super.numeroDeAntenasDRA, DRA.powerAmplifierAntenna),
				Util.getProductByInteger(super.numeroMaximoDePrediosComDRA, temp)
				);
		
		Util.converterEmKWH(this.potenciaDRAOnly);
		this.potenciaTotal = Util.getSoma(this.potenciaMacroOnly,this.potenciaDRAOnly);
	}
	
	/**
	 * Print values for Debug
	 */
	public void debug(){

	}
	
	public static void main(String[] args) {
		new DRABF(6.5, 3000);
	}
}