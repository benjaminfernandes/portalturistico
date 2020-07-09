package br.gov.pr.guaira.portalturistico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LocaisTuristicosController {

	@RequestMapping("/locais")
	public String layout() {
		return "index";
	}
}
