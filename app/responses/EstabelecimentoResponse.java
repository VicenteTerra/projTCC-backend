package responses;

import models.AutorizaEstabelecimento;
import models.Estabelecimento;

public class EstabelecimentoResponse {

	private Integer id;
	private String nome;
	private String cnpj;
	private String email;
	private String nomeResponsavel;
	private String cpfResponsavel;
	private String telefone;
	private String endereco;

	private AutorizaEstabelecimento autorizacao;

	public EstabelecimentoResponse(AutorizaEstabelecimento auto, Estabelecimento estab) {
		this.id = estab.getId();
		this.nome = estab.getNome();
		this.cnpj = estab.getCnpj();
		this.email = estab.getEmail();
		this.nomeResponsavel = estab.getNomeResponsavel();
		this.cpfResponsavel = estab.getCpfResponsavel();
		this.telefone = estab.getTelefone();
		this.endereco = estab.getEndereco();
		this.autorizacao = auto;
	}

	public EstabelecimentoResponse(Estabelecimento estab) {
		this.id = estab.getId();
		this.nome = estab.getNome();
		this.cnpj = estab.getCnpj();
		this.email = estab.getEmail();
		this.nomeResponsavel = estab.getNomeResponsavel();
		this.cpfResponsavel = estab.getCpfResponsavel();
		this.telefone = estab.getTelefone();
		this.endereco = estab.getEndereco();
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

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getCpfResponsavel() {
		return cpfResponsavel;
	}

	public void setCpfResponsavel(String cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public AutorizaEstabelecimento getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(AutorizaEstabelecimento autorizacao) {
		this.autorizacao = autorizacao;
	}

}
