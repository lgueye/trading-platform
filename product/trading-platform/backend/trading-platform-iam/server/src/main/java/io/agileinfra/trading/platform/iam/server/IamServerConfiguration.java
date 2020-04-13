package io.agileinfra.trading.platform.iam.server;

import io.agileinfra.trading.platform.iam.persistence.IamPersistenceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author louis.gueye@gmail.com
 */
@Configuration
@Import({IamPersistenceConfiguration.class})
public class IamServerConfiguration {
}
