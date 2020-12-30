package edu.pku.PKUToolMan;

import edu.pku.PKUToolMan.controller.OrderController;
import edu.pku.PKUToolMan.dao.OrderDAO;
import edu.pku.PKUToolMan.dao.UserDAO;
import edu.pku.PKUToolMan.entity.Order;
import edu.pku.PKUToolMan.entity.OrderState;
import edu.pku.PKUToolMan.entity.User;
import edu.pku.PKUToolMan.service.UserServiceImpl;
import edu.pku.PKUToolMan.service.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

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

@SpringBootTest
class OrderDAOTest {
	@Autowired
	private OrderDAO orderDAO;

	@Test
	public void createOrderTest() {
		Order order = new Order(1, "近邻宝", "农园", LocalDateTime.now(),
				LocalDateTime.now().plusDays(1),"无", "带饭");
		try {
			orderDAO.createOrder(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Order order1;
		try {
			order1 = orderDAO.queryOrder(order.getOrderId());
			assert order1.equals(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getMyOrderListTest() {
		int userId = 3;
		List<Order> orders;
		// several entries
		try {
			orders = orderDAO.getMyOrderList(userId);
			for(Order curOrder: orders)
				assert curOrder.getUserId() == userId || curOrder.getToolManId() == userId;
			//System.out.println(orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 0 entry
		userId = -2;
		try {
			orders = orderDAO.getMyOrderList(userId);
			assert orders.size() == 0;
			//System.out.println(orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateOrderTest() {
		int orderId = 3;
		Order order = new Order(12, 1, 3, "近邻宝", "农园",
				LocalDateTime.now(), LocalDateTime.now().plusDays(1),"无", 1, "带饭");
		try {
			orderDAO.updateOrder(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(orderDAO.queryOrder(3));
	}

	@Test
	public void deleteOrderTest() {
		int orderId = 10;
		try{
			orderDAO.deleteOrder(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void queryOrderTest() {
		int orderId = 10;
		try{
			System.out.println(orderDAO.queryOrder(orderId));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getAllCreatedOrderListTest() {
		List<Order> orders;
		try{
			orders = orderDAO.getAllCreatedOrderList();
			System.out.println(orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

@SpringBootTest
class OrderServiceImplTest {
	@Autowired
	private OrderServiceImpl orderService;

	@Test
	public void createOrder() {
		Order order = new Order(1, "近邻宝", "农园", LocalDateTime.now(),
				LocalDateTime.now().plusDays(1),"无", "带饭");
		try {
			orderService.createOrder(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int orderId = order.getOrderId();
		Order order1;
		try {
			order1 = orderService.queryOrder(orderId);
			assert order1.equals(order);
			//System.out.println(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getMyOrderList() {
		int userId = 3;
		List<Order> orders;
		// several entries
		try {
			orders = orderService.getMyOrderList(userId);
			for(Order curOrder: orders)
				assert curOrder.getUserId() == userId || curOrder.getToolManId() == userId;
			//System.out.println(orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 0 entry
		userId = -2;
		try {
			orders = orderService.getMyOrderList(userId);
			assert orders.size() == 0;
			//System.out.println(orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateOrderTest() {
		int orderId = 3;
		Order order = new Order(12, 1, 3, "近邻宝", "农园",
				LocalDateTime.now(), LocalDateTime.now().plusDays(1),"无", 1, "带饭");
		try {
			orderService.updateOrder(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(orderService.queryOrder(3));
	}

	@Test
	public void deleteOrderTest() {
		int orderId = 5;
		try{
			orderService.deleteOrder(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void queryOrder() {
		int orderId = 5;
		System.out.println(orderService.queryOrder(orderId));
	}

	@Test
	public void getAllCreatedOrderList() {
		List<Order> orders = orderService.getAllCreatedOrderList();
		for(Order order : orders) {
			assert order.getState() == OrderState.CREATED.ordinal();
		}
	}

}