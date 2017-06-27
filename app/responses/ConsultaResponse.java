package responses;

public class ConsultaResponse {

	private String nome;
	private String matricula;
	private String cpf;
	private String situacao;
	private String instituicao;
	private String foto;

	public ConsultaResponse(String nome, String matricula, String cpf, String situação, String instituicao,
			String foto) {
		super();
		this.nome = nome;
		this.matricula = matricula;
		this.cpf = cpf;
		this.situacao = situação;
		this.instituicao = instituicao;
		this.foto = foto;
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

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

}
