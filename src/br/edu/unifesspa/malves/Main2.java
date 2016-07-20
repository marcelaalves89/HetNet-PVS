package br.edu.unifesspa.malves;

import java.text.DecimalFormat;

import br.edu.unifesspa.malves.transportnetwork.DRACF;

public class Main2 {

	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("#.#");		
		
		DRACF x = new DRACF(5.9, 3000);
		
		String formato = "R$ #,##0.00";
		
		System.out.println("Consumo Total de Energia da Arquitetura Macro+DRA-CF: "+x.estatisticas[0]/1000+" mWh");
		
		System.out.println("Consumo de Energia da Rede: "+(x.estatisticas[4])+" mWh");
		
		System.out.println("Consumo de Energia da Arquitetura Macro+DRA-CF que deve ser Gerado: "+x.estatisticas[5]/1000+" mWh");
		
		System.out.println("Geração de Energia do Sistema FV: "+x.estatisticas[1]/1000+" mWh");

		
				
		//System.out.println("Valor do Consumo R$: "+new DecimalFormat(formato).format(diferenca*Meter.custoKwhCompra)+" reais");
		
	}
}
