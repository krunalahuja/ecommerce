package com.ecommerce.controller;

import com.ecommerce.dto.product.productdeletedto;
import com.ecommerce.dto.product.productinsertdto;
import com.ecommerce.dto.product.productupdatedto;
import com.ecommerce.dto.user.changepassworddto;
import com.ecommerce.dto.user.deleteuserdto;
import com.ecommerce.dto.user.loginrequestdto;
import com.ecommerce.dto.user.sellerdto;
import com.ecommerce.service.productservice.productservice;
import com.ecommerce.service.userservice.sellerservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.ecommerce.service.userservice.userservice;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
public class sellercontroller {

    @Autowired
    private sellerservice sellerservice;

    @Autowired
    private productservice productservice;

    @Autowired
    private userservice userservice;

    @PostMapping("/register")
    public ResponseEntity<?> registerSeller(@RequestBody sellerdto dto) {
        return ResponseEntity.ok(sellerservice.registerseller(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginrequestdto loginDto) {
        try {
            return ResponseEntity.ok(sellerservice.login(loginDto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String email) {
        try {
            return ResponseEntity.ok(sellerservice.logout(email));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/addproducts")
    public ResponseEntity<?> createproduct(@RequestParam String email, @RequestBody productinsertdto dto) {
        try {
            sellerservice.checkLogin(email);
            return ResponseEntity.ok(productservice.createProduct(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PutMapping("/updateproducts")
    public ResponseEntity<?> updateproduct(@RequestParam String email, @RequestBody productupdatedto dto) {
        try {
            sellerservice.checkLogin(email);
            return ResponseEntity.ok(productservice.updateProductDetails(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @DeleteMapping("/products")
    public ResponseEntity<?> deleteproduct(@RequestParam String email, @RequestBody productdeletedto dto) {
        try {
            sellerservice.checkLogin(email);
            return ResponseEntity.ok(productservice.deleteProduct(dto));
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
