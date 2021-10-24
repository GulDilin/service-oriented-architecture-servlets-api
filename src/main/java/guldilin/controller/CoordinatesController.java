package guldilin.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import guldilin.entity.Coordinates;
import guldilin.errors.FilterTypeNotFound;
import guldilin.errors.FilterTypeNotSupported;
import guldilin.repository.implementation.CrudRepositoryImpl;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/coordinates/*")
public class CoordinatesController  extends HttpServlet {
    private CrudRepositoryImpl<Coordinates> repository;
    private CrudController<Coordinates> crudController;
    private Gson gson;

    public CoordinatesController() {
        repository = new CrudRepositoryImpl<>(Coordinates.class);
        gson = new GsonBuilder().serializeNulls().create();
        crudController = new CrudController<>(repository, Coordinates.class, gson, Coordinates::getFilterableFields);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("GEEEET REQUEEEEST____________________");
        try {
            crudController.doGet(req, resp);
        } catch (FilterTypeNotFound filterTypeNotFound) {
            resp.getWriter().write(filterTypeNotFound.getMessage());
            filterTypeNotFound.printStackTrace();
        } catch (FilterTypeNotSupported filterTypeNotSupported) {
            resp.getWriter().write(filterTypeNotSupported.getMessage());
            filterTypeNotSupported.printStackTrace();
        }
    }
}
