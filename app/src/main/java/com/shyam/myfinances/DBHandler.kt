package com.shyam.myfinances

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MyFinances.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "FinancialObjects"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TYPE = "type"
        private const val COLUMN_ACCOUNT_NUMBER = "accountNumber"
        private const val COLUMN_INITIAL_BALANCE = "initialBalance"
        private const val COLUMN_CURRENT_BALANCE = "currentBalance"
        private const val COLUMN_INTEREST_RATE = "interestRate"
        private const val COLUMN_PAYMENT_AMOUNT = "paymentAmount"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_TYPE TEXT, $COLUMN_ACCOUNT_NUMBER TEXT, $COLUMN_INITIAL_BALANCE TEXT, $COLUMN_CURRENT_BALANCE TEXT, $COLUMN_INTEREST_RATE TEXT, $COLUMN_PAYMENT_AMOUNT TEXT)"
        db?.execSQL(createTable)
    }


    fun insertFinancialObject(financialObject: FinancialObject) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_TYPE, financialObject.type)
        values.put(COLUMN_ACCOUNT_NUMBER, financialObject.accountNumber)
        values.put(COLUMN_INITIAL_BALANCE, financialObject.initialBalance)
        values.put(COLUMN_CURRENT_BALANCE, financialObject.currentBalance)
        values.put(COLUMN_INTEREST_RATE, financialObject.interestRate)
        values.put(COLUMN_PAYMENT_AMOUNT, financialObject.paymentAmount)

        val success = db.insert(TABLE_NAME, null, values)
        db.close()


    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}
