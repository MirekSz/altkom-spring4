package pl.altkom.shop.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.altkom.shop.model.Product;
import pl.altkom.shop.repo.ProductRepo;
import pl.altkom.shop.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	static File FILE_DIR = new File("c:\\files");

	@Inject
	ProductRepo repo;
	@Inject
	ProductService service;

	@RequestMapping("/list.pdf")
	public String listPdf(Model model) throws Exception {
		List<Product> products = repo.getAll();
		model.addAttribute("products", products);
		return ProductListPDFView.VIEW_NAME;
	}

	@RequestMapping("/list")
	public String list(Model model, @RequestParam(required = false, value = "page") Integer page,
			@RequestParam(required = false, value = "orderBy") String orderBy) throws Exception {

		model.addAttribute("page", page);
		model.addAttribute("orderBy ", orderBy);

		List<Product> products = repo.getAll();
		model.addAttribute("products", products);

		return "product/product-list";
	}

	@RequestMapping("/{id}/delete")
	public String delte(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) throws Exception {
		repo.delete(id);
		return "redirect:/product/list";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String prepareForm(Model model) throws Exception {
		Product product = new Product();
		product.setQuantity(1);
		product.setPrice(BigDecimal.TEN);
		model.addAttribute("product", product);
		return "product/product-form";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String submitForm(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult,
			@RequestParam("file") MultipartFile file) throws Exception {

		String a = null;
		a.toCharArray();

		FileUtils.copyInputStreamToFile(file.getInputStream(), getDestFile(file));

		product.setFileName(file.getOriginalFilename());
		if (bindingResult.hasErrors()) {
			return "product/product-form";
		}

		if (product.getId() != null) {
			repo.update(product);
		} else {
			repo.insert(product);
		}
		return "redirect:/product/list";
	}

	private File getDestFile(MultipartFile file) {
		return new File(FILE_DIR.getAbsolutePath() + File.separator + file.getOriginalFilename());
	}

	@RequestMapping(value = "/{id}/image", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	@ResponseBody
	public byte[] image(@PathVariable("id") Long id) throws Exception {
		Product product = repo.find(id);
		String fileName = product.getFileName();
		File file = new File(FILE_DIR.getAbsolutePath() + File.separator + fileName);

		return FileUtils.readFileToByteArray(file);
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable("id") Long id, Model model) throws Exception {
		Product product = repo.find(id);
		model.addAttribute("product", product);
		return "product/product-form";
	}
}
