package br.com.vr.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import br.com.vr.core.StatusTransacaoEnum;
import br.com.vr.entity.Cartao;
import br.com.vr.entity.repository.CartaoRepository;
import br.com.vr.util.DatabaseCleaner;
import br.com.vr.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class ConsultarTransacaoIT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	@Autowired
	private CartaoRepository cartaoRepository;
	
	
	private Cartao cartao;
	private String jsonTransacao;
	private String jsonTransacaoSaldoInsuficiente;
	private String jsonTransacaoSenhaInvalida;
	private String transacaoCartaoInexistente;
	
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/transacoes";

		jsonTransacao                   = ResourceUtils.getContentFromResource("/json/transacao/transacao_cartao.json");
		jsonTransacaoSaldoInsuficiente  = ResourceUtils.getContentFromResource("/json/transacao/transacao_cartao_saldo_insuficiente.json");
		jsonTransacaoSenhaInvalida      = ResourceUtils.getContentFromResource("/json/transacao/transacao_cartao_senha_invalida.json");
		transacaoCartaoInexistente      = ResourceUtils.getContentFromResource("/json/transacao/transacao_cartao_inexistente.json");
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void deveRetornarStatus201_QuandoRetornaSaldoCartao() {
		given()
			.body(jsonTransacao)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value())
			.body("", equalTo(StatusTransacaoEnum.OK));
	}	
	
	@Test
	public void deveRetornarStatus422_QuandoRetornaSaldoInsuficiente() {
		given()
			.body(jsonTransacaoSaldoInsuficiente)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
			.body("", equalTo(StatusTransacaoEnum.SALDO_INSUFICIENTE));
	}	

	@Test
	public void deveRetornarStatus422_QuandoRetornaSenhaInvalida() {
		given()
			.body(jsonTransacaoSenhaInvalida)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
			.body("", equalTo(StatusTransacaoEnum.SENHA_INVALIDA));
	}
	
	
	@Test
	public void deveRetornarStatus422_QuandoRetornaCartaoInexistente() {
		given()
			.body(transacaoCartaoInexistente)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
			.body("", equalTo(StatusTransacaoEnum.CARTAO_INEXISTENTE));
	}
	
	
	private void prepararDados() {
		cartao = new Cartao();
		cartao.setNumeroCartao("6549873025634502");
		cartao.setSenha("123");
		cartao.setSaldo(BigDecimal.valueOf(500));
		cartaoRepository.save(cartao);
	}
}
