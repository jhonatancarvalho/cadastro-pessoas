package br.com.jhonatan.gpquickstart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.jhonatan.gpquickstart.model.Pessoa;
import br.com.jhonatan.gpquickstart.repository.PessoaRepository;
import br.com.jhonatan.gpquickstart.repository.filter.PessoaFilter;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoas;
	
	public List<Pessoa> findAll() {
		return (List<Pessoa>) pessoas.findAll();
	}
	
	public void salvar(Pessoa pessoa) {
		try {
			pessoas.save(pessoa);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}

	public void excluir(Long codigo) {
		pessoas.delete(codigo);
	}

	public List<Pessoa> filtrar(PessoaFilter filtro) {
		final String nome = filtro.getNome() == null ? "%" : filtro.getNome();
		return pessoas.findByNomeContainingIgnoreCase(nome);
	}

}
