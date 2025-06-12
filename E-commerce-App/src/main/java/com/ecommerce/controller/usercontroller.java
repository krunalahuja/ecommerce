package com.ecommerce.controller;

import com.ecommerce.dto.order.cancelorderdto;
import com.ecommerce.dto.order.orderrequestdto;
import com.ecommerce.dto.product.productsummarydto;
import com.ecommerce.dto.user.changepassworddto;
import com.ecommerce.dto.user.deleteuserdto;
import com.ecommerce.dto.user.loginrequestdto;
import com.ecommerce.dto.user.userregisterdto;
import com.ecommerce.service.orderservice.orderservice;
import com.ecommerce.service.productservice.productservice;
import com.ecommerce.service.userservice.userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class usercontroller {

    @Autowired
    private userservice userservice;

    @Autowired
    private orderservice orderservice;

    @Autowired
    private productservice productservice;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody userregisterdto dto) {
        return ResponseEntity.ok(userservice.registeruser(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginrequestdto loginDto) {
        try {
            return ResponseEntity.ok(userservice.login(loginDto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String email) {
        try {
            String result = userservice.logout(email);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/products")
    public ResponseEntity<List<productsummarydto>> viewProductSummary() {
        return ResponseEntity.ok(productservice.getAllProductSummaries());
    }

    @PostMapping("/orders")
    public ResponseEntity<?> placeorder(@RequestBody orderrequestdto order) {
        try {
            userservice.checkLogin(order.getEmail());
            return ResponseEntity.ok(orderservice.order(order));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/orders/cancel")
    public ResponseEntity<?> cancelorder(@RequestBody cancelorderdto cancel) {
        try {
            userservice.checkLogin(cancel.getEmail());
            return ResponseEntity.ok(orderservice.cancelorder(cancel));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @GetMapping("/products/search")
    public ResponseEntity<?> searchproducts(@RequestParam String name) {
        try {
            return ResponseEntity.ok(productservice.searchProductsByName(name));
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/change")
    public ResponseEntity<?> changepassword(@RequestBody changepassworddto p_change){
        try{
            return ResponseEntity.ok(userservice.change(p_change));
        } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/users/delete")
    public ResponseEntity<?> deleteUser(@RequestBody deleteuserdto deleteDto) {
        try {
            String result = userservice.delete(deleteDto);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}


