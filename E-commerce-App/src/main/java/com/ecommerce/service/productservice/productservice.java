package com.ecommerce.service.productservice;

import com.ecommerce.dto.product.*;
import com.ecommerce.entity.productentity;
import com.ecommerce.entity.reviewentity;
import com.ecommerce.entity.userentity;
import com.ecommerce.repository.productrepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.repository.userrepository;
import com.ecommerce.repository.reviewrepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class productservice {

    @Autowired
    private productrepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private userrepository userrepository;

    @Autowired
    private reviewrepository reviewrepository;

    public String createProduct(productinsertdto dto) {
        productentity product = mapper.map(dto, productentity.class);
        product.setIsavailable(true);
        productRepository.save(product);
        return "Product created successfully";
    }

    public String deleteProduct(productdeletedto dto) {
        productentity product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getEmail().equals(dto.getEmail()) ||
                !product.getSellerpassword().equals(dto.getSellerpassword())) {
            throw new RuntimeException("Unauthorized seller credentials");
        }

        productRepository.delete(product);
        return "Product deleted successfully";
    }

    public String updateProductDetails(productupdatedto dto) {
        productentity product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getEmail().equals(dto.getEmail()) ||
                !product.getSellerpassword().equals(dto.getSellerpassword())) {
            throw new RuntimeException("Unauthorized seller credentials");
        }

        product.setPrice(dto.getNewPrice());
        product.setStock(dto.getNewStock());

        productRepository.save(product);

        return "Product updated successfully";
    }

    public List<productsummarydto> getAllProductSummaries() {
        List<productentity> products = productRepository.findAll();

        return products.stream()
                .map(product -> mapper.map(product, productsummarydto.class))
                .collect(Collectors.toList());
    }
    public List<productsummarydto> searchProductsByName(String name) {
        List<productentity> products = productRepository.findByProductnameContainingIgnoreCase(name);
        return products.stream()
                .map(product -> mapper.map(product, productsummarydto.class))
                .collect(Collectors.toList());
    }
    public void submitreview(Long productId, String username, productreviewdto reviewDTO) {
        Optional<productentity> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            throw new RuntimeException("Product not found");
        }
        productentity product = optionalProduct.get();

        userentity user = userrepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        reviewentity review = new reviewentity();
        review.setProductid(product);
        review.setUser(user);
        review.setRating(reviewDTO.getRating().intValue());
        review.setComment(reviewDTO.getComment());

        reviewrepository.save(review);
    }

    public List<reviewresponsedto> getReviewsByProductId(Long productId) {
        List<reviewentity> reviews = reviewrepository.findByProductProductid(productId);

        return reviews.stream().map(review -> new reviewresponsedto(
                review.getUser().getUsername(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        )).collect(Collectors.toList());
    }

    public List<reviewresponsedto> getReviewsForSeller(String email) {
        List<productentity> products = productRepository.findByEmail(email);

        List<reviewresponsedto> reviewList = new ArrayList<>();

        for (productentity product : products) {
            List<reviewentity> productReviews = reviewrepository.findByProductProductid(product.getProductid());

            for (reviewentity review : productReviews) {
                reviewList.add(new reviewresponsedto(
                        review.getUser().getUsername(),
                        review.getRating(),
                        review.getComment(),
                        review.getCreatedAt()
                ));
            }
        }

        return reviewList;
    }

}