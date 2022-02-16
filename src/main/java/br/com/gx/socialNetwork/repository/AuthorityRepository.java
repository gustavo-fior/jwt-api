package br.com.gx.socialNetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gx.socialNetwork.model.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>{

}
