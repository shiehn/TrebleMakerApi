package com.treblemaker.model;

public class UploadListener {

    private Boolean uploadComplete = false;

    public Boolean isUploadComplete() {
        return uploadComplete;
    }

    public void setUploadComplete() {
        this.uploadComplete = true;
    }
}
