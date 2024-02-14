package org.dms.document.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.dms.document.dto.VideoDto;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final GridFsTemplate gridFsTemplate;

    private final GridFsOperations operations;

    public String addVideo(String title, MultipartFile file) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put("type", "video");
        metaData.put("title", title);
        ObjectId id = gridFsTemplate.store(
                file.getInputStream(), file.getName(), file.getContentType(), metaData);
        return id.toString();
    }

    public VideoDto getVideo(String id) throws IllegalStateException, IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        VideoDto video = new VideoDto();
        assert file != null && file.getMetadata() != null;
        video.setTitle(file.getMetadata().get("title").toString());
        video.setStream(operations.getResource(file).getInputStream());
        return video;
    }
}
