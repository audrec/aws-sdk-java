package s3;

import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.transfer.PersistableTransfer;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.internal.S3ProgressListener;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransferManagerBuilderTest {

    @Test
    public void testPutObjectProgressListenerBuilder(){
        ProgressListener listener = new ProgressListener.NoOpProgressListener();
        TransferManager manager = TransferManagerBuilder.standard().withPutObjectProgressListener(listener).build();
        assertEquals(listener, manager.getPutObjectRequestProgressListener());
    }

    @Test
    public void testUploadProgressListenerBuilder(){
        S3ProgressListener listener = new S3ProgressListener() {
            @Override
            public void onPersistableTransfer(PersistableTransfer persistableTransfer) {

            }

            @Override
            public void progressChanged(ProgressEvent progressEvent) {

            }
        };
        TransferManager manager = TransferManagerBuilder.standard().withUploadProgressListener(listener).build();
        assertEquals(listener, manager.getUploadProgressListener());
    }
}
