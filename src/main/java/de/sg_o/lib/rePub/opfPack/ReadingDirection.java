package de.sg_o.lib.rePub.opfPack;

import java.util.HashMap;
import java.util.Map;

public enum ReadingDirection {
    LTR("ltr"),
    RTL("rtl"),
    DEFAULT("");

    private String code;

    private static final Map<String, ReadingDirection> codes = new HashMap<>();

    static {
        for (ReadingDirection r : values()) {
            codes.put(r.code, r);
        }
    }

    ReadingDirection(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ReadingDirection fromCode(String code) {
        if (code == null) return DEFAULT;
        ReadingDirection ret = codes.get(code);
        if (ret == null) return DEFAULT;
        return ret;
    }
}
