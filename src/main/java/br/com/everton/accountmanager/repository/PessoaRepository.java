package br.com.everton.accountmanager.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.everton.accountmanager.models.entity.Pessoa;

public interface PessoaRepository extends CrudRepository<Pessoa, Integer> {

}
