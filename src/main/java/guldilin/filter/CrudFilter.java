package guldilin.filter;


import com.google.gson.Gson;
import guldilin.dto.CityDTO;
import guldilin.errors.ResourceNotFound;
import lombok.SneakyThrows;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

@WebFilter("/api/coordinates/*")
public class CrudFilter implements Filter {
    private Gson gson;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.gson = new Gson();
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String method = request.getMethod().toLowerCase(Locale.ROOT);
        System.out.println("");
        if (Arrays.asList("post", "put").contains(method)) {
            request.setAttribute("body", gson.fromJson(servletRequest.getReader(), CityDTO.class));
        }
        if (Arrays.asList("get", "delete").contains(method)) {
            System.out.println("request.getPathInfo() " + request.getPathInfo());
            if (request.getPathInfo() != null) {
                try {
                    Long id = Long.valueOf(request.getPathInfo().replaceAll("^/", ""));
                    request.setAttribute("id", id);
                } catch (NumberFormatException e) {
                    System.out.println("Cannot convert id");
//                    throw new ResourceNotFound();
                }
            }
        }
        response.setContentType("application/json");

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
