package my.project.invoicemanager.repository;

import my.project.invoicemanager.model.Role;

import java.util.Collection;

public interface RoleRepository <T extends Role>{

    /* Basic CRUD operations */
    T create(T data);
    Collection<T> list(int page, int pageSize);
    T getById(Long id);
    T update(T data);
    Boolean delete(Long id);

    /* More complex operations */
    void addRoleToUser(Long userId, String roleName);
    Role getRoleByUserId(Long userId);
    Role getRoleByUserEmail(String email);
    void updateUserRole(Long userId, String roleName);
}
