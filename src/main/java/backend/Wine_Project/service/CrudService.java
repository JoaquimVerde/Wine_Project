package backend.Wine_Project.service;

import java.util.List;

public interface CrudService<GET, CREATE, LONG> {
    List<GET> getAll();
    LONG create(CREATE modelCreateDto);
    void delete(Long id);
    void update(Long id, GET modelUpdateDto);
    GET get(Long id);
}
