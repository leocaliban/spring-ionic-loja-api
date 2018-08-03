package com.leocaliban.loja.api.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
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
}
