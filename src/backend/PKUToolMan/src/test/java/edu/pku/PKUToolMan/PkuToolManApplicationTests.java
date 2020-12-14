package edu.pku.PKUToolMan;

import edu.pku.PKUToolMan.Controller.UserController;
import edu.pku.PKUToolMan.DAO.UserDAO;
import edu.pku.PKUToolMan.Entity.User;
import edu.pku.PKUToolMan.Service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@SpringBootTest
class ApplicationTest {
	@Autowired
	DataSource dataSource;

	@Test
	public void connectionTest() {
		System.out.println(dataSource.getClass());
	}
}

@SpringBootTest
class UserDAOTest {
	@Autowired
	private UserDAO userDAO;

	@Test
	public void queryByIdTest() {
		User user = null;
		try {
			user = userDAO.queryById(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
	}

	@Test
	public void queryByEmailTest() {
		User user = null;
		try {
			user = userDAO.queryByEmail("liu@pku.edu.cn");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
	}
}

@SpringBootTest
class UserServiceImplTest {
	@Autowired
	private UserServiceImpl userService;

	@Test
	public void queryByIdTest() {
		User user = null;
		try {
			user = userService.queryById(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
	}

	@Test
	public void queryByEmailTest() {
		User user = null;
		try {
			user = userService.queryByEmail("liu@pku.edu.cn");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
	}
}