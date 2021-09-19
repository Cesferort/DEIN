
public class Pruebas 
{
	/**
	 * Descripción inútil
	 * {@link #noHagoNada(String str)}: Método encargado de dibujar el InternalDeportista
	 * @since 1.0
	 * @author Cesferort
	 */
	private static void yoTampoco()
	{
		System.out.println("Yo tampoco");
		Pruebas.noHagoNada("str");
	}
	
	/**
	 * Descripción inútil
	 * @since 1.0
	 * @param str	-	Parámetro inútil
	 */
	private static void noHagoNada(String str)
	{
		System.out.println("No hago nada");
	}
	
	public static void main(String[] args) 
	{
		Pruebas.noHagoNada("str");
		Pruebas.yoTampoco();
	}
}