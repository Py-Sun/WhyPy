///*
//package com.example.whypyprojdect.Websocket;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import jakarta.websocket.OnOpen;
//import jakarta.websocket.Session;
//import jakarta.websocket.server.ServerEndpoint;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//
//@Component
//@ServerEndpoint("/websocket")
//public class WebSocket {
//
//    //웹소켓 세션을 담는 allaylist
//    private static ArrayList<HttpSession> sessionList=new ArrayList<>();
//
//    //웹소켓 사용자 연결 성립하는 경우 호출
//    @OnOpen
//    public void handleOpen(Session session, HttpServletRequest request) {
//        HttpSession session= request.getSession();
//        if (!session.isNew() && session.getAttribute("loginName") !=null) {
//            String sessionId= session.getId();
//
//            System.out.println(“client is connected. sessionId == [“ + sessionId + “]”);
//            sessionList.add(session);
//    }
//
//}
//*/