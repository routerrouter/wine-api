package wine.com.br.auditor;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {
	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.ofNullable("Rufino").filter(s -> !s.isEmpty()); // current user logged
	}
}
