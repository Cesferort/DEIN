package XML;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import beans.Usuario;

/**
 * Este Gestor de XML nos permitirá gestionar los usuarios.
 * @author Cesferort
 * @since 1.0
 */
public class GestorXML_Usuarios 
{	
	/**
	 * Nombre y direccion del archivo XML en el que guardamos los usuarios de la aplicación.
	 */
	private final String USER_XML = "usuarios.xml";
	/**
	 * Documento en el que cargaremos todos los usuarios del XML.
	 */
	private Document docUsuarios;
	
	/**
	 * Constructor que crea una instancia del gestor de la base de datos y 
	 * llama al método {@link #construirDocUsuarios()} para construir el 
	 * documento.
	 * @since 1.0
	 */
	public GestorXML_Usuarios()
	{
		construirDocUsuarios();
	}
	
	/**
	 * Método que construye el documento de usuarios en base a un archivo XML.
	 * @since 1.0
	 */
	private void construirDocUsuarios() 
	{
		try
		{
			SAXBuilder builder = new SAXBuilder();
			File f = new File(USER_XML);
			FileInputStream fis = new FileInputStream(f);
			docUsuarios = builder.build(fis);
			fis.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println(e.getMessage());
		} 
		catch (JDOMException e) 
		{
			System.out.println(e.getMessage());
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Método que devuelve una lista de usuarios encontrada en el
	 * documento.
	 * @since 1.0
	 * @return Devuelve la lista de usuarios.
	 */
	public ArrayList<Usuario> conseguirUsuarios()
	{
		ArrayList<Usuario>result = new ArrayList<Usuario>();
		
		Element eUsuarios = docUsuarios.getRootElement();
		List<Element>listaUsuarios = eUsuarios.getChildren();
		Iterator<Element>itListaUsuarios = listaUsuarios.iterator();
		while(itListaUsuarios.hasNext())
		{
			Element eUsuario = itListaUsuarios.next();
			boolean esAdmin = false;
			int numUsu = Integer.parseInt(eUsuario.getAttribute("id").getValue());
			
			String textoEsAdmin = eUsuario.getAttribute("esAdmin").getValue();
			if(textoEsAdmin.equals("S"))
				esAdmin = true;
			
			Element eNomUsuario = eUsuario.getChild("nomUsuario");
			Element eConUsuario = eUsuario.getChild("conUsuario");
			
			Usuario usu = new Usuario(numUsu, eNomUsuario.getText(),eConUsuario.getText(),esAdmin);
			result.add(usu);
		}
		return result;
	}
	
	/**
	 * Método que comprueba si el nombre de usuario y contraseña recibidas como 
	 * parámetros son correctas. Para esto hace uso del documento construido con 
	 * el método construirDocUsuarios, llamado en el constructor.
	 * @since 1.0
	 * @see GestorXML_Usuarios#construirDocUsuarios()
	 * @param nomUsuario - nombre del usuario a comprobar
	 * @param conUsuario - contraseña del usuario a comprobar
	 * @return -1 - Nombre de usuario no encontrado
	 * 			0 - Contraseña incorrecta
	 * 			1 - Usuario corriente iniciado correctamente
	 * 			2 - Usuario administrador iniciado correctamente
	 */
	public int comprobarDatos(String nomUsuario, String conUsuario) 
	{
		Element eUsuarios = docUsuarios.getRootElement();
		List<Element>listaUsuarios = eUsuarios.getChildren(); 
		Iterator<Element>itListaUsuarios = listaUsuarios.iterator();
		while(itListaUsuarios.hasNext())
		{
			Element eUsuario = itListaUsuarios.next();
			String nombre = eUsuario.getChild("nomUsuario").getValue();
			String contra = eUsuario.getChild("conUsuario").getValue();
			
			if(nomUsuario.equals(nombre)) 		
			{
				if(conUsuario.equals(contra))								
				{
					String esAdmin = "N";
					if(eUsuario.getAttribute("esAdmin")!=null)
						esAdmin = eUsuario.getAttribute("esAdmin").getValue();
					if(esAdmin.toUpperCase().equals("S"))
						return 2;		
					return 1;			
				}
				return 0;				
			}
		}
		return -1;						
	}
	
	/**
	 * Método que comprueba si un nombre recibido como parámetro está libre
	 * o no. También recibe el identificador para no hacer la comprobación
	 * con el nombre del usuario que está siendo editado.
	 * @since 1.0
	 * @param numUsuBuscar - Identificador del usuario que está siendo editado
	 * @param nomUsuBuscar - Nombre a comprobar
	 * @return Devuelve valor lógico que representa si el nombre está libre
	 */
	public boolean nombreLibre(int numUsuBuscar, String nomUsuBuscar)
	{
		Element eUsuarios = docUsuarios.getRootElement();
		List<Element>listaUsuarios = eUsuarios.getChildren();
		
		Iterator<Element>itListaUsuarios = listaUsuarios.iterator();		
		while(itListaUsuarios.hasNext())
		{
			Element eUsuario = itListaUsuarios.next();
			if(Integer.parseInt(eUsuario.getAttribute("id").getValue()) != numUsuBuscar)
			{
				if(eUsuario.getChild("nomUsuario").getText().equals(nomUsuBuscar))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Método que comprueba si el identificador de usuario recibido 
	 * como parámetro esta libre o no.
	 * @since 1.0
	 * @param id - Identificador que podría representar a un usuario
	 * @return Devuelve un valor lógico que representa si el identificador
	 * está siendo utilizado o no
	 */
	public boolean idLibre(int id)
	{
		Element eUsuarios = docUsuarios.getRootElement();
		List<Element>listaUsuarios = eUsuarios.getChildren();
		
		Iterator<Element>itListaUsuarios = listaUsuarios.iterator();		
		while(itListaUsuarios.hasNext())
		{
			Element eUsuario = itListaUsuarios.next();
			if(Integer.parseInt(eUsuario.getAttribute("id").getValue()) == id)
				return false;
		}
		return true;
	}
	
	/**
	 * Método que actualiza la información de un usuario en base a los
	 * valores recibidos como parámetro. Una vez finalizado, los cambios
	 * sobre el documento serán aplicados al XML de usuarios. Para esto
	 * hará uso del método {@link #grabar()}. 
	 * @since 1.0
	 * @param numUsu - Identificador del usario
	 * @param nomUsu - Nombre del usuario
	 * @param conUsu - Contraseña del usuario
	 * @param esAdmin - Valor lógico que representa la disponibilidad 
	 * de permisos de administrador del usuario
	 */
	public void guardarCambios(int numUsu, String nomUsu, String conUsu, boolean esAdmin)
	{
		Element eUsuarios = docUsuarios.getRootElement();
		List<Element>listaUsuarios = eUsuarios.getChildren();
		
		boolean usuEncontrado = false;
		Iterator<Element>itListaUsuarios = listaUsuarios.iterator();		
		while(itListaUsuarios.hasNext() && usuEncontrado == false)
		{
			Element eUsuario = itListaUsuarios.next();
			if (Integer.parseInt(eUsuario.getAttribute("id").getValue()) == numUsu)
			{
				eUsuario.getChild("nomUsuario").setText(nomUsu);
				eUsuario.getChild("conUsuario").setText(conUsu);
				if(esAdmin == true)
					eUsuario.getAttribute("esAdmin").setValue("S");
				else
					eUsuario.getAttribute("esAdmin").setValue("N");
				usuEncontrado = true;
			}
		}
		
		grabar();
	}
	
	/**
	 * Método que añade un usuario al documento en base a los valores
	 * recibidos como parámetro. Antes de la inserción válida si el 
	 * identificador recibido como parámetro está libre.
	 * Una vez finalizado, los cambios
	 * sobre el documento serán aplicados al XML de usuarios. Para esto
	 * hará uso del método {@link #grabar()}.
	 * @since 1.0
	 * @param numUsu - Identificador del usuario
	 * @param nomUsu - Nombre del usuario
	 * @param conUsu - Contraseña del usuario
	 * @param esAdmin - Valor lógico que representa la disponibilidad 
	 * de permisos de administrador del usuario
	 */
	public void aniadirUsuario(int numUsu, String nomUsu, String conUsu, boolean esAdmin)
	{
		Element eUsuarios = docUsuarios.getRootElement();
		Element eUsuario = new Element("usuario");
		eUsuario.setAttribute("id",String.valueOf(numUsu));
		if(esAdmin == true)
			eUsuario.setAttribute("esAdmin",String.valueOf("S"));
		else
			eUsuario.setAttribute("esAdmin",String.valueOf("N"));
			
		Element eNomUsuario = new Element("nomUsuario");
		eNomUsuario.setText(nomUsu);
		Element eConUsuario = new Element("conUsuario");
		eConUsuario.setText(conUsu);
			
		eUsuario.addContent(eNomUsuario);
		eUsuario.addContent(eConUsuario);
		eUsuarios.addContent(eUsuario);
			
		grabar();
	}
	
	/**
	 * Método que añade al documento XML de usuarios un report en base
	 * a los valores recibidos como parámetro.
	 * @since 1.0
	 * @param nomUsuario - Nombre de usuario que envía el report
	 * @param titulo - Título del report en cuestión
	 * @param texto - Descripción de la queja/report del usuario
	 * @return Devuelve un valor lógico que representa si la creación
	 * del report ha podido completarse correctamente
	 */
	public boolean crearReport(String nomUsuario,String titulo,String texto)
	{
		Element eUsuarios = docUsuarios.getRootElement();
		List<Element>listaUsuarios = eUsuarios.getChildren();
		Iterator<Element>itListaUsuarios = listaUsuarios.iterator();		
		while(itListaUsuarios.hasNext())
		{
			Element eUsuario = itListaUsuarios.next();
			if(eUsuario.getChild("nomUsuario").getValue().equals(nomUsuario))
			{
				Element eReports = eUsuario.getChild("reports");
				if(eReports == null)
				{					
					eReports = new Element("reports");
					eUsuario.addContent(eReports);
				}
				Element eReport = new Element("report");
				eReport.setAttribute("fecha", new Date().toString());
				Element eTitulo = new Element("titulo").setText(titulo);
				Element eDescripcion = new Element("texto").setText(texto);
				eReport.addContent(eTitulo);
				eReport.addContent(eDescripcion);
				eReports.addContent(eReport);
				
				grabar();
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Método que guarda en el archivo XML de deportistas toda la información
	 * encontrada en el documento de usuarios. Es llamada para aplicar cambios
	 * cada vez que ocurre una inserción o edición de los datos para mantener
	 * el XML actualizado.
	 * @since 1.0
	 */
	private void grabar()
	{
		try 
		{
			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			File f = new File(USER_XML);
			FileWriter fw = new FileWriter(f);
			salida.output(docUsuarios, fw);
			fw.close();
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
		}
	}
}