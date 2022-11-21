package com.mylog.base.initData;

import com.mylog.post.service.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "test"})
public class TestInitData {
    @Bean
    CommandLineRunner init(
            PostService postService
    ) {
        return args -> {
            postService.create("subject1", "content1");
            postService.create("subject2", "content2");
            postService.create("subject3", "content3");
        };
    }
}
