package MomenTarek.billingsoftware.service.impl;

import MomenTarek.billingsoftware.entity.CategoryEntity;
import MomenTarek.billingsoftware.io.CategoryRequest;
import MomenTarek.billingsoftware.io.CategoryResponse;
import MomenTarek.billingsoftware.repository.CategoryRepository;
import MomenTarek.billingsoftware.repository.ItemRepository;
import MomenTarek.billingsoftware.service.CategoryService;
import MomenTarek.billingsoftware.service.FileUploadService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;
    private final ItemRepository itemRepository;
    @Override
    public CategoryResponse add(CategoryRequest request, MultipartFile file) {
        Map<String,String>result=fileUploadService.uploadFile(file);
        CategoryEntity newCategory=convertToEntity(request);
        newCategory.setImageUrl(result.get("url"));
        newCategory.setImagePublicId(result.get("public_id"));
        newCategory=categoryRepository.save(newCategory);
        return convertToResponse(newCategory);
    }

    @Override
    public List<CategoryResponse> read() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String categoryCode) {
        CategoryEntity existingCategory= categoryRepository.findByCategoryCode(categoryCode)
                .orElseThrow(()-> new RuntimeException("Category not found: "+categoryCode));
        if(existingCategory.getImagePublicId()!=null) {
            boolean isFileDelete=fileUploadService.deleteFile(existingCategory.getImagePublicId());
            if(isFileDelete){
                categoryRepository.delete(existingCategory);
            }
            else{
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Unable to delete the image");
            }
        }
        else{
            categoryRepository.delete(existingCategory);
        }

    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
        Integer itemsCount=itemRepository.countByCategoryId(newCategory.getId());
        return CategoryResponse.builder()
                .categoryCode(newCategory.getCategoryCode())
                .name(newCategory.getName())
                .description(newCategory.getDescription())
                .bgColor(newCategory.getBgColor())
                .imageUrl(newCategory.getImageUrl())
                .createdAt(newCategory.getCreatedAt())
                .updatedAt(newCategory.getUpdatedAt())
                .items(itemsCount)
                .build();

    }

    private CategoryEntity convertToEntity(CategoryRequest request) {
        return CategoryEntity.builder()
                .categoryCode(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .bgColor(request.getBgColor()).build();
    }
}
