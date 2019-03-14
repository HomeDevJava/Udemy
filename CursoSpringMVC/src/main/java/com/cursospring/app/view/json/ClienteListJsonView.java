package com.cursospring.app.view.json;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.cursospring.app.models.entity.Cliente;

@Component("listar3.json")
public class ClienteListJsonView extends MappingJackson2JsonView{
	
	@Override
	protected Object filterModel(Map<String, Object> model) {	
		model.remove("titulo");
		model.remove("page");
		
		@SuppressWarnings("unchecked")
		Page<Cliente> clientes= (Page<Cliente>) model.get("lista");
		model.remove("lista");
		
		model.put("ClienteList", clientes.getContent());
		
		return super.filterModel(model);
	}
	
}
