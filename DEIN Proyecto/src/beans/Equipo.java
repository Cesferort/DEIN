package beans;

/**
 * Esta clase nos permitirá crear objetos Deportista.
 * @author Cesferort
 * @since 1.0
 */
public class Equipo 
{
	/**
	 * Identificador del equipo.
	 */
	private int codEquipo;
	/**
	 * Nombre del equipo.
	 */
	private String nomEquipo;
	/**
	 * Iniciales del equipo.
	 */
	private String iniEquipo;
	
	/**
	 * Constructor de un equipo en base a los parámetros recibidos.
	 * @since 1.0
	 * @param codEquipo - Código del equipo clave en la tabla
	 * @param nomEquipo - Nombre del equipo
	 * @param iniEquipo - Iniciales del equipo
	 */
	public Equipo(int codEquipo, String nomEquipo, String iniEquipo) 
	{
		this.codEquipo = codEquipo;
		this.nomEquipo = nomEquipo;
		this.iniEquipo = iniEquipo;
	}

	/**
	 * toString que nos permitirá visualizar los datos de un Equipo
	 * de forma simplificada
	 * @since 1.0
	 * @return Devuelve el texto con que el equipo será visualizado
	 */
	public String toString() 
	{
		return nomEquipo;
	}
	
	public int getCodEquipo() 
	{
		return codEquipo;
	}

	public String getNomEquipo() 
	{
		return nomEquipo;
	}

	public String getIniEquipo() 
	{
		return iniEquipo;
	}
}