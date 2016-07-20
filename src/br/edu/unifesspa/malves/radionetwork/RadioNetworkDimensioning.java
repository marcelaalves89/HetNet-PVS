package br.edu.unifesspa.malves.radionetwork;

public abstract class RadioNetworkDimensioning {

	protected String nome;
	
	protected double[] x;
	
	protected double[] y;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double[] getX() {
		return x;
	}

	public void setX(double[] x) {
		this.x = x;
	}

	public double[] getY() {
		return y;
	}

	public void setY(double[] y) {
		this.y = y;
	}
}
