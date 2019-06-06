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
public class UserDao extends Dao<User>{
	@PersistenceContext EntityManager entityManager;
	
	private QUser qUser = QUser.user;
	
	public UserDao(JPAQueryFactory jpaQueryFactory) {		
		super(
			jpaQueryFactory
		);
	}

	@Override
	public void create(User aUser) {
		entityManager.persist(aUser);
	}

	@Override
	public void update(User aUser) {
		this.jpaQueryFactory.update(qUser).where(qUser.id.eq(aUser.getId()))
			.set(qUser.username, aUser.getUsername())
			.set(qUser.password, aUser.getPassword())
			.set(qUser.roleId, aUser.getRoleId());
	}

	
	@Override
	public void delete(User aUser) {
		this.deleteById(aUser.getId());
	}
	
	@Override
	public void deleteById(long id) {
		this.jpaQueryFactory.delete(qUser).where(qUser.id.eq(id)).execute();
	}

	@Override
	public User read(long id) {
		return this.jpaQueryFactory.selectFrom(qUser).where(qUser.id.eq(id)).fetchOne();
	}
	
	public User findByUsername(String name) {
		System.out.println("OUI");
		return this.jpaQueryFactory.selectFrom(qUser).where(qUser.username.eq(name)).fetchOne();
	}
	
	@Override
	public List<User> listAll() {	
		return this.jpaQueryFactory.selectFrom(qUser).fetch();
	}
	
	@Override
	public List<User> list(int page, int size) {
		int offset = (page-1)*size;		
		return this.jpaQueryFactory.selectFrom(qUser).limit(size).offset(offset).fetch();
	}
	
	@Override
	public long count() {
		return this.jpaQueryFactory.selectFrom(qUser).fetchCount();
	}
}
