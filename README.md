# upbit 자동매매

업비트 websocket 실시간 시세
- https://docs.upbit.com/docs/upbit-quotation-websocket
- 실시간 처리 시세는 별도 관리(db연계), 

업비트 주문
- https://docs.upbit.com/reference#%EA%B0%9C%EB%B3%84-%EC%A3%BC%EB%AC%B8-%EC%A1%B0%ED%9A%8C
- 문서 잘 되어 있음//
- db연계(주문내역, api로도 가능하긴 함)

자동매매(스탑로스, 트레일링 스탑), 수 분할, 자동매수(무지성매수) 등등은 미구현!
- 업데이트 계획 없음!
- db연계

적용 계획
- UpbitQuotationWebSocket.onMessage 에서 event 분기(현재가: ticker, 체결: trade, 호가: orderbook),  or OnMessageHandler에서 별도 service 호출하여 처리
- 분기방식의 차이
- 필요한건 ticker(현재가)! 계획상 거래량 거래금은 보지 않음. 추세매매 시 봐야할수도 있음.
- 매수/매도 관련 비지니스 로직 생성(bigdata 사용x, 과거 데이터 참조x, 3분봉, 거래량 등은 활용해야 할지도?)
- 관리에서 순환매는 확인 필요(김치, 중국, 근본 등등)

