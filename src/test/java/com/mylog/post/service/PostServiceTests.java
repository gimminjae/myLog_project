package com.mylog.post.service;

import com.mylog.post.dto.PostDto;
import com.mylog.post.entity.Post;
import com.mylog.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Rollback
public class PostServiceTests {
    @Autowired
    private PostService postService;
    @
    @BeforeEach
    void before() {
        String subject = "subject";
        String content = "content";
    }

    @Test
    @DisplayName("글 작성")
    void test1() {

        PostDto postDto = postService.create("subject", "content").getData();

        List<PostDto> postDtos = postService.getAll().getData();
        assertThat(postDtos.size()).isEqualTo(1);
    }
    @Test
    @DisplayName("글 조회(by id, subject)")
    void test2() {
        PostDto postDto = postService.create("subject", "content").getData();

        PostDto postDto1 = postService.getById(postDto.getId()).getData();
        assertThat(postDto1.getSubject()).isEqualTo("subject");

        PostDto postDto2 = postService.getBySubject(postDto.getSubject()).getData();
        assertThat(postDto1.getSubject()).isEqualTo("subject");
        assertThat(postDto1.getContent()).isEqualTo("content");
    }
    @Test
    @DisplayName("글 수정")
    void test3() {
        PostDto postDto = postService.create("subject", "content").getData();

        postService.modify(postDto.getId(), "modify subject", "modify content").getData();

        PostDto postDto1 = postService.getById(postDto.getId()).getData();

        assertThat(postDto1.getSubject()).isEqualTo("modify subject");
        assertThat(postDto1.getContent()).isEqualTo("modify content");
    }
    @Test
    @DisplayName("글 삭제")
    void test4() {
        PostDto postDto = postService.create("subject", "content").getData();

        List<PostDto> postDtos = postService.getAll().getData();
        assertThat(postDtos.size()).isEqualTo(1);

        postService.delete(postDto.getId());

        postDtos = postService.getAll().getData();
        assertThat(postDtos.size()).isEqualTo(0);
    }
}
