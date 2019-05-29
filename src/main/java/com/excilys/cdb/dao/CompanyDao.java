package com.excilys.cdb.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.mapper.CompanyRowMapper;
import com.excilys.cdb.model.*;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@Transactional(propagation = Propagation.NESTED)
public class CompanyDao extends Dao<Company>{
	private QCompany qCompany = QCompany.company;
	
	public CompanyDao(CompanyRowMapper rowMapper, JPAQueryFactory jpaQueryFactory) {		
		super(
			rowMapper,
			jpaQueryFactory
		);
	}

	@Override
	public long create(Company aCompany) {
		return 0;
	}

	@Override
	public void update(Company aCompany) {
		this.jpaQueryFactory.update(qCompany).where(qCompany.id.eq(aCompany.getId())).set(qCompany.name, aCompany.getName());
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
}
