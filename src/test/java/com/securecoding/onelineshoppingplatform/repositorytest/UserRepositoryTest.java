
package com.securecoding.onelineshoppingplatform.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.securecoding.onelineshoppingplatform.Model.User;
import com.securecoding.onelineshoppingplatform.repository.UserRepository;

@DataJpaTest

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

@RunWith(SpringRunner.class)

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	private User user;

	@Autowired
	private TestEntityManager entityManager;

	@Test

	@DisplayName("check whether the user data is created ")
	public void testCreateUser() {
		user = new User();
		user.setEmail("ravikumar@gmail.com");
		user.setPassword("bsdfhbuirebef"); // user.setPasswordConfirm("ravi2020");
		user.setFirstName("PavanSai");
		user.setLastName("Atukuri");
		user.setRole("USER");
		user.setConfirmed(1);
		userRepository.save(user);

		User peristedUser = entityManager.find(User.class, user.getId());

		assertThat(peristedUser.getId()).isEqualTo(user.getId());

		userRepository.delete(user);

	}

	@Test
	public void testDeleteUser() {

		user = new User();
		user.setEmail("ravikumar@gmail.com");
		user.setPassword("bsdfhbuirebef"); // user.setPasswordConfirm("ravi2020");
		user.setFirstName("PavanSai");
		user.setLastName("Atukuri");
		user.setRole("USER");
		userRepository.save(user);

		userRepository.delete(user);

		User existUser = entityManager.find(User.class, user.getId());

		assertThat(existUser).isEqualTo(null);

	}

	

	
	@AfterAll
	public void destroy() {
		userRepository.delete(user);
	}

}
