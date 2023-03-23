package com.example.listcontacts.view.feature.details

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.listcontacts.*
import com.example.listcontacts.domain.Contact
import java.lang.Exception
import javax.inject.Inject

class ContentActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: ContactRepository

    private val save by lazy { findViewById<Button>(R.id.save) }
    private val cancel by lazy { findViewById<Button>(R.id.cancel) }
    private val editName by lazy { findViewById<EditText>(R.id.enterNameText) }
    private val editSecondName by lazy { findViewById<EditText>(R.id.enterSecondNameText) }
    private val editPhone by lazy { findViewById<EditText>(R.id.phoneText) }

    override fun onCreate(savedInstanceState: Bundle?) {


        try {
            Log.e("***", "${repository.getContactOnId(0)}")
        } catch (e: Exception) {
            Log.e("***", e.toString())
        }

        (application as App).appComponent.plus(this)

        try {
            Log.e("***", "${repository.getContactOnId(0)}")
        } catch (e: Exception) {
            Log.e("***", e.toString())
        }


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val idDataBase = intent?.extras?.getLong("idDataBase") ?: -1L
        if (idDataBase == -1L) {
            save.setOnClickListener {

                val name = editName.text.toString()
                val secondName = editSecondName.text.toString()
                val numPhone = editPhone.text.toString()

                if (!name.isNullOrEmpty() || !secondName.isNullOrEmpty() || !numPhone.isNullOrEmpty())
                    repository.add(
                        Contact(
                            -1,
                            name = name,
                            secondName = secondName,
                            numPhone = numPhone
                        )
                    )
                finish()
            }
        } else {
            val oldContact = repository.getContactOnId(idDataBase)
            editName.setText(oldContact.name)
            editSecondName.setText(oldContact.secondName)
            editPhone.setText(oldContact.numPhone)


            save.setOnClickListener {
                if ((!editName.text.isNullOrEmpty() && editName.text.equals(oldContact.name))  ||
                    (!editSecondName.text.isNullOrEmpty()&&editSecondName.text.equals(oldContact.secondName)) ||
                    (!editPhone.text.isNullOrEmpty()&&editPhone.text.equals(oldContact.numPhone)))
                    repository.update(
                        Contact(
                            oldContact.id,
                            name = editName.text.toString(),
                            secondName = editSecondName.text.toString(),
                            numPhone = editPhone.text.toString()
                        )
                    )
                finish()
            }
        }
        cancel.setOnClickListener {
            this.finish()
        }

    }
}