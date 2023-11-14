package my.project.invoicemanager.service.implementation;

import lombok.RequiredArgsConstructor;
import my.project.invoicemanager.dto.UserDto;
import my.project.invoicemanager.dto.mapper.UserDtoMapper;
import my.project.invoicemanager.model.User;
import my.project.invoicemanager.repository.UserRepository;
import my.project.invoicemanager.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository<User> userRepository;
    @Override
    public UserDto createUser(User user) {
        return UserDtoMapper.fromUser(userRepository.create(user));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return UserDtoMapper.fromUser(userRepository.getUserByEmail(email));
    }

}
