package com.microsevice.ePharmaMS.user.service;

import com.microsevice.ePharmaMS.user.DTO.ReqRes;
import com.microsevice.ePharmaMS.user.modal.User;
import com.microsevice.ePharmaMS.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UsersManagementService {
    @Autowired
    private UserRepository usersRepo;
    @Autowired
    private JwtService jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public ReqRes register(ReqRes registrationRequest){
        ReqRes resp = new ReqRes();

        try {
            User ourUser = new User();
            if (usersRepo.existsByEmail(registrationRequest.getEmail())) {
                resp.setStatusCode(400);
                resp.setMessage("Email đã được sử dụng. Vui lòng chọn email khác.");
                return resp;
            }
            ourUser.setEmail(registrationRequest.getEmail());
            ourUser.setAddress(registrationRequest.getAddress());
            ourUser.setRole("USER");
            ourUser.setName(registrationRequest.getName());
            ourUser.setGender(registrationRequest.getGender());
            ourUser.setPhone(registrationRequest.getPhone());
            ourUser.setCreatedAt(LocalDateTime.now());
            ourUser.setUpdatedAt(LocalDateTime.now());
            ourUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            User ourUsersResult = usersRepo.save(ourUser);
            if (ourUsersResult.getId()>0) {
                resp.setUser((ourUsersResult));
                resp.setMessage("Lưu thành công User");
                resp.setStatusCode(200);
            }

        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }


    public ReqRes login(ReqRes loginRequest){
        ReqRes response = new ReqRes();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword()));
            var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setRefreshToken(refreshToken);
            response.setMessage("Thành công đăng nhập");

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }





    public ReqRes refreshToken(ReqRes res){
        ReqRes response = new ReqRes();
        try{
            String ourEmail = jwtUtils.extractUsername(res.getToken());
            User users = usersRepo.findByEmail(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(res.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(res.getToken());
                response.setMessage("Tải lại token thành công");
            }
            response.setStatusCode(200);
            return response;

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }


    public ReqRes getAllUsers() {
        ReqRes reqRes = new ReqRes();

        try {
            List<User> result = usersRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setUserList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Thành công");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Ko tìm thấy user");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }


    public ReqRes getUsersById(Long id) {
        ReqRes reqRes = new ReqRes();
        try {
            User usersById = usersRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
            reqRes.setUser(usersById);
            reqRes.setStatusCode(200);
            reqRes.setMessage("Users with id '" + id + "' found successfully");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes deleteUser(Long userId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<User> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                usersRepo.deleteById(userId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes updateUser(Long userId, User updatedUser) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<User> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                User existingUser = userOptional.get();
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setRole(updatedUser.getRole());
                existingUser.setPhone(updatedUser.getPhone());
                existingUser.setAddress(updatedUser.getAddress());
                existingUser.setGender(updatedUser.getGender());
                existingUser.setName(updatedUser.getName());
                existingUser.setUpdatedAt(LocalDateTime.now());

                // Check if password is present in the request
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    // Encode the password and update it
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                User savedUser = usersRepo.save(existingUser);
                reqRes.setUser(savedUser);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes getMyInfo(String email){
        ReqRes reqRes = new ReqRes();
        try {
            Optional<User> userOptional = usersRepo.findByEmail(email);
            if (userOptional.isPresent()) {
                reqRes.setUser(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;

    }

    public List<User> listAll() {
        return usersRepo.findAll();
    }
}

