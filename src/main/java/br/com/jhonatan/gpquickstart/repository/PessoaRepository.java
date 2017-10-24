package br.com.jhonatan.gpquickstart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jhonatan.gpquickstart.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	List<Pessoa> findByNomeContainingIgnoreCase(String nome);

}
