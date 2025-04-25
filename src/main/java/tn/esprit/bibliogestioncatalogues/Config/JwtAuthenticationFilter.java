package tn.esprit.bibliogestioncatalogues.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                // Parser le token JWT avec java-jwt
                DecodedJWT decodedJWT = JWT.decode(token);

                // Log des claims pour débogage
                logger.debug("Claims du token JWT : {}", decodedJWT.getClaims());

                // Extraction des rôles
                List<String> roles = new ArrayList<>();

                // 1. Vérifier si "realm_access" existe
                Claim realmAccessClaim = decodedJWT.getClaim("realm_access");
                if (!realmAccessClaim.isNull()) {
                    Map<String, Object> realmAccess = realmAccessClaim.asMap();
                    List<String> realmRoles = (List<String>) realmAccess.get("roles");
                    if (realmRoles != null) {
                        roles.addAll(realmRoles);
                    }
                }

                // 2. Vérifier si un claim "roles" existe directement
                Claim rolesClaim = decodedJWT.getClaim("roles");
                if (!rolesClaim.isNull()) {
                    List<String> directRoles = rolesClaim.asList(String.class);
                    if (directRoles != null) {
                        roles.addAll(directRoles);
                    }
                }

                // 3. Vérifier si "resource_access" existe (rôles spécifiques au client)
                Claim resourceAccessClaim = decodedJWT.getClaim("resource_access");
                if (!resourceAccessClaim.isNull()) {
                    Map<String, Object> resourceAccess = resourceAccessClaim.asMap();
                    // Remplacez "angular-client" par l'ID de votre client Keycloak
                    Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get("angular-client");
                    if (clientAccess != null) {
                        List<String> clientRoles = (List<String>) clientAccess.get("roles");
                        if (clientRoles != null) {
                            roles.addAll(clientRoles);
                        }
                    }
                }

                // Si aucun rôle n'est trouvé, loguer un avertissement
                if (roles.isEmpty()) {
                    logger.warn("Aucun rôle trouvé dans le token JWT pour l'utilisateur : {}", decodedJWT.getSubject());
                }

                // Conversion des rôles en SimpleGrantedAuthority
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList());

                // Création de l'objet d'authentification
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        decodedJWT.getSubject(),
                        null,
                        authorities
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                logger.error("Erreur lors du parsing du jeton JWT", e);
            }
        }

        filterChain.doFilter(request, response);
    }
}