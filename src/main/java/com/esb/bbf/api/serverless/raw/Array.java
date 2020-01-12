package com.esb.bbf.api.serverless.raw;

import java.util.ArrayList;

/**
 * 用于重写脚本语言的某些类型。这里是重写js的array
 * @param <E>
 */
public class Array<E>  extends ArrayList {

    public boolean push(E e) {
       return this.add(e);
    }
}
