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

import br.com.vr.entity.Cartao;
import br.com.vr.entity.repository.CartaoRepository;
import br.com.vr.util.DatabaseCleaner;
import br.com.vr.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class ConsultarCartaoIT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	@Autowired
	private CartaoRepository cartaoRepository;
	
	
	private Cartao cartao;
	private String jsonCartaoVO;
	private String jsonCartaoVO_JaExistente;
	
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cartoes";

		jsonCartaoVO             = ResourceUtils.getContentFromResource("/json/cartao/cartao.json");
		jsonCartaoVO_JaExistente = ResourceUtils.getContentFromResource("/json/cartao/cartao_ja_existente.json");
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoBuscarSaldoCartao() {
		given()
		    .pathParam("numeroCartao", cartao.getNumeroCartao())
			.accept(ContentType.JSON)
		.when()
			.get("/{numeroCartao}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("", equalTo(BigDecimal.valueOf(500)));
	}
	
	@Test
	public void deveRetornarStatus404_QuandoSaldoSaldoCartao_AndCartaoNaoExiste() {
		given()
		    .pathParam("numeroCartao", "9999999999999999")
			.accept(ContentType.JSON)
		.when()
			.get("/{numeroCartao}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}	
	

	@Test
	public void deveRetornarStatus201_QuandoCadastrarCartao() {
		given()
			.body(jsonCartaoVO)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}	
	
	
	@Test
	public void deveRetornarStatus422_QuandoCadastrarCartaoJaExistente() {
		given()
			.body(jsonCartaoVO_JaExistente)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	private void prepararDados() {
		cartao = new Cartao();
		cartao.setNumeroCartao("6549873025634502");
		cartao.setSenha("123");
		cartao.setSaldo(BigDecimal.valueOf(500));
		cartaoRepository.save(cartao);
	}
}
