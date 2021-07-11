package upbit.trade_server.upbit.handler;

public class OnMessageEvent {
	private String message;

	public OnMessageEvent(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
