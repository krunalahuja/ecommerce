package com.ecommerce.controller;


import com.ecommerce.dto.product.productreviewdto;
import com.ecommerce.dto.product.reviewresponsedto;
import com.ecommerce.service.productservice.productservice;
import com.ecommerce.service.userservice.userservice;
import com.ecommerce.service.userservice.sellerservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class reviewcontroller {

    @Autowired
    private productservice reviewService;

    @Autowired
    private userservice userservice;

    @Autowired
    private sellerservice sellerservice;

    @Autowired
    private productservice productservice;

    @PostMapping("/submit/{productId}")
    public ResponseEntity<?> submitReview(@PathVariable Long productId,
                                          @RequestParam String email,
                                          @RequestBody productreviewdto reviewDTO) {
        try {
            userservice.checkLogin(email);
            reviewService.submitreview(productId, email, reviewDTO);
            return ResponseEntity.ok("Review submitted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<reviewresponsedto>> getReviewsForProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProductId(productId));
    }

    @GetMapping("/seller")
    public ResponseEntity<?> getSellerReviews(@RequestParam String email) {
        try {
            sellerservice.checkLogin(email); // check if seller is logged in
            return ResponseEntity.ok(reviewService.getReviewsForSeller(email));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}

