package MomenTarek.billingsoftware.service.impl;

import MomenTarek.billingsoftware.service.FileUploadService;
import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final Cloudinary cloudinary;
    @Override
    public Map<String, String> uploadFile(MultipartFile file) {

        // Generate unique name
        String uniqueFileName = UUID.randomUUID().toString();
        try {
            Map<String, Object> options = new HashMap<>();
            options.put("folder", "billing_software_categories");      // e.g. "products", "users", "jobs"
            options.put("public_id", uniqueFileName);
            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
            Map<String, String> result = new HashMap<>();
            result.put("url", uploadResult.get("secure_url").toString());
            result.put("public_id", uploadResult.get("public_id").toString());
            return result;
        }catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"An error occured while uploaded the file");
        }
    }

    @Override
    public Boolean deleteFile(String publicId) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> result =cloudinary.uploader().destroy(publicId, Map.of());
            if("ok".equals(result.get("result"))){
                return true;
            }
            else{
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"An error occured while Deleted the file");
            }
        }catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"An error occured while Deleted the file");
        }
    }
}
