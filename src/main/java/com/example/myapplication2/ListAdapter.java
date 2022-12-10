package com.example.myapplication2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter {

    private Activity mContext;
    List<Produit> produitsList;

    public ListAdapter(Activity mContext, List<Produit> produitsList){
        super(mContext,R.layout.list_item,produitsList);
        this.mContext = mContext;
        this.produitsList = produitsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_item,null,true);

        TextView tvName = listItemView.findViewById(R.id.tvName);
        TextView tvPrix = listItemView.findViewById(R.id.tvPrix);
        TextView tvCategorie = listItemView.findViewById(R.id.tvCategorie);

        Produit produits = produitsList.get(position);

        tvName.setText(produits.getName());
        tvPrix.setText(produits.getPrix());
        tvCategorie.setText(produits.getCategorie());

        return listItemView;
    }
}