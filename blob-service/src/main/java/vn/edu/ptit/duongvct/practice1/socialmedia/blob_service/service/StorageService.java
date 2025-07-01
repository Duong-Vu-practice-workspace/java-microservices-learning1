package vn.edu.ptit.duongvct.practice1.socialmedia.blob_service.service;

import io.minio.ObjectWriteResponse;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.ptit.duongvct.practice1.socialmedia.blob_service.dto.response.PreSignedURLDTO;
import vn.edu.ptit.duongvct.practice1.socialmedia.blob_service.dto.response.UploadFileDTO;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface StorageService {
    /**
     * Upload file to S3
     *
     * @param file
     * @return
     * @throws Exception
     */

    public ObjectWriteResponse uploadFile(MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    /**
     * Generate pre-signed URL
     *
     * @param fileName
     * @return
     * @throws ServerException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidResponseException
     * @throws XmlParserException
     * @throws InternalException
     */
    public PreSignedURLDTO generatePreSignedUrl(String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    /**
     * List all files in S3 bucket
     * @return
     */
    public List<Item> listAllFiles() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    /**
     * Download a file in S3 bucket
     * @param fileName
     * @return
     */
    public Resource downloadFile(String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    public UploadFileDTO mapToUploadFileDTO(ObjectWriteResponse response);
}
