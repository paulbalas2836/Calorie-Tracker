package licenta.project.Security;

import licenta.project.Models.AppUser;
import licenta.project.Services.AppUserService;
import licenta.project.Utils.JwtToken;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private AppUserService appUserService;

    @Autowired
    private JwtToken jwtToken;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws SecurityException, IOException, ServletException {
        final String authorizationHeader = request.getHeader("Authorization");
        Long userId = null;
        String jwt = null;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwt = authorizationHeader.substring(7);
            userId =  this.jwtToken.extractId(this.jwtToken.extractAllClaims(jwt));
        }
        if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
            AppUser appUser = this.appUserService.findAppUserById(userId);
            if(jwtToken.validateToken(jwt, appUser)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(appUser,null);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request,response);
    }
}
