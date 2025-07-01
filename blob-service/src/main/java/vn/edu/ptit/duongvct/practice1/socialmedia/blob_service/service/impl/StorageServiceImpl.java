package vn.edu.ptit.duongvct.practice1.socialmedia.blob_service.service.impl;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.ptit.duongvct.practice1.socialmedia.blob_service.dto.response.PreSignedURLDTO;
import vn.edu.ptit.duongvct.practice1.socialmedia.blob_service.dto.response.UploadFileDTO;
import vn.edu.ptit.duongvct.practice1.socialmedia.blob_service.service.StorageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StorageServiceImpl implements StorageService {
    private static final int MAX_IMAGE_SIZE_BYTES =  10 * 1024 * 1024; //10MB
    private static final int PRESIGNED_OBJECT_URL_EXPIRY_SECONDS = 60 * 60;//1 hour
    @Value(value = "${minio.bucket-name}")
    private String bucketName;
    private final MinioClient minioClient;

    public StorageServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public ObjectWriteResponse uploadFile(MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        validateFile(file);
        String fileName = file.getOriginalFilename();
        return minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType("application/octet-stream")
                        .build()
        );
    }

    @Override
    public PreSignedURLDTO generatePreSignedUrl(String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        PreSignedURLDTO dto = new PreSignedURLDTO();
        dto.setUrl(minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(fileName)
                        .expiry(PRESIGNED_OBJECT_URL_EXPIRY_SECONDS)
                        .build()
                )
        );
        return dto;
    }

    @Override
    public List<Item> listAllFiles() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        List<Item> items = new ArrayList<>();

        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucketName).build()
        );

        for (Result<Item> result : results) {
            items.add(result.get());
        }

        return items;
    }

    @Override
    public Resource downloadFile(String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        GetObjectResponse object = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build()
        );
        return new InputStreamResource(object);
    }

    @Override
    public UploadFileDTO mapToUploadFileDTO(ObjectWriteResponse response) {
        UploadFileDTO dto = new UploadFileDTO();
        dto.setEtag(response.etag());
        dto.setVersionId(response.versionId());
        dto.setChecksumCRC32(response.checksumCRC32());
        dto.setChecksumSHA1(response.checksumSHA1());
        dto.setChecksumSHA256(response.checksumSHA256());
        dto.setChecksumCRC32C(response.checksumCRC32C());
        return dto;
    }

    /**
     * Check if bucket exists
     */
    @PostConstruct
    public void init () throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean foundBucket;
        foundBucket = minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(bucketName)
                        .build()
        );

        if (!foundBucket) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
        }
    }
    /**
     * Validate file to check if invalid file type or file size exceeded
     * @param file
     * @throws IOException
     */
    private void validateFile(MultipartFile file) throws IOException {
        if (file.getSize() > MAX_IMAGE_SIZE_BYTES) {
            throw new IllegalArgumentException("Image size exceeded the limit.");
        }
        String contentType = Files.probeContentType(Paths.get(Objects.requireNonNull(file.getOriginalFilename())));
        if (contentType == null
                || (!contentType.startsWith("image/"))) {
            throw new IllegalArgumentException("Invalid file type");
        }
    }

}


