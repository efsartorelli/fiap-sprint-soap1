package com.cashcontrol.cashcontrol_api.service;

import com.cashcontrol.cashcontrol_api.model.User;
import com.cashcontrol.cashcontrol_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelas operações relacionadas aos usuários.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Lista todos os usuários cadastrados.
     *
     * @return lista de usuários
     */
    public List<User> listarTodos() {
        return userRepository.findAll();
    }

    /**
     * Busca usuário pelo ID.
     *
     * @param id identificador do usuário
     * @return Optional com o usuário, se encontrado
     */
    public Optional<User> buscarPorId(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Busca usuário pelo email.
     *
     * @param email email do usuário
     * @return Optional com o usuário, se encontrado
     */
    public Optional<User> buscarPorEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Salva um usuário no banco.
     *
     * @param user objeto usuário para salvar
     * @return usuário salvo
     */
    public User salvar(User user) {
        return userRepository.save(user);
    }

    /**
     * Deleta um usuário pelo ID.
     *
     * @param id identificador do usuário a ser deletado
     */
    public void deletar(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Verifica se um usuário com determinado email já existe.
     *
     * @param email email a verificar
     * @return true se existir, false caso contrário
     */
    public boolean existePorEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
