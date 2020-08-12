package com.stuportal.usermanager.services;

import com.stuportal.usermanager.exceptions.serverExceptions.DBException;
import com.stuportal.usermanager.exceptions.serverExceptions.InternalServerException;
import com.stuportal.usermanager.exceptions.userExceptions.*;
import com.stuportal.usermanager.forms.requesetForms.SignUpRequestForm;
import com.stuportal.usermanager.models.Role;
import com.stuportal.usermanager.models.User;
import com.stuportal.usermanager.repositories.UserRepository;
import com.stuportal.usermanager.utilities.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserManagerService {
    //constants
    private static final int PASS_DIGIT = 5;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleManagerService roleManagerService;

    @Autowired
    private AccessManagerService accessManagerService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private void dbSaveUser(User user) throws DuplicateUserException, DBException {
        try {
            User u = userRepository.findByIdNumber(user.getIdNumber());
            if (u != null) {
                throw new DuplicateUserException();
            } else {
                userRepository.save(user);
            }
        } catch (DuplicateUserException e) {
            throw e;
        } catch (Exception e) {
            throw new DBException();
        }
    }

    private void updateUser(User user) throws UserNotFoundException, DBException {
        try {
            User u = userRepository.findByIdNumber(user.getIdNumber());
            if (u == null) {
                throw new UserNotFoundException();
            } else {
                userRepository.save(user);
            }
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DBException();
        }
    }

    private User generateUserObject(SignUpRequestForm signUpForm) throws RoleNotFoundException, DBException {
        Role role = roleManagerService.getRole(signUpForm.getRole());
        User user = new User(signUpForm.getIdNumber(), signUpForm.getFirstName(), signUpForm.getLastName(), role);
        user.setEmail(signUpForm.getEmail());
        user.setPhoneNumber(signUpForm.getPhoneNumber());
        user.setFatherName(signUpForm.getFatherName());
        return user;
    }

    private void sendSMS(User user, String password) throws Exception{
        System.out.println("SMS Sent");
    }

    public String login(String userId, String password) throws
            AuthenticationFailedException, UserNotFoundException, InternalServerException {
        try {
            User user = getUserById(userId);
            boolean result = user.authenticate(password);
            if (result) {
                return jwtTokenUtil.generateToken(user);
            } else {
                throw new AuthenticationFailedException();
            }
        } catch (UserNotFoundException | AuthenticationFailedException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException();
        }
    }

    public Map<String, String> singUp(SignUpRequestForm signUpRequestForm) throws
            DuplicateUserException, InternalServerException, DBException {
        HashMap<String, String> userInfo = new HashMap<>();
        try {
            User user = generateUserObject(signUpRequestForm);
            String pass = user.generateAndSetPass(PASS_DIGIT);
            dbSaveUser(user);
            userInfo.put("id", user.getId());
            userInfo.put("pass", pass);
            this.sendSMS(user, pass);
            return userInfo;
        } catch (DuplicateUserException | DBException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException();
        }
    }

    public Map<String, String> verifyToken(String token) throws UnAuthorizedException {
        HashMap<String, String> userInfo = new HashMap<>();
        boolean result = jwtTokenUtil.validateToken(token);
        if (result) {
            userInfo.put("userId", jwtTokenUtil.getClaimFromToken(token, Claims::getIssuer));
            userInfo.put("role", jwtTokenUtil.getClaimFromToken(token, Claims::getSubject));
            return userInfo;
        } else {
            throw new UnAuthorizedException();
        }
    }

    public User getUserById(String userId) throws DBException,
            UserNotFoundException {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                return user.get();
            } else {
                throw new UserNotFoundException();
            }
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new DBException();
        }
    }

    public List<User> getUsersByRole(String roleName) throws UserNotFoundException, RoleNotFoundException, DBException {
        try {
            Role role = roleManagerService.getRole(roleName);
            return userRepository.findByRole(role);
        } catch (UserNotFoundException | RoleNotFoundException | DBException e) {
            throw e;
        } catch (Exception e) {
            throw new DBException();
        }
    }

    public List<User> getAllUsers() throws DBException {
        List<User> users = new ArrayList<>();
        try {
            userRepository.findAll().forEach(users::add);
            return users;
        } catch (Exception e) {
            throw new DBException();
        }
    }

    public void deleteUser(String userId) throws UserNotFoundException, DBException{
        User user = this.getUserById(userId);
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            throw new DBException();
        }
    }

    public void changePassword(String userId, String previousPassword, String newPassword) throws
            UnAuthorizedException, UserNotFoundException, InternalServerException {
        try {
            User user = this.getUserById(userId);
            if (user.authenticate(previousPassword)) {
                user.setPassword(newPassword);
                updateUser(user);
            } else {
                throw new UnAuthorizedException();
            }
        } catch (UnAuthorizedException | UserNotFoundException | DBException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException();
        }
    }

    public void hasAccess(String token, String resourceName) throws AccessDeniedException, UnAuthorizedException ,
            RoleNotFoundException, ResourceNotFoundException, DBException{
        try {
            Map<String, String> userInfo = this.verifyToken(token);
            if (!accessManagerService.hasAccess(userInfo.get("role"), resourceName)) {
                throw new AccessDeniedException();
            }
        }catch (AccessDeniedException e){
            throw e;
        } catch (Exception e){
            throw new AccessDeniedException();
        }
    }


    public String forgetPassword(String userId) throws Exception{
        User user = this.getUserById(userId);
        String pass = user.generateAndSetPass(PASS_DIGIT);
        userRepository.save(user);
        this.sendSMS(user, pass);
        return pass;
    }
}
