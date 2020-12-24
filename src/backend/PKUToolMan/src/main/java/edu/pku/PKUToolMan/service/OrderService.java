package edu.pku.PKUToolMan.service;

import edu.pku.PKUToolMan.entity.Order;

import java.util.List;

public interface OrderService {
    void createOrder(Order order);
    List<Order> getMyOrderList(int userId);
    void updateOrder(Order order);
    void deleteOrder(int orderId);
    Order queryOrder(int orderId);
    List<Order> getAllCreatedOrderList();
}
