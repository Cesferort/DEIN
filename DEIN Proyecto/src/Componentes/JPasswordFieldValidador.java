package Componentes;
import java.awt.Color;
import javax.swing.JPasswordField;

/**
 * Esta clase nos permitirá crear una versión personalizada del
 * componente JPasswordField.
 * @author Cesferort
 * @since 1.0
 */
public class JPasswordFieldValidador extends JPasswordField
{
	private static final long serialVersionUID = 1L;
	/**
	 * Máximo de carácteres que puede tener una contraseña.
	 */
	private final int MAX_CONTRASENIA = 32;
	/**
	 * Un rojo clarito utilizado para mostrar que contiene
	 * datos no válidos.
	 */
	private final Color LIGHT_RED = new Color(255,102,102);
	/**
	 * Un amarillo clarito utilizado para advertir que el
	 * máximo de carácteres ha sido alcanzado.
	 */
	private final Color LIGHT_YELLOW = new Color(255,255,152);
	/**
	 * Valor lógico que representa si el usuario está
	 * escribiendo sobre el JPassWordFieldValidador o no.
	 */
	private boolean estaEscribiendo;
	
	/**
	 * Constructor por defecto de un JPasswordFieldValidador.
	 * @since 1.0
	 */
	public JPasswordFieldValidador()
	{
		super();
		estaEscribiendo = false;
	}
		
	/**
	 * Método que comprueba si la longitud de la contraseña introducida
	 * por el usuario ha alcanzado el máximo permitido.
	 * @since 1.0
	 */
	public void maximoAlcanzado()
	{
		String conUsuario = String.valueOf(this.getPassword());
		if(conUsuario.length() > MAX_CONTRASENIA)
		{
			conUsuario = conUsuario.substring(0, conUsuario.length() - 1);
			this.setText(conUsuario);
			this.setBackground(LIGHT_YELLOW);
		}
		estaEscribiendo = false;
	}
	
	/**
	 * Este método colorea de rojo el fondo en base al parámetro 
	 * recibido. De esta forma se muestra al usuario que el valor
	 * introducido es incorrecto.
	 * @since 1.0
	 * @param check - Valor lógico que representa si el valor
	 * intoducido por el usuario es correcto o no
	 */
	public void esIncorrecto(boolean check)
	{
		if(check == true)
			this.setBackground(LIGHT_RED);
		estaEscribiendo = false;
	}
	
	/**
	 * Cuando el usuario alcanza el máximo de longitud permitido o
	 * la contraseña es incorrecta, el fondo del campo cambia de 
	 * color. El propósito de este método es limpiar el color del
	 * una vez el usuario vuelva a escribir. 
	 * @since 1.0
	 * @param check - Valor lógico que representa si el usuario
	 * está escribiendo
	 */
	public void estaEscribiendo(boolean check)
	{
		if(check == true && estaEscribiendo == false)
		{
			this.setBackground(Color.WHITE);
			estaEscribiendo = true;
		}
	}
}