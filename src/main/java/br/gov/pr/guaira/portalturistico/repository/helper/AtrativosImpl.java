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

import br.gov.pr.guaira.portalturistico.model.Atrativo;
import br.gov.pr.guaira.portalturistico.repository.filter.AtrativoFilter;
import br.gov.pr.guaira.portalturistico.repository.paginacao.PaginacaoUtil;

public class AtrativosImpl implements AtrativosQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Atrativo> filtrar(AtrativoFilter filtro, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Atrativo> query = builder.createQuery(Atrativo.class);
		Root<Atrativo> rootAtrativo = query.from(Atrativo.class);
		
		Predicate[] predicates = adicionarFiltro(filtro, rootAtrativo);
		
		query.select(rootAtrativo).where(predicates);
		
		TypedQuery<Atrativo> typedQuery =  (TypedQuery<Atrativo>) paginacaoUtil.prepararOrdem(query, rootAtrativo, pageable);
		typedQuery = (TypedQuery<Atrativo>) paginacaoUtil.prepararIntervalo(typedQuery, pageable);
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(filtro));
	}

	private Long total(AtrativoFilter filtro) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<Atrativo> atrativoRoot = query.from(Atrativo.class);
		
		query.select(criteriaBuilder.count(atrativoRoot));
		
		query.where(adicionarFiltro(filtro, atrativoRoot));
		
		return manager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] adicionarFiltro(AtrativoFilter filtro, Root<Atrativo> atrativoEntity) {
		List<Predicate> predicateList = new ArrayList<>();
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		if (filtro != null) {
			
			if (!StringUtils.isEmpty(filtro.getNome())) {
				predicateList.add(builder.like(atrativoEntity.get("nome"), "%"+filtro.getNome()+"%"));
			}
		}
		
		Predicate[] predArray = new Predicate[predicateList.size()];
		return predicateList.toArray(predArray);
	}
}
