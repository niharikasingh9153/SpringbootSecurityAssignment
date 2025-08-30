package com.example.demo.Controller;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/public")
    public String publicBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "publicBooks";
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping
    public String getBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "bookList";
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/{id}")
    public String getBook(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        return "bookDetails";
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/{id}/reserve")
    public String reserveBook(@PathVariable Long id) {
        bookService.reserveBook(id);
        return "redirect:/books";
    }
}

