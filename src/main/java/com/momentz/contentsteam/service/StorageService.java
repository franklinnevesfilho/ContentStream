package com.momentz.contentsteam.service;

import com.momentz.contentsteam.model.Response;
import com.momentz.contentsteam.utils.factories.ResponseFactory;
import com.momentz.contentsteam.utils.storage.BucketStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Service
@Slf4j
public class StorageService extends MainService{

    private ResponseFactory responseFactory;
    private BucketStorage bucketStorage;

    public ResponseEntity<Response> upload(MultipartFile file, String bucketName) {

        boolean result = bucketStorage.upload(file, bucketName);

        if (!result) {
            log.error("Failed to upload file");
            return responseFactory.generateServerError();
        }else{
            return responseFactory.generateOk(Response.builder()
                            .data(mapToJson("Upload successfully"))
                    .build());
        }
    }

    public ResponseEntity<Response> getFileLink(String fileName, String bucketName) {
        int DEFAULT_LINK_TIMEOUT = 60;
        TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;

        String url = bucketStorage.getUrl(fileName, bucketName, DEFAULT_LINK_TIMEOUT, DEFAULT_TIME_UNIT);

        return url == null ?
                responseFactory.generateServerError()
                : responseFactory.generateOk(Response.builder()
                        .data(mapToJson(url))
                .build());
    }

    public ResponseEntity<Response> deleteFile(String fileName, String bucketName) {
        boolean result = bucketStorage.deleteFile(fileName, bucketName);

        if (!result) {
            log.error("Failed to delete file");
            return responseFactory.generateServerError();
        }else{
            return responseFactory.generateOk(null);
        }
    }


}
