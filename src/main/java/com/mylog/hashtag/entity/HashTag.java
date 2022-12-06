package com.mylog.hashtag.entity;

import com.mylog.base.entity.BaseEntity;
import com.mylog.post.entity.Post;
import com.mylog.keyword.entity.Keyword;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class HashTag extends BaseEntity {
    @ManyToOne
    private Post post;

    @ManyToOne
    private Keyword keyword;
}
