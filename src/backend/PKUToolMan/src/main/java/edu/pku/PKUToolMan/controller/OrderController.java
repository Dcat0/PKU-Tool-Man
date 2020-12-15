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
        } catch (Exception e) {
            e.printStackTrace();
            return Result.RESPONSE_ERROR().message("create order failed, unable to insert");
        }
        // TODO: 这里其实可以考虑返回order.orderId: order.getOrderId()
        return Result.SUCCESS();
    }

    @PostMapping("/myorderlist")
    public Result getMyOrderList(@RequestBody Map<String, Object> map) {
        int userId = (Integer)map.get("userID");
        List<Order> orderList;
        try {
            orderList = orderService.getMyOrderList(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.RESPONSE_ERROR().message("get myOrderList failed");
        }
        return Result.SUCCESS().data("orders", orderList);
    }

    // 加入了是否有重复接单、订单已结束、已取消之类的检查
    @PostMapping("/receive")
    public Result receiveOrder(@RequestBody Map<String, Object> map) {
        int orderId = (Integer)map.get("orderID");
        int toolManId = (Integer)map.get("toolManID");
        Order order;
        try {
            order = orderService.queryOrder(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.RESPONSE_ERROR().message("query order failed when receiving order");
        }
        if (order.getToolManId() != -1 || order.getState() != OrderState.CREATED.ordinal()) {
            return Result.AUTH_ERROR().message("duplicated receiving or finished/cancelled/deleted order");
        }

        order.setToolManId(toolManId);
        order.setState(OrderState.EXECUTING.ordinal());
        try {
            orderService.updateOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.RESPONSE_ERROR().message("update order failed when receiving order");
        }
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
