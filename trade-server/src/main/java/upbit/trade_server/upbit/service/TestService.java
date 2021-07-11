package upbit.trade_server.upbit.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
	@Autowired
	private UpbitService upbitService;
	
	@PostConstruct
	public void init() {
		upbitService.test();
		upbitService.test();
	}
}
