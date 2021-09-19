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
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import BD.GestorBD;
import Componentes.JTextFieldNumericos;
import Excepciones.DatosException;
import beans.Deportista;
import java.awt.Component;

/**
 * Clase que representa la ventana de visualización, edición e 
 * inserción de los deportistas.
 * @author Cesferort
 * @since 1.0
 */
public class InternalDeportista extends JInternalFrame
{
	private static final long serialVersionUID = 1L;
	/**
	 * Color rojo claro.
	 */
	private final Color LIGHT_RED = new Color(255,102,102);
	/**
	 * Color verde claro.
	 */
	private final Color LIGHT_GREEN = new Color(102,255,102);
	/**
	 * Gestor de base de datos.
	 */
	private GestorBD gestorBD;					//temp
	/**
	 * Lista de deportistas.
	 */
	private JList<Deportista>listaDeportistas;	//temp
	/**
	 * Modelo de la tabla de deportistas.
	 */
	private DefaultListModel<Deportista>modelo;	//temp
	/**
	 * Campo de tipo JTextFieldNumericos en el que visualizaremos el código identificador 
	 * del deportista.
	 */
	private JTextFieldNumericos codDep;
	/**
	 * Campo textual en el que visualizaremos el nombre del deportista.
	 */
	private JTextField nomDep;
	/**
	 * Desplegable en el que visualizaremos el sexo del deportista.
	 */
	private JComboBox<String>sexoDep;
	/**
	 * Campo de tipo JTextFieldNumericos en el que visualizaremos el peso 
	 * del deportista.
	 */
	private JTextFieldNumericos pesDep;
	/**
	 * Campo de tipo JTextFieldNumericos en el que visualizaremos la altura 
	 * del deportista. 
	 */
	private JTextFieldNumericos altDep;
	/**
	 * Botón utilizado por el usuario para cambiar entre modos de edición e 
	 * inserción.
	 */
	private JButton butCambiarModo;
	/**
	 * Botón utilizado para guardar los datos encontrados en los campos con 
	 * relación a la información al deportista.
	 */
	private JButton butGuardar;
	/**
	 * Valor lógico que gestiona el modo en el que se encuentra la aplicación.
	 * Modo edición o inserción de deportistas.
	 */
	private boolean modoEdicion;
	
	/**
	 * Constructor del InternalDeportista. Inicializa el modo de la 
	 * ventana a edición. También instancia un gestor de la base de datos.
	 * {@link #dibujar()}: Método encargado de dibujar el InternalDeportista
	 * {@link #eventos()}: Método encargado de asociar escuchadores a los elementos del 
	 * InternalDeportista.
	 * {@link #setTooltips()}: Método encargado de añadir ayudas tooltip a los elementos del 
	 * InternalDeportista. 
	 * @since 1.0
	 */
	public InternalDeportista()
	{
		modoEdicion = true;
		gestorBD = new GestorBD();
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
		this.setTitle("Deportistas (Modo EDICIÓN)");
		getContentPane().setLayout(new GridLayout(1,2));
		
		modelo=new DefaultListModel<Deportista>();
		listaDeportistas=new JList<Deportista>();
		listaDeportistas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaDeportistas.setModel(modelo);
		refrescarLista();
		JScrollPane scrollLista=new JScrollPane(listaDeportistas,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JPanel panDerecha = new JPanel();
		panDerecha.setLayout(new BoxLayout(panDerecha,BoxLayout.Y_AXIS));
		codDep = new JTextFieldNumericos();
		codDep.setEditable(false);
		codDep.setPreferredSize(new Dimension(200,20));
		nomDep = new JTextField();
		nomDep.setPreferredSize(new Dimension(200,20));
		
		sexoDep = new JComboBox<String>();
		sexoDep.addItem("Sin definir");
		sexoDep.addItem("M");
		sexoDep.addItem("F");
		sexoDep.setSelectedItem("Sin definir");
		sexoDep.setPreferredSize(new Dimension(200,20));
		
		pesDep = new JTextFieldNumericos();
		pesDep.setPreferredSize(new Dimension(200,20));
		altDep = new JTextFieldNumericos();
		altDep.setPreferredSize(new Dimension(200,20));
		
		JPanel panId = new JPanel();
		panId.setAlignmentX(Component.LEFT_ALIGNMENT);
		panId.setLayout(new FlowLayout());
		JLabel labId = new JLabel("Identificador:");
		labId.setPreferredSize(new Dimension(150,20));
		panId.add(labId);
		panId.add(codDep);
		
		JPanel panNom = new JPanel();
		panNom.setAlignmentX(Component.LEFT_ALIGNMENT);
		panNom.setLayout(new FlowLayout());
		JLabel labNom = new JLabel("Nombre:");
		labNom.setPreferredSize(new Dimension(150,20));
		panNom.add(labNom);
		panNom.add(nomDep);
		
		JPanel panSex = new JPanel();
		panSex.setAlignmentX(Component.LEFT_ALIGNMENT);
		panSex.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panSex.setLayout(new FlowLayout());
		JLabel labSex = new JLabel("Sexo:");
		labSex.setPreferredSize(new Dimension(150,20));
		panSex.add(labSex);
		panSex.add(sexoDep);
		
		JPanel panPes = new JPanel();
		panPes.setAlignmentX(Component.LEFT_ALIGNMENT);
		panPes.setLayout(new FlowLayout());
		JLabel labPes = new JLabel("Peso:");
		labPes.setPreferredSize(new Dimension(150,20));
		panPes.add(labPes);
		panPes.add(pesDep);
		
		JPanel panAlt = new JPanel();
		panAlt.setAlignmentX(Component.LEFT_ALIGNMENT);
		panAlt.setLayout(new FlowLayout());
		JLabel labAlt = new JLabel("Altura:");
		labAlt.setPreferredSize(new Dimension(150,20));
		panAlt.add(labAlt);
		panAlt.add(altDep);
		
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
		
		panDerecha.add(panId);
		panDerecha.add(panNom);
		panDerecha.add(panSex);
		panDerecha.add(panPes);
		panDerecha.add(panAlt);
		panDerecha.add(panInsercion);
		panDerecha.add(panGuardar);
		
		//getContentPane().add(new JScrollPane(tablaUsu));
		getContentPane().add(scrollLista);
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
		listaDeportistas.addListSelectionListener(new ListaListener());
		butGuardar.addActionListener(new ButGuardarListener());
		butCambiarModo.addActionListener(new CambiarModoListener(this));
		
		codDep.addKeyListener(new TextFieldListener());
		nomDep.addKeyListener(new TextFieldListener());
		pesDep.addKeyListener(new TextFieldListener());
		altDep.addKeyListener(new TextFieldListener());
		
		sexoDep.addActionListener(new ActionListener() 
		{
			/**
			 * Método llamado por el escuchador cuando ocurra una acción sobre el campo de texto 
			 * para la introducción del sexo. Su único proposito será restablecer el color de
			 * fondo del campo a blanco. 
			 * @since 1.0
			 * @param evento - Evento de tipo de ActionEvent escuchado por el ActionListener
			 */
			public void actionPerformed(ActionEvent evento) 
			{
				sexoDep.setBackground(Color.WHITE);
			}
		});
	}
	
	/**
     * Clase escuchadora de selecciones sobre la lista listaDeportistas.
     * @author Cesferort
     * @since 1.0
     */
	private class ListaListener implements ListSelectionListener
	{
		/**
		 * Método llamado al ocurrir una selección en la lista de deportistas.
		 * Gracias a este método podremos mantener todos los campos de datos 
		 * actualizados en base al deportista seleccionado.
		 * @since 1.0
		 * @param evento - Evento de tipo ListSelectionEvent.
		 */
		public void valueChanged(ListSelectionEvent evento) 
		{
			Deportista depRecipiente = listaDeportistas.getSelectedValue();
			
			if(depRecipiente != null)
			{
				codDep.limpiar();
				codDep.setText(String.valueOf(depRecipiente.getCodDep()));
				nomDep.setBackground(Color.WHITE);
				nomDep.setText(depRecipiente.getNomDep());
				sexoDep.setBackground(Color.WHITE);
				sexoDep.setSelectedItem(depRecipiente.getSexDep());
				pesDep.limpiar();
				pesDep.setText(String.valueOf(depRecipiente.getPesDep()));
				altDep.limpiar();
				altDep.setText(String.valueOf(depRecipiente.getAltDep()));
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
		 * Método llamado al ocurrir una acción sobre el botón butGuardar.
		 * Comprobaremos el modo en el que nos encontramos, ya sea edición o 
		 * inserción, validaremos la información y haremos las acciones
		 * convenientes de edición o inserción de datos en la base. Para esto
		 * haremos uso del gestor de la base de datos: {@link GestorBD}.
		 * @since 1.0
		 * @see InternalDeportista#butGuardar
		 * @param evento - Evento de tipo ActionEvent.
		 */
		public void actionPerformed(ActionEvent evento) 
		{
			try 
			{
				int erroresEncontrado = 0;
				String listaErrores = "";
				
				int codDepParse = 0;
				String nomDepParse = "";
				int pesDepParse = 0;
				int altDepParse = 0;
				
				//NOMBRE COMPROBACION
				nomDepParse = nomDep.getText(); 
				if(nomDepParse == null || nomDepParse.length() == 0)
				{
					erroresEncontrado++;
					listaErrores += "- El valor introducido como Nombre no es válido\n";
					nomDep.setBackground(LIGHT_RED);
				}
				else
					nomDep.setBackground(LIGHT_GREEN);
				
				//PESO COMPROBACION
				if(pesDep.comprobarNumero(0,500)==false)
				{
					erroresEncontrado++;
					listaErrores += "- El valor introducido como Peso no es válido\n";
				}
				else
					pesDepParse = Integer.parseInt(pesDep.getText());
				
				//ALTURA COMPROBACION
				if(altDep.comprobarNumero(0,350)==false)
				{
					erroresEncontrado++;
					listaErrores += "- El valor introducido como Altura no es válido\n";
				}
				else
					altDepParse = Integer.parseInt(altDep.getText());
				
				//SEXO COMPROBACION
				String sexDepParse = sexoDep.getSelectedItem().toString();
				if(sexDepParse.equals("Sin definir"))
				{
					erroresEncontrado++;
					listaErrores += "- Sexo sin definir\n";
					sexoDep.setBackground(LIGHT_RED);
				}
				else
					sexoDep.setBackground(LIGHT_GREEN);
				
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
					//CODIGO COMPROBACION
					if(codDep.comprobarNumero() == false)
					{
						erroresEncontrado++;
						listaErrores += "- El valor introducido como Identificador no es válido\n";
					}
					else
					{
						codDepParse = Integer.parseInt(codDep.getText());
						if(gestorBD.codDepLibre(codDepParse) == false)
						{
							erroresEncontrado++;
							listaErrores += "- El valor introducido como Identificador no está disponible\n";
							codDep.setBackground(LIGHT_RED);
						}
					}
					
					if(gestorBD.aniadirDeportista
					(
						codDepParse,
						nomDepParse, 
						sexDepParse,
						pesDepParse,
						altDepParse
					) == false)
					{
						codDep.setBackground(LIGHT_RED);
						JOptionPane.showMessageDialog
						(null, "Valor no permitido encontrado:\nEl identificador de Deportista no es válido","Error",JOptionPane.ERROR_MESSAGE);
					}
					else
						limpiar();
				}
				else
				{
					gestorBD.guardarCambios
					(
						Integer.parseInt(codDep.getText()),
						nomDep.getText(),
						sexoDep.getSelectedItem().toString(),
						pesDepParse,
						Integer.parseInt(altDep.getText())
					);				
					refrescarLista();
					limpiar();
				}
				refrescarLista();
			}
			catch(DatosException e)
			{
				JOptionPane.showMessageDialog
				(null, e.toString(),"Error",JOptionPane.ERROR_MESSAGE);
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
		 * Variable en la que guardaremos una instancia del internal 
		 * deportista que recibiremos como parámetro en el constructor.
		 * Esto facilitará la comunicación entre ambos.
		 * @since 1.0
		 */
		private InternalDeportista internalDeportista;
		
		/**
		 * Constructor del CambiarModoListener que recibe como parámetro una
		 * instanciación de InternalDeportista.
		 * @since 1.0
		 * @param internalDeportista - Instanciación del InternalDeportista
		 */
		public CambiarModoListener(InternalDeportista internalDeportista)
		{
			this.internalDeportista=internalDeportista;
		}
		
		/**
		 * Método llamado al ocurrir una acción sobre el botón de cambiar modo. 
		 * Comprobaremos en que modo nos encontramos y cambiaremos al modo inverso
		 * en base a ello, a parte de llamar al método {@link InternalDeportista#limpiar()}
		 * para limpiar la ventana.
		 * @since 1.0
		 * @param evento - Evento de tipo ActionEvent.
		 */
		public void actionPerformed(ActionEvent evento) 
		{
			limpiar();
			
			if(modoEdicion == true)
			{
				internalDeportista.setTitle("Deportistas (Modo INSERCI�N)");
				refrescarLista();
				codDep.setEditable(true);
				butCambiarModo.setText("Modo EDICI�N");
				modoEdicion = false;
			}
			else
			{
				internalDeportista.setTitle("Deportistas (Modo EDICI�N)");
				refrescarLista();
				codDep.setEditable(false);
				butCambiarModo.setText("Modo INSERCI�N");
				modoEdicion = true;
			}
		}
	}

	/**
     * Clase escuchadora de teclas sobre los text field codDep, nomDep, pesDep y altDep.
     * @author Cesferort
     * @since 1.0
     */
	private class TextFieldListener implements KeyListener
	{
		/**
		 * Método llamado al escribir un caracter sobre el campo que tiene asignado
		 * el listener. Gracias a esto podremos reiniciar el color de fondo a blanco
		 * por si anteriormente su estado había sido cambiado a rojo, para representar
		 * que la información no era válida o amarillo para representar que el máximo
		 * número de carácteres había sido alcanzado.
		 * @since 1.0
		 * @param evento - Evento de tipo ActionEvent.
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
	 * Método que asignará ayudas tooltip a los elementos del InternalDeportista.
	 * @since 1.0
	 */
	private void setTooltips()
	{
		codDep.setToolTipText("Número que identifica a los deportistas");
		nomDep.setToolTipText("Nombre del deportista a insertar");
		sexoDep.setToolTipText("Sexo del deportista a insertar");
		pesDep.setToolTipText("Peso del deportista a insertar");
		altDep.setToolTipText("Altura del deportista a insertar");
		butCambiarModo.setToolTipText("Transicionar entre los modos Edición e Inserción");
		butGuardar.setToolTipText("Aplicar las acciones sobre la base de datos");
	}
	
	/**
	 * Método que actualiza la lista de deportistas en base a los deportistas
	 * encontrados en la base de datos. Para esto hará uso del gestor de la 
	 * base de datos.
	 * @since 1.0
	 */
	public void refrescarLista()
	{
		modelo.removeAllElements();
		Deportista deportistas[] = gestorBD.conseguirDeportistas();
		if (deportistas == null)
			return;
		
		for(int i = 0; i < deportistas.length; i++)
			modelo.addElement(deportistas[i]);
	}
	
	/**
	 * Método que limpia el contenido y color de fondo de los campos del frame.
	 * @since 1.0
	 */
	private void limpiar()
	{
		codDep.limpiar();
		nomDep.setText("");
		nomDep.setBackground(Color.WHITE);
		sexoDep.setSelectedItem("Sin definir");
		pesDep.limpiar();
		altDep.limpiar();
	}
}