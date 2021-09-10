package br.com.everton.accountmanager.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.everton.accountmanager.models.entity.Conta;

public interface ContaRepository extends CrudRepository<Conta, Integer> {

}
