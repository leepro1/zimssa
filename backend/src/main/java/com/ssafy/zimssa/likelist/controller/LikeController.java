package com.ssafy.zimssa.likelist.controller;

import com.ssafy.zimssa.likelist.model.dto.Like;
import com.ssafy.zimssa.likelist.model.service.LikeService;
import java.sql.SQLException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/list")
@Slf4j
public class LikeController {


    LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/likes")
    ResponseEntity<String> likeHouse(@RequestBody Like like) throws SQLException {
        System.out.println(like);
            likeService.insert(like);

        return ResponseEntity.ok("success");
    }


    @GetMapping("/getLikes/{userId}")
    public ResponseEntity<List<Like>> getLikes(@PathVariable("userId") String userId) throws SQLException {
        List<Like> likeList = likeService.list(userId);
        System.out.println(likeList);
        for(Like likeitem:likeList)
        {
            System.out.println(likeitem);
        }
        System.out.println();
        return ResponseEntity.ok(likeList);
    }
    @DeleteMapping("/deleteLikes/{house_name}")
    public ResponseEntity<String> deleteLikes(@PathVariable("house_name") String house_name) throws SQLException {
       likeService.delete(house_name);

        return ResponseEntity.ok("sucess");
    }

}
