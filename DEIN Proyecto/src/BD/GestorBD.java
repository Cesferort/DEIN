package BD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import beans.Deportista;

/**
 * Esta clase se encargará de la gestión de la base de datos.
 * @author Cesferort
 * @since 1.0
 */
public class GestorBD 
{
	/**
	 * Conexión a la base de datos.
	 */
	private Connection con;
	
	/**
	 * Constructor que creará una conexión con la base de datos.
	 * Son controladas las excepciones que puedan ocurrir al 
	 * conectar a la base de datos.
	 * @since 1.0
	 */
	public GestorBD()
	{
		try 
		{			
			//Base de datos embebida SQLite
			/**/
			con = DriverManager.getConnection
				(
					"jdbc:sqlite:SQLite/olimpiadas.db"
				);
			
			
			//Base de datos en clase MySQL
			/*
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection
				(
					"jdbc:mysql://127.0.0.1:3306/olimpiadas" + "?autoReconnect=true&useSSL=false",
					"root", 
					"dm2"
				);
			*/
			
			//Base de datos en casa MySQL
			/*
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection
				(
					"jdbc:mysql://127.0.0.1:3306/olimpiadas" + "?autoReconnect=true&useSSL=false",
					"root", 
					"dm2dm2"
				);
			*/
			
			//Base de datos en casa MySQL
			/*
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection
				(
					"jdbc:oracle:thin:@localhost:1521:orcl",
					"system",
					"dm2"
				);
			*/
		} 
		catch (SQLException e) 
		{
			System.out.println("Conexión a Base de Datos fallida: " + e.getMessage());
		}
		catch (Exception e)
		{
			System.out.println("No se ha podido acceder a la base de datos: " + e.getMessage());
		}
	}
	
	/**
	 * Método que guarda en un array todos los deportistas dentro 
	 * de la tabla Deportista.
	 * Son controladas las posibles excepciones de acceso a datos.
	 * @see beans.Deportista
	 * @since 1.0
	 * @return Devuelve un array con los objetos deportista de la 
	 * base de datos.
	 */
	public Deportista[] conseguirDeportistas()
	{
		Deportista deportistas[] = null;
		try 
		{
			String sql = "SELECT COUNT(*) \"CANT\" FROM Deportista";
			java.sql.PreparedStatement st = con.prepareStatement(sql);
			ResultSet rsCantidadDep = st.executeQuery();
			rsCantidadDep.next();
			int cantidadDep = rsCantidadDep.getInt("CANT");
			deportistas = new Deportista[cantidadDep];
			rsCantidadDep.close();
			st.close();
			
			sql =  "SELECT ID_DEPORTISTA,NOMBRE,SEXO,PESO,ALTURA ";
			sql += "FROM Deportista";
			st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			for(int i = 0; rs.next(); i++)
			{				
				int codDep = rs.getInt("ID_DEPORTISTA");
				String nomDep = rs.getString("NOMBRE");
				String sexDep = rs.getString("SEXO");
				int pesDep = rs.getInt("PESO");
				int altDep = rs.getInt("ALTURA");
				
				Deportista depRecipiente = new Deportista(codDep,nomDep,sexDep,pesDep,altDep);
				deportistas[i] = depRecipiente;
			}
			rs.close();
			st.close();
			return deportistas;
		} 
		catch (SQLException e) 
		{
			System.out.println(e.getMessage());
		}
		return deportistas;
	}
	
	/**
	 * Método que añade un deportista a la base de datos según los 
	 * parámetros recibidos.
	 * Son controladas las posibles excepciones de acceso a datos.
	 * @since 1.0
	 * @param codDep - Identificador del deportista. Clave en la tabla
	 * @param nomDep - Nombre del deportista
	 * @param sexDep - Sexo del deportista [M/F]
	 * @param pesDep - Peso del deportista
	 * @param altDep - Altura del deportista
	 * @return Devuelve un valor lógico que muestra si ha ocurrido 
	 * alguna excepción en su ejecución
	 */
	public boolean aniadirDeportista(int codDep,String nomDep,String sexDep,int pesDep,int altDep)
	{
		try 
		{
			String sql = "INSERT INTO Deportista VALUES(?, ?, ?, ?, ?)";
			java.sql.PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, codDep);
			st.setString(2, nomDep);
			st.setString(3, sexDep);
			st.setInt(4, pesDep);
			st.setInt(5, altDep);
			
			st.executeUpdate();
			st.close();
			return true;
		}
		catch(SQLException e) 
		{
			return false;
		}
	}
	
	/**
	 * Método que comprueba si un código de deportista recibido como
	 * parámetro ha sido utilizado anteriormente para identificar a 
	 * otro deportista encontrado en la base de datos.
	 * Son controladas las posibles excepciones de acceso a datos.
	 * @since 1.0
	 * @param codDep - Identificador del deportista a comprobar
	 * @return Devuelve un valor lógico que muestra si el identificador
	 * ha sido utilizado anteriormente o no
	 */
	public boolean codDepLibre(int codDep)
	{
		try 
		{
			String	sql =  "SELECT COUNT(*) \"CANT\" ";
					sql += "FROM Deportista WHERE id_deportista = ?";
			java.sql.PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, codDep);
			ResultSet rs = st.executeQuery();
			rs.next();
			int cantidadDep = rs.getInt("CANT");

			rs.close();
			st.close();
			if(cantidadDep == 0)
				return true;
			else 
				return false;
		} 
		catch (SQLException e) 
		{
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	/**
	 * Método que actualiza en la base de datos la información del 
	 * deportista en base a los parámetros recibidos. 
	 * Son controladas las posibles excepciones de acceso a datos.
	 * @since 1.0
	 * @param codDep - Identificador del deportista. Clave en la tabla
	 * @param nomDep - Nombre del deportista
	 * @param sexDep - Sexo del deportista [M/F]
	 * @param pesDep - Peso del deportista
	 * @param altDep - Altura del deportista
	 */
	public void guardarCambios(int codDep,String nomDep,String sexDep,int pesDep,int altDep)
	{
		try 
		{
			String sql = "UPDATE Deportista SET "
					 + "NOMBRE	= ? ,"
					 + "SEXO	= ? ,"
					 + "PESO	= ? ,"
					 + "ALTURA	= ? "
					 + "WHERE ID_DEPORTISTA = ?";
			java.sql.PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, nomDep);
			st.setString(2, sexDep);
			st.setInt(3, pesDep);
			st.setInt(4, altDep);
			st.setInt(5, codDep);
			
			st.executeUpdate();
			st.close();
		}
		catch(SQLException e) 
		{
			System.out.println("Intentando guardar cambios Error SQL "+e.getMessage());
		}
	}
}