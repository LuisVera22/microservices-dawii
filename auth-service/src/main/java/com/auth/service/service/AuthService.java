package com.auth.service.service;

import com.auth.service.entity.User;
import com.auth.service.model.*;
import com.auth.service.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${mail.urlFront}")
    private String urlFront;
    @Value("${spring.mail.username}")
    private String mailFrom;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();

    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode( request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .country(request.getCountry())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();

    }

    public void sendEmail() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("luis26.ml143@gmail.com");
        mailMessage.setTo("luis22vera03@gmail.com");
        mailMessage.setSubject("Prueba de envío de email");
        mailMessage.setText("Esto es el contenido del email");

        javaMailSender.send(mailMessage);
    }

    public Map<String, Object> recoveryPassword(RecoveryPasswordRequest request) {
        Map<String, Object> response = new HashMap<>();

        Optional<User> user = userRepository.findByUsername(request.getUserName());

        if (user.isEmpty()) {
            response.put("message", "Ocurrió un error en la petición.");
            return response;
        }

        try {
            request.setMailFrom(mailFrom);
            request.setSubject("Recuperación de contraseña");
            request.setUserName("");
            UUID uuid = UUID.randomUUID();
            String tokenPassword =  uuid.toString();
            request.setTokenPassword(tokenPassword);

            Map<String, Object> sendEmail = sendEmail(request);

            response.put("message", sendEmail.get("message"));
        } catch (Exception e) {
            response.put("message", "Ocurrió un error en la petición.");
            throw new RuntimeException(e);
        }

        return response;
    }

    public Map<String, Object> sendEmail(RecoveryPasswordRequest request) {
        Map<String, Object> response = new HashMap<>();

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Context context = new Context();

            Map<String, Object> model = new HashMap<>();
            model.put("userName", request.getUserName());
            model.put("url", urlFront + "/change-password" + request.getTokenPassword());
            context.setVariables(model);

            String htmlText = templateEngine.process("email-template", context);
            helper.setFrom(request.getMailFrom());
            helper.setTo(request.getMailTo());
            helper.setSubject("Recuperación de contraseña");
            helper.setText(htmlText, true);

            javaMailSender.send(message);

            response.put("message", "Email enviado exitosamente.");
        } catch (MessagingException e) {
            response.put("message", "Ocurrió un error al intentar enviar el email.");
            e.printStackTrace();
        }

        return response;
    }
}
