package ru.shameoff.jessenger.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

import static ru.shameoff.jessenger.common.security.SecurityConstants.HEADER_INTEGRATION;

/**
 * Клиент, который интегрируется в сервисы, для взаимодействия с сервисом друзей
 */
@FeignClient(name = "friends-service")
@Repository
public interface FriendsServiceClient {
    /**
     * Возвращает блэклист пользователя с ID = userId
     *
     * @param userId
     * @param apiKey ключ интеграционного запрос
     * @return список UUIDов друзей пользователя
     */
    @GetMapping("/integration/friends")
    List<UUID> retrieveUserFriends(@RequestParam(required = true) UUID userId, @RequestHeader(HEADER_INTEGRATION) String apiKey);

    /**
     * Возвращает блэклист пользователя с ID = userId
     *
     * @param userId
     * @param apiKey ключ интеграционного запрос
     * @return список UUIDов заблокированных пользователем пользователей
     */
    @GetMapping("/integration/blacklist")
    List<UUID> retrieveUserBlacklist(@RequestParam(required = true) UUID userId, @RequestHeader(HEADER_INTEGRATION) String apiKey);

    /**
     * Проверка, не находится ли таргет юзер в черном списке внешнего пользователя
     *
     * @param targetUserId  проверяемый
     * @param foreignUserId владелец черного списка
     * @param apiKey        ключ интеграционного запрос
     * @return
     */
    @GetMapping("/integration/is-blocked")
    Boolean checkIfBlocked(@RequestParam(required = true) UUID targetUserId, @RequestParam UUID foreignUserId, @RequestHeader(HEADER_INTEGRATION) String apiKey);
}
