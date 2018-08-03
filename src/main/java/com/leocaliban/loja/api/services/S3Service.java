package com.leocaliban.loja.api.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class S3Service {

	@Autowired
	private AmazonS3 s3Client;
	
	@Value("${s3.bucket}")
	private String bucketName;
	
	/**
	 * Envia arquivos
	 * @param multipartFile Arquivo 
	 * @return URI do arquivo na nuvem.
	 */
	public URI enviarArquivo(MultipartFile multipartFile) {
		try {
			String nomeDoArquivo = multipartFile.getOriginalFilename();
			InputStream inputStream;
			inputStream = multipartFile.getInputStream();
			String tipoDoArquivo = multipartFile.getContentType();
			return enviarArquivo(inputStream, nomeDoArquivo, tipoDoArquivo);
		} 
		catch (IOException e) {
			throw new RuntimeException("Erro de IO: " + e.getMessage());
		}
	
	}
	
	public URI enviarArquivo(InputStream inputStream , String nomeDoArquivo, String tipoDoArquivo) {
		try {
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(tipoDoArquivo);
			s3Client.putObject(bucketName, nomeDoArquivo, inputStream, meta);
			return s3Client.getUrl(bucketName, nomeDoArquivo).toURI();
		}

		catch (URISyntaxException e) {
			throw new RuntimeException("Erro ao converter URL para URI");
		}
	}

}
