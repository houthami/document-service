package org.dms.document.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.dms.document.commun.FileMethode;
import org.dms.document.config.LoadFile;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {

    private final GridFsTemplate gridFsTemplate;
    private final FileMethode fileMethode;
    private final GridFsOperations operations;

    public String addFile(MultipartFile upload) throws IOException {
        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize", upload.getSize());
        Object fileID = gridFsTemplate.store(upload.getInputStream(), upload.getOriginalFilename(), upload.getContentType(), metadata);
        return fileID.toString();
    }


    public LoadFile downloadFile(String id) throws IOException {
        GridFSFile gridFSFile = gridFsTemplate.findOne( new Query(Criteria.where("_id").is(id)) );
        return fileMethode.formatFile(gridFSFile, operations);
    }


}
