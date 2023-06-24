package com.example.blog;

import com.example.blog.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogAppApisApplicationTests {

	@Autowired
	private UserRepo userRepo;

	@Test
	void contextLoads() {
	}

	@Test
	public void repoTest(){
		System.out.println(userRepo.getClass().getName());
		System.out.println(userRepo.getClass().getPackage());
	}

}
