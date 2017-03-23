package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Estabelecimento;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class EstabelecimentoController extends Controller{

	public Result getAll(){
		return ok(Json.toJson(Estabelecimento.findAll()));
	}
	
	public Result cadastroEstabelecimento(){
		
		ObjectNode jsResp = Json.newObject();
		JsonNode json = request().body().asJson();
		
		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setNome(json.findValue("nome").asText());
		estabelecimento.setCnpj(json.findValue("cnpj").asText());
		estabelecimento.setEmail(json.findValue("email").asText());
		estabelecimento.setEndereco(json.findValue("endereco").asText());
		estabelecimento.setSenha(json.findValue("senha").asText());
		estabelecimento.setTelefone(json.findValue("telefone").asText());
		estabelecimento.setTipoUsuario(2);
		estabelecimento.setCpfResponsavel(json.findValue("cpfResponsavel").asText());
		estabelecimento.setNomeResponsavel(json.findValue("nomeResponsavel").asText());
		if(json.findValue("tipoEstabelecimento").asText().equals("Cinema/Teatro"))
		estabelecimento.setTipoEtabelecimento(1);
		if(json.findValue("tipoEstabelecimento").asText().equals("Shows/Eventos"))
			estabelecimento.setTipoEtabelecimento(2);
		if(json.findValue("tipoEstabelecimento").asText().equals("Transporte"))
			estabelecimento.setTipoEtabelecimento(3);
		
		try {
			estabelecimento.save();
			jsResp.put("status", 0);
			jsResp.put("message", "Cadastrado com sucesso!");
		} catch (Exception e) {
			jsResp.put("status", 1);
			jsResp.put("message", "Erro no cadastro:  " + e.getMessage());
		}
		return ok(jsResp);
	}
}
