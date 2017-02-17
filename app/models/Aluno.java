package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.avaje.ebean.Finder;
import com.avaje.ebean.Model;

@Entity
public class Aluno extends Model {

	public static Finder<Long, Aluno> find = new Finder<Long, Aluno>(Aluno.class);

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "cpf", unique = true)
	private String cpf;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "instituicao")
	private String instituicao;

	@Column(name = "data_nascimento")
	private String dataNascimento;

	@Column(name = "telefone")
	private String telefone;

	@Column(name = "")
	@ManyToMany
	private List<Estabelecimento> listaEstabelecimentos;
	
	@Column(name = "senha")
	private String senha;
	
	@Column(name = "ativo")
	private Integer ativo = 1;
	
	
	
	public Aluno() {
		super();
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public List<Estabelecimento> getListaEstabelecimentos() {
		return listaEstabelecimentos;
	}

	public void setListaEstabelecimentos(List<Estabelecimento> listaEstabelecimentos) {
		this.listaEstabelecimentos = listaEstabelecimentos;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public static List<Aluno> findAll() {
		return find.where().eq("ativo", 1).findList();
	}
	
	public static Aluno findByEmailSenha(String email, String senha){
		return find.where().eq("email", email).eq("senha", senha).eq("ativo", 1).findUnique();
	}

}
