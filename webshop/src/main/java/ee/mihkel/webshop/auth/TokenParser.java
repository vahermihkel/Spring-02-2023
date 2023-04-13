package ee.mihkel.webshop.auth;

import io.jsonwebtoken.*;
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

        if (header != null && !header.startsWith("Bearer ")) {
            header = header.replace("Bearer ", "");
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey("super-secret-key")
                        .parseClaimsJws(header)
                        .getBody();

                String personalCode = claims.getSubject();
                // ÜTLEME, ET MUUTUJA HEADER SEES TULEB TOKEN. SEEGA JÄRGNEVALT
                // TOIMUB TOKENI LAHTIPAKKIMINE, ET SAADA TEADA SEALT SEEST NT ISIKUKOOD
                // SIIN PANEME ISIKUKOODI AUTHENTICATION SISSE
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        personalCode,null, null
                );
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
