package com.group.bbq.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group.bbq.common.lang.Result;
import com.group.bbq.entity.Follow;
import com.group.bbq.mapper.FollowMapper;
import com.group.bbq.service.FollowService;
import com.group.bbq.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * user follow table
 * </p>
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {

    private final FollowMapper followMapper;

    @Autowired
    public FollowServiceImpl(FollowMapper followMapper) {
        this.followMapper = followMapper;
    }

    @Override
    public Result add(Long authorId) {
        if (ObjectUtil.isNotEmpty(getFollow(authorId))) {
            return Result.fail("You're already following the user");
        }

        Follow follow = new Follow();
        follow.setFollowed(authorId);
        follow.setFollower(ShiroUtil.getProfile().getId());
        follow.setCreateTime(LocalDateTime.now());
        followMapper.insert(follow);
        return Result.succ(null);
    }

    @Override
    public Result delete(Long authorId) {
        Follow follow = getFollow(authorId);

        if (ObjectUtil.isEmpty(follow)) {
            return Result.fail("You're not following the user anymore");
        }
        followMapper.deleteById(follow.getId());
        return Result.succ(null);
    }

    private Follow getFollow(Long authorId) {
        return followMapper.selectOne(new QueryWrapper<Follow>()
                .eq("followed", authorId)
                .eq("follower", ShiroUtil.getProfile().getId()));
    }
}
