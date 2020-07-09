package br.gov.pr.guaira.portalturistico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.gov.pr.guaira.portalturistico.model.Evento;
import br.gov.pr.guaira.portalturistico.service.EventoService;

@Controller
@RequestMapping("/eventos")
public class EventosController {

	@Autowired
	private EventoService eventoService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Evento evento) {
		ModelAndView mv = new ModelAndView("evento/CadastroEvento");
		
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public void cadastrar(Evento evento) {
		
		this.eventoService.salvar(evento);
		
	}
	
}
