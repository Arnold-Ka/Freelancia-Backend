package com.hackers.freelancia.security;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackers.freelancia.dto.PermissionDto;
import com.hackers.freelancia.dto.RoleDto;
import com.hackers.freelancia.entity.Role;
import com.hackers.freelancia.entity.User;
import com.hackers.freelancia.mapper.Mapper;
import com.hackers.freelancia.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Mapper mapper;

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest request) {

        if (userService.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken");
        }

        if (userService.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already used");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userRole = userService.getByName("USER");

        user.setRoles(Set.of(userRole));

        userService.postUser(mapper.maps(user));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {

        User user = (User) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()))
                .getPrincipal();

        String accessToken = jwtService.generateToken(user);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    @PostMapping("/refresh")
    public AuthResponse refreshToken(@RequestBody RefreshRequest request) {

        RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        refreshTokenService.verifyExpiration(refreshToken);

        UserDetails userDetails = userService.loadUserByUsername(refreshToken.getUser().getUsername());

        String newAccessToken = jwtService.generateToken(userDetails);

        return new AuthResponse(newAccessToken, refreshToken.getToken());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshRequest request) {

        refreshTokenService.deleteByToken(request.getRefreshToken());

        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/roles")
    public ResponseEntity<String> postRole(@RequestBody RoleDto roleDto) {
        userService.postRole(roleDto);
        return ResponseEntity.ok("Role created successfully");
    }

    @PutMapping("/permissions/{id}")
    public ResponseEntity<String> postPermission(@PathVariable String id, @RequestBody PermissionDto permissionDto) {
        userService.putPermission(id, permissionDto);
        return ResponseEntity.ok("Permission updated successfully");
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<String> putRole(@PathVariable String id, @RequestBody RoleDto roleDto) {
        userService.putRole(id, roleDto);
        return ResponseEntity.ok("Role updated successfully");
    }

    @PostMapping("/permissions")
    public ResponseEntity<String> postPermission(@RequestBody PermissionDto permissionDto) {
        userService.postPermission(permissionDto);
        return ResponseEntity.ok("Permission created successfully");
    }

    @GetMapping("/roles")
    public ResponseEntity<Set<RoleDto>> getRoles() {
        return ResponseEntity.ok(userService.getAllRoles());
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<RoleDto> getRole(@PathVariable String id) {
        return ResponseEntity.ok(userService.getRoleById(id));
    }

    @GetMapping("/permissions")
    public ResponseEntity<Set<PermissionDto>> getPermissions() {
        return ResponseEntity.ok(userService.getAllPermissions());
    }

    @GetMapping("/permissions/{id}")
    public ResponseEntity<PermissionDto> getPermission(@PathVariable String id) {
        return ResponseEntity.ok(userService.getPermission(id));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable String id) {
        userService.deleteRoleById(id);
        return ResponseEntity.ok("Role deleted successfully");
    }

    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<String> deletePermission(@PathVariable String id) {
        userService.deletePermissionById(id);
        return ResponseEntity.ok("Permission deleted successfully");
    }

}
