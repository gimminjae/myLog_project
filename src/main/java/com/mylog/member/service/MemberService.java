package com.mylog.member.service;

import com.mylog.base.dto.DtoUt;
import com.mylog.base.exception.DataNotFoundException;
import com.mylog.member.dto.MemberDto;
import com.mylog.member.entity.Member;
import com.mylog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    //회원 생성
    public void create(String username, String password, String email, String nickname) {
        Member member = Member.builder()
                .nickname(nickname)
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        memberRepository.save(member);
    }

    //모든 회원 조회
    public List<MemberDto> getAll() {
        return memberRepository.findAll().stream().map(i -> DtoUt.toDto(i)).toList();
    }

    //id로 회원 조회
    public MemberDto getById(long id) {
        Member member = memberRepository.findById(id).orElse(null);
        if(member == null) throw new DataNotFoundException("회원을 찾을 수 없습니다.");

        return DtoUt.toDto(member);
    }

    //아이디로 회원 조회
    public MemberDto getByUsername(String username) {
        Member member = memberRepository.findByUsername(username).orElse(null);
        if(member == null) throw new DataNotFoundException("회원을 찾을 수 없습니다.");

        return DtoUt.toDto(member);
    }

    //이메일로 회원 조회
    public MemberDto getByEmail(String email) {
        Member member = memberRepository.findByEmail(email).orElse(null);
        if(member == null) throw new DataNotFoundException("회원을 찾을 수 없습니다.");

        return DtoUt.toDto(member);
    }

    //회원 정보 수정(이메일, 닉네임)
    public void modify(MemberDto memberDto, String email, String nickname) {
        Member member = getByDto(memberDto);
        if(member == null) throw new DataNotFoundException("회원을 찾을 수 없습니다.");

        member.setEmail(email);
        member.setNickname(nickname);

        memberRepository.save(member);
    }

    //dto가 들어오면 member 엔티티로 찾아 반환하기
    private Member getByDto(MemberDto memberDto) {
        return memberRepository.findById(memberDto.getId()).orElse(null);
    }

    //회원 삭제
    public void delete(MemberDto memberDto) {
        Member member = getByDto(memberDto);

        memberRepository.delete(member);
    }

    //비밀번호 변경
    public void modifyPassword(MemberDto memberDto, String password) {
        Member member = getByDto(memberDto);

        member.setPassword(passwordEncoder.encode(password));

        memberRepository.save(member);
    }
}
