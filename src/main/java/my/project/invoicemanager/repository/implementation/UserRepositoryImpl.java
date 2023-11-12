package my.project.invoicemanager.repository.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.project.invoicemanager.exception.ApiException;
import my.project.invoicemanager.model.User;
import my.project.invoicemanager.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

import static my.project.invoicemanager.query.UserQuery.COUNT_USER_EMAIL_QUERY;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public User create(User user) {
        //check the email is unique
        if(getEmailCount(user.getEmail().trim().toLowerCase()) > 0) throw new ApiException("Email already in use. " +
            "Please use a different email and try again.");
        //save new user
        try{
            //add role to the user
            //send verification URL
            //save URL in verification table
            //send email to user with verification URL
            //return the newly created user
            //if any errors, throw exception with proper message
        } catch (EmptyResultDataAccessException exception) {

        } catch (Exception exception){

        }
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

    private Integer getEmailCount(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, Map.of("email", email), Integer.class);
    }

}
