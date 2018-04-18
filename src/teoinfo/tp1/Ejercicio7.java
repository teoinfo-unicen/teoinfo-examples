package teoinfo.tp1;

public class Ejercicio7 
{	
	final float EPSILON = 0.001f;
	final int MINIMO_EXPERIMENTOS = 10000;
	
	private float[] distProbCarasColoreadas = new float[] {1f/27f, 6f/27f, 12f/27f, 8f/27f};
	private float[] distProbAcumCarasColoreadas;
	
	public static void main(String[] args) 
	{
		Ejercicio7 ejercicio7 = new Ejercicio7();
		System.out.println("Resolución de ejercicio TP1 7:\n");
		
		System.out.println("Distribución de probabilidades de Suma de caras coloreadas P(A)");
		ejercicio7.CalcularDistProbabilidadesCarasColoreadas();
		System.out.println();
		
		ejercicio7.CalcularDistProbabilidadesCarasColoreadasUsandoVector();
		System.out.println();
	}
	
	public Ejercicio7() 
	{
		// Inicializar la distribución de probabilidades acumuladas
		distProbAcumCarasColoreadas = new float[distProbCarasColoreadas.length];
		distProbAcumCarasColoreadas[0] = distProbCarasColoreadas[0];
		for (int i = 1; i < distProbAcumCarasColoreadas.length; i++) 
		{
			distProbAcumCarasColoreadas[i] = distProbAcumCarasColoreadas[i - 1] + distProbCarasColoreadas[i]; 
		}
	}
	
	public float CalcularProbabilidadSumaCarasColoreadas(int suma) 
	{
		int exitos = 0;
		int tiradas = 0;
		float prob = 0;
		float probAnterior = -1;
		
		while (!this.Converge(prob, probAnterior) || tiradas < MINIMO_EXPERIMENTOS) 
		{
			int nCaras1 = this.ObtenerNumeroCarasColoreadas();
			int nCaras2 = this.ObtenerNumeroCarasColoreadas();
			
			if (nCaras1 + nCaras2 == suma) {
				exitos++;
			}
			tiradas++;
			
			probAnterior = prob;
			prob = (float) (exitos) / tiradas;
		}
		
		return prob;
	}
		
	public void CalcularDistProbabilidadesCarasColoreadas() 
	{
		final int casosPosibles = 7; // 7 posibles casos
		float[] distCarasColoreadas = new float[casosPosibles]; 
		for (int carasColoreadas = 0; carasColoreadas < casosPosibles; carasColoreadas++) 
		{
			distCarasColoreadas[carasColoreadas] = this.CalcularProbabilidadSumaCarasColoreadas(carasColoreadas);
			System.out.print(carasColoreadas + " = " + distCarasColoreadas[carasColoreadas] + "  ");					
		}
		
		System.out.println();
	}
	
	public void CalcularDistProbabilidadesCarasColoreadasUsandoVector() {
		final int casosPosibles = 7; // 7 posibles casos
		
		int exitos[] = new int[casosPosibles];
		inicializarVector(exitos, 0);
		
		int tiradas = 0;
		
		float prob[] = new float[casosPosibles];
		inicializarVector(prob, 0f);
		
		float probAnterior[] = new float[casosPosibles];
		inicializarVector(probAnterior, -1f);		
		
		while (!this.ConvergeVector(prob, probAnterior) || tiradas < MINIMO_EXPERIMENTOS) 
		{
			int nCaras1 = this.ObtenerNumeroCarasColoreadas();
			int nCaras2 = this.ObtenerNumeroCarasColoreadas();
			
			exitos[nCaras1 + nCaras2]++;			
			tiradas++;
			
			this.copiarVector(prob, probAnterior);
			
			for (int i = 0; i < prob.length; i++) 
			{
				prob[i] = ((float) exitos[i]) / tiradas;
			}			
		}
		
		// Imprimir resultados
		for (int carasColoreadas = 0; carasColoreadas < casosPosibles; carasColoreadas++) 
		{
			System.out.print(carasColoreadas + " = " + prob[carasColoreadas] + "  ");					
		}
		
		System.out.println();
	}
	
	private boolean Converge(float probActual, float probAnterior) 
	{
		return (Math.abs(probActual-probAnterior) < EPSILON);
	}
	
	private boolean ConvergeVector(float[] probActual, float[] probAnterior) 
	{
		for (int i = 0; i < probActual.length; i++) 
		{
			if (!(Math.abs(probActual[i] - probAnterior[i]) < EPSILON)) 
			{
				return false;
			}
		}
		
		return true;		
	}
		
	private int ObtenerNumeroCarasColoreadas() 
	{
		float prob = (float) Math.random();
		int i = 0;
		while (i < distProbAcumCarasColoreadas.length)
		{
			if (prob < distProbAcumCarasColoreadas[i])
			{
				break;
			}
			
			i++;
		}		
		
		return i; 
	}
	
	/// --------------------------------------------------------------------------------------
	/// Utils
	private void inicializarVector(int[] vector, int valor)
	{
		for (int i = 0; i < vector.length; i++) 
		{
			vector[i] = valor;
		}
	}
	
	private void inicializarVector(float[] vector, float valor)
	{
		for (int i = 0; i < vector.length; i++) 
		{
			vector[i] = valor;
		}
	}
	
	private void copiarVector(float[] vector1, float[] vector2) 
	{
		for (int i = 0; i < vector2.length; i++) 
		{
			vector2[i] = vector1[i];
		}
	}
	/// --------------------------------------------------------------------------------------
}
