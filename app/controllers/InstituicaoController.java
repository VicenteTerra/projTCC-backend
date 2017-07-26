package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Estabelecimento;
import models.Instituicao;
import models.Usuario;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import responses.EstabelecimentoResponse;

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

	public Result autorizaEstabelecimento(Integer idEstab, Integer idInst) {
		Instituicao inst = Instituicao.findById(idInst);
		Estabelecimento estab = Estabelecimento.findById(idEstab);
		inst.getEstabelecimentoCredenciados().add(estab);
		inst.update();
		return ok();
	}

	public Result desautorizaEstabelecimento(Integer idEstab, Integer idInst) {
		Instituicao inst = Instituicao.findById(idInst);
		Estabelecimento estab = Estabelecimento.findById(idEstab);
		inst.getEstabelecimentoCredenciados().remove(estab);
		inst.update();
		return ok();
	}

	public Result listFilesEstabelecimento(String cnpj) {
		ObjectNode jsResp = Json.newObject();

		List<String> results = new ArrayList<String>();
		File[] files = new File("/home/vicente/userFiles/" + cnpj).listFiles();
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
			File fileToDownload = new File("/home/vicente/userFiles/" + cnpj + "/" + fileName);
			return ok(fileToDownload);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok();
	}

	public Result getEstabelecimentosInst(Integer id) {
		ObjectNode jsResp = Json.newObject();
		List<Estabelecimento> estabelecimentos = Estabelecimento.findAll();
		Instituicao instituicao = Instituicao.findById(id);
		List<EstabelecimentoResponse> respList = new ArrayList<>();
		if (instituicao.getEstabelecimentoCredenciados().size() == 0) {
			for (Estabelecimento estab2 : estabelecimentos) {
				EstabelecimentoResponse resp = new EstabelecimentoResponse(false, estab2);
				respList.add(resp);
			}
		} else {

			for (Estabelecimento estab2 : estabelecimentos) {
				for (Estabelecimento estab1 : instituicao.getEstabelecimentoCredenciados()) {
					if (estab2.getId() == estab1.getId()) {
						EstabelecimentoResponse resp = new EstabelecimentoResponse(true, estab2);
						respList.add(resp);
					}
				}
			}

			if (respList.size() < estabelecimentos.size())
				for (Estabelecimento estab2 : estabelecimentos) {
					for (Estabelecimento estab1 : instituicao.getEstabelecimentoCredenciados()) {
						if (estab2.getId() != estab1.getId()) {
							EstabelecimentoResponse resp = new EstabelecimentoResponse(false, estab2);
							respList.add(resp);
						}
					}
				}
		}
		jsResp.put("status", 0);
		jsResp.put("message", "ok");
		jsResp.put("listaEstabelecimentos", Json.toJson(respList));
		return ok(jsResp);
	}
}
