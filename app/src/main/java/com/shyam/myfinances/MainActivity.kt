package com.shyam.myfinances

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var accountTypeRadioGroup: RadioGroup
    private lateinit var cdRadioButton: RadioButton
    private lateinit var loanRadioButton: RadioButton
    private lateinit var checkingRadioButton: RadioButton
    private lateinit var accountNumberEditText: EditText
    private lateinit var initialBalanceEditText: EditText
    private lateinit var currentBalanceEditText: EditText
    private lateinit var paymentAmountEditText: EditText
    private lateinit var interestRateEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button
    private lateinit var messageTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        var isUserAction = false

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        accountTypeRadioGroup = findViewById(R.id.account_type_radio_group)
        cdRadioButton = findViewById(R.id.radio_cd)
        loanRadioButton = findViewById(R.id.radio_loan)
        checkingRadioButton = findViewById(R.id.radio_checking)
        accountNumberEditText = findViewById(R.id.account_number)
        initialBalanceEditText = findViewById(R.id.initial_balance)
        currentBalanceEditText = findViewById(R.id.current_balance)
        paymentAmountEditText = findViewById(R.id.payment_amount)
        interestRateEditText = findViewById(R.id.interest_rate)
        saveButton = findViewById(R.id.save_button)
        cancelButton = findViewById(R.id.cancel_button)
        messageTextView = findViewById(R.id.message)

        accountTypeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            //isUserAction = true
            if(isUserAction){
                messageTextView.text = ""
            }

            when (checkedId) {
                R.id.radio_cd -> {
                    initialBalanceEditText.visibility = View.VISIBLE
                    paymentAmountEditText.visibility = View.GONE
                    interestRateEditText.visibility = View.VISIBLE
                }
                R.id.radio_loan -> {
                    initialBalanceEditText.visibility = View.VISIBLE
                    paymentAmountEditText.visibility = View.VISIBLE
                    interestRateEditText.visibility = View.VISIBLE
                }
                R.id.radio_checking -> {
                    initialBalanceEditText.visibility = View.GONE
                    paymentAmountEditText.visibility = View.GONE
                    interestRateEditText.visibility = View.GONE
                }
            }
        }

        saveButton.setOnClickListener {
            var accountNumber = accountNumberEditText.text.toString().trim()
            var currentBalance = currentBalanceEditText.text.toString().trim()

            var initialBalance = ""
            var interestRate = ""
            var paymentAmount = ""

            if (accountNumber.isEmpty() || currentBalance.isEmpty()) {
                messageTextView.text = "Please enter account number and current balance"
                return@setOnClickListener
            }

            val selectedId = accountTypeRadioGroup.checkedRadioButtonId
            if (selectedId == -1) {
                messageTextView.text = "Please select account type"
                return@setOnClickListener
            }

            val accountType = when (selectedId) {
                R.id.radio_cd -> {
                    initialBalance = initialBalanceEditText.text.toString().trim()
                    interestRate = interestRateEditText.text.toString().trim()
                    if (initialBalance.isEmpty() || interestRate.isEmpty()) {
                        messageTextView.text = "Please enter initial balance and interest rate"
                        return@setOnClickListener
                    }
                    "CD"
                }
                R.id.radio_loan -> {
                    initialBalance = initialBalanceEditText.text.toString().trim()
                    paymentAmount = paymentAmountEditText.text.toString().trim()
                    interestRate = interestRateEditText.text.toString().trim()
                    if (initialBalance.isEmpty() || paymentAmount.isEmpty() || interestRate.isEmpty()) {
                        messageTextView.text =
                            "Please enter initial balance, payment amount, and interest rate"
                        return@setOnClickListener
                    }
                    "Loan"
                }
                R.id.radio_checking -> {
                    "Checking"
                }
                else -> {
                    ""
                }
            }

            val dbHandler = DBHandler(this)
            val financialObject = FinancialObject().apply {
                type = accountType
                accountNumber = accountNumber
                initialBalance =initialBalance
                currentBalance = currentBalance
                interestRate = interestRate
            }
            dbHandler.insertFinancialObject (financialObject)

            messageTextView.text = "Saved"
            isUserAction = false

            // Clear the screen
            accountNumberEditText.setText("")
            initialBalanceEditText.setText("")
            currentBalanceEditText.setText("")
            paymentAmountEditText.setText("")
            interestRateEditText.setText("")
            accountTypeRadioGroup.clearCheck()
        }

        cancelButton.setOnClickListener {
            accountNumberEditText.setText("")
            initialBalanceEditText.setText("")
            currentBalanceEditText.setText("")
            interestRateEditText.setText("")
            paymentAmountEditText.setText("")
            messageTextView.setText("Data Cleared")
            isUserAction = false
        }

    }
}


