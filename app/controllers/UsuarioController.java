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

public class UsuarioController extends Controller {

	public Result userAuth() {
		ObjectNode jsResp = Json.newObject();
		JsonNode json = request().body().asJson();
		String email = json.findValue("userName").asText();
		String senha = json.findValue("password").asText();
		Usuario user = Usuario.getUserByLogin(email);
		
		if (user != null) {
			if (user.getTipo() == 0) {
				Instituicao inst = Instituicao.findByEmailSenha(email, senha);
				if (inst != null) {
					jsResp.put("status", 0);
					jsResp.put("message", "ok");
					jsResp.put("userName", inst.getNome());
					jsResp.put("userId", inst.getId());
					jsResp.put("userType", user.getTipo());
				} else {
					jsResp.put("status", 1);
					jsResp.put("message", "Usuário e senha não conferem.");
				}
			}
			if (user.getTipo() == 1) {
				Estabelecimento estab = Estabelecimento.findByEmailSenha(email, senha);
				if (estab != null) {
					jsResp.put("status", 0);
					jsResp.put("message", "ok");
					jsResp.put("userName", estab.getNome());
					jsResp.put("userId", estab.getId());
					jsResp.put("userType", user.getTipo());
				} else {
					jsResp.put("status", 1);
					jsResp.put("message", "Usuário e senha não conferem.");
				}
			}
			if (user.getTipo() == 2) {
				Aluno aluno = Aluno.findByEmailSenha(email, senha);
				if (aluno != null) {
					jsResp.put("status", 0);
					jsResp.put("message", "ok");
					jsResp.put("userName", aluno.getNome());
					jsResp.put("userId", aluno.getId());
					jsResp.put("userType", user.getTipo());
				} else {
					jsResp.put("status", 1);
					jsResp.put("message", "Usuário e senha não conferem.");
				}
			}
		} else {
			jsResp.put("status", 1);
			jsResp.put("message", "Usuário não encontrado");
		}

		return ok(jsResp);
	}
	
	public Result logout(){
		return ok();
	}

}
