package com.example.demo.Model;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public void reserveBook(Long id) {
        Book book = getBookById(id);
        book.setAvailable(false);
        bookRepository.save(book);
    }
}
