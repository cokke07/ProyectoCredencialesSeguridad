package cl.cokke.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {

	@GetMapping("/")
	public RedirectView home() {
		return new RedirectView("/login");
	}
	
	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
}
