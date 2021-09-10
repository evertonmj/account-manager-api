package br.com.everton.accountmanager.models.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaqueRequest {
	@NotNull(message="Account ID must not be empty") @Pattern(regexp = "[0-9]", message="Only numbers")
	@Schema(description = "Account ID", example="3")
	private Integer idConta;
	
	@NotNull(message="Withdraw amount value")
	@Schema(description = "Withdraw amount value", example="15.00")
	private BigDecimal valor;
}
