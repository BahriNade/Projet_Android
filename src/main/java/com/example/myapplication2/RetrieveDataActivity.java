package com.example.myapplication2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RetrieveDataActivity extends AppCompatActivity {

    ListView myListview;
    List<Produit> produitsList;

    DatabaseReference produitDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_data);

        myListview = findViewById(R.id.ListView);
        produitsList = new ArrayList<>();

        produitDbRef = FirebaseDatabase.getInstance().getReference("Produits");

        produitDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produitsList.clear();

                for (DataSnapshot produitDatasnap : dataSnapshot.getChildren()){
                    Produit produits = produitDatasnap.getValue(Produit.class);
                    produitsList.add(produits);
                }

                ListAdapter adapter = new ListAdapter(RetrieveDataActivity.this,produitsList);
                myListview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //set itemLong listener on listview item

        myListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Produit produits = produitsList.get(position);
                showUpdateDialog(produits.getId(), produits.getName() ,produits.getPrix());

                String name = produits.getName();
                String prix = produits.getPrix();
                
                return false;
            }
        });
    }

    private void showUpdateDialog(final String id, String name, String prix){

        final AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View mDialogView = inflater.inflate(R.layout.update_dialog, null);

        mDialog.setView(mDialogView);

        //create views refernces
        final EditText etUpdateName = mDialogView.findViewById(R.id.etUpdateName);
        final EditText etUpdatePrix = mDialogView.findViewById(R.id.etUpdatePrix);
        final Spinner mSpinner = mDialogView.findViewById(R.id.updateSpinner);
        Button btnUpdate = mDialogView.findViewById(R.id.btnUpdate);
        Button btnDelete = mDialogView.findViewById(R.id.btnDelete);

        etUpdateName.setText(name);
        etUpdatePrix.setText(prix);


        mDialog.setTitle("Updating " + name +" record");

        final AlertDialog alertDialog = mDialog.create();
        alertDialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                //here we will update data in database
                //now get values from view

                String newName = etUpdateName.getText().toString();
                String newPrix = etUpdatePrix.getText().toString();
                String newCategorie = mSpinner.getSelectedItem().toString();

                    updateData(id, newName, newPrix, newCategorie);

                    Toast.makeText(RetrieveDataActivity.this, "Record Updated", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();

            }

        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRecord(id);

                alertDialog.dismiss();
            }
        });
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void deleteRecord(String id){
        //create reference to database
        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("Produits").child(id);
        //we referencing child here because we will be delete one record not whole data data in database
        //we will use generic Task here so lets do it..

        Task<Void> mTask = DbRef.removeValue();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showToast("Deleted");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showToast("Error deleting record");
            }
        });
    }

    private void updateData(String id, String name, String prix, String categorie){

        //creating database reference
        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("Produits").child(id);


        Produit produit = new Produit(id, name, prix, categorie);
        DbRef.setValue(produit);
    }

}