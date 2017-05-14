package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Documento;
import models.Estabelecimento;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;

public class EstabelecimentoController extends Controller {

	public Result getAll() {
		return ok(Json.toJson(Estabelecimento.findAll()));
	}

	public Result uploadFile() throws FileNotFoundException, IOException {
		JsonNode json = request().body().asJson();

		byte[] data = Base64.getDecoder().decode(json.findValue("base64").asText().getBytes());
		Path destinationFile = Paths.get("/home/vicente/userFiles/" + json.findValue("filename").asText());
		Files.write(destinationFile, data);

		return ok();
	}

	public Result cadastroEstabelecimento() {

		ObjectNode jsResp = Json.newObject();
		JsonNode json = request().body().asJson();
		String cnpj = json.findValue("cnpj").asText();

		Estabelecimento estabelecimento = new Estabelecimento();
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
			Estabelecimento estab = Estabelecimento.findByCnpj(cnpj);
			JsonNode files = json.findValue("files");

			for (JsonNode js : files) {
				Documento newDoc = new Documento();
				newDoc.setBase64(js.findValue("base64").asText());
				newDoc.setFilename(js.findValue("filename").asText());
				newDoc.setFiletype(js.findValue("filetype").asText());
				newDoc.setFilesize(js.findValue("filesize").asInt());
				newDoc.setOwnerID(estab.getId());

				byte[] data = Base64.getDecoder().decode(newDoc.getBase64().getBytes());
				if (Paths.get("/home/vicente/userFiles/" + estab.getCnpj()) == null) {
					Files.createDirectory(Paths.get("/home/vicente/userFiles/" + estab.getCnpj()));
				}
				Path destinationFile = Paths
						.get("/home/vicente/userFiles/" + estab.getCnpj() + "/" + newDoc.getFilename());
				Files.write(destinationFile, data);
				newDoc.save();
			}

			jsResp.put("status", 0);
			jsResp.put("message", "Cadastrado com sucesso!");
		} catch (Exception e) {
			jsResp.put("status", 1);
			jsResp.put("message", "Erro no cadastro:  " + e.getMessage());
		}
		return ok(jsResp);
	}
}
