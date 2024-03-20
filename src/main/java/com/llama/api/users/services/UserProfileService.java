package com.llama.api.users.services;

import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.users.dto.UserDTO;
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
    UserService userService;

    @Autowired
    UserRepository userRepository;

    public UserProfile getProfile(String userID) throws ResourceNotFound {
        Users user = userService.getUser(userID);

        return userProfileRepository
                .findById(
                        user
                                .getUserProfile().getId()
                ).orElseThrow(
                        () -> new ResourceNotFound("User does not exist")
                );
    }

    public UserProfile addProfile(String userID, UserProfileDTO profile) throws ResourceNotFound {
        Users user = userService.getUser(userID);

        UserProfile userProfile = new UserProfile();
        BeanUtils.copyProperties(profile, userProfile);

        user.setUserProfile(userProfile);

        Users result = userRepository.save(user);

        return result.getUserProfile();
    }

    public UserProfile updateProfile(String userID, UserProfileDTO profile) throws ResourceNotFound {
        Users user = userService.getUser(userID);

        UserProfile userProfile = user.getUserProfile();
        BeanUtils.copyProperties(profile, userProfile);

        user.setUserProfile(userProfile);

        Users result = userRepository.save(user);

        return result.getUserProfile();
    }
}
