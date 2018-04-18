package teoinfo.util;

public class VectorOperation 
{	
	static public void InicializarVector(int[] vector, int valor)
	{
		for (int i = 0; i < vector.length; i++) 
		{
			vector[i] = valor;
		}
	}
	
	static public void InicializarVector(float[] vector, float valor)
	{
		for (int i = 0; i < vector.length; i++) 
		{
			vector[i] = valor;
		}
	}
	
	static public void CopiarVector(float[] vector1, float[] vector2) 
	{
		for (int i = 0; i < vector2.length; i++) 
		{
			vector2[i] = vector1[i];
		}
	}
	
	static public boolean ConvergeVector(float[] probActual, float[] probAnterior, float epsilon) 
	{
		for (int i = 0; i < probActual.length; i++) 
		{
			if (!(Math.abs(probActual[i] - probAnterior[i]) < epsilon)) 
			{
				return false;
			}
		}
		
		return true;		
	}
}