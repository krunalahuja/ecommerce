package com.ecommerce.controller;

import com.ecommerce.dto.user.changepassworddto;
import com.ecommerce.dto.user.loginrequestdto;
import com.ecommerce.service.orderservice.orderservice;
import com.ecommerce.service.productservice.productservice;
import com.ecommerce.service.userservice.adminservice;
import com.ecommerce.service.userservice.userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
public class admincontroller {

    @Autowired
    private adminservice adminservice;

    @Autowired
    private productservice productservice;

    @Autowired
    private orderservice orderservice;

    @Autowired
    private userservice userservice;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginrequestdto loginDto) {
        try {
            return ResponseEntity.ok(adminservice.login(loginDto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String email) {
        try {
            return ResponseEntity.ok(adminservice.logout(email));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getallusers(@RequestParam String email) {
        try {
            adminservice.checkLogin(email);
            return ResponseEntity.ok(adminservice.viewallusers(email));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteuser(@RequestParam String email, @PathVariable Long id) {
        try {
            adminservice.checkLogin(email);

            return ResponseEntity.ok(adminservice.deleteUserById(id,email));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/products")
    public ResponseEntity<?> getproductsummary(@RequestParam String email) {
        try {
            adminservice.checkLogin(email);
            return ResponseEntity.ok(productservice.getAllProductSummaries());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<?> getordersummary(@RequestParam String email, @PathVariable Long orderId) {
        try {
            adminservice.checkLogin(email);
            return ResponseEntity.ok(orderservice.orderList(orderId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getusersummary(@RequestParam String email, @PathVariable Long id) {
        try {
            adminservice.checkLogin(email);
            return ResponseEntity.ok(adminservice.findUserById(id,email));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<?> searchproducts(@RequestParam String name, @RequestParam String email) {
        try {
            adminservice.checkLogin(email);
            return ResponseEntity.ok(productservice.searchProductsByName(name));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
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
}


