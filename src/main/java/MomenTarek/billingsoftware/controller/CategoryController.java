package MomenTarek.billingsoftware.controller;

import MomenTarek.billingsoftware.io.CategoryRequest;
import MomenTarek.billingsoftware.io.CategoryResponse;
import MomenTarek.billingsoftware.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController

@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestPart("category") String categoryString, @RequestPart("file")MultipartFile file){
        ObjectMapper objectMapper=new ObjectMapper();
        if (!file.getContentType().startsWith("image/")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Only images allowed");
        }
        try {
            CategoryRequest request=objectMapper.readValue(categoryString,CategoryRequest.class);
            return categoryService.add(request,file);
        }catch (JsonParseException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Exception occurred while parsing the json: "+e.getMessage());
        }
    }
    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> fetchCategories(){
        return categoryService.read();
    }

    @DeleteMapping("/admin/categories/{categoryCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable String categoryCode){
        try {
           categoryService.delete(categoryCode);
        }catch (Exception e){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

}
