package cl.neokey.reporteventas;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import cl.neokey.reporteventas.method.GenerarInformeReporteVentas;

public class Main 
{
    public static void main( String[] args ) throws JsonGenerationException, JsonMappingException, IOException
    {	GenerarInformeReporteVentas info = new GenerarInformeReporteVentas();
    	String mails = args[0];
    	System.out.println(mails);
    	info.createMail(mails);
    }
}
