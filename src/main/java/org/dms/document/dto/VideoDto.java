package org.dms.document.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoDto {
    private String title;
    private InputStream stream;
}
