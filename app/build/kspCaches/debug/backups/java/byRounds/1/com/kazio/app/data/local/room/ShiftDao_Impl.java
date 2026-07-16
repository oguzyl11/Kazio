package com.kazio.app.data.local.room;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
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
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ShiftDao_Impl implements ShiftDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ShiftEntity> __insertionAdapterOfShiftEntity;

  private final SharedSQLiteStatement __preparedStmtOfUpdateShiftEnd;

  private final SharedSQLiteStatement __preparedStmtOfUpdateShiftPauseState;

  public ShiftDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfShiftEntity = new EntityInsertionAdapter<ShiftEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `shifts` (`id`,`vehicleId`,`startAt`,`endAt`,`note`,`totalPausedDuration`,`isPaused`,`lastPausedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ShiftEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getVehicleId());
        statement.bindLong(3, entity.getStartAt());
        if (entity.getEndAt() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getEndAt());
        }
        if (entity.getNote() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getNote());
        }
        statement.bindLong(6, entity.getTotalPausedDuration());
        final int _tmp = entity.isPaused() ? 1 : 0;
        statement.bindLong(7, _tmp);
        if (entity.getLastPausedAt() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getLastPausedAt());
        }
      }
    };
    this.__preparedStmtOfUpdateShiftEnd = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE shifts SET endAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateShiftPauseState = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE shifts SET isPaused = ?, lastPausedAt = ?, totalPausedDuration = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertShift(final ShiftEntity shift, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfShiftEntity.insertAndReturnId(shift);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateShiftEnd(final long shiftId, final long endAt,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateShiftEnd.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, endAt);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, shiftId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateShiftEnd.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateShiftPauseState(final long shiftId, final boolean isPaused,
      final Long lastPausedAt, final long totalPausedDuration,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateShiftPauseState.acquire();
        int _argIndex = 1;
        final int _tmp = isPaused ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        if (lastPausedAt == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, lastPausedAt);
        }
        _argIndex = 3;
        _stmt.bindLong(_argIndex, totalPausedDuration);
        _argIndex = 4;
        _stmt.bindLong(_argIndex, shiftId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateShiftPauseState.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<ShiftEntity> getActiveShift() {
    final String _sql = "SELECT * FROM shifts WHERE endAt IS NULL ORDER BY startAt DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"shifts"}, new Callable<ShiftEntity>() {
      @Override
      @Nullable
      public ShiftEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfVehicleId = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicleId");
          final int _cursorIndexOfStartAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startAt");
          final int _cursorIndexOfEndAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endAt");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfTotalPausedDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPausedDuration");
          final int _cursorIndexOfIsPaused = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaused");
          final int _cursorIndexOfLastPausedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPausedAt");
          final ShiftEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpVehicleId;
            _tmpVehicleId = _cursor.getLong(_cursorIndexOfVehicleId);
            final long _tmpStartAt;
            _tmpStartAt = _cursor.getLong(_cursorIndexOfStartAt);
            final Long _tmpEndAt;
            if (_cursor.isNull(_cursorIndexOfEndAt)) {
              _tmpEndAt = null;
            } else {
              _tmpEndAt = _cursor.getLong(_cursorIndexOfEndAt);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final long _tmpTotalPausedDuration;
            _tmpTotalPausedDuration = _cursor.getLong(_cursorIndexOfTotalPausedDuration);
            final boolean _tmpIsPaused;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaused);
            _tmpIsPaused = _tmp != 0;
            final Long _tmpLastPausedAt;
            if (_cursor.isNull(_cursorIndexOfLastPausedAt)) {
              _tmpLastPausedAt = null;
            } else {
              _tmpLastPausedAt = _cursor.getLong(_cursorIndexOfLastPausedAt);
            }
            _result = new ShiftEntity(_tmpId,_tmpVehicleId,_tmpStartAt,_tmpEndAt,_tmpNote,_tmpTotalPausedDuration,_tmpIsPaused,_tmpLastPausedAt);
          } else {
            _result = null;
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
  public Flow<List<ShiftEntity>> getShiftsForDateRange(final long startAt, final long endAt) {
    final String _sql = "SELECT * FROM shifts WHERE startAt >= ? AND startAt <= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startAt);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endAt);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"shifts"}, new Callable<List<ShiftEntity>>() {
      @Override
      @NonNull
      public List<ShiftEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfVehicleId = CursorUtil.getColumnIndexOrThrow(_cursor, "vehicleId");
          final int _cursorIndexOfStartAt = CursorUtil.getColumnIndexOrThrow(_cursor, "startAt");
          final int _cursorIndexOfEndAt = CursorUtil.getColumnIndexOrThrow(_cursor, "endAt");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfTotalPausedDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "totalPausedDuration");
          final int _cursorIndexOfIsPaused = CursorUtil.getColumnIndexOrThrow(_cursor, "isPaused");
          final int _cursorIndexOfLastPausedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastPausedAt");
          final List<ShiftEntity> _result = new ArrayList<ShiftEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ShiftEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpVehicleId;
            _tmpVehicleId = _cursor.getLong(_cursorIndexOfVehicleId);
            final long _tmpStartAt;
            _tmpStartAt = _cursor.getLong(_cursorIndexOfStartAt);
            final Long _tmpEndAt;
            if (_cursor.isNull(_cursorIndexOfEndAt)) {
              _tmpEndAt = null;
            } else {
              _tmpEndAt = _cursor.getLong(_cursorIndexOfEndAt);
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final long _tmpTotalPausedDuration;
            _tmpTotalPausedDuration = _cursor.getLong(_cursorIndexOfTotalPausedDuration);
            final boolean _tmpIsPaused;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPaused);
            _tmpIsPaused = _tmp != 0;
            final Long _tmpLastPausedAt;
            if (_cursor.isNull(_cursorIndexOfLastPausedAt)) {
              _tmpLastPausedAt = null;
            } else {
              _tmpLastPausedAt = _cursor.getLong(_cursorIndexOfLastPausedAt);
            }
            _item = new ShiftEntity(_tmpId,_tmpVehicleId,_tmpStartAt,_tmpEndAt,_tmpNote,_tmpTotalPausedDuration,_tmpIsPaused,_tmpLastPausedAt);
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
