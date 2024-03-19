package com.llama.api.users.services;

import com.llama.api.users.dto.UserProfileDTO;
import com.llama.api.users.models.UserProfile;
import com.llama.api.users.models.Users;
import com.llama.api.users.repository.UserProfileRepository;
import com.llama.api.users.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserProfileService {
    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    UserRepository userRepository;

    public UserProfile getProfile(String userID) {
        Users user = userRepository
                .findById(
                        UUID.fromString(userID)
                ).orElseThrow(
                        // implement later
                );

        return userProfileRepository
                .findById(
                        user
                                .getUserProfile().getId()
                ).orElseThrow(
                        // implement later
                );
    }

    public UserProfile addProfile(String userID, UserProfileDTO profile) {
        Users user = userRepository
                .findById(
                        UUID.fromString(userID)
                ).orElseThrow(
                        // implement later
                );

        UserProfile userProfile = new UserProfile();
        BeanUtils.copyProperties(profile, userProfile);

        user.setUserProfile(userProfile);

        userRepository.save(user);

        return user.getUserProfile();
    }

    public UserProfile updateProfile(String userID, UserProfileDTO profile) {
        Users user = userRepository
                .findById(
                        UUID.fromString(userID)
                ).orElseThrow(
                        // implement later
                );

        UserProfile userProfile = user.getUserProfile();
        BeanUtils.copyProperties(profile, userProfile);

        user.setUserProfile(userProfile);

        userRepository.save(user);

        return userProfile;
    }
}
