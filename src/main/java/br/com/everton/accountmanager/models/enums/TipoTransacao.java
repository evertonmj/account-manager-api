package br.com.everton.accountmanager.models.enums;

public enum TipoTransacao {
	DEPOSIT(1),
	WITHDRAW(2);
	
	private Integer tipoTransacao;
	
	TipoTransacao(Integer tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public Integer getTipoTransacao() {
		return tipoTransacao;
	}
}
