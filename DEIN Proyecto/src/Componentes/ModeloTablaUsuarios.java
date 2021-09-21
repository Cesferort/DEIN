package Componentes;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import beans.Usuario;

/**
 * Esta clase nos permitirá crear un modelo de tabla personalizado. Será
 * utilizado para crear una tabla en la que visualizaremos los usuarios 
 * de la aplicación.
 * @author Cesferort
 * @since 1.0
 */
public class ModeloTablaUsuarios extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;
	/**
	 * Nombre de las columnas de la tabla
	 */
	private String[] columnNames = 
        {
        	"ID",
        	"Nombre",	 
            "Contraseña", 
            "esAdmin"    
        };
	/**
	 * Contenedor de los objetos que almacenará la tabla
	 */
    private Object datos[][];
        
    /**
     * Constructor de un ModeloTablaUsuarios que recibirá una lista de usuarios
     * como parámetro. Guardará todos los usuarios en un contenedor gracias al
     * método {@link #setAllData(ArrayList)}.
     * @since 1.0
     * @param usuarios
     */
    public ModeloTablaUsuarios(ArrayList<Usuario>usuarios)
    {
        this.setAllData(usuarios);
        fireTableDataChanged();
    }
       
    /**
     * Método que guarda en un contenedor todos los usuarios recibidos como
     * parámetro. Será llamado desde el constructor.
     * @since 1.0
     * @param usuarios
     */
    public void setAllData(ArrayList<Usuario>usuarios)
    {
        int cont = 0;
        datos = new Object[usuarios.size()][];
        	
        for(Usuario e: usuarios)
        {
        	datos[cont]= new Object[]{e.getNumUsu(),e.getNomUsu(),e.getConUsu(),e.isEsAdmin()};
        	cont++;
        }
        fireTableDataChanged();
    }    							
    
    /**
     * Método que devuelve la cantidad de columnas de la tabla.
     * @since 1.0
     * @return Devuelve la cantidad de columnas de la tabla
     */
    public int getColumnCount() 
    {
        return columnNames.length;
    }
    
    /**
     * Método que devuelve la cantidad de filas de la tabla.
     * @since 1.0
     * @return Devuelve la cantidad de filas de la tabla
     */
    public int getRowCount() 
    {
        return datos.length;
    }
    
    /**
     * Método que devuelve el nombre de la columna cuyo número
     * será recibido como parámetro.
     * @since 1.0
     * @return Devuelve el nombre de la columna
     */
    public String getColumnName(int col) 
    {
        return columnNames[col];
    }
    
    /**
     * Método que devuelve el objeto depositado en la fila y columna
     * pasadas como parámetro.
     * @since 1.0
     * @return Devuelve el objeto en la posición determinada
     */
    public Object getValueAt(int row, int col) 
    {
        return datos[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Class getColumnClass(int c) 
    {
        return getValueAt(0, c).getClass();
    }
}