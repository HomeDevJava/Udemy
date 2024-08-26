package io.codemalone33.springboot.di.springboot_di.providers;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.codemalone33.springboot.di.springboot_di.models.Product;

/*esta clase al ser externa no se declara como @Repository, se debe declarar desde el archivo de configuracion con @Bean 
 * para argregarlo como un bean de spring y se pueda inyectar con Autowired
*/

public class ProductRepositoryJson implements ProductoRepository {

    private List<Product> data;

    public ProductRepositoryJson() {
        // 1a. Forma.-se obtiene el resource json de la carpeta json de manera programatica con un ClassPathResource
        ClassPathResource resource = new ClassPathResource("json/product.json");
        readValueJson(resource);
    }

    public ProductRepositoryJson(Resource resource) {
        // 2a. Forma.-se obtiene el resource json con anotaciones @Value desde el archivo AppConfig
        readValueJson(resource);
    }

    private void readValueJson(Resource resource) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            data = Arrays.asList(mapper.readValue(resource.getFile(), Product[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> findAll() {
        return this.data;
    }

    @Override
    public Product findById(Long id) {
        return data.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

}
