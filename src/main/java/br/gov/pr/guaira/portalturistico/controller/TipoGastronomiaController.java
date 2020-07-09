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
import br.gov.pr.guaira.portalturistico.model.Hospedagem;
import br.gov.pr.guaira.portalturistico.model.TipoGastronomia;
import br.gov.pr.guaira.portalturistico.repository.TiposGastronomias;
import br.gov.pr.guaira.portalturistico.repository.filter.TipoGastronomiaFilter;
import br.gov.pr.guaira.portalturistico.service.TipoGastronomiaService;
import br.gov.pr.guaira.portalturistico.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/tiposGastronomia")
public class TipoGastronomiaController {

	@Autowired
	private TiposGastronomias tiposGastronomia;
	@Autowired
	private TipoGastronomiaService tipoGastronomiaService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(TipoGastronomia tipoGastronomia) {
		ModelAndView mv = new ModelAndView("tipoGastronomia/CadastroTipoGastronomia");
		
		return mv;
	}
	
	@PostMapping(value = {"/novo", "{\\d+}"})
	public ModelAndView salvar(@Valid TipoGastronomia tipoGastronomia, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(tipoGastronomia);
		}
		
		try {
			tipoGastronomiaService.salvar(tipoGastronomia);
		}catch (ImpossivelExcluirEntidadeException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(tipoGastronomia);
		}
		
		attributes.addFlashAttribute("mensagem", "Salvo com sucesso!");
		return new ModelAndView("redirect:/tiposGastronomia/novo");
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") TipoGastronomia tipoGastronomia) {
		ModelAndView mv = novo(tipoGastronomia);
		mv.addObject(tipoGastronomia);
		return mv;
	}
	
	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") TipoGastronomia tipoGastronomia){
		try {
			tipoGastronomiaService.excluir(tipoGastronomia);
		}catch(ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build(); 
	}
	
	@GetMapping
	public ModelAndView pesquisar(TipoGastronomiaFilter tipoGastronomiaFilter, BindingResult result,@PageableDefault(size=10) Pageable pageable, 
			 HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("tipoGastronomia/PesquisaTiposGastronomia");
		PageWrapper<Hospedagem> paginaWrapper = new PageWrapper<>(tiposGastronomia.filtrar(tipoGastronomiaFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
}
