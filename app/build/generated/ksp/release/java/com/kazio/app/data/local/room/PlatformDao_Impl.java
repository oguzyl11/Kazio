package com.kazio.app.data.local.room;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PlatformDao_Impl implements PlatformDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PlatformEntity> __insertionAdapterOfPlatformEntity;

  public PlatformDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlatformEntity = new EntityInsertionAdapter<PlatformEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `platforms` (`id`,`name`,`colorTag`,`isCustom`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlatformEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getColorTag());
        final int _tmp = entity.isCustom() ? 1 : 0;
        statement.bindLong(4, _tmp);
      }
    };
  }

  @Override
  public Object insertPlatform(final PlatformEntity platform,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPlatformEntity.insertAndReturnId(platform);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PlatformEntity>> getAllPlatforms() {
    final String _sql = "SELECT * FROM platforms";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"platforms"}, new Callable<List<PlatformEntity>>() {
      @Override
      @NonNull
      public List<PlatformEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfColorTag = CursorUtil.getColumnIndexOrThrow(_cursor, "colorTag");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "isCustom");
          final List<PlatformEntity> _result = new ArrayList<PlatformEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlatformEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpColorTag;
            _tmpColorTag = _cursor.getString(_cursorIndexOfColorTag);
            final boolean _tmpIsCustom;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
            _tmpIsCustom = _tmp != 0;
            _item = new PlatformEntity(_tmpId,_tmpName,_tmpColorTag,_tmpIsCustom);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
