package com.momentz.contentsteam.utils.storage;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.TimeUnit;


public interface BucketStorage {

    /**
     * @return a list of all active bucket names
     */
    List<String> getAllBucketNames();

    /**
     * @param bucketName The name of bucket to add
     * @return true if the bucket was added successfully or false otherwise
     */
    boolean addBucket(String bucketName);

    /**
     *
     * @param bucketName name of bucket to delete
     * @return true if the bucket has been deleted successfully
     */
    boolean deleteBucket(String bucketName);

    /**
     *
     * @param file file to upload
     * @param bucketName the bucket name for which to upload it to
     * @return true if the file has been added successfully
     */
    boolean upload(MultipartFile file, String bucketName);

    /**
     *
     * @param duration the duration the link should last
     * @param timeUnit the unit of the duration in TimeUnit
     * @param fileName file name to retrieve with extension
     * @param bucketName bucket name of where the file should be
     * @return the url or Null if not found
     */
    String getUrl(String fileName, String bucketName, int duration, TimeUnit timeUnit);

    /**
     *
     * @param fileName file looking to delete
     * @param bucketName the bucket name of the file
     * @return true if the file has been deleted successfully
     */
    boolean deleteFile(String fileName, String bucketName);
}
