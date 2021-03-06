package com.excilys.cdb.repository;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.enums.ComputerFields;
import com.excilys.cdb.model.*;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@Transactional(propagation = Propagation.NESTED)
public class ComputerDao extends Dao<Computer> {
	@PersistenceContext EntityManager entityManager;
	private QComputer qComputer = QComputer.computer;
	private QCompany qCompany = QCompany.company;
	
	public ComputerDao(JPAQueryFactory jpaQueryFactory) {
		super(
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
		return this.listAll("id");
	}	
	public List<Computer> listAll(String orderBy) {
		return this.list(1, (int) this.count(), orderBy);
	}
	
	@Override
	public List<Computer> list(int page, int size) {
		return this.list(page, size, "");
	}	
	public List<Computer> list(int page, int size, String orderBy) {
		return this.listByName("", page, size, orderBy);
	}
	
	@Override
	public List<Computer> listByName(String name, int page, int size, String orderBy) {
		int offset = (page-1)*size;	
		return this.jpaQueryFactory.selectFrom(qComputer)
				.leftJoin(qCompany).on(qComputer.companyId.eq(qCompany.id))
				.where(qComputer.name.like("%"+name+"%")
					.or(qCompany.name.like("%"+name+"%"))
				).orderBy(ComputerFields.getOrderByField(orderBy).getField())
				.limit(size).offset(offset).fetch();
	}
	
	@Override
	public long countByName(String name) {
		return this.jpaQueryFactory.selectFrom(qComputer)
			.leftJoin(qCompany).on(qComputer.companyId.eq(qCompany.id))
			.where(qComputer.name.like("%"+name+"%")
				.or(qCompany.name.like("%"+name+"%"))
			).fetchCount();
	}

}
