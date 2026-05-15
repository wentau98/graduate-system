package com.secondhand.common;

/**
 * Order status constant
 */
public interface OrderStatus {
    int WAIT_PAY = 1;        // Pending payment
    int WAIT_DELIVER = 2;    // Pending shipment
    int WAIT_RECEIVE = 3;    // Pending receipt
    int COMPLETED = 4;       // Completed
    int CANCELLED = 5;       // Cancelled
}