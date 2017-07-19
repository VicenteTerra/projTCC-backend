package responses;

import models.Estabelecimento;

public class EstabelecimentoResponse {

	private Integer id;
	private String nome;
	private String cnpj;
	private String email;
	private boolean autorizado = false;

	public EstabelecimentoResponse(boolean autoriza, Estabelecimento estab) {
		this.id = estab.getId();
		this.nome = estab.getNome();
		this.cnpj = estab.getCnpj();
		this.email = estab.getEmail();
		if (autoriza)
			this.autorizado = true;
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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAutorizado() {
		return autorizado;
	}

	public void setAutorizado(boolean autorizado) {
		this.autorizado = autorizado;
	}
}
