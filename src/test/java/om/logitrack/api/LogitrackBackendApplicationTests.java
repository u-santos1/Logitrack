package om.logitrack.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class LogitrackBackendApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void gerarSenhaBcrypt() {
		System.out.println(new BCryptPasswordEncoder().encode("123456"));
	}
}
