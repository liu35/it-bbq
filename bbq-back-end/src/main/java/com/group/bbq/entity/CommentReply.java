package com.group.bbq.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bbq_comment_reply")
public class CommentReply {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String username;
    private String avatar;
    private Long commentId;
    private Long replyId;
    private String replyName;
    private Integer replyNumber;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
