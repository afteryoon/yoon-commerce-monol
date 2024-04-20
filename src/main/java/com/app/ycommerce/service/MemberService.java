package com.app.ycommerce.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.ycommerce.dto.MemberSignUpDTO;
import com.app.ycommerce.dto.VerificationEmailDto;
import com.app.ycommerce.entity.Member;
import com.app.ycommerce.repository.MemberRepository;
import com.app.ycommerce.repository.RedisRepository;

@Service
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JavaMailSender mailSender;
	private final RedisRepository redisRepository;

	public MemberService(MemberRepository memberRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
		JavaMailSender emailSender, RedisRepository redisRepository) {

		this.memberRepository = memberRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.mailSender = emailSender;
		this.redisRepository = redisRepository;

	}

	//회원 로그인 -이메일 중복 확인 후 저장
	public Member signUp(MemberSignUpDTO memberSignUpDTO) {
		String email = memberSignUpDTO.getEmail();

		checkDuplication(email);

		if ((redisRepository.verifyVerificationCode
			(memberSignUpDTO.getEmail(), memberSignUpDTO.getVerificationCode()))) {

			Member member = Member.builder()
				.email(memberSignUpDTO.getEmail())
				.password(bCryptPasswordEncoder.encode(memberSignUpDTO.getPassword()))
				.name(memberSignUpDTO.getName())
				.phone(memberSignUpDTO.getPhone())
				.address(memberSignUpDTO.getAddress())
				.role("ROLE_MEMBER")
				.createdAt(LocalDate.from(LocalDateTime.now()))
				.build();

			return memberRepository.save(member);
		}
		//        ModelMapper modelMapper = new ModelMapper();
		//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		//        Member member = modelMapper.map(memberSignUpDTO, Member.class);

		throw new RuntimeException("잘못된 인증 코드입니다.");
	}

	//회원 코드 전송
	public void sendVerificationEmail(String email, String verificationCode) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("khsso102649@gmail.com");
		mailMessage.setTo(email);
		mailMessage.setSubject("회원가입 인증 코드");
		mailMessage.setText(verificationCode);

		mailSender.send(mailMessage);
		redisRepository.saveVerificationCode(email, verificationCode);
	}

	//인증코드 랜덤 6자리 생성
	public String generateVerificationCode() {
		int min = 100000;
		int max = 999999;
		Random random = new Random();
		int randomNumber = random.nextInt(max - min + 1) + min;

		return String.valueOf(randomNumber);
	}

	//email 인증코드 저장 및 중복체크
	public VerificationEmailDto verificationEmail(String email) {

		checkDuplication(email);

		String verificationCode = generateVerificationCode();
		sendVerificationEmail(email, verificationCode);

		VerificationEmailDto verificationEmailDto = VerificationEmailDto.builder()
			.verificationCode(verificationCode)
			.build();

		return verificationEmailDto;
	}

	public void checkDuplication(String email) {

		if (memberRepository.existsByEmail(email))
			throw new IllegalArgumentException("이미 존재하는 이메일입니다.");

	}

}
