package com.excilys.cdb.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.cdb.repository.UserDao;
import com.excilys.cdb.mapper.UserMapper;
import com.excilys.cdb.model.User;

@Service
public class UserService extends AbstractService<User> implements UserDetailsService {
	private final UserMapper userMapper;
	
	public UserService(UserDao userDao, UserMapper userMapper) {
		super(userDao);
		this.userMapper = userMapper;
	}
	
	public User findByUsername(String name) {
		return ((UserDao)this.dao).findByUsername(name);
	}
	
	public User findByNameAndPass(String name, String pass) {
		return ((UserDao)this.dao).checkLogin(name, pass);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userMapper.map(this.findByUsername(username));
	}
}