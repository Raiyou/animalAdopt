package com.lovepet.animal.controller;


import com.lovepet.animal.dto.MessageQueryParams;
import com.lovepet.animal.model.UserFeedback;
import com.lovepet.animal.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class PersonalMessage {

    @Autowired
    MessageService messageService;

    @PostMapping ("/message")
    public ResponseEntity createMessage(@RequestBody MessageQueryParams messageQueryParams){

            messageService.createMessage(messageQueryParams);

            return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/message")
    public ResponseEntity<List<UserFeedback>> getMessage(@RequestParam Integer tId){
        MessageQueryParams messageQueryParams=new MessageQueryParams();
        messageQueryParams.settId(tId);

        List<UserFeedback> userFeedbacks =   messageService.getMessage(messageQueryParams);
           if (userFeedbacks!=null){
               return ResponseEntity.status(HttpStatus.OK).body(userFeedbacks);
           }
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}