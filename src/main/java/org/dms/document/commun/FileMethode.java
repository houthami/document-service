package org.dms.document.commun;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.dms.document.config.LoadFile;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FileMethode {

    public LoadFile formatFile(GridFSFile gridFSFile, GridFsOperations operations) throws IOException {
        assert gridFSFile != null && gridFSFile.getMetadata() != null;
        return LoadFile.builder()
                .file(IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()))
                .fileSize(gridFSFile.getMetadata().get("fileSize").toString())
                .fileType(gridFSFile.getMetadata().get("_contentType").toString())
                .filename(gridFSFile.getFilename())
                .build();
    }
}
