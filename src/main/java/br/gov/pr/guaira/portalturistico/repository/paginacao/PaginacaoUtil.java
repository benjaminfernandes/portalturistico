package br.gov.pr.guaira.portalturistico.repository.paginacao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginacaoUtil {

	@PersistenceContext
	private EntityManager manager;

	public TypedQuery<?> prepararIntervalo(TypedQuery<?> typedQuery, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;

		typedQuery.setFirstResult(primeiroRegistro);
		typedQuery.setMaxResults(totalRegistrosPorPagina);

		return typedQuery;
	}

	public TypedQuery<?> prepararOrdem(CriteriaQuery<?> query, Root<?> fromEntity, Pageable pageable) {
		Sort sort = pageable.getSort();

		if (sort != null && sort.isSorted()) {
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			Sort.Order sortOrder = sort.iterator().next();
			String property = sortOrder.getProperty();

			Order order = sortOrder.isAscending() ? builder.asc(fromEntity.get(property))
					: builder.desc(fromEntity.get(property));
			query.orderBy(order);
		}

		return manager.createQuery(query);
	}

	public void preparar(Criteria criteria, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;

		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistrosPorPagina);

		Sort sort = pageable.getSort();
		if (sort != null) {
			Sort.Order order = sort.iterator().next();
			String property = order.getProperty();
			criteria.addOrder(order.isAscending() ? org.hibernate.criterion.Order.asc(property)
					: org.hibernate.criterion.Order.desc(property));
		}
	}
	
}
