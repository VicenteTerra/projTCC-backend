package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Usuario;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class UsuarioController extends Controller {

	public Result getUserType() {
		ObjectNode jsResp = Json.newObject();
		JsonNode json = request().body().asJson();

		Usuario user = Usuario.getUserByLogin(json.findValue("login").asText());

		if (user != null) {
			jsResp.put("status", 0);
			jsResp.put("message", "Usuario encontrado.");
			jsResp.put("tipoUsuario", user.getTipo());
		} else {
			jsResp.put("status", 1);
			jsResp.put("message", "Usuário não encontrado");
		}

		return ok(jsResp);
	}

}
