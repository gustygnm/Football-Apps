package com.baleit.footballmatchschedule.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteTeam.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(FavoriteMatch.TABLE_FAVORITE, true,
                FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteMatch.homeTeamId to TEXT,
                FavoriteMatch.awayTeamId to TEXT,
                FavoriteMatch.scoreHome to TEXT,
                FavoriteMatch.scoreAway to TEXT,
                FavoriteMatch.teamHome to TEXT,
                FavoriteMatch.teamAway to TEXT,
                FavoriteMatch.dateMatch to TEXT,
                FavoriteMatch.eventId to TEXT + UNIQUE)

        db.createTable(FavoriteTeams.TABLE_FAVORITE, true,
                FavoriteTeams.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteTeams.TEAM_ID to TEXT + UNIQUE,
                FavoriteTeams.TEAM_NAME to TEXT,
                FavoriteTeams.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(FavoriteMatch.TABLE_FAVORITE, true)
        db.dropTable(FavoriteTeams.TABLE_FAVORITE, true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)