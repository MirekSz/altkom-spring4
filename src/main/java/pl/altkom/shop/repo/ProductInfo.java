package pl.altkom.shop.repo;

public class ProductInfo {
	private String name;

	public ProductInfo(String name) {
		this.name = name;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// @Value("#{target.price + ' ' + target.quantity}")
	// String getDetails();
}
