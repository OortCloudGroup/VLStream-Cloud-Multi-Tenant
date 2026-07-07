package org.springblade.modules.auth.service;

import org.springblade.core.oauth2.provider.OAuth2Request;
import org.springblade.core.oauth2.service.OAuth2Client;
import org.springblade.core.oauth2.service.impl.OAuth2ClientDetailService;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * BladeClientDetailService
 *
 * @author Chill
 */
public class BladeClientDetailService extends OAuth2ClientDetailService {
	public BladeClientDetailService(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	@Override
	public OAuth2Client loadByClientId(String clientId) {
		return super.loadByClientId(clientId);
	}

	@Override
	public OAuth2Client loadByClientId(String clientId, OAuth2Request request) {
		return super.loadByClientId(clientId, request);
	}

	@Override
	public boolean validateClient(OAuth2Client client, String clientId, String clientSecret) {
		return super.validateClient(client, clientId, clientSecret);
	}

	@Override
	public boolean validateGranter(OAuth2Client client, String grantType) {
		return super.validateGranter(client, grantType);
	}
}
