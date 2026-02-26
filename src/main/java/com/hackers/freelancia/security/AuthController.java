package com.hackers.freelancia.security;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hackers.freelancia.dto.PermissionDto;
import com.hackers.freelancia.dto.RoleDto;
import com.hackers.freelancia.service.UserService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    /*
     * ==========================
     * AUTHENTIFICATION
     * ==========================
     */

    /**
     * Enregistre un nouvel utilisateur et envoie un email d'activation.
     *
     * @param request les informations d'enregistrement de l'utilisateur
     * @return une réponse contenant le token JWT et le token de rafraîchissement
     */
    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    /**
     * Active le compte d'un utilisateur à l'aide d'un token d'activation.
     *
     * @param token le token d'activation envoyé par email
     * @return une réponse indiquant que le compte a été activé avec succès
     */
    @GetMapping("/auth/activate")
    public ResponseEntity<String> activateAccount(@RequestParam String token) {
        authService.activateAccount(token);
        return ResponseEntity.ok("Compte activé avec succès !");
    }

    /**
     * Authentifie un utilisateur et génère un token JWT et un token de
     * rafraîchissement.
     *
     * @param request les informations d'authentification de l'utilisateur
     * @return une réponse contenant le token JWT et le token de rafraîchissement
     */
    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Rafraîchit un token JWT à l'aide d'un token de rafraîchissement valide.
     *
     * @param request les informations de rafraîchissement du token
     * @return une réponse contenant le nouveau token JWT et le nouveau token de
     *         rafraîchissement
     */
    @PostMapping("/auth/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @Valid @RequestBody RefreshRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    /**
     * Envoie un email de réinitialisation de mot de passe à l'utilisateur.
     *
     * @param email l'adresse email de l'utilisateur qui a oublié son mot de
     *              passe
     * @return une réponse indiquant que l'email de réinitialisation a été envoyé
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {

        authService.forgotPassword(email);

        return ResponseEntity.ok("Email de réinitialisation envoyé");
    }

    /**
     * Réinitialise le mot de passe d'un utilisateur à l'aide d'un token de
     * réinitialisation valide.
     *
     * @param token       le token de réinitialisation envoyé par email
     * @param newPassword le nouveau mot de passe à définir
     * @return une réponse indiquant que le mot de passe a été réinitialisé avec
     *         succès
     */
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword) {

        authService.resetPassword(token, newPassword);

        return ResponseEntity.ok("Mot de passe réinitialisé avec succès");
    }

    /**
     * Déconnecte un utilisateur en invalidant son token de rafraîchissement.
     *
     * @param request les informations de déconnexion contenant le token de
     *                rafraîchissement à invalider
     * @return une réponse indiquant que l'utilisateur a été déconnecté avec succès
     */
    @PostMapping("/auth/logout")
    public ResponseEntity<String> logout(
            @Valid @RequestBody RefreshRequest request) {
        authService.logout(request);
        return ResponseEntity.ok("Logged out successfully");
    }

    /*
     * ==========================
     * ROLES
     * ==========================
     */

    /**
     * Crée un nouveau rôle.
     *
     * @param roleDto les informations du rôle à créer
     * @return une réponse indiquant que le rôle a été créé avec succès
     */
    @PostMapping("auth/roles")
    public ResponseEntity<String> createRole(
            @Valid @RequestBody RoleDto roleDto) {
        userService.postRole(roleDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Role created successfully");
    }

    /**
     * Récupère tous les rôles.
     *
     * @return une réponse contenant la liste des rôles
     */
    @GetMapping("auth/roles")
    public ResponseEntity<Set<RoleDto>> getRoles() {
        return ResponseEntity.ok(userService.getAllRoles());
    }

    /**
     * Met à jour un rôle existant.
     *
     * @param id      l'ID du rôle à mettre à jour
     * @param roleDto les nouvelles informations du rôle
     * @return une réponse indiquant que le rôle a été mis à jour avec succès
     */
    @PutMapping("/roles/{id}")
    public ResponseEntity<String> updateRole(
            @PathVariable String id,
            @Valid @RequestBody RoleDto roleDto) {
        userService.putRole(id, roleDto);
        return ResponseEntity.ok("Role updated successfully");
    }

    /**
     * Supprime un rôle par son ID.
     *
     * @param id l'ID du rôle à supprimer
     * @return une réponse indiquant que le rôle a été supprimé avec succès
     */
    @DeleteMapping("/roles/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable String id) {
        userService.deleteRoleById(id);
        return ResponseEntity.ok("Role deleted successfully");
    }

    /*
     * ==========================
     * PERMISSIONS
     * ==========================
     */

    /**
     * Crée une nouvelle permission.
     *
     * @param permissionDto les informations de la permission à créer
     * @return une réponse indiquant que la permission a été créée avec succès
     */
    @PostMapping("/permissions")
    public ResponseEntity<String> createPermission(
            @Valid @RequestBody PermissionDto permissionDto) {
        userService.postPermission(permissionDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Permission created successfully");
    }

    /**
     * Récupère toutes les permissions.
     *
     * @return une réponse contenant la liste des permissions
     */
    @GetMapping("/permissions")
    public ResponseEntity<Set<PermissionDto>> getPermissions() {
        return ResponseEntity.ok(userService.getAllPermissions());
    }

    /**
     * Met à jour une permission existante.
     *
     * @param id            l'ID de la permission à mettre à jour
     * @param permissionDto les nouvelles informations de la permission
     * @return une réponse indiquant que la permission a été mise à jour avec succès
     */
    @PutMapping("/permissions/{id}")
    public ResponseEntity<String> updatePermission(
            @PathVariable String id,
            @Valid @RequestBody PermissionDto permissionDto) {
        userService.putPermission(id, permissionDto);
        return ResponseEntity.ok("Permission updated successfully");
    }

    /**
     * Supprime une permission par son ID.
     *
     * @param id l'ID de la permission à supprimer
     * @return une réponse indiquant que la permission a été supprimée avec succès
     */
    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<String> deletePermission(@PathVariable String id) {
        userService.deletePermissionById(id);
        return ResponseEntity.ok("Permission deleted successfully");
    }
}
