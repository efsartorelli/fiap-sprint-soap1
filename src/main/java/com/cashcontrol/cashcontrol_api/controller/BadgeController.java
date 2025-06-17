package com.cashcontrol.cashcontrol_api.controller;

import com.cashcontrol.cashcontrol_api.dto.BadgeDto;
import com.cashcontrol.cashcontrol_api.model.Badge;
import com.cashcontrol.cashcontrol_api.model.User;
import com.cashcontrol.cashcontrol_api.service.BadgeService;
import com.cashcontrol.cashcontrol_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * Controller responsável pela gestão das badges (insígnias) dos usuários.
 * <p>
 * Permite consultar todas as badges, buscar por usuário autenticado via token,
 * ou buscar por ID específico.
 * </p>
 */
@RestController
@RequestMapping("/api/badges")
public class BadgeController {

    @Autowired
    private BadgeService badgeService;

    @Autowired
    private UserService userService;

    /**
     * Lista todas as badges cadastradas no sistema (endpoint público).
     *
     * @return Lista de BadgeDto.
     *
     * Exemplo de resposta:
     * <pre>
     * [
     *   {
     *     "id": 1,
     *     "nome": "1B XP",
     *     "descricao": "Primeiro dia sem apostas",
     *     "dataConquista": "2025-06-10",
     *     "userId": 1
     *   }
     * ]
     * </pre>
     */
    // @Operation(summary = "Listar todas as badges", description = "Retorna todas as badges cadastradas no sistema")
    @GetMapping
    public List<BadgeDto> listarTodos() {
        return badgeService.listarTodos()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Lista todas as badges conquistadas por um usuário específico, validando o token de autenticação.
     *
     * @param userId ID do usuário.
     * @param token Token de autenticação no header Authorization.
     * @return Lista de BadgeDto do usuário autenticado ou erro 401.
     *
     * Exemplo de header: Authorization: seu-token-aqui
     */
    // @Operation(summary = "Listar badges por usuário", description = "Retorna as badges conquistadas por um usuário, protegido por token")
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<BadgeDto>> listarPorUsuario(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String token) {
        Optional<User> userOpt = userService.buscarPorId(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = userOpt.get();

        // Valida token
        if (user.getToken() == null || !user.getToken().equals(token)) {
            return ResponseEntity.status(401).build();
        }

        List<BadgeDto> lista = badgeService.listarPorUsuario(user)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    /**
     * Busca uma badge específica pelo seu ID (endpoint público).
     *
     * @param id Identificador único da badge.
     * @return BadgeDto encontrada ou 404 se não existir.
     */
    // @Operation(summary = "Buscar badge por ID", description = "Retorna a badge referente ao ID fornecido")
    @GetMapping("/{id}")
    public ResponseEntity<BadgeDto> buscarPorId(@PathVariable Long id) {
        Optional<Badge> badge = badgeService.buscarPorId(id);
        return badge.map(value -> ResponseEntity.ok(toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Converte a entidade Badge para o DTO BadgeDto.
     *
     * @param b Badge do banco de dados.
     * @return BadgeDto preenchido.
     */
    private BadgeDto toDto(Badge b) {
        BadgeDto dto = new BadgeDto();
        dto.setId(b.getId());
        dto.setNome(b.getNome());
        dto.setDescricao(b.getDescricao());
        dto.setDataConquista(b.getDataConquista());
        dto.setUserId(b.getUser().getId());
        return dto;
    }
}
