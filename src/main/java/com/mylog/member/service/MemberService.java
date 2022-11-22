package com.mylog.member.service;

import com.mylog.member.dto.MemberDto;
import com.mylog.member.entity.Member;
import com.mylog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

}
