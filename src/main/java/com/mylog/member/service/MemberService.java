package com.mylog.member.service;

import com.mylog.base.dto.DtoUt;
import com.mylog.base.exception.DataNotFoundException;
import com.mylog.base.util.Ut;
import com.mylog.member.dto.MemberDto;
import com.mylog.member.entity.Member;
import com.mylog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    //회원 생성
    public void create(String username, String password, String email, String nickname) {
        Member member = Member.builder()
                .nickname(nickname)
                .username(username)
                .password(passwordEncoder.encode(password))
                .createDate(LocalDateTime.now())
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
        member.setUpdateDate(LocalDateTime.now());

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

    public boolean passwordConfirm(String oldPassword, MemberDto memberDto) {
        return passwordEncoder.matches(oldPassword, memberDto.getPassword());
    }

    public void modifyProfileImg(MemberDto memberDto, MultipartFile profileImg) {
        Member member = getByDto(memberDto);

        String profileImgDirName = "member/" + Ut.date.getCurrentDateFormatted("yyyy_MM_dd");

        String ext = Ut.file.getExt(profileImg.getOriginalFilename());

        String fileName = UUID.randomUUID() + "." + ext;
        String profileImgDirPath = genFileDirPath + "/" + profileImgDirName;
        String profileImgFilePath = profileImgDirPath + "/" + fileName;

        new File(profileImgDirPath).mkdirs(); // 폴더가 혹시나 없다면 만들어준다.
        try {
            profileImg.transferTo(new File(profileImgFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String profileImgRelPath = profileImgDirName + "/" + fileName;

        member.setProfileImg(profileImgRelPath);

        memberRepository.save(member);
    }
}
