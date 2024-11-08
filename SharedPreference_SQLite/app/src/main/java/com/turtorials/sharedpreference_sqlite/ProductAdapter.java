package com.turtorials.sharedpreference_sqlite;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

//
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //tạo ra các ViewHolder mới để chứa các item hiển thị trong RecyclerView
        //"bơm" (inflate) một layout item_product XML thành một đối tượng View
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    //để gán dữ liệu vào danh sách
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Sử dụng toán tử modulo để lấy phần tử đúng từ danh sách
        Product product = productList.get(position % productList.size());
        holder.itemNameDetail.setText(product.getName());
        holder.itemPriceDetail.setText(String.format("%.2f", product.getPrice()));

        // Lấy ảnh từ drawable như trước
        int imageResId = context.getResources().getIdentifier(
                product.getImagePath(), "drawable", context.getPackageName());

        if (imageResId != 0) {
            holder.itemImage.setImageResource(imageResId);
        } else {
            holder.itemImage.setImageResource(R.drawable.spinner_background);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
    ImageView itemImage;
    TextView itemNameDetail;
    TextView itemPriceDetail;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        itemImage = itemView.findViewById(R.id.itemImage);
        itemNameDetail = itemView.findViewById(R.id.itemNameDetail);
        itemPriceDetail = itemView.findViewById(R.id.itemPriceDetail);
    }
}
}