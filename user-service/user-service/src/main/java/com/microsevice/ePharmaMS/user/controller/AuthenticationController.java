//package com.microsevice.ePharmaMS.user.controller;
//
//import com.microsevice.ePharmaMS.user.auth.AuthenticationRequest;
//import com.microsevice.ePharmaMS.user.auth.AuthenticationResponse;
//import com.microsevice.ePharmaMS.user.modal.User;
//import com.microsevice.ePharmaMS.user.repository.UserRepository;
//import com.microsevice.ePharmaMS.user.service.JwtService;
//import com.microsevice.ePharmaMS.user.service.OurUserDetailsService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/auth")
//public class AuthenticationController {
//    private final AuthenticationManager authenticationManager;
//    private final UserRepository userRepository;
//    private final JwtService jwtService;
//    private final OurUserDetailsService userDetailsService;
//
//    private final PasswordEncoder passwordEncoder;
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
//        // Xác thực thông tin người dùng
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//        );
//
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        UserDetails userDetails = jwtService.convertToUserDetails(user);
//
//        String accessToken = jwtService.generateToken(userDetails);
//        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(),userDetails);
//
//        return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken));
//    }
//
//        @PostMapping("/register")
//    public ResponseEntity<?> register(@Valid @RequestBody AuthenticationRequest request) {
//        // Kiểm tra email đã tồn tại chưa
//        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
//            return ResponseEntity.badRequest().body("Email đã được sử dụng");
//        }
//
//        // Tạo user mới với vai trò mặc định là USER
//        User newUser = new User();
//        newUser.setUserName(request.getUserName());
//        newUser.setRole(request.getRole());
//        newUser.setEmail(request.getEmail());
//        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
//
//
//        try{
//            User savedUser = userRepository.save(newUser);
//
//            // Tạo UserDetails từ user vừa lưu
//            UserDetails userDetails = jwtService.convertToUserDetails(savedUser);
//            // Tạo JWT token
//            String accessToken = jwtService.generateToken(userDetails);
//            String refreshToken = jwtService.generateRefreshToken(new HashMap<>(),userDetails);
//            return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken));
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi đăng ký: " + e.getMessage());
//
//        }
//
//
//    }
//
//    @PostMapping("/refresh-token")
//    public ResponseEntity<?> refreshToken(@RequestBody AuthenticationResponse request) {
//        String refreshToken = request.getRefreshToken();
//        String username = jwtService.extractUsername(refreshToken);
//
//        if (username != null) {
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            if (jwtService.isTokenValid(refreshToken, userDetails)) {
//                String newAccessToken = jwtService.generateToken(userDetails);
//                return ResponseEntity.ok(new AuthenticationResponse(newAccessToken, refreshToken));
//            }
//        }
//        return ResponseEntity.status(403).body("Invalid or expired refresh token");
//    }
//}
