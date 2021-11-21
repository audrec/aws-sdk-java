/*
 * Copyright 2011-2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.amazonaws.services.s3.transfer;

import com.amazonaws.annotation.SdkInternalApi;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.internal.S3ProgressListener;

import java.util.concurrent.ExecutorService;

/**
 * Internal class to wrap all params needed by {@link TransferManager}. Used by {@link
 * TransferManagerBuilder}.
 */
@SdkInternalApi
class TransferManagerParams {

    private AmazonS3 s3Client;
    private ExecutorService executorService;
    private Boolean shutDownThreadPools;
    private TransferManagerConfiguration configuration;
    private ProgressListener putObjectProgressListener;
    private S3ProgressListener uploadProgressListener;

    public AmazonS3 getS3Client() {
        return s3Client;
    }

    public TransferManagerParams withS3Client(AmazonS3 s3Client) {
        this.s3Client = s3Client;
        return this;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public TransferManagerParams withExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
        return this;
    }

    public Boolean getShutDownThreadPools() {
        return shutDownThreadPools;
    }

    public TransferManagerParams withShutDownThreadPools(Boolean shutDownThreadPools) {
        this.shutDownThreadPools = shutDownThreadPools;
        return this;
    }

    /**
     * Returns the progress listener attached to the put object request.
     *
     * @return The progress listener.
     */
    public ProgressListener getPutObjectProgressListener() {
        return putObjectProgressListener;
    }


    /**
     * Sets the progress listener attached to the put object request in the TransferManagerParams.
     *
     * @param progressListener The progress listener to use
     * @return This object for method chaining.
     */
    public TransferManagerParams withPutObjectProgressListener(ProgressListener progressListener) {
        this.putObjectProgressListener = progressListener;
        return this;
    }

    /**
     * Returns the progress listener attached to the Upload process.
     *
     * @return The progress listener.
     */
    public S3ProgressListener getUploadProgressListener() {
        return uploadProgressListener;
    }

    /**
     * Sets the progress listener attached to the upload process in the TransferManagerParams.
     *
     * @param progressListener The progress listener to use
     * @return This object for method chaining.
     */
    public TransferManagerParams withUploadProgressListener(S3ProgressListener progressListener) {
        this.uploadProgressListener = progressListener;
        return this;
    }

    public TransferManagerConfiguration getConfiguration() {
        return configuration;
    }

    public TransferManagerParams withTransferManagerConfiguration(
            TransferManagerConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

}
