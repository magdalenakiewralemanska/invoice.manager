package my.project.invoicemanager.repository.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.project.invoicemanager.exception.ApiException;
import my.project.invoicemanager.model.Role;
import my.project.invoicemanager.model.User;
import my.project.invoicemanager.model.UserPrincipal;
import my.project.invoicemanager.repository.RoleRepository;
import my.project.invoicemanager.repository.UserRepository;
import my.project.invoicemanager.rowmapper.UserRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static my.project.invoicemanager.enumeration.RoleType.ROLE_USER;
import static my.project.invoicemanager.enumeration.VerificationType.ACCOUNT;
import static my.project.invoicemanager.query.UserQuery.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository, UserDetailsService {

    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public User create(User user) {
        if (getEmailCount(user.getEmail().trim().toLowerCase()) > 0) throw new ApiException("Email already in use. " +
                "Please use a different email and try again.");
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameters, holder);
            user.setId(requireNonNull(holder.getKey()).longValue());
            roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());
            String verificationUrl = getVerificationUrl(UUID.randomUUID().toString(), ACCOUNT.getType());
            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, Map.of("userId", user.getId(), "url", verificationUrl));
//            TODO in email service create method for sending verification url
            user.setEnabled(false);
            user.setNotLocked(true);
            return user;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
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

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", encoder.encode(user.getPassword()));
    }

    private String getVerificationUrl(String key, String type) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/user/verify/" + type + "/" + key)
                .toUriString();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getUserByEmail(email);
            if (user == null) {
                log.error("User not found in the database");
                throw new UsernameNotFoundException("User not found in the database");
            } else {
                log.info("User found in the database: {}", email);
            }
        return new UserPrincipal(user, roleRepository.getRoleByUserId(user.getId()).getPermission());
    }

    public User getUserByEmail(String email) {
        try {
            return jdbc.queryForObject(SELECT_USER_BY_EMAIL_QUERY, Map.of("email", email), new UserRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            log.error(exception.getMessage());
            throw new ApiException("No user found by email: " + email);
        } catch (Exception exception){
            log.error(exception.getMessage());
            throw new ApiException("An error occurred. Please try again.");
        }
    }
}
