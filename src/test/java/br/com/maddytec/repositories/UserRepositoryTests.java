package br.com.maddytec.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.maddytec.domain.User;
import br.com.maddytec.domain.enums.Role;
import br.com.maddytec.repository.UserRepository;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void asaveTest() {
		User user = User.builder().email("maddytec@gmail.com").name("Madson").password("123").role(Role.ADMINISTRATOR)
				.build();

		User createdUser = userRepository.save(user);

		assertThat(createdUser.getId()).isEqualTo(1L);
	}

	@Test
	public void updateTest() {
		User user = new User();
		user.setId(1L);
		user.setEmail("maddytec@gmail.com");
		user.setName("Madson Silva");
		user.setPassword("1234");
		user.setRole(Role.ADMINISTRATOR);
		
		User updateUser = userRepository.save(user);

		assertThat(updateUser.getName()).isEqualTo("Madson Silva");
	}

	@Test
	public void findByIdTest() {
		Optional<User> result = userRepository.findById(1L);
		User user = result.get();
		assertThat(user.getPassword()).isEqualTo("1234");
	}

	@Test
	public void listTest() {
		List<User> users = userRepository.findAll();

		assertThat(users.size()).isEqualTo(1);
	}

	@Test
	public void loginTest() {
		Optional<User> result = userRepository.login("maddytec@gmail.com", "1234");
		User loggedUser = result.get();

		assertThat(loggedUser.getId()).isEqualTo(1L);
	}
}
