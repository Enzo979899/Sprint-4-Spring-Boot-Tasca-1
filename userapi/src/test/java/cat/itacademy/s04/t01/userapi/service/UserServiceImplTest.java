package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.repository.UserRepository;
import cat.itacademy.s04.t01.userapi.service.UserServiceImpl;
import cat.itacademy.s04.t01.userapi.exceptions.EmailAlreadyExistsException;
import cat.itacademy.s04.t01.userapi.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_shouldThrowExceptionWhenEmailAlreadyExists() {
        User user = new User(null, "Enzo Gallardo", "enzo@email.com");

        when(userRepository.existsByEmail("enzo@email.com")).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(user));

        verify(userRepository, never()).save(user);
    }

    @Test
    void createUser_shouldGenerateIdAndSaveUserWhenEmailDoesNotExist() {
        User user = new User(null, "Enzo Gallardo", "enzo@email.com");

        when(userRepository.existsByEmail("enzo@email.com")).thenReturn(false);

        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser.getId());

        verify(userRepository).save(user);
    }
}
