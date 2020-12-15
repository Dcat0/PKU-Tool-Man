package edu.pku.PKUToolMan.dao;

import edu.pku.PKUToolMan.entity.Order;

import java.util.List;

public interface OrderDAO {
    void createOrder(Order order);
    List<Order> getMyOrderList(int userId);
    void updateOrder(Order order);
    // void receiveOrder(int orderId, int toolManId);
    // void completeOrder(int orderId);
    void deleteOrder(int orderId);
    Order queryOrder(int orderId);
    List<Order> getAllCreatedOrderList();

    void cancelOrder(int userId, int orderId);
    void sendReport();
    void updateState(int orderId, int userId, int newState);
}
