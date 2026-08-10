package MomenTarek.billingsoftware.controller;

import MomenTarek.billingsoftware.io.CategoryRequest;
import MomenTarek.billingsoftware.io.CategoryResponse;
import MomenTarek.billingsoftware.io.ItemRequest;
import MomenTarek.billingsoftware.io.ItemResponse;
import MomenTarek.billingsoftware.service.ItemService;
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
public class ItemController {
    private final ItemService itemService;

    @PostMapping("/admin/items")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse addItem(@RequestPart("item") String itemString, @RequestPart("file") MultipartFile file){
        ObjectMapper objectMapper=new ObjectMapper();
        if (!file.getContentType().startsWith("image/")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Only images allowed");
        }
        try {
            ItemRequest request=objectMapper.readValue(itemString,ItemRequest.class);
            return itemService.add(request,file);
        }catch (JsonParseException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Exception occurred while parsing the json: "+e.getMessage());
        }
    }
    @GetMapping("/items")
    public List<ItemResponse> readItems(){
        return itemService.fetchItems();
    }
    @DeleteMapping("/admin/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItem(@PathVariable String itemId){
        try {
            itemService.deleteItem(itemId);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Item not found");
        }
    }
}
