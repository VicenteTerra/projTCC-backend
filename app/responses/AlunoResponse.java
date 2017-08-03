package responses;

import models.Aluno;
import models.Instituicao;

public class AlunoResponse {
	private Integer id;
	private String nome;
	private String matricula;
	private String instituicaoNome;
	private Integer instituicaoId;
	private Integer ativo;

	public AlunoResponse(Aluno aluno, Instituicao instituicao) {
		this.id = aluno.getId();
		this.nome = aluno.getNome();
		this.matricula = aluno.getMatricula();
		this.instituicaoNome = instituicao.getNome();
		this.instituicaoId = instituicao.getId();
		this.ativo = aluno.getAtivo();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getInstituicaoNome() {
		return instituicaoNome;
	}

	public void setInstituicaoNome(String instituicaoNome) {
		this.instituicaoNome = instituicaoNome;
	}

	public Integer getInstituicaoId() {
		return instituicaoId;
	}

	public void setInstituicaoId(Integer instituicaoId) {
		this.instituicaoId = instituicaoId;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

}
