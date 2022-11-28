package com.mylog.answer.service;

import com.mylog.answer.dto.AnswerDto;
import com.mylog.answer.entity.Answer;
import com.mylog.answer.repository.AnswerRepository;
import com.mylog.base.dto.DtoUt;
import com.mylog.member.dto.MemberDto;
import com.mylog.member.entity.Member;
import com.mylog.post.dto.PostDto;
import com.mylog.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    //생성 메서드
    public void create(String content, PostDto postDto, MemberDto memberDto) {
        Post post = DtoUt.toEntity(postDto);
        Member member = DtoUt.toEntity(memberDto);

        Answer answer = Answer.builder()
                .content(content)
                .member(member)
                .post(post)
                .createDate(LocalDateTime.now())
                .build();
        answerRepository.save(answer);
    }


    //조회 - 전체, id, post, member
    public List<AnswerDto> getAll() {
        return answerRepository.findAll().stream().map(i -> DtoUt.toDto(i)).toList();
    }

    public AnswerDto getById(long id) {
        return DtoUt.toDto(answerRepository.findById(id).orElse(null));
    }

    public AnswerDto getByPost(PostDto postDto) {
        return DtoUt.toDto(answerRepository.findByPostId(postDto.getId()).orElse(null));
    }

    public AnswerDto getByMember(MemberDto memberDto) {
        return DtoUt.toDto(answerRepository.findByMemberId(memberDto.getId()).orElse(null));
    }

    //수정 메서드
    public void modify(AnswerDto answerDto, String content) {
        Answer answer = answerRepository.findById(answerDto.getId()).orElse(null);

        answer.setContent(content);
        answerRepository.save(answer);
    }

    //삭제 메서드
    public void delete(AnswerDto answerDto) {
        Answer answer = answerRepository.findById(answerDto.getId()).orElse(null);

        answerRepository.delete(answer);
    }
}
