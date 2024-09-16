package com.cursospring.app.view.xml;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.cursospring.app.models.entity.Cliente;

@Component("listar3.xml")
public class ClienteListXmlView extends MarshallingView{
	
	
	@Autowired
	public ClienteListXmlView(Jaxb2Marshaller marshaller) {
		super(marshaller);		
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//removemos lo que no se renderizara a XML
		model.remove("titulo");
		model.remove("page");
		
		//antes de remover la lista de clientes la obtenemos y la pasamos a un objeto
		Page<Cliente> clientes= (Page<Cliente>) model.get("lista");
		
		//removemos la lista de clientes
		model.remove("lista");
		
		//colocamos en el modelo la nueva lista de clientes, ojo hay que utilizar el wraper ClienteList
		model.put("clienteList",new ClienteList(clientes.getContent()));
		
		super.renderMergedOutputModel(model, request, response);
	}
}
