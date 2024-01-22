package backend.Wine_Project.service;

import java.util.List;

public interface CrudeService<GET,CREATE> {
    List<GET> getAll();
    GET create(CREATE wine);
    void delete(Long id);
    void update(Long id, GET wine);
    GET get(Long id);
}
