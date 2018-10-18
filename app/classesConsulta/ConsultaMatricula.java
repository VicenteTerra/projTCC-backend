package classesConsulta;

import play.libs.ws.WSClient;
import responses.ConsultaResponse;

public interface ConsultaMatricula {

	public ConsultaResponse obterStatusMatricula(String mat, WSClient ws);
	public ConsultaResponse obterStatusMatricula(String mat);

}
