package vn.edu.ptit.duongvct.practice1.socialmedia.blob_service.controller;

import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.ptit.duongvct.practice1.socialmedia.blob_service.annotation.ApiMessage;
import vn.edu.ptit.duongvct.practice1.socialmedia.blob_service.dto.response.PreSignedURLDTO;
import vn.edu.ptit.duongvct.practice1.socialmedia.blob_service.dto.response.UploadFileDTO;
import vn.edu.ptit.duongvct.practice1.socialmedia.blob_service.service.StorageService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/storage")
public class StorageController {
    private final StorageService storageService;

    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    @ApiMessage("Upload successfully")
    public ResponseEntity<UploadFileDTO> uploadFile(@RequestParam("file") MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return ResponseEntity.status(HttpStatus.CREATED).body(storageService.mapToUploadFileDTO(storageService.uploadFile(file)));
    }

    @GetMapping("/url")
    public ResponseEntity<PreSignedURLDTO> getPresignedUrl(@RequestParam String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return  ResponseEntity.ok(storageService.generatePreSignedUrl(fileName));
    }
    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        List<String> filenames = storageService.listAllFiles()
                .stream()
                .map(Item::objectName)
                .toList();
        return ResponseEntity.ok(filenames);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Resource resource = storageService.downloadFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
