package com.microsevice.ePharmaMS.user.repository;

import com.microsevice.ePharmaMS.user.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Tìm người dùng theo userName
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

//    // Kiểm tra nếu userName đã tồn tại
//    boolean existsByUserName(String userName);
}
