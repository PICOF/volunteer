package com.example.volunteer.WebSocket;

import com.example.volunteer.Entity.Msg;
import com.example.volunteer.Entity.Result;
import com.example.volunteer.Utils.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/messages/{id}/{jwt}")
@Component
public class MyWebSocket {
	private static Map<String,Session> map=new HashMap<String,Session>();
	private static RedisTemplate redisTemplate;
	private static ObjectMapper objectMapper;
	private static JWTUtil jwtUtil;
	private String openid;
	private String nickname;
	@Autowired
	public void setBarrageMessageService(RedisTemplate redisTemplate,JWTUtil jwtUtil,ObjectMapper objectMapper) {
		MyWebSocket.redisTemplate=redisTemplate;
		MyWebSocket.jwtUtil=jwtUtil;
		MyWebSocket.objectMapper=objectMapper;
	}
    @OnOpen
	public void onOpen(Session session,@PathParam("jwt") String jwt) throws IOException {
		try {
			openid = jwtUtil.getId(jwt);
			nickname= jwtUtil.getNickname(jwt);
		} catch (JwtException e) {
			session.getBasicRemote().sendText(objectMapper.writeValueAsString(new Result(null,e.getMessage())));
			throw e;
		}
		map.put(openid, session);
		session.getBasicRemote().sendText("连接已建立");
	}

	@OnClose
	public void onClose(Session session) throws IOException {
		map.remove(openid);
		session.getBasicRemote().sendText("连接已断开");
	}

	@OnMessage
	public void OnMessage(String message, @PathParam("id") String id) throws IOException {
		String msg=objectMapper.writeValueAsString(new Msg(message,System.currentTimeMillis(),openid,nickname));
		Session session=map.get(id);
		if (session!=null){
			session.getBasicRemote().sendText(msg);
			redisTemplate.opsForList().rightPush("Read-"+id,msg);
		}else {
			redisTemplate.opsForList().rightPush("Unread-"+id,msg);
		}
	}
}
