package my.project.invoicemanager.service;

import my.project.invoicemanager.dto.UserDto;
import my.project.invoicemanager.model.User;

public interface UserService {

    UserDto createUser(User user);
}
