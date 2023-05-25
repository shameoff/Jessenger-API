package ru.shameoff.jessenger.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

import static ru.shameoff.jessenger.common.security.SecurityConstants.HEADER_INTEGRATION;

@FeignClient(name = "friends-service")
@Repository
public interface FriendsServiceClient {

    @GetMapping("/integration/friends")
    List<UUID> retrieveUserFriends(@RequestParam(required = true) UUID userId, @RequestHeader(HEADER_INTEGRATION) String apiKey);

    @GetMapping("/integration/blacklist")
    List<UUID> retrieveUserBlacklist(@RequestParam(required = true) UUID userId, @RequestHeader(HEADER_INTEGRATION) String apiKey);

    @GetMapping("/integraion/is-blocked")
    Boolean checkIfBlocked(@RequestParam(required = true) UUID targetUserId, @RequestParam UUID foreignUserId, @RequestHeader(HEADER_INTEGRATION) String apiKey);
}
