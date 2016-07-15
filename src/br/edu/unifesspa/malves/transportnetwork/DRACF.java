package br.edu.unifesspa.malves.transportnetwork;

import br.edu.unifesspa.malves.backhaul.Fiber;
import br.edu.unifesspa.malves.util.Util;
import br.edu.unifesspa.malves.wireless.DRA;

/**
 * 
 * @author	Marcela Alves
 * @since	2016-06-18
 *
 */
public class DRACF extends DRAPVBased{
	
	/**
	 * Super constructor call and initializing values
	 */
	public DRACF(double hsp, double densidadeDeUsuarios){
		super(hsp, densidadeDeUsuarios);
		this.nome = "Macro+DRA-CF Architecture";
		this.getConsumoMacro();
		this.getConsumoDRA();		
		this.getPotenciaDeGeracao();
		this.getTCO();
		this.getEstatisticas();
		//this.debug();
	}

	/**
	 * The Power Consumption of DRA-CF Only (KWH)
	 */
	public void getConsumoDRA(){		
		double temp = (DRA.potenciaRRU + 2*Fiber.potenciaSFP + DRA.potenciaPorPortaDU + (DRA.potenciaPorRackDU/DRA.numeroDePortasDUporRackDU))/DRA.numeroMaximoDeAntenasPorRRU;
		this.potenciaDRAOnly = Util.getSoma(
				Util.getProdutoPorEscalar(super.numeroDeAntenasDRA, temp),
				Util.getProdutoPorEscalar(super.numeroDeAntenasDRA, DRA.potenciaAntenaAmplificadora)
				);		
		Util.converterEmKWH(this.potenciaDRAOnly);
		this.potenciaTotal = Util.getSoma(this.potenciaMacroOnly,this.potenciaDRAOnly);
	}

	/**
	 * Print values for Debug
	 */
	public void debug(){
		System.out.println("Power Consumption of DRA-CF Architecture");
		Util.imprime(this.consumoTotal);
		System.out.println();

		System.out.println("Power Generation of DRA-CF Architecture");
		Util.imprime(this.potenciaGerada);
		System.out.println();

		System.out.println("TCO");
		Util.imprime(this.tco);
		System.out.println();
	}
}