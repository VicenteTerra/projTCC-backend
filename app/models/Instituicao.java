package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.avaje.ebean.Model;

@Entity
public class Instituicao extends Model {

	public static Finder<Long, Instituicao> find = new Finder<>(Instituicao.class);

	@Id
	@Column(name = "id")
	private Integer id;
	@Column(name = "ativo")
	private Integer ativo = 1;
	@Column(name = "nome")
	private String nome;
	@Column(name = "endereco")
	private String endereco;
	@Column(name = "email")
	private String email;
	@Column(name = "nome_representante")
	private String nomeRepresentante;
	@Column(name = "cpf_representante")
	private String cpfRepresentante;
	@Column(name = "telefone")
	private String telefone;
	@Column(name = "estabelecimentos_credenciados")
	@ManyToMany
	private List<Estabelecimento> estabelecimentoCredenciados;
	@Column(name = "senha")
	private String senha;
	@Column(name = "classe_consulta")
	private String classeConsulta;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomeRepresentante() {
		return nomeRepresentante;
	}

	public void setNomeRepresentante(String nomeRepresentante) {
		this.nomeRepresentante = nomeRepresentante;
	}

	public String getCpfRepresentante() {
		return cpfRepresentante;
	}

	public void setCpfRepresentante(String cpfRepresentante) {
		this.cpfRepresentante = cpfRepresentante;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public List<Estabelecimento> getEstabelecimentoCredenciados() {
		return estabelecimentoCredenciados;
	}

	public void setEstabelecimentoCredenciados(List<Estabelecimento> estabelecimentoCredenciados) {
		this.estabelecimentoCredenciados = estabelecimentoCredenciados;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public static List<Instituicao> getAllAtivo() {
		return find.where().eq("ativo ", 1).findList();
	}

	public static Instituicao findById(Integer id) {
		return find.where().eq("id", id).eq("ativo", 1).findUnique();
	}

	public String getClasseConsulta() {
		return classeConsulta;
	}

	public void setClasseConsulta(String classeConsulta) {
		this.classeConsulta = classeConsulta;
	}

	public static Instituicao findByEmailSenha(String email, String senha) {
		return find.where().eq("email", email).eq("senha", senha).eq("ativo", 1).findUnique();
	}
}
