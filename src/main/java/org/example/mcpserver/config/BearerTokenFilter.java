package org.example.mcpserver.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class BearerTokenFilter extends OncePerRequestFilter {

  private final String expectedToken;

  public BearerTokenFilter(String expectedToken) {
    this.expectedToken = expectedToken;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    String header = request.getHeader("Authorization");
    if (header != null && header.startsWith("Bearer ")
        && expectedToken.equals(header.substring(7).trim())) {
      SecurityContextHolder.getContext().setAuthentication(
          new UsernamePasswordAuthenticationToken("mcp-client", null, List.of())
      );
    }
    chain.doFilter(request, response);
  }
}