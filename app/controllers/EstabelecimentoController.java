package controllers;

import models.Estabelecimento;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class EstabelecimentoController extends Controller{

	public Result getAll(){
		return ok(Json.toJson(Estabelecimento.findAll()));
	}
}
