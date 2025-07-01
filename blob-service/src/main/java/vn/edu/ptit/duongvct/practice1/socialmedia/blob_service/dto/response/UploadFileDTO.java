package vn.edu.ptit.duongvct.practice1.socialmedia.blob_service.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFileDTO {
    private String etag;
    private String versionId;
    private String checksumCRC32;
    private String checksumCRC32C;
    private String checksumSHA1;
    private String checksumSHA256;
}
