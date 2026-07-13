package com.kazio.app.data.local.room;

import android.database.Cursor;
import androidx.annotation.NonNull;
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
public final class IncomeDao_Impl implements IncomeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<IncomeEntity> __insertionAdapterOfIncomeEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteIncome;

  public IncomeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfIncomeEntity = new EntityInsertionAdapter<IncomeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `incomes` (`id`,`shiftId`,`platformId`,`amount`,`occurredAt`,`note`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final IncomeEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getShiftId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, entity.getShiftId());
        }
        statement.bindLong(3, entity.getPlatformId());
        statement.bindDouble(4, entity.getAmount());
        statement.bindLong(5, entity.getOccurredAt());
        if (entity.getNote() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNote());
        }
      }
    };
    this.__preparedStmtOfDeleteIncome = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM incomes WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertIncome(final IncomeEntity income,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfIncomeEntity.insertAndReturnId(income);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteIncome(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteIncome.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
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
          __preparedStmtOfDeleteIncome.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<IncomeEntity>> getIncomesForDateRange(final long startAt, final long endAt) {
    final String _sql = "SELECT * FROM incomes WHERE occurredAt >= ? AND occurredAt <= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startAt);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endAt);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"incomes"}, new Callable<List<IncomeEntity>>() {
      @Override
      @NonNull
      public List<IncomeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfShiftId = CursorUtil.getColumnIndexOrThrow(_cursor, "shiftId");
          final int _cursorIndexOfPlatformId = CursorUtil.getColumnIndexOrThrow(_cursor, "platformId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfOccurredAt = CursorUtil.getColumnIndexOrThrow(_cursor, "occurredAt");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final List<IncomeEntity> _result = new ArrayList<IncomeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final IncomeEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Long _tmpShiftId;
            if (_cursor.isNull(_cursorIndexOfShiftId)) {
              _tmpShiftId = null;
            } else {
              _tmpShiftId = _cursor.getLong(_cursorIndexOfShiftId);
            }
            final long _tmpPlatformId;
            _tmpPlatformId = _cursor.getLong(_cursorIndexOfPlatformId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final long _tmpOccurredAt;
            _tmpOccurredAt = _cursor.getLong(_cursorIndexOfOccurredAt);
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            _item = new IncomeEntity(_tmpId,_tmpShiftId,_tmpPlatformId,_tmpAmount,_tmpOccurredAt,_tmpNote);
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
