package cl.neokey.reporteventas.method;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import cl.neokey.reporteventas.model.InformeReporteVentas;
import cl.neokey.reporteventas.method.UtilsReporte;;

public class GenerarInformeReporteVentas {
	public static String URL_LOGIN="http://localhost:8080/MundoCredito/usuario/login/";
	public static String URL_RESOURCE="http://localhost:8080/MundoCredito/informes/InformeReporteVentas";
	public String NAME_FILE = "ReporteVentas.xsl";
	
	public String TOKEN;
	public static String MAIL_PASS = "";
	
	public Integer INDEX_MES = 0;
	public Integer ANIO = 0;

	public void createMail(String mails) {
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		List<InformeReporteVentas> listDataInforme = new ArrayList<InformeReporteVentas>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar calendar = Calendar.getInstance();
		Date fechaActual = calendar.getTime();
		INDEX_MES = calendar.get(Calendar.MONTH);
		ANIO = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date fechaInicioMes = calendar.getTime();
		String fechaDesde = "";
		String fechaHasta = "";
		
		try {
			fechaDesde = sdf.format(fechaInicioMes);
			fechaHasta = sdf.format(fechaActual);
			System.out.println("Logging-in...");
			boolean login = loginUserExterno();
			if(login) {
				System.out.println("Done!");
				System.out.println("Fetching InformeReporteVentas...");
				System.out.println(URL_RESOURCE+"/"+fechaDesde+"/"+fechaHasta+"/4/0/?access_token="+TOKEN);
				ResponseEntity<String> response = restTemplate.getForEntity(URL_RESOURCE+"/"+fechaDesde+"/"+fechaHasta+"/4/0/?access_token="+TOKEN, String.class);
				System.out.println("StatusCode : " + response.getStatusCode());
				if(Integer.parseInt(response.getStatusCode().toString())==200) {
					listDataInforme = mapper.readValue(response.getBody(), new TypeReference<List<InformeReporteVentas>>(){});
					System.out.println("Sending Mails...");
					boolean isSend = generateMail(listDataInforme,mails);
					if(isSend==true) {
						System.out.println("OK : Mail has been sent successfully!");
					} else {
						System.out.println("Error : Could not been sent mail");
					}
				} else {
					System.out.println("Terminate : Could not get InformeReporteVentas by the actual URL");
				}
			} else {
				System.out.println("Terminate : Could not Login in MundoNet");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean loginUserExterno() {
		boolean res = false;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		String JSONString = "{\n" + "	\"c_id\": \"serviciorest@mundocredito.cl\",\n" + "	\"a_Clave\": \"123\"\n"+ "}";

		JSONParser parser = new JSONParser();
		JSONObject json = new JSONObject();

		try {
			HttpEntity<String> request = new HttpEntity<String>(JSONString, headers);
			ResponseEntity<String> responseUsuarioLogin = restTemplate.exchange(URL_LOGIN, HttpMethod.POST, request, String.class);
			json = (JSONObject) parser.parse(responseUsuarioLogin.getBody());
			System.out.println("Access Token : "+((JSONObject) json.get("token")).get("access_token"));
			res = true;
			TOKEN = ((JSONObject) json.get("token")).get("access_token").toString();}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return res;
	}
	
	public boolean generateMail(List<InformeReporteVentas> listDataInforme, String mails) {
		
		Properties prop = new Properties();
        prop.put("mail.smtp.host", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        
//        String[] arrayMails = mails.split(",");
		
		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication("esteban.vergara@nw.cl", "DesarrolladorNeokey");
		    }
		});
		
		int n_solicitud_otros = 0;
		int n_aprobadas_otros = 0;
		BigDecimal n_porc_aprobadas_otros = BigDecimal.ZERO;
		int n_cursadas_otros = 0;
		BigDecimal n_porc_cursadas_otros = BigDecimal.ZERO;
		
		InformeReporteVentas totales = null;
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mails));
			message.setSubject("PRUEBA : REPORTE DE VENTAS : "+ UtilsReporte.getNombreMes(INDEX_MES)+" "+ANIO);
			 
			String msg = "SOLO SON PRUEBAS";
			msg += "<table>\n" + 
					"\n" + 
					"<thead style=\"background-color: #9EC7F7\">\n" + 
					"\n" + 
					"<tr>\n" + 
					"\n" + 
					"<th style=\"border:1px solid black\" colspan=6>Acumulado "+ UtilsReporte.getNombreMes(INDEX_MES)+" "+ANIO+"</th>\n" + 
					"\n" + 
					"</tr>\n" + 
					"\n" + 
					"</thead>\n" + 
					"\n" + 
					"<thead style=\"background-color: #9EC7F7\">\n" + 
					"\n" + 
					"<tr>\n" + 
					"\n" + 
					"<th style=\"border:1px solid black\">Agrupado Por</th>\n" + 
					"\n" + 
					"<th style=\"border:1px solid black\">Solicitudes Evaluadas</th>\n" + 
					"\n" + 
					"<th style=\"border:1px solid black\">Aprobado</th>\n" + 
					"\n" + 
					"<th style=\"border:1px solid black\">Aprobado %</th>\n" + 
					"\n" + 
					"<th style=\"border:1px solid black\">Cursado</th>\n" + 
					"\n" + 
					"<th style=\"border:1px solid black\">Cursado %</th>\n" + 
					"\n" + 
					"</tr>\n" + 
					"\n" + 
					"</thead>\n" + 
					"\n" + 
					"<tbody>\n" + 
					"\n"; 
					
			for (int i = 0; i < listDataInforme.size(); i++) {
				if(i < 25) {
					msg +=	"<tr>\n" + 
							"\n"+
							"<td style=\"border:1px solid black\">"+listDataInforme.get(i).getA_dealer()+"</td>\n" + 
							"\n" + 
							"<td style=\"border:1px solid black\">"+listDataInforme.get(i).getN_solicitud()+"</td>\n" + 
							"\n" + 
							"<td style=\"border:1px solid black\">"+listDataInforme.get(i).getN_aprobadas()+"</td>\n" + 
							"\n" + 
							"<td style=\"border:1px solid black\">"+((listDataInforme.get(i).getN_porc_aprobadas()!=null)?listDataInforme.get(i).getN_porc_aprobadas()+"%":"")+"</td>\n" + 
							"\n" + 
							"<td style=\"border:1px solid black\">"+listDataInforme.get(i).getN_cursadas()+"</td>\n" + 
							"\n" + 
							"<td style=\"border:1px solid black\">"+((listDataInforme.get(i).getN_porc_cursadas()!=null)?listDataInforme.get(i).getN_porc_cursadas()+"%":"")+"</td>\n" + 
							"\n" + 
							"</tr>\n" + 
							"\n";  
				} else {
					if(!listDataInforme.get(i).getA_dealer().equals("TOTAL")) {
						n_solicitud_otros = n_solicitud_otros + listDataInforme.get(i).getN_solicitud();
						n_aprobadas_otros = n_aprobadas_otros + listDataInforme.get(i).getN_aprobadas();
						n_cursadas_otros = n_cursadas_otros + listDataInforme.get(i).getN_cursadas();
					} else {
						totales = listDataInforme.get(i);
					}
				}
				
			}
			n_porc_aprobadas_otros = (n_solicitud_otros!=0)?BigDecimal.valueOf((n_aprobadas_otros*100)/n_solicitud_otros).setScale(0, RoundingMode.HALF_UP):null;
			n_porc_cursadas_otros = (n_aprobadas_otros!=0)?BigDecimal.valueOf((n_cursadas_otros*100)/n_aprobadas_otros).setScale(0, RoundingMode.HALF_UP):null;
			
			msg += "<tr style=\"color: red; \">\n" + 
					"\n" + 
					"<td style=\"border:1px solid black\"><b>Otros Dealer</b></td>\n" + 
					"\n" + 
					"<td style=\"border:1px solid black\">"+n_solicitud_otros+"</td>\n" + 
					"\n" + 
					"<td style=\"border:1px solid black\">"+n_aprobadas_otros+"</td>\n" + 
					"\n" + 
					"<td style=\"border:1px solid black\">";
					msg += (n_porc_aprobadas_otros!=BigDecimal.ZERO)?n_porc_aprobadas_otros+"%":"";
			msg +="</td>\n" + 
					"\n" + 
					"<td style=\"border:1px solid black\">"+n_cursadas_otros+"</td>\n" + 
					"\n" + 
					"<td style=\"border:1px solid black\">";
					msg += (n_porc_cursadas_otros!=BigDecimal.ZERO)?n_porc_cursadas_otros+"%":"";
			msg += "</td>\n" + 
					"\n" + 
					"</tr>\n" + 
					"\n" + 
					"<tr style=\"background-color: #9EC7F7\">\n" + 
					"\n" + 
					"<td style=\"border:1px solid black\"><b>TOTAL</b></td>\n" + 
					"\n" + 
					"<td style=\"border:1px solid black\">"+totales.getN_solicitud()+"</td>\n" + 
					"\n" + 
					"<td style=\"border:1px solid black\">"+totales.getN_aprobadas()+"</td>\n" + 
					"\n" + 
					"<td style=\"border:1px solid black\">";
					msg += (totales.getN_porc_aprobadas()!=BigDecimal.ZERO)?totales.getN_porc_aprobadas()+"%":"";
			msg +="</td>\n" + 
					"\n" + 
					"<td style=\"border:1px solid black\">"+totales.getN_cursadas()+"</td>\n" + 
					"\n" + 
					"<td style=\"border:1px solid black\">";
					msg += (totales.getN_porc_aprobadas()!=BigDecimal.ZERO)?totales.getN_porc_cursadas()+"%":"";
			msg +="</td>\n" + 
					"\n" + 
					"</tr>\n" + 
					"\n" + 
					"</tbody>\n" + 
					"\n" + 
					"<tfoot style=\"background-color: #EDDA0C\">\n" + 
					"\n" + 
					"<tr >\n" + 
					"\n" + 
					"<td style=\"border:1px solid black\">AL ??/??</td>\n" + 
					"\n" + 
					"<td style=\"border:1px solid black\"></td>\n" + 
					"\n" + 
					"<td style=\"border:1px solid black\">%</td>\n" + 
					"\n" + 
					"<td style=\"border:1px solid black\"></td>\n" + 
					"\n" + 
					"<td style=\"border:1px solid black\">%</td>\n" + 
					"\n" + 
					"<td style=\"border:1px solid black\"></td>\n" + 
					"\n" + 
					"</tr>\n" + 
					"\n" + 
					"</tfoot>\n" + 
					"\n" + 
					"</table>";
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msg, "text/html");
			 
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			 
			message.setContent(multipart);
			 
			Transport.send(message);
			
			return true;
		} catch (Exception e) {
			System.err.println(e);
		}
		return false;
	}
}
