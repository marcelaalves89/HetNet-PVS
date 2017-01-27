package br.edu.unifesspa.malves;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import br.edu.unifesspa.malves.tests.Grafico01;
import br.edu.unifesspa.malves.tests.Grafico02;
import br.edu.unifesspa.malves.tests.Grafico03;
import br.edu.unifesspa.malves.tests.Grafico04;
import br.edu.unifesspa.malves.tests.Grafico05;
import br.edu.unifesspa.malves.tests.Grafico06;
import br.edu.unifesspa.malves.tests.Grafico07;
import br.edu.unifesspa.malves.tests.Grafico08;
import br.edu.unifesspa.malves.tests.Grafico09;

/**
 * 
 * @author	Marcela Alves
 * @since	2016-06-11
 *
 */
public class Main {

	public static String path = "/home/hugo/Desktop/5G2/";	

	public static void main(String[] args) {

		String resposta = null;
		while (resposta == null || resposta.equals("")) {
			resposta = JOptionPane.showInputDialog("Defina o Caminho de Geração dos Graficos: ", Main.path);
			try {
				Files.createDirectories(Paths.get(resposta));
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"Diretório informado inválido.");
				resposta = null;
				e.printStackTrace();
			}
		}
		Main.path = resposta;
		
		//Variando a Radiacao
		new Grafico01();		
		new Grafico02();
		new Grafico03();				
		new Grafico04();

		//Variando a Densidade de Usuarios
		new Grafico05();
		new Grafico06();
		new Grafico07();
		new Grafico08();
		new Grafico09();
	}
}