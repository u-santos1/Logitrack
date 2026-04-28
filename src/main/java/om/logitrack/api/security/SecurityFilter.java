package om.logitrack.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import om.logitrack.api.repository.UsuarioRepository;
import om.logitrack.api.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var tokenJWT = recuperarToken(request);
            if(tokenJWT != null){
                var subject = tokenService.getSubject(tokenJWT);
                var usuario = usuarioRepository.findByEmail(subject).orElseThrow(()-> new UsernameNotFoundException("Usuario nao encontrado "));
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e){
            SecurityContextHolder.clearContext();
            log.error("Erro na autenticacao {}" , e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
    public String recuperarToken(HttpServletRequest request){

        var authorizationHEader = request.getHeader("Authorization");

        if (authorizationHEader != null && authorizationHEader.toLowerCase().startsWith("bearer ")){
            return authorizationHEader.substring(7).trim();
        }
        return null;
    }
}

