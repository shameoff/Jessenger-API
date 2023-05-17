package ru.shameoff.jessenger.friends.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shameoff.jessenger.friends.repository.FriendsRepository;

@Service
@RequiredArgsConstructor
public class FriendsService {
    private final FriendsRepository friendsRepository;

    public void retrieveFriends() {
    }

    public void retrieveFriendsProfile() {
    }

}
