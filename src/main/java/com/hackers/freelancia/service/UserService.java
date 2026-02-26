package com.hackers.freelancia.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
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
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username));
    }

    /**
     * Récupère un rôle par son nom.
     * 
     * @param name le nom du rôle
     * @return Role le rôle trouvé
     * @throws RuntimeException si le rôle n'est pas trouvé
     */
    public Role getByName(final String name) throws NotFoundException {
        return roleRepository.findByNameAndStatut(name, Statut.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    /**
     * Crée un nouveau rôle.
     * 
     * @param role le DTO du rôle à créer
     */
    public void postRole(final RoleDto roleDto) throws NotFoundException {
        if (roleRepository.existsByName(roleDto.getName())) {
            throw new RuntimeException("Role already exists");
        }
        roleDto.setId(Utils.generateId());
        List<String> permissionsName = roleDto.getPermissionsName();
        Set<Permission> permissions = new HashSet<>();
        for(String permissionName: permissionsName){
            permissions.add(permissionRepository.findByNameAndStatut(permissionName,Statut.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Permission non Trouvé")
            ));
        }
        Role role = mapper.maps(roleDto);
        role.setPermissions(permissions);
        roleRepository.save(role);
    }

    /**
     * Récupère tous les utilisateurs.
     * 
     * @return Set<UserDto> l'ensemble des utilisateurs sous forme de DTOs
     */
    public Set<UserDto> getAllUsers() throws NotFoundException {
        return userRepository.findAllActive().stream().map(mapper::maps).collect(Collectors.toSet());
    }

    /**
     * Récupère un utilisateur par son ID.
     * 
     * @param id l'ID de l'utilisateur
     * @return UserDto le DTO de l'utilisateur trouvé
     * @throws RuntimeException si l'utilisateur n'est pas trouvé
     */
    public UserDto getUser(@NonNull final String id) throws NotFoundException {
        return mapper.maps(userRepository.findByIdAndStatut(id, Statut.ACTIVE)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    /**
     * Crée un nouvel utilisateur.
     * 
     * @param user le DTO de l'utilisateur à créer
     * @throws RuntimeException si le nom d'utilisateur ou l'email est déjà utilisé
     */
    public void postUser(final UserDto userDto) throws NotFoundException {

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
    public User getByUsername(final String username) throws NotFoundException {
        return userRepository.findByUsernameAndStatut(username, Statut.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "utilisateur non trouvée"));
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
    public void deleteUser(final String id) throws NotFoundException {
        User user = userRepository.findByIdAndStatut(id, Statut.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "utilisateur non trouvée"));
        user.setStatut(Statut.DELETED);
        userRepository.save(user);
    }

    /**
     * Supprime un utilisateur par son nom d'utilisateur (soft delete).
     * 
     * @param username le nom d'utilisateur de l'utilisateur à supprimer
     * @throws RuntimeException si l'utilisateur n'est pas trouvé
     */
    public void deleteByUsername(final String username) throws NotFoundException {
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
    public RoleDto getRoleById(@NonNull final String id) throws NotFoundException {
        Role role = roleRepository.findByIdAndStatut(id, Statut.ACTIVE)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        RoleDto roleDto = mapper.maps(role);
        Set<Permission> permissions = role.getPermissions();
        List<String> permissionsString = new ArrayList<>();
        for (Permission permission : permissions) {
            permissionsString.add(permission.getName());
        }
        roleDto.setPermissionsName(permissionsString);
        return roleDto;
    }

    /**
     * Récupère tous les rôles.
     * 
     * @return Set<RoleDto> l'ensemble des rôles sous forme de DTOs
     */
    public List<RoleDto> getAllRoles() throws NotFoundException {
        List<Role> roles = roleRepository.findAllActive();
        List<RoleDto> roleDtos = new ArrayList<>();

        for (Role role : roles) {
            RoleDto roleDto = mapper.maps(role);
            Set<Permission> permissions = role.getPermissions();
            List<String> permissionsString = new ArrayList<>();
            for (Permission permission : permissions) {
                permissionsString.add(permission.getName());
            }
            roleDto.setPermissionsName(permissionsString);
            roleDtos.add(roleDto);
        }
        return roleDtos;
    }

    /**
     * Supprime un rôle par son ID (soft delete).
     * 
     * @param id      l'ID du rôle à supprimer
     * @param userDto les nouvelles données
     * @throws RuntimeException si le rôle n'est pas trouvé
     */
    public void putUser(final String id, final UserDto userDto) throws NotFoundException {

        userDto.setId(id);

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));

        if (!existingUser.getUsername().equals(userDto.getUsername()) && existsByUsername(userDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already taken");
        }
        if (!existingUser.getEmail().equals(userDto.getEmail()) && existsByEmail(userDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already used");
        }

        User updatedUser = mapper.maps(userDto);
        userRepository.save(updatedUser);
    }

    /**
     * Modifie un rôle existant.
     * 
     * @param id   l'ID du rôle à modifier
     * @param role le DTO du rôle avec les nouvelles données
     * @throws RuntimeException si le rôle n'est pas trouvé
     */
    public void putRole(final String id, final RoleDto roleDto) throws NotFoundException {
        roleDto.setId(id);
        if (roleRepository.existsByName(roleDto.getName()) && !getRoleById(id).getName().equals(roleDto.getName())) {
            throw new RuntimeException("Role already exists");
        }
        List<String> permissionsName = roleDto.getPermissionsName();
        Set<Permission> permissions = new HashSet<>();
        for(String permissionName: permissionsName){
            permissions.add(permissionRepository.findByNameAndStatut(permissionName,Statut.ACTIVE).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Permission non Trouvé")
            ));
        }
        Role role = mapper.maps(roleDto);
        role.setPermissions(permissions);
        roleRepository.save(role);
    }

    /**
     * Supprime un rôle par son ID (soft delete).
     * 
     * @param id l'ID du rôle à supprimer
     * @throws RuntimeException si le rôle n'est pas trouvé
     */
    public void deleteRoleById(final String id) throws NotFoundException {
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
    public PermissionDto getPermission(final String id) throws NotFoundException {
        return mapper.maps(
                permissionRepository.findByIdAndStatut(id, Statut.ACTIVE)
                        .orElseThrow(() -> new RuntimeException("Permission not found")));
    }

    /**
     * Récupère tous les rôles.
     * 
     * @return Set<RoleDto> l'ensemble des rôles sous forme de DTOs
     */
    public Set<PermissionDto> getAllPermissions() throws NotFoundException {
        return permissionRepository.findAllActive().stream().map(mapper::maps).collect(Collectors.toSet());
    }

    /**
     * Crée une nouvelle permission.
     * 
     * @param permission le DTO de la permission à créer
     * @throws RuntimeException si la permission existe déjà
     */
    public void postPermission(final PermissionDto permission) throws NotFoundException {
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
    public void putPermission(final String id, final PermissionDto permission) throws NotFoundException {
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
    public void deletePermissionById(final String id) throws NotFoundException {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));
        permission.setStatut(Statut.DELETED);
        permissionRepository.save(permission);
    }

}