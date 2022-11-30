package com.mylog.series.entity;

import com.mylog.base.entity.BaseEntity;
import com.mylog.member.entity.Member;
import com.mylog.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Series extends BaseEntity {
    private String subject;

    @OneToMany
    private List<Post> postList;

    @ManyToOne
    private Member member;
}
