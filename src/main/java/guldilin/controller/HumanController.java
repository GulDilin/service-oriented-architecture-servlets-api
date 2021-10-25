package guldilin.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import guldilin.dto.HumanDTO;
import guldilin.entity.Human;
import guldilin.errors.EntryNotFound;
import guldilin.repository.implementation.CrudRepositoryImpl;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/human/*")
public class HumanController extends HttpServlet {
    private CrudRepositoryImpl<Human> repository;
    private CrudController<Human, HumanDTO> crudController;
    private Gson gson;

    public HumanController() {
        repository = new CrudRepositoryImpl<>(Human.class);
        gson = new GsonBuilder().serializeNulls().create();
        crudController = new CrudController<>(
                Human.class,
                HumanDTO.class,
                repository,
                gson,
                Human::getFilterableFields,
                this::mapToEntity);
    }

    private Human mapToEntity(HumanDTO dto) {
        return Human.builder()
                .id(dto.getId())
                .birthday(dto.getBirthday())
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
