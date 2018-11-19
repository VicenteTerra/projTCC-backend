package classesConsulta;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import com.fasterxml.jackson.databind.JsonNode;

import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import responses.ConsultaResponse;

public class CONSULTAUFLA implements ConsultaMatricula {

	public ConsultaResponse obterStatusMatricula(String mat, WSClient ws) {

		WSRequest request = ws.url("http://localhost:8777/ufla/consultaMatricula/" + mat);
		CompletionStage<WSResponse> responsePromise = request.get();
		CompletionStage<JsonNode> jsonPromise = responsePromise.thenApply(WSResponse::asJson);
		try {
			if (jsonPromise.toCompletableFuture().get().findValue("matricula") == null) {
				return null;
			}
			return new ConsultaResponse(jsonPromise.toCompletableFuture().get().findValue("nome").asText(),
					jsonPromise.toCompletableFuture().get().findValue("matricula").asText(),
					jsonPromise.toCompletableFuture().get().findValue("cpf").asText(),
					jsonPromise.toCompletableFuture().get().findValue("situacaoMatricula").asText(), "UFLA",
					jsonPromise.toCompletableFuture().get().findValue("foto").asText());
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ConsultaResponse obterStatusMatricula(String mat) {
		return null;
	}


}
