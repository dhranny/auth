package com.project.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.auth.services.LdapService;
import java.util.List;
import java.util.Map;
import com.project.auth.models.User;
import com.project.auth.config.JwtUtil;

@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private LdapService ldapServ;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public String welcome(){
        return "welcome";
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> perso){
        boolean isA = ldapServ.checkPass(perso.get("username"), perso.get("userpassword"));;
        User person = new User((String)perso.get("username"), (String)perso.get("userpassword"));
        if(isA){
            String token = jwtUtil.generateToken(new User(perso.get("username"), perso.get("userpassword")));
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> perso){
        ldapServ.createUser(perso.get("username"), perso.get("username"), perso.get("username"), perso.get("userpassword"));
        return ResponseEntity.ok("success");
    }
}