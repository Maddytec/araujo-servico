package br.com.maddytec.service.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.maddytec.util.HashUtil;

@RunWith(SpringRunner.class)
public class HashUtilTests {

	@Test
	public void getSecureHashTest() {
		String hash = HashUtil.getSecureHash("123");
		System.out.println("hash: " + hash);
		assertThat(hash.length()).isEqualTo(64);
	}
}
