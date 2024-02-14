package org.dms.document.commun;

import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.*;
import org.apache.commons.io.IOUtils;
import org.dms.document.config.LoadFile;
import org.dms.document.dto.PhotoDto;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Component;

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

    public byte[] ResizeImg(BufferedImage bufferedImage, PhotoDto photoDto) throws IOException {
        Image resizedImage = bufferedImage.getScaledInstance(photoDto.getWidth(), photoDto.getHeight(), photoDto.getScale());
        BufferedImage resizedBufferedImage = new BufferedImage(resizedImage.getWidth(null),
                resizedImage.getHeight(null), photoDto.getRgb());
        Graphics g = resizedBufferedImage.createGraphics();
        g.drawImage(resizedImage, 0, 0, null);
        g.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedBufferedImage, "jpg", baos);
        return baos.toByteArray();
    }
}
