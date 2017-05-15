package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import com.avaje.ebean.Model;

/**
 * @author vicente
 *
 */
/**
 * @author vicente
 *
 */
@Entity
public class Estabelecimento extends Model {

	public static Finder<Long, Estabelecimento> find = new Finder<>(Estabelecimento.class);

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "cnpj", unique = true)
	private String cnpj;

	@Column(name = "nome_responsavel")
	private String nomeResponsavel;

	@Column(name = "cpf_reponsavel")
	private String cpfResponsavel;

	@Column(name = "telefone")
	private String telefone;

	@Column(name = "endereco")
	private String endereco;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "tipo_estabelecimento")
	private Integer tipoEtabelecimento;

	@Column(name = "tipo_usuario")
	private Integer tipoUsuario;

	@Column(name = "ativo")
	private Integer ativo = 1;

	@Column(name = "senha")
	private String senha;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getTipoEtabelecimento() {
		return tipoEtabelecimento;
	}

	public void setTipoEtabelecimento(Integer tipoEtabelecimento) {
		this.tipoEtabelecimento = tipoEtabelecimento;
	}

	public Integer getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(Integer tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public static List<Estabelecimento> findAll() {
		return find.where().eq("ativo", 1).findList();
	}

	public static Estabelecimento findByEmailSenha(String email, String senha) {
		return find.where().eq("email", email).eq("senha", senha).eq("ativo", 1).findUnique();
	}

	public static Estabelecimento findByCnpj(String cnpj) {
		return find.where().eq("ativo", 1).eq("cnpj", cnpj).findUnique();
	}
}
