package ru.magentasmalltalk.db;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.magentasmalltalk.model.User;

import java.util.List;

@Repository
public interface UsersRepository extends CrudRepository<User, Integer> {
    User findByLogin(String login);

    @Query("select u from User u where u.id > :limit")
    List<User> findUsersWithBigIds(int limit);
}
