package com.secondhand.common;

/**
 * Refund status constant
 */
public interface RefundStatus {
    int UNDER_AUDIT = 1;     // Pending review
    int AGREED = 2;          // Agreed
    int REJECTED = 3;        // Rejected
    int REFUNDED = 4;        // Refunded
}