package clientesConsulta;


import responses.ConsultaResponse;

public interface ConsultaMatricula {
	public ConsultaResponse obterStatusMatricula(String mat);
	public String obterNomeInstituicao();
	
}
