package ru.shameoff.jessenger.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shameoff.jessenger.common.sharedDto.UserDto;

import java.util.UUID;

import static ru.shameoff.jessenger.common.security.SecurityConstants.HEADER_INTEGRATION;

@FeignClient(name = "user-service")
@Repository
public interface UserServiceClient {

    @GetMapping("/integration/users/check")
    Boolean checkUserById(@RequestParam(required = true) UUID userId, @RequestHeader(HEADER_INTEGRATION) String apiKey);

    @GetMapping("/integration/users/profile")
    UserDto getUserById(@RequestParam(required = true) UUID userId, @RequestHeader(HEADER_INTEGRATION) String apiKey);
}
