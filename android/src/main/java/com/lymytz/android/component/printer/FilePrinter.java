package com.lymytz.android.component.printer;

import com.lymytz.android.component.printer.part.PartPrinter;

import java.io.File;
import java.util.List;

public class FilePrinter {
    File file;
    List<PartPrinter> parts;
    ContentType type;

    public FilePrinter(File file) {
        this.file = file;
        this.type = ContentType.FILE;
    }

    public FilePrinter(List<PartPrinter> parts) {
        this.parts = parts;
        this.type = ContentType.PART;
    }

    public ContentType getType() {
        return type;
    }

    public File getFile() {
        return file;
    }

    public List<PartPrinter> getParts() {
        return parts;
    }

    public enum ContentType {
        FILE, PART;
    }
}
