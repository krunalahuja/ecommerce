package com.ecommerce.service.userservice;

import com.ecommerce.dto.user.loginrequestdto;
import com.ecommerce.dto.user.userregisterdto;
import com.ecommerce.dto.user.userresponsedto;
import com.ecommerce.entity.userentity;
import com.ecommerce.enums.role;
import com.ecommerce.repository.orderrepository;
import com.ecommerce.repository.userrepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class adminservice {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private userrepository repository;

    @Autowired
    private orderrepository orderrepo;

    private Set<String> loggedInAdmins = new HashSet<>();

    public userregisterdto registeradmin(userregisterdto userregisterdto) {
        userentity user = convertToEntity(userregisterdto);
        user.setRole(role.ADMIN);
        userentity saved = repository.save(user);
        return convertToDTO(saved);
    }

    public loginrequestdto login(loginrequestdto login) {
        userentity user = repository.findByEmail(login.getEmail());
        if (user != null && user.getRole() == role.ADMIN) {
            if (user.getPassword().equals(login.getPassword())) {
                loggedInAdmins.add(login.getEmail());
                return mapper.map(user, loginrequestdto.class);
            } else {
                throw new RuntimeException("Incorrect password for admin email: " + login.getEmail());
            }
        } else {
            throw new RuntimeException("Admin user not found with email: " + login.getEmail());
        }
    }

    public String logout(String email) {
        if (loggedInAdmins.contains(email)) {
            loggedInAdmins.remove(email);
            return "Admin " + email + " logged out successfully.";
        } else {
            throw new RuntimeException("Admin is not logged in.");
        }
    }

    public void checkLogin(String email) {
        if (!loggedInAdmins.contains(email)) {
            throw new RuntimeException("Access denied. Admin " + email + " is not logged in.");
        }
    }

    public List<userregisterdto> registerusers(List<userregisterdto> userregisterdtos, String adminEmail) {
        checkLogin(adminEmail);

        List<userentity> entities = userregisterdtos.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());

        List<userentity> savedEntities = repository.saveAll(entities);
        return savedEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<userresponsedto> viewallusers(String adminEmail) {
        checkLogin(adminEmail);

        List<userentity> users = repository.findAll();
        return users.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public userresponsedto findUserById(Long id, String adminEmail) {
        checkLogin(adminEmail);

        userentity user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return convertToResponseDTO(user);
    }

    public String deleteall(String adminEmail) {
        checkLogin(adminEmail);

        repository.deleteAll();
        return "All users deleted successfully!";
    }

    public String deleteUserById(Long id, String adminEmail) {
        checkLogin(adminEmail);

        userentity user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        repository.delete(user);
        return "User deleted successfully!";
    }

    public userregisterdto convertToDTO(userentity user) {
        return mapper.map(user, userregisterdto.class);
    }

    public userresponsedto convertToResponseDTO(userentity user) {
        return mapper.map(user, userresponsedto.class);
    }

    public userentity convertToEntity(userregisterdto dto) {
        return mapper.map(dto, userentity.class);
    }
}

