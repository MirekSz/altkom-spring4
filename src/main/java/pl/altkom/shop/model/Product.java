package pl.altkom.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import pl.altkom.shop.FileSize;

@Entity
@Indexed
public class Product implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@NotEmpty
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
	private String name;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	private String description;
	@NotNull
	private Integer quantity;
	@NotNull
	private BigDecimal price;
	private String imgLocation;
	@Transient
	@XmlTransient
	@FileSize(size = 100)
	private MultipartFile file;

	public Product(String name, String description, int quentity, BigDecimal price) {
		this.name = name;
		this.description = description;
		this.setQuantity(quentity);
		this.price = price;
	}

	public Product() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getImgLocation() {
		return imgLocation;
	}

	public void setImgLocation(String imgLocation) {
		this.imgLocation = imgLocation;
	}

	@XmlTransient
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
