package s3;

import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.*;
import com.amazonaws.services.s3.transfer.internal.MultipleFileUploadImpl;
import com.amazonaws.services.s3.transfer.internal.UploadImpl;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TransferManagerTest {

    /**
     * Unit test to check if the null input for the resume upload API will correctly throw an exception.
     */
    @Test
    public void testNullInputResumeUpload() {
        // Mock a client that call service from aws s3
        AmazonS3Client s3Client = Mockito.mock(AmazonS3Client.class);
        // Mock a file
        File myFile = Mockito.mock(File.class);
        // Mock a progress listener that we set as an input to the new API
        PersistableUpload persistableUpload = null;
        ProgressListener progressListenerForTest = Mockito.mock(ProgressListener.class);
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(s3Client).build();

        // Test if exception is correctly thrown due to null input
        boolean thrown = false;
        try {
            transferManager.resumeUpload(persistableUpload, progressListenerForTest);
        } catch (IllegalArgumentException e){
            thrown = true;
        }
        assertTrue(thrown);
    }

    /**
     * Unit test to validate the behavior of the resume upload API with the normal inputs.
     * This test is to check if the progress listener passed from the resume upload API will
     * be chained in the progress listener list in the doUpload API.
     *
     */
    @Test
    public void testListenerInResumeUpload() {
        // Mock a client that call service from aws s3
        AmazonS3Client s3Client = Mockito.mock(AmazonS3Client.class);
        // Mock a file
        File myFile = Mockito.mock(File.class);
        PersistableUpload persistableUpload = new PersistableUpload("bucketName", "key", "myFile",
                "multipartUploadId", 10, 1000);
        // Mock a progress listener that we set as an input to the new API
        ProgressListener progressListenerForTest = Mockito.mock(ProgressListener.class);
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(s3Client).build();
        // Actually run the new resume upload API
        UploadImpl actualResult = (UploadImpl) transferManager.resumeUpload(persistableUpload, progressListenerForTest);

        // Make assertion for the tests
        assertNotNull(transferManager.getPutObjectRequestProgressListener());
        assertTrue(actualResult.getProgressListenerChain().getListeners().contains(progressListenerForTest));
        assertNotNull(actualResult.getProgressListenerChain());
    }

    @Test
    public void testUploadFileList(){
        AmazonS3Client client = Mockito.mock(AmazonS3Client.class);
        File file = Mockito.mock(File.class);
        PutObjectRequest request = Mockito.mock(PutObjectRequest.class);
        Mockito.when(file.exists()).thenReturn(true);
        Mockito.when(file.isDirectory()).thenReturn(true);
        Mockito.when(file.isFile()).thenReturn(true);
        Mockito.when(file.getAbsolutePath()).thenReturn("./test");
        ObjectMetadataProvider metadataProvider = Mockito.mock(ObjectMetadataProvider.class);
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(client).build();
        List<File> fileList = new ArrayList<>();
        fileList.add(new File("./test/file1"));
        MultipleFileUploadImpl upload = (MultipleFileUploadImpl) transferManager.uploadFileList("bucket", "key", file, fileList, metadataProvider);
        assertEquals("bucket", upload.getBucketName());
        assertEquals("key/", upload.getKeyPrefix());
        assertNotNull(upload.getProgressListenerChain());
    }
}
