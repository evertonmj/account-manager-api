package br.com.everton.accountmanager.models.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.com.everton.accountmanager.models.enums.TipoConta;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaRequest {
	@NotNull(message="Person ID must not be nullCodigo da pessoa nao pode ser vazio") @Pattern(regexp = "[0-9]", message="ID must be composed only with numbers")
	@Schema(description = "Person ID", example="3")
	private Integer idPessoa;
	
	@NotNull(message="Daily limit must not be empty")
	@Schema(description = "Withdraw daily limit", example="1500.99")
	private BigDecimal limiteSaqueDiario;
	
	@NotNull(message="Account type must not be empty")
	@Schema(description = "Account type. Valid values: PERSONAL or BUSINESS", example = "PERSONAL", allowableValues = {"PERSONAL", "BUSINESS"})
	private TipoConta tipoConta;
}
