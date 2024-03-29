package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.AutorizaEstabelecimento;
import models.Estabelecimento;
import models.Instituicao;
import models.Usuario;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import responses.EstabelecimentoResponse;
import responses.InstituicaoResponse;

public class InstituicaoController extends Controller {
	@SuppressWarnings("deprecation")
	public Result getAll() {

		ObjectNode jsResp = Json.newObject();
		try {
			List<Instituicao> instituicoes = Instituicao.getAllAtivo();
			List<InstituicaoResponse> respList = new ArrayList<>();
			for (Instituicao inst : instituicoes) {
				respList.add(new InstituicaoResponse(inst));
			}
			jsResp.put("status", 0);
			jsResp.put("message", "Busca realizada com sucesso!");
			jsResp.put("listaInstituicoes", Json.toJson(respList));
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
		instituicao.setClasseConsulta(json.findValue("classeConsulta").asText());

		try {
			instituicao.save();
			Usuario newUser = new Usuario();
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

	public Result autorizaEstabelecimento() {
		ObjectNode jsResp = Json.newObject();
		JsonNode json = request().body().asJson();
		Integer idEstab = json.findValue("idEstabelecimento").asInt();
		Integer idInst = json.findValue("idInstituicao").asInt();
		AutorizaEstabelecimento auto = AutorizaEstabelecimento.getAutorizacoesByEstabelecimentoInstituicao(idEstab,
				idInst);
		auto.setStatus(1);
		auto.update();
		jsResp.put("status", 0);
		return ok(jsResp);
	}

	public Result desautorizaEstabelecimento() {
		ObjectNode jsResp = Json.newObject();
		JsonNode json = request().body().asJson();
		Integer idEstab = json.findValue("idEstabelecimento").asInt();
		Integer idInst = json.findValue("idInstituicao").asInt();
		String msg = json.findValue("msg").asText();
		AutorizaEstabelecimento auto = AutorizaEstabelecimento.getAutorizacoesByEstabelecimentoInstituicao(idEstab,
				idInst);
		auto.setStatus(2);
		auto.setMensagem(msg);
		auto.update();
		jsResp.put("status", 0);
		return ok(jsResp);
	}

	public Result listFilesEstabelecimento(String cnpj) {
		ObjectNode jsResp = Json.newObject();

		List<String> results = new ArrayList<String>();
		File[] files = new File("/userFiles/" + cnpj).listFiles();
		for (File file : files) {
			if (file.isFile()) {
				results.add(file.getName());
			}
		}
		jsResp.put("status", 0);
		jsResp.put("data", Json.toJson(results));
		return ok(jsResp);
	}

	public Result downloadDoc(String cnpj, String fileName) {
		ObjectNode jsResp = Json.newObject();
		response().setContentType("application/pdf");
		try {
			File fileToDownload = new File("/userFiles/" + cnpj + "/" + fileName);
			return ok(fileToDownload);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok();
	}

	public Result getEstabelecimentosInst(Integer id) {
		ObjectNode jsResp = Json.newObject();
		List<EstabelecimentoResponse> respList = new ArrayList<>();
		List<AutorizaEstabelecimento> autorizacoes = AutorizaEstabelecimento.getAutorizacoesByInstituicao(id);

		for (AutorizaEstabelecimento auto : autorizacoes) {
			EstabelecimentoResponse resp = new EstabelecimentoResponse(auto,
					Estabelecimento.findById(auto.getIdEstabelecimento()));
			respList.add(resp);
		}

		jsResp.put("status", 0);
		jsResp.put("message", "ok");
		jsResp.put("listaEstabelecimentos", Json.toJson(respList));
		return ok(jsResp);
	}
}
