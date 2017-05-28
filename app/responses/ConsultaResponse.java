package responses;

public class ConsultaResponse {
	
	private String nome;
	private String matricula;
	private String cpf;
	private String situação;
	private String instituicao;
	
	public ConsultaResponse(String nome, String matricula, String cpf, String situação, String instituicao) {
		super();
		this.nome = nome;
		this.matricula = matricula;
		this.cpf = cpf;
		this.situação = situação;
		this.instituicao = instituicao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSituação() {
		return situação;
	}

	public void setSituação(String situação) {
		this.situação = situação;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}
}
