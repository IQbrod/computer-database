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
public class CompanyDao extends Dao<Company>{
	@PersistenceContext EntityManager entityManager;
	
	private QCompany qCompany = QCompany.company;
	
	public CompanyDao(JPAQueryFactory jpaQueryFactory) {		
		super(
			jpaQueryFactory
		);
	}

	@Override
	public void create(Company aCompany) {
		entityManager.persist(aCompany);
	}

	@Override
	public void update(Company aCompany) {
		this.jpaQueryFactory.update(qCompany).where(qCompany.id.eq(aCompany.getId())).set(qCompany.name, aCompany.getName()).execute();
	}

	
	@Override
	public void delete(Company aCompany) {
		this.deleteById(aCompany.getId());
	}
	
	@Override
	public void deleteById(long id) {
		this.jpaQueryFactory.delete(qCompany).where(qCompany.id.eq(id)).execute();
	}

	@Override
	public Company read(long id) {
		return this.jpaQueryFactory.selectFrom(qCompany).where(qCompany.id.eq(id)).fetchOne();
	}
	
	@Override
	public List<Company> listAll() {	
		return this.jpaQueryFactory.selectFrom(qCompany).fetch();
	}
	
	@Override
	public List<Company> list(int page, int size) {
		int offset = (page-1)*size;		
		return this.jpaQueryFactory.selectFrom(qCompany).limit(size).offset(offset).fetch();
	}
	
	@Override
	public long count() {
		return this.jpaQueryFactory.selectFrom(qCompany).fetchCount();
	}
	
	@Override
	public long countByName(String name) {
		return this.jpaQueryFactory.selectFrom(qCompany)
			.where(qCompany.name.like("%"+name+"%")
			).fetchCount();
	}
}
