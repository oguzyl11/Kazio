package com.kazio.app.data.local.room;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PersonalRecordDao_Impl implements PersonalRecordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PersonalRecordEntity> __insertionAdapterOfPersonalRecordEntity;

  public PersonalRecordDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPersonalRecordEntity = new EntityInsertionAdapter<PersonalRecordEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `personal_records` (`type`,`value`,`achievedAt`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PersonalRecordEntity entity) {
        statement.bindString(1, entity.getType());
        statement.bindDouble(2, entity.getValue());
        statement.bindLong(3, entity.getAchievedAt());
      }
    };
  }

  @Override
  public Object insertRecord(final PersonalRecordEntity record,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPersonalRecordEntity.insert(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PersonalRecordEntity>> getAllRecords() {
    final String _sql = "SELECT * FROM personal_records";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"personal_records"}, new Callable<List<PersonalRecordEntity>>() {
      @Override
      @NonNull
      public List<PersonalRecordEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
          final int _cursorIndexOfAchievedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "achievedAt");
          final List<PersonalRecordEntity> _result = new ArrayList<PersonalRecordEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PersonalRecordEntity _item;
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final double _tmpValue;
            _tmpValue = _cursor.getDouble(_cursorIndexOfValue);
            final long _tmpAchievedAt;
            _tmpAchievedAt = _cursor.getLong(_cursorIndexOfAchievedAt);
            _item = new PersonalRecordEntity(_tmpType,_tmpValue,_tmpAchievedAt);
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

  @Override
  public Object getRecordByType(final String type,
      final Continuation<? super PersonalRecordEntity> $completion) {
    final String _sql = "SELECT * FROM personal_records WHERE type = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, type);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PersonalRecordEntity>() {
      @Override
      @Nullable
      public PersonalRecordEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
          final int _cursorIndexOfAchievedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "achievedAt");
          final PersonalRecordEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final double _tmpValue;
            _tmpValue = _cursor.getDouble(_cursorIndexOfValue);
            final long _tmpAchievedAt;
            _tmpAchievedAt = _cursor.getLong(_cursorIndexOfAchievedAt);
            _result = new PersonalRecordEntity(_tmpType,_tmpValue,_tmpAchievedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
