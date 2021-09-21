package LP;
import java.awt.Dimension;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.JFrame;

/**
 * @author javier.cerro
 * Clase que hereda de un formulario y que va a tener la propiedad de tener ayuda asociada.
 * Para crear esta ayuda y mostrarla, tiene un método que carga la informaci�n correspondiente.
 */
public abstract class FormHelp extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private final String RUTA_HELP_SET = "/Help/help_set.hs";
	private HelpSet objHelpSet;
	private HelpBroker objHelpBroker;
	
	public FormHelp() 
	{
		super();
		
		try 
		{
			this.loadHelp();
		} 
		catch (MalformedURLException | HelpSetException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		this.setSize(new Dimension(300,400));
	}
	
	public HelpSet getObjHelpSet() 
	{
		return objHelpSet;
	}

	public HelpBroker getObjHelpBroker() 
	{
		return objHelpBroker;
	}
	
	/**
	 * @throws MalformedURLException En caso de no encontrar los ficheros de ayuda
	 * @throws HelpSetException En caso de no crear bien el objeto HelpSet. 
	 */
	public void loadHelp () throws MalformedURLException, HelpSetException
	{
		String ruta = System.getProperty("user.dir") + RUTA_HELP_SET;
		// Carga el fichero de ayuda
		File fichero = new File(ruta);
		URL hsURL = fichero.toURI().toURL();
		// Crea el HelpSet y el HelpBroker
		objHelpSet = new HelpSet(getClass().getClassLoader(), hsURL);
		objHelpBroker = objHelpSet.createHelpBroker();
		
		//objHelpBroker.enableHelpKey(this.getRootPane(), this.getMapId(), objHelpSet);
		objHelpBroker.enableHelpKey(this.getContentPane(), this.getMapId(), objHelpSet);
	}
	
	/**
	 * Metodo que devuelve el nombre de la clase. Se trata de poder enlazar las entradas del fichero de ayuda
	 * map_file.jhm
	 * @return el nombre de la clase.
	 */
	public String getMapId()
	{
		String retorno="";
		
		retorno =this.getClassName(this);
		//System.out.println(retorno);
		//retorno="clsVentanaPrincipal";
		return retorno;
	}
	
	/**
	 * Metodo que obliga a las clases hijas a establecer la ayuda a los elementos correspondientes de la interfaz grafica.
	 */
	public abstract void setHelp();
	
    private String getClassName(Object o) 
    {
    	String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }
}