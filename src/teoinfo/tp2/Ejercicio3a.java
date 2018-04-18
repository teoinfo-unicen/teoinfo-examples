package teoinfo.tp2;

import teoinfo.util.VectorOperation;

/**
 * @author Marcos
 *
 */
public class Ejercicio3a {

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
		System.out.println("Resolución de ejercicio TP2 3 a):\n");
		System.out.println("Si el símbolo emitido inicialmente es 0, obtenga la probabilidad de emitir el símbolo 0 en los instantes 1, 2 y 3:");
		
		Ejercicio3a ejercicio3a = new Ejercicio3a();		
		ejercicio3a.CalcularProbEstadoTiempo(0, 3);		
	}

	/**
	 * Inicializar vectores y matrices de probabilidad acumulada
	 */
	public Ejercicio3a() 
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
	 * Calcula la probabilidad de partir de un estado y volver al mismo estado en los tiempo entre 1 y maxTiempo
	 * @param estadoInicial: Estado donde inicia la simulación
	 * @param maxTiempo: Tiempo máximo para calcular la probabilidad
	 */
	public void CalcularProbEstadoTiempo(int estadoInicial, int maxTiempo) 
	{
		int[] exitos = new int[maxTiempo];
		VectorOperation.InicializarVector(exitos, 0);

		int trayectorias = 0;

		float[] prob = new float[maxTiempo];
		VectorOperation.InicializarVector(prob, 0);

		float[] probAnterior = new float[maxTiempo];
		VectorOperation.InicializarVector(probAnterior, -1);

		while (!VectorOperation.ConvergeVector(prob, probAnterior, EPSILON) || trayectorias < MINIMO_EXPERIMENTOS) 
		{
			int estadoActual = estadoInicial;			
			for (int t = 0; t < maxTiempo; t++) 
			{
				estadoActual = this.ProximoEstadoDadoAnterior(estadoActual);
				
				if (estadoActual == estadoInicial)
				{
					exitos[t]++;
				}
			}

			trayectorias++;

			VectorOperation.CopiarVector(prob, probAnterior);
			
			for (int t = 0; t < prob.length; t++) 
			{
				prob[t] = ((float) exitos[t]) / trayectorias;
			}	
		}

		// Imprimir resultados
		for (int tiempo = 0; tiempo < maxTiempo; tiempo++) 
		{
			System.out.println("Probabilidad de pasar de " + estadoInicial + " --> " + estadoInicial + " en t = " + (tiempo+1) + " :: " + prob[tiempo]);					
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