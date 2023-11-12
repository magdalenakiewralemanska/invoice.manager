package my.project.invoicemanager.repository;

import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public interface UserRepository<T extends User> {

    /* Basic CRUD operations */
    T create(T data);
    Collection<T> list(int page, int pageSize);
    T getById(Long id);
    T update(T data);
    Boolean delete(Long id);

    /* More complex operations */
}
