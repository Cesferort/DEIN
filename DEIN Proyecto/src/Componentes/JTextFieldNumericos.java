package Componentes;
import java.awt.Color;
import javax.swing.JTextField;

/**
 * Esta clase nos permitirá crear una versión personalizada del
 * componente JTextField.
 * @author Cesferort
 * @since 1.0
 */
public class JTextFieldNumericos extends JTextField
{
	private static final long serialVersionUID = 1L;
	/**
	 * Un rojo clarito utilizado para mostrar que contiene
	 * datos no válidos.
	 */
	public final Color LIGHT_RED = new Color(255,102,102);
	/**
	 * Un verde clarito utilizado para mostrar que contiene
	 * datos válidos.
	 */
	public final Color LIGHT_GREEN = new Color(102,255,102);
	
	/**
	 * Método que comprueba si el valor dentro del JTextFieldNumericos
	 * es númerico gracias al método {@link #esNumerico()}. Cambiará el 
	 * color de fondo en base al resultado.
	 * @since 1.0
	 * @return Devuelve falso o verdadero en base al resultado de
	 * la comprobación
	 */
	public boolean comprobarNumero()
	{
		if(esNumerico()==false)
		{
			this.setBackground(LIGHT_RED);
			return false;
		}
		else 
		{
			this.setBackground(LIGHT_GREEN);
			return true;
		}
	}
	
	/**
	 * Método que comprueba si el valor dentro del JTextFieldNumericos
	 * es númerico gracias al método {@link #esNumerico()} y se encuentra 
	 * dentro de dos límites numéricos gracias al método 
	 * {@link #seEncuentraEntre(int, int, int) } Cambiará el color de 
	 * fondo en base al resultado.
	 * @since 1.0
	 * @param min - Límite mínimo
	 * @param max - Límite máximo
	 * @return Devuelve falso o verdadero en base al resultado de
	 * la comprobación
	 */
	public boolean comprobarNumero(int min, int max)
	{
		if(esNumerico()==false)
		{
			this.setBackground(LIGHT_RED);
			return false;
		}
		else 
		{
			int num = Integer.parseInt(getText());
			if (seEncuentraEntre(num,min,max)==false)
			{
				this.setBackground(LIGHT_RED);
				return false;
			}
			else
			{
				this.setBackground(LIGHT_GREEN);
				return true;
			}
		}
	}
	
	/**
	 * Método que comprueba si el valor dentro del JTextFieldNumericos
	 * es númerico.
	 * @since 1.0
	 * @return Devuelve falso o verdadero en base al resultado de
	 * la comprobación
	 */
	private boolean esNumerico()
	{
		String texto = getText();
	    if (texto == null || texto.length() == 0)
	    	return false;
	    try
	    {
			int parsePrueba = Integer.parseInt(texto);
		    parsePrueba = parsePrueba + 1;
	    }
	    catch(NumberFormatException e) 
	    {
	    	return false;
	    }
	    return true;
	}
	
	/**
	 * Método que comprueba si el valor recibido se encuentra 
	 * dentro de dos límites numéricos. 
	 * @since 1.0
	 * @param num - Valor numérico a comprobar
	 * @param min - Límite mínimo (dentro del código pasará a ser 
	 * un límite máximo en caso de ser más grande que max)
	 * @param max - Límite máximo (dentro del código pasará a ser 
	 * un límite mínimo en caso de ser más pequeño que min)
	 * @return Devuelve falso o verdadero en base al resultado de
	 * la comprobación
	 */
	private boolean seEncuentraEntre(int num, int min, int max)
	{
		if(max < min)
		{
			int aux = max;
			max = min;
			min = aux;
		}
		
		if(min <= num && max >= num)
			return true;
		return false;
	}
	
	/**
	 * Método que limpia el contenido y color de fondo del 
	 * JTextFieldNumericos
	 * @since 1.0
	 */
	public void limpiar()
	{
		this.setText("");
		this.setBackground(Color.WHITE);
	}
}