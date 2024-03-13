import org.example.controller.BookController;
import org.example.entity.Book;
import org.example.exception.ResourceNotFoundException;
import org.example.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    @Test
    public void testGetBookById_whenBookExists_returnBook() {
        // Arrange
        long bookId = 1L;
        Book mockBook = new Book();
        mockBook.setId(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));

        // Act
        ResponseEntity<Book> response = bookController.getBookById(bookId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookId, response.getBody().getId());
    }

    @Test
    public void testGetBookById_whenBookNotFound_throwResourceNotFoundException() {
        // Arrange
        long nonExistentBookId = 100L;
        when(bookRepository.findById(nonExistentBookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> bookController.getBookById(nonExistentBookId));
    }
}
