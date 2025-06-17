package com.cashcontrol.cashcontrol_api.controller;

import com.cashcontrol.cashcontrol_api.dto.UserCreateDto;
import com.cashcontrol.cashcontrol_api.dto.UserDto;
import com.cashcontrol.cashcontrol_api.model.User;
import com.cashcontrol.cashcontrol_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

/**
 * Controller responsável por cadastro, autenticação e consulta de usuários.
 *
 * Possui endpoints públicos para registro e login, e endpoints autenticados para consultas.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Registra um novo usuário no sistema.
     *
     * @param dto DTO contendo nome, email, senha e rendaMensal.
     * @return UserDto cadastrado.
     *
     * <p>
     * Exemplo de requisição:
     * <pre>
     * POST /api/users/register
     * {
     *   "nome": "João",
     *   "email": "joao@email.com",
     *   "senha": "123456",
     *   "rendaMensal": 2000
     * }
     * </pre>
     * </p>
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserCreateDto dto) {
        if (userService.existePorEmail(dto.getEmail())) {
            return ResponseEntity.badRequest().build();
        }
        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());
        user.setRendaMensal(dto.getRendaMensal());
        user.setPerfilRisco("moderado");
        user.setLimiteMensalAposta(dto.getRendaMensal() * 0.1); // 10% da renda
        user.setSaldo(dto.getRendaMensal());
        user.setXp(0);
        user.setDiasSemApostar(0);

        User salvo = userService.salvar(user);

        UserDto response = toDto(salvo);
        return ResponseEntity.ok(response);
    }

    /**
     * Realiza login de usuário e retorna um token de autenticação.
     *
     * @param dto DTO com email e senha.
     * @return LoginResponse contendo token, id, nome e email.
     *
     * <p>
     * Exemplo de requisição:
     * <pre>
     * POST /api/users/login
     * {
     *   "email": "joao@email.com",
     *   "senha": "123456"
     * }
     * </pre>
     * Exemplo de resposta:
     * <pre>
     * {
     *   "token": "uuid-gerado",
     *   "userId": 1,
     *   "nome": "João",
     *   "email": "joao@email.com"
     * }
     * </pre>
     * </p>
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserCreateDto dto) {
        Optional<User> userOpt = userService.buscarPorEmail(dto.getEmail());
        if (userOpt.isPresent() && userOpt.get().getSenha().equals(dto.getSenha())) {
            User user = userOpt.get();

            // Gera token novo e salva
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            userService.salvar(user);

            return ResponseEntity.ok(new LoginResponse(token, user.getId(), user.getNome(), user.getEmail()));
        }
        return ResponseEntity.status(401).build();
    }

    /**
     * Busca usuário pelo ID. Pode ser público ou protegido por token.
     *
     * @param id    ID do usuário.
     * @param token Token de autenticação (opcional).
     * @return UserDto se autorizado ou público.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> buscarPorId(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String token) {
        Optional<User> userOpt = userService.buscarPorId(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = userOpt.get();

        // Verifica token SE for enviado (pode deixar público se preferir)
        if (token != null && (user.getToken() == null || !user.getToken().equals(token))) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(toDto(user));
    }

    /**
     * DTO de resposta para login.
     */
    public static class LoginResponse {
        private String token;
        private Long userId;
        private String nome;
        private String email;

        public LoginResponse(String token, Long userId, String nome, String email) {
            this.token = token;
            this.userId = userId;
            this.nome = nome;
            this.email = email;
        }

        public String getToken() { return token; }
        public Long getUserId() { return userId; }
        public String getNome() { return nome; }
        public String getEmail() { return email; }
    }

    /**
     * Converte entidade User para UserDto.
     */
    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setNome(user.getNome());
        dto.setEmail(user.getEmail());
        dto.setRendaMensal(user.getRendaMensal());
        dto.setPerfilRisco(user.getPerfilRisco());
        dto.setLimiteMensalAposta(user.getLimiteMensalAposta());
        dto.setSaldo(user.getSaldo());
        dto.setXp(user.getXp());
        dto.setDiasSemApostar(user.getDiasSemApostar());
        return dto;
    }
}
