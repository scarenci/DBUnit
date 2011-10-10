package br.com.DBUnit.domain.repository;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import br.com.DBUnit.domain.entity.Terceiro;

@Repository
public class TerceiroRepository extends DAOGeneric<Terceiro>{
	
	public List<Terceiro> findAll(Long id){
		DetachedCriteria criteria = DetachedCriteria.forClass(Terceiro.class);
		return find(criteria);
	}
}
