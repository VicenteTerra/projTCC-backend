package controllers;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.AutorizaEstabelecimento;
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

	public Result updateEstabelecimento() {
		ObjectNode jsResp = Json.newObject();
		JsonNode json = request().body().asJson();
		Estabelecimento estabelecimento = Estabelecimento.findById(json.findValue("id").asInt());

		estabelecimento.setNome(json.findValue("nome").asText());
		estabelecimento.setCnpj(json.findValue("cnpj").asText());
		estabelecimento.setEmail(json.findValue("email").asText());
		estabelecimento.setEndereco(json.findValue("endereco").asText());
		estabelecimento.setTelefone(json.findValue("telefone").asText());
		estabelecimento.setCpfResponsavel(json.findValue("cpfResponsavel").asText());
		estabelecimento.setNomeResponsavel(json.findValue("nomeResponsavel").asText());
		estabelecimento.update();

		JsonNode files = json.findValue("files");
		if (files != null && files.size() > 0) {
			try {
				for (JsonNode js : files) {
					Documento newDoc = new Documento();
					newDoc.setBase64(js.findValue("base64").asText());
					newDoc.setFilename(js.findValue("filename").asText());
					newDoc.setFiletype(js.findValue("filetype").asText());
					newDoc.setFilesize(js.findValue("filesize").asInt());
					newDoc.setOwnerID(estabelecimento.getId());

					byte[] data = Base64.getDecoder().decode(newDoc.getBase64().getBytes());
					if (!Files.exists(Paths.get("/userFiles/" + estabelecimento.getCnpj()))) {
						Files.createDirectory(Paths.get("/userFiles/" + estabelecimento.getCnpj()));
					}
					Path destinationFile = Paths
							.get("/userFiles/" + estabelecimento.getCnpj() + "/" + newDoc.getFilename());
					Files.write(destinationFile, data);
					newDoc.save();
				}
			} catch (Exception e) {
				jsResp.put("status", 1);
				jsResp.put("message" , "Erro interno: Contate o suporte.");
				return ok(jsResp);
			}
		}
		jsResp.put("status", 0);
		jsResp.put("message", "Dados atualizados com sucesso!");
		return ok(jsResp);
	}

	public Result getEstabelecimento(Integer id) {
		ObjectNode jsResp = Json.newObject();
		EstabelecimentoResponse resp = new EstabelecimentoResponse(Estabelecimento.findById(id));
		jsResp.put("status", 0);
		jsResp.put("message", "ok");
		jsResp.put("estabelecimento", Json.toJson(resp));
		return ok(jsResp);
	}

	public Result getStatusCadastroInstituicao(Integer id) {
		ObjectNode jsResp = Json.newObject();
		List<AutorizaEstabelecimento> autorizacoes = AutorizaEstabelecimento.getAutorizacoesByEstabelecimento(id);
		HashMap<String, Object> data;
		List<HashMap<String, Object>> result = new ArrayList<>();

		for (AutorizaEstabelecimento auto : autorizacoes) {
			data = new HashMap<>();
			data.put("estabelecimentoNome", Instituicao.findById(auto.getIdInstituicao()).getNome());
			data.put("statusAutorizacao", auto.getStatus());
			data.put("mensagemAutorizacao", auto.getMensagem());
			result.add(data);
		}

		jsResp.put("status", 0);
		jsResp.put("message", "ok");
		jsResp.put("autorizacoes", Json.toJson(result));
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
			List<Instituicao> instituicoes = Instituicao.find.all();
			for (Instituicao inst : instituicoes) {
				AutorizaEstabelecimento auto = new AutorizaEstabelecimento();
				auto.setIdEstabelecimento(estabelecimento.getId());
				auto.setIdInstituicao(inst.getId());
				auto.save();
			}
			newUser.setLogin(estabelecimento.getEmail());
			newUser.setTipo(1);
			newUser.setDescricao("Estabelecimento");
			newUser.save();
		} catch (Exception e) {
			jsResp.put("status", 1);
			jsResp.put("message", e.getMessage());
			return ok(jsResp);
		}

		JsonNode files = json.findValue("files");
		if (files.size() > 0) {
			try {
				for (JsonNode js : files) {
					Documento newDoc = new Documento();
					newDoc.setBase64(js.findValue("base64").asText());
					newDoc.setFilename(js.findValue("filename").asText());
					newDoc.setFiletype(js.findValue("filetype").asText());
					newDoc.setFilesize(js.findValue("filesize").asInt());
					newDoc.setOwnerID(estabelecimento.getId());

					byte[] data = Base64.getDecoder().decode(newDoc.getBase64().getBytes());
					if (!Files.exists(Paths.get("/userFiles/" + estabelecimento.getCnpj()))) {
						Files.createDirectory(Paths.get("/userFiles/" + estabelecimento.getCnpj()));
					}
					Path destinationFile = Paths
							.get("/userFiles/" + estabelecimento.getCnpj() + "/" + newDoc.getFilename());
					Files.write(destinationFile, data);
					newDoc.save();
				}
			} catch (Exception e) {
				estabelecimento.delete();
				newUser.delete();
				jsResp.put("status", 1);
				jsResp.put("message", e.getMessage());
				return ok(jsResp);
			}
		}

		jsResp.put("status", 0);
		jsResp.put("message", "Cadastrado com sucesso!");

		return ok(jsResp);
	}
}
