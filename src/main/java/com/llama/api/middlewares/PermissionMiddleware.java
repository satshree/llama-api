package com.llama.api.middlewares;

import com.llama.api.Utils;
import com.llama.api.authentication.jwt.JwtUtils;
import com.llama.api.users.models.Users;
import com.llama.api.users.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class PermissionMiddleware implements HandlerInterceptor {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestURI = request.getRequestURI();

        if (isAdminAllowedUrl(requestURI)) {
            // ONLY PERFORM THESE IF THE URLS MATCH FOR ADMIN
            String header = request.getHeader("Authorization");

            if (header != null && header.startsWith("Bearer ")) {
                String jwtToken = header.substring(7);

                if (jwtUtils.validateJwtToken(jwtToken)) {
                    Users user = userService.getUserByUsername(
                            jwtUtils.getUserNameFromJwtToken(jwtToken)
                    );

                    if (!user.getIsSuper()) {
                        // ONLY ALLOW SUPER USER

                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                    }
                } else {
                    return false;
                }
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid JWT token");
                return false;
            }
        }

        return true;
    }

    private boolean isAdminAllowedUrl(String requestURI) {
        return Utils.getAdminURLs().stream().anyMatch(requestURI::startsWith);
    }
}
