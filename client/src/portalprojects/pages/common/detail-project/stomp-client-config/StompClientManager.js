import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";
import { toast } from "react-toastify";
import { AppConfig } from "../../../../../AppConfig";

let stompClient = null;

export const connectStompClient = () => {
  const socket = new SockJS(
    AppConfig.apiUrl + "/portal-projects-websocket-endpoint"
  );
  stompClient = Stomp.over(socket, {
    heartbeatIncoming: 10000,
    heartbeatOutgoing: 10000,
  });

  stompClient.activate();
};

export const getStompClient = () => stompClient;
