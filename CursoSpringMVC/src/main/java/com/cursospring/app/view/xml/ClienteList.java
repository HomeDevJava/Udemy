package com.cursospring.app.view.xml;
//esta clase es un wraper ya que a diferencia de JSON, XML no puede conevrtir arraylist
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.cursospring.app.models.entity.Cliente;

@XmlRootElement(name="clientes")
public class ClienteList {

	@XmlElement(name="cliente")
	public List<Cliente> clientes;

	public ClienteList() {}

	public ClienteList(List<Cliente> clientes) {		
		this.clientes = clientes;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}	
	
}
