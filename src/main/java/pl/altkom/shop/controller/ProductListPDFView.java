package pl.altkom.shop.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

import pl.altkom.shop.model.Product;

@Component(ProductListPDFView.VIEW_NAME)
public class ProductListPDFView extends AbstractPdfView {
	public static final String VIEW_NAME = "ProductListPDFView";

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Product> products = (List<Product>) model.get("products");
		Table table = new Table(2);
		table.addCell("Name");
		table.addCell("Cena");
		for (Product product : products) {
			table.addCell(product.getName());
			table.addCell(product.getPrice().toString());
		}
		document.add(table);
	}

}
