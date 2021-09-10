package br.com.everton.accountmanager.controller;

import java.text.ParseException;

import javax.validation.Valid;

import br.com.everton.accountmanager.models.request.PessoaRequest;
import br.com.everton.accountmanager.models.response.Response;
import br.com.everton.accountmanager.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.everton.accountmanager.models.entity.Pessoa;
import br.com.everton.accountmanager.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping(path="/pessoa")
@Tag(name = "Person API")
public class PessoaController {
	@Autowired
  PessoaRepository pessoaRepository;
	
	@Operation(summary = "Create a new person")
	@PostMapping(path="/criar")
	public @ResponseBody
  Response<Pessoa> criarPessoa(@Valid @RequestBody PessoaRequest requestBody) {
		Pessoa pessoa = new Pessoa();
		Response<Pessoa> response = new Response<Pessoa>();
		try {
			pessoa.setCpf(requestBody.getCpf());
			pessoa.setNome(requestBody.getNome());
			pessoa.setDataNascimento(Utils.converterStringData(requestBody.getDataNascimento()));
			pessoa = pessoaRepository.save(pessoa);
			response.setMensagem("Person created!");
			response.setPayload(pessoa);
		} catch (ParseException e) {
			e.printStackTrace();
			response.setMensagem("Error formatting birth date: " + e.getMessage());
			response.setPayload(pessoa);
			return response;
		}
		
		return response;
	}
}
