package br.gov.pr.guaira.portalturistico.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.gov.pr.guaira.portalturistico.controller.page.PageWrapper;
import br.gov.pr.guaira.portalturistico.model.Atrativo;
import br.gov.pr.guaira.portalturistico.repository.Atrativos;
import br.gov.pr.guaira.portalturistico.repository.filter.AtrativoFilter;
import br.gov.pr.guaira.portalturistico.service.AtrativoService;
import br.gov.pr.guaira.portalturistico.service.exception.EntidadeJaExistenteException;
import br.gov.pr.guaira.portalturistico.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/atrativos")
public class AtrativoController {
	
	@Autowired
	private Atrativos atrativos;
	@Autowired
	private AtrativoService atrativoService;
	
	@GetMapping("/novo")
	public ModelAndView novo(Atrativo atrativo) {
		ModelAndView mv = new ModelAndView("atrativo/CadastroAtrativo");
		return mv;
	}
	
	@PostMapping(value = {"/novo", "{\\d+}"})
	public ModelAndView salvar(@Valid Atrativo atrativo, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(atrativo);
		}
		try {
			this.atrativoService.salvar(atrativo);
		}catch (EntidadeJaExistenteException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(atrativo);
		}
		attributes.addFlashAttribute("mensagem", "Atrativo salvo com sucesso!");
		return new ModelAndView("redirect:/atrativos/novo");
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Atrativo atrativo) {
		ModelAndView mv = novo(atrativo);
		mv.addObject("atrativo",atrativo);
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> excluir(@PathVariable("codigo") Atrativo atrativo){
		try {
			this.atrativoService.excluir(atrativo);
		}catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ModelAndView pesquisar(AtrativoFilter atrativoFilter, BindingResult result, @PageableDefault(size=10) Pageable pageable, 
			 HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("atrativo/PesquisaAtrativos");
		PageWrapper<Atrativo> paginaWrapper = new PageWrapper<>(this.atrativos.filtrar(atrativoFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
}
