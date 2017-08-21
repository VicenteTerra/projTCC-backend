package controllers;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Documento;
import models.Estabelecimento;
import models.Instituicao;
import models.Usuario;
import play.libs.Json;
import play.mvc.Controller;

import play.mvc.Result;
import responses.EstabelecimentoResponse;

public class EstabelecimentoController extends Controller {

	public Result getAll() {
		ObjectNode jsResp = Json.newObject();
		jsResp.put("status", 0);
		jsResp.put("message", "ok");
		jsResp.put("listaEstabelecimento", Json.toJson(Estabelecimento.findAll()));
		return ok(jsResp);
	}

	public Result uploadFile() throws FileNotFoundException, IOException {
		JsonNode json = request().body().asJson();

		byte[] data = Base64.getDecoder().decode(json.findValue("base64").asText().getBytes());
		Path destinationFile = Paths.get("/userFiles/" + json.findValue("filename").asText());
		Files.write(destinationFile, data);

		return ok();
	}

	public Result cadastroEstabelecimento() throws IOException {

		ObjectNode jsResp = Json.newObject();
		JsonNode json = request().body().asJson();
		String cnpj = json.findValue("cnpj").asText();

		Estabelecimento estabelecimento = new Estabelecimento();
		Usuario newUser = new Usuario();
		
		estabelecimento.setNome(json.findValue("nome").asText());
		estabelecimento.setCnpj(cnpj);
		estabelecimento.setEmail(json.findValue("email").asText());
		estabelecimento.setEndereco(json.findValue("endereco").asText());
		estabelecimento.setSenha(json.findValue("senha").asText());
		estabelecimento.setTelefone(json.findValue("telefone").asText());
		estabelecimento.setTipoUsuario(2);
		estabelecimento.setCpfResponsavel(json.findValue("cpfResponsavel").asText());
		estabelecimento.setNomeResponsavel(json.findValue("nomeResponsavel").asText());
		if (json.findValue("tipoEstabelecimento").asText().equals("Cinema/Teatro"))
			estabelecimento.setTipoEtabelecimento(1);
		if (json.findValue("tipoEstabelecimento").asText().equals("Shows/Eventos"))
			estabelecimento.setTipoEtabelecimento(2);
		if (json.findValue("tipoEstabelecimento").asText().equals("Transporte"))
			estabelecimento.setTipoEtabelecimento(3);
		try {
			estabelecimento.save();
			newUser.setLogin(estabelecimento.getEmail());
			newUser.setTipo(1);
			newUser.setDescricao("Estabelecimento");
			newUser.save();
		} catch (Exception e) {
			jsResp.put("status", 1);
			jsResp.put("message", e.getMessage());
			return ok(jsResp);
		}

		Estabelecimento estab = Estabelecimento.findByCnpj(cnpj);
		JsonNode files = json.findValue("files");
		try {
			for (JsonNode js : files) {
				Documento newDoc = new Documento();
				newDoc.setBase64(js.findValue("base64").asText());
				newDoc.setFilename(js.findValue("filename").asText());
				newDoc.setFiletype(js.findValue("filetype").asText());
				newDoc.setFilesize(js.findValue("filesize").asInt());
				newDoc.setOwnerID(estab.getId());

				byte[] data = Base64.getDecoder().decode(newDoc.getBase64().getBytes());
				if (!Files.exists(Paths.get("/userFiles/" + estab.getCnpj()))) {
					Files.createDirectory(Paths.get("/userFiles/" + estab.getCnpj()));
				}
				Path destinationFile = Paths.get("/userFiles/" + estab.getCnpj() + "/" + newDoc.getFilename());
				Files.write(destinationFile, data);
				newDoc.save();
			}
		} catch (Exception e) {
			estabelecimento.delete();
			newUser.delete();
			jsResp.put("status", 1);
			jsResp.put("message", "Ocorreu algum problema interno com o sistema! ");
			return ok(jsResp);
		}

		jsResp.put("status", 0);
		jsResp.put("message", "Cadastrado com sucesso!");

		return ok(jsResp);
	}
}
