package com.cursospring.app.view.xlsx;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.cursospring.app.models.entity.Factura;
import com.cursospring.app.models.entity.ItemFactura;

@Component("factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//cambiar el nombre del xlsx
		response.setHeader("Content-Disposition", "attachment; filename=\"Factura_view.xlsx\"");
		
		Factura factura= (Factura) model.get("factura");
		MessageSourceAccessor msj=getMessageSourceAccessor();
		Sheet sheet= workbook.createSheet("Factura Spring");
		
		Row row=sheet.createRow(0);
		Cell cell= row.createCell(0);		
		cell.setCellValue(msj.getMessage("text.factura.ver.datoscliente"));
		
		row=sheet.createRow(1);
		cell=row.createCell(0);
		cell.setCellValue(factura.getCliente().getNombre()+" "+ factura.getCliente().getApellidos());
		
		row=sheet.createRow(2);
		cell=row.createCell(0);
		cell.setCellValue(factura.getCliente().getEmail());
		
		sheet.createRow(4).createCell(0).setCellValue(msj.getMessage("text.factura.ver.datosfactura"));
		sheet.createRow(5).createCell(0).setCellValue(msj.getMessage("text.factura.ver.folio")+": "+factura.getId());
		sheet.createRow(6).createCell(0).setCellValue(msj.getMessage("text.factura.ver.descripcion")+": "+factura.getDescripcion());
		sheet.createRow(7).createCell(0).setCellValue(msj.getMessage("text.factura.ver.fecha")+": "+factura.getCreateAt());
		
		//Estilos
		CellStyle theaderStyle= workbook.createCellStyle();
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle tbodyStyle= workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.THIN);
		tbodyStyle.setBorderTop(BorderStyle.THIN);
		tbodyStyle.setBorderLeft(BorderStyle.THIN);
		tbodyStyle.setBorderRight(BorderStyle.THIN);
		
		//Celdas detalle x productos
		Row header= sheet.createRow(9);
		header.createCell(0).setCellValue(msj.getMessage("text.factura.ver.item.producto"));
		header.createCell(1).setCellValue(msj.getMessage("text.factura.ver.item.precio"));
		header.createCell(2).setCellValue(msj.getMessage("text.factura.ver.item.cantidad"));
		header.createCell(3).setCellValue(msj.getMessage("text.factura.ver.item.total"));
		
		header.getCell(0).setCellStyle(theaderStyle);
		header.getCell(1).setCellStyle(theaderStyle);
		header.getCell(2).setCellStyle(theaderStyle);
		header.getCell(3).setCellStyle(theaderStyle);
		
		int rownum=10;
		for (ItemFactura i : factura.getItems()) {
			Row fila= sheet.createRow(rownum ++);
			fila.createCell(0).setCellValue(i.getProducto().getNombre());
			fila.createCell(1).setCellValue(i.getProducto().getPrecio());
			fila.createCell(2).setCellValue(i.getCantidad());
			fila.createCell(3).setCellValue(i.calcularImporte());
			
			fila.getCell(0).setCellStyle(tbodyStyle);
			fila.getCell(1).setCellStyle(tbodyStyle);
			fila.getCell(2).setCellStyle(tbodyStyle);
			fila.getCell(3).setCellStyle(tbodyStyle);
		}
		
		Row filatotal= sheet.createRow(rownum);
		filatotal.createCell(2).setCellValue("Gran Total: ");
		filatotal.createCell(3).setCellValue(factura.getTotal());
		filatotal.getCell(2).setCellStyle(tbodyStyle);
		filatotal.getCell(3).setCellStyle(tbodyStyle);
		
		
	}

}
