package dependencies;

import com.fsd.taskmanager.data.entity.User;
import com.fsd.taskmanager.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by MD63MId on 19/09/2019.
 */
public class UserRepositoryDependency implements UserRepository {
    @Override
    public <S extends User> S save(S s) {
        return null;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<User> findById(Integer integer) {
        return null;
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<User> findAll() {
         User user;

         List<User> users = new ArrayList<>();

        user = User.builder().userId(1).employeeId(111).firstName("Manju").lastName("Sahu").build();
        User user1 = User.builder().userId(2).employeeId(222).firstName("Sathya").lastName("Mani").build();
        users.add(user);
        users.add(user1);

        return users;
    }

    @Override
    public Iterable<User> findAllById(Iterable<Integer> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
