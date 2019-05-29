package com.excilys.cdb.dao;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.mapper.ComputerRowMapper;
import com.excilys.cdb.model.*;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@Transactional(propagation = Propagation.NESTED)
public class ComputerDao extends Dao<Computer> {
	@PersistenceContext EntityManager entityManager;
	private QComputer qComputer = QComputer.computer;
	private QCompany qCompany = QCompany.company;
	
	public ComputerDao(ComputerRowMapper rowMapper, JPAQueryFactory jpaQueryFactory) {
		super(
			rowMapper,
			jpaQueryFactory
		);
	}
	
	@Override
	public void create(Computer aComputer) {
		this.entityManager.persist(aComputer);
	}

	@Override
	public void update(Computer aComputer) {
		this.jpaQueryFactory.update(qComputer).where(qComputer.id.eq(aComputer.getId()))
			.set(qComputer.name, aComputer.getName())
			.set(qComputer.introduced, aComputer.getIntroduced())
			.set(qComputer.discontinued, aComputer.getDiscontinued())
			.set(qComputer.companyId, aComputer.getCompanyId()).execute();
	}

	public void deleteByCompanyId(long companyId) {		
		this.jpaQueryFactory.delete(qComputer).where(qComputer.companyId.eq(companyId)).execute();
	}
	
	@Override
	public void delete(Computer aComputer) {
		this.deleteById(aComputer.getId());
	}
	
	@Override
	public void deleteById(long id) {
		this.jpaQueryFactory.delete(qComputer).where(qComputer.id.eq(id)).execute();
	}

	@Override
	public Computer read(long id) {
		return this.jpaQueryFactory.selectFrom(qComputer).where(qComputer.id.eq(id)).fetchOne();
	}
	
	@Override
	public long count() {
		return this.jpaQueryFactory.from(qComputer).fetchCount();
	}	

	@Override
	public List<Computer> listAll() {
		return this.jpaQueryFactory.selectFrom(qComputer).fetch();
	}	
	public List<Computer> listAll(String orderBy) {
		//ICI
		return this.jpaQueryFactory.selectFrom(qComputer).orderBy(
			new CaseBuilder().when(qComputer.id.isNull()).then(0).otherwise(1).asc()
		).fetch();
	}
	
	@Override
	public List<Computer> list(int page, int size) {
		return this.list(page, size, "");
	}	
	public List<Computer> list(int page, int size, String orderBy) {
		return this.listByName("", page, size, orderBy);
	}
	
	public List<Computer> listByName(String name, int page, int size, String orderBy) {
		int offset = (page-1)*size;	
		return this.jpaQueryFactory.selectFrom(qComputer)
				.leftJoin(qCompany).on(qComputer.companyId.eq(qCompany.id))
				.where(qComputer.name.like("%"+name+"%")
					.or(qCompany.name.like("%"+name+"%"))
				).limit(size).offset(offset).fetch();
	}
	
	public long countByName(String name) {
		return this.jpaQueryFactory.selectFrom(qComputer)
			.leftJoin(qCompany).on(qComputer.companyId.eq(qCompany.id))
			.where(qComputer.name.like("%"+name+"%")
				.or(qCompany.name.like("%"+name+"%"))
			).fetchCount();
	}

}
