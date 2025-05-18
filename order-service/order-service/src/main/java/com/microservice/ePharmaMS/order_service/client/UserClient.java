package com.microservice.ePharmaMS.order_service.client;

import com.microservice.ePharmaMS.order_service.DTO.UserDTO;
import com.microservice.ePharmaMS.order_service.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", configuration = FeignClientConfig.class)
public interface UserClient {
    @GetMapping("api/user/admin/get-users/{id}")
    UserDTO getUserById(@PathVariable Long id);
}
