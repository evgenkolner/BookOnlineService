package bookstore.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Category management", description = "endpoints")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/categories")
public class CategoryController {
}
