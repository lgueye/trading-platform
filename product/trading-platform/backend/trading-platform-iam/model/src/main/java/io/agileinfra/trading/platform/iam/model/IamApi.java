package io.agileinfra.trading.platform.iam.model;

public interface IamApi {
	/**
	 * sets token in user principal (thread local)
	 * @param request
	 */
	AuthenticatedUserDto signin(SigninRequestDto request);
}
