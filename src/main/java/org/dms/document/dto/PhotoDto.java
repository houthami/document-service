package org.dms.document.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.awt.image.BufferedImage;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotoDto {
    private String format;
    private int width;
    private int height;
    private int scale;
    private int rgb;

    public String getFormat() {
        return format == null ? "jpg":format;
    }

    public int getScale() {
        return this.scale == 0 ? Image.SCALE_DEFAULT : this.scale;
    }

    public int getRgb() {
        return rgb == 0 ? BufferedImage.TYPE_INT_RGB : rgb;
    }
}
