package com.excilys.cdb.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.model.*;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@Transactional(propagation = Propagation.NESTED)
public class RoleDao extends Dao<Role>{
	@PersistenceContext EntityManager entityManager;
	
	private QRole qRole = QRole.role;
	
	public RoleDao(JPAQueryFactory jpaQueryFactory) {		
		super(
			jpaQueryFactory
		);
	}

	@Override
	public void create(Role aRole) {
		entityManager.persist(aRole);
	}

	@Override
	public void update(Role aRole) {
		this.jpaQueryFactory.update(qRole).where(qRole.id.eq(aRole.getId())).set(qRole.name, aRole.getName());
	}

	
	@Override
	public void delete(Role aRole) {
		this.deleteById(aRole.getId());
	}
	
	@Override
	public void deleteById(long id) {
		this.jpaQueryFactory.delete(qRole).where(qRole.id.eq(id)).execute();
	}

	@Override
	public Role read(long id) {
		return this.jpaQueryFactory.selectFrom(qRole).where(qRole.id.eq(id)).fetchOne();
	}

	public Role findByName(String name) {
		return this.jpaQueryFactory.selectFrom(qRole).where(qRole.name.eq(name)).fetchOne();
	}
	
	@Override
	public List<Role> listAll() {	
		return this.jpaQueryFactory.selectFrom(qRole).fetch();
	}
	
	@Override
	public List<Role> list(int page, int size) {
		int offset = (page-1)*size;		
		return this.jpaQueryFactory.selectFrom(qRole).limit(size).offset(offset).fetch();
	}
	
	@Override
	public long count() {
		return this.jpaQueryFactory.selectFrom(qRole).fetchCount();
	}
}
