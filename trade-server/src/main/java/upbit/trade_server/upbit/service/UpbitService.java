package upbit.trade_server.upbit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import upbit.trade_server.upbit.quotation.UpbitQuotationWebSocket;

@EnableAsync
@Service
public class UpbitService {
	@Autowired
	private UpbitQuotationWebSocket webSocket;
	
	private SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor("fooExecutor");
    
    public void test() {
    	System.out.println("test!!!!");
    	run();
    	check();
    }

//    @Async("fooExecutor")
	public void check() {
		if (taskExecutor.isThrottleActive()) {
			System.out.println("run check!!!");
			return;
		}
		taskExecutor.setDaemon(true);
		taskExecutor.setConcurrencyLimit(2);
		/**
		 * TODO: GET UPBIT ACCOUNT
		 */
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000 * 10);
						System.out.println(1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		/**
		 * TODO: GET SETTING
		 */
		taskExecutor.execute(new Runnable() {			
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000 * 30);
						System.out.println(2);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

//    @Async("fooExecutor2")
	public void run() {
//		Executors.newCachedThreadPool(threadFactory)
		if (webSocket.isStop()) {//stop = stop..
			return;
		}
		if (webSocket.isRun()) {//stop = stop..
			System.out.println("already run");
			return;
		}
		//wait, run
		webSocket.run(new String[] {"KRW-CBK", "KRW-XRP", "KRW-BTC"});
		/**
		 * TODO: MANAGEMENT..LOOP... STATUS CHECK STOP, RESTART...
		 */
	}

	public void close() {
		webSocket.stop();
	}
}
