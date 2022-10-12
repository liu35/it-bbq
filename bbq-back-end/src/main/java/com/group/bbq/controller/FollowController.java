package com.group.bbq.controller;


import com.group.bbq.common.lang.Result;
import com.group.bbq.mapper.FollowMapper;
import com.group.bbq.service.FollowService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * user follow table
 * </p>
 */
@RestController
@RequestMapping("/follow")
public class FollowController {


    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @RequiresAuthentication
    @GetMapping("/add")
    public Result follow(Long authorId) {
        return followService.add(authorId);
    }

    @RequiresAuthentication
    @DeleteMapping("/delete")
    public Result unfollow(Long authorId) {
        return followService.delete(authorId);
    }
}
