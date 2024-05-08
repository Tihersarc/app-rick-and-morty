// DatabaseHelper.kt
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    object CharacterInfo {
        object CharacterEntry {
            const val TABLE_NAME = "bookmarks"
            const val COLUMN_NAME_ID = "_id"
            const val COLUMN_NAME_NAME = "name"
            const val COLUMN_NAME_IMAGE = "image"
            const val COLUMN_NAME_GENDER = "gender"
            const val COLUMN_NAME_ORIGIN = "origin"
            const val COLUMN_NAME_STATUS = "status"
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun insertBookmark(name: String, image: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(CharacterInfo.CharacterEntry.COLUMN_NAME_NAME, name)
            put(CharacterInfo.CharacterEntry.COLUMN_NAME_IMAGE, image)
        }
        return db.insert(CharacterInfo.CharacterEntry.TABLE_NAME, null, values)
    }

    fun deleteBookmark(name: String): Int {
        val db = writableDatabase
        val selection = "${CharacterInfo.CharacterEntry.COLUMN_NAME_NAME} LIKE ?"
        val selectionArgs = arrayOf(name)
        return db.delete(CharacterInfo.CharacterEntry.TABLE_NAME, selection, selectionArgs)
    }

    fun getAllBookmarks(): Cursor {
        val db = readableDatabase
        val projection = arrayOf(CharacterInfo.CharacterEntry.COLUMN_NAME_ID, CharacterInfo.CharacterEntry.COLUMN_NAME_NAME, CharacterInfo.CharacterEntry.COLUMN_NAME_IMAGE)
        return db.query(CharacterInfo.CharacterEntry.TABLE_NAME, projection, null, null, null, null, null)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "CharacterBookmarks.db"
        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${CharacterInfo.CharacterEntry.TABLE_NAME} (" +
                    "${CharacterInfo.CharacterEntry.COLUMN_NAME_ID} INTEGER PRIMARY KEY," +
                    "${CharacterInfo.CharacterEntry.COLUMN_NAME_NAME} TEXT," +
                    "${CharacterInfo.CharacterEntry.COLUMN_NAME_IMAGE} TEXT)"
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${CharacterInfo.CharacterEntry.TABLE_NAME}"
    }
}
