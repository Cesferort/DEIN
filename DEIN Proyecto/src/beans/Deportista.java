package beans;

/**
 * Esta clase nos permitirá crear objetos Deportista.
 * @author Cesferort
 * @since 1.0
 */
public class Deportista 
{
	/**
	 * Identificador del deportista.
	 */
	private int codDep;
	/**
	 * Nombre del deportista.
	 */
	private String nomDep;
	/**
	 * Sexo del deportista.
	 */
	private String sexDep;
	/**
	 * Peso del deportista.
	 */
	private int pesDep;
	/**
	 * Altura del deportista.
	 */
	private int altDep;
	
	/**
	 * Constructor de un deportista en base a los parámetros recibidos.
	 * @since 1.0
	 * @param codDep - Código del deportista clave en la tabla
	 * @param nomDep - Nombre del deportista
	 * @param sexDep - Sexo del deportista [M/F]
	 * @param pesDep - Peso del deportista
	 * @param altDep - Altura del deportista
	 */
	public Deportista(int codDep, String nomDep, String sexDep, int pesDep, int altDep) 
	{
		this.codDep = codDep;
		this.nomDep = nomDep;
		this.sexDep = sexDep;
		this.pesDep = pesDep;
		this.altDep = altDep;
	}

	/**
	 * toString que nos permitirá visualizar los datos de un Deportista
	 * de forma simplificada
	 * @since 1.0
	 * @return Devuelve el texto con el que el deportista será visualizado
	 */
	public String toString() 
	{
		return codDep + " - Nombre: " + nomDep;
	}
	
	public int getCodDep() 
	{
		return codDep;
	}
	
	public String getNomDep() 
	{
		return nomDep;
	}

	public String getSexDep() 
	{
		return sexDep;
	}

	public int getPesDep() 
	{
		return pesDep;
	}
	
	public int getAltDep() 
	{
		return altDep;
	}
	
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + codDep;
		return result;
	}
	
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Deportista other = (Deportista) obj;
		if (codDep != other.codDep)
			return false;
		return true;
	}
}