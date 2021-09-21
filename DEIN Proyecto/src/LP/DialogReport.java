package LP;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import XML.GestorXML_Usuarios;

/**
 * Clase que nos permitirá crear un JDialog utilizado para pedir al usuario
 * que introduzca un nombre y contraseña de usuario antes de abrir la ventana
 * principal.
 * @author Cesferort
 * @since 1.0
 */
public class DialogReport extends JDialog 
{
	private static final long serialVersionUID = 1L;
	/**
	 * Nombre de usuario que está enviando el report.
	 */
	private String nomUsuario;
	/**
	 * Panel que contiene el campo en el que el usuario deberá introducir
	 * el título del report y el label asociado al campo.
	 */
	private JPanel panelTitulo;
	/**
	 * Panel que contiene el campo en el que el usuario deberá introducir
	 * la descripción del report y el label asociado al campo.
	 */
	private JPanel panelQueja;
	/**
	 * Panel que contiene el botón que procede a intentar enviar el report
	 * escrito por el usuario. 
	 */
	private JPanel panelEnviar;
	/**
	 * Campo en el que el usuario deberá escribir el título del report.
	 */
	private JTextField txtTitulo;
	/**
	 * Campo de texto en el que el usuario deberá escribir la descripción
	 * del report.
	 */
	private JTextArea txtAreaQueja;
	/**
	 * Butón para restablecer el campo de texto en el que el usuario
	 * deberá escribir el report.
	 */
	private JButton butBorrar;
	/**
	 * Butón para proceder un intento de envío del report.
	 */
	private JButton butEnviar;
	
	/**
	 * Constructor de DialogReport que recibe el nombre del usuario como parámetro.
	 * Se ayudará de los métodos:
	 * {@link #dibujar()}: Método encargado de dibujar el DialogLogin
	 * {@link #eventos()}: Método encargado de asociar escuchadores a los elementos del 
	 * DialogReport.
	 * @since 1.0
	 * @param nomUsuario - Nombre del usuario que está enviando el report
	 */
    public DialogReport(String nomUsuario) 
    {
    	this.nomUsuario = nomUsuario;
    	dibujar();
    	eventos();
    }
    
    /**
     * Método que dibuja el DialogReport. Será llamado por el constructor.
     * @since 1.0
     */
    private void dibujar()
    {       
        this.setTitle("Reportar un problema");
    	this.setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
    	
    	//PANEL TITULO
        panelTitulo = new JPanel(new FlowLayout());
        JLabel labTit = new JLabel();
        labTit.setText("Título: ");
        labTit.setPreferredSize(new Dimension(150,20));
    	txtTitulo = new JTextField();
    	txtTitulo.setPreferredSize(new Dimension(200,20));
    	panelTitulo.add(labTit);
    	panelTitulo.add(txtTitulo);
        
    	//PANEL QUEJA
    	panelQueja = new JPanel(new FlowLayout());
        txtAreaQueja = new JTextArea();
        txtAreaQueja.setLineWrap(true);
        JScrollPane scrollQueja = new JScrollPane(txtAreaQueja);
        scrollQueja.setPreferredSize(new Dimension(350,100));
        scrollQueja.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panelQueja.add(scrollQueja);
        
        //PANEL ENVIAR
        panelEnviar = new JPanel(new FlowLayout());
        butEnviar = new JButton("Enviar");
        butEnviar.setPreferredSize(new Dimension(200,20));
        butBorrar = new JButton("Borrar");
        butBorrar.setPreferredSize(new Dimension(150,20));
        panelEnviar.add(butBorrar);
        panelEnviar.add(butEnviar);
        
        this.add(panelTitulo);
        this.add(panelQueja);
        this.add(panelEnviar);
        this.pack();
        
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    /**
     * Método que se encarga de asignar escuchadores a los elementos
     * del DialogReport.
     * @since 1.0
     */
    private void eventos()
    {
    	butBorrar.addActionListener(new ActionListener() 
    	{
			/**
			 * Método llamado por el escuchador cuando el botón butBorrar es pulsado.
			 * Su único proposito será restablecer/borrar el la descripción del report
			 * escrita por el usuario.
			 * @since 1.0
			 * @param evento - Evento de tipo de ActionEvent escuchado por el ActionListener
			 */    		
    		public void actionPerformed(ActionEvent evento) 
			{
				txtAreaQueja.setText("");
			}
		});
    	
    	butEnviar.addActionListener(new ButEnviarListener(this));
    }
    
    /**
     * Clase escuchadora del botón butEnviar.
     * @author Cesferort
     * @since 1.0
     * @see DialogReport#butEnviar
     */
    private class ButEnviarListener implements ActionListener
    {
    	/**
    	 * Instancia del DialogReport que recibiremos en el constructor.
    	 */
    	private DialogReport dialog;
    	/**
    	 * Gestor del XML de usuarios que utilizaremos para guardar en la 
    	 * información de los usuarios.
    	 */
    	private GestorXML_Usuarios gestorXML_Usuarios;
    	
    	/**
    	 * Constructor de la clase ButEnviarListener que recibe la instancia
    	 * del DialogReport como parámetro para facilitar la comunicación
    	 * entre clases.
    	 * @param dialog - Instancia del DialogReport.
    	 */
		public ButEnviarListener(DialogReport dialog)
		{
			this.dialog = dialog;
			gestorXML_Usuarios = new GestorXML_Usuarios();
		}
		
		/**
		 * Método llamado por el escuchador cuando ocurra un evento sobre el botón butEnviar. 
		 * En este método validaremos la información que se desea enviar como report y en 
		 * caso de ser válida guardaremos en el XML con ayuda del método 
		 * {@link GestorXML_Usuarios#crearReport(String, String, String)}.
		 * @since 1.0
		 * @param evento - Evento de tipo de ActionEvent escuchado por el ActionListener
		 */
		public void actionPerformed(ActionEvent evento)
		{
			boolean textoValido = false;
			boolean tituloValido = false;
			
			String titulo = txtTitulo.getText();
			if(titulo != null && titulo.length() > 0)
				tituloValido = true;

			String texto = "";
			texto = txtAreaQueja.getText();
			if(texto != null && texto.length() > 0)
				textoValido = true;
			
			if(tituloValido == false && textoValido == false)
			{
    			JOptionPane.showMessageDialog
    			(null, "No puede hacer un report sin título ni información.","Error",JOptionPane.ERROR_MESSAGE);
           	}
			else if(tituloValido == false)
			{
				JOptionPane.showMessageDialog
    			(null, "No puede hacer un report sin título.","Error",JOptionPane.ERROR_MESSAGE);
           	}
			else if(textoValido == false)
			{
				JOptionPane.showMessageDialog
    			(null, "No puede hacer un report sin información.","Error",JOptionPane.ERROR_MESSAGE);
           	}
			else
			{
				if(gestorXML_Usuarios.crearReport(nomUsuario,titulo,texto) == true)
				{
    				JOptionPane.showMessageDialog
    				(null, "Report enviado correctamente.","Report",JOptionPane.INFORMATION_MESSAGE);
    				
    				dialog.setVisible(false);
				}
				else
				{
    				JOptionPane.showMessageDialog
    				(null, "Ha ocurrido un error enviando el report. Vuelve a intentarlo.","Report",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
    }
}