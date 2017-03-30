package com.xiaoka.xksupportutils.upload;

/**
 * Created by shenmengchao on 16/11/16.
 */

public class UploadClient {


    public static class Builder{

        private boolean isUseCamera = true;
        private boolean isUseGallery = true;
        private boolean isMulitPhoto = false;
        private boolean isCompress = true;
        private boolean isUpload = true;

        public Builder isUseCamera(boolean isUseCamera){
            this.isUseCamera = isUseCamera;
            return this;
        }

        public Builder isUseGallery(boolean isUseGallery){
            this.isUseGallery = isUseGallery;
            return this;
        }

        public Builder isMulitPhoto(boolean isMulitPhoto){
            this.isMulitPhoto = isMulitPhoto;
            return this;
        }

        public Builder isCompress(boolean isCompress){
            this.isCompress = isCompress;
            return this;
        }

        public Builder isUpload(boolean isUpload){
            this.isUpload = isUpload;
            return this;
        }

    }
}
