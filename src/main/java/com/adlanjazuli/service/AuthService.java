package com.adlanjazuli.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.adlanjazuli.dto.GeneralResponse;
import com.adlanjazuli.dto.UserRequest;
import com.adlanjazuli.model.Auth;
import com.adlanjazuli.repository.AuthRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthService {
	private final AuthRepository authRepo;
	
	@SuppressWarnings("unused")
	public ResponseEntity<Object> login(UserRequest loginRequest) {
		GeneralResponse response = new GeneralResponse();
				
		try {
			if(loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
				response.setMessage("Username dan / atau password kosong");
				return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
			}
			
			Auth auth =  authRepo.findByUsername(loginRequest.getUsername());
			String password = loginRequest.getPassword();
			
			
			if(auth == null) {
				response.setMessage("username atau password salah ");
				return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
			}else {
				if(!BCrypt.checkpw(loginRequest.getPassword(), auth.getPassword()))  {
					response.setMessage("username atau password salah ");
					return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
				}
			}
						
			response.setMessage("Sukses Login");
			
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
}
