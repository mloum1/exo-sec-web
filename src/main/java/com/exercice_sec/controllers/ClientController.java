import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClientController {

    public ClientController() {
        System.out.println("🚀 ClientController loaded");
    }

    @GetMapping("/client/{id}")
    public void recupererClient() {

    }

    @GetMapping("/public/hello")
    public String sayHello() {
        System.out.println("Test ");
        return "Hello buddy";
    }

    @GetMapping("/clients")
    public void recupererClients() {

    }

    @PostMapping("/ajout/client")
    public void ajouterClient() {

    }

    @DeleteMapping("/suppression/client/{id}")
    public void supprimerClient(@PathVariable Long id) {

    }

}
