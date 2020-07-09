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
import br.gov.pr.guaira.portalturistico.repository.filter.HospedagemFilter;
import br.gov.pr.guaira.portalturistico.repository.paginacao.PaginacaoUtil;

public class HospedagensImpl implements HospedagensQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Hospedagem> filtrar(HospedagemFilter filtro, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Hospedagem> query = builder.createQuery(Hospedagem.class);
		Root<Hospedagem> hospedagemEntity = query.from(Hospedagem.class);
		Predicate[] filtros = adicionarFiltro(filtro, hospedagemEntity);

		query.select(hospedagemEntity).where(filtros);
		
		TypedQuery<Hospedagem> typedQuery =  (TypedQuery<Hospedagem>) paginacaoUtil.prepararOrdem(query, hospedagemEntity, pageable);
		typedQuery = (TypedQuery<Hospedagem>) paginacaoUtil.prepararIntervalo(typedQuery, pageable);
		
		return new PageImpl<>(typedQuery.getResultList(), pageable, total(filtro));
	}
	
	private Long total(HospedagemFilter filtro) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
		Root<Hospedagem> cervejaEntity = query.from(Hospedagem.class);
		
		query.select(criteriaBuilder.count(cervejaEntity));
		query.where(adicionarFiltro(filtro, cervejaEntity));
		
		return manager.createQuery(query).getSingleResult();
	}
	
	private Predicate[] adicionarFiltro(HospedagemFilter filtro, Root<Hospedagem> hospedagemEntity) {
		List<Predicate> predicateList = new ArrayList<>();
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		
		if (filtro != null) {
			
			if (!StringUtils.isEmpty(filtro.getNome())) {
				predicateList.add(builder.like(hospedagemEntity.get("nome"), "%"+filtro.getNome()+"%"));
			}
			
			if (filtro.getTipoHospedagem() != null) {
				predicateList.add(builder.equal(hospedagemEntity.get("tipoHospedagem"), filtro.getTipoHospedagem()));
			}
		}
		
		Predicate[] predArray = new Predicate[predicateList.size()];
		return predicateList.toArray(predArray);
	}

}
