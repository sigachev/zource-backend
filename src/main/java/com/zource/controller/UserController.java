package com.zource.controller;

import com.zource.config.jwt.JwtTokenProvider;
import com.zource.model.Role;
import com.zource.model.User;
import com.zource.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;


    @PostMapping("registration")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        user.setRole(Role.USER);
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PostMapping("user/update")
    public ResponseEntity<?> update(@RequestBody User user) {
        if (userService.findById(user.getId()) == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }

    @GetMapping("login")
    public ResponseEntity<?> authenticate(Principal principal) {
        if (principal == null) {
            //This should be ok http status because this will be used for logout path.
            return ResponseEntity.ok(principal);
        }
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        User user = userService.findByUsername(authenticationToken.getName());
        user.setToken(jwtTokenProvider.generateToken(authenticationToken));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("users/all")
    public ResponseEntity<?> allUsers() {
        return new ResponseEntity(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("user/check/username/{username}")
    public ResponseEntity<?> checkIfUsernameExists(@PathVariable String username) {
        return new ResponseEntity(userService.findByUsername(username) == null ? false : true, HttpStatus.OK);
    }

    /*    @GetMapping("/api/user/check/email/{email}")
        public ResponseEntity<?> checkIfEmailExists(@PathVariable String email) {
            return new ResponseEntity(userService.findByEmail(email) == null ? false : true, HttpStatus.OK);
        }*/
    @PutMapping("user/check/email")
    public ResponseEntity<?> checkIfEmailExists(@RequestBody String email) {
        return new ResponseEntity(userService.findByEmail(email) == null ? false : true, HttpStatus.OK);
    }

}
