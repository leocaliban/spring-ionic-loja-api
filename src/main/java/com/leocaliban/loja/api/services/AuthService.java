package com.leocaliban.loja.api.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.leocaliban.loja.api.domain.Cliente;
import com.leocaliban.loja.api.repositories.ClienteRepository;
import com.leocaliban.loja.api.services.exceptions.ObjetoNaoEncontratoException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private Random random = new Random();

	public void enviarNovaSenha(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {
			throw new ObjetoNaoEncontratoException("E-mail não encontrado.");
		}
		
		String novaSenha = novaSenha();
		cliente.setSenha(passwordEncoder.encode(novaSenha));
		
		clienteRepository.save(cliente);
		emailService.enviarNovaSenhaEmail(cliente, novaSenha);
	}

	private String novaSenha() {
		char[] vetor = new char[10];
		for(int i = 0; i < 10; i++) {
			vetor[i] = randomChar();
		}
		return new String(vetor);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		if(opt == 0) { //gera dígito
			return (char) (random.nextInt(10) + 48);
		}
		else if(opt == 1) {//gera letra maiúscula
			return (char) (random.nextInt(26) + 65);
		}
		else {//gera letra minúscula
			return (char) (random.nextInt(26) + 97);
		}
	}
}
