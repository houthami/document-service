package org.dms.document.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.dms.document.commun.FileMethode;
import org.dms.document.config.LoadFile;
import org.dms.document.dto.PhotoDto;
import org.dms.document.model.Photo;
import org.dms.document.repository.PhotoRepository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final GridFsTemplate gridFsTemplate;

    private final PhotoRepository photoRepo;
    private final FileMethode fileMethode;
    private final GridFsOperations operations;

    public String addPhoto(String title, MultipartFile file) throws IOException {
        Photo photo = Photo.builder().title(title).build();
        photo.setImage(
                new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        photo = photoRepo.insert(photo); return photo.getId();
    }

    public Photo getPhoto(String id) {
        var photoOptional = photoRepo.findById(id);
        assert photoOptional.isPresent();
        return photoOptional.get();
    }

    public LoadFile resize(String id, PhotoDto photoDto) throws IOException {
        GridFSFile gridFSFile = gridFsTemplate.findOne( new Query(Criteria.where("_id").is(id)) );
        LoadFile photo = fileMethode.formatFile(gridFSFile, operations);
        ByteArrayInputStream bais = new ByteArrayInputStream(photo.getFile());
        BufferedImage bufferedImage = ImageIO.read(bais);
        photo.setFile(fileMethode.ResizeImg(bufferedImage, photoDto));
        return photo;
    }

    /*public Photo resize(String id) {
        var photoOptional = photoRepo.findById(id);
    }*/
}
