package com.company.salesanalysis.domain.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.Objects;

@MappedSuperclass
public class Line {

    private String fileId;
    @Transient private Class<?> type;

    public Line(String idFile, Class<? extends Line> type){
        this.fileId = idFile;
        this.type = type;
    }

    public String getFileId() { return fileId; }
    public Class<?> getType() { return type; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(fileId, line.fileId) &&
                Objects.equals(type, line.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileId, type);
    }

    @Override
    public String toString() {
        return "Line{" +
                "fileId='" + fileId + '\'' +
                ", type=" + type +
                '}';
    }
}
