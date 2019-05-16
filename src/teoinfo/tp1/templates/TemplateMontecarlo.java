package teoinfo.tp1.templates;

public class TemplateMontecarlo {
	
	public static void main(String[] args) 
	{
		float prob = (new TemplateMontecarlo()).CalcularProbabilidadSumaDados(2);
		System.out.println("La probabilidad calculada es " + prob);
	}
	
	private int ArrojarDado() 
	{
		// Dado virtual
		float[] probAcumulada = new float[] {1f/6, 2f/6, 3f/6, 4f/6, 5f/6, 1f};
		
		float prob = (float) Math.random();
		for (int i = 1; i <= probAcumulada.length; i++) 
		{
			if (prob < probAcumulada[i-1]) 
			{
				return i;
			}
		}
		
		return 1;
	}
			
	public float CalcularProbabilidadSumaDados(int suma)
	{ 
		int exitos = 0;
		int tiradas = 0;
		float prob = 0;
		float probAnterior = -1;
		
		while (!this.Converge(prob, probAnterior) || tiradas < 10000) 
		{
			int dado1 = this.ArrojarDado();
			int dado2 = this.ArrojarDado();
			
			if (dado1 + dado2 == suma) {
				exitos++;
			}
			tiradas++;
			
			probAnterior = prob;
			prob = (float) (exitos) / tiradas;
		}
		
		return prob;
	}
	
	private boolean Converge(float probActual, float probAnterior) 
	{
		return (Math.abs(probActual-probAnterior) < 0.00000001f);
	}
}
