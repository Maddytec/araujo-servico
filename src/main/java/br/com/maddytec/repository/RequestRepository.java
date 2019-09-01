package br.com.maddytec.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.maddytec.domain.Request;
import br.com.maddytec.domain.enums.RequestState;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>{

	public List<Request> findAllByOwnerId(Long id);
	
	public Page<Request> findAllByOwnerId(Long idOwner, Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("UPDATE request SET state = ?2 WHERE id = ?1")
	public void updateStatus(Long id, RequestState state);
	
}
