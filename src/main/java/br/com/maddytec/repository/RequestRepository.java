package br.com.maddytec.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.maddytec.domain.Request;
import br.com.maddytec.domain.enums.RequestState;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>{

	public List<Request> findAllByOwnerId(Long id);
	
	@Query("UPDATE request SET state = ?2 WHERE id = ?1")
	public Request updateStatus(Long id, RequestState state);
	
}
