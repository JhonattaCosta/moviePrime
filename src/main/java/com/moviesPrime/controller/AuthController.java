package com.moviesPrime.controller;

import com.moviesPrime.config.TokenService;
import com.moviesPrime.controller.request.LoginRequest;
import com.moviesPrime.controller.request.UserRequest;
import com.moviesPrime.controller.response.LoginResponse;
import com.moviesPrime.controller.response.UserResponse;
import com.moviesPrime.entity.User;
import com.moviesPrime.exception.UsernameOrPasswordInvalidException;
import com.moviesPrime.mapper.UserMapper;
import com.moviesPrime.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/moviesprime/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Operation(summary = "Registro de usuario", description = "Registra o usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Usuario criado"),
            @ApiResponse(responseCode = "400", description = "Não foi possivel registrar o usuario!")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request){
        User savedUser = userService.saveUser(UserMapper.toUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toUserResponse(savedUser));
    }

    @Operation(summary = "Login do usuario", description = "Loga o usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario logado"),
            @ApiResponse(responseCode = "400", description = "Não foi possivel logar tente novamente!")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        try{
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authenticate = authenticationManager.authenticate(userAndPass);

        User user = (User) authenticate.getPrincipal();

        String token = tokenService.generateToken(user);

        return ResponseEntity.ok(new LoginResponse(token));
        }catch (BadCredentialsException e){
            throw new UsernameOrPasswordInvalidException("Usuário ou senha inválido.");
        }

    }

}
