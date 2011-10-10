package br.com.DBUnit.domain.repository;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class DAOGeneric<T> extends HibernateDaoSupport{
	
	private final Class<T> entityClass;
	
	@SuppressWarnings("unchecked")
	public DAOGeneric(){
		this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	protected Class<T> getEntityClass(){
		return this.entityClass;
	}
	
	public Boolean saveOrUpdate(T entity){
		
		if(entity == null)
			throw new IllegalArgumentException();
		
		getHibernateTemplate().saveOrUpdate(entity);
	    return true;
	}
	
	public T get(Long id){
		return getHibernateTemplate().get(getEntityClass(), id);
	}
	
	public List<T> findAll(){
		return find( DetachedCriteria.forClass(getEntityClass()));
	}
	
	@SuppressWarnings("unchecked")
	public List<T> find(DetachedCriteria criteria){
		
		if(criteria == null)
			throw new IllegalArgumentException();

		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public Boolean delete(T entity){
		getHibernateTemplate().delete(entity);
		return true;
	}
	
}
