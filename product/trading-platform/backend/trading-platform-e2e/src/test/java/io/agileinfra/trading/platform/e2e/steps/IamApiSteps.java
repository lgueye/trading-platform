package io.agileinfra.trading.platform.e2e.steps;

import io.agileinfra.trading.platform.iam.api.IamApiImpl;
import io.agileinfra.trading.platform.iam.model.AuthenticatedUserDto;
import io.agileinfra.trading.platform.iam.model.SigninRequestDto;
import io.cucumber.java8.En;
import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.assertNotNull;

@Slf4j
public class IamApiSteps implements En {

	public IamApiSteps(IamApiImpl iamApi) {
		Given("signed in as {} / {}", (final String login, final String password) -> {
			AuthenticatedUserDto dto = iamApi.signin(SigninRequestDto.builder().login(login.trim()).password(password.trim()).build());

			assertNotNull(dto);
			assertNotNull(dto.getPrincipal());
			assertNotNull(dto.getToken());
		});

		Then("signout", iamApi::signout);
	}
}
