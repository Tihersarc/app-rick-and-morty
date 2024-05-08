// DatabaseHelper.kt
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    object CharacterEntry {
        const val TABLE_NAME = "bookmarks"
        const val COLUMN_NAME_ID = "_id"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_IMAGE = "image"
        const val COLUMN_NAME_GENDER = "gender"
        const val COLUMN_NAME_ORIGIN = "origin"
        const val COLUMN_NAME_STATUS = "status"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun insertBookmark(characterData: com.example.app.Character): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(CharacterEntry.COLUMN_NAME_NAME, characterData.name)
            put(CharacterEntry.COLUMN_NAME_IMAGE, characterData.image)
            put(CharacterEntry.COLUMN_NAME_GENDER, characterData.gender)
            put(CharacterEntry.COLUMN_NAME_ORIGIN, characterData.origin.name)
            put(CharacterEntry.COLUMN_NAME_STATUS, characterData.status)
        }
        return db.insert(CharacterEntry.TABLE_NAME, null, values)
    }


    fun deleteBookmark(name: String): Int {
        val db = writableDatabase
        val selection = "${CharacterEntry.COLUMN_NAME_NAME} LIKE ?"
        val selectionArgs = arrayOf(name)
        return db.delete(CharacterEntry.TABLE_NAME, selection, selectionArgs)
    }

    fun getAllBookmarks(): Cursor {
        val db = readableDatabase
        val projection = arrayOf(
            CharacterEntry.COLUMN_NAME_ID,
            CharacterEntry.COLUMN_NAME_NAME,
            CharacterEntry.COLUMN_NAME_IMAGE,
            CharacterEntry.COLUMN_NAME_GENDER,
            CharacterEntry.COLUMN_NAME_ORIGIN,
            CharacterEntry.COLUMN_NAME_STATUS
        )
        return db.query(CharacterEntry.TABLE_NAME, projection, null, null, null, null, null)
    }

    fun isCharacterBookmarked(name: String): Boolean {
        val db = readableDatabase
        val selection = "${CharacterEntry.COLUMN_NAME_NAME} = ?"
        val selectionArgs = arrayOf(name)
        val cursor = db.query(
            CharacterEntry.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val isBookmarked = cursor.count > 0
        cursor.close()
        return isBookmarked
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "CharacterBookmarks.db"
        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS ${CharacterEntry.TABLE_NAME} (" +
                    "${CharacterEntry.COLUMN_NAME_ID} INTEGER PRIMARY KEY," +
                    "${CharacterEntry.COLUMN_NAME_NAME} TEXT," +
                    "${CharacterEntry.COLUMN_NAME_IMAGE} TEXT," +
                    "${CharacterEntry.COLUMN_NAME_GENDER} TEXT," +
                    "${CharacterEntry.COLUMN_NAME_ORIGIN} TEXT," +
                    "${CharacterEntry.COLUMN_NAME_STATUS} TEXT)"
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${CharacterEntry.TABLE_NAME}"
    }
}
