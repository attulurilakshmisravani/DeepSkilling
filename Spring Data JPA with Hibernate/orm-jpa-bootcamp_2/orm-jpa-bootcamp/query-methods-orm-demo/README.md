# query-methods-orm-demo

This module covers two objectives in one Spring Boot app (H2 in-memory DB, port `8081`):

1. **Spring Data JPA Query Methods** — deriving queries purely from method names.
2. **O/R Mapping (relationships)** — `@ManyToOne`, `@JoinColumn`, `@OneToMany`,
   `mappedBy`, `FetchType.EAGER` / `FetchType.LAZY`, `@ManyToMany`, `@JoinTable`.

Sample data (2 authors, 5 books, 2 students, 2 courses) is seeded automatically
on startup by `DataSeeder`, so every endpoint below is curl-able immediately.

---

## 1. Query Methods (see `BookRepository`, `AuthorRepository`, `StudentRepository`, `CourseRepository`)

Spring Data JPA parses the **name of the repository method** and builds the
equivalent JPQL query — no `@Query` annotation, no SQL, no HQL string needed.
Reference: Spring Data JPA docs, *Query creation* (`jpa.query-methods.query-creation`).

| Feature | Method | What it generates |
|---|---|---|
| Containing text | `findByTitleContainingIgnoreCase(String keyword)` | `WHERE UPPER(title) LIKE UPPER('%keyword%')` |
| Starting text | `findByTitleStartingWith(String prefix)` | `WHERE title LIKE 'prefix%'` |
| Sorting (baked into name) | `findByAuthorIdOrderByPublishedDateDesc(Long id)` | `WHERE author_id = ? ORDER BY published_date DESC` |
| Sorting (dynamic `Sort` param) | `findByPriceGreaterThan(Double price, Sort sort)` | Same filter, sort order chosen at call time |
| Between dates | `findByPublishedDateBetween(LocalDate start, LocalDate end)` | `WHERE published_date BETWEEN ? AND ?` |
| Greater / less than | `findByPriceGreaterThan`, `findByPriceLessThan`, `findByPriceGreaterThanEqual`, `findByPublishedDateAfter`, `findByPublishedDateBefore` | `WHERE price > / < / >= ?`, `WHERE published_date > / < ?` |
| Top / First N | `findTop3ByOrderByPriceAsc()`, `findFirstByOrderByPublishedDateDesc()`, `findTop5ByAuthorNameOrderByPriceDesc(String name)` | `ORDER BY ... LIMIT n` |
| Traversing a relationship | `findTop5ByAuthorNameOrderByPriceDesc`, `findByCoursesTitleContainingIgnoreCase`, `findByStudentsNameIgnoreCase` | Automatically joins across `@ManyToOne`/`@ManyToMany` to reach a nested property (`Book.author.name`, `Student.courses.title`, `Course.students.name`) |

### Try it
```
# containing
curl "http://localhost:8081/api/library/books/search?keyword=harry"

# starting with
curl http://localhost:8081/api/library/books/starting-with/The

# sorted by date for one author (author id 1)
curl http://localhost:8081/api/library/authors/1/books/by-date

# greater/less than
curl "http://localhost:8081/api/library/books/price?value=600&cheaper=true"

# between dates
curl "http://localhost:8081/api/library/books/published-between?start=1950-01-01&end=2000-01-01"

# top N
curl http://localhost:8081/api/library/books/cheapest
curl http://localhost:8081/api/library/books/most-recent
curl "http://localhost:8081/api/library/books/top-by-author?authorName=J.R.R.%20Tolkien"

# dynamic sort
curl "http://localhost:8081/api/library/books/price-greater-than-sorted?price=500&sortBy=title&ascending=true"

# relationship traversal (many-to-many)
curl http://localhost:8081/api/enrollment/courses/spring/students
curl http://localhost:8081/api/enrollment/students/Alice/courses
```

---

## 2. O/R Mapping (see the `model` package)

### One-to-Many / Many-to-One — `Author` ↔ `Book`
- **`Book.author`** is annotated `@ManyToOne(fetch = FetchType.LAZY)` with
  `@JoinColumn(name = "author_id")` — Book is the **owning side**: it holds
  the foreign key column (`author_id`) in the `BOOK` table.
- **`Author.books`** is annotated `@OneToMany(mappedBy = "author", fetch = FetchType.LAZY)`
  — Author is the **inverse side**. `mappedBy = "author"` tells Hibernate
  "don't create a second join column here, the relationship is already fully
  described by `Book.author`". This side exists purely so you can conveniently
  call `author.getBooks()` in Java.
- `FetchType.LAZY` on `Book.author` means loading a `Book` does **not**
  automatically pull its `Author` row too — the author query only fires the
  moment you call `book.getAuthor()`.

### Many-to-Many — `Student` ↔ `Course`
- **`Student.courses`** is annotated `@ManyToMany(fetch = FetchType.EAGER)`
  with `@JoinTable(name = "STUDENT_COURSE", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))`.
  Student is the **owning side**: Spring/Hibernate creates a separate junction
  table `STUDENT_COURSE(student_id, course_id)` to represent the relationship,
  since neither side can hold the other's foreign key directly in a
  many-to-many.
- **`Course.students`** is annotated `@ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)`
  — the inverse side, again using `mappedBy` to say "the join table is already
  defined over on Student, just mirror it here."
- Note the **deliberate contrast in fetch types**: `Student.courses` is
  `EAGER` (loading a Student always loads all their Courses immediately),
  while `Book.author` and `Course.students` are `LAZY` (only loaded on demand).
  This is to make the EAGER vs. LAZY difference visible in the same codebase —
  in a real app you'd default to `LAZY` almost everywhere and only opt into
  `EAGER` where you know you always need the related data.

Reference: Baeldung, *Spring Data REST relationships*.

### Try it
```
# create + enroll (many-to-many)
curl -X POST "http://localhost:8081/api/enrollment/students?name=Chen"
curl -X POST "http://localhost:8081/api/enrollment/courses?title=Databases"
curl -X POST http://localhost:8081/api/enrollment/students/3/enroll/3

# create an author + a book under them (one-to-many / many-to-one)
curl -X POST "http://localhost:8081/api/library/authors?name=George%20Orwell"
curl -X POST "http://localhost:8081/api/library/authors/3/books?title=1984&price=399&publishedDate=1949-06-08"
```
