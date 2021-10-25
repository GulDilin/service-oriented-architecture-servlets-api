package guldilin.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import guldilin.dto.ErrorDTO;
import guldilin.dto.ValidationErrorDTO;
import guldilin.errors.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ErrorController extends HttpServlet {

    private final Gson gson;
    private final Map<String, Integer> errosMap;

    public ErrorController() {
        gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        errosMap = new HashMap<>();

        errosMap.put(UnsupportedMethod.class.getName(), HttpServletResponse.SC_BAD_REQUEST);
        errosMap.put(UnsupportedContentType.class.getName(), HttpServletResponse.SC_BAD_REQUEST);
        errosMap.put(EntryNotFound.class.getName(), HttpServletResponse.SC_NOT_FOUND);
        errosMap.put(ResourceNotFound.class.getName(), HttpServletResponse.SC_BAD_REQUEST);
        errosMap.put(FilterTypeNotFound.class.getName(), HttpServletResponse.SC_BAD_REQUEST);
        errosMap.put(FilterTypeNotSupported.class.getName(), HttpServletResponse.SC_BAD_REQUEST);
        errosMap.put(UnknownFilterType.class.getName(), HttpServletResponse.SC_BAD_REQUEST);
        errosMap.put(javax.persistence.NoResultException.class.getName(), HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doValidationError(HttpServletResponse response, Throwable throwable)
            throws IOException {
        System.out.println("Catch validation Exception");
        ValidationException validationException = (ValidationException) throwable;
        ValidationErrorDTO errorDTO = ValidationErrorDTO.builder()
                .error(ValidationException.class.getName())
                .message(validationException.getFieldsErrors())
                .build();
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(gson.toJson(errorDTO));
    }

    protected void doConstraintViolationException(HttpServletResponse response, Throwable throwable)
            throws IOException {
        System.out.println("Catch validation Exception");
        ConstraintViolationException validationError = (ConstraintViolationException) throwable;
        Map<String, String> validationErrors = new HashMap<>();
        validationError.getConstraintViolations().forEach(
                c -> validationErrors.put(c.getPropertyPath().toString(), c.getMessage()));
        ValidationErrorDTO errorDTO = ValidationErrorDTO.builder()
                .error(ConstraintViolationException.class.getName())
                .message(validationErrors)
                .build();
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(gson.toJson(errorDTO));
    }

    protected void doDefaultError(HttpServletRequest request, HttpServletResponse response,
                                  Throwable throwable, String errorName)
            throws IOException {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        if (errosMap.containsKey(errorName)) {
            statusCode = errosMap.get(errorName);
        }
        ErrorDTO errorDTO = ErrorDTO.builder()
                .error(errorName)
                .message(throwable.getMessage())
                .build();
        response.setContentType("application/json");
        response.setStatus(statusCode);
        response.getWriter().write(gson.toJson(errorDTO));
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String errorName = throwable.getClass().getName();

        switch (errorName) {
            case "guldilin.errors.ValidationException":
                doValidationError(response, throwable);
                break;
            case "javax.validation.ConstraintViolationException":
                doConstraintViolationException(response, throwable);
                break;
            default:
                doDefaultError(request, response, throwable, errorName);
                break;
        }


    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
