package br.com.everton.accountmanager.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import br.com.everton.accountmanager.models.entity.Transacao;
import br.com.everton.accountmanager.models.enums.TipoTransacao;
import br.com.everton.accountmanager.models.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.everton.accountmanager.models.entity.Conta;
import br.com.everton.accountmanager.models.request.ContaRequest;
import br.com.everton.accountmanager.models.request.DepositoRequest;
import br.com.everton.accountmanager.models.request.SaqueRequest;
import br.com.everton.accountmanager.repository.ContaRepository;
import br.com.everton.accountmanager.repository.TransacaoRepository;
import br.com.everton.accountmanager.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping(path="/conta")
@Tag(name = "Management Account API")
public class ContaController {
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Operation(summary = "Create a new account")
	@PostMapping(path="/criar")
	public @ResponseBody
  Response<Conta> criarConta(@RequestBody ContaRequest requestBody) {
		Conta conta = new Conta();
		Response<Conta> response = new Response<Conta>(); 
		
		conta.setIdPessoa(requestBody.getIdPessoa());
		conta.setLimiteSaqueDiario(requestBody.getLimiteSaqueDiario());
		conta.setTipoConta(requestBody.getTipoConta().getTipoConta());
		conta.setDataCriacao(new Date(Calendar.getInstance().getTime().getTime()));
		conta.setFlagAtivo(true);
		conta.setSaldo(BigDecimal.ZERO);
		
		conta = contaRepository.save(conta);
		response.setMensagem("Account created!");
		response.setPayload(conta);
		
		return response;
	}
	
	@Operation(summary = "Deposit in an account")
	@PostMapping(path="/depositar")
	public @ResponseBody Response<Transacao> depositarValor(@RequestBody DepositoRequest requestBody) {
		Optional<Conta> contaRep = contaRepository.findById(requestBody.getIdConta());
		Response<Transacao> response = new Response<Transacao>();
		Conta conta = new Conta();
		
		if (!contaRep.isPresent()) {
			response.setMensagem("Account not found!");
			return response;
		} 
		
		// get account and update balance with deposit value
		conta = contaRep.get();
		
		//check if account is blocked to prevent deposits
		if (conta.getFlagAtivo().equals(false)) {
			response.setMensagem("Account blocked!");
			return response;
		}
		BigDecimal saldoAtual = conta.getSaldo();
		conta.setSaldo(saldoAtual.add(requestBody.getValor()));
		contaRepository.save(conta);
		
		//salva deposito na tabela de transacoes
		Transacao transacao = new Transacao();
		transacao.setIdConta(conta.getIdConta());
		transacao.setDataTransacao(new Date(Calendar.getInstance().getTime().getTime()));
		transacao.setValor(requestBody.getValor());
		transacao.setTipoTransacao(TipoTransacao.DEPOSIT.getTipoTransacao());
		transacaoRepository.save(transacao);
		
		response.setMensagem("Deposit made successfully");
		response.setPayload(transacao);
		
		return response;
	}
	
	@Operation(summary = "Get account balance")
	@GetMapping(path="/saldo/{idConta}")
	public @ResponseBody Response<BigDecimal> obterSaldo(@PathVariable Integer idConta) {
		Conta conta = new Conta();
		Response<BigDecimal> response = new Response<BigDecimal>();
		Optional<Conta> contaRep = contaRepository.findById(idConta);
		
		if (!contaRep.isPresent()) {
			response.setMensagem("Account not found!");
			return response;
		}
		
		conta = contaRep.get();
		if (conta.getFlagAtivo().equals(false)) {
			response.setMensagem("Blocked account!");
			return response;
		}
		
		response.setMensagem("Account balance:");
		response.setPayload(conta.getSaldo());
		
		return response;
	}
	
	@Operation(summary = "Blocks an account")
	@PostMapping(path="/bloquear/{idConta}")
	public @ResponseBody Response<Conta> bloquearConta(@PathVariable Integer idConta) {
		Conta conta = new Conta();
		Response<Conta> response = new Response<Conta>();
		Optional<Conta> contaRep = contaRepository.findById(idConta);
		
		if (!contaRep.isPresent()) {
			response.setMensagem("Account not found!");
			return response;
		}
		
		conta = contaRep.get();
		conta.setFlagAtivo(false);
		
		conta = contaRepository.save(conta);
		
		response.setMensagem("Account blocked!");
		response.setPayload(conta);
		
		return response;
	}
	
	@Operation(summary = "Withdraw value from account")
	@PostMapping(path="/sacar")
	public @ResponseBody Response<Transacao> sacarValor(@RequestBody SaqueRequest requestBody) {
		Optional<Conta> contaRep = contaRepository.findById(requestBody.getIdConta());
		Response<Transacao> response = new Response<Transacao>();
		Conta conta = new Conta();
		
		//check if account exists
		if (!contaRep.isPresent()) {
			response.setMensagem("Account not found!");
			return response;
		} 
		
		//get account
		conta = contaRep.get();
		
		if (conta.getFlagAtivo().equals(false)) {
			response.setMensagem("Account blocked!");
			return response;
		}
		
		//check if withdraw amount is bigger than daily limit
		int saldoDiario = requestBody.getValor().compareTo(conta.getLimiteSaqueDiario());
		if (saldoDiario == 1) {
			response.setMensagem("Amount is bigger than daily limit");
			return response;
		}
		
		// get all transactions on that day to check if daily limit is exceeded
		List<Transacao> transacoes = transacaoRepository.findByIdContaSaquesDia(conta.getIdConta());
		BigDecimal totalTransacoesSaque = BigDecimal.ZERO;
		
		//sum all transactions
		for (Transacao transacao : transacoes) {
			totalTransacoesSaque = totalTransacoesSaque.add(transacao.getValor());
		}
		
		int limite = totalTransacoesSaque.compareTo(conta.getLimiteSaqueDiario()); 
		if(limite == 0 || limite == 1) {
			response.setMensagem("Daily limit exceeded!");
			return response;
		}
		
		BigDecimal saldoAtual = conta.getSaldo();
		
		//check if account has sufficient funds
		int saldoValido = saldoAtual.compareTo(requestBody.getValor());
		if (saldoValido == -1) {
			response.setMensagem("You don't have enough funds!");
			return response;
		}
		
		conta.setSaldo(saldoAtual.subtract(requestBody.getValor()));
		conta = contaRepository.save(conta);
		
		Transacao transacao = new Transacao();
		transacao.setIdConta(conta.getIdConta());
		transacao.setDataTransacao(new Date(Calendar.getInstance().getTime().getTime()));
		transacao.setValor(requestBody.getValor());
		transacao.setTipoTransacao(TipoTransacao.WITHDRAW.getTipoTransacao());
		transacao = transacaoRepository.save(transacao);
		
		response.setMensagem("Withdraw made with success");
		response.setPayload(transacao);
		
		return response;
	}
	
	@Operation(summary = "Get account transaction extract")
	@GetMapping(path="/extrato")
	public @ResponseBody Response<List<Transacao>> extratoConta(@RequestParam Integer idConta, @RequestParam(required = false) String dataInicial, @RequestParam(required = false) String dataFinal) {
		Conta conta = new Conta();
		Response<List<Transacao>> response = new Response<List<Transacao>>();
		
		List<Transacao> transacoes = new ArrayList<Transacao>();
		if (!ObjectUtils.isEmpty(dataInicial) && !ObjectUtils.isEmpty(dataFinal)) {
			try {
				transacoes = transacaoRepository.findByIdConta(idConta, Utils.converterStringData(dataInicial) , Utils.converterStringData(dataFinal));
			} catch (ParseException e) {
				e.printStackTrace();
				response.setMensagem("Error converting dates");
				return response;
			}
		} else {
			transacoes = transacaoRepository.findByIdConta(idConta);
		}
		
		response.setMensagem("Account transactions");
		response.setPayload(transacoes);
		
		return response;
	}
}
