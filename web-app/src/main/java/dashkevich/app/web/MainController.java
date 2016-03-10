package dashkevich.app.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class MainController {

    /**
     * Redirect to main page.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:/main";
    }


    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("pageTitle", "Test task for EPAM");
        model.addAttribute("pageHeading", "MainPage");

        return "index";
    }

}
