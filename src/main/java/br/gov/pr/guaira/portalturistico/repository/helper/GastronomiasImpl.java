package br.gov.pr.guaira.portalturistico.repository.helper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.gov.pr.guaira.portalturistico.model.Gastronomia;
import br.gov.pr.guaira.portalturistico.model.TipoGastronomia;
import br.gov.pr.guaira.portalturistico.repository.filter.GastronomiaFilter;
import br.gov.pr.guaira.portalturistico.repository.paginacao.PaginacaoUtil;

public class GastronomiasImpl implements GastronomiasQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Gastronomia> filtrar(GastronomiaFilter filtro, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Gastronomia> query = builder.createQuery(Gastronomia.class);
		Root<Gastronomia> rootGastronomia = query.from(Gastronomia.class);
		
		Predicate[] predicates = adicionafiltros(filtro, builder, query, rootGastronomia);
		
		query.select(rootGastronomia).where(predicates);
		
		TypedQuery<Gastronomia> typedQuery =  (TypedQuery<Gastronomia>) paginacaoUtil.prepararOrdem(query, rootGastronomia, pageable);
		typedQuery = (TypedQuery<Gastronomia>) paginacaoUtil.prepararIntervalo(typedQuery, pageable);
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(filtro));
	}

	@Override
	@Transactional(readOnly = true)
	public Gastronomia buscaComTipos(Long codigo) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Gastronomia> query = builder.createQuery(Gastronomia.class);
		Root<Gastronomia> gastronomiaEntity = query.from(Gastronomia.class);
		gastronomiaEntity.fetch("tiposGastronomias", JoinType.LEFT);
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (codigo != null) {
			predicates.add(
					builder.equal(gastronomiaEntity.get("codigo"), codigo));
		}

		query.select(gastronomiaEntity);
		query.where(predicates.toArray(new Predicate[0]));
		query.orderBy(builder.asc(gastronomiaEntity.get("codigo")));

		TypedQuery<Gastronomia> typeQuery = manager.createQuery(query);
		return typeQuery.getSingleResult();
	}
	
	private Long total(GastronomiaFilter filtro) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<Gastronomia> gastronomiaEntity = query.from(Gastronomia.class);
		
		query.select(criteriaBuilder.count(gastronomiaEntity));
		
		query.where(adicionafiltros(filtro,criteriaBuilder, query, gastronomiaEntity));
		
		return manager.createQuery(query).getSingleResult();
	}
	

	private Predicate[] adicionafiltros(GastronomiaFilter filtro, CriteriaBuilder builder,
			CriteriaQuery<?> query, Root<Gastronomia> rootGastronomia) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (filtro != null) {
			
			if (!StringUtils.isEmpty(filtro.getNome())) {
				predicates.add(builder.like(rootGastronomia.get("nome"), "%"+filtro.getNome()+"%"));
			}
			
			if(filtro.getTiposGastronomias() != null && !filtro.getTiposGastronomias().isEmpty()) {
				
				for(Long codigoGrupo : filtro.getTiposGastronomias().stream().mapToLong(TipoGastronomia::getCodigo).toArray()) {
				Subquery<TipoGastronomia> subquery = query.subquery(TipoGastronomia.class);	
				Root<Gastronomia> subqueryRoot = subquery.correlate(rootGastronomia);
				Join<Gastronomia, TipoGastronomia> tipoGastronomiaRoot = subqueryRoot.join("tiposGastronomias", JoinType.LEFT);
				subquery.select(tipoGastronomiaRoot).where(builder.equal(tipoGastronomiaRoot.get("codigo"), codigoGrupo));
				
				predicates.add(builder.exists(subquery));
				}
			}
			
			if(filtro.getTipoGastronomia() != null) {
				
					Subquery<TipoGastronomia> subquery = query.subquery(TipoGastronomia.class);	
					Root<Gastronomia> subqueryRoot = subquery.correlate(rootGastronomia);
					Join<Gastronomia, TipoGastronomia> tipoGastronomiaRoot = subqueryRoot.join("tiposGastronomias", JoinType.LEFT);
					subquery.select(tipoGastronomiaRoot).where(builder.equal(tipoGastronomiaRoot.get("codigo"), 
						filtro.getTipoGastronomia().getCodigo()));
					
					predicates.add(builder.exists(subquery));
				
			}
			
		}
		return predicates.toArray(new Predicate[0]);
	}
}
