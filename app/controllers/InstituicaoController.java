package controllers;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Instituicao;
import models.Usuario;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class InstituicaoController extends Controller {
	@SuppressWarnings("deprecation")
	public Result getAll() {

		ObjectNode jsResp = Json.newObject();
		try {
			List<Instituicao> instituicoes = Instituicao.getAllAtivo();
			jsResp.put("status", 0);
			jsResp.put("message", "Busca realizada com sucesso!");
			jsResp.put("listaInstituicoes", Json.toJson(instituicoes));
		} catch (Exception e) {
			jsResp.put("status", 1);
			jsResp.put("message", "Falha ao busca lista de insituições!");
		}

		return ok(jsResp);
	}

	public Result cadastroInstituicao() {
		ObjectNode jsResp = Json.newObject();
		JsonNode json = request().body().asJson();

		Instituicao instituicao = new Instituicao();
		instituicao.setNome(json.findValue("nome").asText());
		instituicao.setEmail(json.findValue("email").asText());
		instituicao.setEndereco(json.findValue("endereco").asText());
		instituicao.setSenha(json.findValue("senha").asText());
		instituicao.setTelefone(json.findValue("telefone").asText());
		instituicao.setCpfRepresentante(json.findValue("cpfResponsavel").asText());
		instituicao.setNomeRepresentante(json.findValue("nomeResponsavel").asText());

		try {
			instituicao.save();
			Usuario newUser = new  Usuario();
			newUser.setLogin(instituicao.getEmail());
			newUser.setTipo(0);
			newUser.setDescricao("Instituição de Ensino");
			newUser.save();
			jsResp.put("status", 0);
			jsResp.put("message", "Instituição cadastrada com sucesso!");
		} catch (Exception e) {
			jsResp.put("status", 1);
			jsResp.put("message", "Erro ao efetuar do cadastro");
		}
		return ok(jsResp);
	}
}
