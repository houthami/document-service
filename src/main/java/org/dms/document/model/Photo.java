package org.dms.document.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "photos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Photo {
    @Id
    private String id;

    private String title;

    private Binary image;
}
