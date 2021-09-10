package br.com.everton.accountmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.everton.accountmanager.AccountManagerApplication;

@SpringBootTest(classes= AccountManagerApplication.class)
@AutoConfigureTestDatabase(replace = Replace.ANY)
class AccountManagerApplicationTests {
	
	@Resource
	private AccountManagerApplication controller;
	
	@Test
	public void testaHelloWorld() {
		String retorno = controller.home();
		assertEquals(retorno, "Hello World!");
	}

}
