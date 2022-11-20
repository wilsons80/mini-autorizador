package br.com.vr.controller;

import static io.restassured.RestAssured.given;

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
public class RealizarTransacaoIT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	@Autowired
	private CartaoRepository cartaoRepository;
	
	
	private Cartao cartao;
	private String jsonTransacaoCartao_Inexistente;
	private String jsonTransacaoCartao_SaldoInsuficiente;
	private String jsonTransacaoCartao_SenhaInvalida;
	private String jsonTransacaoCartao_ResuldadoOK;
	
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/transacoes";

		jsonTransacaoCartao_Inexistente       = ResourceUtils.getContentFromResource("/json/transacao/transacao_cartao_inexistente.json");
		jsonTransacaoCartao_SaldoInsuficiente = ResourceUtils.getContentFromResource("/json/transacao/transacao_cartao_saldo_insuficiente.json");
		jsonTransacaoCartao_SenhaInvalida     = ResourceUtils.getContentFromResource("/json/transacao/transacao_cartao_senha_invalida.json");
		jsonTransacaoCartao_ResuldadoOK       = ResourceUtils.getContentFromResource("/json/transacao/transacao_cartao.json");
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	
	@Test
	public void deveRetornarStatus201_QuandoTransacaoRealizadaComSucesso() {
		given()
			.body(jsonTransacaoCartao_ResuldadoOK)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
		.statusCode(HttpStatus.CREATED.value())
		.assertThat().equals(StatusTransacaoEnum.OK);
	}
	
	
	@Test
	public void deveRetornarStatus422_QuandoTransacaoRealizadaComSaldoInsuficiente() {
		given()
			.body(jsonTransacaoCartao_SaldoInsuficiente)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
		.statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
		.assertThat().equals(StatusTransacaoEnum.SALDO_INSUFICIENTE);
	}
	
	
	@Test
	public void deveRetornarStatus422_QuandoTransacaoRealizadaComSenhaInvalida() {
		given()
			.body(jsonTransacaoCartao_SenhaInvalida)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
		.statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
		.assertThat().equals(StatusTransacaoEnum.SENHA_INVALIDA);
	}
	
	
	@Test
	public void deveRetornarStatus422_QuandoTransacaoRealizadaComCartaoInexistente() {
		given()
			.body(jsonTransacaoCartao_Inexistente)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
		.statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
		.assertThat().equals(StatusTransacaoEnum.CARTAO_INEXISTENTE);
	}
	
		
	private void prepararDados() {
		cartao = new Cartao();
		cartao.setNumeroCartao("2223334445556666");
		cartao.setSenha("1234");
		cartao.setSaldo(BigDecimal.valueOf(500));
		cartaoRepository.save(cartao);
	}
}

