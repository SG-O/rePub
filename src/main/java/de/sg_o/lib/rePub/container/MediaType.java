package de.sg_o.lib.rePub.container;

import java.util.HashMap;
import java.util.Map;

public enum MediaType {
    EPUB("application/epub+zip"),
    GIF_A("image/gif"),
    GIF_B("text/gif"),
    JPEG_A("image/jpeg"),
    JPEG_B("text/jpeg"),
    PNG_A("image/png"),
    PNG_B("text/png"),
    SVG_A("image/svg+xml"),
    SVG_B("text/svg+xml"),
    MP3("audio/mpeg"),
    MP4("audio/mp4"),
    CSS("text/css"),
    TTF_A("font/ttf"),
    TTF_B("application/font-sfnt"),
    OTF_A("font/otf"),
    OTF_B("application/font-sfnt"),
    OTF_C("application/vnd.ms-opentype"),
    WOFF_A("font/woff"),
    WOFF_B("application/font-woff"),
    WOFF2("font/woff2"),
    XHTML("application/xhtml+xml"),
    JS_A("application/javascript"),
    JS_B("text/javascript"),
    NCX("application/x-dtbncx+xml"),
    OPF("application/oebps-package+xml"),
    SMIL3("application/smil+xml"),
    PLS("application/pls+xml"),
    XML_A("application/xml"),
    XML_B("text/xml"),
    CONTAINER("application/container"),
    MIMETYPE("application/mimetype"),
    UNKNOWN("");

    private String mime;

    private static final Map<String, MediaType> mimes = new HashMap<>();

    static {
        for (MediaType r : values()) {
            mimes.put(r.mime.toLowerCase(), r);
        }
    }

    MediaType(String mime) {
        this.mime = mime;
    }

    public String getMime() {
        return mime;
    }

    public static MediaType fromMime(String mime) {
        if (mime == null) return UNKNOWN;
        mime = mime.trim();
        MediaType ret = mimes.get(mime.toLowerCase());
        if (ret == null) return UNKNOWN;
        return ret;
    }
}
