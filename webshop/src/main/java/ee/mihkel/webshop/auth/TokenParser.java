package ee.mihkel.webshop.auth;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenParser extends BasicAuthenticationFilter {
    public TokenParser(@Lazy AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader("Authorization");

        System.out.println(header);
        // ÜTLEME, ET MUUTUJA HEADER SEES TULEB TOKEN. SEEGA JÄRGNEVALT
        // TOIMUB TOKENI LAHTIPAKKIMINE, ET SAADA TEADA SEALT SEEST NT ISIKUKOOD

        // SIIN PANEME ISIKUKOODI AUTHENTICATION SISSE
//        Authentication authentication = new UsernamePasswordAuthenticationToken(
//                "123",null, null
//        );
//
//        // SIIN PANEME SELLE ISIKU GLOBAALSELT SISSELOGITUKS
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.doFilterInternal(request, response, chain);
    }
}
