package com.cashcontrol.cashcontrol_api.controller;

import com.cashcontrol.cashcontrol_api.model.User;
import com.cashcontrol.cashcontrol_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

/**
 * Controller responsável pela autenticação de usuários.
 *
 * <p>Exemplo de endpoint RESTful para login de usuário, retorno de token e dados básicos.</p>
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Realiza login do usuário e retorna um token de sessão.
     *
     * @param request Objeto com email e senha do usuário.
     * @return ResponseEntity contendo o token, id, nome e email se login for válido;
     *         ou erro 401 se não autenticado.
     *
     * <p>Exemplo de requisição:
     * <pre>
     * POST /api/auth/login
     * {
     *   "email": "usuario@email.com",
     *   "senha": "123456"
     * }
     * </pre>
     * </p>
     *
     * <p>Exemplo de resposta (200 OK):
     * <pre>
     * {
     *   "token": "uuid-gerado",
     *   "userId": 1,
     *   "nome": "João Silva",
     *   "email": "usuario@email.com"
     * }
     * </pre>
     * </p>
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Usuário não encontrado");
        }
        User user = userOpt.get();
        if (!user.getSenha().equals(request.getSenha())) {
            return ResponseEntity.status(401).body("Senha incorreta");
        }

        // Gera token aleatório e salva
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        userRepository.save(user);

        // Retorna token e dados básicos do usuário
        return ResponseEntity.ok(new LoginResponse(token, user.getId(), user.getNome(), user.getEmail()));
    }

    /**
     * DTO para requisição de login.
     */
    public static class LoginRequest {
        /**
         * Email do usuário para login.
         */
        private String email;
        /**
         * Senha do usuário.
         */
        private String senha;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getSenha() { return senha; }
        public void setSenha(String senha) { this.senha = senha; }
    }

    /**
     * DTO para resposta de login.
     */
    public static class LoginResponse {
        /**
         * Token gerado para a sessão do usuário.
         */
        private String token;
        /**
         * ID do usuário autenticado.
         */
        private Long userId;
        /**
         * Nome do usuário autenticado.
         */
        private String nome;
        /**
         * Email do usuário autenticado.
         */
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
}
