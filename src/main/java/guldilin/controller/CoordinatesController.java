package guldilin.controller;


import guldilin.dto.CoordinatesDTO;
import guldilin.entity.Coordinates;
import guldilin.errors.EntryNotFound;
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

    public CoordinatesController() {
        repository = new CrudRepositoryImpl<>(Coordinates.class);
        crudController = new CrudController<>(
                Coordinates.class,
                CoordinatesDTO.class,
                repository,
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

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        crudController.doGet(request, response);
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
