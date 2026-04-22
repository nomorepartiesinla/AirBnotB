/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.controllers;

import com.ettore.noemi.AirBnotB.DTOs.chatMessagePreviewDTO;
import com.ettore.noemi.AirBnotB.services.chatService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ettore
 */
@RestController
@RequestMapping("/api/chat")
public class chatController {
    @Autowired
    chatService chat;
    @PostMapping("/getChatPreview")
    public ResponseEntity<List<chatMessagePreviewDTO>> getChatPreview(@CookieValue(value = "authToken", required = false) String jwtToken) {
        return ResponseEntity.ok(chat.GetMessageInboxPreview(jwtToken));
    }
    
    @PostMapping("/{ConversationID}/sendMessage") 
    public ResponseEntity<?> sendMessageToChat(@PathVariable long ConversationID, @CookieValue(value = "authToken", required = false) String jwtToken, @RequestBody String msg) {
        chat.sendMessageToChat(jwtToken, ConversationID, msg);
        return ResponseEntity.ok("Invio ok!");
    }
    
    @PostMapping("/{ConversationID}/DownloadMessages")
    public ResponseEntity<?> sendMessageToChat(@PathVariable long ConversationID, @CookieValue(value = "authToken", required = false) String jwtToken) {
        return ResponseEntity.ok(chat.getMessagesFromChat(jwtToken, ConversationID));
    }
    
    @GetMapping("/{ConversationID}/ReadReceipt")
    public ResponseEntity<?> sendReadReceipt(@PathVariable long ConversationID, @CookieValue(value = "authToken", required = false) String jwtToken) {
        chat.setReadReceipt(jwtToken, ConversationID);
        return ResponseEntity.ok("settato il visualizzato");
    }
    
    @GetMapping("/getNotificationCount")
    public ResponseEntity<?> getNotificationCount(@CookieValue(value = "authToken", required = false) String jwtToken) {
        return ResponseEntity.ok(Map.of("notifications", chat.getNotificationCount(jwtToken)));
    }

}
