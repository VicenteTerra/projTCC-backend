package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
public class Usuario extends Model {

	public static Finder<Long, Usuario> find = new Finder<>(Usuario.class);

	@Id
	@Column(name = "id")
	private Integer id;
	@Column(name = "login", unique = true)
	private String login;
	@Column(name = "tipo")
	private Integer tipo;
	@Column(name = "descricao")
	private String descricao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static Usuario getUserByLogin(String login) {
		return find.where().eq("login", login).findUnique();
	}
}
