package clientesConsulta;

public class ConsultaUFLA implements ConsultaMatricula {
	public String obterStatusMatricula() {
		return "Deu certo: UFLA";
	}

	@Override
	public String obterNomeInstituicao() {
		return "UFLA";
	}
}
