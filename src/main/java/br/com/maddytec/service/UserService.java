package br.com.maddytec.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.maddytec.domain.User;
import br.com.maddytec.exception.NotFoundException;
import br.com.maddytec.model.PageModel;
import br.com.maddytec.model.PageRequestModel;
import br.com.maddytec.repository.UserRepository;
import br.com.maddytec.util.HashUtil;

@Service
public class UserService implements UserDetailsService {

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

	public int updateRole(User user) {
		return userReposotory.updateRole(user.getId(), user.getRole());
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> result = userReposotory.findByEmail(email);

		if (!result.isPresent()) {
			throw new UsernameNotFoundException("Doesn't exist user with email: " + email);
		}

		User user = result.get();

		List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

		org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
				user.getEmail(), user.getPassword(), authorities);

		return userDetails;
	}

}
