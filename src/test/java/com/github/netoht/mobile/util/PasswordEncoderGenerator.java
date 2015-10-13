package com.github.netoht.mobile.util;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderGenerator {

    public static void main(String[] args) {
        PasswordEncoder enc = new BCryptPasswordEncoder();

        Arrays.asList("root", "admin", "usuario")
        .stream()
        .forEach(pass -> {
            System.out.println(String.format("'%s', '%s'", pass, StringUtils.strip(enc.encode(pass).substring(10))));
        });
    }
}