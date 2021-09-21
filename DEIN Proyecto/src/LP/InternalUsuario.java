package LP;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import Componentes.JTextFieldNumericos;
import Componentes.ModeloTablaUsuarios;
import Excepciones.DatosException;
import XML.GestorXML_Usuarios;
import beans.Usuario;
import java.awt.Component;

/**
 * Clase que representa la ventana de visualización, edición e 
 * inserción de los usuarios.
 * @author Cesferort
 * @since 1.0
 */
public class InternalUsuario extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	/**
	 * Color personalizado que utilizaremos para visualizar un rojo claro.
	 */
	private final Color LIGHT_RED = new Color(255,102,102);
	/**
	 * Color personalizado que utilizaremos para visualizar un verde claro.
	 */
	private final Color LIGHT_GREEN = new Color(102,255,102);
	/**
	 * Tabla en la que serán visualizados los usuarios de la aplicación.
	 */
	private JTable tablaUsu;
	/**
	 * Modelo de tabla personalizado que utilizaremos para crear y controlar
	 * la tabla de usuarios.
	 */
	private ModeloTablaUsuarios modeloTablaUsu;
	/**
	 * Gestor de archivos XML que utilizaremos para gestionar todas las 
	 */
	private GestorXML_Usuarios gestorXML_Usuarios;
	/**
	 * Campo de texto personalizado en el que solo se aceptarán datos numéricos. Donde se 
	 * visualizará el identificador del usuario seleccionado o a insertar.
	 */
	private JTextFieldNumericos numUsu;
	/**
	 * Campo de texto donde se visualizará el nombre del usuario seleccionado o a insertar.
	 */
	private JTextField nomUsu;
	/**
	 * Campo de texto donde se visualizará la contraseña del usuario seleccionado o a insertar.
	 */
	private JTextField conUsu;
	/**
	 * Desplegable donde se visualizará el nombre del usuario seleccionado o a insertar.
	 */
	private JComboBox<String>esAdmin;
	/**
	 * Botón utilizado para cambiar entre modos de la ventana. Estos modos serían: edición e
	 * inserción.
	 */
	private JButton butCambiarModo;
	/**
	 * Botón utilizado para intentar guardar los cambios realizados sobre los campos.
	 */
	private JButton butGuardar;
	/**
	 * Valor lógico que representa en que modo se encuentra la venta. 
	 */
	private boolean modoEdicion;
	
	/**
	 * Constructor del InternalUsuario. Inicializa el modo de la 
	 * ventana a edición.
	 * @since 1.0
	 */
	public InternalUsuario()
	{
		modoEdicion = true;
		gestorXML_Usuarios = new GestorXML_Usuarios();
		dibujar();
		eventos();
		setTooltips();
	}
	
	/**
     * Método que dibuja el frame. Será llamado por el constructor.
     * @since 1.0
     */
	private void dibujar()
	{
		this.setTitle("Usuarios (Modo EDICIÓN)");
		getContentPane().setLayout(new GridLayout(1,2));
		
		ArrayList<Usuario>listaUsuarios = gestorXML_Usuarios.conseguirUsuarios();
		modeloTablaUsu = new ModeloTablaUsuarios(listaUsuarios);
		tablaUsu = new JTable(modeloTablaUsu);
		tablaUsu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		numUsu = new JTextFieldNumericos();
		numUsu.setEditable(false);
		numUsu.setPreferredSize(new Dimension(200,20));
		nomUsu = new JTextField();
		nomUsu.setPreferredSize(new Dimension(200,20));
		conUsu = new JTextFieldNumericos();
		conUsu.setPreferredSize(new Dimension(200,20));
		
		JPanel panId = new JPanel();
		panId.setAlignmentX(Component.LEFT_ALIGNMENT);
		panId.setLayout(new FlowLayout());
		JLabel labId = new JLabel("Identificador:");
		labId.setPreferredSize(new Dimension(150,20));
		panId.add(labId);
		panId.add(numUsu);
		
		JPanel panNom = new JPanel();
		panNom.setAlignmentX(Component.LEFT_ALIGNMENT);
		panNom.setLayout(new FlowLayout());
		JLabel labNom = new JLabel("Nombre:");
		labNom.setPreferredSize(new Dimension(150,20));
		panNom.add(labNom);
		panNom.add(nomUsu);
		
		JPanel panCon = new JPanel();
		panCon.setAlignmentX(Component.LEFT_ALIGNMENT);
		panCon.setLayout(new FlowLayout());
		JLabel labCon = new JLabel("Contraseña:");
		labCon.setPreferredSize(new Dimension(150,20));
		panCon.add(labCon);
		panCon.add(conUsu);
		
		JPanel panAdmin = new JPanel();
		panAdmin.setAlignmentX(Component.LEFT_ALIGNMENT);
		panAdmin.setLayout(new FlowLayout());
		JLabel labAdmin = new JLabel("Es Admin:");
		labAdmin.setPreferredSize(new Dimension(150,20));
		panAdmin.add(labAdmin);
		esAdmin = new JComboBox<String>();
		esAdmin.addItem("NO");
		esAdmin.addItem("SI");
		esAdmin.setSelectedItem("NO");
		esAdmin.setPreferredSize(new Dimension(200,20));
		panAdmin.add(esAdmin);
		
		JPanel panInsercion = new JPanel();
		panInsercion.setAlignmentX(Component.LEFT_ALIGNMENT);
		panInsercion.setLayout(new FlowLayout());
		JLabel espacioBlanco1 = new JLabel();
		espacioBlanco1.setPreferredSize(new Dimension(150,20));
		panInsercion.add(espacioBlanco1);
		butCambiarModo = new JButton("Modo INSERCIÓN");
		butCambiarModo.setPreferredSize(new Dimension(200,20));
		panInsercion.add(butCambiarModo);
		JPanel panGuardar = new JPanel();
		panGuardar.setAlignmentX(Component.LEFT_ALIGNMENT);
		panGuardar.setLayout(new FlowLayout());
		JLabel espacioBlanco2 = new JLabel();
		espacioBlanco2.setPreferredSize(new Dimension(150,20));
		panGuardar.add(espacioBlanco2);
		butGuardar = new JButton("Guardar"); 
		butGuardar.setPreferredSize(new Dimension(200,20));
		panGuardar.add(butGuardar);
		
		JPanel panDerecha = new JPanel();
		panDerecha.setLayout(new BoxLayout(panDerecha,BoxLayout.Y_AXIS));
		panDerecha.add(panId);
		panDerecha.add(panNom);
		panDerecha.add(panCon);
		panDerecha.add(panAdmin);
		panDerecha.add(panInsercion);
		panDerecha.add(panGuardar);
		
		getContentPane().add(new JScrollPane(tablaUsu));
		getContentPane().add(panDerecha);
		
		this.pack();
		this.setResizable(false);
		this.setClosable(true);
        this.setMaximizable(true);
        try 
        {
			this.setMaximum(true);
		} 
        catch (PropertyVetoException e) 
        {
        	JOptionPane.showMessageDialog
			(null, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
       	}
        this.setIconifiable(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	/**
     * Método que creará escuchadores relacionados con elementos del frame.
     * Será llamado por el constructor.
     * @since 1.0
     */
	private void eventos()
	{
		ListSelectionModel selectionModel = tablaUsu.getSelectionModel();
		selectionModel.addListSelectionListener(new TablaUsuListener()); 

		butCambiarModo.addActionListener(new CambiarModoListener(this));	
		butGuardar.addActionListener(new ButGuardarListener());
		
		numUsu.addKeyListener(new TextFieldListener());
		nomUsu.addKeyListener(new TextFieldListener());
		conUsu.addKeyListener(new TextFieldListener());
	}
	
	/**
     * Clase escuchadora de acciones sobre la tabla de usuarios.
     * @author Cesferort
     * @since 1.0
     */
	private class TablaUsuListener implements ListSelectionListener
	{
	    /**
	     * Método llamado por el escuchador cuando ocurre una selección sobre la tabla
	     * de usuarios. Una vez ocurra la selección actualizaremos la información mostrada
	     * en los campos.
	     * @since 1.0
	     * @param evento - Evento de tipo de ActionEvent escuchado por el ActionListener
	     */
		public void valueChanged(ListSelectionEvent evento) 
	    {
	    	DefaultListSelectionModel model = (DefaultListSelectionModel) evento.getSource();
	    	int indiceUsuario = model.getLeadSelectionIndex();
	    	try 
	    	{
	    		if(indiceUsuario != -1)
	    		{
		    		String idUsuSelec = String.valueOf(modeloTablaUsu.getValueAt(indiceUsuario, 0));
			    	String nomUsuSelec = String.valueOf(modeloTablaUsu.getValueAt(indiceUsuario, 1));
			    	String conUsuSelec = String.valueOf(modeloTablaUsu.getValueAt(indiceUsuario, 2));
				    boolean esAdminSelec = Boolean.parseBoolean(String.valueOf(modeloTablaUsu.getValueAt(indiceUsuario, 3)));
				    
					numUsu.setText(idUsuSelec);
					nomUsu.setText(nomUsuSelec);
					conUsu.setText(conUsuSelec);
					if(esAdminSelec == false)
						esAdmin.setSelectedIndex(0);
					else
						esAdmin.setSelectedIndex(1);
	    		}
	    	}
	    	catch(Exception e) 
	    	{
	    		JOptionPane.showMessageDialog
				(null, "Los valores encontrados en la base de datos no son válidos","Error",JOptionPane.ERROR_MESSAGE);
	    	}
	    }
	}

	/**
     * Clase escuchadora de acciones sobre el botón butCambiarModo.
     * @author Cesferort
     * @since 1.0
     */
	private class CambiarModoListener implements ActionListener
	{
		/**
		 * Atributo utilizado para guardar una instancia de InternalUsuario que nos facilitará
		 * la comunicación entre el escuchador y la instancia.
		 */
		private InternalUsuario internalUsuario;
		
		/**
		 * Constructor del escuchador CambiarModoListener que recibirá una instancia de 
		 * InternalUsuario a guardar.
		 * @since 1.0
		 * @param internalUsuario
		 */
		public CambiarModoListener(InternalUsuario internalUsuario)
		{
			this.internalUsuario=internalUsuario;
		}
		
		/**
		 * Método llamado por el escuchador cuando ocurra un evento. En este método 
		 * comprobaremos el modo en el que nos encontramos y procederemos a hacer los cambios
		 * necesarios para transicionar entre modos. Los modos serían edición e inserción.
		 * @since 1.0
		 * @param evento - Evento de tipo de ActionEvent escuchado por el ActionListener
		 */
		public void actionPerformed(ActionEvent evento) 
		{
			limpiar();
			if(modoEdicion == true)
			{
				internalUsuario.setTitle("Usuarios (Modo INSERCIÓN)");
				numUsu.setEditable(true);
				butCambiarModo.setText("Modo EDICIÓN");
				modoEdicion = false;
			}
			else
			{
				internalUsuario.setTitle("Usuarios (Modo EDICIÓN)");
				numUsu.setEditable(false);
				butCambiarModo.setText("Modo INSERCI�N");
				modoEdicion = true;
			}
		}
	}
	
	/**
     * Clase escuchadora de acciones sobre el botón butGuardar.
     * @author Cesferort
     * @since 1.0
     */
	private class ButGuardarListener implements ActionListener
	{
		/**
		 * Método llamado por el escuchador cuando ocurra un acción sobre el botón butGuardar. 
		 * En este método se validarán los datos introducidos en los diferentes campos y se
		 * procederá a intentar editar o insertar un usuario con ellos.
		 * @since 1.0
		 * @param evento - Evento de tipo de ActionEvent escuchado por el ActionListener
		 */
		public void actionPerformed(ActionEvent evento) 
		{
			try 
			{
				int erroresEncontrado = 0;
				String listaErrores="";
				
				int numUsuParse = 0;
				String nomUsuParse = "";
				String conUsuParse = "";
				boolean esAdminParse = false;
				
				//CODIGO COMPROBACION
				if(numUsu.comprobarNumero() == false)
				{
					erroresEncontrado++;
					listaErrores += "- El valor introducido como Identificador no es válido\n";
				}
				else
				{
					numUsuParse = Integer.parseInt(numUsu.getText());
					if(gestorXML_Usuarios.idLibre(numUsuParse) == false)
					{
						erroresEncontrado++;
						listaErrores += "- El valor introducido como Identificador no está disponible\n";
						numUsu.setBackground(LIGHT_RED);
					}
				}
				
				//NOMBRE COMPROBACION
				nomUsuParse = nomUsu.getText(); 
				if(nomUsuParse == null || nomUsuParse.length() == 0)
				{
					erroresEncontrado++;
					listaErrores += "- El valor introducido como Nombre no es válido\n";
					nomUsu.setBackground(LIGHT_RED);
				}
				else if(gestorXML_Usuarios.nombreLibre(numUsuParse,nomUsuParse) == true)
					nomUsu.setBackground(LIGHT_GREEN);
				else
				{
					erroresEncontrado++;
					listaErrores += "- Nombre de usuario no disponible. Pruebe con otro\n";
					nomUsu.setBackground(LIGHT_RED);
				}	
				
				//CONTRASEÑA COMPROBACION
				conUsuParse = conUsu.getText(); 
				if(conUsuParse.length() < 5)
				{
					erroresEncontrado++;
					listaErrores += "- El valor introducido como Contraseña no llega a un mínimo de 5 carácteres\n";
					conUsu.setBackground(LIGHT_RED);
				}
				else
					conUsu.setBackground(LIGHT_GREEN);
				
				//FILTRAR ESADMIN
				if(esAdmin.getSelectedItem().equals("SI"))
					esAdminParse = true;
				
				//LANZAR ERRORES
				if(erroresEncontrado > 0)
				{
					if(erroresEncontrado == 1)
						listaErrores = "Valor no permitido encontrado:\n" + listaErrores;
					else
						listaErrores = "Valores no permitidos encontrados:\n" + listaErrores;
					
					throw new DatosException(listaErrores);
				}
				
				
				if(modoEdicion == false)
				{
					gestorXML_Usuarios.aniadirUsuario
					(
						numUsuParse,
						nomUsuParse,
						conUsuParse,
						esAdminParse
					);
					refrescarTabla();
					limpiar();
				}
				else
				{
					gestorXML_Usuarios.guardarCambios
					(
						numUsuParse,
						nomUsuParse,
						conUsuParse,
						esAdminParse
					);				
					refrescarTabla();
					limpiar();
				}
			}
			catch(DatosException e)
			{
				JOptionPane.showMessageDialog
				(null, e.toString(),"Error",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
     * Clase escuchadora de teclas sobre los text field numUsu, nomUsu, conUsu
     * @author Cesferort
     * @since 1.0
     */
	private class TextFieldListener implements KeyListener
	{
		/**
		 * Método llamado por el escuchador cuando ocurra una acción de escritura sobre el campo
		 * de texto. Una vez ocurrido el intento se restablecerá el color de fondo a blanco.
		 * @since 1.0
		 * @param evento - Evento de tipo de ActionEvent escuchado por el KeyListener
		 */
		public void keyTyped(KeyEvent evento) 
		{
			JTextField txt = (JTextField)evento.getSource();
			txt.setBackground(Color.WHITE);
		}
		public void keyPressed(KeyEvent evento) {}
		public void keyReleased(KeyEvent evento) {}    	
	}
	
	/**
	 * Método que actualiza la tabla con los datos de usuarios más recientes gracias
	 * a la ayuda del gestor de archivos XML.
	 * @since 1.0
	 */
	private void refrescarTabla()
	{
		ArrayList<Usuario>listaUsuarios = gestorXML_Usuarios.conseguirUsuarios();
		modeloTablaUsu = new ModeloTablaUsuarios(listaUsuarios);
		modeloTablaUsu.fireTableDataChanged();
		tablaUsu.setModel(modeloTablaUsu);
	}
	
	/**
	 * Método que añade ayudas tooltip a los elementos del DialogLogin. 
	 * @since 1.0
	 */
	private void setTooltips()
	{
		numUsu.setToolTipText("Número que identifica a los usuarios");
		nomUsu.setToolTipText("Nombre que el usuario utilizará para iniciar sesión");
		conUsu.setToolTipText("Contraseña vinculada al nombre de inicio determinado");
		esAdmin.setToolTipText("Asignación de permisos de administrador sobre la app");
		butCambiarModo.setToolTipText("Transicionar entre los modos Edición e Inserción");
		butGuardar.setToolTipText("Aplicar las acciones sobre la base de datos");
	}
	
	/**
	 * Método que limpia el contenido y color de fondo de los campos del frame.
	 * @since 1.0
	 */
	private void limpiar()
	{
		numUsu.limpiar();
		nomUsu.setText("");
		nomUsu.setBackground(Color.WHITE);
		conUsu.setText("");
		conUsu.setBackground(Color.WHITE);
	}
}