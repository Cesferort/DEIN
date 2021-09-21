package XML;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import BD.GestorBD;
import beans.Deportista;

/**
 * Este Gestor de XML nos permitirá gestionar los deportistas.
 * @author Cesferort
 * @since 1.0
 */
public class GestorXML_Deportistas 
{	
	/**
	 * Documento en el que cargaremos todos los deportistas del XML.
	 */
	private Document docDeportistas;
	/**
	 * Documento en el que cargaremos todos los deportistas que deseamos 
	 * importar del XML.
	 */
	private Document docImpDep;
	/**
	 * Gestor de la base de datos.
	 */
	private GestorBD gestorBD;
	
	/**
	 * Constructor que crea una instancia del gestor de la base de datos y 
	 * llama al método construirDocUsuarios.
	 * @since 1.0
	 */
	public GestorXML_Deportistas()
	{
		gestorBD = new GestorBD();
	}
	
	/**
	 * Método que construye un documento de deportistas en base a la dirección de 
	 * un archivo XML recibida como parámetro. Posteriormente, llamará al método 
	 * {@link #importarDeportistas()} para la inserción de todos los deportistas 
	 * cargados.
	 * @since 1.0
	 * @see GestorXML_Deportistas#importarDeportistas()
	 * @param absPath - dirección del archivo XML a utilizar
	 * @return Valor textual que guardará null en caso de no encontrar errores o
	 * una descripción detallada de todos los errores ocurridos durante la 
	 * ejecución del método
	 */
	public String construirDocImpDep(String absPath)
	{		
		String resultadoErrores = null;
		try
		{
			docImpDep = new Document();
			SAXBuilder builder = new SAXBuilder();
			File f = new File(absPath);
			FileInputStream fis = new FileInputStream(f);
			docImpDep = builder.build(fis);
			fis.close();
			
			importarDeportistas();
		} 
		catch (FileNotFoundException e) 
		{
			resultadoErrores = "Archivo no encontrado";
		} 
		catch (JDOMException e) 
		{
			resultadoErrores = "Ha ocurrido un error en la lectura y escritura de datos. Vuelve a intentarlo";
		} 
		catch (IOException e) 
		{
			resultadoErrores = "Ha ocurrido un error en la lectura y escritura de datos. Vuelve a intentarlo";
		}
		
		return resultadoErrores;
	}
	
	/**
	 * Método que añade a la base de datos todos los deportistas encontrados en el
	 * documento de deportistas. Será llamado por el método 
	 * {@link #construirDocImpDep(String)} 
	 * y hace uso del gestor de la base de datos para las inserciones gracias al método 
	 * {@link GestorBD#aniadirDeportista(int, String, String, int, int)}.
	 * @since 1.0
	 * @see GestorXML_Deportistas#construirDocImpDep(String absPath)
	 */
	private void importarDeportistas()
	{
		Element eDeportistas = docImpDep.getRootElement();
		List<Element>listaDeportistas = eDeportistas.getChildren();
		Iterator<Element>itListaDeportistas = listaDeportistas.iterator();
		while(itListaDeportistas.hasNext())
		{
			Element eDeportista = itListaDeportistas.next();
			int codDep = Integer.parseInt(eDeportista.getAttribute("id").getValue());
			String nomDep = eDeportista.getChild("nomDep").getText();
			String sexDep = eDeportista.getChild("sexDep").getText();
			int pesDep = Integer.parseInt(eDeportista.getChild("pesDep").getText());
			int altDep = Integer.parseInt(eDeportista.getChild("altDep").getText());
			
			gestorBD.aniadirDeportista(codDep, nomDep, sexDep, pesDep, altDep);
		}
	}	
	
	/**
	 * Método que construye un documento de deportistas haciendo uso del método
	 * {@link #construirDocDeportistas()} 
	 * y guarda su contenido en un fichero XML, cuya 
	 * direccién será recibida como parámetro.
	 * @since 1.0
	 * @see GestorXML_Deportistas#construirDocDeportistas()
	 * @param nomFich - dirección del archivo XML a crear
	 */
	public void exportarDeportistas(String nomFich)
	{
		try
		{
			construirDocDeportistas();
			
			XMLOutputter salida = new XMLOutputter(Format.getPrettyFormat());
			File f = new File(nomFich);
			FileWriter fw = new FileWriter(f);			
			salida.output(docDeportistas, fw);
			fw.close();
		}
		catch(IOException e) 
    	{
    		System.out.println("IOException");
    	}
	}
	
	/**
	 * Método que construye un documento de deportistas con todos los encontrados
	 * dentro de la base de datos, para esto se ayudará del gestor de la base de
	 * datos. Será llamado por el método exportarDeportistas.
	 * @since 1.0
	 * @see GestorXML_Deportistas#exportarDeportistas(String nomFich)
	 */
	private void construirDocDeportistas()
	{
		docDeportistas = new Document();
		Element root = new Element("deportistas");
		docDeportistas.setRootElement(root);
		Deportista deportistas[] = gestorBD.conseguirDeportistas();
		
		for(int i = 0; i < deportistas.length;i++)
		{
			Element eDeportista = new Element("deportista");
			root.addContent(eDeportista);
			
			Deportista depRecipiente = deportistas[i];
			int codDep = depRecipiente.getCodDep();
			eDeportista.setAttribute("id", String.valueOf(codDep));
			
			String nomDep = depRecipiente.getNomDep();
			String sexDep = depRecipiente.getSexDep();
			int pesDep = depRecipiente.getPesDep();
			int altDep = depRecipiente.getAltDep();
			eDeportista.addContent(new Element("nomDep").addContent(nomDep));
			eDeportista.addContent(new Element("sexDep").addContent(sexDep));
			eDeportista.addContent(new Element("pesDep").addContent(String.valueOf(pesDep)));
			eDeportista.addContent(new Element("altDep").addContent(String.valueOf(altDep)));		
		}
	}
}