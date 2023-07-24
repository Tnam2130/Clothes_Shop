package com.main.asm.service.impl;

import com.main.asm.constant.EmailType;
import com.main.asm.entity.Role;
import com.main.asm.entity.UserDto;
import com.main.asm.entity.Users;
import com.main.asm.repository.RoleRepository;
import com.main.asm.repository.UsersRepository;
import com.main.asm.service.EmailService;
import com.main.asm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private EmailService emailService;

    private UserDto convertEntityToDto(Users user){
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        System.out.println(user.getEmail());
        return userDto;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("USER");
        return roleRepository.save(role);
    }
    @Override
    public void saveUser(UserDto userDto) {
        Users user = new Users();
        user.setEmail(userDto.getEmail());
        System.out.println(userDto.getEmail());
        //encrypt the password once we integrate spring security
        //user.setPassword(userDto.getPassword());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//        emailService.sendEmail(user,user.getEmail(),"", EmailType.WELCOME_TO_WEBSITE);

        Role role = roleRepository.findByName("USER");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    @Override
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<Users> users = userRepository.findAll();
        return users.stream().map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Users resetPassword(String email,String newPassword) {
        return Optional.ofNullable(findByEmail(email))
                .map(users -> {
                    users.setPassword(passwordEncoder.encode(newPassword));
                    return userRepository.save(users);
                }).orElse(null);

    }


}
