package br.com.lucasveras.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.lucasveras.todolist.user.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTask extends OncePerRequestFilter{
    @Autowired
    private IUserRepository iUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if(servletPath.equals("/task/")){

            var authorization = request.getHeader("Authorization");

            var authEncoded = authorization.substring("Basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(authEncoded);

            var authString = new String(authDecode);

            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];
            // Validando usuário
            var user = iUserRepository.findByUserName(username);
            if(user == null){
                response.sendError(401);
            } else{
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray() ,user.getPassword());
                if(passwordVerify.verified){
                    filterChain.doFilter(request,response);
                } else {
                    response.sendError(401);
                }
            }
        } else {
            filterChain.doFilter(request,response);
        }

    }
}
