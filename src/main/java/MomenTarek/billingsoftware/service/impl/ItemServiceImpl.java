package MomenTarek.billingsoftware.service.impl;

import MomenTarek.billingsoftware.entity.CategoryEntity;
import MomenTarek.billingsoftware.entity.ItemEntity;
import MomenTarek.billingsoftware.io.ItemRequest;
import MomenTarek.billingsoftware.io.ItemResponse;
import MomenTarek.billingsoftware.repository.CategoryRepository;
import MomenTarek.billingsoftware.repository.ItemRepository;
import MomenTarek.billingsoftware.service.FileUploadService;
import MomenTarek.billingsoftware.service.ItemService;
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
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final FileUploadService fileUploadService;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    @Override
    public ItemResponse add(ItemRequest request, MultipartFile file) {
        Map<String,String> results =fileUploadService.uploadFile(file);
        ItemEntity newItem=convertToEntity(request);
        CategoryEntity existingCategory=categoryRepository.findByCategoryCode(request.getCategoryId())
                .orElseThrow(()->new RuntimeException("Category not found :"+request.getCategoryId()));
        newItem.setImgPublicId(results.get("public_id"));
        newItem.setImgUrl(results.get("url"));
        newItem.setCategory(existingCategory);
        itemRepository.save(newItem);
        return convertToResponse(newItem);


    }


    private ItemResponse convertToResponse(ItemEntity newItem) {
        return ItemResponse.builder()
                .itemId(newItem.getItemId())
                .name(newItem.getName())
                .description(newItem.getDescription())
                .price(newItem.getPrice())
                .imgUrl(newItem.getImgUrl())
                .categoryName(newItem.getCategory().getName())
                .categoryId(newItem.getCategory().getCategoryCode())
                .createdAt(newItem.getCreatedAt())
                .updatedAt(newItem.getUpdatedAt())
                .build();
    }

    private ItemEntity convertToEntity(ItemRequest request) {
        return ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
    }

    @Override
    public List<ItemResponse> fetchItems() {
        return itemRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(String itemId) {
        ItemEntity existingItem=itemRepository.findByItemId(itemId)
                .orElseThrow(()->new RuntimeException("Item not found :"+itemId));
        boolean isFileDelete=fileUploadService.deleteFile(existingItem.getImgPublicId());
        if(isFileDelete){
            itemRepository.delete(existingItem);
        }
        else{
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Unable to delete the image");
        }
    }
}
