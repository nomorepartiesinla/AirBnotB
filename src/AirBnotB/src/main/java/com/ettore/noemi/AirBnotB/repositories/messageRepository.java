/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.repositories;

/**
 *
 * @author ettore
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.ettore.noemi.AirBnotB.DTOs.chatMessagePreviewDTO;
import com.ettore.noemi.AirBnotB.models.chatMessage;
import com.ettore.noemi.AirBnotB.models.image;
import com.ettore.noemi.AirBnotB.models.rental;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ettore.noemi.AirBnotB.models.image;
import com.ettore.noemi.AirBnotB.models.rental;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author ettore
 */
public interface messageRepository extends JpaRepository<chatMessage, Long> {
   
    @Query(value = """
            SELECT
                b.id            AS booking_id,
                b.check_in,
                b.check_out,
                b.status,
            
                r.rental_id,
                r.listing_name,
            
                lm.messageid,
                lm.message,
                lm.sender_id
            FROM booking b
            INNER JOIN rental r
                ON r.rental_id = b.rental_id
            
            LEFT JOIN chat_message lm
                ON lm.messageid = (
                    SELECT cm2.messageid
                    FROM chat_message cm2
                    WHERE cm2.reservation = b.id
                    ORDER BY cm2.sent_time DESC
                    LIMIT 1
                )
            
            WHERE b.user_id = :user
               OR r.id = :user
            
            ORDER BY lm.sent_time DESC;
            """, nativeQuery = true)
    List<chatMessagePreviewDTO> FetchMessagePreviews(long user);



    
    
    @Query(value="""
                 SELECT EXISTS (                                        
                       SELECT 1
                       FROM booking b
                       JOIN rental r ON b.rental_id = r.rental_id
                       WHERE b.id = :reservationId
                         AND (b.user_id = :userId OR r.id = :userId)
                   ) AS can_send;
                 
           """, nativeQuery=true)
    int isUserApartOfChat(long userId, long reservationId);
    
    
    @Query(value="""
                    SELECT
                        cm.messageid,
                        cm.message,
                        cm.sent_time,
                        cm.sender_id,
                        cm.reservation,
                        cm.has_message_been_read  -- <- aggiunta qui
                    FROM booking b
                    INNER JOIN rental r ON r.rental_id = b.rental_id
                    INNER JOIN chat_message cm ON cm.reservation = b.id
                    WHERE b.id = :bookingid
                      AND (b.user_id = :userid OR r.id = :userid)
                    ORDER BY cm.sent_time ASC;
                 """, nativeQuery=true)
    List<chatMessage> getChatMessages(long userid, long bookingid);
    
    @Query (value = """
                        UPDATE `chat_message` 
                        SET `has_message_been_read`= true
                        WHERE reservation = :convo AND NOT sender_id = :user
                    """, nativeQuery = true)
    void setReadReceipt(long user, long convo);
    
    @Query(value = """
            SELECT COUNT(*) FROM (SELECT
                
                b.id            AS booking_id,
                b.check_in,
                b.check_out,
                b.status,
            
                r.rental_id,
                r.listing_name,
            
                lm.messageid,
                lm.message,
                lm.sender_id,
                lm.has_message_been_read
            FROM booking b
            INNER JOIN rental r
                ON r.rental_id = b.rental_id
            
            LEFT JOIN chat_message lm
                ON lm.messageid = (
                    SELECT cm2.messageid
                    FROM chat_message cm2
                    WHERE cm2.reservation = b.id
                    ORDER BY cm2.sent_time DESC
                    LIMIT 1
                )
            
            WHERE (b.user_id = :user
               OR r.id = :user) AND (lm.has_message_been_read = 0) AND (lm.sender_id <> :user)
            
            ORDER BY lm.sent_time DESC) threadsWithNewMessages
                  
            """, nativeQuery = true)
    long getNotificationsCount(long user);
}
