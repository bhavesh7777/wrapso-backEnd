package wrapsto.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wrapsto.config.AppContext;
import wrapsto.exceptionhandling.SessionExpired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class TokenVerification {

    @Autowired
    AppContext appContext;

    /**
     * This function is used to set cookie with given details respectively
     *
     * @param response  HttpServletResponse response
     * @param tokenType AccessToken/RefreshToken
     * @param token     Jwt
     * @param path      User
     *
     **/
    public void setCookie(HttpServletResponse response, String tokenType, String token, String path) {

        Cookie cookie = new Cookie(tokenType, token);
        cookie.setHttpOnly(true);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    /**
     * This function is used to generate new AccessToken with given validity for given email
     * @param mobileNumber user's mobile number
     * @param time Validity of Token
     * @return Access/Refresh token
     */
    public String accessRefreshToken(String mobileNumber, Long time) {
        return Jwts.builder()
                .setSubject(mobileNumber)
                .setExpiration(new Date(System.currentTimeMillis() + (time)))
                .signWith(SignatureAlgorithm.HS512, appContext.getSecretKey() )
                .compact();
    }

    /**
     * This function is used to check if the token is valid
     * If AccessToken is valid, updates the RefreshToken with new one
     * If AccessToken expires, checks if RefreshToken is valid, if true updates the Existing AccessToken with new one
     * If RefreshToken expires, throws a error saying Session Expired
     *
     * @param response     HttpServletResponse
     * @param accessToken  AccessToken
     * @param refreshToken RefreshToken
     * @param path         User
     * @return mobileNumber respectively
     */
    public String checkToken(HttpServletResponse response, String accessToken, String refreshToken, String path) {
        if (extractDataFromToken1(accessToken)) {
            Claims accessTokenInfo = extractDataFromToken(accessToken);
            setCookie(response, "RefreshToken", accessRefreshToken(accessTokenInfo.getSubject(),1L), path);
            return accessTokenInfo.getSubject();
        } else if (extractDataFromToken1(refreshToken)) {
            Claims refreshTokenInfo = extractDataFromToken(refreshToken);
            setCookie(response, "AccessToken", accessRefreshToken(refreshTokenInfo.getSubject(),1L), path);
            return refreshTokenInfo.getSubject();
        } else {
            throw new SessionExpired("Session Expired");
        }
    }
    /**
     * This function is used to check token expiration
     *
     * @param token JWT
     * @return If valid returns true
     * If Invalid returns false
     */
    public boolean extractDataFromToken1(String token) {
        try {
            Claims claim = Jwts.parser().setSigningKey(appContext.getSecretKey()).parseClaimsJws(token).getBody();
            return claim.getExpiration().after(new Date(System.currentTimeMillis()));

        } catch (Exception e) {
            return false;
        }
    }
    /**
     * This function is used to extract details from Token
     *
     * @param token JWT
     * @return Claims Body of the given token
     */
    public Claims extractDataFromToken(String token) {
        return Jwts.parser().setSigningKey(appContext.getSecretKey()).parseClaimsJws(token).getBody();
    }


}
