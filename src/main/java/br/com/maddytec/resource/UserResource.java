package br.com.maddytec.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.maddytec.domain.User;
import br.com.maddytec.dto.UserLoginDTO;
import br.com.maddytec.service.UserService;

@RestController
@RequestMapping(value = "users")
public class UserResource {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<User> save(@RequestBody User user) {
		User createdUser = userService.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable(name = "id") Long id, @RequestBody User user) {
		user.setId(id);
		User updatedUser = userService.save(user);
		return ResponseEntity.ok().body(updatedUser);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable(name = "id") Long id) {
		User user = userService.findById(id);
		return ResponseEntity.ok().body(user);
	}

	public ResponseEntity<List<User>> findAll() {
		List<User> users = userService.findAll();
		return ResponseEntity.ok(users);
	}

	@PostMapping("/{login}")
	public ResponseEntity<User> login(@RequestBody UserLoginDTO userLoginDTO) {
		User loggedUser = userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword());
		return ResponseEntity.ok(loggedUser);
		
	}
}
