package br.com.everton.accountmanager.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response <T> {
	private String mensagem;
	private T payload;
}
