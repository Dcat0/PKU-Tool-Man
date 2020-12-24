package edu.pku.PKUToolMan;

import edu.pku.PKUToolMan.dao.UserDAO;
import edu.pku.PKUToolMan.entity.User;
import edu.pku.PKUToolMan.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
	public void createTest() {
		User user = new User("liuliuliu", "123123",
				"yunhui@pku.edu.cn", "18501850185");
		try {
			userDAO.create(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			user = userDAO.queryByNickname("liuliuliu");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
	}

	@Test
	public void queryByIdTest() {
		User user;

		// exists
		user = null;
		try {
			user = userDAO.queryById(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);

		// not exists
		user = null;
		try {
			user = userDAO.queryById(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
	}

	@Test
	public void queryByNicknameTest() {
		User user;

		// exists
		user = null;
		try {
			user = userDAO.queryByNickname("liuyunhui");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);

		// not exists
		user = null;
		try {
			user = userDAO.queryByNickname("liutwohui");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
	}

	@Test
	public void queryByEmailTest() {
		User user;

		// exists
		user = null;
		try {
			user = userDAO.queryByEmail("liu@pku.edu.cn");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);

		// not exists
		user = null;
		try {
			user = userDAO.queryByEmail("liuliu@pku.edu.cn");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
	}

	@Test
	public void queryByPhoneNumTest() {
		User user;

		// exists
		user = null;
		try {
			user = userDAO.queryByPhoneNum("18501232877");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);

		// not exists
		user = null;
		try {
			user = userDAO.queryByPhoneNum("18512345678");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
	}

	@Test
	public void modifyWithTest() {
		User modifyUser = new User(
				4, "liuliuliu",
				"123123",
				"yunhui@pku.edu.cn", "18501850185"
		);
		User modifiedUser = null;

		// modify into
		try {
			modifyUser.setNickname("liuliu");
			userDAO.modifyWith(modifyUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			modifiedUser = userDAO.queryById(modifyUser.getid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(modifiedUser);

		// modify back
		try {
			modifyUser.setNickname("liuliuliu");
			userDAO.modifyWith(modifyUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			modifiedUser = userDAO.queryById(modifyUser.getid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(modifiedUser);
	}
}

@SpringBootTest
class UserServiceImplTest {
	@Autowired
	private UserServiceImpl userService;

	@Test
	public void createTest() {
		User user = new User(
				"yunyunyun", "123123",
				"liuliu@pku.edu.cn", "18522342234"
		);
		try {
			userService.create(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			user = userService.queryByNickname("yunyunyun");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
	}

	@Test
	public void queryByIdTest() {
		User user;

		// exists
		user = null;
		try {
			user = userService.queryById(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);

		// not exists
		user = null;
		try {
			user = userService.queryById(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
	}

	@Test
	public void queryByNicknameTest() {
		User user;

		// exists
		user = null;
		try {
			user = userService.queryByNickname("liuyunhui");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);

		// not exists
		user = null;
		try {
			user = userService.queryByNickname("yunhuiliu");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
	}

	@Test
	public void queryByEmailTest() {
		User user;

		// exists
		user = null;
		try {
			user = userService.queryByEmail("liu@pku.edu.cn");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);

		// not exists
		user = null;
		try {
			user = userService.queryByEmail("huihui@pku.edu.cn");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
	}

	@Test
	public void queryByPhoneNumTest() {
		User user;

		// exists
		user = null;
		try {
			user = userService.queryByPhoneNum("18501232877");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);

		// not exists
		user = null;
		try {
			user = userService.queryByEmail("18500000000");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(user);
	}

	@Test
	public void modifyWithTest() {
		User modifyUser = new User(
				5, "yunyunyun", "123123",
				"liuliu@pku.edu.cn", "18522342234"
		);
		User modifiedUser = null;

		// modify into
		try {
			modifyUser.setNickname("liuliuliu");
			userService.modifyWith(modifyUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			modifiedUser = userService.queryById(modifyUser.getid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(modifiedUser);

		// modify back
		try {
			modifyUser.setNickname("yunyunyun");
			userService.modifyWith(modifyUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			modifiedUser = userService.queryById(modifyUser.getid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(modifiedUser);
	}
}