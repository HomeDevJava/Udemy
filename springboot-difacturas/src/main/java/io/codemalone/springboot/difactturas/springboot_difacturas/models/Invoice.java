package io.codemalone.springboot.difactturas.springboot_difacturas.models;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
@RequestScope
@JsonIgnoreProperties({"targetSource","advisors"})
public class Invoice {

    @Autowired
    private Client client;

    @Value("${invoice.description.office}")
    private String description;

    @Autowired
    private List<Item> items;

    @PostConstruct
    public void init(){
        System.out.println("init->Generating Invoice");
        client.setName(client.getName()+" Said");
        description = description.concat(String.format(" del cliente %s",client.getName()));
    }

    /* @PreDestroy se utiliza para  realizar otras operaciones antes de que se destruya el contexto
     * como por ejemplo cerrar la base de datos o liberar recursos
     */
    @PreDestroy
    public void destroy(){
        System.out.println("destroy->Destroying Invoice");
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Double getInvoiceTotal() {
        /*
         * double total = items.stream()
         * .map(Item::getImporte)
         * .reduce(0.0, (sum, current) -> sum + current);
         */

        /*
         * for (Item item : items) {
         * total += item.getImporte();
         * }
         */
        double t = items.stream().mapToDouble(Item::getImporte).sum();
        return t;
    }
}
