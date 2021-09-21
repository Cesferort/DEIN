package beans;

/**
 * Esta clase nos permitirá crear objetos Usuario.
 * @author Cesferort
 * @since 1.0
 */
public class Usuario 
{
	/**
	 * Identificador del usuario.
	 */
	private int 	numUsu;
	/**
	 * Nombre del usuario.
	 */
	private String 	nomUsu;
	/**
	 * Contraseña del usuario.
	 */
	private String 	conUsu;
	/**
	 * Representa la disponibilidad de permisos de administrador del usuario.
	 */
	private boolean esAdmin;
	
	/**
	 * Constructor de un usuario en base a los parámetros recibidos.
	 * @since 1.0
	 * @param numUsu - Número de usuario
	 * @param esAdmin - Representa el estado del usuario, si este es un usuario
	 * o un administrador
	 * @param nomUsu - Nombre del usuario
	 * @param conUsu - Contraseña del usuario
	 */
	public Usuario(int numUsu, String nomUsu, String conUsu, boolean esAdmin) 
	{
		this.numUsu = numUsu;
		this.esAdmin = esAdmin;
		this.nomUsu = nomUsu;
		this.conUsu = conUsu;		
	}

	/**
	 * toString que nos permitirá visualizar los datos de un Usuario
	 * de forma simplificada
	 * @since 1.0
	 * @return Devuelve el texto con el que el usuario será visualizado
	 */
	public String toString() 
	{
		return numUsu+ " - Nombre: "+nomUsu;
	}
	
	public boolean isEsAdmin() 
	{
		return esAdmin;
	}

	public String getNomUsu() 
	{
		return nomUsu;
	}

	public String getConUsu() 
	{
		return conUsu;
	}

	public int getNumUsu() 
	{
		return numUsu;
	}
	
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conUsu == null) ? 0 : conUsu.hashCode());
		result = prime * result + (esAdmin ? 1231 : 1237);
		result = prime * result + ((nomUsu == null) ? 0 : nomUsu.hashCode());
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
		Usuario other = (Usuario) obj;
		if (conUsu == null) {
			if (other.conUsu != null)
				return false;
		} else if (!conUsu.equals(other.conUsu))
			return false;
		if (esAdmin != other.esAdmin)
			return false;
		if (nomUsu == null) {
			if (other.nomUsu != null)
				return false;
		} else if (!nomUsu.equals(other.nomUsu))
			return false;
		return true;
	}
}