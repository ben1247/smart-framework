package org.smart4j.framework.bean;

import java.io.InputStream;

/**
 * 封装上传文件参数
 * Created by yuezhang on 17/10/29.
 */
public class FileParam {

    /**
     * 表示文件表单的字段名
     */
    private String fieldName;

    /**
     * 表示上传文件的文件名
     */
    private String fileName;

    /**
     * 表示上传文件的文件大小
     */
    private long fileSize;

    /**
     * 表示上传文件的Content-Type，可判断文件类型
     */
    private String contentType;

    /**
     * 表示上传文件的字节输入流
     */
    private InputStream inputStream;

    public FileParam(String fieldName , String fileName , long fileSize ,
        String contentType , InputStream inputStream){
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.inputStream = inputStream;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
