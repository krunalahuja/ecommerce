package com.ecommerce.service.orderservice;

import com.ecommerce.dto.order.cancelorderdto;
import com.ecommerce.dto.order.orderitemdto;
import com.ecommerce.dto.order.orderrequestdto;
import com.ecommerce.dto.order.orderresponse;
import com.ecommerce.entity.orderentity;
import com.ecommerce.entity.productentity;
import com.ecommerce.entity.userentity;
import com.ecommerce.enums.status;
import com.ecommerce.repository.orderrepository;
import com.ecommerce.repository.productrepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.repository.userrepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class orderservice {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private userrepository repository;

    @Autowired
    private productrepository productrepo;

    @Autowired
    private orderrepository orderrepo;

    public orderresponse order(orderrequestdto order) {
        userentity user = repository.findByEmail(order.getEmail());

        if (user == null || !user.getEmail().equals(order.getEmail())) {
            throw new RuntimeException("Invalid user email");
        }

        double totalAmount = 0.0;

        for (Map.Entry<Long, Integer> entry : order.getItems().entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            if (quantity <= 0) {
                throw new RuntimeException("Invalid quantity for product ID: " + productId);
            }

            productentity product = productrepo.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product with ID " + productId + " not found"));

            if (product.getStock() < quantity) {
                throw new RuntimeException("Not enough stock for product ID " + productId +
                        ". Available: " + product.getStock() +
                        ", Requested: " + quantity);
            }

            int updatedStock = product.getStock() - quantity;
            product.setStock(updatedStock);

            productrepo.save(product);

            totalAmount += product.getPrice() * quantity;
        }

        orderresponse response = new orderresponse();
        response.setEmail(order.getEmail());
        response.setTotalAmount(totalAmount);
        response.setShippingAddress(order.getShippingAddress());
        response.setPaymentMethod(order.getPaymentMethod());

        return response;
    }


    public String cancelorder(cancelorderdto cancel) {
        userentity user = repository.findByEmail(cancel.getEmail());
        if (user == null || !user.getEmail().equals(cancel.getEmail())) {
            throw new RuntimeException("Invalid user email");
        }
        
        Optional<orderentity> optionalOrder = orderrepo.findById(cancel.getOrderid());
        if (optionalOrder.isEmpty()) {
            throw new RuntimeException("Order with ID " +cancel.getOrderid() + " not found");
        }

        orderentity order = optionalOrder.get();
        
        if (!order.getUserid().getEmail().equals(cancel.getEmail())) {
            throw new RuntimeException("Order does not belong to the given user");
        }
        
        order.setStatus(status.CANCELLED); 
        orderrepo.save(order);

        return "Order with ID " + cancel.getOrderid() + " has been cancelled successfully.";
    }

    public List<orderitemdto> orderList(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }

        List<orderitemdto> items = orderrepo.findByOrderid(orderId);

        if (items.isEmpty()) {
            throw new RuntimeException("There are no pending orders for the given ID.");
        }

        return items;
    }
}