package cl.neokey.reporteventas.method;

public class UtilsReporte {
	public static String getNombreMes(int index) {
		String mes = "";
		switch (index) {
		case 0:
			mes = "ENERO";
			break;
		case 1:
			mes = "FEBRERO";
			break;
		case 2:
			mes = "MARZO";
			break;
		case 3:
			mes = "ABRIL";
			break;
		case 4:
			mes = "MAYO";
			break;
		case 5:
			mes = "JUNIO";
			break;
		case 6:
			mes = "JULIO";
			break;
		case 7:
			mes = "AGOSTO";
			break;
		case 8:
			mes = "SEPTIEMBRE";
			break;
		case 9:
			mes = "OCTUBRE";
			break;
		case 10:
			mes = "NOMBIEMBRE";
			break;
		case 11:
			mes = "DICIEMBRE";
			break;
		default:
			mes = "--";
			break;
		}
		
		return mes;
	}
}
