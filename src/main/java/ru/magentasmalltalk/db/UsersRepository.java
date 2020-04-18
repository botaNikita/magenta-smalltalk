package ru.magentasmalltalk.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.magentasmalltalk.model.User;

import java.util.List;

@Repository
@RepositoryRestResource(path = "users-api", collectionResourceRel = "users-api", itemResourceRel = "users-api")
//public interface UsersRepository extends CrudRepository<User, Integer> {
public interface UsersRepository extends PagingAndSortingRepository<User, Integer> {

    User findByLogin(@Param("login-param") String login);

    Page<User> findUsersByLoginIsLike(@Param("template") String loginTemplate, Pageable page);

    @Query("select u from User u where u.id > :limit")
    List<User> findUsersWithBigIds(@Param("limit") int limit);
}
