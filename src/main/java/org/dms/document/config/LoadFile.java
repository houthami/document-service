package org.dms.document.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoadFile {
    private String filename;
    private String fileType;
    private String fileSize;
    private byte[] file;
}
