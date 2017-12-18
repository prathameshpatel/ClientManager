package io.github.prathameshpatel.clientmanager.db;

import io.github.prathameshpatel.clientmanager.dao.ClientDao;
import io.github.prathameshpatel.clientmanager.entity.Client;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Prathamesh Patel on 12/16/2017.
 */

@Database(entities = {Client.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;
    public abstract ClientDao clientDao();

    public static AppDatabase getAppDatabase(Context context) {
        if(appDatabase == null) {
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "database-file.db")
                    .fallbackToDestructiveMigration().build();
        }
        return appDatabase;
    }

    public static void destroyInstance() {
        appDatabase = null;
    }

}