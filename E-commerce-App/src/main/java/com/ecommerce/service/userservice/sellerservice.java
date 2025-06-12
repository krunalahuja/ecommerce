package com.ecommerce.service.userservice;

import com.ecommerce.dto.user.sellerdto;
import com.ecommerce.dto.user.loginrequestdto;
import com.ecommerce.dto.user.userresponsedto;
import com.ecommerce.dto.user.userupdatedto;
import com.ecommerce.entity.userentity;
import com.ecommerce.entity.sellerentity;
import com.ecommerce.enums.role;
import com.ecommerce.repository.userrepository;
import com.ecommerce.repository.sellerrepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class sellerservice {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private userrepository repository;

    @Autowired
    private sellerrepository sellerRepository;

    private Set<String> loggedInSellers = new HashSet<>();

    public sellerdto registerseller(sellerdto sellerdto) {
        sellerentity seller = mapper.map(sellerdto, sellerentity.class);
        sellerentity savedSeller = sellerRepository.save(seller);
        return mapper.map(savedSeller, sellerdto.class);
    }

    public loginrequestdto login(loginrequestdto login) {
        userentity user = repository.findByEmail(login.getEmail());
        if (user != null && user.getRole() == role.SELLER) {
            if (user.getPassword().equals(login.getPassword())) {
                loggedInSellers.add(user.getEmail());
                return mapper.map(user, loginrequestdto.class);
            } else {
                throw new RuntimeException("Incorrect password for seller email: " + login.getEmail());
            }
        } else {
            throw new RuntimeException("Seller not found with email: " + login.getEmail());
        }
    }

    public String logout(String email) {
        if (loggedInSellers.contains(email)) {
            loggedInSellers.remove(email);
            return "Seller " + email + " logged out successfully.";
        } else {
            throw new RuntimeException("Seller is not logged in.");
        }
    }

    public void checkLogin(String email) {
        if (!loggedInSellers.contains(email)) {
            throw new RuntimeException("Access denied. Seller " + email + " is not logged in.");
        }
    }

    public userresponsedto viewsellerprofile(String email) {
        checkLogin(email);
        userentity user = repository.findByEmail(email);
        if (user != null && user.getRole() == role.SELLER) {
            return convertToResponseDTO(user);
        } else {
            throw new RuntimeException("Seller not found with email: " + email);
        }
    }

    public userresponsedto updatesellerprofile(userupdatedto updateDTO) {
        checkLogin(updateDTO.getEmail());
        userentity user = repository.findByEmail(updateDTO.getEmail());
        if (user != null && user.getRole() == role.SELLER) {
            user.setFirstName(updateDTO.getFirstName());
            user.setLastName(updateDTO.getLastName());
            user.setPhone(updateDTO.getPhone());
            userentity savedUser = repository.save(user);
            return convertToResponseDTO(savedUser);
        } else {
            throw new RuntimeException("Seller not found with email: " + updateDTO.getEmail());
        }
    }

    public sellerdto convertToDTO(userentity user) {
        return mapper.map(user, sellerdto.class);
    }

    public userresponsedto convertToResponseDTO(userentity user) {
        return mapper.map(user, userresponsedto.class);
    }

    public userentity convertToEntity(sellerdto dto) {
        return mapper.map(dto, userentity.class);
    }
}
