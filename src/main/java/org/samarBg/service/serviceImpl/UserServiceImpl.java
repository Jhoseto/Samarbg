package org.samarBg.service.serviceImpl;

import org.samarBg.models.UserEntity;
import org.samarBg.models.enums.UserRole;
import org.samarBg.repository.UserRepository;
import org.samarBg.service.Mappers.UsersMapper;
import org.samarBg.service.UserService;
import org.samarBg.views.UserProfileViewModel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the UserService interface.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UsersMapper usersMapper;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserDetailsService userDetailsService,
                           UsersMapper usersMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.usersMapper = usersMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserEntity> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserEntity> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Authenticates a user using email and password.
     *
     * @param email    the email of the user
     * @param password the password of the user
     * @return an Authentication object if authentication is successful, otherwise null
     */
    public Authentication authenticateUser(String email, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            getCurrentUser().setOnlineStatus(1);
            return authentication;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserProfileViewModel> getAllUsers() {
        List<UserProfileViewModel> allUsers = new ArrayList<>();
        List<UserEntity> users = userRepository.findAll();

        // Mapping users to UserProfileViewModel using MapperForUsers
        for (UserEntity user : users) {
            UserProfileViewModel userProfileViewModel = usersMapper.mapUserToProfileViewModel(user);
            allUsers.add(userProfileViewModel);
        }
        return allUsers;
    }

    /**
     * Retrieves information about the currently logged-in user.
     *
     * @return the UserEntity object representing the currently logged-in user, or null if no user is logged in
     */
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<UserEntity> userOptional = userRepository.findByUsername(username);
            if (userOptional.isPresent()) {
                return userOptional.get();
            } else {
                // The user not found by userName, then find by Email
                Optional<UserEntity> userByEmailOptional = userRepository.findByEmail(username);
                return userByEmailOptional.orElse(null);
            }
        }
        return null;
    }


    public void promoteUserToAdmin(String username) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        UserRole newRole = UserRole.ADMIN;

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            if (!user.getRole().equals(newRole)) {
                user.setRole(newRole);
                userRepository.save(user);
            } else {
                throw new RuntimeException("User is already an ADMIN.");
            }
        } else {
            throw new RuntimeException("User not found with username: " + username);
        }
    }

    public void promoteAdminToUser(String username) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        UserRole newRole = UserRole.USER;

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            if (!user.getRole().equals(newRole)) {
                user.setRole(newRole);
                userRepository.save(user);
            } else {
                throw new RuntimeException("Role 'USER' not found in the database.");
            }
        } else {
            throw new RuntimeException("User not found with username: " + username);
        }
    }

    @Override
    public void changeUserRole(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);

        if (user.isPresent()){
            UserEntity currentUser = user.get();
           if (currentUser.getRole().equals(UserRole.USER)){
               promoteUserToAdmin(currentUser.getUsername());
           }else {
               promoteAdminToUser(currentUser.getUsername());
           }
           userRepository.save(currentUser);

        }else {
            throw new RuntimeException("User not found !");
        }
    }


    /**
     * Deletes a user from the repository based on the provided user ID.
     * If the user exists in the repository, it is deleted.
     * If the user does not exist, a message is printed indicating that the delete operation failed.
     *
     * @param userId The ID of the user to be deleted.
     */

    @Override
    public void deleteUser(Long userId){
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()){
            UserEntity currentUser = user.get();
            System.out.println("Delete user => "+currentUser.getUsername());
            userRepository.delete(currentUser);

        }else {
            throw new RuntimeException("Delete operation false ! The User not exist");
        }
    }

    /**
     * Retrieves the user profile information for the user with the specified username.
     * If a user with the given username exists, their profile information is retrieved and mapped to a UserProfileViewModel object.
     * If no user with the specified username exists, an empty UserProfileViewModel object is returned.
     *
     * @param userName The username of the user whose profile information is to be retrieved.
     * @return UserProfileViewModel containing the profile information of the user.
     */
    @Override
    public UserProfileViewModel getUserByUsername (String userName) {
        Optional<UserEntity> user = userRepository.findByUsername(userName);

        UserProfileViewModel userProfileViewModel = new UserProfileViewModel();
        if (user.isPresent()) {
            UserEntity currentUser = user.get();
            userProfileViewModel = usersMapper.mapUserToProfileViewModel(currentUser);
        }
        return userProfileViewModel;
    }
}
