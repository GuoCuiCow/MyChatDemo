package com.cow.test.mychatdemo.data.bean;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import com.hyphenate.chat.EMMessage;

/**
 * Created by cuiguo on 2017/3/3.
 * 为了解决一对多问题
 */

public class MessageBean {
    // @formatter:off
    public abstract static class TypeFrom {}
    public abstract static class TypeTo {}
    // @formatter:on

    @SerializedName("message") public EMMessage message;

    public Class typeClass;


    public MessageBean(@NonNull EMMessage message) {
        this.message = message;
        this.typeClass = getTypeClass(message.direct());
    }


    @Nullable
    public static Class getTypeClass(EMMessage.Direct type) {
        switch (type) {
            case RECEIVE:
                return TypeFrom.class;
            case SEND:
                return TypeTo.class;
//            default:
//                return TypeTo.class;
        }
        return null;
    }
}
