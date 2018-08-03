package com.leocaliban.loja.api.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.leocaliban.loja.api.services.exceptions.ArquivoException;

@Service
public class ImagemService {

	/**
	 * Converte um tipo de imagem para jpg
	 * @param uploadedFile
	 * @return
	 */
	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		String extensao = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
		if(!"png".equals(extensao) && !"jpg".equals(extensao)) {
			throw new ArquivoException("Somente imagens PNG e JPG são permitidas.");
		}
		
		try {
			BufferedImage imagem = ImageIO.read(uploadedFile.getInputStream());
			if("png".equals(extensao)) {
				imagem = pngToJpg(imagem);
			}
			return imagem;
		} 
		catch (IOException e) {
			throw new ArquivoException("Erro ao ler o arquivo.");
		}
	}

	public BufferedImage pngToJpg(BufferedImage imagem) {
		BufferedImage jpgImagem = new BufferedImage(imagem.getWidth(), imagem.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImagem.createGraphics().drawImage(imagem, 0, 0, Color.WHITE, null);
		return jpgImagem;
	}
	
	public InputStream getInputStream(BufferedImage imagem, String extensao) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(imagem, extensao, os);
			return new ByteArrayInputStream(os.toByteArray());
		}
		catch (IOException e) {
			throw new ArquivoException("Erro ao ler o arquivo.");
		}
	}
	
	/**
	 * Recorta a imagem de forma quadrada
	 * @param sourceImg
	 * @return imagem recortada
	 */
	public BufferedImage recortarImagem(BufferedImage sourceImg) {
		int min = (sourceImg.getHeight() <= sourceImg.getWidth() ? sourceImg.getHeight() : sourceImg.getWidth());
		return Scalr.crop(
				sourceImg, 
				(sourceImg.getWidth())/2 - (min/2),
				(sourceImg.getHeight())/2 - (min/2), min, min);
	}
	
	/**
	 * Redimensiona a imagem
	 * @param sourceImg Imagem
	 * @param size Dimensão desejada	
	 * @return imagem redimensionada
	 */
	public BufferedImage redimensionarImagem(BufferedImage sourceImg, int size) {
		return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);
	}
}
