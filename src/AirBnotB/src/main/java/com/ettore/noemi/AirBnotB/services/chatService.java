/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.services;

import com.ettore.noemi.AirBnotB.DTOs.chatMessagePreviewDTO;
import com.ettore.noemi.AirBnotB.DTOs.userDTO;
import com.ettore.noemi.AirBnotB.authUtils.JwtUtil;
import com.ettore.noemi.AirBnotB.models.chatMessage;
import com.ettore.noemi.AirBnotB.models.user;
import com.ettore.noemi.AirBnotB.repositories.messageRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.SecurityProperties.User;
import org.springframework.stereotype.Service;

/**
 *
 * @author ettore
 */
@Service
public class chatService {
    @Autowired
    JwtUtil jwtUtil;
    
    @Autowired
    userService _user;
    
    @Autowired
    messageRepository messageRepo;
    
    @Autowired
    bookingService bookings;

    
    public List<chatMessagePreviewDTO> GetMessageInboxPreview(String authToken) {
        userDTO _userDTO = new userDTO();
        _userDTO.setUsername(jwtUtil.extractUsername(authToken));
        user userToFetch = _user.fetchUserByUsername(_userDTO);
        return messageRepo.FetchMessagePreviews(userToFetch.getId());
    }
    
    public void sendMessageToChat (String authToken, long reservationID, String Message) {
        //fetch utente
        userDTO _userDTO = new userDTO();
        _userDTO.setUsername(jwtUtil.extractUsername(authToken));
        user userToFetch = _user.fetchUserByUsername(_userDTO);
        //check se ha una chat
        if (messageRepo.isUserApartOfChat(userToFetch.getId(), reservationID) > 0) {
            //k send vro
            chatMessage msg = new chatMessage();
            msg.setBooking(bookings.getBookingFromID(reservationID));
            msg.setMessage(Message);
            msg.setSender(userToFetch);
            msg.setSentTime(LocalDate.now());
            messageRepo.save(msg);
        } else {
            //pdd
        }
    }
    public List<chatMessage> getMessagesFromChat(String authToken, long reservationID) {
         //fetch utente
        userDTO _userDTO = new userDTO();
        _userDTO.setUsername(jwtUtil.extractUsername(authToken));
        user userToFetch = _user.fetchUserByUsername(_userDTO);
        //check se ha una chat
        if (messageRepo.isUserApartOfChat(userToFetch.getId(), reservationID) > 0) {
            return messageRepo.getChatMessages(userToFetch.getId(), reservationID);
        } else {
            return null;
        }
    }
    public void setReadReceipt(String authToken, long reservationID) {
        userDTO _userDTO = new userDTO();
        _userDTO.setUsername(jwtUtil.extractUsername(authToken));
        user userToFetch = _user.fetchUserByUsername(_userDTO);
        //check se ha una chat
        if (messageRepo.isUserApartOfChat(userToFetch.getId(), reservationID) > 0) {
            messageRepo.setReadReceipt(userToFetch.getId(), reservationID);
        }
    }
    
    public long getNotificationCount(String authToken) {
        userDTO _userDTO = new userDTO();
        _userDTO.setUsername(jwtUtil.extractUsername(authToken));
        user userToFetch = _user.fetchUserByUsername(_userDTO);
        return messageRepo.getNotificationsCount(userToFetch.getId());
    }
}
