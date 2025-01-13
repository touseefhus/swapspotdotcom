package com.example.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    // Constructor for the adapter
    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    // Create a new ViewHolder (called when RecyclerView needs a new view to display)
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each product item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    // Bind the data (product) to the ViewHolder (called for each item in the list)
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Bind product data to the respective views
        holder.name.setText(product.name);
        holder.category.setText(product.category);
        holder.description.setText(product.description);

        // Load the image URI using Glide
        Glide.with(holder.itemView.getContext())
                .load(product.imageUri)
                .into(holder.image);
    }

    // Return the total number of items (products) in the list
    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder class that holds references to the views for each product item
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name, category, description;
        ImageView image;

        public ProductViewHolder(View itemView) {
            super(itemView);
            // Find views by ID in the product_item layout
            name = itemView.findViewById(R.id.product_name);
            category = itemView.findViewById(R.id.product_category);
            description= itemView.findViewById(R.id.product_description);
            image = itemView.findViewById(R.id.product_image);
        }
    }
}
