package my.project.invoicemanager.repository.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.project.invoicemanager.model.User;
import my.project.invoicemanager.repository.UserRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbc;
    @Override
    public User create(User user) {
        //check the email is unique
        //save new user
        //add role to the user
        //send verification URL
        //save URL in verification table
        //send email to user with verification URL
        //return the newly created user
        //if any errors, throw exception with proper message
        return null;
    }

    @Override
    public Collection list(int page, int pageSize) {
        return null;
    }

    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

}
