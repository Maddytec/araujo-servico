package br.com.maddytec.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.maddytec.domain.Request;
import br.com.maddytec.domain.RequestStage;
import br.com.maddytec.domain.User;
import br.com.maddytec.domain.enums.RequestState;
import br.com.maddytec.repository.RequestStageRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RequestStageRepositoryTest {

	@Autowired
	private RequestStageRepository requestStageRepository;

	
	public void AsaveTest() {
		User owner = new User();
		owner.setId(1L);

		Request request = new Request();
		request.setId(1L);

		RequestStage requestStage = RequestStage.builder().owner(owner).description("Notebook SAMSUNG I5")
				.realizationDate(new Date()).request(request).state(RequestState.CLOSED).build();

		RequestStage createdRequestStage = requestStageRepository.save(requestStage);

		assertThat(createdRequestStage.getId()).isEqualTo(1L);

	}

	
	public void findByIdTest() {

		RequestStage requestStage = requestStageRepository.findById(1L).orElseGet(null);

		assertThat(requestStage.getDescription()).isEqualTo("Notebook SAMSUNG I7");
	}

	@Test
	public void listByRequestIdTest() {
		List<RequestStage> listRequestStage = requestStageRepository.findAllByRequestId(1L);

		assertThat(listRequestStage.size()).isEqualTo(1);
	}

}
