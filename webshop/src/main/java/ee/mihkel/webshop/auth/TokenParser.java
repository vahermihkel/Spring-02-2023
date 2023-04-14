package ee.mihkel.webshop.auth;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Log4j2
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
        log.info(header);

        if (header != null && header.startsWith("Bearer ")) {
            header = header.replace("Bearer ", "");
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey("super-secret-key")
                        .parseClaimsJws(header)
                        .getBody();

                String personalCode = claims.getSubject();

                String role = claims.getId();

                List<GrantedAuthority> authorities = new ArrayList<>();
                if (role != null && role.equals("admin")) {
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("admin");
                    authorities = new ArrayList<>(Collections.singletonList(authority));
                }

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        personalCode,null, authorities
                );
                // ÜTLEME, ET MUUTUJA HEADER SEES TULEB TOKEN. SEEGA JÄRGNEVALT
                // TOIMUB TOKENI LAHTIPAKKIMINE, ET SAADA TEADA SEALT SEEST NT ISIKUKOOD
                // SIIN PANEME ISIKUKOODI AUTHENTICATION SISSE
//
//        // SIIN PANEME SELLE ISIKU GLOBAALSELT SISSELOGITUKS
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException e) {
                log.error("QUERY WITH EXPIRED JWT {}", header);
            } catch (UnsupportedJwtException e) {
                log.error("QUERY WITH UNSUPPORTED JWT {}", header);
            } catch (MalformedJwtException e) {
                log.error("QUERY WITH MALFORMED JWT {}", header);
            } catch (SignatureException e) {
                log.error("QUERY WITH WRONG SIGNATURE JWT {}", header);
            } catch (IllegalArgumentException e) {
                log.error("QUERY WITH ILLEGAL ARGUMENT JWT {}", header);
            }
        }

        super.doFilterInternal(request, response, chain);
    }
}
