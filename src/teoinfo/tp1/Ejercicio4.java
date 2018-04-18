package teoinfo.tp1;

public class Ejercicio4 
{
	final float EPSILON = 0.001f;
	final int MINIMO_EXPERIMENTOS = 10000;
	
	final int ENFERMO = 0;
	final int NO_ENFERMO = 1;
	
	final int RESULTADO_POSITIVO = 0;
	final int RESULTADO_NEGATIVO = 1;
	
	private float[] DistAcumEnfermedad = new float[] {0.005f, 1.0f};
	
	private float[][] DistAcumResultadoDadoEnfermedad = new float [][] {{0.95f, 1.0f}, {0.04f, 1.0f}};
	private int DistAcumResultadoDadoEnfermedadHeight = 2;
	
	public static void main(String[] args) 
	{
		System.out.println("Resolución de ejercicio TP1 4:\n");
		
		Ejercicio4 ejercicio4 = new Ejercicio4();
		
		System.out.println("(a) Si alguien obtiene un resultado negativo en el examen, cuál es la probabilidad de que esté sana?");
		System.out.println("La probabilidad es: " + (ejercicio4.CalcularProbabilidadResultadoNegativoDadoNoEnfermo()));
		
		System.out.println();
		
		System.out.println("(b) Si una persona obtiene un resultado positivo, ¿cuál es la probabilidad de que posea realmente la enfermedad?");
		System.out.println("La probabilidad es: " + (ejercicio4.CalcularProbabilidadResultadoPositivoDadoEnfermo()));
	}
		
	public float CalcularProbabilidadResultadoNegativoDadoNoEnfermo()
	{
		// Inicialización
		int exitos = 0;
		int pruebas = 0;
		float prob = 0;
		float probAnterior = -1;
		
		// Simulación montecarlo
		while (!this.Converge(prob, probAnterior) || pruebas < MINIMO_EXPERIMENTOS) 
		{
			int diagnostico = this.SimularEnfermedad();
			int resultado = this.SimularResultadoDadoEnfermedad(diagnostico);
			
			if (resultado == RESULTADO_NEGATIVO) 
			{
				if (diagnostico == NO_ENFERMO)
				{
					exitos++;
				}				
			
				pruebas++;
				
				// Actualización de probabilidades
				probAnterior = prob;
				prob = (float) (exitos) / pruebas;
			}		
			
		}
		
		return prob;
	}
	
	public float CalcularProbabilidadResultadoPositivoDadoEnfermo()
	{
		// Inicialización
		int exitos = 0;
		int pruebas = 0;
		float prob = 0;
		float probAnterior = -1;
		
		// Simulación montecarlo
		while (!this.Converge(prob, probAnterior) || pruebas < MINIMO_EXPERIMENTOS) 
		{
			int diagnostico = this.SimularEnfermedad();
			int resultado = this.SimularResultadoDadoEnfermedad(diagnostico);
			
			if (resultado == RESULTADO_POSITIVO) 
			{
				if (diagnostico == ENFERMO)
				{
					exitos++;
				}				
			
				pruebas++;
				
				// Actualización de probabilidades
				probAnterior = prob;
				prob = (float) (exitos) / pruebas;
			}									
		}
		
		return prob;
	}	
		
	private boolean Converge(float probActual, float probAnterior) 
	{
		return (Math.abs(probActual-probAnterior) < EPSILON);
	}
		
	private int SimularEnfermedad() 
	{
		float prob = (float) Math.random();
		int i = 0;
		while (i < DistAcumEnfermedad.length)
		{
			if (prob < DistAcumEnfermedad[i])
			{
				break;
			}
			
			i++;
		}		
		
		return i; 
	}
	
	private int SimularResultadoDadoEnfermedad(int diagnostico)
	{
		float prob = (float) Math.random();
		int i = 0;
		while (i < DistAcumResultadoDadoEnfermedadHeight)
		{
			if (prob < DistAcumResultadoDadoEnfermedad[diagnostico][i])
			{
				break;
			}
			
			i++;
		}		
		
		return i;
	}
}
