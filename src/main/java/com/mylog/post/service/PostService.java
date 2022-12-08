package com.mylog.post.service;

import com.mylog.base.dto.DtoUt;
import com.mylog.base.dto.RsData;
import com.mylog.base.exception.DataNotFoundException;
import com.mylog.base.util.CommonUtil;
import com.mylog.hashtag.service.HashTagService;
import com.mylog.member.dto.MemberDto;
import com.mylog.post.dto.PostDto;
import com.mylog.post.entity.Post;
import com.mylog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.color.ICC_Profile;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final HashTagService hashTagService;
    public PostDto create(String subject, String content, String tagStr,MemberDto memberDto) {
        Post post = Post.builder()
                .createDate(LocalDateTime.now())
                .subject(subject)
                .content(content)
                .contentHtml(CommonUtil.markdown(content))
                .member(DtoUt.toEntity(memberDto))
                .build();
        postRepository.save(post);

        hashTagService.applyHashTags(post, tagStr);

        return DtoUt.toDto(post);
    }

    public PostDto create(String subject, String content, MemberDto memberDto) {
        Post post = Post.builder()
                .createDate(LocalDateTime.now())
                .subject(subject)
                .content(content)
                .contentHtml(CommonUtil.markdown(content))
                .member(DtoUt.toEntity(memberDto))
                .build();
        postRepository.save(post);

        return DtoUt.toDto(post);
    }
    public PostDto create(String subject, String content) {
        Post post = Post.builder()
                .createDate(LocalDateTime.now())
                .subject(subject)
                .content(content)
                .contentHtml(CommonUtil.markdown(content))
                .build();
        postRepository.save(post);

        return DtoUt.toDto(post);
    }

    public List<PostDto> getAll() {
        return postRepository.findAll().stream().map(i -> DtoUt.toDto(i)).toList();
    }
    public Page<PostDto> getAll(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 9, Sort.by(sorts));
        return postRepository.findAll(pageable).map(i -> DtoUt.toDto(i));
        //        return postRepository.findByMemberId(pageable, memberDto.getId()).map(i -> DtoUt.toDto(i));
    }

    public PostDto getById(long id) {
        Post post = postRepository.findById(id).orElse(null);

        return post != null ? DtoUt.toDto(post) : null;
    }

    public PostDto getBySubject(String subject) {
        Post post = postRepository.findBySubject(subject).orElse(null);

        return post != null ? DtoUt.toDto(post) : null;

    }

    public PostDto modify(PostDto postDto, String subject, String content) {
        Post post = findById(postDto.getId());

        if(post == null) throw new DataNotFoundException("데이터를 찾을 수 없습니다.");

        post.setSubject(subject);
        post.setContent(content);
        post.setUpdateDate(LocalDateTime.now());
        post.setContentHtml(CommonUtil.markdown(content));
        postRepository.save(post);

        return DtoUt.toDto(post);
    }
    public PostDto modify(PostDto postDto, String subject, String content, String tagStr) {
        PostDto postDto2 = modify(postDto, subject, content);

        Post post = findById(postDto.getId());

        postRepository.save(post);

        hashTagService.applyHashTags(post, tagStr);

        return DtoUt.toDto(post);
    }

    public void delete(PostDto postDto) {
        Post post = findById(postDto.getId());

        postRepository.delete(post);
    }
    private Post findById(long id) {
        return postRepository.findById(id).orElse(null);
    }

    public Page<PostDto> getByMember(int page, MemberDto memberDto) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return postRepository.findByMemberId(pageable, memberDto.getId()).map(i -> DtoUt.toDto(i));
//        Page<Post> posts = postRepository.findByMemberId(pageable, memberDto.getId());
//        List<PostDto> postDtos = new ArrayList<>();
//        for(Post post : posts) postDtos.add(DtoUt.toDto(post));

//        return postDtos;
    }
}
