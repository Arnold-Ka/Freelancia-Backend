package com.hackers.freelancia.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hackers.freelancia.entity.Permission;
import com.hackers.freelancia.entity.Role;
import com.hackers.freelancia.entity.User;
import com.hackers.freelancia.mapper.Mapper;
import com.hackers.freelancia.repository.PermissionRepository;
import com.hackers.freelancia.repository.RoleRepository;
import com.hackers.freelancia.repository.UserRepository;
import com.hackers.freelancia.config.Statut;
import com.hackers.freelancia.config.Utils;
import com.hackers.freelancia.dto.PermissionDto;
import com.hackers.freelancia.dto.RoleDto;
import com.hackers.freelancia.dto.UserDto;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final Mapper mapper;

    /**
     * Loads the user by username pour l'authentification.
     * 
     * @param username le pseudo de l'utilisateur
     * @return UserDetails l'utilisateur chargé
     * @throws UsernameNotFoundException si l'utilisateur n'est pas trouvé
     */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameAndStatut(username, Statut.ACTIVE).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username));
    }

    /**
     * Récupère un rôle par son nom.
     * 
     * @param name le nom du rôle
     * @return Role le rôle trouvé
     * @throws RuntimeException si le rôle n'est pas trouvé
     */
    public Role getByName(final String name) {
        return roleRepository.findByNameAndStatut(name, Statut.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    /**
     * Crée un nouveau rôle.
     * 
     * @param role le DTO du rôle à créer
     */
    public void postRole(final RoleDto role) {
        if (roleRepository.existsByName(role.getName())) {
            throw new RuntimeException("Role already exists");
        }
        role.setId(Utils.generateId());
        roleRepository.save(mapper.maps(role));
    }

    /**
     * Récupère tous les utilisateurs.
     * 
     * @return Set<UserDto> l'ensemble des utilisateurs sous forme de DTOs
     */
    public Set<UserDto> getAllUsers() {
        return userRepository.findAllActive().stream().map(mapper::maps).collect(Collectors.toSet());
    }

    /**
     * Récupère un utilisateur par son ID.
     * 
     * @param id l'ID de l'utilisateur
     * @return UserDto le DTO de l'utilisateur trouvé
     * @throws RuntimeException si l'utilisateur n'est pas trouvé
     */
    public UserDto getUser(@NonNull final String id) {
        return mapper.maps(userRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(() -> new RuntimeException("User not found")));
    }

    /**
     * Crée un nouvel utilisateur.
     * 
     * @param user le DTO de l'utilisateur à créer
     * @throws RuntimeException si le nom d'utilisateur ou l'email est déjà utilisé
     */
    public void postUser(final UserDto userDto) {

        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Username already taken");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already used");
        }

        User user = mapper.maps(userDto);
        user.setId(Utils.generateId());
        user.setStatut(Statut.INACTIVE);

        userRepository.save(user);
    }

    /**
     * Récupère un utilisateur par son nom d'utilisateur.
     * 
     * @param username le nom d'utilisateur
     * @return User l'utilisateur trouvé
     * @throws RuntimeException si l'utilisateur n'est pas trouvé
     */
    public User getByUsername(final String username) {
        return userRepository.findByUsernameAndStatut(username, Statut.ACTIVE).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "utilisateur non trouvée")
        );
    }

    /**
     * Récupère un utilisateur par son email.
     * 
     * @param email l'email de l'utilisateur
     * @return User l'utilisateur trouvé
     * @throws RuntimeException si l'utilisateur n'est pas trouvé
     */
      public User getByEmail(final String email) {
        return userRepository.findByEmail(email);
    }


    /**
     * Supprime un utilisateur par son ID (soft delete).
     * 
     * @param id l'ID de l'utilisateur à supprimer
     * @throws RuntimeException si l'utilisateur n'est pas trouvé
     */
    public void deleteUser(final String id) {
        User user = userRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "utilisateur non trouvée")
        );
        user.setStatut(Statut.DELETED);
        userRepository.save(user);
    }

    /**
     * Supprime un utilisateur par son nom d'utilisateur (soft delete).
     * 
     * @param username le nom d'utilisateur de l'utilisateur à supprimer
     * @throws RuntimeException si l'utilisateur n'est pas trouvé
     */
    public void deleteByUsername(final String username) {
        User user = getByUsername(username);
        user.setStatut(Statut.DELETED);
        userRepository.save(user);
    }

    /**
     * Récupère un rôle par son ID.
     * 
     * @param id l'ID du rôle
     * @return RoleDto le DTO du rôle trouvé
     * @throws RuntimeException si le rôle n'est pas trouvé
     */
    public RoleDto getRoleById(@NonNull final String id) {
        return mapper.maps(roleRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(() -> new RuntimeException("Role not found")));
    }

    /**
     * Récupère tous les rôles.
     * 
     * @return Set<RoleDto> l'ensemble des rôles sous forme de DTOs
     */
    public Set<RoleDto> getAllRoles() {
        return roleRepository.findAllActive().stream().map(mapper::maps).collect(Collectors.toSet());
    }

    /**
     * Supprime un rôle par son ID (soft delete).
     * 
     * @param id l'ID du rôle à supprimer
     * @throws RuntimeException si le rôle n'est pas trouvé
     */
    public void putUser(final String id, final UserDto user) {
        user.setId(id);
        if (existsByUsername(user.getUsername()) && !userRepository.findByIdAndStatut(id, Statut.ACTIVE)
            .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "utilisateur non trouvé"))
        .getUsername().equals(user.getUsername())) {
            throw new RuntimeException("Username already taken");
        }
        if (existsByEmail(user.getEmail()) && !userRepository.findByIdAndStatut(id, Statut.ACTIVE)
            .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "utilisateur non trouvé"))
            .getEmail().equals(user.getEmail())) {
            throw new RuntimeException("Email already used");
        }
        userRepository.save(mapper.maps(user));
    }

    /**
     * Modifie un rôle existant.
     * 
     * @param id   l'ID du rôle à modifier
     * @param role le DTO du rôle avec les nouvelles données
     * @throws RuntimeException si le rôle n'est pas trouvé
     */
    public void putRole(final String id, final RoleDto role) {
        role.setId(id);
        if (roleRepository.existsByName(role.getName()) && !getRoleById(id).getName().equals(role.getName())) {
            throw new RuntimeException("Role already exists");
        }
        roleRepository.save(mapper.maps(role));
    }

    /**
     * Supprime un rôle par son ID (soft delete).
     * 
     * @param id l'ID du rôle à supprimer
     * @throws RuntimeException si le rôle n'est pas trouvé
     */
    public void deleteRoleById(final String id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        role.setStatut(Statut.DELETED);
        roleRepository.save(role);
    }

    /**
     * Supprime un rôle par son nom (soft delete).
     * 
     * @param name le nom du rôle à supprimer
     * @throws RuntimeException si le rôle n'est pas trouvé
     */
    public boolean existsByUsername(final String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Vérifie si un email est déjà utilisé.
     * 
     * @param email l'email à vérifier
     * @return boolean true si l'email existe, false sinon
     */
    public boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Vérifie si un rôle existe par son nom.
     * 
     * @param name le nom du rôle à vérifier
     * @return boolean true si le rôle existe, false sinon
     */
    public PermissionDto getPermission(final String id) {
        return mapper.maps(
                permissionRepository.findByIdAndStatut(id, Statut.ACTIVE)
                        .orElseThrow(() -> new RuntimeException("Permission not found")));
    }

    /**
     * Récupère tous les rôles.
     * 
     * @return Set<RoleDto> l'ensemble des rôles sous forme de DTOs
     */
    public Set<PermissionDto> getAllPermissions() {
        return permissionRepository.findAllActive().stream().map(mapper::maps).collect(Collectors.toSet());
    }

    /**
     * Crée une nouvelle permission.
     * 
     * @param permission le DTO de la permission à créer
     * @throws RuntimeException si la permission existe déjà
     */
    public void postPermission(final PermissionDto permission) {
        if (permissionRepository.existsByName(permission.getName())) {
            throw new RuntimeException("Permission already exists");
        }
        permission.setId(Utils.generateId());
        permissionRepository.save(mapper.maps(permission));
    }

    /**
     * Modifie une permission existante.
     * 
     * @param id         l'ID de la permission à modifier
     * @param permission le DTO de la permission avec les nouvelles données
     * @throws RuntimeException si la permission n'est pas trouvée
     */
    public void putPermission(final String id, final PermissionDto permission) {
        permission.setId(id);
        if (permissionRepository.existsByName(permission.getName())
                && !getPermission(id).getName().equals(permission.getName())) {
            throw new RuntimeException("Permission name already exists");
        }
        permissionRepository.save(mapper.maps(permission));
    }

    /**
     * Supprime une permission par son ID (soft delete).
     * 
     * @param id l'ID de la permission à supprimer
     * @throws RuntimeException si la permission n'est pas trouvée
     */
    public void deletePermissionById(final String id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
        permission.setStatut(Statut.DELETED);
        permissionRepository.save(permission);
    }

}