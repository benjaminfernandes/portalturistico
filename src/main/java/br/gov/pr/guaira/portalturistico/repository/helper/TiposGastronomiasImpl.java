package br.gov.pr.guaira.portalturistico.repository.helper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.gov.pr.guaira.portalturistico.model.Hospedagem;
import br.gov.pr.guaira.portalturistico.model.TipoGastronomia;
import br.gov.pr.guaira.portalturistico.repository.filter.TipoGastronomiaFilter;
import br.gov.pr.guaira.portalturistico.repository.paginacao.PaginacaoUtil;

public class TiposGastronomiasImpl implements TiposGastronomiasQueries {
	
	@PersistenceContext
	private EntityManager manager;
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Hospedagem> filtrar(TipoGastronomiaFilter filtro, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<TipoGastronomia> query = builder.createQuery(TipoGastronomia.class);
		Root<TipoGastronomia> tipoGastronomiaEntity = query.from(TipoGastronomia.class);
		Predicate[] filtros = adicionarFiltro(filtro, tipoGastronomiaEntity);

		query.select(tipoGastronomiaEntity).where(filtros);
		
		TypedQuery<Hospedagem> typedQuery =  (TypedQuery<Hospedagem>) paginacaoUtil.prepararOrdem(query, tipoGastronomiaEntity, pageable);
		typedQuery = (TypedQuery<Hospedagem>) paginacaoUtil.prepararIntervalo(typedQuery, pageable);
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(filtro));
	}
	
	private Long total(TipoGastronomiaFilter filtro) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<TipoGastronomia> tipoGastronomiaEntity = query.from(TipoGastronomia.class);
		
		query.select(criteriaBuilder.count(tipoGastronomiaEntity));
		query.where(adicionarFiltro(filtro, tipoGastronomiaEntity));
		
		return manager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] adicionarFiltro(TipoGastronomiaFilter filtro, Root<TipoGastronomia> tipoGastronomiaEntity) {
		List<Predicate> predicateList = new ArrayList<>();
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		if (filtro != null) {
			
			if (!StringUtils.isEmpty(filtro.getNome())) {
				predicateList.add(builder.like(tipoGastronomiaEntity.get("nome"), "%"+filtro.getNome()+"%"));
			}
		}
		Predicate[] predArray = new Predicate[predicateList.size()];
		return predicateList.toArray(predArray);
	}

}
