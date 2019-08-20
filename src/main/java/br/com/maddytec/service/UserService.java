package br.com.maddytec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.maddytec.domain.User;
import br.com.maddytec.repository.UserRepository;
import br.com.maddytec.util.HashUtil;

@Service
public class UserService {

	@Autowired private UserRepository userReposotory;
	
	public User save(User user) {
		String hash = HashUtil.getSecureHash(user.getPassword());
		 user.setPassword(hash);
		return userReposotory.save(user);
	}
	
	public User update(User user) {
		return userReposotory.save(user);
	}
	
	public User findById(Long id) {
		Optional<User> result = userReposotory.findById(id);
		return  result.get();
	}
	
	public List<User> listAll(){
		List<User> users = userReposotory.findAll();
		return users;
	}
	
	public User login(String email, String password) {
		password = HashUtil.getSecureHash(password);
		Optional<User> result = userReposotory.login(email, password);
		return result.get();
	}
}
