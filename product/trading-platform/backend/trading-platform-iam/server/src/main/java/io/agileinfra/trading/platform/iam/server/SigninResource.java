package io.agileinfra.trading.platform.iam.server;

import io.agileinfra.trading.platform.iam.model.AuthenticatedUserDto;
import io.agileinfra.trading.platform.iam.model.SigninRequestDto;
import io.agileinfra.trading.platform.iam.persistence.IamPersistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/iam")
@RequiredArgsConstructor
public class SigninResource {
	private final IamPersistenceService service;

	@PostMapping("signin")
	public ResponseEntity<AuthenticatedUserDto> signin(@RequestBody SigninRequestDto request) {
		final AuthenticatedUserDto dto = service.signin(request);
		final HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", String.format("Bearer %s", dto.getToken()));
		return new ResponseEntity<>(dto, headers, HttpStatus.OK);
	}

}
