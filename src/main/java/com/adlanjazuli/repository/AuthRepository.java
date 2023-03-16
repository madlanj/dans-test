package com.adlanjazuli.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adlanjazuli.model.Auth;


@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
	Auth findByUsername(String username);
	
	
	

}
