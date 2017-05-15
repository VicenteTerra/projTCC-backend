package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Aluno;
import models.Estabelecimento;
import models.Instituicao;
import models.Usuario;
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
		novoAluno.setSenha(json.findValue("senha").asText());
		novoAluno.setTipoUsuario(1);
		Instituicao inst = Instituicao.findById(json.findValue("instituicao").asInt());
		novoAluno.setInstituicao(inst);
		if (json.findValue("telefone") != null) {
			novoAluno.setTelefone(json.findValue("telefone").asText());
		}
		try {
			novoAluno.save();
			Usuario newUser = new Usuario();
			newUser.setLogin(novoAluno.getEmail());
			newUser.setTipo(2);
			newUser.setDescricao("Aluno");
			newUser.save();
			
			jsResp.put("status", 0);
			jsResp.put("message", "Cadastrado com sucesso!");
		} catch (Exception e) {
			jsResp.put("status", 1);
			jsResp.put("message", "Erro no cadastro:  " + e.getMessage());
		}

		return ok(jsResp);
	}

	public Result loginAluno() {

		ObjectNode jsResp = Json.newObject();
		JsonNode json = request().body().asJson();
		Aluno alunoLogado = Aluno.findByEmailSenha(json.findValue("email").asText(), json.findValue("senha").asText());
		Estabelecimento estabelecimentoLogado = Estabelecimento.findByEmailSenha(json.findValue("email").asText(),
				json.findValue("senha").asText());
		if (alunoLogado != null) {
			jsResp.put("status", 0);
			jsResp.put("tipoUsuario", 1);
			jsResp.put("message", "Logado com sucesso!");
		} else if (estabelecimentoLogado != null) {
			jsResp.put("status", 0);
			jsResp.put("tipoUsuario", 2);
			jsResp.put("message", "Logado com sucesso!");
		} else {
			jsResp.put("status", 1);
			jsResp.put("message", "Usuário não encontrado!");
		}
		return ok(jsResp);
	}
}
