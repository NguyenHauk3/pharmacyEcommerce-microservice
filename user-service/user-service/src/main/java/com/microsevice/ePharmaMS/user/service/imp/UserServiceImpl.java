//package com.microsevice.ePharmaMS.user.service.imp;
//
//import com.microsevice.ePharmaMS.user.exception.UserException;
//import com.microsevice.ePharmaMS.user.modal.User;
//import com.microsevice.ePharmaMS.user.repository.UserRepository;
//import com.microsevice.ePharmaMS.user.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class UserServiceImpl implements UserService {
//
//    private final UserRepository userRepository;
//    @Override
//    public User getUserById(Long id) throws UserException {
//        Optional<User> otp = userRepository.findById(id);
//        if(otp.isPresent()){
//            return otp.get();
//        }
//        throw new UserException("User not found");
//
//    }
//
//    @Override
//    public User createUser(User user) {
//        return userRepository.save(user);
//    }
//
//    @Override
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    @Override
//    public void deleteUser(Long id) throws UserException {
//        Optional<User> otp = userRepository.findById(id);
//        if(otp.isEmpty()){
//            throw new UserException("User not exist with id"+ id);
//        }
//        userRepository.deleteById(otp.get().getId());
//
//    }
//
//    @Override
//    public User updateUser(Long id, User user) throws UserException {
//        Optional<User> otp = userRepository.findById(id);
//        if(otp.isEmpty()){
//            throw new UserException("User not found with id"+ id);
//        }
//        User existingUser = otp.get();
//        existingUser.setEmail(user.getEmail());
//        existingUser.setFullName(user.getFullName());
//        existingUser.setPhone(user.getPhone());
//        existingUser.setRole(user.getRole());
//        existingUser.setUserName(user.getUserName());
//        existingUser.setUpdatedAt(LocalDateTime.now());
//
//        return userRepository.save(existingUser);
//    }
//}
