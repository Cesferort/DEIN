package Excepciones;

/**
 * Esta clase nos permitirá crear excepciones de tipo DatosException
 * @author Cesferort
 * @since 1.0
 */
public class DatosException extends Exception
{
	private static final long serialVersionUID = 1L;
	/**
	 * Variable en la que guardaremos descripciones de todos los errores 
	 * que han surgido.
	 */
	private String listaErrores;
	
	/**
	 * Constructor que guardará el texto recibido como parámetro en el atributo 
	 * de la clase utilizado por el toString para visualizar la excepción.
	 * @since 1.0
	 * @param listaErrores
	 */
	public DatosException(String listaErrores)
	{
		this.listaErrores = listaErrores;
	}
	
	/**
	 * Método toString que nos permitirá visualizar la lista de errores guardada. 
	 * @since 1.0
	 * @return devuelve un texto con la lista de errores
	 */
	public String toString()
	{
		return listaErrores;
	}
}