package s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.ObjectMetadataProvider;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.internal.MultipleFileUploadImpl;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TransferManagerTest {

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
