package com.cursospring.app.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import com.cursospring.app.models.entity.Factura;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView{

	//para traducir
	@Autowired private MessageSource messageSource;
	@Autowired private LocaleResolver localeResolver; 
	
	private PdfPCell cell=null;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Factura factura=(Factura) model.get("factura");
		Locale locale= localeResolver.resolveLocale(request);
		
		MessageSourceAccessor msj=getMessageSourceAccessor();
		
		PdfPTable tabla= new PdfPTable(1);
		tabla.setSpacingAfter(20);		
		
		//PdfPCell cell=null;
		cell=new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datoscliente", null, locale)));
		cell.setBackgroundColor(new Color(137, 183, 251));
		cell.setPadding(8f);
		tabla.addCell(cell);
		
		tabla.addCell(factura.getCliente().getNombre()+" "+ factura.getCliente().getApellidos());
		tabla.addCell(factura.getCliente().getEmail());
		
		PdfPTable tabla2= new PdfPTable(1);
		tabla2.setSpacingAfter(20);
		
		cell=new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datosfactura", null, locale)));
		cell.setBackgroundColor(new Color(137,251,157));
		cell.setPadding(8f);
		
		tabla2.addCell(cell);
		tabla2.addCell(msj.getMessage("text.factura.ver.folio")+": " +factura.getId());
		tabla2.addCell(msj.getMessage("text.factura.ver.descripcion")+": " + factura.getDescripcion());
		tabla2.addCell(msj.getMessage("text.factura.ver.fecha")+": "+factura.getCreateAt());
		
		PdfPTable tabla3= new PdfPTable(4);
		tabla3.setWidths(new float[] {3.5f,1,1,1});
		tabla3.addCell(msj.getMessage("text.factura.ver.item.producto"));
		tabla3.addCell(msj.getMessage("text.factura.ver.item.precio"));
		tabla3.addCell(msj.getMessage("text.factura.ver.item.cantidad"));
		tabla3.addCell(msj.getMessage("text.factura.ver.item.total"));
		
		factura.getItems().forEach(i->{
			tabla3.addCell(i.getProducto().getNombre());
			tabla3.addCell(i.getProducto().getPrecio().toString());
			cell= new PdfPCell(new Phrase(i.getCantidad().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			tabla3.addCell(cell);
			tabla3.addCell(i.calcularImporte().toString());
		});
		
		cell= new PdfPCell(new Phrase("Total: "));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		tabla3.addCell(cell);
		tabla3.addCell(factura.getTotal().toString());
		
		document.setPageSize(PageSize.LETTER);
		document.setMargins(1, 1, 1, 1);
		document.add(tabla);
		document.add(tabla2);
		document.add(tabla3);
	}

}
