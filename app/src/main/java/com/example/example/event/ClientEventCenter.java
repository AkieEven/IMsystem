package com.example.example.event;

import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;

public class ClientEventCenter {
    private static final String TAG = "EVENT_CENTER";

    private static final HashMap<String, LinkedList<ClientEventListener>> mListenerMap = new HashMap<>();
    private static final Object mListenerLock = new Object();

    //注册监听器
    public static void registerEventListener(ClientEventListener listener, String topic) {
        registerEventListener(listener, new String[]{topic});
    }

    public static void registerEventListener(ClientEventListener listener, String[] topics) {
        if (null == listener || null == topics) {
            Log.e(TAG, "无效的注册监听事件！");
            return;
        }

        synchronized (mListenerLock) {
            for (String topic : topics) {
                if (TextUtils.isEmpty(topic)) continue;

                LinkedList<ClientEventListener> list = mListenerMap.get(topic);
                if (list == null) {
                    LinkedList<ClientEventListener> listeners = new LinkedList<>();
                    listeners.add(listener);
                    mListenerMap.put(topic, listeners);
                } else {
                    if (list.contains(listener)) continue;
                    list.add(listener);
                }
            }
        }
    }

    //注销监听器
    public static void unregisterEventListener(ClientEventListener listener, String topic) {
        unregisterEventListener(listener, new String[]{topic});
    }

    public static void unregisterEventListener(ClientEventListener listener, String[] topics) {
        if (null == listener || null == topics) {
            Log.e(TAG, "无效的注销监听事件！");
            return;
        }

        synchronized (mListenerLock) {
            for (String topic : topics) {
                if (TextUtils.isEmpty(topic)) continue;
                LinkedList<ClientEventListener> listeners = mListenerMap.get(topic);

                if (listeners == null) continue;
                else if ((listeners.size() == 1) &&(listeners.contains(listener))) {
                    mListenerMap.remove(topic);
                } else {
                    listeners.remove(listener);
                }
            }
        }
    }

    //分发事件
    public static void dispatchEvent(String topic,int msgCode,int resultCode,Object obj) {
        if(!TextUtils.isEmpty(topic)) {
            //没有监听器
            if(mListenerMap.size() == 0) return;
            if(TextUtils.isEmpty(topic)) return;

            LinkedList<ClientEventListener> listeners = null;

            synchronized (mListenerLock) {
                Log.d(TAG,"派发事件：topic:"+topic);
                listeners = mListenerMap.get(topic);
                if (listeners != null) {
                    for (ClientEventListener listener : listeners) {
                        if (listener != null) listener.onEvent(topic,msgCode,resultCode,obj);
                    }
                }
            }
        }
    }

}
