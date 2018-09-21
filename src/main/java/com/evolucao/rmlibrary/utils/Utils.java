package com.evolucao.rmlibrary.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;

import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.evolucao.rmlibrary.database.Database;
import com.evolucao.rmlibrary.database.KeyValue;
import com.evolucao.rmlibrary.database.enumerators.MessageWindowType;
import com.evolucao.rmlibrary.window.RmFormButtonBase;
import com.evolucao.rmlibrary.window.RmFormWindow;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent;
import com.evolucao.rmlibrary.window.event.RmFormButtonClickEvent.RmFormButtonClickEventListener;

public class Utils {
	
	public static Cookie getCookieByName(String name) { 
		// Fetch all cookies from the request 
		Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();

		if (cookies!=null) {
			// Iterate to find cookie by its name 
			for (Cookie cookie : cookies) { 
				if (name.equals(cookie.getName())) { 
					return cookie; 
				} 
			}
		}

		return null; 
	}
	
	public static void saveCookie(String cookieName, String content) {
		saveCookie(cookieName, content, 2592000);
	}
	
	public static void saveCookie(String cookieName, String content, Integer maxAgeSeconds) {
		Cookie cookie = new Cookie(cookieName, content);
		cookie.setMaxAge(maxAgeSeconds);
		cookie.setPath("/");
		VaadinService.getCurrentResponse().addCookie(cookie);
	}
	
	public static String getCookieString(String cookieName) {
		String retorno = "";
		Cookie cookie = getCookieByName(cookieName);
		if (cookie!=null) {
			retorno = cookie.getValue();
		}
		
		return retorno;
	}
	
	public static String getCookieString(String cookieName, String defaultValue) {
		String retorno = defaultValue;
		String value = getCookieString(cookieName);
		if (!value.isEmpty()) {
			retorno = value;
		}
		
		return retorno;
	}
	
	public static String strZero(Integer value, Integer lenght) {
		String conteudo = String.format("%0" + lenght + "d", value);
		return conteudo;
	}
	
	public static Double roundDouble(Double valor, int decimals) {
        BigDecimal bigDecimal = new BigDecimal(valor);
        BigDecimal roundedWithScale = bigDecimal.setScale(decimals, BigDecimal.ROUND_HALF_UP);
        double resultado = roundedWithScale.doubleValue();
        return resultado;
	}
	
	private static int calcularDigito(String str, int[] peso) {
		int soma = 0;
		for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
			digito = Integer.parseInt(str.substring(indice,indice+1));
			soma += digito*peso[peso.length-str.length()+indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	public static boolean isValidCPF(String cpf) {
		cpf = cpf.replace(".", "");
		cpf = cpf.replace("-", "");
		cpf = cpf.replace("_", "");
		
		int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
		if ((cpf==null) || (cpf.length()!=11)) return false;

		Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
		Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
		return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
	}

	public static boolean isValidCNPJ(String cnpj) {
		cnpj = cnpj.replace(".", "");
		cnpj = cnpj.replace("-", "");
		cnpj = cnpj.replace("_", "");
		cnpj = cnpj.replace("/", "");
		
		int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};	
		if ((cnpj==null)||(cnpj.length()!=14)) return false;

		Integer digito1 = calcularDigito(cnpj.substring(0,12), pesoCNPJ);
		Integer digito2 = calcularDigito(cnpj.substring(0,12) + digito1, pesoCNPJ);
		return cnpj.equals(cnpj.substring(0,12) + digito1.toString() + digito2.toString());
	}
	
	public static void addJavascripts(boolean async, String location) {
		StringBuilder scriptString = new StringBuilder();
		scriptString.append("var script = document.createElement('script');");
		scriptString.append("script.type = 'text/javascript';");
		scriptString.append(String.format("script.src='%s';", location));
		scriptString.append(String.format("%s;", async ? "script.setAttribute('async', '');" : ""));
		scriptString.append("document.getElementsByTagName('head')[0].appendChild(script);");
		synchronized (scriptString) {
			Page.getCurrent().getJavaScript().execute(scriptString.toString());
		}
		scriptString = null;
	}
	
	public static int getDiaSemana(Date data) {
		int retorno = 1;  
		Calendar c = new GregorianCalendar();
		 c.setTime(data) ;
		 String nome = "";
		 int dia = c.get(c.DAY_OF_WEEK);
		 switch(dia){
		 case Calendar.SUNDAY: 
			 retorno=1;
			 break;
		 case Calendar.MONDAY: 
			 retorno = 2;
			 break;
		 case Calendar.TUESDAY: 
			 retorno=3;
			 break;
		 case Calendar.WEDNESDAY: 
			 retorno=4;
			 break;
		 case Calendar.THURSDAY: 
			 retorno=5;
			 break;
		 case Calendar.FRIDAY: 
			 retorno=6;
			 break;
		 case Calendar.SATURDAY: 
			 retorno=7;
			 break;
		 }
		 
		 return retorno;
	}
	
	public static String getNomeDiaSemana(Date data) {
		Calendar c = new GregorianCalendar();
		c.setTime(data) ;
		String nome = "";
		int dia = c.get(c.DAY_OF_WEEK);
		switch(dia){
		case Calendar.SUNDAY: 
			nome = "Domingo";
			break;
		case Calendar.MONDAY: 
			nome = "Segunda";
			break;
		case Calendar.TUESDAY: 
			nome = "Terça";
			break;
		case Calendar.WEDNESDAY: 
			nome = "Quarta";
			break;
		case Calendar.THURSDAY: 
			nome = "Quinta";
			break;
		case Calendar.FRIDAY: 
			nome = "Sexta";
			break;
		case Calendar.SATURDAY: 
			nome = "sábado";
			break;
		}
		return nome;
	}
	
	public static String encodeBase64URL(String conteudo) {
		String retorno = null;
		try {
			String encodedString = Base64.getUrlEncoder().encodeToString(conteudo.getBytes("utf-8"));
			retorno = encodedString;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return retorno;
	}
	
	public static String decodeBase64URL(String conteudo) {
		String retorno = null;
		try {
	        byte[] decodedBytes = Base64.getDecoder().decode(conteudo);
	        String decodedString = new String(decodedBytes, "utf-8");
	        retorno = decodedString;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return retorno;
	}
	
	/**
	 * Get a diff between two dates
	 * @param date1 the oldest date
	 * @param date2 the newest date
	 * @param timeUnit the unit in which you want the diff
	 * @return the diff value, in the provided unit
	 */
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	

	/*
	 String frase = "Quero gerar códigos hash desta mensagem.";
	  System.out.println(stringHexa(gerarHash(frase, "MD5")));
	  System.out.println(stringHexa(gerarHash(frase, "SHA-1")));
	  System.out.println(stringHexa(gerarHash(frase, "SHA-256")));
	  */
	public static byte[] gerarHash(String frase, String algoritmo) {
		try {
			MessageDigest md = MessageDigest.getInstance(algoritmo);
			md.update(frase.getBytes());
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	
	private static String stringHexa(byte[] bytes) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
			int parteBaixa = bytes[i] & 0xf;
			if (parteAlta == 0) s.append('0');
			s.append(Integer.toHexString(parteAlta | parteBaixa));
		}
		return s.toString();
	}
	
	public static String md5(String conteudo) {
		return stringHexa(gerarHash(conteudo, "MD5"));
	}
	
	public static boolean isInteger(String conteudo) {
		boolean retorno = false;
		try {
			Integer intContent = Integer.parseInt(conteudo);
			retorno = true;
		}
		catch (Exception e) {
			
		}
		return retorno;
	}
	
	public static boolean isDouble(String conteudo) {
		boolean retorno = false;
		try {
			Double doubleContent = Double.parseDouble(conteudo);
			retorno = true;
		}
		catch (Exception e) {
			
		}
		return retorno;
	}
	
	public static boolean isFloat(String conteudo) {
		boolean retorno = false;
		try {
			Float floatContent = Float.parseFloat(conteudo);
			retorno = true;
		}
		catch (Exception e) {
			
		}
		return retorno;
	}
	
	public static boolean isDate(String conteudo) {
		boolean retorno = false;
		try {
			SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
			Date data = simpleDate.parse(conteudo);
			retorno = true;
		}
		catch (Exception e) {
			
		}
		return retorno;
	}
	
	public static void ShowMessageWindow(String title, String message, int width, int height, MessageWindowType messageWindowType) {
		RmFormWindow formWindow = new RmFormWindow();
		formWindow.setTitle(title);
		formWindow.setWidth(width+"px");
		formWindow.setHeight(height+"px");
		
		formWindow.getBody().addStyleName("flex-direction-row");

		CssLayout body = new CssLayout();
		body.addStyleName("flex-vertical-center");
		formWindow.getBody().addComponent(body);
		{
			CssLayout div = new CssLayout();
			div.addStyleName("flex-direction-row");
			body.addComponent(div);
			{
				CssLayout divImg = new CssLayout();
				divImg.addStyleName("message-icon");
				div.addComponent(divImg);
				{
					Image img = new Image(null, new ThemeResource("imagens/library/icon-error.png"));
					divImg.addComponent(img);
				}
				
				Label lblMensagem = new Label(message);
				lblMensagem.addStyleName("message-label");
				div.addComponent(lblMensagem);
			}
		}

		RmFormButtonBase button = formWindow.addMessageOkButton();
		button.addRmFormButtonClickEventListener(new RmFormButtonClickEventListener() {
			@Override
			public void onRmFormButtonClick(RmFormButtonClickEvent event) {
				event.getWindow().close();
			}
		});
		formWindow.show();
		
	}
	
	public static void showMessageWindow(String title, String message) {
	}
	
	public static KeyValue getKeyValueItem(String key, List<KeyValue> keyValueList) {
		KeyValue retorno = null;
		for (KeyValue keyValue: keyValueList) {
			if (keyValue.getKey().equalsIgnoreCase(key)) {
				retorno = keyValue;
				break;
			}
		}
		return retorno;
	}
	
	public static String getWorkAreaWidth() {
		String retorno = "";
		Integer screenWidth = UI.getCurrent().getPage().getBrowserWindowWidth();
		
		if (screenWidth<1200) {
			retorno = "100%";
		}
		
		if (screenWidth>=1200) {
			retorno = "1170px";
		}
		
		return retorno;
	}
	
	public static Integer timeToInt(String timeString) {
		Integer retorno = 0;
		Integer hora = Integer.parseInt(timeString.substring(0,timeString.indexOf(":")));
		Integer minutos = Integer.parseInt(timeString.substring(timeString.indexOf(":")+1, timeString.length()));
		retorno = (hora*60)+minutos;
		
		return retorno;
	}
	
	public static String intToTime(Integer timeInteger) {
		String retorno = "";
		Integer hora = (int) (timeInteger/60);
		Integer minutos = timeInteger - (hora*60);
		retorno = Utils.strZero(hora, 2) + ":" + Utils.strZero(minutos, 2);
		
		return retorno;
	}
	
	/**
	 * Pega somente data sem time
	 * @param date
	 * @return data sem time
	 */
	public static Date getJustDate(Date date) {
		Date retorno = null;
		if (date!=null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			try {
				retorno = dateFormat.parse(dateFormat.format(date));
			}
			catch (Exception e) {
			}
		}
		return retorno;
	}
	
	public static Database createDatabaseConnection() {
		Database database = null;
		
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		String server = null;
		String databaseName = null;
		String userName = null;
		String password = null;
		
		System.out.println(basepath);
		
		try {
		     BufferedReader buffRead = null;
		     buffRead = new BufferedReader(new FileReader(basepath + "/config/database.conf"));
		     String linha = "";
		     while (true) {
		         if (linha == null) {
		             break;
		          }
		          linha = buffRead.readLine();
		          
		          if (linha.contains("server")) {
		        	  server = linha.substring(linha.indexOf("=")+1).trim();
		          }
		          else if (linha.contains("databaseName")) {
		        	  databaseName = linha.substring(linha.indexOf("=")+1).trim();
		          }
		          else if (linha.contains("userName")) {
		        	  userName = linha.substring(linha.indexOf("=")+1).trim();
		          }
		          else if (linha.contains("password")) {
		        	  password = linha.substring(linha.indexOf("=")+1).trim();
		          }
		          
		          System.out.println(linha);
		     }
		     buffRead.close();
		}
		catch (Exception e) {
		    System.out.println(e.getMessage());
		}		
		System.out.println(basepath);		
		
		//database = new Database("127.0.0.1", "prosaude_aparecida_teste", "usuario", "rataplan");
		database = new Database(server, databaseName, userName, password);
		
		return database;
	}
	
	/**
	 * dd/MM/yyyy HH:mm:ss
	 * @param data
	 * @param format
	 * @return
	 */
	public static String getSimpleDateFormat(Date data, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		String retorno = simpleDateFormat.format(data);
		return retorno;
	}
	
	public static String simpleDateFormat(Date data) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String retorno = simpleDateFormat.format(data);
		return retorno;
	}
	
	public static String simpleDateTimeFormat(Date data) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String retorno = simpleDateFormat.format(data);
		return retorno;
	}
	
	public static String replaceAll(String content, String oldString, String newString) {
		String conteudo = content;
		String novoConteudo = "";
		while (conteudo.indexOf(oldString)>=0) {
			novoConteudo = conteudo.substring(0, conteudo.indexOf(oldString)) + newString;
			conteudo = conteudo.substring(conteudo.indexOf(oldString)+oldString.length());
		}
		// Adiciona o que sobrou em conteudo
		novoConteudo += conteudo;

		return novoConteudo;
	}
	
	public static Date getFullDateMSSQL(ResultSet resultSet, String fieldName) {
		Date retorno = null;
		try {
			
			if (resultSet.getDate(fieldName)!=null) {
				Time time = resultSet.getTime(fieldName);
				Date date = resultSet.getDate(fieldName);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String fullDate = date.toString() + " " + time.toString();
				retorno = dateFormat.parse(fullDate);
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return retorno;
	}
	
	
	public static Date somaDias(Date data, Integer numeroDias) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		calendar.add(Calendar.DAY_OF_MONTH, numeroDias);
		Date retorno  = calendar.getTime();
		
		return retorno;
	}
	
	public static Date addDate(Date data, Integer numeroDias) {
		return somaDias(data, numeroDias);
	}
	
	public static String formatDouble(Double value) {
		String retorno = null;
		NumberFormat numberFormat = new DecimalFormat("#,##0.00");
		retorno = numberFormat.format(value);
		
		retorno.replaceAll(",", "a");
		retorno.replaceAll(".", ",");
		retorno.replaceAll("a", ".");
		
		return retorno;
	}
	
	public static String formatInteger(Integer value) {
		String retorno = null;
		NumberFormat numberFormat = new DecimalFormat("#,##0");
		retorno = numberFormat.format(value);
		
		retorno.replaceAll(",", ".");
		return retorno;
	}
}
	
	
