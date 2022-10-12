package com.group.bbq.service;

import com.group.bbq.common.lang.Result;
import com.group.bbq.entity.Posts;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * posts table service
 * </p>
 */
public interface PostsService extends IService<Posts> {

    Result indexList(Integer pageNum, Integer pageSize, String search, Long typeId);

    Result indexDetail(Long id);

    Result indexEdit(Posts posts);

    Result approval(Long postId, Integer click);

    Result views(Long postId);

    Result delete(Long postId);

    Result setOfficial(Long postId);

    Result deleteOfficial(Long postId);

    Result setTop(Long postId);

    Result deleteTop(Long postId);

    Result setMarrow(Long postId);

    Result deleteMarrow(Long postId);

    Result setPostsUnSeen(Long postId);

    Result setPostsSeen(Long postId);

    Result allList(Integer pageNum, Integer pageSize, String search, Long typeId);
}
