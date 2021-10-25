package guldilin.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import guldilin.dto.ValidationErrorDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ValidationErrorController extends HttpServlet {

    private Gson gson;

    public ValidationErrorController() {
        gson = new GsonBuilder().serializeNulls().create();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConstraintViolationException throwable = (ConstraintViolationException) request.getAttribute("javax.servlet.error.exception");
        Map<String, String> validationErrors = new HashMap<>();
        throwable.getConstraintViolations().forEach(
                c -> validationErrors.put(c.getPropertyPath().toString(), c.getMessage()));
        ValidationErrorDTO errorDTO = ValidationErrorDTO.builder()
                .error(ConstraintViolationException.class.getName())
                .message(validationErrors)
                .build();
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(gson.toJson(errorDTO));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
