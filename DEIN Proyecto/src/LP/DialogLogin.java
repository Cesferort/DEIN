package LP;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Componentes.JPasswordFieldValidador;
import XML.GestorXML_Usuarios;

/**
 * Clase que nos permitirá crear un JDialog utilizado para pedir al usuario
 * que introduzca un nombre y contraseña de usuario para iniciar sesión
 * en la aplicación. En caso de ocurrir un inicio de sesión válido, se abrirá
 * la ventana principal.
 * @author Cesferort
 * @since 1.0
 */
public class DialogLogin extends JDialog 
{
	private static final long serialVersionUID = 1L;
	/**
	 * Color rojo claro.
	 */
	private final Color LIGHT_RED = new Color(255,102,102);
	/**
	 * Instancia de la ventana principal que ha llamado al DialogLogin.
	 */
	private VentanaPrincipal ventanaPrincipal;
	/**
	 * Gestor XML encargado de gestionar los usuarios.
	 */
	private GestorXML_Usuarios gestorXML_Usuarios;
	/**
	 * Panel que contiene el campo en el que el usuario deberá introducir
	 * su nombre y el label asociado al campo.
	 */
	private JPanel panelNombre;
	/**
	 * Panel que contiene el campo en el que el usuario deberá introducir 
	 * su contraseña y el label asociado al campo.
	 */
	private JPanel panelContra;
	/**
	 * Panel que contiene el campo en el que se encuentra el botón con el que
	 * el usuario procederá a hacer un intento de inicio de sesión con los valores
	 * introducidos como nombre y contraseña. También contiene un label en blanco
	 * por motivos de diseño. 
	 */
	private JPanel panelEnviar;
	/**
	 * Campo de texto en el que el usuario deberá introducir su nombre de inicio
	 * de sesión.
	 */
	private JTextField txtNomUsuario;
	/**
	 * Campo para contraseñas en el que el usuario deberá introducir la contraseña
	 * vinculada a su nombre de inicio de sesión.
	 */
	private JPasswordFieldValidador passwordUsuario;
	/**
	 * Botón con el que usuario procederá a hacer un intento de inicio de sesión
	 * en la aplicación.
	 */
	private JButton butIniciar;
	
	/**
	 * Constructor que guardará una instancia de la ventana principal para poder comunicarse
	 * con ella tras la introducción de los datos del usuario. También creará una instancia 
	 * del gestor XML de usuarios y llamará a 3 métodos: 
	 * {@link #dibujar()}: Método encargado de dibujar el DialogLogin
	 * {@link #eventos()}: Método encargado de asociar escuchadores a los elementos del 
	 * DialogLogin.
	 * {@link #setTooltips()}: Método encargado de añadir ayudas tooltip a los elementos del 
	 * DialogLogin. 
	 * @since 1.0
	 * @param ventanaPrincipal - ventana principal del programa
	 */
    public DialogLogin(VentanaPrincipal ventanaPrincipal) 
    {
    	this.ventanaPrincipal = ventanaPrincipal;
    	gestorXML_Usuarios = new GestorXML_Usuarios();
    	dibujar();
    	eventos();
    	setTooltips();
    }
    
    /**
     * Método que dibuja el JDialog. Será llamado por el constructor.
     * @since 1.0
     */
    private void dibujar()
    {       
        this.setTitle("Inicio de sesión");
    	this.setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
    	
    	//PANEL NOMBRE
        panelNombre = new JPanel(new FlowLayout());
        JLabel labNom = new JLabel();
    	labNom.setText("Nombre de usuario: ");
    	labNom.setPreferredSize(new Dimension(150,20));
    	txtNomUsuario = new JTextField();
    	txtNomUsuario.setPreferredSize(new Dimension(200,20));
    	panelNombre.add(labNom);
        panelNombre.add(txtNomUsuario);
        
    	//PANEL CONTRASEÑA
        panelContra = new JPanel(new FlowLayout());
        JLabel labCon = new JLabel();
        labCon.setText("Contraseña: ");
        labCon.setPreferredSize(new Dimension(150,20));
        passwordUsuario = new JPasswordFieldValidador();
        passwordUsuario.setPreferredSize(new Dimension(200,20));
        panelContra.add(labCon);
        panelContra.add(passwordUsuario);
        
        //PANEL INICIAR
        panelEnviar = new JPanel(new FlowLayout());
        butIniciar = new JButton("Iniciar sesión");
        butIniciar.setPreferredSize(new Dimension(200,20));
        JLabel espacioBlanco = new JLabel();
        espacioBlanco.setPreferredSize(new Dimension(150,20));
        panelEnviar.add(espacioBlanco);
        panelEnviar.add(butIniciar);
        
        this.add(panelNombre);
        this.add(panelContra);
        this.add(panelEnviar);
        this.pack();
        
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    /**
     * Método que creará escuchadores relacionados con elementos del JDialog.
     * Será llamado por el constructor.
     * @since 1.0
     */
    private void eventos()
    {
    	butIniciar.addActionListener(new ActionListener() 
    	{
			public void actionPerformed(ActionEvent evento) 
			{
				iniciar();
			}
		});
    	passwordUsuario.addKeyListener(new Password_TxtNomListener());
    	txtNomUsuario.addKeyListener(new Password_TxtNomListener());
    }
    
    /**
     * Método que añade ayudas tooltip a los elementos del DialogLogin. 
     * @since 1.0
     */
    private void setTooltips()
	{
    	txtNomUsuario.setToolTipText("Nombre que utilizarás para iniciar sesión");
    	passwordUsuario.setToolTipText("Contraseña asociada al nombre de usuario utilizado");
    }
    
    /**
     * Método que intentar iniciar sesión con los datos introducidos por el usuario. Para
     * esto hará uso del método {@link GestorXML_Usuarios#comprobarDatos(String, String)}.
     * En caso de que los datos sean válidos se procederá a llamar al método 
     * {@link #iniciarSesionAdmin(boolean, String)}.
     * @since 1.0
     */
    private void iniciar()
    {
		String nomUsuario = txtNomUsuario.getText().trim();    	
    	String conUsuario = String.valueOf(passwordUsuario.getPassword());  
        
        int checkDatos = gestorXML_Usuarios.comprobarDatos(nomUsuario,conUsuario);
        if(checkDatos == -1 || checkDatos == 0)
        {
        	txtNomUsuario.setBackground(LIGHT_RED);
        	passwordUsuario.esIncorrecto(true);
        	JOptionPane.showMessageDialog
			(null, "El nombre de usuario y contraseña no corresponden","Error",JOptionPane.ERROR_MESSAGE);
        }
        else if(checkDatos == 1)
        {
        	this.setVisible(false);
        	this.iniciarSesionAdmin(false,nomUsuario);
        }
        else if(checkDatos == 2)
        {
        	this.setVisible(false);
        	this.iniciarSesionAdmin(true,nomUsuario);
        }
        else
        {
        	JOptionPane.showMessageDialog
			(null, "Ha ocurrido un error intentando validar los datos de inicio de sesión","Error",JOptionPane.ERROR_MESSAGE);
       	}
    }
    
    /**
     * Clase escuchadora de acciones sobre el JPasswordFieldValidador y
     * TextField del nombre de usuario.
     * @author Cesferort
     * @since 1.0
     */
    private class Password_TxtNomListener implements KeyListener
    {
    	/**
         * Método llamadoque llama a los métodos estaEscribiendo y maximoAlcanzado del 
         * JPasswordFieldValidador. Las llamadas ocurrirán al escribir una letra
         * dentro del campo. A parte de esto también limpia el color de fondo 
         * del TextField del nombre del usuario.
         * @since 1.0
         * @see beans.Deportista
         * @param evento - Evento de tipo de KeyEvent escuchado por el KeyListener
         */
    	public void keyTyped(KeyEvent evento) 
		{
			passwordUsuario.estaEscribiendo(true);
    		passwordUsuario.maximoAlcanzado();
    		txtNomUsuario.setBackground(Color.WHITE);
		}
    	
    	/**
    	 * Método que es llamado al presionar una tecla. En caso de que esta tecla
    	 * sea la tecla Enter se intentará iniciar sesión gracias 
    	 * para acelerar y facilitar el uso. 
    	 * @since 1.0
    	 * @param evento - Evento de tipo de KeyEvent escuchado por el KeyListener
    	 */
		public void keyPressed(KeyEvent evento) 
		{
			if(evento.getKeyCode() == KeyEvent.VK_ENTER)
    			iniciar();
		}
		
		public void keyReleased(KeyEvent evento) {}    	
    }
    
    /**
     * Método que llama al método setEsAdmin de la instancia guardada de 
     * VentanaPrincipal
     * @since 1.0
     */
    public void iniciarSesionAdmin(boolean check, String nomUsuario)
    {
    	ventanaPrincipal.setEsAdmin(check,nomUsuario);
    }
}