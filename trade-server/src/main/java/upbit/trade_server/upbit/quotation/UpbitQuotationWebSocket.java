package upbit.trade_server.upbit.quotation;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import upbit.trade_server.upbit.handler.OnMessageEvent;

@Component
public final class UpbitQuotationWebSocket extends WebSocketListener {
	@Autowired
	ApplicationEventPublisher publisher; // 1

	public enum Status {
		RUN
		, STOP
		, WAIT
		;
	}
	private Status status = Status.WAIT;

	public boolean isStop() {
		return Status.STOP.equals(status);
	}
	
	public boolean isRun() {
		return Status.RUN.equals(status);
	}

	public void stop() {
		status = Status.STOP;
	}

	public void run(String[] codes) {
		System.out.println("run");
		status = Status.RUN;
		/**
		 * TODO: OPTION 처리~
		 */
		OkHttpClient client = new OkHttpClient.Builder().readTimeout(0, TimeUnit.SECONDS).build();

		/**
		 * TODO: option 처리~
		 */
		JsonArray root = new JsonArray();
		root.add(new JsonObject());
		root.get(0).getAsJsonObject().addProperty("ticket", "UNIQUE_TICKET");

		JsonObject type = new JsonObject();
		/**
		 * TODO: ENUM
		 * 
		 * 현재가 -> ticker 체결 -> trade, 호가 ->orderbook
		 */
		type.addProperty("type", "ticker");

		JsonArray codesObj = new JsonArray();
		for (int i = 0; i < codes.length; i++) {
			codesObj.add(codes[i]);
		}
		type.add("codes", codesObj);

		root.add(type);

		Request request = new Request.Builder().url("wss://api.upbit.com/websocket/v1")
				.addHeader("options", new Gson().toJson(root)).build();
		client.newWebSocket(request, this);

		// Trigger shutdown of the dispatcher's executor so this process can exit
		// cleanly.
		client.dispatcher().executorService().shutdown();
		
	}

	@Override
	public void onOpen(WebSocket webSocket, Response response) {
		System.out.println("onOpen");

		String options = webSocket.request().header("options");
		webSocket.send(options);
	}

	@Override
	public void onMessage(WebSocket webSocket, String text) {
		System.out.println("MESSAGE 1: " + text);
	}

	@Override
	public void onMessage(WebSocket webSocket, ByteString bytes) {
		if (isStop()) {
			webSocket.close(1000, "close~~~");
			return;
		}
		String jsonStr = bytes.string(Charset.forName("utf-8"));
		try {
			publisher.publishEvent(new OnMessageEvent(jsonStr));
		} catch (IllegalStateException e) {
		}
	}

	@Override
	public void onClosing(WebSocket webSocket, int code, String reason) {
		webSocket.close(1000, null);
		System.out.println("CLOSE: " + code + " " + reason);
	}

	@Override
	public void onFailure(WebSocket webSocket, Throwable t, Response response) {
		t.printStackTrace();
	}
}