package guldilin.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import guldilin.dto.CityDTO;
import guldilin.entity.City;
import guldilin.entity.Coordinates;
import guldilin.entity.Human;
import guldilin.entity.ValidationMessages;
import guldilin.errors.EntryNotFound;
import guldilin.errors.ValidationException;
import guldilin.repository.implementation.CrudRepositoryImpl;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@WebServlet("/api/city/*")
public class CityController extends HttpServlet {
    private CrudRepositoryImpl<City> repository;
    private CrudRepositoryImpl<Human> repositoryHuman;
    private CrudRepositoryImpl<Coordinates> repositoryCoordinates;
    private CrudController<City, CityDTO> crudController;
    private Gson gson;

    public CityController() {
        repository = new CrudRepositoryImpl<>(City.class);
        repositoryHuman = new CrudRepositoryImpl<>(Human.class);
        repositoryCoordinates = new CrudRepositoryImpl<>(Coordinates.class);
        gson = new GsonBuilder().serializeNulls().create();
        crudController = new CrudController<>(
                City.class,
                CityDTO.class,
                repository,
                gson,
                City::getFilterableFields,
                this::mapToEntity);
    }

    @SneakyThrows
    private City mapToEntity(CityDTO dto) {
        return City.builder()
                .id(dto.getId())
                .name(dto.getName())
                .coordinates(Objects.isNull(dto.getCoordinates()) ? null : repositoryCoordinates
                        .findById(dto.getCoordinates()).orElseThrow(() -> {
                            HashMap<String, String> errorsMap = new HashMap<>();
                            errorsMap.put("coordinates", ValidationMessages.NOT_FOUND);
                            return new ValidationException(errorsMap);
                        }))
                .area(dto.getArea())
                .population(dto.getPopulation())
                .metersAboveSeaLevel(dto.getMetersAboveSeaLevel())
                .populationDensity(dto.getPopulationDensity())
                .carCode(dto.getCarCode())
                .climate(dto.getClimate())
                .governor(Objects.isNull(dto.getGovernor()) ? null : repositoryHuman
                        .findById(dto.getGovernor()).orElseThrow(() -> {
                            HashMap<String, String> errorsMap = new HashMap<>();
                            errorsMap.put("governor", ValidationMessages.NOT_FOUND);
                            return new ValidationException(errorsMap);
                        }))
                .build();
    }

    ;

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
