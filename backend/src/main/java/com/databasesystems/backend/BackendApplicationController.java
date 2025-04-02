import org.springframework.boot.RequestMapping;
import org.springframework.boot.RestController;

@RestController
public class BackendApplicationController {

    @RequestMapping("/hello")
    public String hello(){
        return "Hello World";
    }
}
