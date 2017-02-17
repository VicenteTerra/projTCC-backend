package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Finder;
import com.avaje.ebean.Model;

/**
 * @author vicente
 *
 */
@Entity
public class Estabelecimento extends Model {

	public static Finder<Long, Estabelecimento> find = new Finder<>(Estabelecimento.class);

	@Id
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public static List<Estabelecimento> findAll() {
		return find.all();
	}
}
