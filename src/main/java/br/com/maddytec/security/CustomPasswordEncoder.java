package br.com.maddytec.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.maddytec.util.HashUtil;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		String hash = HashUtil.getSecureHash(rawPassword.toString());
		return hash;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String hash = HashUtil.getSecureHash(rawPassword.toString());

		return hash.equals(encodedPassword);
	}

}
