package com.leocaliban.loja.api.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

	/**
	 * Faz a conversão de String para Integer, recuperando somente os números.
	 * @param url String da URL (/categoria=1,2,3)
	 * @return lista de inteiros.
	 */
	public static List<Integer> converterUrlIdsParaListaDeInteiros(String url){
		String[] vetorUrl = url.split(",");
		List<Integer> listaIds = new ArrayList<>();
		
		for(int i = 0; i < vetorUrl.length; i++) {
			listaIds.add(Integer.parseInt(vetorUrl[i]));
		}
		return listaIds;
		//Usando lambda
		//return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
	
	/**
	 * Método que faz o decode de strings removendo os espaços em branco quando surgirem na URL
	 * @param url String da URL (/nome=no me)
	 * @return nome sem espaços
	 */
	public static String decodeParam(String url) {
		try {
			return URLDecoder.decode(url, "UTF-8");
		} 
		catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
