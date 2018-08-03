package com.leocaliban.loja.api.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.leocaliban.loja.api.domain.Cidade;
import com.leocaliban.loja.api.domain.Cliente;
import com.leocaliban.loja.api.domain.Endereco;
import com.leocaliban.loja.api.domain.enums.PerfilUsuario;
import com.leocaliban.loja.api.domain.enums.TipoCliente;
import com.leocaliban.loja.api.dto.ClienteDTO;
import com.leocaliban.loja.api.dto.ClienteNovoDTO;
import com.leocaliban.loja.api.repositories.ClienteRepository;
import com.leocaliban.loja.api.repositories.EnderecoRepository;
import com.leocaliban.loja.api.security.UserSpringSecurity;
import com.leocaliban.loja.api.services.exceptions.AutorizacaoException;
import com.leocaliban.loja.api.services.exceptions.IntegridadeDeDadosException;
import com.leocaliban.loja.api.services.exceptions.ObjetoNaoEncontratoException;

@Service
public class ClienteService {
	
	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImagemService imagemService;
	
	public Cliente buscarPorId(Integer id) {
		UserSpringSecurity usuario = UserService.usuarioAutenticado();
		if(usuario == null || !usuario.hasRole(PerfilUsuario.ADMIN) && !id.equals(usuario.getId())) {
			throw new AutorizacaoException("Acesso negado.");
		}
		
		//OPTIONAL - Container que encapsula o objeto buscado, evitando excessao nullpointer [JAVA 8]
		Optional<Cliente> objeto = repository.findById(id);
		//Se não achar o objeto retorna null.
		return objeto.orElseThrow(() -> new ObjetoNaoEncontratoException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public List<Cliente> buscarTodos() {
		return repository.findAll();
	}
	
	/**
	 * Busca os clientes com paginação
	 * @param page Integer que representa a página (0-1-2...)
	 * @param linesPerPage Integer que representa quantas linhas a página possui.
	 * @param orderBy String que representa por qual atributo a lista será ordenada.
	 * @param direction String que representa se a lista será Ascendente ou Descendente.
	 * @return Página
	 */
	public Page<Cliente> buscarComPaginacao(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	//Ao salvar um cliente o endereço tem que ser salvo também, para garantir que tudo ocorra em uma única sessão usa o Transactional
	@Transactional 
	public Cliente salvar(Cliente objeto) {
		objeto.setId(null);
		objeto = repository.save(objeto);
		enderecoRepository.saveAll(objeto.getEnderecos());
		return objeto;
	}

	public Cliente editar(Cliente objeto) {
		Cliente novoObjeto = buscarPorId(objeto.getId());
		aplicarEdicao(novoObjeto, objeto);
		return repository.save(novoObjeto);
	}

	/**
	 * Método auxiliar para aplicar as edições no objeto.
	 * @param novoObjeto - objeto que será salvo contendo os dados complementares do objeto antigo.
	 * @param objeto - objeto recuperado do banco que irá tranferir seus dados para o novo objeto.
	 */
	private void aplicarEdicao(Cliente novoObjeto, Cliente objeto) {
		novoObjeto.setNome(objeto.getNome());
		novoObjeto.setEmail(objeto.getEmail());
	}

	public void deletar(Integer id) {
		buscarPorId(id);
		try {
			repository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new IntegridadeDeDadosException("Não é possível excluir um cliente que possui pedidos.");
		}
	}
	
	public Cliente converterDTO(ClienteDTO objetoDTO) {
		return new Cliente(objetoDTO.getId(), objetoDTO.getNome(), objetoDTO.getEmail(), null, null, null);
	}
	
	public Cliente converterDTO(ClienteNovoDTO objetoDTO) {
		Cliente cliente = new Cliente(null, objetoDTO.getNome(), objetoDTO.getEmail(), 
							objetoDTO.getCpfOuCnpj(), TipoCliente.toEnum(objetoDTO.getTipoCliente()), //tipo cliente convertido
							passwordEncoder.encode(objetoDTO.getSenha()));
		
		Cidade cidade = new Cidade(objetoDTO.getCidadeId(), null, null);
		
		Endereco endereco = new Endereco(null, objetoDTO.getLogradouro(), objetoDTO.getNumero(), 
							objetoDTO.getComplemento(), objetoDTO.getBairro(), objetoDTO.getCep(), cliente, cidade);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(objetoDTO.getTelefone1());
		
		if(objetoDTO.getTelefone2() != null) {
			cliente.getTelefones().add(objetoDTO.getTelefone2());
		}
		if(objetoDTO.getTelefone3() != null) {
			cliente.getTelefones().add(objetoDTO.getTelefone3());
		}
		return cliente;
	}
	
	public URI enviarFotoDePerfil(MultipartFile multipartFile) {
		UserSpringSecurity usuario = UserService.usuarioAutenticado();
		if(usuario == null) {
			throw new AutorizacaoException("Acesso negado.");
		}
		
		BufferedImage jpgImage = imagemService.getJpgImageFromFile(multipartFile);
		String nomeDoArquivo = prefix + usuario.getId() + ".jpg";

		return s3Service.enviarArquivo(imagemService.getInputStream(jpgImage, "jpg"), nomeDoArquivo, "image");

	}
}
