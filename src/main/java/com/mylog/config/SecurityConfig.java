package com.mylog.config;

import com.mylog.base.util.Ut;
import com.mylog.member.service.MemberSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final MemberSecurityService memberSecurityService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/member/login") //로그인 url
                .defaultSuccessUrl("/?msg=%s".formatted(Ut.url.encode("환영합니다!")))
                .failureUrl("/member/login?errorMsg=%s".formatted(Ut.url.encode("아이디 혹은 비밀번호를 확인하세요.")))//로그인 성공시 이동할 url
                .and()
                .logout()
                //로그아웃 url
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/") //로그아웃 성공시 이동할 url
                .invalidateHttpSession(true) //로그아웃시 생성된 세션 삭제 활성화
        ;
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}