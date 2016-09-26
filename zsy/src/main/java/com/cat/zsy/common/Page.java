package com.cat.zsy.common;

import lombok.Getter;
import lombok.ToString;
import org.apache.ibatis.session.RowBounds;

@Getter
@ToString
public class Page {

    private int offset;
    private int limit;

    private Page(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public static Page of(int offset, int limit) {
        return new Page(offset, limit);
    }

    public static RowBounds forDao(int offset, int limit) {
        return new RowBounds(offset, limit);
    }

    public static RowBounds forService(int pageNo, int pageSize) {
        return new RowBounds((pageNo - 1) * pageSize, pageSize);
    }

    public RowBounds row() {
        return new RowBounds(this.offset, this.limit);
    }
}
