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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.gov.pr.guaira.portalturistico.controller.page.PageWrapper;
import br.gov.pr.guaira.portalturistico.model.Gastronomia;
import br.gov.pr.guaira.portalturistico.repository.Gastronomias;
import br.gov.pr.guaira.portalturistico.repository.TiposGastronomias;
import br.gov.pr.guaira.portalturistico.repository.filter.GastronomiaFilter;
import br.gov.pr.guaira.portalturistico.service.GastronomiaService;
import br.gov.pr.guaira.portalturistico.service.exception.EntidadeJaExistenteException;
import br.gov.pr.guaira.portalturistico.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/gastronomias")
public class GastronomiaController {
	
	@Autowired
	private TiposGastronomias tipos;
	@Autowired
	private GastronomiaService gastronomiaService;
	@Autowired
	private Gastronomias gastronomias;

	@GetMapping("/novo")
	public ModelAndView novo(Gastronomia gastronomia) {
		ModelAndView mv = new ModelAndView("gastronomia/CadastroGastronomia");
		mv.addObject("tipos", this.tipos.findAll());
		return mv;
	}
	
	@PostMapping(value = {"/novo", "{\\d+}"})
	public ModelAndView salvar(@Valid Gastronomia gastronomia, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(gastronomia);
		}
		try {
			this.gastronomiaService.salvar(gastronomia);
		}catch (EntidadeJaExistenteException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(gastronomia);
		}
		
		attributes.addFlashAttribute("mensagem", "Salvo com sucesso!");
		return new ModelAndView("redirect:/gastronomias/novo");
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Gastronomia gastronomia){
		try {
			gastronomiaService.excluir(gastronomia);
		}catch(ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build(); 
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Long codigo) {
		Gastronomia gastronomia = this.gastronomias.buscaComTipos(codigo);
		ModelAndView mv = novo(gastronomia);
		mv.addObject("gastronomia", gastronomia);
		
		return mv;
	}
	
	@GetMapping
	public ModelAndView pesquisar(GastronomiaFilter gastronomiaFilter, BindingResult result,@PageableDefault(size=10) Pageable pageable, 
			 HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("gastronomia/PesquisaGastronomia");
		mv.addObject("tipos", this.tipos.findAll());		
		PageWrapper<Gastronomia> paginaWrapper = new PageWrapper<>(gastronomias.filtrar(gastronomiaFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
}
