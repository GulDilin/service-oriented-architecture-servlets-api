package guldilin.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import guldilin.dto.ErrorDTO;
import guldilin.errors.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ErrorController extends HttpServlet {

    private Gson gson;
    private Map<String, Integer> errosMap;

    public ErrorController() {
        gson = new GsonBuilder().serializeNulls().create();
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer)  request.getAttribute("javax.servlet.error.status_code");
        String errorName = throwable.getClass().getName();
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
