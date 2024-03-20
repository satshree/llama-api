package com.llama.api.users.services;

import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.users.UserUtils;
import com.llama.api.users.dto.UserDTO;
import com.llama.api.users.dto.UserProfileDTO;
import com.llama.api.users.models.UserProfile;
import com.llama.api.users.models.Users;
import com.llama.api.users.repository.UserProfileRepository;
import com.llama.api.users.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

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

    public Users getUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User does not exist")
                );
    }

    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Users addUser(UserDTO user, UserProfileDTO profile) throws ResourceNotFound {
        Users userModel = new Users();

        BeanUtils.copyProperties(user, userModel);

        userModel = userRepository.save(userModel);

        // SAVE PROFILE
        UserProfile userProfile = new UserProfile();
        BeanUtils.copyProperties(profile, userProfile);
        userModel.setUserProfile(
                userProfileRepository.save(userProfile)
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
    }
}
