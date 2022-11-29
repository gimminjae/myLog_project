package com.mylog.answer.entity;

import com.mylog.base.entity.BaseEntity;
import com.mylog.member.entity.Member;
import com.mylog.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Answer extends BaseEntity {
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Member member;
}
