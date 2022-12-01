package com.mylog.member.service;

import com.mylog.config.MemberContext;
import com.mylog.member.entity.Member;
import com.mylog.member.repository.MemberRepository;
import com.mylog.member.role.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        if(optionalMember.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        Member member = optionalMember.get();
        List<GrantedAuthority> authorityList = new ArrayList<>();
        if("admin".equals(username)) {
            authorityList.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        } else  {
            authorityList.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
        }
        return new MemberContext(member, authorityList);
    }
}