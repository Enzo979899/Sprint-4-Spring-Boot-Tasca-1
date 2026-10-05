package cat.itacademy.s04.t01.userapi.repository;

import cat.itacademy.s04.t01.userapi.models.User;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {

    @Test
    void save_shouldStoreUser() {
        InMemoryUserRepository repository = new InMemoryUserRepository();
        User user = new User(UUID.randomUUID(), "Enzo Gallardo", "enzo@email.com");

        repository.save(user);

        assertEquals(1, repository.findAll().size());
        assertEquals(user, repository.findAll().getFirst());
    }

    @Test
    void findById_shouldReturnUserWhenExists() {
        InMemoryUserRepository repository = new InMemoryUserRepository();

        UUID id = UUID.randomUUID();

        User user = new User(id, "Enzo Gallardo", "enzo@email.com");

        repository.save(user);

        User foundUser = repository.findById(id).orElseThrow();

        assertEquals(user, foundUser);
    }

    @Test
    void findById_shouldReturnEmptyWhenUserDoesNotExist() {
        InMemoryUserRepository repository = new InMemoryUserRepository();

        UUID randomId = UUID.randomUUID();

        assertTrue(repository.findById(randomId).isEmpty());
    }

    @Test
    void searchByName_shouldReturnMatchingUsers() {
        InMemoryUserRepository repository = new InMemoryUserRepository();

        User firstUser = new User(UUID.randomUUID(), "Enzo Gallardo", "enzo@email.com");

        User secondUser = new User(UUID.randomUUID(), "Carla Casanovas", "carla@email.com");

        repository.save(firstUser);
        repository.save(secondUser);

        List<User> result = repository.searchByName("enzo");

        assertEquals(1, result.size());
        assertEquals(firstUser, result.getFirst());
    }

    @Test
    void existsByEmail_shouldReturnTrueWhenEmailExists() {
        InMemoryUserRepository repository = new InMemoryUserRepository();

        User user = new User(UUID.randomUUID(), "Enzo Gallardo", "enzo@email.com");

        repository.save(user);

        assertTrue(repository.existsByEmail("enzo@email.com"));
    }
}
