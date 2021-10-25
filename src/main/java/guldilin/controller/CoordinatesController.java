package guldilin.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import guldilin.dto.CoordinatesDTO;
import guldilin.entity.Coordinates;
import guldilin.errors.EntryNotFound;
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
public class CoordinatesController extends HttpServlet {
    private CrudRepositoryImpl<Coordinates> repository;
    private CrudController<Coordinates, CoordinatesDTO> crudController;
    private Gson gson;

    public CoordinatesController() {
        repository = new CrudRepositoryImpl<>(Coordinates.class);
        gson = new GsonBuilder().serializeNulls().create();
        crudController = new CrudController<>(
                Coordinates.class,
                CoordinatesDTO.class,
                repository,
                gson,
                Coordinates::getFilterableFields,
                this::mapToEntity);
    }

    private Coordinates mapToEntity(CoordinatesDTO dto) {
        return Coordinates.builder()
                .id(dto.getId())
                .x(dto.getX())
                .y(dto.getY())
                .build();
    };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            crudController.doGet(request, response);
        } catch (FilterTypeNotFound filterTypeNotFound) {
            response.getWriter().write(filterTypeNotFound.getMessage());
            filterTypeNotFound.printStackTrace();
        } catch (FilterTypeNotSupported filterTypeNotSupported) {
            response.getWriter().write(filterTypeNotSupported.getMessage());
            filterTypeNotSupported.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        crudController.doPost(request, response);
    }

    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        crudController.doPut(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            crudController.doDelete(request);
        } catch (EntryNotFound entryNotFound) {
            throw new ServletException(entryNotFound);
        }
    }
}
