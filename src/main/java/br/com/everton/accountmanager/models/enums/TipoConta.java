package br.com.everton.accountmanager.models.enums;

public enum TipoConta {
	PERSONAL(1),
	BUSINESS(2);

	private Integer tipoConta;
	
	TipoConta(Integer tipoConta) {
		this.tipoConta = tipoConta;
	}

	public Integer getTipoConta() {
		return tipoConta;
	}
}
