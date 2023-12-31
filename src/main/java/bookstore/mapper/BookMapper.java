package bookstore.mapper;

import bookstore.dto.BookDto;
import bookstore.dto.BookWithoutCategoryDto;
import bookstore.dto.CreateBookRequestDto;
import bookstore.model.Book;
import bookstore.model.Category;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface BookMapper {
    BookDto toDto(Book book);

    Book toBook(CreateBookRequestDto requestDto);

    BookWithoutCategoryDto toDtoWithoutCategory(Book book);

    void updateBook(CreateBookRequestDto updatedRequestDto, @MappingTarget Book bookToUpdate);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        bookDto.setCategoryIds(book.getCategories()
                .stream()
                .map(Category::getId)
                .toList());
    }

    @AfterMapping
    default void setCategories(@MappingTarget Book book, CreateBookRequestDto bookDto) {
        book.setCategories(bookDto.categoryIds().stream()
                .map(Category::new)
                .collect(Collectors.toSet()));
    }
}
