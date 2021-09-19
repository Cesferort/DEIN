package LP;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import XML.GestorXML_Deportistas;

/**
 * Clase que nos permitirá crear un JFrame que representará la ventana principal
 * del programa. 
 * @author Cesferort
 * @since 1.0
 */
public class VentanaPrincipal extends FormHelp
{
	private static final long serialVersionUID = 1L;
	/**
	 * Desktop pane utilizado para la visualización de paneles.
	 */
	private JDesktopPane desktopPane;
	/**
	 * Item del menú utilizado para abrir la ventana asociada a los deportistas de la
	 * base de datos.
	 */
	private JMenuItem mntmDeportista;
	/**
	 * Item del menú utilizado para abrir la ventana asociada a los equipos de la
	 * base de datos.
	 */
	//private JMenuItem mntmEquipo;
	/**
	 * InternalFrame en el que se llevarán a cabo acciones relacionadas con los
	 * deportistas de la base de datos. 
	 */
	private InternalDeportista intDeportista;
	/**
	 * InternalFrame en el que se llevarán a cabo acciones relacionadas con los
	 * equipos de la base de datos. 
	 */
	//private InternalEquipo intEquipo;
	/**
	 * InternalFrame en el que se llevarán a cabo acciones relacionadas con los
	 * usuarios de la aplicación.
	 */
	private InternalUsuario intUsuarios;
	/**
	 * Dialog utilizado para pedir datos al usuario con los que hacer un intento
	 * de inicio de sesión en la app.
	 */
	private DialogLogin login;
	/**
	 * Item del menú utilizado para ejecutar la funcionalidad asociada a la importación
	 * de deportistas a través de un XML. 
	 */
	private JMenuItem mntmImportarDep;
	/**
	 * Item del menú utilizado para ejecutar la funcionalidad asociada a la exportación
	 * de deportistas a través de un XML.   
	 */
	private JMenuItem mntmExportarDep;
	/**
	 * Item del menú utilizado para abrir la ventana asociada a la gestión de usuarios
	 * de la aplicación.
	 */
	private JMenuItem mntmGestUser;
	/**
	 * Item del menú utilizado para abrir el JavaHelp asociado a la aplicación.
	 */
	private JMenuItem mntmAyuda;
	/**
	 * Item del menú utilizado para abrir la ventana asociada al envío de reports por
	 * parte de los usuarios.
	 */
	private JMenuItem mntmReport;
	/**
	 * Valor lógico que representa la disponibilidad de permisos de administrador por 
	 * parte del usuario que ha iniciado sesión.
	 */
	private boolean esAdmin;
	/**
	 * Valor lógico que representa si el inicio de sesión ha sido correcto.
	 */
	private boolean iniDeSesionCorrecto;
	/**
	 * Valor textual que representa el nombre del usuario que ha iniciado sesión.
	 */
	private String nomUsuario;
	
	/**
	 * Constructor de la ventana principal. Inicializará el JDialog DialogLogin
	 * para pedir al usuario datos de inicio de sesión.
	 * @since 1.0
	 */
	public VentanaPrincipal() 
	{
		esAdmin = false;
		iniDeSesionCorrecto = false;
		login = new DialogLogin(this);
		login.setModal(true);
		login.setVisible(true);
		
		if(iniDeSesionCorrecto == true)
		{
			dibujar();
			eventos();
			setHelp();
			setTooltips();
		}
	}
	
	/**
     * Método que dibuja el JFrame. Será llamado por el constructor.
     * @since 1.0
     */
	public void dibujar()
	{		
		this.setTitle("Proyecto DEIN");
		
		desktopPane = new JDesktopPane();  
		desktopPane.setPreferredSize(new Dimension(800,400));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmImportarDep = new JMenuItem("Importar deportistas (XML)");
		mnFile.add(mntmImportarDep);
		
		mntmExportarDep = new JMenuItem("Exportar deportistas (XML)");
		mnFile.add(mntmExportarDep);
		
		JMenu mnOlimpiada = new JMenu("Olimpiada");
		menuBar.add(mnOlimpiada);
		
		mntmDeportista = new JMenuItem("Deportistas (Edici\u00F3n e Inserci\u00F3n)");
		mnOlimpiada.add(mntmDeportista);
		
		//mntmEquipo = new JMenuItem("Equipos (Edici\u00F3n e Inserci\u00F3n)");
		//mnOlimpiada.add(mntmEquipo);

		if(esAdmin == true)
		{
			//EL USUARIO HA INICIADO SESIÓN COMO ADMINISTRADOR
			JMenu mnUsuarios = new JMenu("Usuarios");
			menuBar.add(mnUsuarios);
			mntmGestUser = new JMenuItem("Gestión de usuarios");
			mnUsuarios.add(mntmGestUser);
		}
		
		JMenu mnAyuda = new JMenu("Opciones");
		menuBar.add(mnAyuda);
		
		mntmAyuda = new JMenuItem("Ayuda");
		mnAyuda.add(mntmAyuda);
		
		mntmReport = new JMenuItem("Reportar un problema...");
		mnAyuda.add(mntmReport);
		
		intDeportista = new InternalDeportista();
		desktopPane.add(intDeportista);
		
		//intEquipo = new InternalEquipo();
		//desktopPane.add(intEquipo);
		
		intUsuarios = new InternalUsuario();
		desktopPane.add(intUsuarios, BorderLayout.WEST);
		
		//JavaHelp
		Container contentPane = getContentPane();
		contentPane.add(desktopPane, BorderLayout.CENTER);  
		JButton botonInutil = new JButton();
		botonInutil.setPreferredSize(new Dimension(1,1));
		contentPane.add(botonInutil, BorderLayout.WEST);
		 
		this.pack();
		this.setVisible(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
     * Método que creará escuchadores relacionados con elementos del JFrame.
     * Será llamado por el constructor.
     * @since 1.0
     */
	public void eventos()
	{
		mntmDeportista.addActionListener(new MntmDeportistaListener());
		//mntmEquipo.addActionListener(new MntmEquipoListener());
		mntmImportarDep.addActionListener(new MntmImpDepListener());
		mntmExportarDep.addActionListener(new MntmExpDepListener());
		if(esAdmin == true)
			mntmGestUser.addActionListener(new MntmGestUser());
		mntmReport.addActionListener(new MntmReportListener());
		intDeportista.addInternalFrameListener(new IntDeportistaListener());
		//intEquipo.addInternalFrameListener(new IntEquipoListener());
		intUsuarios.addInternalFrameListener(new IntUsuariosListener());
	}

	/**
     * Clase escuchadora de acciones sobre el menú item mntmDeportista.
     * @author Cesferort
     * @since 1.0
     */
	private class MntmDeportistaListener implements ActionListener
	{
		/**
		 * Método llamado por el escuchador cuando ocurra un evento sobre el menú item asociado
		 * con los deportistas. Procederemos a visualizar el internal frame validando si este 
		 * ha sido instanciado o no.
		 * @since 1.0
		 * @param evento - Evento de tipo de ActionEvent escuchado por el ActionListener
		 */
		public void actionPerformed(ActionEvent evento) 
		{
			if(intDeportista == null)
			{
				intDeportista = new InternalDeportista();
				intDeportista.addInternalFrameListener(new IntDeportistaListener());
				intDeportista.setVisible(true);
				desktopPane.add(intDeportista);					
			}
			else
			{
				try 
			    {
					intDeportista.setMaximum(true);
					intDeportista.setVisible(true);
				} 
			    catch (PropertyVetoException e) 
			    {
			    	e.printStackTrace();
				}
			}
		}
	}
	
	/**
     * Clase escuchadora de acciones sobre el menú item mntmEquipo.
     * @author Cesferort
     * @since 1.0
     */
	//private class MntmEquipoListener implements ActionListener
	//{
		//public void actionPerformed(ActionEvent evento) 
		//{
			//if(intEquipo == null)
			//{
				//intEquipo = new InternalEquipo();
				//intEquipo.addInternalFrameListener(new IntEquipoListener());
				//intEquipo.setVisible(true);
				//desktopPane.add(intEquipo);					
			//}
			//else
			//{
				//try 
			    //{
					//intEquipo.setMaximum(true);
					//intEquipo.setVisible(true);
				//} 
			    //catch (PropertyVetoException e) 
			    //{
			    	//JOptionPane.showMessageDialog
					//(null, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		       	//}
			//}
		//}
	//}
	
	/**
     * Clase escuchadora de acciones sobre el menú item mntmImportarDep.
     * @author Cesferort
     * @since 1.0
     */
	private class MntmImpDepListener implements ActionListener
	{		
		/**
		 * Instancia del gestor de archivos XML relacionado a los deportistas
		 * que utilizaremos para gestionar el XML relacionado a los deportistas.
		 */
		private GestorXML_Deportistas gestorXML_Deportistas;
		
		/**
		 * Constructor por defecto en el que instanciaremos un gestor de archivos
		 * XML relacionados con los deportistas de la base de datos.
		 */
		public MntmImpDepListener()
		{
			gestorXML_Deportistas = new GestorXML_Deportistas();
		}
		
		/**
		 * Método llamado por el escuchador cuando ocurra un evento sobre el menú item asociado
		 * con la importación de deportistas a través de un XML. Procederemos a ejecutar 
		 * la funcionalidad.
		 * @since 1.0
		 * @param evento - Evento de tipo de ActionEvent escuchado por el ActionListener
		 */
		public void actionPerformed(ActionEvent evento) 
		{
			JFileChooser selector = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		    FileNameExtensionFilter filtro = new FileNameExtensionFilter("XML Files", "xml");
		    selector.setFileFilter(filtro);
		    int returnVal = selector.showSaveDialog(null); 
		    
		    if(returnVal == JFileChooser.APPROVE_OPTION) 
		    {		    	
		    	try 
		    	{
				    selector.getSelectedFile().getAbsolutePath();
		    		String nomArchivo = selector.getSelectedFile().getName();
			    	String extension = nomArchivo.substring(nomArchivo.length()-3);
			    	if(extension.equals("xml"))
			    	{
			    		//EXTENSIÓN CORRECTA
				    	String absPath = selector.getSelectedFile().getAbsolutePath();
				    	String resultErrores = gestorXML_Deportistas.construirDocImpDep(absPath);
				    	if(resultErrores != null)
				    		throw new Exception(resultErrores);
				    	if(intDeportista != null)
				    		intDeportista.refrescarLista();
			    	}
			    	else
			    	{
			    		//EXTENSIÓN INCORRECTA
			    		throw new Exception("El archivo no es un XML o no está bien formateado");
			    	}
		    	}
		    	catch(Exception e) 
		    	{
		    		JOptionPane.showMessageDialog
					(null, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		       	}
		    }		       
		}
	}
	
	/**
     * Clase escuchadora de acciones sobre el menú item mntmExportarDep.
     * @author Cesferort
     * @since 1.0
     */
	private class MntmExpDepListener implements ActionListener
	{		
		/**
		 * Instancia del gestor de archivos XML relacionado a los deportistas
		 * que utilizaremos para gestionar el XML relacionado a los deportistas.
		 */
		private GestorXML_Deportistas gestorXML_Deportistas;
		
		/**
		 * Constructor por defecto en el que instanciaremos un gestor de archivos
		 * XML relacionados con los deportistas de la base de datos. 
		 */
		public MntmExpDepListener()
		{
			gestorXML_Deportistas = new GestorXML_Deportistas();
		}
		
		/**
		 * Método llamado por el escuchador cuando ocurra un evento sobre el menú item asociado
		 * con la exportación de deportistas a través de un XML. Procederemos a ejecutar 
		 * la funcionalidad.
		 * @since 1.0
		 * @param evento - Evento de tipo de ActionEvent escuchado por el ActionListener
		 */
		public void actionPerformed(ActionEvent evento) 
		{
			JFileChooser selector = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			int returnVal = selector.showSaveDialog(null); 
		   		    
		    if(returnVal == JFileChooser.APPROVE_OPTION) 
		    {		
		    	String nomFich = selector.getSelectedFile()+".xml";
		    	gestorXML_Deportistas.exportarDeportistas(nomFich);
		    }
		}
	}
	
	/**
     * Clase escuchadora de acciones sobre el menú item mntmGestUser.
     * @author Cesferort
     * @since 1.0
     */
	private class MntmGestUser implements ActionListener
	{	
		/**
		 * Método llamado por el escuchador cuando ocurra un evento sobre el menú item asociado
		 * con la gestión de usuarios de la aplicación. Procederemos a visualizar el internal 
		 * frame validando si este ha sido instanciado o no.
		 * @since 1.0
		 * @param evento - Evento de tipo de ActionEvent escuchado por el ActionListener
		 */
		public void actionPerformed(ActionEvent evento) 
		{
			if(intUsuarios == null)
			{
				intUsuarios = new InternalUsuario();
				intUsuarios.addInternalFrameListener(new IntUsuariosListener());
				intUsuarios.setVisible(true);
				desktopPane.add(intUsuarios);					
			}
			else
			{
				try 
			    {
					intUsuarios.setMaximum(true);
					intUsuarios.setVisible(true);
				} 
			    catch (PropertyVetoException e) 
			    {
			    	JOptionPane.showMessageDialog
					(null, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		       	}
			}
		}
	}
	
	/**
     * Clase escuchadora de acciones sobre el menú item mntmReportListener.
     * @author Cesferort
     * @since 1.0
     */
	private class MntmReportListener implements ActionListener
	{	
		/**
		 * Método llamado por el escuchador cuando ocurra un evento sobre el menú item asociado
		 * con la publicación de reports por parte del usuario. Procederemos a visualizar el 
		 * dialog para los reports.
		 * @since 1.0
		 * @param evento - Evento de tipo de ActionEvent escuchado por el ActionListener
		 */
		public void actionPerformed(ActionEvent evento) 
		{
			DialogReport dialogReport = new DialogReport(nomUsuario);
			dialogReport.setModal(true);
			dialogReport.setVisible(true);
		}
	}
	
	/**
     * Clase escuchadora de acciones sobre el internal frame intDeportista.
     * @author Cesferort
     * @since 1.0
     */
	private class IntDeportistaListener extends InternalFrameAdapter
	{
		/**
		 * Método llamado por el escuchador cuando el internal frame sea cerrado. Al ocurrir esto
		 * pasaremos la variable que guarda el internal a guardar un valor nulo.
		 * @since 1.0
		 * @param evento - Evento de tipo de ActionEvent escuchado por el InternalFrameAdapter
		 */
		public void internalFrameClosed(InternalFrameEvent evento) 
		{
			intDeportista = null;
		}
	}
	
	/**
     * Clase escuchadora de acciones sobre el internal frame intEquipo.
     * @author Cesferort
     * @since 1.0
	private class IntEquipoListener extends InternalFrameAdapter
	{
		public void internalFrameClosed(InternalFrameEvent evento) 
		{
			intEquipo = null;
		}
	}
	*/
	
	/**
     * Clase escuchadora de acciones sobre el internal frame intUsuarios.
     * @author Cesferort
     * @since 1.0
     */
	private class IntUsuariosListener extends InternalFrameAdapter
	{
		/**
		 * Método llamado por el escuchador cuando el internal frame sea cerrado. Al ocurrir esto
		 * pasaremos la variable que guarda el internal a guardar un valor nulo.
		 * @since 1.0
		 * @param evento - Evento de tipo de ActionEvent escuchado por el InternalFrameAdapter
		 */
		public void internalFrameClosed(InternalFrameEvent evento) 
		{
			intUsuarios = null;
		}
	}
	
	/**
	 * Método que asigna el correspondiente JavaHelp a la VentanaPrincipal.
	 * @since 1.0
	 */
	public void setHelp() 
	{
		this.getObjHelpBroker().enableHelpOnButton(mntmAyuda, "VentanaPrincipal", this.getObjHelpSet());
	}
	
	/**
     * Método que añade ayudas tooltip a los elementos del DialogLogin. 
     * @since 1.0
     */
	private void setTooltips()
	{
		mntmDeportista.setToolTipText("Visualización, edición e inserción de deportistas");
		//mntmEquipo.setToolTipText("Visualización, edición e inserción de equipos");
		mntmImportarDep.setToolTipText("Importación en masa de deportistas a través de un XML");
		mntmExportarDep.setToolTipText("Exportación de todos los deportistas a un XML");
		if(esAdmin == true)
			mntmGestUser.setToolTipText("Visualización, edición e inserción de usuarios");
		mntmAyuda.setToolTipText("Abrir la ayuda vinculada a la aplicación");
		mntmReport.setToolTipText("Notificar un problema o sugerencia");
	}
	
	/**
	 * Método que guarda si el usuario que ha iniciado sesión es administrador o no
	 * en base al parámetro recibido.
	 * @since 1.0
	 * @param bool - representa si el usuario ha iniciado sesión correctamente como
	 * admin o usuario
	 */
	public void setEsAdmin(boolean bool,String nomUsuario)
	{
		esAdmin = bool;
		iniDeSesionCorrecto = true;
		this.nomUsuario=nomUsuario;
	}
}