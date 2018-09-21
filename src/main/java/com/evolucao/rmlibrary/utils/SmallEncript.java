package com.evolucao.rmlibrary.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.vaadin.ui.Label;

public class SmallEncript {
	
	public SmallEncript() {
		
	}
	
	/*
	 * Executa o processo de encriptacao
	 */
	public static String encript(String mensagem) {
		String retorno = "";
		Integer tamanho = mensagem.length();
		Integer posicao = -1;
		String de = loadOriginalKey();
		String para = "";
		
		// Sorteia o grupo de encriptacao
		String group = sortEncriptGround();
		List<String> chaves = loadEncriptKeyGroup(group);
		Integer posicoes = chaves.size();
		
		for (int vi1=0; vi1<tamanho; vi1++) {
			posicao = posicao + 1;
			if (posicao>posicoes-1) {
				posicao = 0;
			}
			para = chaves.get(posicao);
			retorno = retorno + troca(mensagem.substring(vi1, vi1+1), de, para);
		}
		
		return group+retorno;
	}

	public static String loadOriginalKey() {
		String chave1 = "ABCDEFGHIJLKMNOPQRSTUVWYXZ1234567890";
		return chave1;
	}

	/*
	 * Gera novas sequencias encripitadas para serem utilizadas como ribbon
	 * essa rotina deve ser utilizada quando se desejar expandir o numero de ribbons
	 */
	public String sortEncriptKey() {
		String chave = loadOriginalKey();
		String chave2 = "";
		
		Random random = new Random();
		while (chave.length()>0) {
			Integer tamanho = chave.length();
			if (tamanho>1) {
				Integer posicao = random.nextInt(tamanho);
				if (posicao<0) {
					posicao = 0;
				}
				
				chave2 = chave2 + chave.substring(posicao, posicao+1);
				chave = chave.substring(0, posicao) + chave.substring(posicao+1, chave.length());
			}
			else {
				chave2 = chave2 + chave;
				chave = "";
			}
		}
		
		return chave2;
	}

	/*
	 * Sorteia a sequencia de ribbons que vai compor um novo grupo de encriptacao
	 * essa rotina deve ser utilizada quando se desejar expandir o numero de grupos de encriptacao
	 */
	public Integer sortRibbonSequence() {
		Random random = new Random();
		Integer posicao = random.nextInt(20);
		return posicao;
	}


	/*
	 * Troca um caracter normal por um de ribbon ou vice versa
	 */
	private static String troca(String caracter, String origem, String destino) {
		Integer posicao = origem.indexOf(caracter);
		/*
		 * Caso nao tenha identificado o caracter como sendo um caracter valido
		 */
		if (posicao<0) {
			throw new IllegalAccessError("Illegal character detected [" + caracter + "]!");
		}
		String retorno = destino.substring(posicao, posicao+1);
		return retorno;
	}
	
	/*
	 * Sorteia utilizando numeros aleatorios qual o grupo de esquema de encriptacao
	 * sera utilizado para encriptar a frase passada como parametro na encriptacao
	 */
	private static String sortEncriptGround() {
		String retorno = "";
		
		Random random = new Random();
		// Numero de grupos que existe
		Integer grupo = random.nextInt(3);
		switch (grupo) {
		case 0:
			retorno = "00";
			break;
		case 1:
			retorno = "01";
			break;
		case 2:
			retorno = "02";
			break;
		}
		
		return retorno;
	}
	
	/*
	 * Carrega os ribbons que fazem parte do grupo de encriptacao que esta sendo solicitado
	 */
	private static List<String> loadEncriptKeyGroup(String identificador) {
		List<String> retorno = new ArrayList<String>();
		
		switch (identificador) {
		case "00":
			retorno.add(loadEncriptRibbonKey(8));
			retorno.add(loadEncriptRibbonKey(5));
			retorno.add(loadEncriptRibbonKey(9));
			retorno.add(loadEncriptRibbonKey(14));
			retorno.add(loadEncriptRibbonKey(2));
			retorno.add(loadEncriptRibbonKey(12));
			retorno.add(loadEncriptRibbonKey(16));
			retorno.add(loadEncriptRibbonKey(15));
			retorno.add(loadEncriptRibbonKey(11));
			retorno.add(loadEncriptRibbonKey(7));
            break;
		case "01":
			retorno.add(loadEncriptRibbonKey(2));
			retorno.add(loadEncriptRibbonKey(9));
			retorno.add(loadEncriptRibbonKey(1));
			retorno.add(loadEncriptRibbonKey(1));
			retorno.add(loadEncriptRibbonKey(10));
			retorno.add(loadEncriptRibbonKey(13));
			retorno.add(loadEncriptRibbonKey(3));
			retorno.add(loadEncriptRibbonKey(7));
			retorno.add(loadEncriptRibbonKey(15));
			retorno.add(loadEncriptRibbonKey(8));
            break;
		case "02":
			retorno.add(loadEncriptRibbonKey(6));
			retorno.add(loadEncriptRibbonKey(9));
			retorno.add(loadEncriptRibbonKey(7));
			retorno.add(loadEncriptRibbonKey(0));
			retorno.add(loadEncriptRibbonKey(15));
			retorno.add(loadEncriptRibbonKey(10));
			retorno.add(loadEncriptRibbonKey(14));
			retorno.add(loadEncriptRibbonKey(13));
			retorno.add(loadEncriptRibbonKey(1));
			retorno.add(loadEncriptRibbonKey(17));
            break;
		}
		
		return retorno;
	}
	
	/*
	 * Chama o ribbon para encriptacao
	 */
	private static String loadEncriptRibbonKey(Integer identificador) {
		String retorno = null;
		
		switch (identificador) {
		case 0:
			retorno = "TBDPVS5Y8LXU9A4MORHE1J2I06FQ7KWZCGN3";
			break;
		case 1:
			retorno = "C4X7UEY8VP9S30FARGKNW625ZJQMI1LDHOBT";
			break;
		case 2:
			retorno = "CLIM6SVEXOPF8573TNB9AHKWR4ZYU0DQ2GJ1";
			break;
		case 3:
			retorno = "1D3ETNKXJ7VOH4SG6QC9R5PWLF2YI0MAU8BZ";
			break;
		case 4:
			retorno = "04UWPVE8GS1OF6DYM39HNTBRXQIJAC572LKZ";
			break;
		case 5:
			retorno = "FCX6HALVJKIZ8WON9U17GRQMT02YP5EDB43S";
			break;
		case 6:
			retorno = "1F6GY4LVBH7ZUJR59TPMNSX3ED0AQKC82WOI";
			break;
		case 7:
			retorno = "6Y8C742HDUM9AT0RNIWV3GLOB1FKPXEQ5ZSJ";
			break;
 		case 8:        
			retorno = "74L5GBZ82HJ9SVEM1Q6ANOK3XFWUCDT0IYRP";
			break;
		case 9:
			retorno = "5HG4NFL7U9R0VTEXQOCKY1BP6MJ23SAZ8WID";
			break;
		case 10:
			retorno = "QKRNAVCJP2BM3T8GD9ZIS4FLU7O0H56WEYX1";
			break;
		case 11:
			retorno = "RK53ZT9PF6CAYVXDQGL708HBIUMES4ONJ1W2";
			break;
		case 12:
			retorno = "BYZ1ENWXRO9C7UM546SD2HPLGKTJIQ8FV0A3";
			break;
		case 13:
			retorno = "MHWYIEJONK47DPLU5RVXF6G208TSZB193AQC";
			break;
		case 14:
			retorno = "FB2Q0M845HAWDP3XLVE9CUY1RIZ6OT7GNKSJ";
			break;
		case 15:
			retorno = "YK3IXQBERUM4JT9APS7N5LOG0ZF81WCH6V2D";
			break;
		case 16:
			retorno = "ZW0KMQPS5HIXYFJT13VGBDRN428C6OE9LU7A";
			break;
		case 17:
			retorno = "2JPL81C5T76HXZB9VUM4DON0FYKIRSGQW3EA";
			break;
		case 18:
			retorno = "RGIOMN02FQDCAW619KJUP5HEX7VSZ4BYTL38";
			break;
		case 19:
			retorno = "PE6UN02AKR5DY4391ZHFBQV8TSIGMCJX7LOW";
			break;
		}
		
		if (retorno==null) {
			throw new IllegalAccessError("EncriptKey " + identificador + " not found!");
		}
		return retorno;
	}
}
