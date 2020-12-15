package edu.pku.PKUToolMan.controller;

import edu.pku.PKUToolMan.entity.Order;
import edu.pku.PKUToolMan.entity.OrderState;
import edu.pku.PKUToolMan.service.OrderServiceImpl;
import edu.pku.PKUToolMan.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("/add")
    public Result createOrder(@RequestBody Map<String, Object> map) {
        int userId = (Integer)map.get("userID");
        String place = map.get("place").toString();
        String destination = map.get("destination").toString();
        String description = map.get("description").toString();
        // get startTime
        Order order = new Order(userId, place, destination, description);
        order.setState(OrderState.CREATED.ordinal());

        try {

            orderService.createOrder(order);
            System.out.println("orderId=" + order.getOrderId());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(order.toString());
            return Result.RESPONSE_ERROR().message("create order failed, unable to insert");
        }

        return Result.SUCCESS();
    }

    @PostMapping("/myOrderList")
    public Result getMyOrderList(@RequestBody Map<String, Object> map) {
        int userId = (Integer)map.get("userID");
        // List<Order> orderList = orderService.getMyOrderList(userId);
        // return Result.SUCCESS().data("orders", orderList);
        return Result.SUCCESS();
    }

    @PostMapping("/receive")
    public Result receiveOrder(int orderId, int toolManId) {
        Order order = orderService.queryOrder(orderId);
        order.setToolManId(toolManId);
        order.setState(OrderState.EXECUTING.ordinal());
        orderService.updateOrder(order);
        return Result.SUCCESS();
    }

    @PostMapping("/complete")
    public Result completeOrder(int orderId) {
        Order order = orderService.queryOrder(orderId);
        order.setState(OrderState.FINISHED.ordinal());
        orderService.updateOrder(order);
        return Result.SUCCESS();
    }

    @PostMapping("/delete")
    public Result deleteOrder(@RequestBody Map<String, Object> map) {
        return Result.SUCCESS();
    }

}
