package br.com.everton.accountmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.everton.accountmanager.AccountManagerApplication;
import br.com.everton.accountmanager.controller.PessoaController;
import br.com.everton.accountmanager.models.entity.Pessoa;
import br.com.everton.accountmanager.models.request.PessoaRequest;
import br.com.everton.accountmanager.models.response.Response;

@SpringBootTest(classes= AccountManagerApplication.class)
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class PessoaControllerTest {
	
	public PessoaControllerTest() {
		super();
	}

	@Autowired
	PessoaController pessoaController;
	
	@Test
	public void testarCriacaoPessoa() {
		PessoaRequest pessoaRequest = new PessoaRequest();
		pessoaRequest.setCpf("0000");
		pessoaRequest.setDataNascimento("01/01/2000");
		pessoaRequest.setNome("Joao Test");
		
		Response<Pessoa> response = pessoaController.criarPessoa(pessoaRequest);
		
		assertEquals(response.getMensagem(), "Person created!");
		assertNotNull(response.getPayload());
	}
}
