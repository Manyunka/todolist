package com.example.notforgot.view.tasks

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.notforgot.R
import com.example.notforgot.model.NotForgotAPIService
import com.example.notforgot.model.PreferencesHandler
import com.example.notforgot.model.tasks.Category
import com.example.notforgot.presenter.tasks.CreateTaskPresenter
import kotlinx.android.synthetic.main.activity_create_task.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import okhttp3.OkHttpClient


class CreateTaskActivity : AppCompatActivity(), CreateTaskView {

    private lateinit var presenter: CreateTaskPresenter

    @SuppressLint("SetTextI18n")
    override fun getDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                dataEdit.setText("До $mDay.$mMonth.$mYear")
            }, year, month, day
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        val presenter = CreateTaskPresenter(this, applicationContext)



        val preferencesHandler = PreferencesHandler(applicationContext)
        val token = preferencesHandler.readToken()


        addCategory.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            builder.setTitle("Добавить категорию")

            val dialogLayout = inflater
                .inflate(R.layout.alert_dialog, null)

            val editCategory = dialogLayout
                .findViewById<EditText>(R.id.editCategory)
            builder.setView(dialogLayout)
            builder.setPositiveButton(
                "СОХРАНИТЬ", null
            )
            builder.setNegativeButton("ОТМЕНА", null)

            val dialog = builder.create()
            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                if (editCategory.text.isNotEmpty()) {
                    val client = OkHttpClient.Builder().addInterceptor { chain ->
                        val newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer $token")
                            .build()
                        chain.proceed(newRequest)
                    }.build()

                    val retrofit = Retrofit.Builder()
                        .client(client)
                        .baseUrl("http://practice.mobile.kreosoft.ru/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    val service = retrofit.create(NotForgotAPIService::class.java)

                    val request = service.createCategory(editCategory.text.toString())

                    request.enqueue(object : Callback<Category> {
                        override fun onFailure(call: Call<Category>, t: Throwable) {
                            editCategory.error = t.message
                        }

                        override fun onResponse(
                            call: Call<Category>,
                            response: Response<Category>
                        ) {
                            val body = response.body()
                            if (body != null) {
                                Toast.makeText(
                                    applicationContext,
                                    "Категория успешно сохранена",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                dialog.dismiss()
                            } else {
                                editCategory.error = response.message()
                            }

                        }

                    })
                } else {
                    editCategory.error = "Введите название категории"
                }
            }

        }

        dataEdit.setOnClickListener {
            presenter.onDataEditClick()
        }

        createTaskButton.setOnClickListener {
            val taskTitle = taskTitleEdit.text.toString()
            val descript = descriptEdit.text.toString()

            if (taskTitle.isEmpty()) {
                taskTitleTil.error = "Введите e-mail"
                taskTitleEdit.requestFocus()
                return@setOnClickListener
            } else taskTitleTil.error = ""

            if (descript.isEmpty()) {
                descriptTil.error = "Введите пароль"
                descriptEdit.requestFocus()
                return@setOnClickListener
            } else descriptTil.error = ""
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Сохранить?")
            .setPositiveButton("ДА") { _, _ -> }
            .setNegativeButton("НЕТ") { _, _ -> finish() }

        val alert = builder.create()
        alert.show()
        //super.onBackPressed()
    }

    override fun onDestroy() {
        presenter.onDestroyActivity()
        super.onDestroy()
    }
}
