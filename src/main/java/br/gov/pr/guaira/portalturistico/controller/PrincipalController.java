package br.gov.pr.guaira.portalturistico.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.gov.pr.guaira.portalturistico.controller.page.PageWrapper;
import br.gov.pr.guaira.portalturistico.model.Atrativo;
import br.gov.pr.guaira.portalturistico.model.Gastronomia;
import br.gov.pr.guaira.portalturistico.model.Hospedagem;
import br.gov.pr.guaira.portalturistico.model.TipoHospedagem;
import br.gov.pr.guaira.portalturistico.repository.Atrativos;
import br.gov.pr.guaira.portalturistico.repository.Gastronomias;
import br.gov.pr.guaira.portalturistico.repository.Hospedagens;
import br.gov.pr.guaira.portalturistico.repository.TiposGastronomias;
import br.gov.pr.guaira.portalturistico.repository.filter.AtrativoFilter;
import br.gov.pr.guaira.portalturistico.repository.filter.GastronomiaFilter;
import br.gov.pr.guaira.portalturistico.repository.filter.HospedagemFilter;
import br.gov.pr.guaira.portalturistico.service.AtrativoService;

@Controller
@RequestMapping("/")
public class PrincipalController {

	@Autowired
	private TiposGastronomias tipos;
	@Autowired
	private Gastronomias gastronomias;
	@Autowired
	private Hospedagens hospedagens;
	@Autowired
	private Atrativos atrativos;
	@Autowired
	private AtrativoService atrativoService;
	
	@GetMapping
	public String layout() {
		return "index";
	}
	
	@GetMapping("/OndeComer")
	public ModelAndView listaOndeComer(GastronomiaFilter gastronomiaFilter, BindingResult result,@PageableDefault(size=10) Pageable pageable, 
			 HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("OndeComer");
		mv.addObject("tipos", this.tipos.findAll());		
		PageWrapper<Gastronomia> paginaWrapper = new PageWrapper<>(gastronomias.filtrar(gastronomiaFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@GetMapping("/OndeDormir")
	public ModelAndView listaPaginaOndeDormir(HospedagemFilter hospedagemFilter, BindingResult result,@PageableDefault(size=10) Pageable pageable, 
			 HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("/OndeDormir");
		
		mv.addObject("tiposHospedagens", TipoHospedagem.values());		
		PageWrapper<Hospedagem> paginaWrapper = new PageWrapper<>(hospedagens.filtrar(hospedagemFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@GetMapping("/ListaAtrativos")
	public ModelAndView listaAtrativos(AtrativoFilter atrativoFilter, BindingResult result,@PageableDefault(size=10) Pageable pageable, 
			 HttpServletRequest httpServletRequest) {
		ModelAndView mv = new ModelAndView("Atrativos");
		
		PageWrapper<Atrativo> paginaWrapper = new PageWrapper<>(atrativos.filtrar(atrativoFilter, pageable), httpServletRequest);
		mv.addObject("pagina", paginaWrapper);
		return mv;
	}
	
	@GetMapping("/ListaAtrativos/{codigo}")
	public ModelAndView detalheAtrativos(@PathVariable("codigo") Atrativo atrativo) {
		ModelAndView mv = new ModelAndView("AtrativoDetalhe");
		mv.addObject("atrativo", atrativo);
		mv.addObject("galeria", this.atrativoService.geraGaleria(atrativo));
		return mv;
	}
	
	@GetMapping("/LocaisTuristicos")
	public ModelAndView listaLocaisTuristicos() {
		ModelAndView mv = new ModelAndView("LocaisTuristicos");
		return mv;
	}
	
	@GetMapping("/Historia")
	public ModelAndView historia() {
		ModelAndView mv = new ModelAndView("Historia");
		return mv;
	}
	
}
