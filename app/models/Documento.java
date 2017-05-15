package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

/**
 * @author vicente
 *
 */
@Entity(name = "documento")
public class Documento extends Model{
	
	public static Finder<Long,  Documento> find = new Finder<>(Documento.class);
	
	@Id
	private Integer id;
	@Column(name = "owner_id")
	private Integer ownerID;
	@Column(name = "base64_string" , columnDefinition = "longtext")
	private String base64;
	@Column(name = "file_name")
	private String filename;
	@Column(name = "file_type")
	private String filetype;
	@Column(name = "file_size")
	private Integer filesize;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOwnerID() {
		return ownerID;
	}
	public void setOwnerID(Integer ownerID) {
		this.ownerID = ownerID;
	}
	public String getBase64() {
		return base64;
	}
	public void setBase64(String base64) {
		this.base64 = base64;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public Integer getFilesize() {
		return filesize;
	}
	public void setFilesize(Integer filesize) {
		this.filesize = filesize;
	}
	
}
