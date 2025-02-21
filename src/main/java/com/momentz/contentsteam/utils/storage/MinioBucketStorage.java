package com.momentz.contentsteam.utils.storage;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
@Profile("dev")
@Slf4j
public class MinioBucketStorage implements BucketStorage {

    private MinioClient minioClient;

    /**
     * @return a list of all active bucket names
     */
    @Override
    public List<String> getAllBucketNames() {
        try{
            List<Bucket> buckets = minioClient.listBuckets();
            return buckets.stream().map(Bucket::name).collect(Collectors.toList());
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * @param bucketName The name of bucket to add
     * @return true if the bucket was added successfully or false otherwise
     */
    @Override
    public boolean addBucket(String bucketName) {
        boolean result = false;
        try{
            BucketExistsArgs bucketExists = BucketExistsArgs.builder().bucket(bucketName).build();
            if(minioClient.bucketExists(bucketExists)){
                minioClient.makeBucket(MakeBucketArgs.builder()
                                .bucket(bucketName)
                        .build());

                // Sanity check
                result = minioClient.bucketExists(bucketExists);
            }
        } catch (Exception e){
            log.error(e.getMessage());
        }

        return result;
    }

    /**
     * @param bucketName name of bucket to delete
     * @return true if the bucket has been deleted successfully
     */
    @Override
    public boolean deleteBucket(String bucketName) {
        boolean result = false;
        try{
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
            result = !minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return result;
    }

    /**
     * @param file       file to upload
     * @param bucketName the bucket name for which to upload it to
     * @return true if the file has been added successfully
     */
    @Override
    public boolean upload(MultipartFile file, String bucketName) {
        boolean result = false;

        try{
            String objectName = file.getOriginalFilename();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

            result = true;

        } catch (Exception e){
            log.error(e.getMessage());
        }

        return result;
    }

    public String getUrl(String objectName, String bucketName) {
        return this.getUrl(objectName, bucketName, 30, TimeUnit.MINUTES);
    }

    /**
     *
     * @param duration the duration the link should last
     * @param timeUnit the unit of the duration in TimeUnit
     * @param fileName   file name to retrieve with extension
     * @param bucketName bucket name of where the file should be
     * @return the url or Null if not found
     */
    @Override
    public String getUrl(String fileName, String bucketName, int duration, TimeUnit timeUnit) {
        String url = null;

        try{
            url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(fileName)
                            .expiry(duration, timeUnit)
                            .build());

        }catch (Exception e){
            log.error(e.getMessage());
        }


        return url;
    }

    /**
     * @param fileName   file looking to delete
     * @param bucketName the bucket name of the file
     * @return true if the file has been deleted successfully
     */
    @Override
    public boolean deleteFile(String fileName, String bucketName) {
        boolean result = false;

        try{
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build());

            result = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName).build());
        }catch(Exception e){
            log.error(e.getMessage());
        }

        return result;
    }
}