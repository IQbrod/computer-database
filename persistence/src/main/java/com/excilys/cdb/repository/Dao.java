package com.excilys.cdb.repository;

import java.util.*;

import org.springframework.jdbc.core.RowMapper;
import com.excilys.cdb.model.AbstractModel;
import com.querydsl.jpa.impl.JPAQueryFactory;

public abstract class Dao<T extends AbstractModel> {
	protected final JPAQueryFactory jpaQueryFactory;
	protected final RowMapper<T> rowMapper;
	
	protected Dao(JPAQueryFactory jpaQueryFactory) {
		this(null, jpaQueryFactory);
	}
	
	protected Dao(RowMapper<T> rowMapper, JPAQueryFactory jpaQueryFactory) {
		this.rowMapper = rowMapper;
		this.jpaQueryFactory = jpaQueryFactory;
	}
	
	public abstract void create(T obj);
	public abstract void update(T obj);
	public abstract void delete(T obj);
	public abstract void deleteById(long i);
	public abstract T read(long id);
	public abstract List<T> listAll();
	public abstract List<T> list(int page, int size);
	public abstract long count();
	
}
