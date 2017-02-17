package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Aluno;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class AlunoController extends Controller {

	public Result getAll() {
		return ok(Json.toJson(Aluno.findAll()));
	}

	public Result cadastroAluno() {

		ObjectNode jsResp = Json.newObject();
		JsonNode json = request().body().asJson();

		Aluno novoAluno = new Aluno();
		novoAluno.setNome(json.findValue("nome").asText());
		novoAluno.setCpf(json.findValue("cpf").asText());
		novoAluno.setEmail(json.findValue("email").asText());
		novoAluno.setDataNascimento(json.findValue("dataNascimento").asText());
		novoAluno.setInstituicao(json.findValue("instituicao").asText());
		novoAluno.setSenha(json.findValue("senha").asText());
		if (json.findValue("telefone") != null) {
			novoAluno.setTelefone(json.findValue("telefone").asText());
		}
		try {
			novoAluno.save();
			jsResp.put("message", "Cadastrado com sucesso!");
		} catch (Exception e) {
			jsResp.put("message", "Erro no cadastro:  " + e.getMessage());
		}

		return ok(jsResp);
	}

	public Result loginAluno() {

		ObjectNode jsResp = Json.newObject();
		JsonNode json = request().body().asJson();
		try {
			Aluno alunoLogado = Aluno.findByEmailSenha(json.findValue("email").asText(),
					json.findValue("senha").asText());
			jsResp.put("status", 0);
			jsResp.put("message", "Logado com sucesso!");
			jsResp.put("alunoLogado", Json.toJson(alunoLogado));
		} catch (Exception e) {
			jsResp.put("status", 1);
			jsResp.put("message", "Erro no login!" + e.getMessage());
		}
		return ok(jsResp);
	}
}
