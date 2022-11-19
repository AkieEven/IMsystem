package com.example.example.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.example.bean.Friendship;
import com.example.example.bean.LastMessage;
import com.example.example.bean.Message;
import com.example.example.bean.MessagePersistant;
import com.example.example.bean.User;
import com.example.example.bean.UserGroup;
import com.example.example.database.dao.FriendshipDao;
import com.example.example.database.dao.LastMessageDao;
import com.example.example.database.dao.MessagePersistantDao;
import com.example.example.database.dao.UserDao;
import com.example.example.database.dao.UserGroupDao;

@Database(entities = {User.class, UserGroup.class, Friendship.class, LastMessage.class, MessagePersistant.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = AppDatabase.class.getSimpleName();
    private static volatile AppDatabase instance;

    public static AppDatabase getInstance() {
        return instance;
    }

    public static void buildDatabase(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if(instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"IM_database").build();
                }
            }
        }
    }


    public abstract UserDao UserDao();
    public abstract FriendshipDao FriendshipDao();
    public abstract MessagePersistantDao MessagePersistantDao();
    public abstract UserGroupDao UserGroupDao();
    public abstract LastMessageDao LastMessageDao();
}
