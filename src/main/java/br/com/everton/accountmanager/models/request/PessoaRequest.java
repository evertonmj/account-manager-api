package br.com.everton.accountmanager.models.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaRequest {
	@NotNull(message="Person name must not be empty")
	@Schema(description = "Person name", example="Everton M")
	private String nome;
	
	@NotNull(message="CPF must not be empty") @Pattern(regexp = "[0-9]", message="Only numbers")
	@Schema(description = "Person CPF", example = "12345678900")
	private String cpf;
	
	@NotNull(message="Birth date must no be empty") @Pattern(regexp = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$", message="Birth date in pattern DD/MM/AAAA")
	@Schema(description = "Birth date in pattern DD/MM/AAAA", example="22/09/1987")
	private String dataNascimento;
}
