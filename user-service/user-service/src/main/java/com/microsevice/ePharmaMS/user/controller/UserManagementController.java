package com.microsevice.ePharmaMS.user.controller;

import com.microsevice.ePharmaMS.user.DTO.ReqRes;
import com.microsevice.ePharmaMS.user.modal.User;
import com.microsevice.ePharmaMS.user.service.UsersManagementService;
import com.microsevice.ePharmaMS.user.util.UserExport;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserManagementController {

        @Autowired
        private UsersManagementService usersManagementService;

        @PostMapping("/auth/register")
        public ResponseEntity<ReqRes> register(@RequestBody ReqRes reg){
            return ResponseEntity.ok(usersManagementService.register(reg));
        }

        @PostMapping("/auth/login")
        public ResponseEntity<ReqRes> login(@RequestBody ReqRes req){
            ReqRes response = usersManagementService.login(req);
            return ResponseEntity.ok(response);
        }

        @PostMapping("/auth/refresh")
        public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req){
            return ResponseEntity.ok(usersManagementService.refreshToken(req));
        }

        @GetMapping("/admin/get-all-users")
        public ResponseEntity<ReqRes> getAllUsers(){
            return ResponseEntity.ok(usersManagementService.getAllUsers());

        }

        @GetMapping("/admin/get-users/{userId}")
        public ResponseEntity<ReqRes> getUserByID(@PathVariable Long userId){
            return ResponseEntity.ok(usersManagementService.getUsersById(userId));

        }

            @PutMapping("/administer/update/{userId}")
        public ResponseEntity<ReqRes> updateUser(@PathVariable Long userId, @RequestBody User user){
            return ResponseEntity.ok(usersManagementService.updateUser(userId, user));
        }

        @GetMapping("/administer/get-profile")
        public ResponseEntity<ReqRes> getMyProfile(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            ReqRes response = usersManagementService.getMyInfo(email);
            return  ResponseEntity.status(response.getStatusCode()).body(response);
        }

        @DeleteMapping("/admin/delete/{userId}")
        public ResponseEntity<ReqRes> deleteUSer(@PathVariable Long userId){
            return ResponseEntity.ok(usersManagementService.deleteUser(userId));
        }

    @GetMapping("/admin/export-to-excel")
    public void exportToExcel(HttpServletResponse response) throws Exception {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=nguoidung_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = usersManagementService.listAll();

        UserExport excelExporter = new UserExport(listUsers);

        excelExporter.export(response);
    }
}
