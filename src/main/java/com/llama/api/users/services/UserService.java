package com.llama.api.users.services;

import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.users.UserUtils;
import com.llama.api.users.dto.UserDTO;
import com.llama.api.users.dto.UserProfileDTO;
import com.llama.api.users.models.UserProfile;
import com.llama.api.users.models.Users;
import com.llama.api.users.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserProfileService userProfileService;

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users getUser(String id) throws ResourceNotFound {
        return userRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        () -> new ResourceNotFound("User does not exist")
                );
    }

    public Users getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Users addUser(UserDTO user, UserProfileDTO profile) throws ResourceNotFound {
        Users userModel = new Users();

        BeanUtils.copyProperties(user, userModel);

        userModel = userRepository.save(userModel);
        
        // SAVE PROFILE
        userModel.setUserProfile(
                userProfileService.addProfile(
                        userModel.getId().toString(), profile
                )
        );

        return userModel;
    }

    public Users updateUser(String id, UserDTO user) throws ResourceNotFound {
        Users userModel = getUser(id);

        BeanUtils.copyProperties(user, userModel);
        return userRepository.save(userModel);
    }

    public void setPassword(String id, String password) throws ResourceNotFound {
        Users user = getUser(id);

        user.setPassword(UserUtils.hashPassword(password));

        userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(UUID.fromString(id));
    }
}
