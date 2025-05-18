//package com.microsevice.ePharmaMS.user.controller;
//
//import com.microsevice.ePharmaMS.user.exception.UserException;
//import com.microsevice.ePharmaMS.user.modal.User;
//import com.microsevice.ePharmaMS.user.repository.UserRepository;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/users")
//public class UserController {
//
//    private final UserRepository userRepository;
//
//    // Chỉ USER được truy cập
//    @GetMapping("/profile")
//    @PreAuthorize("hasAuthority('USER')")
//    public ResponseEntity<?> getUserProfile(Authentication authentication) {
//        String username = authentication.getName();
//        User user = userRepository.findByEmail(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        return ResponseEntity.ok(user);
//    }
//
//    // Cả USER và ADMIN đều được truy cập
//    @GetMapping("/all")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
//    public ResponseEntity<List<User>> getAllUsers() {
//        return ResponseEntity.ok(userRepository.findAll());
//    }
//
//    // Chỉ ADMIN mới được tạo tài khoản mới
//    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        User saved = userRepository.save(user);
//        return ResponseEntity.ok(saved);
//    }
//
//    // ADMIN có thể xóa người dùng
//    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
//        userRepository.deleteById(id);
//        return ResponseEntity.ok("Deleted user with id: " + id);
//    }
//}
////
////
////    private final UserService userService ;
////
////    @PostMapping("")
////    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
////        User createdUser = userService.createUser(user);
////        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
////    }
////
////    @GetMapping("")
////    public ResponseEntity<List<User>> getUser(){
////
////        List<User> users = userService.getAllUsers();
////        return new ResponseEntity<>(users, HttpStatus.OK);
////    }
////
////    @GetMapping("{id}")
////    public ResponseEntity<User> getUserById(@PathVariable Long id)  throws Exception{
////        User user = userService.getUserById(id);
////        return new ResponseEntity<>(user, HttpStatus.OK);
////        }
////
////    @PutMapping("{id}")
////    public ResponseEntity<User> updateUser (@RequestBody User user ,
////                            @PathVariable Long id) throws Exception{
////        User updatedUser = userService.updateUser(id, user);
////        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
////
////    }
////
////    @DeleteMapping("{id}")
////    public ResponseEntity<String> deleteUserById (@PathVariable Long id) throws Exception{
////
////        userService.deleteUser(id);
////        return new ResponseEntity<>("User deleted", HttpStatus.OK);
////
////    }
////}
//
//
