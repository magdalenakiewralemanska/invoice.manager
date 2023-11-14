package my.project.invoicemanager.repository;


import my.project.invoicemanager.model.User;

import java.util.Collection;

public interface UserRepository<T extends User> {

    /* Basic CRUD operations */
    T create(T data);
    Collection<T> list(int page, int pageSize);
    T getById(Long id);
    T update(T data);
    Boolean delete(Long id);

    T getUserByEmail(String email);

    /* More complex operations */
}
