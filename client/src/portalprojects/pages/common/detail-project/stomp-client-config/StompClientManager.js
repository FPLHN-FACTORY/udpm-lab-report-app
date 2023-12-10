import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

import { AppConfig } from "../../../../../AppConfig";

let stompClient = null;

export const connectStompClient = () => {
  const socket = new SockJS(
    AppConfig.apiUrl + "/portal-projects-websocket-endpoint"
  );
  stompClient = Stomp.over(socket, {
    heartbeatIncoming: 10000,
    heartbeatOutgoing: 10000,
    debug: (str) => {},
  });

  stompClient.activate();
};

export const getStompClient = () => stompClient;
