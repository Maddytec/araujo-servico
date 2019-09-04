package br.com.maddytec.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.maddytec.domain.Request;
import br.com.maddytec.domain.User;
import br.com.maddytec.dto.UserLoginDTO;
import br.com.maddytec.dto.UserSaveDTO;
import br.com.maddytec.dto.UserUpdateDTO;
import br.com.maddytec.dto.UserUpdateRoleDTO;
import br.com.maddytec.model.PageModel;
import br.com.maddytec.model.PageRequestModel;
import br.com.maddytec.service.RequestService;
import br.com.maddytec.service.UserService;

@RestController
@RequestMapping(value = "users")
public class UserResource {

	@Autowired
	private UserService userService;

	@Autowired
	private RequestService requestService;

	@PostMapping
	public ResponseEntity<User> save(@RequestBody @Valid UserSaveDTO userSaveDTO) {
		
		User createdUser = userService.save(userSaveDTO.converterToUser(userSaveDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable(name = "id") Long id, @RequestBody @Valid UserUpdateDTO userUpdateDTO) {
		
		User user = userUpdateDTO.converterToUser(userUpdateDTO);
		user.setId(id);
		
		User updatedUser = userService.update(user);
		return ResponseEntity.ok().body(updatedUser);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable(name = "id") Long id) {
		User user = userService.findById(id);
		return ResponseEntity.ok().body(user);
	}

	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		List<User> users = userService.findAll();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/v2") //Lazy loading
	public ResponseEntity<PageModel<User>> findAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {

		PageRequestModel pageRequestModel = new PageRequestModel(page, size);

		PageModel<User> pageModel = userService.findAllOnLazyMode(pageRequestModel);
		return ResponseEntity.ok(pageModel);
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody @Valid  UserLoginDTO userLoginDTO) {
		User loggedUser = userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword());
		return ResponseEntity.ok(loggedUser);
	}

	@GetMapping("/{ownerId}/requests")
	public ResponseEntity<List<Request>> findAllRequestsByOwnerId(@PathVariable(name = "ownerId") Long ownerId) {
		List<Request> requests = requestService.findAllByOwnerId(ownerId);
		return ResponseEntity.ok(requests);
	}

	@GetMapping("/v2/{ownerId}/requests") // OnLazyMode
	public ResponseEntity<PageModel<Request>> findAllByOwnerIdOnLazyModel(
			@PathVariable(name = "ownerId") Long ownerId,
			@RequestParam(name = "size", defaultValue = "0") int size,
			@RequestParam(name = "page", defaultValue = "10") int page ) {
		
		PageRequestModel pageRequestModel = new PageRequestModel(page, size);
		PageModel<Request> pageModel = requestService.findAllByOwnerIdOnLazyModel(ownerId, pageRequestModel);
		
		return ResponseEntity.ok(pageModel);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateRole(
			@RequestBody @Valid UserUpdateRoleDTO userUpdateRoleDTO, 
			@PathVariable(name = "id") Long id){
		
		User user = User.builder()
				.role(userUpdateRoleDTO.getRole())
				.id(id).build();
		
		userService.updateRole(user);
		
		return ResponseEntity.ok().build();
	}
}
