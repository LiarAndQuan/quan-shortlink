package online.aquan.shortlink.project.common.constant;

import lombok.Getter;

@Getter
public enum VailDateTypeEnum {

    PERMANENT(0),
    COMMON(1);


    private final int type;

    private VailDateTypeEnum(int type) {
        this.type = type;
    }

}
