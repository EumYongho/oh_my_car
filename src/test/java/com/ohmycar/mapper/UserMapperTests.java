package com.ohmycar.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ohmycar.domain.AuthVO;
import com.ohmycar.domain.UserVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/security-context.xml" })
@Log4j
public class UserMapperTests {

	@Autowired
	private UserMapper mapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void testGetUserList() {
		List<UserVO> list = mapper.getUserList();

		list.forEach(user -> log.info(user));
	}

	/* 회원가입 테스트 */
	@Test
	public void testJoinUser() {
		/* String encodedPassword = passwordEncoder.encode("1234"); */
		UserVO vo = new UserVO();
		vo.setUserId("user01");
		vo.setUserName("박유저");
		vo.setPassword("1234");
		vo.setEmail("useruser@gmail.com");
		vo.setNickName("저유박");

		AuthVO authvo = new AuthVO();
		authvo.setUserId("user01");
		authvo.setAuth("ROLE_MEMBER");

		int result = mapper.joinUser(vo) + mapper.joinUserAuth(authvo);
		log.info(result);
	}

	@Test
	public void testGetUserByUserid() {
		UserVO vo = mapper.getUserByUserId("user01");
		log.info(vo);
		log.info(vo.getAuthList());
	}

	@Test
	public void testGetUserByEmail() {
		String email = "useruser@gmail.com";
		log.info(mapper.getUserByEmail(email));
	}

	@Test
	public void testUpdateUser() {
		UserVO vo = mapper.getUserByEmail("useruser@gmail.com");
		vo.setPassword("1234");
		vo.setNickName("수정수정");

		int result = mapper.updateUser(vo);
		log.info(result);

	}

	@Test
	public void testDeleteUser() {
		int result = mapper.deleteUser("user6");
		log.info(result);
	}

	@Test
	public void testGetPassword() {
		String userid = "user1";
		String pwd = mapper.userPasswordCheckByUserId(userid);
		log.info(pwd);
	}

	@Test
	public void testEncode() {
		log.info(passwordEncoder.encode("1234"));
	}

	@Test
	public void testIdDup() {
		if (mapper.getUserByUserId("user1") != null) {
			log.info("이미 있는 아이디입니다.");
		} else {
			log.info("사용 가능한 아이디입니다.");
		}
	}
}
