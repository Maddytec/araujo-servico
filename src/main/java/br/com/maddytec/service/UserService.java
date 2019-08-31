package br.com.maddytec.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.maddytec.domain.User;
import br.com.maddytec.exception.NotFoundException;
import br.com.maddytec.model.PageModel;
import br.com.maddytec.model.PageRequestModel;
import br.com.maddytec.repository.UserRepository;
import br.com.maddytec.util.HashUtil;

@Service
public class UserService {

	@Autowired
	private UserRepository userReposotory;

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
		return result.orElseThrow(() -> new NotFoundException("Not exist user with id: " + id));
	}

	public List<User> findAll() {
		List<User> users = userReposotory.findAll();
		return users;
	}

	public PageModel<User> findAllOnLazyMode(PageRequestModel pageRequestModel) {
		PageRequest pageable = PageRequest.of(pageRequestModel.getPage(), pageRequestModel.getSize());
		Page<User> page = userReposotory.findAll(pageable);

		return new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
	}

	public User login(String email, String password) {
		password = HashUtil.getSecureHash(password);
		Optional<User> result = userReposotory.login(email, password);
		return result.get();
	}
}
