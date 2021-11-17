package com.lymytz.android.component;

import java.io.Serializable;

public class ResultatAction<T extends Serializable> implements Serializable {

    private boolean result = false;
    private int codeInfo;
    private Long idEntity;
    private String sourceEntity;
    private String message = "Pas encore synchronisé";
    private String module;
    private String fonctionalite;
    private Object data;
    private T entity;

    public ResultatAction() {

    }

    public ResultatAction(boolean result, Object data) {
        this();
        this.result = result;
        this.data = data;
    }

    public ResultatAction(boolean result, Object data, String message) {
        this(result, data);
        this.message = message;
    }

    public ResultatAction(boolean result, Long idEntity, String sourceEntity, String message) {
        this.result = result;
        this.idEntity = idEntity;
        this.sourceEntity = sourceEntity;
        this.message = message;
    }

    public ResultatAction(boolean result, int codeInfo, String message) {
        this();
        this.result = result;
        this.codeInfo = codeInfo;
        this.message = message;
    }

    public ResultatAction(boolean result, int codeInfo, String message, String module, String fonctionalite) {
        this();
        this.result = result;
        this.codeInfo = codeInfo;
        this.message = message;
        this.module = module;
        this.fonctionalite = fonctionalite;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getCodeInfo() {
        return codeInfo;
    }

    public void setCodeInfo(int codeInfo) {
        this.codeInfo = codeInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFonctionalite() {
        return fonctionalite;
    }

    public void setFonctionalite(String fonctionalite) {
        this.fonctionalite = fonctionalite;
    }

    public Long getIdEntity() {
        return idEntity != null ? idEntity : 0;
    }

    public void setIdEntity(Long idEntity) {
        this.idEntity = idEntity;
    }

    public String getSourceEntity() {
        return sourceEntity;
    }

    public void setSourceEntity(String sourceEntity) {
        this.sourceEntity = sourceEntity;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
