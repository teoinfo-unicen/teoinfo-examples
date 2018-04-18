package teoinfo.tp2.templates;

import teoinfo.util.VectorOperation;

public class TemplateVectorEstacionario 
{
	final float EPSILON = 0.001f;
	final int MINIMO_EXPERIMENTOS = 10000;

	private int nEstados = 3;
	private float[][] ProbTransicionEstado = new float[][] {
		{1f/4f, 1f/2f, 1f/4f},
		{3f/4f, 1f/4f, 0f},
		{0f,    1f/2f, 1f/2f},
	};
	
	private float[][] ProbTransicionEstadoAcum;

	public static void main(String[] args) 
	{
		System.out.println("Template calculo de vector estacionario (TP2):");
		System.out.println("*) Probabilidad en estado estacionario de visitar el estado X (X = 1, 2, 3):");
		System.out.println();
		
		TemplateVectorEstacionario templateVectorEstacionario = new TemplateVectorEstacionario();		
		templateVectorEstacionario.CalcularVectorEstacionario();		
	}

	/**
	 * Inicializar vectores y matrices de probabilidad acumulada
	 */
	public TemplateVectorEstacionario() 
	{
		ProbTransicionEstadoAcum = new float[nEstados][nEstados];
		
		for (int i = 0; i < nEstados; i++) 
		{
			this.ProbTransicionEstadoAcum[i][0] = this.ProbTransicionEstado[i][0];
			for (int j = 1; j < nEstados; j++) 
			{
				this.ProbTransicionEstadoAcum[i][j] = this.ProbTransicionEstadoAcum[i][j-1] + this.ProbTransicionEstado[i][j];
			}
		}
	}

	/**
	 * Calcula el vector estacionario para la fuente definida
	 */
	public void CalcularVectorEstacionario() 
	{
		int[] exitos = new int[nEstados];
		VectorOperation.InicializarVector(exitos, 0);

		int pasos = 0;

		float[] prob = new float[nEstados];
		VectorOperation.InicializarVector(prob, 0);

		float[] probAnterior = new float[nEstados];
		VectorOperation.InicializarVector(probAnterior, -1);
		
		int estadoActual = 0;

		while (!VectorOperation.ConvergeVector(prob, probAnterior, EPSILON) || pasos < MINIMO_EXPERIMENTOS) 
		{
			estadoActual = this.ProximoEstadoDadoAnterior(estadoActual);
			exitos[estadoActual]++;
			
			pasos++;

			VectorOperation.CopiarVector(prob, probAnterior);
			
			// Actualizar probabilidades
			for (int t = 0; t < prob.length; t++) 
			{
				prob[t] = ((float) exitos[t]) / pasos;
			}	
		}

		// Imprimir resultados
		for (int i = 0; i < nEstados; i++) 
		{
			System.out.println("Probabilidad en estado estacionario de estar en " + i + " es " + prob[i]);					
		}
	}

	private int ProximoEstadoDadoAnterior(int estadoAnterior) 
	{
		float prob = (float) Math.random();
		int i = 0;
		while (i < nEstados)
		{
			if (prob < this.ProbTransicionEstadoAcum[estadoAnterior][i])
			{
				break;
			}
			
			i++;
		}		
		
		return i;
	}
}