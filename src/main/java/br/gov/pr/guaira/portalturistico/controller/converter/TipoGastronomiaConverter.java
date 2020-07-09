package br.gov.pr.guaira.portalturistico.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.gov.pr.guaira.portalturistico.model.TipoGastronomia;

@Component
public class TipoGastronomiaConverter implements Converter<String, TipoGastronomia> {

	@Override
	public TipoGastronomia convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			TipoGastronomia tipo = new TipoGastronomia();
			tipo.setCodigo(Long.valueOf(codigo));
			return tipo;
		}
		return null;
	}

}
