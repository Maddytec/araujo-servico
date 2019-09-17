package br.com.maddytec.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.maddytec.constants.SecurityConstant;
import br.com.maddytec.domain.Request;
import br.com.maddytec.domain.User;
import br.com.maddytec.exception.NotFoundException;
import br.com.maddytec.repository.UserRepository;
import br.com.maddytec.service.RequestService;

@Component("accessManager")
public class AccessManager {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RequestService requestService;

	public Boolean isOwner(Long idOwner) {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<User> result = userRepository.findByEmail(email);

		if (!result.isPresent()) {
			throw new NotFoundException(SecurityConstant.JWT_INVALID_MSG + ": " + email);
		}

		User user = result.get();

		return user.getId() == idOwner;
	}

	public Boolean isRequestOwner(Long idRequest) {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<User> result = userRepository.findByEmail(email);

		if (!result.isPresent()) {
			throw new NotFoundException(SecurityConstant.JWT_INVALID_MSG + ": " + email);
		}

		User user = result.get();

		Request request = requestService.findById(idRequest);

		return user.getId() == request.getOwner().getId();

	}

}
