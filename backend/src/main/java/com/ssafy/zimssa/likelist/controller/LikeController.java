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
@RequestMapping("/jjim")
@Slf4j
public class LikeController {


    LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping()
    ResponseEntity<String> likeHouse(@RequestBody Like like) throws SQLException {
            likeService.insert(like);

        return ResponseEntity.ok("success");
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<Like>> getLikes(@PathVariable("userId") String userId) throws SQLException {
        List<Like> likeList = likeService.list(userId);
               return ResponseEntity.ok(likeList);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLikes(@PathVariable("id") int id) throws SQLException {
        System.out.println(id);
       likeService.delete(id);

        return ResponseEntity.ok("sucess");
    }

}
