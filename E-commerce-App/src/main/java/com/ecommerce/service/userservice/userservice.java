package com.ecommerce.service.userservice;

import com.ecommerce.dto.user.*;
import com.ecommerce.entity.userentity;
import com.ecommerce.enums.role;
import com.ecommerce.repository.userrepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class userservice {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private userrepository repository;

    private Set<String> loggedInUsers = new HashSet<>();

    public userregisterdto registeruser(userregisterdto userregisterdto) {
        userentity user = convertToEntity(userregisterdto);
        user.setRole(role.CUSTOMER);
        userentity saved = repository.save(user);
        return convertToDTO(saved);
    }

    public loginrequestdto login(loginrequestdto login) {
        userentity user = repository.findByEmail(login.getEmail());
        if (user != null && user.getRole() == role.CUSTOMER) {
            if (user.getPassword().equals(login.getPassword())) {
                loggedInUsers.add(user.getEmail());
                return mapper.map(user, loginrequestdto.class);
            } else {
                throw new RuntimeException("Incorrect password for email: " + login.getEmail());
            }
        } else {
            throw new RuntimeException("User not registered with this email: " + login.getEmail());
        }
    }


    public String logout(String email) {
        if (loggedInUsers.contains(email)) {
            loggedInUsers.remove(email);
            return "User " + email + " logged out successfully.";
        } else {
            throw new RuntimeException("User is not logged in.");
        }
    }

    public void checkLogin(String email) {
        if (!loggedInUsers.contains(email)) {
            throw new RuntimeException("Access denied. User " + email + " is not logged in.");
        }
    }

    public userupdatedto update(userupdatedto updateDTO) {
        checkLogin(updateDTO.getEmail());
        userentity user = repository.findByEmail(updateDTO.getEmail());

        if (user != null && user.getRole() == role.CUSTOMER) {
            user.setFirstName(updateDTO.getFirstName());
            user.setLastName(updateDTO.getLastName());
            user.setPhone(updateDTO.getPhone());
            userentity savedUser = repository.save(user);
            return mapper.map(savedUser, userupdatedto.class);
        } else {
            throw new RuntimeException("User not found or unauthorized.");
        }
    }


    public changepassworddto change(changepassworddto change) {
        checkLogin(change.getEmail());
        userentity user = repository.findByEmail(change.getEmail());

        if (user != null && user.getRole() == role.CUSTOMER) {
            if (user.getPassword().equals(change.getCurrentPassword())) {
                user.setPassword(change.getNewPassword());
                userentity savedUser = repository.save(user);
                return mapper.map(savedUser, changepassworddto.class);
            } else {
                throw new RuntimeException("Incorrect current password.");
            }
        } else {
            throw new RuntimeException("User not found or unauthorized.");
        }
    }


    public String delete(deleteuserdto delete) {
        checkLogin(delete.getEmail());
        userentity user = repository.findByEmail(delete.getEmail());

        if (user != null && user.getRole() == role.CUSTOMER) {
            if (user.getPassword().equals(delete.getPassword())) {
                loggedInUsers.remove(delete.getEmail());
                repository.delete(user);
                return "User deleted successfully!";
            } else {
                throw new RuntimeException("Incorrect password.");
            }
        } else {
            throw new RuntimeException("User not found or unauthorized.");
        }
    }


    public String view(usersummarydto view) {
        checkLogin(view.getEmail());
        userentity user = repository.findByEmail(view.getEmail());

        if (user != null && user.getRole() == role.CUSTOMER) {
            return "User ID: " + user.getUserid() +
                    " | First Name: " + user.getFirstName() +
                    " | Last Name: " + user.getLastName() +
                    " | Email: " + user.getEmail();
        } else {
            throw new RuntimeException("User not found or unauthorized.");
        }
    }


    public userregisterdto convertToDTO(userentity user) {
        return mapper.map(user, userregisterdto.class);
    }

    public userentity convertToEntity(userregisterdto dto) {
        return mapper.map(dto, userentity.class);
    }
}

