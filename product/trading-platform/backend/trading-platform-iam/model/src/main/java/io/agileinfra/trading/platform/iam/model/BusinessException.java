package io.agileinfra.trading.platform.iam.model;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * @author louis.gueye@gmail.com
 */
public class BusinessException extends RuntimeException {
	private String code;

	public String getCode() {
		return code;
	}

	public BusinessException(String code, String defaultMessage) {
		super(defaultMessage);
		Preconditions.checkArgument(!Strings.isNullOrEmpty(code), "Code is required for any business exception. Used to lookup i18n messages");
		Preconditions.checkArgument(!Strings.isNullOrEmpty(defaultMessage),
				"Default message is required for any business exception. Used as a fallback message (ie: missing translation)");
		this.code = code;
	}
}
