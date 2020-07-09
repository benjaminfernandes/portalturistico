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
import br.gov.pr.guaira.portalturistico.model.TipoHospedagem;
import br.gov.pr.guaira.portalturistico.repository.Hospedagens;
import br.gov.pr.guaira.portalturistico.repository.filter.HospedagemFilter;
import br.gov.pr.guaira.portalturistico.service.HospedagemService;
import br.gov.pr.guaira.portalturistico.service.exception.EntidadeJaExistenteException;
import br.gov.pr.guaira.portalturistico.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/hospedagens")
public class HospedagemController {
	
	@Autowired
	private HospedagemService hospedagemService;
	@Autowired
	private Hospedagens hospedagens;

	@RequestMapping("/novo")
	public ModelAndView novo(Hospedagem hospedagem) {
		ModelAndView mv = new ModelAndView("hospedagem/CadastroHospedagem");
		mv.addObject("tiposHospedagens", TipoHospedagem.values());
		return mv;
	}
	
	@PostMapping(value = {"/novo", "{\\d+}"})
	public ModelAndView salvar(@Valid Hospedagem hospedagem, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return novo(hospedagem);
		}
		try {
			this.hospedagemService.salvar(hospedagem);
		}catch (EntidadeJaExistenteException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(hospedagem);
		}
		attributes.addFlashAttribute("mensagem", "Salvo com sucesso!");
		return new ModelAndView("redirect:/hospedagens/novo");
	}
	
	@GetMapping
	public ModelAndView pesquisar(HospedagemFilter hospedagemFilter, BindingResult result,@PageableDefault(size=10) Pageable pageable, 
			 HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("hospedagem/PesquisaHospedagens");		
		mv.addObject("tiposHospedagens", TipoHospedagem.values());		
		PageWrapper<Hospedagem> paginaWrapper = new PageWrapper<>(hospedagens.filtrar(hospedagemFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}

	@DeleteMapping("/{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Hospedagem hospedagem){
		try {
			hospedagemService.excluir(hospedagem);
		}catch(ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build(); 
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Hospedagem hospedagem) {
		ModelAndView mv = novo(hospedagem);
		mv.addObject(hospedagem);
		return mv;
	}
}
