package responses;

import models.Instituicao;

public class InstituicaoResponse {

	private Integer id;
	private String nome;

	public InstituicaoResponse(Instituicao inst) {
		this.id = inst.getId();
		this.nome = inst.getNome();
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
}
