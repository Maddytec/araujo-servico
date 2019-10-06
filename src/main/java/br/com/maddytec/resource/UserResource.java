package br.com.maddytec.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import br.com.maddytec.domain.enums.Role;
import br.com.maddytec.dto.UserLoginDTO;
import br.com.maddytec.dto.UserSaveDTO;
import br.com.maddytec.dto.UserUpdateDTO;
import br.com.maddytec.dto.UserUpdateRoleDTO;
import br.com.maddytec.model.PageModel;
import br.com.maddytec.model.PageRequestModel;
import br.com.maddytec.security.JwtManager;
import br.com.maddytec.service.RequestService;
import br.com.maddytec.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "users")
public class UserResource {

	@Autowired
	private UserService userService;

	@Autowired
	private RequestService requestService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtManager jwtManager;

	@Secured({ "ROLE_ADMINISTRATOR" })
	@PostMapping
	public ResponseEntity<User> save(@RequestBody @Valid UserSaveDTO userSaveDTO) {

		User createdUser = userService.save(userSaveDTO.converterToUser(userSaveDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@PreAuthorize("@accessManager.isOwner(#id)")
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable(name = "id") Long id,
			@RequestBody @Valid UserUpdateDTO userUpdateDTO) {

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

	@GetMapping("/v2") // Lazy loading
	public ResponseEntity<PageModel<User>> findAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {

		PageRequestModel pageRequestModel = new PageRequestModel(page, size);

		PageModel<User> pageModel = userService.findAllOnLazyMode(pageRequestModel);
		return ResponseEntity.ok(pageModel);
	}

	@PostMapping("/login")
	public ResponseEntity<UserLoginDTO> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userLoginDTO.getEmail(), userLoginDTO.getPassword());

		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		org.springframework.security.core.userdetails.User userdetails = (org.springframework.security.core.userdetails.User) authentication
				.getPrincipal();

		String email = userdetails.getUsername();
		List<String> roles = userdetails.getAuthorities().stream().map(authority -> authority.getAuthority())
				.collect(Collectors.toList());
		
		String token = jwtManager.createToken(email, roles);
		userLoginDTO.setPassword(token);
		return ResponseEntity.ok(userLoginDTO);
	}

	@GetMapping("/{ownerId}/requests")
	public ResponseEntity<List<Request>> findAllRequestsByOwnerId(@PathVariable(name = "ownerId") Long ownerId) {
		List<Request> requests = requestService.findAllByOwnerId(ownerId);
		return ResponseEntity.ok(requests);
	}

	@GetMapping("/v2/{ownerId}/requests") // OnLazyMode
	public ResponseEntity<PageModel<Request>> findAllByOwnerIdOnLazyModel(@PathVariable(name = "ownerId") Long ownerId,
			@RequestParam(name = "size", defaultValue = "0") int size,
			@RequestParam(name = "page", defaultValue = "10") int page) {

		PageRequestModel pageRequestModel = new PageRequestModel(page, size);
		PageModel<Request> pageModel = requestService.findAllByOwnerIdOnLazyModel(ownerId, pageRequestModel);

		return ResponseEntity.ok(pageModel);
	}

	@Secured({ "ROLE_ADMINISTRATOR" })
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateRole(@RequestBody @Valid UserUpdateRoleDTO userUpdateRoleDTO,
			@PathVariable(name = "id") Long id) {

		userService.updateRole(User.builder().id(id).role(userUpdateRoleDTO.getRole()).build());

		return ResponseEntity.ok().build();
	}
	
	@Secured({ "ROLE_ADMINISTRATOR" })
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long id) {
		userService.deleteById(id);
		return ResponseEntity.ok().body(HttpStatus.OK);
	}
	
	
}
