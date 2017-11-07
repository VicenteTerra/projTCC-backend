package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity(name = "autoriza_estabelecimento")
public class AutorizaEstabelecimento extends Model {

	public static Finder<Long, AutorizaEstabelecimento> find = new Finder<>(AutorizaEstabelecimento.class);

	@Id
	private Integer id;
	private Integer idInstituicao;
	private Integer idEstabelecimento;
	private Integer status = 0;
	private String mensagem;

	public static List<AutorizaEstabelecimento> getAutorizacoesByEstabelecimento(Integer id) {
		return find.where().eq("idEstabelecimento", id).findList();
	}

	public static List<AutorizaEstabelecimento> getAutorizacoesByInstituicao(Integer id) {
		return find.where().eq("idInstituicao", id).findList();
	}

	public static AutorizaEstabelecimento getAutorizacoesByEstabelecimentoInstituicao(Integer idEstab, Integer idInst) {
		return find.where().eq("idEstabelecimento", idEstab).eq("idInstituicao", idInst).findUnique();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdInstituicao() {
		return idInstituicao;
	}

	public void setIdInstituicao(Integer idInstituicao) {
		this.idInstituicao = idInstituicao;
	}

	public Integer getIdEstabelecimento() {
		return idEstabelecimento;
	}

	public void setIdEstabelecimento(Integer idEstabelecimento) {
		this.idEstabelecimento = idEstabelecimento;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
