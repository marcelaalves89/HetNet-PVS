package br.edu.unifesspa.malves.util;

import java.text.DecimalFormat;

public class Teste {

	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("0.####");        		
		double x=1;
		int i = 0;
		while(i<21){			
			String dx = df.format(x);
			System.out.print(dx+", ");
			x*=0.992;
			i++;
		}
		
	}
}
