package upbit.trade_server.upbit.handler;

import java.util.Date;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Component
public class OnMessageHandler {

	@Async
	@EventListener
	public void onMessage(OnMessageEvent event) {
		JsonObject jsonObject = new Gson().fromJson(event.getMessage(), JsonObject.class);
		String type = "";
		if (jsonObject.get("type").isJsonNull()) {
			return;
		}
		type = jsonObject.get("type").getAsString();
		if ("ticker".equalsIgnoreCase(type)) {
			String code = jsonObject.get("code").getAsString();
//type":"ticker","code":"KRW-BTC","opening_price":39633000.00000000,"high_price":39930000.0,"low_price":39250000.0,"trade_price":39641000.0,"prev_closing_price":39626000.00000000,"acc_trade_price":54392276109.146170000,"change":"RISE","change_price":15000.00000000,"signed_change_price":15000.00000000,"change_rate":0.0003785393,"signed_change_rate":0.0003785393,"ask_bid":"BID","trade_volume":0.0097775,"acc_trade_volume":1372.51681202,"trade_date":"20210711","trade_time":"054937","trade_timestamp":1625982577000,"acc_ask_volume":740.18223817,"acc_bid_volume":632.33457385,"highest_52_week_price":81994000.00000000,"highest_52_week_date":"2021-04-14","lowest_52_week_price":10835000.00000000,"lowest_52_week_date":"2020-07-16","trade_status":null,"market_state":"ACTIVE","market_state_for_ios":null,"is_trading_suspended":false,"delisting_date":null,"market_warning":"NONE","timestamp":1625982577908,"acc_trade_price_24h":168186814068.09371000,"acc_trade_volume_24h":4232.22566562,"stream_type":"REALTIME
			Double prev_closing_price = jsonObject.get("prev_closing_price").getAsDouble();
			Double trade_price = jsonObject.get("trade_price").getAsDouble();

			Double high_price = jsonObject.get("high_price").getAsDouble();
			Double low_price = jsonObject.get("low_price").getAsDouble();

			long trade_timestamp = jsonObject.get("trade_timestamp").getAsLong();

			float per = (float) -((prev_closing_price - trade_price) / prev_closing_price * 100);

			/**
			 * buy/sell logic..
			 */
			System.out.println(new Date(trade_timestamp) + " -> " + code + " trade_price is "
					+ String.format("%.2f", trade_price.floatValue()) + " per is " + String.format("%.2f", per)

					+ " prev_closing_price is " + String.format("%.2f", prev_closing_price.floatValue())
					+ " high_price is " + String.format("%.2f", high_price.floatValue()) + " low_price is "
					+ String.format("%.2f", low_price.floatValue()));
		}
	}
}
