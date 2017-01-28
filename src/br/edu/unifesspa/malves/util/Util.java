package br.edu.unifesspa.malves.util;

import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * Utility class for storing various auxiliary functions
 * 
 * @author	Marcela Alves
 * @since	2016-06-12
 * @version 1.0
 *
 */
public class Util {

	/**
	 * Returns the product between a matrix and an integer
	 * 
	 * @param matrix matrix of double values
	 * @param integerNumber a double (integer) value
	 * @return Return a new matrix of double values
	 */
	public static double[][] getProductByInteger(double[][] matrix, double integerNumber){
		double[][] results = new double[matrix.length][matrix[0].length];
		for (int i=0; i<matrix.length; i++)
			for (int j=0; j<matrix[0].length; j++)
				results[i][j] = matrix[i][j] * integerNumber;
		return results;
	}
	
	/**
	 * Returns the product between an array and an integer
	 * 
	 * @param array array of double values
	 * @param integerNumber a double (integer) value
	 * @return Return a new array of double values
	 */
	public static double[] getProductByInteger(double[] array, double integerNumber){
		double[] results = new double[array.length];
		for (int i=0; i<array.length; i++)
			results[i] = array[i] * integerNumber;
		return results;
	}

	/**
	 * Returns a new matrix of double formatted values
	 * 
	 * @param matrix The original matrix of double values
	 * @return The new matrix of double formatted values 
	 */
	public static double[][] formatValues(double[][] matrix){
		double[][] results = new double[matrix.length][matrix[0].length];
		DecimalFormat format = new DecimalFormat("#.####");   
		for (int i=0; i<matrix.length; i++)
			for (int j=0; j<matrix[0].length; j++){
				results[i][j] = Double.valueOf(format.format(matrix[i][j]).replace(',', '.'));
			}
		return results;
	}

	/**
	 * Returns a new array of double formatted values
	 * 
	 * @param array The original array of double values
	 * @return The new array of double formatted values 
	 */
	public static double[] formatValues(double[] array){
		double[] results = new double[array.length];
		DecimalFormat formato = new DecimalFormat("#.####");   
		for (int i=0; i<array.length; i++)
			results[i] = Double.valueOf(formato.format(array[i]).replace(',', '.'));			
		return results;
	}

	/**
	 * Prints a matrix and its dimensions
	 * 
	 * @param matrix The matrix to be printed
	 */
	public static void print(double[][] matrix){
		System.out.println("Matrix Data: ");
		System.out.println("Number of Rows: "+matrix.length);
		System.out.println("Number of Columns: "+matrix[0].length);
		for (int i=0; i<matrix.length; i++)
			for (int j=0; j<matrix[0].length; j++)
				System.out.println("Element "+i+"-"+j+": "+matrix[i][j]);
		System.out.println();
	}

	/**
	 * Prints an array and its dimensions
	 * 
	 * @param array The array to be printed
	 */
	public static void print(double[] array){
		System.out.println("Array Data: ");
		System.out.println("Number of Elements: "+array.length);
		for (int i=0; i<array.length; i++)
			System.out.println("Element "+i+": "+array[i]);
		System.out.println();
	}

	/**
	 * Returns the transposed matrix
	 * 
	 * @param matrix
	 * @return the transposed matrix
	 */
	public static double[][] getTransposed(double[][] matrix){
		double[][] results = new double[matrix[0].length][matrix.length];
		for(int linha=0;linha<matrix.length;linha++)
			for(int coluna=0;coluna<matrix[linha].length;coluna++)
				if(coluna>linha)
					results[linha][coluna]=matrix[coluna][linha];
				else if(coluna==linha)
					results[linha][coluna]=matrix[linha][coluna];
				else
					results[linha][coluna]=matrix[coluna][linha];
		return results;	
	}

	/**
	 * Returns the transposed array
	 * 
	 * @param arrayRow
	 * @return the transposed array line (Column)
	 */
	public static double[][] getTransposed(double[] arrayRow){
		double[][] results = new double[1][arrayRow.length];
		for (int i=0; i<arrayRow.length; i++)
			results[0][i] = arrayRow[i];	
		return results;
	}


	/**
	 * Returns the i-th column of a matrix
	 *  
	 * @param matrix the matrix
	 * @param columnIndex The matrix column index
	 * @return the i-th column of a matrix as an array
	 */
	public static double[] getColunaMatriz(double[][] matrix, int columnIndex){
		double[] results = new double[matrix.length];
		for (int i=0; i<matrix.length; i++)
			results[i] = matrix[i][columnIndex];
		return results;
	}

	/**
	 * Retorna o produto matricial em a e b (a*b)
	 * @param a
	 * @param b
	 * @return
	 */
	public static double[][] getProdutoMatricial(double[][] a, double[][] b){
		if (a[0].length != b.length) throw new RuntimeException("Dimensões inconsistentes. Impossível multiplicar as matrizes");
		double[][] results = new double[ a.length ][ b[0].length ];
		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < b[0].length; j++) 
				for (int k = 0; k < a[0].length; k++) 
					results[i][j] += (a[i][k] * b[k][j]);
		return results;
	}

	/**
	 * Retorna produto matricial elemento por elemento
	 * @return
	 */
	public static double[][] getProdutoMatricialPorElemento(double[][] a, double[][] b){
		if ((a.length != b.length) || (a[0].length != b[0].length)) throw new RuntimeException("Dimensões inconsistentes. Impossível multiplicar as matrizes");
		double[][] results = new double[a.length][a[0].length];
		for (int i=0; i<a.length; i++)
			for (int j=0; j<a[0].length; j++)
				results[i][j] = a[i][j] * b[i][j];
		return results;
	}

	/**
	 * Retorn uma vetor linha com a soma das colunas de uma matriz
	 * @param matriz
	 * @return
	 */
	public static double[] getSomaPorColuna(double[][] matriz){
		double[] results = new double[matriz[0].length];
		for (int j=0; j<matriz[0].length; j++)
			for (int i=0; i<matriz.length; i++)
				results[j] += matriz[i][j];
		return results;
	}

	/**
	 * Retorn uma vetor linha com a soma das colunas de uma matriz
	 * @param matriz
	 * @return
	 */
	public static double getSomaPorColuna(double[][] matriz, int coluna){
		double soma = 0;
		for (int i=0; i<matriz.length; i++)
			soma += matriz[i][coluna];
		return soma;
	}

	/**
	 * 
	 * @param numeroLinhas
	 * @param numeroColunas
	 * @return
	 */
	public static double[][] getZeros(int numeroLinhas, int numeroColunas){
		double[][] results = new double[numeroLinhas][numeroColunas];
		for (int i=0; i<numeroLinhas; i++)
			for (int j=0; j<numeroColunas; j++)
				results[i][j] = 0;
		return results;
	}

	/**
	 * 
	 */
	public static void getDepreciacao(double[][] matriz, double fator){
		for (int i=0; i<matriz.length; i++)
			for (int j=i; j<matriz[0].length; j++)
				if (i != j)
					matriz[i][j] = matriz[i][j-1]*fator;
	}

	/**
	 * Retorn um valor (double) com o total das colunas do vetor
	 * @param vetor
	 * @return
	 */
	public static double getSomaColunasVetor(double[] vetor){
		double results = 0;
		for (int i=0; i<vetor.length; i++)
			results += vetor[i];
		return results;
	}

	/**
	 * Retorn uma collection com os valores maximos e minimos de um vetor
	 * @param vetor
	 * @return
	 */
	public static HashMap<String, Double> getMinEMaximo(double[] vetor){
		HashMap<String, Double> results = new HashMap<String, Double>();
		double minimo = 10000;
		double maximo = -1;
		for (int i=0; i<vetor.length; i++){
			if (vetor[i] < minimo)
				minimo = vetor[i];
			if (vetor[i] > maximo)
				maximo = vetor[i];
		}
		results.put("maximo", maximo);
		results.put("minimo", minimo);
		return results;
	}


	/**
	 * Retorna um vetor elevado à uma dada potencia
	 * @param vetor
	 * @param potencia
	 * @return
	 */
	public static double[] getPotencia(double[] vetor, double potencia){
		double[] results = new double[vetor.length];
		for (int i=0; i<vetor.length; i++)
			results[i] = Math.pow(vetor[i], potencia);
		return results;
	}

	/**
	 * 
	 * @param vetor
	 * @param dividendo
	 * @return
	 */
	public static double[] getDivisao(double[] vetor, double dividendo){
		double[] results = new double[vetor.length];
		for (int i=0; i<vetor.length; i++)
			results[i] = Math.ceil(dividendo/vetor[i]);
		return results;
	}

	/**
	 * 
	 * @param vetorColuna
	 * @param dividendo
	 * @return
	 */
	public static double[][] getDivisao(double[][] vetorColuna, double divisor){
		double[][] results = new double[vetorColuna.length][vetorColuna[0].length];
		for (int i=0; i<vetorColuna.length; i++)
			results[i][0] = Math.ceil(vetorColuna[i][0]/divisor);
		return results;
	}

	/**
	 * Soma duas matrizes
	 * @param a
	 * @param b
	 * @return
	 */
	public static double[][] getSoma(double[][] a, double[][] b){
		double[][] results = new double[a.length][a[0].length];
		for (int i=0; i<a.length; i++)
			for (int j=0; j<a[0].length; j++)
				results[i][j] = a[i][j] + b[i][0];
		return results;
	}

	/**
	 * Soma duas matrizes
	 * @param a
	 * @param b
	 * @return
	 */
	public static double[][] getSoma(double[][] a, double[] b){
		double[][] results = new double[a.length][a[0].length];
		for (int i=0; i<a.length; i++)
			for (int j=0; j<a[0].length; j++)
				results[i][j] = a[i][j] + b[i];
		return results;
	}

	/**
	 * Soma duas matrizes
	 * @param a
	 * @param b
	 * @return
	 */
	public static double[] getSoma(double[] a, double[] b){
		double[] results = new double[a.length];
		for (int i=0; i<a.length; i++)
			results[i] = a[i] + b[i];
		return results;
	}

	public static double[][] getDiferenca(double[][] a, double[] b){
		double[][] results = new double[a.length][a[0].length];
		for (int i=0; i<a.length; i++)
			for (int j=0; j<a[0].length; j++)
				results[i][j] = (a[i][j] - b[i]);
		return results;
	}

	public static double[] getDiferenca(double[] a, double[] b){
		double[] results = new double[a.length];
		for (int i=0; i<a.length; i++)
			results[i] = a[i] - b[i];
		return results;
	}

	public static void converterEmKWH(double[][] a){
		for (int i=0; i<a.length; i++)
			for (int j=0; j<a[0].length; j++)
				a[i][j] = ((a[i][j]*24.0)/1000.0);
	}

	public static double[] getDiagonalPrincipal(double[][] matriz){
		double[] diagonal = new double[matriz.length];
		for (int i=0; i<matriz.length; i++)
			for (int j=0; j<matriz.length; j++)
				if (i ==j)
					diagonal[i] = matriz[i][j];
		return diagonal;
	}

	public static String formataValorEmReais(double valor) {  
		DecimalFormat d = new DecimalFormat("R$ #,##0.00");  
		return d.format(valor);  
	}  
}