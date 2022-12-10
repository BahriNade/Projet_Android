package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertingDataActivity extends AppCompatActivity {

    EditText etName;
    EditText etPrix;
    Spinner spinnerCategories;
    Button btnInsertData;
    DatabaseReference produitDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserting_data);

        etName = findViewById(R.id.etName);
        etPrix = findViewById(R.id.etPrix);
        spinnerCategories = findViewById(R.id.spinnerCategorie);
        btnInsertData = findViewById(R.id.btnInsertData);
        produitDbRef = FirebaseDatabase.getInstance().getReference("Produits");
        btnInsertData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertProduitData();
            }
        });
    }

    private void insertProduitData(){
        String name = etName.getText().toString();
        String prix = etPrix.getText().toString();
        String categorie = spinnerCategories.getSelectedItem().toString();

        String id = produitDbRef.push().getKey();
        if (TextUtils.isEmpty(name)){
            etName.setError("Name cannot be empty");
            etName.requestFocus();
        }else if(TextUtils.isEmpty(prix)){
            etPrix.setError("Prix cannot be empty");
            etPrix.requestFocus();
        }else{

        Produit students = new Produit(id,name,prix,categorie);
        assert id != null;
        produitDbRef.child(id).setValue(students);
        Toast.makeText(InsertingDataActivity.this,"Data inserted!",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(InsertingDataActivity.this,RetrieveDataActivity.class));
    }}
}